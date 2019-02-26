package com.company;


import java.io.*;
import java.net.*;

class TCP_Handling {

  private Socket clientSocket;

  public TCP_Handling () {
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
      String host = "ftp.soeborg.it";
      String username = "soeborg.it";
      String password = "1OJ*RU6i$";
      int port = 21;

      try {
        clientSocket = new Socket(host,port);
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
