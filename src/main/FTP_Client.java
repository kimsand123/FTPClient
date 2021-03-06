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
  BufferedInputStream readData = null;
  BufferedOutputStream writeData = null;
  File file;
  String path="";
  String OS="";

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
    System.out.println("Connection established");
    menu();
  }

  public void menu() throws Exception {
    //check which OS and change the path accordingly
    String path;
    String filename;

    System.out.println(OS);
    String menuAnswer;
    Scanner scan = new Scanner(System.in);
    do {
      System.out.println();
      System.out.println();
      System.out.println("FTP Assignment Menu:");
      System.out.println("Press 1 to download Testfile1.txt from folder1 and print at most 1kb on screen");
      System.out.println("Press 2 to download Testfile2.txt from folder2 and print at most 1kb on screen");
      System.out.println("Press 3 to upload Testfile3.txt to ftp root folder");
      System.out.println("Press 4 to exit");
      menuAnswer = scan.nextLine();
      switch (Integer.parseInt(menuAnswer)) {
        case 1:
          //download file 1
          //Change to correct folder
          changeDirectory("../folder1");
          //Getting ready for a filetransfer. calculating the dataport to use for dataSocket.
          calculatePortNumber(requestPassiveFTP());

          //Sending the FTP command and file for upload
          System.out.println("Enter path for file : ");
          path=scan.nextLine();
          System.out.println("Enter filename : ");
          filename=scan.nextLine();

          path.replace("\\","\\\\");
          file = new File(path, filename);
          setFileForDownload(file);

          //Opening a communication line for Data communication with the dataSocket
          openDataCommunication();

          //Doing the transfer
          downloadFile(file);

          //print 1kb to screen
          // printFile(file);
          getItemSize(file);

          break;
        case 2:
          //download file 2
          changeDirectory("../folder2");
          //Getting ready for a filetransfer. calculating the dataport to use for dataSocket.
          calculatePortNumber(requestPassiveFTP());

          //Sending the FTP command and file for upload
          System.out.println("Enter path for file : ");
          path=scan.nextLine();
          System.out.println("Enter filename : ");
          filename=scan.nextLine();

          path.replace("\\","\\\\");
          file = new File(path, filename);
          setFileForDownload(file);

          //Opening a communication line for Data communication with the dataSocket
          openDataCommunication();

          //Doing the transfer
          downloadFile(file);

          // print 1kb to screen
          // printFile(file);

          getItemSize(file);

          break;
        case 3:

          changeDirectory("..");
          //Getting ready for a filetransfer. calculating the dataport to use for dataSocket.
          calculatePortNumber(requestPassiveFTP());

          //Sending the FTP command and file for upload
          System.out.println("Enter path for file : ");
          path=scan.nextLine();
          System.out.println("Enter filename : ");
          filename=scan.nextLine();

          path.replace("\\","\\\\");
          file = new File(path, filename);
          setFileForUpload(file);

          //Opening a communication line for Data communication with the dataSocket
          openDataCommunication();

          //Doing the transfer
          uploadfile(file);

          // Print file Size
          getItemSize(file);

          break;
        case 4:
          closeDataCommunication();
          closeCommunication();
          return;
      }
    } while (!menuAnswer.contains("4")); // End of loop
    scan.close();
  }

  private void setFileForUpload(File file) {
    tcpSendStream("STOR " + file.getName(), commandSocket);
  }

  private void uploadfile(File file)throws Exception {
    InputStream fileInput = new FileInputStream(file);
    BufferedInputStream fileData = new BufferedInputStream(fileInput);
    writeData = new BufferedOutputStream(dataSocket.getOutputStream());

    int dataRead = 0;
    StringBuilder printToScreen=null;
    while (true) {
      byte[] buffer = new byte[2048];
      if ((dataRead = fileData.read(buffer)) == -1) break;
      writeData.write(buffer, 0, dataRead);

    }

    writeData.flush();
    writeData.close();
    System.out.println(tcpReadStream(commandSocket));
  }

  private void setFileForDownload(File file) {
    tcpSendStream("RETR " + file.getName(), commandSocket);
  }

  private void downloadFile(File file) throws Exception{
    FileOutputStream fileOutput = new FileOutputStream(file);
    BufferedOutputStream fileData = new BufferedOutputStream(fileOutput);
    readData = new BufferedInputStream(dataSocket.getInputStream());


    byte[] tmpbuffer = new byte[1024];
    int dataRead = 0;
    boolean first=true;
    int counter =0;

    while (true) {
      //clears buffer before filling it.
      byte[] buffer = new byte[2048];
      if (readData.read(buffer) == -1) break;

      if(first) {
        for (counter = 0; counter < 1024; counter++) {
          if (buffer[counter] == 0) {
            System.out.println();
            System.out.println("Number of bytes written to screen: " + counter);
            return;
          }
          System.out.print((char) buffer[counter]);

        }
        first=false;
      }
      fileData.write(buffer);
    }

    fileData.flush();
    fileData.close();
    System.out.println();
    System.out.println("Number of bytes written to screen: " + counter);
    System.out.println(tcpReadStream(commandSocket));
  }

  private void printFile(File file) {
      // ???? Hvad laver denne funktion?
  }

  //Used for recieving data over TCP
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
    System.out.println("Dataport: " + dataport);
  }

  public void changeDirectory (String dir){
    tcpSendStream("CWD " + dir, commandSocket);
    System.out.println(tcpReadStream(commandSocket));
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

  public void getItemSize(File file) {
    if(file.exists()){

      double bytes = file.length();
      double kilobytes = (bytes / 1024);
      // System.out.println("bytes : " + bytes);
      System.out.println("Filesize : " + kilobytes + " Kb");

    }else{
      System.out.println("File does not exists!");
    }
  }

}
