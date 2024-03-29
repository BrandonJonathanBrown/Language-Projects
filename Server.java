import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.sound.sampled.*;

// Author Brandon Jonathan Brown

public class Server {

    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 1024;
    private static final AudioFormat FORMAT = new AudioFormat(44100, 16, 2, true, true);

    private volatile boolean running = true;
    private ServerSocket serverSocket;

    public Server(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
            System.out.println("Server started. Waiting for connection...");

            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Client connected.");
                startCommunication(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    private void startCommunication(Socket clientSocket) {
        new Thread(() -> handleAudioOutput(clientSocket)).start();
        new Thread(() -> handleAudioInput(clientSocket)).start();
    }

    private void handleAudioOutput(Socket clientSocket) {
        try (InputStream inputStream = clientSocket.getInputStream()) {
            DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, FORMAT);
            SourceDataLine speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo);
            speaker.open(FORMAT);
            speaker.start();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while (running && (bytesRead = inputStream.read(buffer)) != -1) {
                speaker.write(buffer, 0, bytesRead);
            }
        } catch (IOException | LineUnavailableException e) {
            System.err.println("Audio output error: " + e.getMessage());
        }
    }

    private void handleAudioInput(Socket clientSocket) {
        try (OutputStream outputStream = clientSocket.getOutputStream()) {
            DataLine.Info microphoneInfo = new DataLine.Info(TargetDataLine.class, FORMAT);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(microphoneInfo);
            microphone.open(FORMAT);
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
        }
    }

    public static void main(String[] args) {
        new Server(PORT);
    }
}
