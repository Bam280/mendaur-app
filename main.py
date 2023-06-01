# pylint: disable=locally-disabled, multiple-statements, fixme, line-too-long

from fastapi import FastAPI, UploadFile, File
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from google.cloud import firestore
from google.cloud import storage
from werkzeug.utils import secure_filename
from nanoid import generate

app = FastAPI()
project_id="mendaurbeta"
db = firestore.Client(project=project_id)

CLOUD_STORAGE_BUCKET = "mendaurbeta.appspot.com"
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}

class Location(BaseModel):
    uid: str
    longitude: float
    latitude: float

class Article(BaseModel):
    title: str
    content: str

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.get("/")
def home():
    return 'Hello, world!'

@app.post("/save-location")
async def save_location(location: Location):

    doc_ref = db.collection("users").document(location.uid)
    doc_ref.set({
        "uid": location.uid,
        "longitude": location.longitude,
        "latitude": location.latitude,
    })

    return {"message": "Location saved successfully"}

@app.post("/upload")
async def upload(file: UploadFile = File(...)):

    if not allowed_file(file.filename):
        return JSONResponse(content="Invalid file type. Please upload an image.", status_code=400)

    gcs = storage.Client()
    bucket = gcs.get_bucket(CLOUD_STORAGE_BUCKET)     
    filename = secure_filename(file.filename)
    file_path = f"scan_img/{filename}"

    blob = bucket.blob(file_path)
    blob.upload_from_file(file.file, content_type=file.content_type)

    public_url = blob.public_url

    response = {
        "message": "File uploaded successfully",
        "public_url": public_url
    }

    return response

@app.post("/article")
async def upload_article(article: Article):
    article_id = str(generate())

    doc_ref = db.collection("articles").document(article_id)
    doc_ref.set({
        "article_id": article_id,
        "title": article.title,
        "content": article.content,
    })

    return {
        "article_id": article_id,
        "title": article.title,
        "content": article.content,
    }

@app.exception_handler(500)
async def server_error(request, exc):
    return JSONResponse(content="An internal error occurred.", status_code=500)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8080)