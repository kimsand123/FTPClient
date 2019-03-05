package com.company;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.stream.Stream;

class TCP_Handling {
  String host;
  int port;
  private Socket clientSocket;

  //Constructor makes an object with a host IP and a portnumber.
  public TCP_Handling(String host, int port) {
    this.host = host;
    this.port = port;
  }

  //Reads a TCP stream
  public String tcpReadStream(){
    StringBuilder endResult=new StringBuilder();
    String line="";
    BufferedReader inFromServer = null;
    String result="";

    try {
      inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      while ((line=inFromServer.readLine()) != null) {
        result=line;
        System.out.println(result);
      }
    } catch (IOException e) {
      System.out.println("Could not create an inputstream");
      e.printStackTrace();
    }

    return result ;
  }

  //Used for sending data over TCP
  public String tcpSendStream(String data) {
    DataOutputStream outToServer = null;
    String result="";

    try {
      outToServer = new DataOutputStream(clientSocket.getOutputStream());
      outToServer.writeBytes(data + '\n');

    } catch (IOException e) {
      System.out.println("Could not establish DataOutputStream");
      e.printStackTrace();
    }

    return result;
  }

  //Used to open a TCP communication connection.
  public String openCommunication() {
    String message="";
    try {
      clientSocket = new Socket(host, port);
      //message=tcpReadStream();
    } catch (IOException e) {
      System.out.print("Communication could not be opened");
      e.printStackTrace();
    }
    return message;
  }

  //Used to close a TCP communication connection.
  public void closeCommunication() {
    try {
      clientSocket.close();
    } catch (IOException e) {
      System.out.print("Problems closing communication");
      e.printStackTrace();
    }
  }

}
