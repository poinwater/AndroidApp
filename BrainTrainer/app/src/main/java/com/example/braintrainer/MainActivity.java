package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView scoreTextView;
    TextView timerTextView;
    TextView questionTextView;
    CountDownTimer countDown;
    int ansBtnId = 0;
    int totalScores = 0;
    int totalQuestions = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        questionTextView = findViewById(R.id.questionTextView);
        generateQuestion();

    }

    public void generateQuestion(){
        scoreTextView.setText(String.valueOf(totalScores) + " / " + String.valueOf(totalQuestions));
        if (totalQuestions < 10){
            randomMathQuestion();
            countDown();
        }else{
            Button playAgainBtn = findViewById(R.id.playAgainBtn);
            playAgainBtn.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
        }
    }

    public void countDown(){
        countDown = new CountDownTimer(10000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.format("%02d", millisUntilFinished/1000) + " s");
            }

            @Override
            public void onFinish() {
                totalQuestions += 1;
                generateQuestion();
            }
        };
        countDown.start();
    }

    public void randomMathQuestion(){
        Random randomGen = new Random();
        int firstNumber = randomGen.nextInt(10) + 1;
        int secondNumber = randomGen.nextInt(10) + 1;
        int operators = randomGen.nextInt(4);
        int answer = 0;
        String operator = "";

        switch (operators){
            case 0:
                answer = firstNumber + secondNumber;
                operator = " + ";
                break;
            case 1:
                answer = firstNumber - secondNumber;
                operator = " - ";
                break;
            case 2:
                answer = firstNumber * secondNumber;
                operator = " * ";
                break;
            case 3:
                answer = firstNumber / secondNumber;
                operator = " / ";
        }

        String operation = String.valueOf(firstNumber) + operator + String.valueOf(secondNumber);
        questionTextView.setText(operation);
        ansBtnId = AnswerPositions(answer);
    }

    public int AnswerPositions(int answer){
        Random randomGen = new Random();
        Button btn;
        int[] positions = new int[] {R.id.button1, R.id.button2, R.id.button3, R.id.button4};
        int ansPosition = randomGen.nextInt(4);
        for (int i=0; i<4; i++){
            btn = findViewById(positions[i]);
            if (i == ansPosition){
                btn.setText(String.valueOf(answer));
            }else{
                int randomNum = randomGen.nextInt(100) + 1;
                while (randomNum == answer){
                    randomNum = randomGen.nextInt(100) + 1;
                }
                btn.setText(String.valueOf(randomNum));
            }
        }
        return positions[ansPosition];
    }

    public void onClick(View view){
        if (totalQuestions >= 10){
            return;
        }
        int id = view.getId();
        if (id == ansBtnId){
            totalScores += 1;
        }
        totalQuestions += 1;
        countDown.cancel();
        generateQuestion();

    }

    public void playAgain(View view){
        Button playAgainBtn = findViewById(R.id.playAgainBtn);
        playAgainBtn.setVisibility(View.GONE);

        totalScores = 0;
        totalQuestions = 0;
        generateQuestion();
    }
}