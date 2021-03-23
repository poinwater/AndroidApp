package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CountDownTimer eggTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar timeSeekBar = findViewById(R.id.timeSeekBar);
        TextView timeTextView = findViewById(R.id.timeTextView);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String minute = String.format("%02d", progress / 60);
                String seconds = String.format("%02d", progress % 60);
                timeTextView.setText(minute + " : " + seconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void startCounterDown(View view){
        SeekBar timeSeekBar = findViewById(R.id.timeSeekBar);
        TextView timeTextView = findViewById(R.id.timeTextView);
        Button countBtn = findViewById(R.id.countBtn);
        MediaPlayer md = MediaPlayer.create(getApplicationContext(), R.raw.bell);

        if (eggTimer != null && countBtn.getText() == "stop"){
            eggTimer.cancel();
            countBtn.setText("start");
            return;
        }
        countBtn.setText("stop");
        long milliseconds = timeSeekBar.getProgress() * 1000;
        eggTimer = new CountDownTimer(milliseconds, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                String minute = String.format("%02d", (millisUntilFinished/ 1000) / 60);
                String seconds = String.format("%02d", (millisUntilFinished / 1000) % 60);
                timeTextView.setText(minute + " : " + seconds);
            }

            @Override
            public void onFinish() {
                md.start();
                countBtn.setText("start");

            }


        };

        eggTimer.start();
    };
}