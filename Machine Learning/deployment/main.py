from typing import Union
from fastapi import FastAPI
from fastapi.responses import JSONResponse
import json
from os import getcwd, path
import uvicorn

app = FastAPI()
RECYCLE_METHOD_FILE = path.join(getcwd(), path.join("dataset", path.join("recycle_recommendation.json")))

def predict(image):
    return 

@app.get("/get-recycle")
async def save_location(jenis: Union[str, None] = None):
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