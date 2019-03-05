package com.company;


import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.stream.Stream;

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
    String character="";
    int length=0;
    BufferedReader inFromServer = null;
    Stream<String> message="";
    String[] messagearray;

    try {
      inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      //inFromServer.mark(1);
      message = inFromServer.lines();
      messagearray = (String[])message.toArray();
      int n=0;
      while ( true) {

        if (messagearray[n].equals("\\")) {
          if ((messagearray[n+1].equals("u"))) {
            break;
          }
          endResult.append(messagearray[n]);
          n++;
          break;
        }
      }
      inFromServer.close();
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
