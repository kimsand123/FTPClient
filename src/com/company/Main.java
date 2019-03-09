package com.company;

import java.io.File;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    // Variables - Connection information
    String username = "ftp";
    String password = "test";
    String host = "127.0.0.1";
    int port = 21;

    /*String username = "soeborg.it";
    String password = "1OJ*RU6i$";
    String host = "ftp.soeborg.it";
    int port = 21;*/

    try {
      FTP_Client connection = new FTP_Client(username, password, host, port);
    } catch (Exception e) {
      e.printStackTrace();
    }

    /*TEST connection = new TEST();
    connection.connect(username, password, host, port);
    File file = new File("c:","Testfile3.txt");
    connection.stor(file);*/
  }
}
