package com.example.src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Main server to communicate between clients and DB. Handles primary logic and DB control.
 * Language: Java
 * Filename: Server.java
 * Author: Nick Patel
 */
public class Server implements CommunicationProtocol, Runnable {
    private ServerSocket server; // ServerSocket is for communication with the client
    private ArrayList<Duplexer> duplexers; // Each connection to the server gets a duplexer.
    private final Queue<String> putQueue; // Queue that the duplexers will wait on to perform IO
    private final Queue<String> getQueue; // """"
    private final Queue<String> outputQueue;
    private final DatabaseInterface dbi;

    /**
     * Constructor for server, opens the server on the supplied port and initializes the queues for
     * I/O to the DB.
     * @param port The port number that the server will open on.
     */
    public Server(int port) {
        this.putQueue = new LinkedList<String>();
        this.getQueue = new LinkedList<String>();
        this.outputQueue = new LinkedList<String>();
        this.duplexers = new ArrayList<Duplexer>();
        this.dbi = new DatabaseInterface(putQueue, getQueue, outputQueue);
        try {
            server = new ServerSocket(port);
        } catch(IOException ioe) {
            System.out.println("Error during server initialization!");
            System.out.println("Printing exception data: ");
            System.err.println(ioe.getMessage());
        }
    }

    /**
     * Primary run for the threaded server, will loop until interrupted accepting connections.
     */
    @Override
    public void run() {
        System.out.println("Entering await loop, press ctrl + c to exit.");
        int count = 0;
        while(true){
            try {
                Socket client = server.accept();
                ++count; // Increment plexerID.
                new Thread(new Duplexer(client, putQueue, getQueue,Integer.toString(count))).start();
                System.out.println("Connection #" + count + " established");
            }catch(IOException ioe) {
                System.err.println("Error accepting a new connection.");
            }
            for(Duplexer d : duplexers){
                d.end();
            }
        }
    }

    /**
     * Driver for the server, will open the server and start it on port 1337 unless specifed in the
     * command line.
     * @param args Command line args - used to specify the port.
     */
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