package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TheGame extends AppCompatActivity {

    private TicTacTocBoard ticTacTocBoard;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game);

        Button playAgainBTN = findViewById(R.id.playAgainButton);
        Button homeBTN = findViewById(R.id.homeButton);
        TextView playerTurn = findViewById(R.id.displayTurn);

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");

        if(playerNames != null){
            playerTurn.setText((playerNames[0] + "'s Turn"));
        }

        ticTacTocBoard = findViewById(R.id.ticTacTocBoard);

        ticTacTocBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, playerNames);
    }

    public void platAgainButtonClick(View view){
        ticTacTocBoard.resetGame();
        ticTacTocBoard.invalidate();
    }

    public void homeButtonClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}