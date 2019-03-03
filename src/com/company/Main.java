package com.company;

public class Main {

    public static void main(String[] args) {
	FTP_controlprogram ftpProgram = new FTP_controlprogram();
	//ftpProgram.menu();

	TCP_Handling tcpCom = new TCP_Handling();

	tcpCom.openCommunication();
		tcpCom.tcpSendStream("HELO\n");
		System.out.println(tcpCom.tcpReadStream());
	tcpCom.closeCommunication();



    }
}
