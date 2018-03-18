package com.carl.android.androidproject_tictactoe;

import java.util.Random;

public class Board{

    private static final  Random rand = new Random();
    private char[] boardElements;
    private char currentPlayer;
    private boolean gameOver;

    public Board(){
        boardElements = new char[9];
        newGame();
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public char play(int x, int y){
        if(!gameOver && boardElements[3 * y + x] == ' '){
            boardElements[3 * y + x] = currentPlayer;
            changePlayer();
        }

        return checkGameOver();
    }

    public void changePlayer(){
        currentPlayer = (currentPlayer == 'X' ? 'O' : 'X');
    }

    public char getBoard(int x, int y){
        return boardElements[3 * y + x];
    }

    public void newGame(){
        for(int i = 0; i < boardElements.length; i++){
            boardElements[i] = ' ';
        }

        currentPlayer = 'X';
        gameOver = false;
    }

    public char checkGameOver(){
        for(int i = 0; i < 3; i++){
            if(getBoard(i, 0) != ' ' && getBoard(i, 0) == getBoard(i, 1) &&
                    getBoard(i, 1) == getBoard(i, 2)){
                gameOver = true;
                return getBoard(i, 0);
            }

            if(getBoard(0, i) != ' ' && getBoard(0, i) == getBoard(1, i) &&
                    getBoard(1, i) == getBoard(2, i)){
                gameOver = true;
                return getBoard(0, i);
            }
        }

        if(getBoard(0,0) != ' ' && getBoard(0,0) == getBoard(1,1) &&
                getBoard(1,1) == getBoard(2,2)){
            gameOver = true;
            return getBoard(0,0);
        }

        if(getBoard(0,2) != ' ' && getBoard(0,2) == getBoard(1,1) &&
                getBoard(1,1) == getBoard(2,0)){
            gameOver = true;
            return getBoard(2,0);
        }

        for(int i = 0; i < 9; i++){
            if(boardElements[i] == ' '){
                return ' ';
            }
        }

        return 'T';
    }

    public char computer(){
        if(!gameOver){
            int position;

            do {
                position = rand.nextInt(9);
            }while(boardElements[position] != ' ');

            boardElements[position] = currentPlayer;
            changePlayer();
        }

        return checkGameOver();
    }
}
