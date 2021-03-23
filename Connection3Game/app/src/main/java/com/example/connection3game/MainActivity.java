package com.example.connection3game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 0: yellow, 1: red
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int activePlayer = 0;

    boolean gameActive = true;

    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] != 2 || !gameActive){
            return;
        }

        TextView winnerText = (TextView) findViewById(R.id.winnerTextView);

        Button playAgain = (Button) findViewById((R.id.playBtn));

        gameState[tappedCounter] = activePlayer;

        counter.setTranslationY(-1500);

        if (activePlayer == 0) {

            counter.setImageResource(R.drawable.yellow);

            activePlayer = 1;

        }else{
            counter.setImageResource(R.drawable.red);

            activePlayer = 0;
        }
        counter.animate().translationYBy(1500).setDuration(300);

        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){

                boolean gameActive = false;

                String winner = "";

                // Someone has won

                if (activePlayer == 1){

                    winner = "Yellow";

                }else{

                    winner = "Red";

                }

                winnerText.setText(winner + " has won!");

                playAgain.setVisibility(View.VISIBLE);

                winnerText.setVisibility(View.VISIBLE);

                Toast.makeText(this, winner + " has won!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void playAgain(View view){

        TextView winnerText = (TextView) findViewById(R.id.winnerTextView);

        Button playAgain = (Button) findViewById((R.id.playBtn));

        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);

        playAgain.setVisibility(View.INVISIBLE);

        winnerText.setVisibility(View.INVISIBLE);

        for(int i=0; i<grid.getChildCount(); i++){

            ImageView counter = (ImageView) grid.getChildAt(i);

            counter.setImageDrawable(null);
        }

        for (int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        activePlayer = 0;

        gameActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}