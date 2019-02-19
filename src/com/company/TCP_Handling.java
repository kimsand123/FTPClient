package com.company;


import java.io.*;
import java.net.*;

class TCPClient {

  private Socket clientSocket;

  public static void main(String argv[]) throws Exception {
    // Variables
    String sentence;
    String modifiedSentence;
    // Code
    // User input that will be send to FTP server
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    // Create socket (hostname, TCP port)

    // String data which will be sent to FTP server

    // Wait for user input
    sentence = inFromUser.readLine();
    // Send data to FTP server

    }
    public String tcpReadStream(){
    String result="";
      BufferedReader inFromServer = null;
      try {
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      } catch (IOException e) {
        System.out.println("Could not create an inputstream");
        e.printStackTrace();
      }

      try {
        result = inFromServer.readLine();
      } catch (IOException e) {
        System.out.println("Could not read stream from server");
        e.printStackTrace();
      }

      return result;
    }

    public void tcpSendStream (String data) {
      DataOutputStream outToServer = null;

      try {
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
      } catch (IOException e) {
        System.out.println("Could not establish DataOutputStream");
        e.printStackTrace();
      }

      try {
        outToServer.writeBytes(data + '\n');
      } catch (IOException e) {
        System.out.print("Problems writing bytestream");
        e.printStackTrace();
      }
    }

    public void openCommunication(){
      try {
        clientSocket = new Socket("hostname",6789);
      } catch (IOException e) {
        System.out.print("Communication could not be opened");
        e.printStackTrace();
      }
    }

    public void closeCommunication(){
      try {
        clientSocket.close();
      } catch (IOException e) {
        System.out.print("Problems closing communication");
        e.printStackTrace();
      }
    }

    private ByteArrayOutputStream convertOutToBytesStream(String data){
    ByteArrayOutputStream result=null;


    return result;
    }

}
