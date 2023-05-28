import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class Client {
   

    private static boolean running = true;
    private static Socket socket = null;
    private static AudioFormat format = null;
    private static DataLine.Info speakerinfo = null;
    private static DataLine.Info microphoneinfo = null;
    private static SourceDataLine speaker = null;
    private static TargetDataLine microphone = null;
    private static InputStream inputstream = null;
    private static OutputStream outputstream = null;
    
    
    
    public static Socket socket() throws UnknownHostException, IOException
    {
    	return socket = new Socket("127.0.0.1",5000);
    }
    
    public static InputStream inputstream() throws IOException
    {
    	return inputstream = socket.getInputStream();
    }
    
    public static OutputStream outputstream() throws IOException
    {
    	return outputstream = socket.getOutputStream();
    }
    
    public static AudioFormat Audio()
    {
    	return format = new AudioFormat(44100,16,2,true,true);
    }
    
    
    
    public static void Output() throws IOException, LineUnavailableException
    {
    	speakerinfo = new DataLine.Info(SourceDataLine.class,Audio());
        speaker = (SourceDataLine)AudioSystem.getLine(speakerinfo);
        
        if(!AudioSystem.isLineSupported(speakerinfo)){
            System.err.println("Hardware not supported!");
            System.exit(1);
        }
       
    	speaker.open(Audio());
    	speaker.start();
    	
        int index;
        byte[] buffer = new byte[1024 * 100];
    	
        System.out.println("Starting Speaker...");
        while((index = inputstream().read(buffer,0,buffer.length)) != -1){
        	
            if(index > 0)
            {
                System.out.print(index+"\n");
                speaker.write(buffer, 0, index);
                buffer = new byte[1024 * 100];
            }
            
        }
    }
    
    
    public static void Input() throws IOException, LineUnavailableException
    {
    	
    	microphoneinfo = new DataLine.Info(TargetDataLine.class,Audio());
    	
        if(!AudioSystem.isLineSupported(microphoneinfo)){
            System.err.println("Hardware not supported!");
            System.exit(1);
        }
        
        microphone = (TargetDataLine)AudioSystem.getLine(microphoneinfo);
        microphone.open(format);
        microphone.start();
        System.out.println("Starting Recording...");
        
        byte buffer[] = new byte[(int)Audio().getSampleRate()* Audio().getFrameSize()];
        
        while(running){
            int count = microphone.read(buffer, 0, buffer.length);
            if(count > 0){
                outputstream().write(buffer, 0, count);
            }
        }
        
    }
    
    public Client()
    {
    	 try {  
         	
         	socket();
         	
         Thread reciever = new Thread(()->{                
             try {
                
             	inputstream();
             	Output();
             	
                 } catch (IOException ex) {
                     System.err.println(ex.getMessage());
                 Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
             } catch (LineUnavailableException ex) {
 					ex.printStackTrace();
 					Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
 				}
             
         });
         reciever.start();
         
         Thread sender = new Thread(() -> {
         try {
            
            outputstream();
            Input();
             
             } catch (IOException ex) {
                 System.err.println(ex.getMessage());
             Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
         } catch (LineUnavailableException ex) {
             System.err.println(ex.getMessage());
             Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
         }
         
     });
         
         sender.start();
         } catch (IOException ex) {
             System.err.println(ex.getMessage());
         }
         
    	
    }
    
    public static void main(String[] args) throws LineUnavailableException {
    		new Client();
    }
}