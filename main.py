from fastapi import FastAPI, UploadFile, File
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from google.cloud import firestore
from google.cloud import storage
from werkzeug.utils import secure_filename

app = FastAPI()
db = firestore.Client()

CLOUD_STORAGE_BUCKET = "mendaur-projtest.appspot.com"
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}

class Location(BaseModel):
    uid: str
    longitude: float
    latitude: float

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
    

@app.get("/")
def home():
    return 'Hello, world!'

@app.post("/save-location")
async def save_location(location: Location):
    # Create a new document in the 'locations' collection with the provided data
    doc_ref = db.collection("locations").document()
    doc_ref.set({
        "uid": location.uid,
        "longitude": location.longitude,
        "latitude": location.latitude
    })

    return {"message": "Location saved successfully"}

@app.post("/upload")
async def upload(file: UploadFile = File(...)):
    """Process the uploaded file and upload it to Google Cloud Storage."""
    if not allowed_file(file.filename):
        return JSONResponse(content="Invalid file type. Please upload an image.", status_code=400)

    # Create a Cloud Storage client.
    gcs = storage.Client()

    # Get the bucket that the file will be uploaded to.
    bucket = gcs.get_bucket(CLOUD_STORAGE_BUCKET)

    # Generate a secure filename
    filename = secure_filename(file.filename)

    # Create a new blob and upload the file's content.
    blob = bucket.blob(filename)

    blob.upload_from_file(file.file, content_type=file.content_type)

    # The public URL can be used to directly access the uploaded file via HTTP.
    public_url = blob.public_url

    # Create JSON response
    response = {
        "public_url": public_url
    }

    # Return JSON response
    return response

@app.exception_handler(500)
async def server_error(request, exc):
    return JSONResponse(content="An internal error occurred.", status_code=500)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="127.0.0.1", port=8080)
