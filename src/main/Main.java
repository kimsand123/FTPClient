package main;

public class Main {

  public static void main(String[] args) throws Exception {
    /* Variables - Connection information
    //String username = "ftp";
    String password = "test";
    String host = "127.0.0.1";
    int port = 21;*/

    String username = "soeborg.it";
    String password = "1OJ*RU6i$";
    String host = "ftp.soeborg.it";
    int port = 21;

    FTP_Client connection = new FTP_Client(username, password, host, port);
  }
}
