import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Author Brandon Jonathan Brown
 */

public class Client extends Thread {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("127.0.0.1", 7999);
        System.out.printf("%s\n", "Client Starting ...");
        
        Thread insideThread = new Thread(new Inside(socket));
        insideThread.start();
        
        Thread outsideThread = new Thread(new Outside(socket)); 
        outsideThread.start();
    }

    public static class Inside implements Runnable {
        private Socket socket;
        private BufferedReader br;

        public Inside(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.printf("%s\n", line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) br.close();
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Outside implements Runnable {
        private Socket socket;
        private PrintWriter pw;

        public Outside(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                this.pw = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in);

                while (true) {
                    System.out.print("SSH Chat: ");
                    String command = scanner.nextLine();
                    pw.println(command);

                    if (command.equalsIgnoreCase("exit")) {
                        break;
                    }
                }
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pw != null) pw.close();
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
