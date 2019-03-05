package com.company;

import java.io.IOException;
import java.util.Scanner;

class FTP_controlprogram {
  String host;
  String username;
  String password;
  int port;

  TCP_Handling tcpHandling;

  public FTP_controlprogram(String username, String password, String host, int port) {

    this.host = host;
    this.username = username;
    this.password = password;
    this.port = port;
    tcpHandling = new TCP_Handling(host, port);
  }

  public String connectToFTPServer() throws IOException {
    String returnmessage="";

    tcpHandling.openCommunication();

    returnmessage = sendFtpCommand("USER " + username);
    System.out.println("tghisone"+returnmessage);
    /*if (!(returnmessage.startsWith("331 "))) {
      throw new IOException("Unknown response after sending username" + returnmessage);
    }*/
    //System.out.println(returnmessage);
    sendFtpCommand("PASS " + password);
    /*returnmessage = recieveTCPstream();
    if(!(returnmessage.startsWith("230"))){
      throw new IOException("Wrong password");
    }*/

    //sendFtpCommand("PASS " + password);
    /*returnmessage = recieveTCPstream();
    if(!returnmessage.startsWith("230 ")){
      throw new IOException("You where not able to login with supplied password");
    }*/

    return returnmessage;
  }

  public void menu() {
    int menuAnswer;
    Scanner scan = new Scanner(System.in);
    do {
      System.out.println("Connection established");
      System.out.println("FTP Assignment Menu:");
      System.out.println("Press 1 to download file 1 from ???? folder and print at most 1kb on screen");
      System.out.println("Press 2 to download file 2 from ???? folder and print at most 1kb on screen");
      System.out.println("Press 3 to upload test file to folder");
      System.out.println("Press 4 to close FTP connection and exit");
      menuAnswer = scan.nextInt();
      switch (menuAnswer) {
        case 1:
          //download file 1
          String file1 = downloadFile("filepath/somefilename");
          //print 1kb to screen
          printFile(file1);
        case 2:
          //download file 2
          String file2 = downloadFile("filepath2/somefilename2");
          //print 1kb to screen
          printFile(file2);
        case 3:
          // Upload file
          uploadFile("filepath/somefilename");
        case 4:
          // Close FTP connection
          tcpHandling.closeCommunication();
          return;
      }
    } while (true); // End of loop
  }

  public String downloadFile(String filenameAndPath){
    String fileData="";

    return fileData;
  }

  public void printFile (String file){

  }

  public void uploadFile(String filenameAndPath){

  }

  public String sendFtpCommand(String command){
    return tcpHandling.tcpSendStream(command + '\n');
  }

  public String closeFtp(){
    String result="";
    sendFtpCommand("close");
    return result;
  }

  public String closeTcp(){
    String result="";
    tcpHandling.closeCommunication();
    return result;
  }

  public String requestFileTransfer (){
    String message="";

    return message;

  }

  public String changeDirectory (String dir){
    String message="";
    sendFtpCommand("CWD " + dir);
    return message;
  }
/*
public String getFile (String file){
    String message="";
    String ftpCommand="";

    sendFtpCommand("GET " +

    return message;
}*/

  public String recieveTCPstream(){
    return tcpHandling.tcpReadStream();
  }

}
