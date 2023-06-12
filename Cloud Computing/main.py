import json
from os import getcwd, path
import uvicorn
import tensorflow as tf
import tensorflow_hub as hub
import numpy as np
import datetime
from io import BytesIO
from typing import Union
from fastapi import FastAPI, UploadFile, File, Form
from fastapi.responses import JSONResponse
from google.cloud import firestore, storage
from google.oauth2 import service_account
from pydantic import BaseModel
from PIL import Image

app = FastAPI()
project_id = "your_project_id"
key_path = path.join(getcwd(), "path/to/serviceAccount.json")
credentials = service_account.Credentials.from_service_account_file(key_path)
db = firestore.Client(project=project_id, credentials=credentials)
CLOUD_STORAGE_BUCKET = "your_bucket"
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}

class Article(BaseModel):
    title: str
    content: str
    createdAt: str

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

RECYCLE_METHOD_FILE = path.join(getcwd(), path.join("dataset", path.join("recycle_recommendation.json")))
MODEL_PATH = path.join(getcwd(), path.join("model", path.join("model_fv_resnet50_v6.h5")))
MODEL = tf.keras.models.load_model(MODEL_PATH, custom_objects={'KerasLayer':hub.KerasLayer})
LABEL = ['besi', 'kaca', 'kardus', 'kertas', 'organik', 'plastik']


def preprocess(image):
    img = tf.convert_to_tensor(image, dtype=np.float32) / 255.0
    img_tensor = tf.image.resize(img, [224, 224])
    img_tensor = np.expand_dims(img_tensor, axis=0)
    img_tensor = np.vstack([img_tensor])
    return img_tensor

def predict(image_file):
    res_dict = dict()
    try:
        img_tensor = preprocess(image_file)
        pred_res  = MODEL.predict(img_tensor)
        pred_idx = np.argmax(pred_res)
        pred_label = LABEL[pred_idx]
        pred_percent = round(np.max(pred_res) * 100, 2)
        res_dict["error"] = False
        res_dict["message"] = "Image predicted successfully"
        res_dict["result"] = {
            "jenis": pred_label if pred_label != "organik" else "sampah",
            "tipe" : "non-organik" if pred_label != "organik" else "organik",
            "akurasi": f"{pred_percent}%"
        }
        return res_dict
    except Exception as err:
        res_dict["error"] = True
        res_dict["message"] = err.__str__
        return res_dict

@app.get("/")
def home():
    return 'Hello, world!'

@app.post("/upload")
async def upload(file: UploadFile = File(...)):
    if not allowed_file(file.filename):
        return JSONResponse(content="Jenis file tidak valid. Silakan unggah gambar.", status_code=400)

    image = Image.open(BytesIO(await file.read()))
    image_arr = np.asarray(image)
    return predict(image_arr)

@app.post("/articles")
async def post_articles(
    uid: str = Form(...),
    title: str = Form(...),
    content: str = Form(...),
    file: UploadFile = File(...),
):
    try:
        gcs = storage.Client()
        bucket = gcs.get_bucket(CLOUD_STORAGE_BUCKET)
        file_path = f"articles_img/{file.filename}"
        blob = bucket.blob(file_path)

        blob.upload_from_file(file.file)
        img_url = blob.public_url

        doc_test = db.collection('users').document(uid)
        data = doc_test.get()
        if data.exists:
            datauser = data.to_dict()
            name = datauser['name']
            photo_url = datauser['photourl']

        doc_ref = db.collection('articles').document()
        doc_ref.set({
            'name': name,
            'profile_picture': photo_url,
            'title': title,
            'content': content,
            'url': img_url,
            'createdAt': datetime.datetime.now().isoformat() + "Z"
        })

        res_dict = {
            "error": False,
            "message": "Data berhasil diunggah",
            "public url": img_url
        }
        return res_dict

    except Exception as err:
        res_dict = {
            "error": True,
            "message": "Terjadi kesalahan saat memposting artikel.",
            "details": str(err)
        }
        return JSONResponse(content=res_dict, status_code=500)

@app.get("/get-articles")
async def get_articles():
    try:
        articles = []
        articles_ref = db.collection('articles').stream()

        for doc in articles_ref:
            article_data = doc.to_dict()
            article_data["createdAt"] = datetime.datetime.strptime(article_data["createdAt"], "%Y-%m-%dT%H:%M:%S.%fZ").isoformat()
            articles.append(article_data)

        res_dict = {
            "error": False,
            "message": "Berhasil mengambil data",
            "articles": articles
        }
        return res_dict

    except Exception as err:
        res_dict = {
            "error": True,
            "message": "Terjadi kesalahan saat mengambil artikel.",
            "details": str(err)
        }
        return JSONResponse(content=res_dict, status_code=500)



@app.get("/get-recycle")
async def recycle_rec(jenis: Union[str, None] = None):
    res_dict = dict()
    if jenis == None:
        res_dict["error"] = True
        res_dict["message"] = "Please input query 'jenis'."
        return JSONResponse(content=res_dict, status_code=400)
    try:
        with open(RECYCLE_METHOD_FILE) as json_file:
            data = json.load(json_file)
            for item in data:
                if item["jenis"].lower() == jenis.lower():
                    res_dict["error"] = False
                    res_dict["message"] = f"Recycle methods for {jenis} found."
                    res_dict["list_recycle"] = item
                    return res_dict
            res_dict["error"] = True
            res_dict["message"] = f"Sorry! recycle methods for {jenis} not found."
            return JSONResponse(content=res_dict, status_code=404)
    except:
        res_dict["error"] = True
        res_dict["message"] = "Internal Server Error"
        return JSONResponse(content=res_dict, status_code=500)


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8080)
