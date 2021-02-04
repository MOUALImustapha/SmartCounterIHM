from flask import Flask,render_template, request
#from flask_cors import CORS
import requests
import cv2
import pytesseract
import numpy as np
import base64
from datetime import datetime
import re

app = Flask(_name_) //On donne un nom à l’application ici ce sera app
#CORS(app)

@app.route("/")
def index():
    r = requests.get('http://localhost:9080/api/utilisateurs').json()
    cr = requests.get('http://localhost:9080/api/compteurs').json()
    users = map(lambda user: user['cin'],r['_embedded']['utilisateurs'])

    return render_template('index.html',users = list(users))

@app.route("/predict", methods=["POST"])
def predict():

    pytesseract.pytesseract.tesseract_cmd = r"C:\Program Files\Tesseract-OCR\tesseract.exe"
    kernel = np.array([[-1,-1,-1], [-1,9,-1], [-1,-1,-1]])
    image = cv2.imdecode(np.fromstring(request.files['image'].read(), np.uint8), 0)
    
    image = cv2.erode(image, kernel, iterations=1)
    image = cv2.threshold(image, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)[1]

    image = cv2.filter2D(image, -1, kernel)

    data = pytesseract.image_to_string(image, lang='eng',config='--psm 6')
    print(request.files)
    data = re.sub('[^0-9]','', data)
    print(int(data))
    
    obj = {
        "date" : "2021-02-01T10:34:01.754Z",
        "valeur" : float(data),
        "compteur" : {"id" : 1 }
    }
    response = requests.post('http://localhost:9080/api/mesures',json=obj,headers = {"Content-Type": "application/json"})
   
    print(response.json())
    return data

if _name_ == "_main_":
    app.run(debug=True)