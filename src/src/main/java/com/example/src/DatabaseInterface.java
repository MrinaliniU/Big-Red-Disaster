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
    private Queue<String> inQueue;

    public DatabaseInterface(Queue<String> inQueue) {
        this.inQueue = inQueue;
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