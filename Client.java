import java.io.*;
import java.net.Socket;
import javax.sound.sampled.*;

public class Client {

    private Socket socket;
    private AudioFormat format;
    private SourceDataLine speaker;
    private TargetDataLine microphone;
    private boolean running = true;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to server.");
            setupAudio();
            startCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupAudio() {
        format = new AudioFormat(44100, 16, 2, true, true);
    }

    private void startCommunication() {
        new Thread(this::handleAudioOutput).start();
        new Thread(this::handleAudioInput).start();
    }

    private void handleAudioOutput() {
        try (InputStream inputStream = socket.getInputStream()) {
            DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
            speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo);
            speaker.open(format);
            speaker.start();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1 && running) {
                speaker.write(buffer, 0, bytesRead);
            }
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void handleAudioInput() {
        try (OutputStream outputStream = socket.getOutputStream()) {
            DataLine.Info microphoneInfo = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(microphoneInfo);
            microphone.open(format);
            microphone.start();

            byte[] buffer = new byte[1024];
            while (running) {
                int count = microphone.read(buffer, 0, buffer.length);
                if (count > 0) {
                    outputStream.write(buffer, 0, count);
                }
            }
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void cleanup() {
        if (microphone != null) microphone.close();
        if (speaker != null) speaker.close();
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 5000);
    }
}

