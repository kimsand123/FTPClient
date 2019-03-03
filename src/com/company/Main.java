package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
		String username = "ftp";
		String password = "dtutest123!";
		String host = "127.0.0.1";
		int port = 21;

		/*String username = "soeborg.it";
		String password = "1OJ*RU6i$";
		String host = "ftp.soeborg.it";
		int port = 21;*/
		FTP_controlprogram ftpProgram = new FTP_controlprogram(username, password, host, port);



		ftpProgram.connectToFTPServer();
		ftpProgram.recieveTCPstream();
		ftpProgram.closeFtp();
		ftpProgram.closeTcp();




    }
}
