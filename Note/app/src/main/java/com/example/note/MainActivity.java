package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView noteListView;
    ArrayAdapter<String> adapter;
    ArrayList<String> notes;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.addNote) {
            Log.i("menu", "Add Note");
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notes = readNote(getApplicationContext(), "notes");
        noteListView = findViewById(R.id.noteListView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        noteListView.setAdapter(adapter);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), textEditor.class);
                intent.putExtra("content", notes.get(position));
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), textEditor.class);

                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Delete note")
                        .setMessage("Do you want to delete the note?")
                        .show();
                return true;
            }
        });

    }

    public void getEditedText() {
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        int position = intent.getIntExtra("position", 0);
        if (content != null) {
            updateNote(position, content);
        }
    }

    public void updateNote(int position, String text) {
        if (position < notes.size()) {
            notes.set(position, text);
        } else {
            notes.add(text);
        }
        saveNote(getApplicationContext(), "notes");

    }

    protected void saveNote(Context mContext, String filename) {
        try {
            FileOutputStream fos = mContext.openFileOutput(filename + ".dat", mContext.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(notes);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ArrayList<String> readNote(Context mContext, String filename) {
        try {
            FileInputStream fis = mContext.openFileInput(filename + ".dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<String> obj = (ArrayList<String>) ois.readObject();
            ois.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }
}