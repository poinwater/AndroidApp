package com.example.downloadingwebcontents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            Log.i("URL", strings[0]);
            return "Done";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String result = "";
        try{
            DownloadTask task = new DownloadTask();
            result = task.execute("http://www.zappycode.com").get();

        }catch(Exception e){
            e.printStackTrace();
        }
        Log.i("result", result);

    }
}