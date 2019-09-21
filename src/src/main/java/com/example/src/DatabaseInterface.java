package com.example.src;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutionException;


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
            serviceAccount = new FileInputStream("../../../../../../bigreddisaster-1-007aa273199d.json");
            credentials = GoogleCredentials.fromStream(serviceAccount);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }

        Firestore db = FirestoreClient.getFirestore();



        DocumentReference docRef = db.collection("userLocation").document("userloc");
                Map<String, Object> data = new HashMap<>();

                data.put("latitude", 0);
                data.put("longitude", 0);
        //asynchronously write data
                ApiFuture<WriteResult> result = docRef.set(data);
        // ...
        // result.get() blocks on response
            try {
                System.out.println("Update time : " + result.get().getUpdateTime());
            }
            catch(InterruptedException ie){
                ie.printStackTrace();
            }
            catch(ExecutionException ee){
                ee.printStackTrace();
            }
    }


}