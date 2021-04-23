package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDatabase db = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "users").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
//        userDao.insertUser(new Users("Nick", 28));
//        userDao.insertUser(new Users("Shanshan", 30));
        Cursor c = userDao.getAllUsers();


        int nameIndex = c.getColumnIndex("name");
        int ageIndex = c.getColumnIndex("age");

        c.moveToFirst();

        while (c.isAfterLast()) {
            Log.i("name", c.getString(nameIndex));
            Log.i("age", c.getString(ageIndex));
            c.moveToNext();
        }
    }



}