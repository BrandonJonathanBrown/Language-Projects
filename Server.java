import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author Brandon Jonathan Brown
 * @NOTE Simple TCP Server For Chat Application
 */

public class Server implements Runnable {


    public static void main(String[] args) throws IOException {

        Thread thread = new Thread(new Server());
        thread.start();
    }

    @Override
    public void run() {


        try {

            ServerSocket ss = new ServerSocket(777);
            System.out.println("Server listening ...");
            Socket socket = ss.accept();
            System.out.println("Incoming connection ... " + socket + "!");
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            while(true) {

                try {
                    System.out.printf("%s","Please enter a message: ");
                    Scanner input = new Scanner(System.in);
                    String line = input.nextLine();
                    if(line.equals("$!"))
                        break;
                    dataOutputStream.writeUTF(line);
                    dataOutputStream.flush();
                    String message = dataInputStream.readUTF();
                    System.out.println("Client: " + message);
                }
                catch(Exception ex)
                {
                    System.err.println("SOCKET ERROR " + ex.getMessage());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}