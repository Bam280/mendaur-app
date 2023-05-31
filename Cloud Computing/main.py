from __future__ import annotations

import logging
import os
from werkzeug.utils import secure_filename
from flask import Flask, request, jsonify
from google.cloud import storage

app = Flask(__name__)

# Configure this environment variable via app.yaml
CLOUD_STORAGE_BUCKET = os.environ["CLOUD_STORAGE_BUCKET"]
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route("/", methods=["GET"])
def home():
    return 'Hello, world!'

@app.route("/upload", methods=["POST"])
def upload() -> str:
    """Process the uploaded file and upload it to Google Cloud Storage."""
    uploaded_file = request.files.get("file")

    if not uploaded_file:
        return "No file uploaded.", 400

    if not allowed_file(uploaded_file.filename):
        return "Invalid file type. Please upload an image.", 400

    # Create a Cloud Storage client.
    gcs = storage.Client()

    # Get the bucket that the file will be uploaded to.
    bucket = gcs.get_bucket(CLOUD_STORAGE_BUCKET)

    # Generate a secure filename
    filename = secure_filename(uploaded_file.filename)

    # Create a new blob and upload the file's content.
    blob = bucket.blob(filename)

    blob.upload_from_string(
        uploaded_file.read(), content_type=uploaded_file.content_type
    )

    # Make the blob public. This is not necessary if the
    # entire bucket is public.
    # See https://cloud.google.com/storage/docs/access-control/making-data-public.
    # blob.make_public()

    # # The public URL can be used to directly access the uploaded file via HTTP.
    # return blob.public_url
    public_url = blob.public_url

    # Create JSON response
    response = {
        "public_url": public_url
    }

    # Return JSON response
    return jsonify(response)

@app.errorhandler(500)
def server_error(e: Exception | int) -> str:
    logging.exception("An error occurred during a request.")
    return """
    An internal error occurred: <pre>{}</pre>
    See logs for full stacktrace.
    """.format(e), 500

if __name__ == "__main__":
    # This is used when running locally. Gunicorn is used to run the
    # application on Google App Engine. See entrypoint in app.yaml.
    app.run(host="127.0.0.1", port=8080, debug=True)
