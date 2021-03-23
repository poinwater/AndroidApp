package com.example.timetables;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Integer[] numberTable = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ListView numberListView = (ListView) findViewById(R.id.numberList);
        SeekBar numberSeekBar = (SeekBar) findViewById(R.id.numberSeekBar);


        ArrayAdapter<Integer> arrayAdapter;
        arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numberTable);
        numberListView.setAdapter(arrayAdapter);


        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for (int i=0; i<numberTable.length; i++){
                    numberTable[i] = (i + 1) * (progress + 1);
                }
                numberListView.setAdapter(arrayAdapter);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        numberSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

    }

}