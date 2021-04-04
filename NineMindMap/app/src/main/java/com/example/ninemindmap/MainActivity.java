package com.example.ninemindmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


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
import android.widget.LinearLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Recording";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    protected static String Path;
    protected static String tag;


    protected static MediaRecorder recorder = null;
    protected static MediaPlayer   player = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        player = MediaPlayer.create(this, R.raw.voice);
        Path = getExternalCacheDir().getAbsolutePath();


    }


    public static void createRecorder(String newFileName) {

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(Path + "/" + newFileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

    }

    public void onClick(View v){

        player.start();
        int tag = Integer.parseInt(v.getTag().toString());
        Intent intent = new Intent(getBaseContext(), Recording.class);
        intent.putExtra("index", tag);
        startActivity(intent);


    }



    public static void PlayText(String text) {
        Log.i("tag", text);
    }
}
