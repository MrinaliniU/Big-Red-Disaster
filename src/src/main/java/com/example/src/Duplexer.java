package com.example.src;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;


/**
 * Duplexer is the primary means of communication for the server and clients.
 */
public class Duplexer implements AutoCloseable, Runnable {
    private final Writer clientOut;
    private final Scanner clientIn;
    private final Socket sock;
    private final Queue<String> putQueue;
    private final Queue<String> getQueue;
    private final Queue<String> outQueue;
    private boolean sentinel;

    public Duplexer(Socket sock, Queue<String> putQueue, Queue<String> getQueue) throws IOException {
        this.sock = sock;
        this.putQueue = putQueue;
        this.getQueue = getQueue;
        this.outQueue = new LinkedList<String>();
        this.clientIn = new Scanner(this.sock.getInputStream());
        this.clientOut = new PrintWriter(this.sock.getOutputStream());
        this.sentinel = true;
    }

    public void recieveMessage() {
        String message="";
        try {
            message = this.clientIn.nextLine();

            String[] tokens = message.split(":");
            System.out.println(message);
            if (tokens[tokens.length - 1].toUpperCase().equals("T")) {
                // can walk. --> query DB for shelters then push to client.
                synchronized (this.getQueue) {
                    this.getQueue.add(message);
                }

            }
            synchronized (this.putQueue) {
                this.putQueue.add(message);
            }
            System.out.println(getQueue);
            System.out.println(putQueue);
            // Add to DB, if cannot walk then have Firebase dispatch a push to rescue workers.
        } catch(NoSuchElementException nse){
            //squash
        }
    }

    public void sendMessage() {
        synchronized (outQueue) {
            if(outQueue.size() > 0) {
                String message = "";
                message += outQueue.poll();
                try {
                    this.clientOut.write(message + "\n");
                } catch(IOException ioe) {
                    System.err.println(ioe.getMessage());
                }
            }
        }
    }

    public void end() {
        this.sentinel = false;
    }

    @Override
    public void run() {
        while(sentinel) {
            recieveMessage();
            sendMessage();
        }
    }

    /**
     * Ensures that all closable objects are safely closed.
     * @throws IOException Throws an IOException if there is an issue closing the writer or Scanner
     */
    @Override
    public void close() throws IOException {
        sock.close();
        clientIn.close();
        clientOut.close();
    }
}
