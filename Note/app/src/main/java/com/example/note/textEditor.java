package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class textEditor extends AppCompatActivity {
    EditText textEditor;
    String content = "";
    Intent intent;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);

        textEditor = findViewById(R.id.textEditor);
        intent = getIntent();

        position = intent.getIntExtra("position", -1);

        if (position != -1 ) {
            content = MainActivity.notes.get(position);
            textEditor.setText(content);
        } else {
            MainActivity.notes.add("");
            position = MainActivity.notes.size() - 1;
        }


        textEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(position, String.valueOf(s));
                MainActivity.adapter.notifyDataSetChanged();
                MainActivity.saveNote(getApplicationContext(), "notes");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
}