import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	/**
 		Author Brandon Jonathan Brown
	*/

    private static final String BASE_IP = "192.168.0.";
    private static final int TIMEOUT = 100; // milliseconds
    
    public static void scanNetworkDevices() {
        ExecutorService executor = Executors.newFixedThreadPool(20); // Use 20 threads for scanning

        for (int i = 1; i <= 255; i++) {
            int finalI = i; // Effectively final for use in lambda
            executor.submit(() -> {
                String host = BASE_IP + finalI;
                try {
                    InetAddress address = InetAddress.getByName(host);
                    if (address.isReachable(TIMEOUT)) {
                        System.out.println(host + " is on the network");
                    } else {
                        System.err.println("Not Reachable: " + host);
                    }
                } catch (IOException e) {
                    System.err.println("Error checking " + host);
                }
            });
        }
        
        executor.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        try {
            scanNetworkDevices();
        } catch (Exception e) {
            System.err.println("Network scanning failed: " + e.getMessage());
        }
    }
}

