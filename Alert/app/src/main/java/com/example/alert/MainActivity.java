package com.example.alert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String language;
    SharedPreferences sharedPreferences;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int itemId = item.getItemId();
        switch(itemId){
            case R.id.chinese:
                itemSelectedAction(itemId,"chinese");
                return true;
            case R.id.english:
                itemSelectedAction(itemId, "english");
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.alert", MODE_PRIVATE);
        language = sharedPreferences.getString("language", "english");

    }

    public void itemSelectedAction (int itemId, String newLanguage) {

        sharedPreferences.edit().putString("language", newLanguage).apply();
        language = newLanguage;
        Log.i(newLanguage, "Selected!");
        Log.i("language", language);

    }
}