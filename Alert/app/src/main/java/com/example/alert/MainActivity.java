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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String language;
    TextView helloworld;

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
                itemSelectedAction("chinese");
                return true;
            case R.id.english:
                itemSelectedAction("english");
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
        helloworld = findViewById(R.id.textView);

        updateLanguage();

    }

    public void itemSelectedAction (String newLanguage) {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Change Language Preference")
                .setMessage("Do you want to change to " + newLanguage + " ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferences.edit().putString("language", newLanguage).apply();
                        language = newLanguage;
                        Log.i(newLanguage, "Selected!");
                        Log.i("language", language);
                        updateLanguage();
                    }
                })
                .setNegativeButton("no", null)
                .show();
    }

    public void updateLanguage() {
        if (language.equals("chinese")){
            helloworld.setText("你好，世界！");
        } else if(language.equals("english")) {
            helloworld.setText("Hello，World！");
        };
    }
}