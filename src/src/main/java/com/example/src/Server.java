package com.example.src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Main server to communicate between clients and DB. Handles primary logic and DB control.
 */
public class Server implements CommunicationProtocol, Runnable {
    private ServerSocket server; // ServerSocket is for communication with the client
    private ArrayList<Duplexer> duplexers;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
        } catch(IOException ioe) {
            System.out.println("Error during server initialization!");
            System.out.println("Printing exception data: ");
            System.err.println(ioe.getMessage());
        }
    }
    @Override
    public void run() {
        System.out.println("Entering await loop, press ctrl + c to exit.");
        int count = 0;
        while(true){
            try {
                Socket client = server.accept();
                duplexers.add(new Duplexer(client));
                System.out.println("Connection #" + ++count);
            }catch(IOException ioe) {
                System.err.println("Error accepting a new connection.");
            }
        }
    }

    public static void main(String[] args) {
        Server s;
        if(args.length == 0) {
            System.out.println("Initiating server startup on default port: 1337" );
            s = new Server(1337);
        } else {
            System.out.println("Initiating server startup on port: " + args[0]);
            s = new Server(Integer.parseInt(args[0]));
        }
        new Thread(s).start();
    }
}