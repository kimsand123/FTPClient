package com.company;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public static void main(String args[]) {

// Variables
Scanner scan = new Scanner(System.in);
String menuAnswer = null;
String password = "anon123";
// Get serverName
String serverName = "HOSTNAME";
// Create menu
  do {
    System.out.println("Connected to: " + serverName);
    System.out.println("FTP Client Menu:");
    System.out.println("Default User: anon");
    System.out.println("Press 1 to add password (Default = anon123)");
    System.out.println("Press 2 to see FTP servers directories");
    System.out.println("Press 3 to upload test file to FTP server");
    System.out.println("Press q to close FTP connection and exit");
    menuAnswer = scan.nextLine();
    switch(menuAnswer) {
      case 1:
        password = scan.nextLine();
        System.out.println("Your password: " + password);
      case 2:
        // Read directories on FTP Server
      case 3:
        // Upload file
      case "q":
        // Close FTP connection
        // Return
        return;
    }
  } while (!menuAnswer.equals("q")); // End of loop
}