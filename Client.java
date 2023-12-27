import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author Brandon Jonathan Brown
 * @NOTE Simple TCP Client For Chat Application
 */

public class Client implements Runnable {
    public static void main(String[] args) throws IOException {

        Thread thread = new Thread(new Client());
        thread.start();
    }

    @Override
    public void run() {

        try {
            Socket socket = new Socket("127.0.0.1", 777);
            System.out.println("Client Online...!");
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            while(true) {

                try {
                    System.out.printf("%s","Please enter a message: ");
                    String message = dataInputStream.readUTF();
                    System.out.println("Server: " + message);
                    Scanner input = new Scanner(System.in);
                    String line = input.nextLine();
                    if(line.equals("$!"))
                        break;
                    dataOutputStream.writeUTF(line);
                    dataOutputStream.flush();
                }
                catch(Exception ex)
                {
                    System.err.println("SOCKET ERROR: " + ex.getMessage());
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}

