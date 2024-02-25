import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.sound.sampled.*;

public class Server {

    private boolean running = true;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private AudioFormat format;
    private SourceDataLine speaker;
    private TargetDataLine microphone;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for connection...");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

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
        try (InputStream inputStream = clientSocket.getInputStream()) {
            DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
            speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo);
            speaker.open(format);
            speaker.start();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while (running && (bytesRead = inputStream.read(buffer)) != -1) {
                speaker.write(buffer, 0, bytesRead);
            }
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void handleAudioInput() {
        try (OutputStream outputStream = clientSocket.getOutputStream()) {
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
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(5000);
    }
}
