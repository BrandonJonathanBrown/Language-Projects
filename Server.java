import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("SSH-Server Starting...");
        try (ServerSocket serverSocket = new ServerSocket(7999)) {
			while (true) {
			    Socket clientSocket = serverSocket.accept();
			    System.out.println("Client connected...");
			    Thread insideThread = new Thread(new Inside(clientSocket));
			    Thread outsideThread = new Thread(new Outside(clientSocket));
			    insideThread.start();
			    outsideThread.start();
			}
		}
    }

    public static class Inside implements Runnable {
        private final Socket socket;

        public Inside(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String command;
                while ((command = br.readLine()) != null) {
                    if (command.equalsIgnoreCase("exit")) {
                        System.out.println("Client requested to exit.");
                        pw.println("Goodbye!");
                        break;
                    }
                    
                    	System.out.println("Executing Command: " + command);
                    	String sudoCommand = command;
                    	Process process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", sudoCommand});

                    	try (BufferedReader processReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                        String line;
                        pw.println("Output:");
                        	while ((line = processReader.readLine()) != null) {
                        		pw.println(line);
                        	}

                        	pw.println("Errors:");
                        	while ((line = errorReader.readLine()) != null) {
                        		pw.println("ERROR: " + line);
                        	}
                    	}
                    }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Outside implements Runnable {
        private final Socket socket;

        public Outside(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)
            ) {
                while (true) {
                    System.out.print("Enter Command to Send: ");
                    String command = scanner.nextLine();
                    pw.println(command);

                    if (command.equalsIgnoreCase("exit")) {
                        System.out.println("Session terminated by the client.");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
