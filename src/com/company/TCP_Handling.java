package com.company;


import java.io.*;
import java.net.*;

class TCP_Handling {
  String host;
  int port;
  private Socket clientSocket;

  public TCP_Handling(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public String tcpReadStream(){
    StringBuilder endResult=new StringBuilder();
    String line="";
    BufferedReader inFromServer = null;

    try {
      inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      while ((line = inFromServer.readLine())!= null) {
        endResult.append(line);
      }
    } catch (IOException e) {
      System.out.println("Could not create an inputstream");
      e.printStackTrace();
    }

    return endResult.toString();
  }


  public String tcpSendStream(String data) {
    DataOutputStream outToServer = null;
    String result="";

    try {
      outToServer = new DataOutputStream(clientSocket.getOutputStream());
      outToServer.writeBytes(data + '\n');
      result=tcpReadStream();
    } catch (IOException e) {
      System.out.println("Could not establish DataOutputStream");
      e.printStackTrace();
    }

    return result;
  }


  public String openCommunication() {
    String message="";
    try {
      clientSocket = new Socket(host, port);
      message=tcpReadStream();
    } catch (IOException e) {
      System.out.print("Communication could not be opened");
      e.printStackTrace();
    }
    return message;
  }


  public void closeCommunication() {
    try {
      clientSocket.close();
    } catch (IOException e) {
      System.out.print("Problems closing communication");
      e.printStackTrace();
    }
  }

}
