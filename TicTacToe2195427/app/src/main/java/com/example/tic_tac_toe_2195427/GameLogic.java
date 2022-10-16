package com.example.tic_tac_toe_2195427;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {

    private int[][] gameBoard;

    private String[] playerNames = {"Player Red", "Player Blue"};

    private int[] winType = {-1,-1,-1};

    private Button startAgainButton;
    private Button homeButton;
    private TextView playerTurn;

    private int player =1;

    GameLogic(){
        gameBoard = new int[3][3];
        for (int i=0;i<3; i++)
            for (int j=0; j<3; j++)
                gameBoard[i][j] = 0;
    }

    public boolean updateGameBoard(int row, int col){
        if (gameBoard[row-1][col-1] == 0){
           gameBoard[row-1][col-1] = player;

           if (player == 1){
               playerTurn.setText(playerNames[1] + "'s Turn");
           }
           else playerTurn.setText(playerNames[0] + "'s Turn");

           return true;
        }
        else return false;
    }

    public boolean isWinner(){
        boolean isWinner = false;
        int fullFlag = 0;

        for (int i=0;i<3;i++){
            if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][0] == gameBoard[i][2] && gameBoard[i][0] != 0){
                winType = new int[] {i, 0,1};
                isWinner = true;
            }
        }

        for (int j=0;j<3;j++){
            if (gameBoard[0][j] == gameBoard[1][j] && gameBoard[2][j] == gameBoard[0][j] && gameBoard[0][j] !=0){
                winType = new int[] {0, j,2};
                isWinner = true;
            }
        }

        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] !=0){
            winType = new int[]{0,2,3};
            isWinner = true;
        }

        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[2][2] && gameBoard[2][0] !=0){
            winType = new int[]{2,2,4};
            isWinner = true;
        }

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (gameBoard[i][j] != 0){
                    fullFlag +=1;
                }
            }
        }

        if (isWinner){
            startAgainButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
            playerTurn.setText(playerNames[player-1] + " Won!!");
            return true;
        }
        else if (fullFlag == 9){
            startAgainButton.setVisibility(View.VISIBLE);
            homeButton.setVisibility(View.VISIBLE);
            playerTurn.setText("Tie Game!!");
            return true;
        }
        else return false;
    }

    public void resetGame(){
        for (int i=0; i<3;i++)
            for (int j=0; j<3; j++){
                gameBoard[i][j] = 0;
            }
        player = 1;
            startAgainButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);
        playerTurn.setText(playerNames[0] + "'s turn");

    }

    public void setStartAgainButton(Button startAgainButton){
        this.startAgainButton = startAgainButton;
    }

    public void setHomeButton(Button homeButton){
        this.homeButton = homeButton;
    }

    public void setPlayerTurn(TextView playerTurn){
        this.playerTurn = playerTurn;
    }

    public void setPlayerNames(String[] playerNames){
        this.playerNames = playerNames;
    }

    public int[][] getGameBoard(){
        return gameBoard;
    }

    public void setPlayer(int player){
        this.player = player;

    }

    public  int getPlayer(){
        return player;
    }
    public int[] getWinType(){
        return winType;
    }
}
