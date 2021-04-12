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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Recording";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    protected static String Path;
    protected static String prefix = "";
    public static boolean isRecording = false;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    MediaRecorder recorder;
    MediaPlayer audio_player;
    MediaPlayer recording_player;
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

        Path = getExternalCacheDir().getAbsolutePath();
        createRecorder("1.3gp");

        setLongClickToAll(9);


    }

    public boolean startRecording (String fileName, boolean isRecording) {
        createRecorder(fileName);
        try {
        recorder.prepare();
        } catch (IOException e) {
        Log.e(LOG_TAG, "prepare() failed");
        return false;
        }
        recorder.start();
        return true;
    }

    public boolean stopRecording (String fileName, boolean isRecording) {
        if (recorder == null) {
            recorder.release();
            recorder = null;
        }
        return false;
    }


    public void createRecorder(String newFileName) {

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(Path + "/" + prefix + newFileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

    }

    public void onClick(View v){

        Log.i("test", "single click" + v.getTag().toString());

    }

    protected void setLongClickToAll(int bound) {
        ArrayList<Button> BtnList = getAllButton(bound);
        for (Button Btn : BtnList) {
            setLongClick(Btn);
        }

    }

    protected ArrayList<Button> getAllButton(int bound) {
        ArrayList<Button> BtnList = new ArrayList();

        for (int i = 0; i < bound; i++) {
            int id = getResources().getIdentifier("button"+i, "id", getPackageName());
            BtnList.add((Button) findViewById(id));
        }
        return BtnList;
    }

    protected void setLongClick(Button Btn) {
        Btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("test", "long click" + Btn.getText().toString());
                String FileName = Btn.getText().toString() + ".3gp";

                if (isRecording) {
                    isRecording = stopRecording(FileName, isRecording);
                    audio_player = MediaPlayer.create(Btn.getContext(), R.raw.recording_end);
                    audio_player.start();
                    return false;
                }
                isRecording = startRecording(FileName, isRecording);
                audio_player = MediaPlayer.create(Btn.getContext(), R.raw.recording_start);
                audio_player.start();
                return true;
            }
        });
    }
}
