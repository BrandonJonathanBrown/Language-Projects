import java.io.*;
import java.net.Socket;
import javax.sound.sampled.*;

//Author Brandon Jonathan Brown 

public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000;
    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = 1024; // bytes

    private Socket socket;
    private AudioFormat format;
    private SourceDataLine speaker;
    private TargetDataLine microphone;
    private volatile boolean running = true;

    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            System.out.println("Connected to server.");
            setupAudio();
            startCommunication();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            return;
        }
    }

    private void setupAudio() {
        format = new AudioFormat(SAMPLE_RATE, 16, 2, true, true);
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

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1 && running) {
                speaker.write(buffer, 0, bytesRead);
            }
        } catch (IOException | LineUnavailableException e) {
            System.err.println("Audio output error: " + e.getMessage());
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

            byte[] buffer = new byte[BUFFER_SIZE];
            while (running) {
                int count = microphone.read(buffer, 0, buffer.length);
                if (count > 0) {
                    outputStream.write(buffer, 0, count);
                }
            }
        } catch (IOException | LineUnavailableException e) {
            System.err.println("Audio input error: " + e.getMessage());
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
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
