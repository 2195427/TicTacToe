package com.example.tic_tac_toe_2195427;

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

public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winnerColor;
    boolean isWon = false;

    private final Paint paint = new Paint();

    private final GameLogic game;

    private int cellSize = getWidth()/3;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard,
                0,0);

        try{
            boardColor = array.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = array.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = array.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winnerColor = array.getInteger(R.styleable.TicTacToeBoard_winnerColor, 0);
        }finally{
            array.recycle();
        }
    }
    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension/3;
        setMeasuredDimension(dimension,dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if (isWon){
            paint.setColor(winnerColor);
            drawWinningLine(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            int row = (int)Math.ceil(y/cellSize);
            int col = (int)Math.ceil(x/cellSize);

        if (!isWon) {
            if (game.updateGameBoard(row, col)) {
                invalidate();

                if (game.isWinner()){
                    isWon = true;
                    invalidate();
                }
                if (game.getPlayer() % 2 == 0) {
                    game.setPlayer(game.getPlayer() - 1);
                } else game.setPlayer(game.getPlayer() + 1);
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

        for (int i=1; i<3; i++){
           canvas.drawLine(cellSize*i,0, cellSize*i, canvas.getWidth(), paint);
        }

        for (int j=1; j<3; j++){
            canvas.drawLine(0,cellSize*j, canvas.getWidth(),cellSize*j,  paint);
        }

    }

    private void drawMarkers(Canvas canvas){
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++){
                if (game.getGameBoard()[i][j] !=0){
                    if (game.getGameBoard()[i][j] == 1){
                        drawX(canvas, i, j);
                    }
                    else{
                        drawO(canvas, i, j);
                    }
                }
        }
    }

    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(XColor);

        canvas.drawLine((float)((col+1)*cellSize - cellSize*0.2),
                        (float)(row*cellSize + cellSize*0.2),
                        (float)(col*cellSize + cellSize*0.2),
                        (float)((row+1)*cellSize - cellSize*0.2),
                        paint);

        canvas.drawLine((float)(col*cellSize + cellSize*0.2),
                        (float)(row*cellSize + cellSize*0.2),
                        (float)((col+1)*cellSize - cellSize*0.2),
                        (float)((row+1)*cellSize - cellSize*0.2),
                        paint);
    }

    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(OColor);

        canvas.drawOval((float)(col*cellSize + cellSize*0.2),
                        (float)(row*cellSize + cellSize*0.2),
                        (float)((col*cellSize + cellSize) - cellSize*0.2),
                        (float)((row*cellSize + cellSize) - cellSize*0.2),
                        paint);
    }

    private void drawWinningHorizontal(Canvas canvas, int row, int col){
        canvas.drawLine(col,
                        row*cellSize + (float)cellSize/2,
                        cellSize*3,
                        row*cellSize + (float)cellSize/2,
                        paint);
    }

    private void drawWinningVertical(Canvas canvas, int row, int col){
        canvas.drawLine(col*cellSize + (float)cellSize/2,
                        row,
                        col*cellSize + (float)cellSize/2,
                        cellSize*3,
                         paint);
    }

    private void drawWinningDiagonal(Canvas canvas){
        canvas.drawLine(0,
                        cellSize*3,
                        cellSize*3,
                        0,
                        paint);
    }

    private void drawWinningDiagonalNegative(Canvas canvas){
        canvas.drawLine(0,
                0,
                cellSize*3,
                cellSize*3,
                paint);
    }


    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2]){
            case 1:
                drawWinningHorizontal(canvas, row, col);
                break;
            case 2:
                drawWinningVertical(canvas, row, col);
                break;
            case 3:
                drawWinningDiagonalNegative(canvas);
                break;
            case 4:
                drawWinningDiagonal(canvas);
        }
    }

    public void setUpGame (Button startAgain, Button home, TextView playerDisplay, String[] names){
        game.setStartAgainButton(startAgain);
        game.setHomeButton(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    public void resetGame(){
        game.resetGame();
        isWon = false;
    }
}
