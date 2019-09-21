package com.example.src;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {

    public void SendMessage() {

    }


    public static void main(String[] args) {
        /*
        System.out.println("Hello");
        try {
            Socket sock = new Socket("127.0.0.1", 1337);
            System.out.println(sock.getInetAddress());

            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            pw.flush();
            Scanner sc = new Scanner(sock.getInputStream());
            pw.write("test:test:t");
            System.out.println(sc.nextLine());
        }catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }
        */
        System.out.println("Hello?");
        try{
            Socket sock = new Socket("127.0.0.1", 1337);
            System.out.println(sock.getInetAddress());

            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            Scanner sc = new Scanner(sock.getInputStream());
            pw.write("test:test:t\n");
            pw.flush();

        }catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }
    }
}
