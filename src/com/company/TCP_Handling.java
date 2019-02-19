import java.io.*;
import java.net.*;

class TCPClient {
  public static void main(String argv[]) throws Exception {
    // Variables
    String sentence;
    String modifiedSentence;
    // Code
    // User input that will be send to FTP server
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    // Create socket (hostname, TCP port)
    Socket clientSocket = new Socket("hostname",6789);
    // String data which will be sent to FTP server
    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    // Wait for user input
    sentence = inFromUser.readLine();
    // Send data to FTP server
    outToServer.writeBytes(sentence + '\n');

    modifiedSentence = inFromServer.readLine();

    System.out.println("FROM SERVER: " + modifiedSentence);

    clientSocket.close();

    }
}
