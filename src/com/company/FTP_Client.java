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
  BufferedOutputStream writeData = null;

  File file;

  //Constructor makes an object with a host IP and a portnumber and logs on FTP.
  public FTP_Client(String username, String password, String host, int port) throws Exception{
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

  }

  public void menu() throws Exception {
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
          //Getting ready for a filetransfer. calculating the dataport to use for dataSocket.
          calculatePortNumber(requestPassiveFTP());
          System.out.println(dataport);

          //Sending the FTP command and file for upload
          file = new File("c:\\","Testfile3.txt");
          setFileForUpload(file);

          //Opening a communication line for Data communication with the dataSocket
          openDataCommunication();

          //Doing the transfer
          uploadfile(file);
          System.out.println(tcpReadStream(commandSocket));
        case 4:
          closeDataCommunication();
          closeCommunication();
          return;
      }
    } while (true); // End of loop
  }

  private void setFileForUpload(File file) {
    tcpSendStream("STOR " + file.getName(), commandSocket);
  }


  private void uploadfile(File file)throws Exception {
    InputStream fileInput = new FileInputStream(file);
    BufferedInputStream fileData = new BufferedInputStream(fileInput);
    writeData = new BufferedOutputStream(dataSocket.getOutputStream());

    byte[] buffer = new byte[4096];
    int dataRead = 0;
    while (true) {

      if (!((dataRead = fileData.read(buffer)) != -1)) break;
      writeData.write(buffer, 0, dataRead);
      writeData.flush();
      writeData.close();

    }

  }


  private String downloadFile(String s) {
    String file="";

    return file;
  }

  private void printFile(String file2) {
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

  /*227 Entering Passive Mode (10,20,1,25,19,15)
  The first four numbers are the IP address, and
  the last two are the port number.
  To calculate the port number, use the formula:
  {(first value x [2^8]) + 6th value}.
  In the example it will be (19 x 256) + 15 = 4879.*/
  public void calculatePortNumber(String message){
    String[] numbers;
    int amountOfNumbers = 0;
    System.out.println(message);
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

  public String requestPassiveFTP(){
    tcpSendStream("PASV", commandSocket);
    return (tcpReadStream(commandSocket));
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
      System.out.println(tcpReadStream(commandSocket));
    } catch (IOException e) {
      System.out.print("DataCommunication could not be opened");
      e.printStackTrace();
    }
  }

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

}
