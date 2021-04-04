package com.example.ninemindmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

public class Recording extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;

    private RecordButton recordButton = null;
    private MediaRecorder recorder = null;

    private PlayButton   playButton = null;
    private MediaPlayer   player = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};


    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            Log.i("saved", fileName);
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    private void startRecording() {
        player = MediaPlayer.create(this, R.raw.recording_start);
        recorder = new MediaRecorder();

        player.start();
        player = null;
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        recorder.start();
    }

    private void stopRecording() {
        player = MediaPlayer.create(this, R.raw.recording_end);
        player.start();
        player = null;

        recorder.stop();
        recorder.release();
        recorder = null;
    }

    class RecordButton extends androidx.appcompat.widget.AppCompatButton {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("停止录音");
                } else {
                    setText("开始录音");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("开始录音");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends androidx.appcompat.widget.AppCompatButton {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("停止播放");
                } else {
                    setText("开始播放");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("开始播放");
            setOnClickListener(clicker);
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Record to the external cache directory for visibility
        Intent intent = getIntent();
        int extension = intent.getIntExtra("index", 0);
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/" + Integer.toString(extension) + ".3gp";


        LinearLayout ll = new LinearLayout(this);
        recordButton = new RecordButton(this);
        ll.addView(recordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1));
        playButton = new PlayButton(this);
        ll.addView(playButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1));
        setContentView(ll);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }
}