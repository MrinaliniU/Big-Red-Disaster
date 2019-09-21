package com.example.src;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.util.Queue;



public class DatabaseInterface {
    private final Queue<String> inQueue;
    private final Queue<String> outQueue;
    private final Queue<String> outputQueue;

    public DatabaseInterface(Queue<String> inQueue, Queue<String> outQueue, Queue<String> outputQueue) {
        this.inQueue = inQueue; // Messages will have format 'lat:long:mobile:plexerID'
        this.outQueue = outQueue; // Messages will have format 'lat:long:mobile:plexerID'
        this.outputQueue = outputQueue; // Messages will have format 'lat:long:plexerID'
    }

    public static void main(String[] args) {
        // Use a service account
        InputStream serviceAccount;
        GoogleCredentials credentials;
        try {
            serviceAccount = new FileInputStream("path/to/serviceAccount.json");
            credentials = GoogleCredentials.fromStream(serviceAccount);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        Firestore db = FirestoreClient.getFirestore();
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }


}