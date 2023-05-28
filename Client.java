package ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private static Socket socket = null;
	private static InputStream stin= null;
	private static OutputStream stout = null;
	private static BufferedReader br = null;
	private static PrintWriter pw = null;
	
	public static void main(String[]args) throws UnknownHostException, IOException
	{
		
		socket = new Socket("localhost",194);
		stin= socket.getInputStream();
		stout = socket.getOutputStream();
		br = new BufferedReader(new InputStreamReader(stin));
		pw = new PrintWriter(new OutputStreamWriter(stout));
		
		pw.println("Sucessfully Connected!");
		pw.flush();
		
	}
	

}
