package com.example.voice;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    boolean isUserControl = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.dayu);
        mediaPlayer.start();

        // Volume Seek Bar
        SeekBar volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });



        SeekBar scrapControl = (SeekBar) findViewById(R.id.scrapSeekBar);

        scrapControl.setMax(mediaPlayer.getDuration());

        scrapControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (isUserControl){
                    mediaPlayer.seekTo(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isUserControl = true;

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isUserControl = false;
            }

        });

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                scrapControl.setProgress(mediaPlayer.getCurrentPosition());

            }
        }, 0, 300);
    }

    public void playVoice(View view){
        mediaPlayer.start();
    }

    public void stopVoice(View view){
        mediaPlayer.pause();
    }



}