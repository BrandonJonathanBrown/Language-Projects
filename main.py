from pytube import YouTube
from tqdm import tqdm
import requests

class YouTubeDownloader:

    def __init__(self, url):
        print("[*] YouTube Downloader Starting ...")
        self.url = url
        self.video_data = None
        self.get_video_info()
        self.download()

    def get_video_info(self):
        try:
            youtube = YouTube(self.url)
            self.video_data = {
                "title": youtube.title,
                "streams": self.get_streams_info(youtube.streams.filter(progressive=True, file_extension='mp4'))
            }
        except Exception as ex:
            print(f"Failed to recognize URL: {ex}")

    def get_streams_info(self, streams):
        stream_info = []
        for stream in streams:
            stream_info.append({
                "resolution": stream.resolution,
                "url": stream.url,
                "filesize": stream.filesize
            })
        return stream_info

    def download(self):
        if not self.video_data:
            self.get_video_info()
        if self.video_data:
            try:
                highest_resolution_stream = max(self.video_data['streams'], key=lambda x: x['resolution'])

                response = requests.get(highest_resolution_stream['url'], stream=True)
                total_size = highest_resolution_stream['filesize']
                pbar = tqdm(total=total_size, unit='B', unit_scale=True)
                with open(f"{self.video_data['title']}_highest_resolution.mp4", "wb") as f:
                    for chunk in response.iter_content(chunk_size=1024):
                        if chunk:
                            f.write(chunk)
                            pbar.update(len(chunk))
                pbar.close()
                print(f"Download successful: {self.video_data['title']} (Highest resolution)")
            except Exception as ex:
                print(f"Failed to download file: {ex}")

if __name__ == "__main__":

    urls = []
    while True:
        package = input("Please enter a URL or 'q' to quit: \n")
        if package.lower() == 'q':
            break
        urls.append(package)

    for url in urls:
        Obj = YouTubeDownloader(url=url)
