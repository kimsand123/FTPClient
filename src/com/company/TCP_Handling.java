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

  public String tcpReadStream() {
    String fullMessage;
    fullMessage = readFullMessage();
    System.out.println(fullMessage);
    return readFullMessage();
  }

  public void tcpSendStream(String data) {
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


  public void openCommunication() {
    try {
      clientSocket = new Socket(host, port);
      System.out.println(readFullMessage());
    } catch (IOException e) {
      System.out.print("Communication could not be opened");
      e.printStackTrace();
    }
  }


  public void closeCommunication() {
    try {
      clientSocket.close();
    } catch (IOException e) {
      System.out.print("Problems closing communication");
      e.printStackTrace();
    }
  }

  public String readFullMessage() {
    String fullMessage = "";
    BufferedReader inFromServer = null;
    String line = "";

    try {
      inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    } catch (IOException e) {
      System.out.println("Could not create an inputstream");
      e.printStackTrace();
    }
    while (true) {
      try {
        if ((line = inFromServer.readLine()) == null) break;
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println(line);
      fullMessage = fullMessage + line;
    }
    return fullMessage;
  }
}
