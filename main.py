from pytube import YouTube
from pytube.cli import on_progress
from progress.spinner import MoonSpinner
import time
import os


def Progressbar():
    spinner = MoonSpinner('Youtube Downloader :)')
    FINISHED = False
    while not FINISHED:
        for i in range(100):
            time.sleep(0.01)
            spinner.next()
        FINISHED = True
        spinner.finish()

def GetYoutubeDownloader(url):

    print("Select Video Or Audio:-")
    quality_list = {
        '1': 'Video',
        '2': 'Audio',
    }

    for i, q in quality_list.items():
        print(f"{i}.{q}")

    chosen = input("\nOption 1.Video 2.Audio: ")

    yt = YouTube(url, on_progress_callback=on_progress)

    print(f"\nTitle: {yt.title}\n")

    try:
            if chosen == '1':
                video = yt.streams.get_highest_resolution()
                video.download()

            elif chosen == '2':
                audio = yt.streams.filter(type="audio").first()
                audio.download()

            print("\n\n Download Completed!")

    except Exception as ex:
            print(f"Error due to {ex}")
            redo = GetYoutubeDownloader(url)


if __name__=="__main__":
    Progressbar()
    url = input("Enter URL Here: ")
    GetYoutubeDownloader("https://www.youtube.com/watch?v=jEf3d2otZqI&t=7095s")
