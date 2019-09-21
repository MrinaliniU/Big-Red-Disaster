package com.fishes.bigreddisaster;


import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

public class ClientDuplexer implements Serializable, Runnable {
    Socket server;
    PrintWriter pw;
    Scanner sc;

    ClientDuplexer(Socket server){
        this.server = server;
    }


    public void sendMessage(String message){
        pw.println(message);
    }

    public String recieveMessage() {
        return sc.nextLine();
    }

    @Override
    public void run() {

    }

}
