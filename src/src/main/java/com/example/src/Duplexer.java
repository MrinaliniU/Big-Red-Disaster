package com.example.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class Duplexer implements AutoCloseable, Runnable {
    Writer clientOut;
    Reader clientIn;
    Socket sock;

    public Duplexer(Socket sock) throws IOException {
        this.sock = sock;
        this.clientIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        this.clientOut = new PrintWriter(sock.getOutputStream());
    }

    @Override
    public void run() {

    }


    @Override
    public void close() throws Exception {

    }
}
