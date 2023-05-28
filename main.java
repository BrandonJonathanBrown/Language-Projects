import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class main {
	
	private static String host = " ";
	
	public static void NetworkDevices() throws IOException {
	    
	for(int i=1;i<=255;i++) {
	    	host = "10.0.0" + "." + i;
	                    InetAddress address = InetAddress.getByName(host);
	                    if (address.isReachable(1000)) {
	                        System.out.println(host + " is on the network");
	                    } else {
	                        System.err.println("Not Reachable: "+ host);
	                    }
	    }
	}

	
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		NetworkDevices();
	}
	
	
	

}
