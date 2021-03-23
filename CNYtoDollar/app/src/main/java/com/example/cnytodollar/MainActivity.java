package com.example.cnytodollar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ConvertCurrency(View view){
        EditText editText = (EditText) findViewById(R.id.currency);
        String CNYammount = editText.getText().toString();
        double CNYdouble = Double.parseDouble(CNYammount);
        double USDdouble = CNYdouble * 0.15;
        String USDammount = String.format("%.2f", USDdouble);
        Toast.makeText(this, "¥" + CNYammount + " is $" + USDammount, Toast.LENGTH_LONG).show();

    }
}