package com.example.src.test;

import java.io.IOException;
import java.net.Socket;

public class TestClient {

    public void SendMessage() {

    }


    public static void main(String[] args) {
        try {
            Socket sock = new Socket("localhost", 1337);
        }catch(IOException ioe){
            System.err.println(ioe.getMessage());
        }

    }
}
