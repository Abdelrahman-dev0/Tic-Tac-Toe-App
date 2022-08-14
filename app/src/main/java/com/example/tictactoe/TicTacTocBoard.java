package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class TicTacTocBoard extends View {

    private final int boardColor;
    private final int xColor;
    private final int oColor;
    private final int winningLineColor;
    private final Paint paint = new Paint();
    private int cellSize = getWidth()/3;
    private final GameLogic game;
    private boolean winningLine = false;

    public TicTacTocBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacTocBoard, 0, 0);

        try{
           boardColor = array.getInteger(R.styleable.TicTacTocBoard_boardColor , 0);
           xColor = array.getInteger(R.styleable.TicTacTocBoard_xColor , 0);
           oColor = array.getInteger(R.styleable.TicTacTocBoard_oColor , 0);
           winningLineColor = array.getInteger(R.styleable.TicTacTocBoard_winningLineColor , 0);
        }
        finally {
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension /3;
        setMeasuredDimension(dimension, dimension);

    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        int action = motionEvent.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellSize);
            int colm = (int) Math.ceil(x/cellSize);

            if(!winningLine){
                if(game.updateGameBoard(row, colm)){
                    invalidate();

                    if(game.winnerCheck()){
                        winningLine = true;
                        invalidate();
                    }

                    // Updating the players turn
                    if(game.getPlayer() % 2 == 0){
                        game.setPlayer(game.getPlayer()-1);
                    }

                    else{
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }

            invalidate();
            return true;
        }

        return false;
    }

    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for(int c = 1; c < 3; c++){
            canvas.drawLine(cellSize * c, 0, cellSize* c, canvas.getWidth(), paint);
        }

        for(int r = 1; r < 3; r++){
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);
        }
    }

    private void drawMarkers(Canvas canvas){
        for(int r = 0;r < 3;r++){
            for(int c = 0;c < 3;c++){
                if(game.getGameBoard()[r][c] != 0){
                    if(game.getGameBoard()[r][c] == 1){
                        drawX(canvas, r, c);
                    }

                    else{
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int colm){
        paint.setColor(xColor);
        canvas.drawLine((float) ((colm+1) * cellSize - cellSize*0.2), (float) (row * cellSize + cellSize*0.2), (float) (colm * cellSize + cellSize*0.2), (float) ((row+1) * cellSize - cellSize*0.2), paint);
        canvas.drawLine((float) (colm * cellSize + cellSize*0.2), (float) (row * cellSize + cellSize*0.2),(float) ((colm+1) * cellSize - cellSize*0.2), (float) ((row+1) * cellSize - cellSize*0.2), paint);
    }

    private void drawO(Canvas canvas, int row, int colm){
        paint.setColor(oColor);
        canvas.drawOval((float) (colm * cellSize + cellSize*0.2), (float) (row * cellSize + cellSize*0.2),(float) (colm * cellSize + cellSize - cellSize*0.2), (float) (row * cellSize + cellSize - cellSize*0.2), paint);

    }

    private void drawHorizontalLine(Canvas canvas, int row, int colm){
        canvas.drawLine(colm, row*cellSize + (float) cellSize/2, cellSize*3, row*cellSize + (float) cellSize/2, paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int colm){
        canvas.drawLine(colm*cellSize + (float) cellSize/2, row, colm*cellSize + (float) cellSize/2, cellSize*3,paint);
    }

    private void drawDiagonalLinePos(Canvas canvas){
        canvas.drawLine(0, cellSize*3, cellSize*3,0,paint);
    }

    private void drawDiagonalLineNegative(Canvas canvas){
        canvas.drawLine(0, 0, cellSize*3,cellSize*3,paint);
    }

    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int colm = game.getWinType()[1];

        switch(game.getWinType()[2]){
            case 1:
                drawHorizontalLine(canvas, row, colm);
                break;
            case 2:
                drawVerticalLine(canvas, row, colm);
                break;
            case 3:
                drawDiagonalLineNegative(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }

    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] names){
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    public void resetGame(){
        game.resetGame();
        winningLine = false;
    }
}
