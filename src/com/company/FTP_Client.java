package com.company;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class FTP_Client {
  String host;
  int port;
  int dataport;
  String username;
  String password;

  Socket commandSocket = null;
  Socket dataSocket = null;

  BufferedReader readCommand = null;
  BufferedWriter writeCommand = null;

  BufferedReader readData = null;
  BufferedWriter writeData = null;

  //Constructor makes an object with a host IP and a portnumber and logs on FTP.
  public FTP_Client(String username, String password, String host, int port) {
    this.username = username;
    this.password = password;
    this.host = host;
    this.port = port;


    openCommunication();
    tcpReadStream(commandSocket);
    //logon FTP server
    tcpSendStream("USER " + username, commandSocket);
    System.out.println(tcpReadStream(commandSocket));
    tcpSendStream("PASS " + password, commandSocket);
    System.out.println(tcpReadStream(commandSocket));
    //Getting ready for a filetransfer. calculating the dataport to use for
    //dataSocket.
    requestFileTransfer();
    System.out.println(dataport);
    System.out.println(tcpReadStream(commandSocket));
    //Opening a communication line for Data communication with the dataSocket
    openDataCommunication();

    
    System.out.println(tcpReadStream(commandSocket));
    uploadFile("C:\\ftp\\Testfile3.txt");
    System.out.println(tcpReadStream(commandSocket));

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
          changeDirectory("folder1");
          System.out.println(tcpReadStream(commandSocket));
          requestFileTransfer();
          openDataCommunication();
          getFile("Testfile1");
          String file1 = downloadFile("filepath/somefilename");
          //print 1kb to screen
          printFile(file1);
          closeDataCommunication();
        case 2:
          //download file 2
          changeDirectory("folder2");
          System.out.println(tcpReadStream(commandSocket));
          String file2 = downloadFile("filepath2/somefilename2");
          //print 1kb to screen
          printFile(file2);
        case 3:
          // Upload file
          uploadFile("C:\\ftp\\Testfile3.txt");
        case 4:
          // Close FTP connection
          return;
      }
    } while (true); // End of loop
  }

  private void uploadFile(String filename) {
    tcpSendStream("STOR " + filename, dataSocket);
  }

  private void printFile(String file2) {
  }

  private String downloadFile(String s) {
    String file="";

    return file;
  }

  //Reads a TCP stream
  public String tcpReadStream(Socket socket){
    String line="";
    try {
      readCommand = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      line= readCommand.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return line ;
  }

  //Used for sending data over TCP
  public void tcpSendStream(String data, Socket socket) {
    try {
      writeCommand = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

      writeCommand.write(data+ "\r\n");
      writeCommand.flush();
    } catch (IOException e) {
      System.out.println("Could not writeCommand data");
      e.printStackTrace();
    }
  }

  //Used to open a TCP communication connection.
  public String openCommunication() {
    String message="";
    try {
      commandSocket = new Socket(host, port);
    } catch (IOException e) {
      System.out.print("Communication could not be opened");
      e.printStackTrace();
    }
    return message;
  }

  //Used to open a TCP datacommunication connection.
  private void openDataCommunication() {
    try {
      dataSocket = new Socket(host, dataport);
    } catch (IOException e) {
      System.out.print("DataCommunication could not be opened");
      e.printStackTrace();
    }
  }

  //Used to close a TCP communication connection.
  public void closeCommunication() {
    try {
      commandSocket.close();
    } catch (IOException e) {
      System.out.print("Problems closing communication");
      e.printStackTrace();
    }
  }

  public void closeDataCommunication() {
    try {
      dataSocket.close();
    } catch (IOException e) {
      System.out.print("Problems closing communication");
      e.printStackTrace();
    }
  }

  /*227 Entering Passive Mode (10,20,1,25,19,15)
  The first four numbers are the IP address, and
  the last two are the port number.
  To calculate the port number, use the formula:
  {(first value x [2^8]) + 6th value}.
  In the example it will be (19 x 256) + 15 = 4879.*/
  public void calculatePortNumber(){
    String message;
    String[] numbers;
    int amountOfNumbers = 0;

    tcpSendStream("PASV", commandSocket);
    message = tcpReadStream(commandSocket);
    message = message.replace("(","");
    message = message.replace(")","");
    message = message.replace(".","");
    numbers = message.split(",");
    amountOfNumbers = numbers.length;
    dataport = ((256*Integer.parseInt(numbers[amountOfNumbers-2]))) + Integer.parseInt(numbers[amountOfNumbers-1]);
  }

  public void changeDirectory (String dir){
    tcpSendStream("CWD " + dir, commandSocket);
  }

  public void getFile (String file){
    tcpSendStream("RETR " + file, commandSocket);
  }

  public void closeFtp(){
    tcpSendStream("QUIT",commandSocket);
  }

  public void requestFileTransfer (){
    tcpSendStream("TYPE A", commandSocket);
    tcpReadStream(commandSocket);
    calculatePortNumber();
  }

}
