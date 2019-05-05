import main.FTP_Client;

public class Main {

  public static void main(String[] args) throws Exception {
    /* Variables - Connection information
    //String username = "ftp";
    String password = "test";
    String host = "127.0.0.1";
    int port = 21;*/

    String username = "test_user";
    String password = "";
    String host = "192.168.88.251";
    int port = 21;

    FTP_Client connection = new FTP_Client(username, password, host, port);

  }
}
