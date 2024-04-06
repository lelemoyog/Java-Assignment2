import java.io.*;
import java.net.*;

public class ElectionVoteClient {
    // Server configuration
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9999;
    private static final String MULTICAST_GROUP = "224.1.1.1";
    private static final int MULTICAST_PORT = 5007;

    public static void main(String[] args) {
        // Get vote from the user
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your vote (A or B): ");
            String vote = reader.readLine().toUpperCase();

            // Send the vote to the server
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);
            DatagramPacket packet = new DatagramPacket(vote.getBytes(), vote.length(), serverAddress, SERVER_PORT);
            clientSocket.send(packet);

            // Sleep briefly to ensure multicast message is received
            Thread.sleep(100);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
