package com.example.paulo.attendly.firebase;

import android.net.Uri;
import android.os.storage.StorageManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * Created by Daniel on 21/03/2018.
 */

public class connectFirebase {

    private static StorageReference mStorageRef;

    public static void writeData(){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");

        myRef.setValue("teste 123");
        Log.d("FB", "OI");

    }

    public static void readData(){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("FB", value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FB", "Failed to read value!", databaseError.toException());
            }
        });

    }

//    public static void storageTest(){
//
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//
//        Uri file = Uri.fromFile(new File());
//        StorageReference riversRef = mStorageRef.child("images/rivers.jpg");
//
//
//
//    }



}
