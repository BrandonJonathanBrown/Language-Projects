
import requests
from bs4 import BeautifulSoup
import shutil
from appscript import app, mactypes
import datetime
import re

def find_image_urls(html_content):
    """
    Finds image URLs in the given HTML content using regular expressions.
    
    Args:
        html_content (str): The HTML content as a string.
        
    Returns:
        list: A list of found image URLs.
    """
    # Adjust the regex pattern based on the actual HTML structure and URL patterns
    regex_pattern = r'src="([^"]+\.(?:png|jpg|jpeg|gif))"'
    urls = re.findall(regex_pattern, html_content)
    return urls

def grab_image(url):
    """
    Downloads the image of the day from the specified URL and sets it as desktop wallpaper.
    
    Args:
        url (str): The URL to fetch the images from.
    """
    try:
        response = requests.get(url)
        response.raise_for_status()  # Raises an HTTPError for bad responses
        print("Page downloaded successfully!")
    except requests.RequestException as e:
        print(f"Failed to load URL: {e}")
        return
    
    try:
        # Use BeautifulSoup to parse HTML if needed or directly use regex on response.text
        soup = BeautifulSoup(response.content, 'html.parser')
        html_content = str(soup)
        image_urls = find_image_urls(html_content)
        
        day_of_month = datetime.datetime.now().day
        image_url = image_urls[day_of_month - 1] if day_of_month <= len(image_urls) else image_urls[-1]
        
        download_image(image_url)
    except Exception as e:
        print(f"Failed to process page content: {e}")

def download_image(image_url):
    """
    Downloads an image from the given URL and saves it locally.
    
    Args:
        image_url (str): The URL of the image to download.
    """
    try:
        response = requests.get(image_url, stream=True)
        response.raise_for_status()
        
        with open("PictureOfTheDay.png", "wb") as file:
            shutil.copyfileobj(response.raw, file)
        print("Image downloaded and saved successfully!")
        
        # Set the downloaded image as the desktop background
        app('Finder').desktop_picture.set(mactypes.File('PictureOfTheDay.png'))
        print("Desktop background updated.")
    except requests.RequestException as e:
        print(f"Failed to download image: {e}")

if __name__ == "__main__":
    print("Downloading Picture Of The Day! ....")
    grab_image("https://esahubble.org/images/archive/top100/")
    print("Done ...")
