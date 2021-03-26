package com.example.storingdatapermanently;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.storingdatapermanently", Context.MODE_PRIVATE);

        ArrayList<String> friends = new ArrayList<>();
        friends.add("Fido");
        friends.add("Sarah");
        friends.add("Jones");



//        try {
//            sharedPreferences.edit().putString("friends", ObjectSerializer.serialize(friends)).apply();
//
//            Log.i("friends", ObjectSerializer.serialize(friends));
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        ArrayList<String> newFriends = new ArrayList<>();
        try {
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends", ObjectSerializer.serialize(new ArrayList<String>())));

            Log.i("new Friends", newFriends.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


        //sharedPreferences.edit().putString("username", "nick").apply();

        //String username = sharedPreferences.getString("username", "");

        //Log.i("Username", username);

    }
}