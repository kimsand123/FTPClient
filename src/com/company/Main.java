package com.company;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    // Variables - Connection information
    String username = "ftp";
    String password = "dtutest123!";
    String host = "127.0.0.1";
    int port = 21;

    /*String username = "soeborg.it";
    String password = "1OJ*RU6i$";
    String host = "ftp.soeborg.it";
    int port = 21;*/
    FTP_controlprogram ftpProgram = new FTP_controlprogram(username, password, host, port);

    // Call function. Connect to FTP Server
    ftpProgram.connectToFTPServer();
    // Call function. Get TCP Stream
    ftpProgram.recieveTCPstream();
    // Call function. Close ftp connection
    ftpProgram.closeFtp();
    // Call function, Close TCP connection
    ftpProgram.closeTcp();

    


  }
}
