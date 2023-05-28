import requests
from bs4 import BeautifulSoup
import os
import re
import shutil
from appscript import app, mactypes
import datetime


def Find(string):

    try:
        regex = r"(?i)\b((?:https?://|www\d{0,3}[.]|[a-z0-9.\-]+[.][a-z]{2,4}/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’]))"
        url = re.findall(regex, string)
        return [x[0] for x in url]
    except:
        print("Regex Failed To Load!")


def GrabImage(url):
    try:
        page = requests.get(url)
        if page.status_code == 200:
            print("Page Downloaded Successfully!")
    except:
        print("URL Failed To Load!")
    try:
        arr = []
        time = datetime.datetime.now()
        soup = BeautifulSoup(page.content, 'html.parser')
        slider = soup.select('#top100-carousel')

        for s in slider:
             for i in s.find_all('img'):
                arr.append(Find(str(i)))

        if time.strftime("%d") == "01":
            image_url = str(arr[0])
        if time.strftime("%d") == "02":
            image_url = str(arr[1])
        if time.strftime("%d") == "03":
            image_url = str(arr[2])
        if time.strftime("%d") == "04":
            image_url = str(arr[3])
        if time.strftime("%d") == "05":
            image_url = str(arr[4])
        if time.strftime("%d") == "06":
            image_url = str(arr[5])
        if time.strftime("%d") == "07":
            image_url = str(arr[7])
        if time.strftime("%d") == "08":
            image_url = str(arr[8])
        if time.strftime("%d") == "09":
            image_url = str(arr[9])
        if time.strftime("%d") == "10":
            image_url = str(arr[10])
        if time.strftime("%d") == "11":
            image_url = str(arr[11])
        if time.strftime("%d") == "12":
            image_url = str(arr[12])
        if time.strftime("%d") == "13":
            image_url = str(arr[13])
        if time.strftime("%d") == "14":
            image_url = str(arr[14])
        if time.strftime("%d") == "15":
            image_url = str(arr[15])
        if time.strftime("%d") == "16":
            image_url = str(arr[16])
        if time.strftime("%d") == "17":
            image_url = str(arr[17])
        if time.strftime("%d") == "18":
            image_url = str(arr[18])
        if time.strftime("%d") == "19":
            image_url = str(arr[19])
        if time.strftime("%d") == "20":
            image_url = str(arr[20])
        if time.strftime("%d") == "21":
            image_url = str(arr[21])
        if time.strftime("%d") == "22":
            image_url = str(arr[22])
        if time.strftime("%d") == "23":
            image_url = str(arr[23])
        if time.strftime("%d") == "24":
            image_url = str(arr[24])
        if time.strftime("%d") == "25":
            image_url = str(arr[25])
        if time.strftime("%d") == "26":
            image_url = str(arr[26])
        if time.strftime("%d") == "27":
            image_url = str(arr[27])
        if time.strftime("%d") == "28":
            image_url = str(arr[28])

        image_url = image_url.strip("[']")

        response = requests.get(image_url, stream=True)
        file = open("PictureOfTheDay.png", "wb")
        response.raw_decode_content = True
        shutil.copyfileobj(response.raw, file)
        del response

        app('Finder').desktop_picture.set(mactypes.File('PictureOfTheDay.png'))

    except:
        print("Failed To Download Image!")

if __name__ == "__main__":

    print("Downloading Picture Of The Day! ....")
    GrabImage("https://esahubble.org/images/archive/top100/")
    print("Done ...")
