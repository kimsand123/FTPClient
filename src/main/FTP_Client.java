package main;

import java.io.*;
import java.net.Socket;

public class FTP_Client {
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
    downloadDatafile();
  }

  public void downloadDatafile() throws Exception {
    //download datafile
    //Change to correct folder
    changeDirectory("/home/user/FTP/data");

    //Getting ready for a filetransfer. calculating the dataport to use for dataSocket.
    calculatePortNumber(requestPassiveFTP());

    //Destination folder.
    file = new File("/root/home/ftp/", "sensordata.csv");
    setFileForDownload(file);

    //Opening a communication line for Data communication with the dataSocket
    openDataCommunication();

    //Doing the transfer
    downloadFile(file);

    //print 1kb to screen
    // printFile(file);

    closeDataCommunication();
    closeCommunication();
    return;
  }



  private void setFileForUpload(File file) {
    tcpSendStream("STOR " + file.getName(), commandSocket);
  }

  private void setFileForDownload(File file) {
    tcpSendStream("RETR " + file.getName(), commandSocket);
  }

  private void downloadFile(File file) throws Exception{
    FileOutputStream fileOutput = new FileOutputStream(file);
    BufferedOutputStream fileData = new BufferedOutputStream(fileOutput);
    readData = new BufferedInputStream(dataSocket.getInputStream());
    while (true) {
      //clears buffer before filling it.
      byte[] buffer = new byte[2048];
      if (readData.read(buffer) == -1) break;
      fileData.write(buffer);
    }

    fileData.flush();
    fileData.close();
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

}
