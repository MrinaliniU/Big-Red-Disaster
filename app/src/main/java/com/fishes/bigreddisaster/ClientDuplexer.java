package com.fishes.bigreddisaster;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ClientDuplexer implements AutoCloseable {
    Socket server;
    PrintWriter pw;
    Scanner sc;

    ClientDuplexer(Socket server){
        this.server = server;
        try {
            this.pw = new PrintWriter(server.getOutputStream());
            this.sc = new Scanner(server.getInputStream());
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }



    public void sendMessage(String message){
        pw.println(message+"\n");
        pw.flush();
    }

    public String recieveMessage() {
        return sc.nextLine();
    }



    @Override
    public void close(){
        this.pw.close();
        this.sc.close();
        try {
            this.server.close();
        }catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

}
