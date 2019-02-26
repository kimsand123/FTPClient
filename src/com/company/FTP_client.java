package com.company;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class FTP_client {

    TCP_Handling tcpHandling;

    public FTP_client() {
        tcpHandling = new TCP_Handling();
        tcpHandling.openCommunication();
    }

    public void connectToFTPServer() {
        // Variables
        Scanner scan = new Scanner(System.in);
        String menuAnswer = null;
        String host = "ftp.soeborg.it";
        String username = "soeborg.it";
        String password = "1OJ*RU6i$";
        int port = 21;

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
                    String file1 = downloadFile("filepath/somefilename");
                    //print 1kb to screen
                    printFile(file1);
                case 2:
                    //download file 2
                    String file2 = downloadFile("filepath2/somefilename2");
                    //print 1kb to screen
                    printFile(file2);
                case 3:
                    // Upload file
                    uploadFile("filepath/somefilename");
                case 4:
                    // Close FTP connection
                    tcpHandling.closeCommunication();
                    return;
            }
        } while (true); // End of loop
    }

    public String downloadFile(String filenameAndPath){

    }

    public void printFile (String file){

    }

    public void uploadFile(String filenameAndPath){

    }


}

