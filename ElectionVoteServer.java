import java.net.*;

public class ElectionVoteServer {
    // Server configuration
    private static final String MULTICAST_GROUP = "224.1.1.1";
    private static final int MULTICAST_PORT = 5007;

    public static void main(String[] args) {
        // MulticastSocket to handle multicast messages
        try (MulticastSocket multicastSocket = new MulticastSocket(MULTICAST_PORT)) {
            multicastSocket.joinGroup(InetAddress.getByName(MULTICAST_GROUP));
            
            // Map to store votes
            int votesForA = 0;
            int votesForB = 0;

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);
                String vote = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received multicast message: " + vote);

                // Update vote count
                if (vote.equals("A")) {
                    votesForA++;
                } else if (vote.equals("B")) {
                    votesForB++;
                }

                // Check if all votes received
                if (votesForA + votesForB == 5) {
                    // Determine the winner
                    String winner = (votesForA > votesForB) ? "A" : "B";
                    System.out.println("Winner: Candidate " + winner);
                    break; // Exit the loop
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
