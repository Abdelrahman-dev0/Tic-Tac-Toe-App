package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {
    private int[][] gameBoard;
    private int player = 1;
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;
    private String[] playerNames = {"Player1", "Player2"};
    // 1st element ---> row, 2nd element ---> colm, 3rd element ---> line type
    private int[] winType = {-1, -1, -1};

    GameLogic(){
        gameBoard = new int[3][3];
        for(int r = 0;r < 3;r++){
            for(int c = 0;c < 3;c++){
                gameBoard[r][c] = 0;
            }
        }
    }

    public void resetGame(){
        for(int r = 0;r < 3;r++){
            for(int c = 0;c < 3;c++){
                gameBoard[r][c] = 0;
            }
        }

        winType[0] = -1;
        winType[1] = -1;
        winType[2] = -1;

        player = 1;
        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);
        playerTurn.setText((playerNames[0] + "'s Turn"));
    }

    @SuppressLint("SetTextI18n")
    public boolean updateGameBoard(int row, int colm){
         if(gameBoard[row-1][colm-1] == 0){
            gameBoard[row-1][colm-1] = player;

            if(player == 1){
                playerTurn.setText((playerNames[1] + "'s Turn"));
            }

            else{
                playerTurn.setText((playerNames[0] + "'s Turn"));
            }

            return true;
         }

        else{
            return false;
        }
    }

    public boolean winnerCheck(){
        boolean isWinner = false;

        // Horizontal check (1)
        for(int r = 0;r < 3;r++){
            if(gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard[r][0] != 0){
                winType = new int[] {r, 0 ,1};
                isWinner = true;
            }
        }

        // Vertical check (2)
        for(int c = 0; c < 3; c++){
            if(gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] && gameBoard[0][c] != 0){
                winType = new int[] {0, c ,2};
                isWinner = true;
            }
        }

        // Negative diagonal check (3)
        if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0){
            winType = new int[] {0, 2 ,3};
            isWinner = true;
        }

        // Positive diagonal check (4)
        if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0){
            winType = new int[] {2, 2 ,4};
            isWinner = true;
        }

        int boardFilled = 0;
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                if(gameBoard[r][c] != 0){
                    boardFilled++;
                }
            }
        }

        if(isWinner){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText(playerNames[player-1] + " Won!!!!!!");
            return true;
        }

        else if(boardFilled == 9){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText("Tie Game!!!!!!!");
            return false;
        }

        else{
            return false;
        }
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setHomeBTN(Button homeBTN) {
        this.homeBTN = homeBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public int[] getWinType() {
        return winType;
    }
}
