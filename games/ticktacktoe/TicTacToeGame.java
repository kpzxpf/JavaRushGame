package com.javarush.games.ticktacktoe;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;

import java.util.Collection;
import java.util.Collections;

public class TicTacToeGame extends Game {
    private int[][] model = new int[3][3];
    private int currentPlayer;
    private boolean isGameStopped;
    @Override
    public void initialize() {
        setScreenSize(3, 3);
        startGame();
        updateView();
    }

    public void  startGame(){
        isGameStopped = false;
        currentPlayer = 1;
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                model[y][x] = 0;
            }
        }
    }
    public void updateCellView(int x, int y, int value){
        if (value == 1)
            setCellValueEx(x, y,Color.ANTIQUEWHITE, "X", Color.BLACK);
        else if (value == 2)
            setCellValueEx(x, y,Color.ANTIQUEWHITE, "O",Color.BLACK);
        else
            setCellValueEx(x, y, Color.ANTIQUEWHITE, " ", Color.BLACK);
    }
    public void updateView() {
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++) {
                    updateCellView(x, y, model[x][y]);
            }
        }
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        if(isGameStopped){
            return;
        }
        if(model[x][y] == currentPlayer){
            return;
        }
        setSignAndCheck(x, y);
        currentPlayer = currentPlayer == 1 ? 2 : 1;
        computerTurn();
        currentPlayer = 3 - currentPlayer;
    }

    public boolean checkWin(int x, int y, int n){
        if(model[0][y] == n && model[1][y] == n && model[2][y] == n){
            return true;
        }
        if(model[x][0] == n && model[x][1] == n && model[x][2] == n){
            return true;
        }
        if((model[0][0] == n && model[1][1] == n && model[2][2] == n) ||
           (model[0][2] == n && model[1][1] == n && model[2][0] == n)){
            return true;
        }
        return  false;
    }
    public void setSignAndCheck(int x, int y){
        model[x][y] = currentPlayer;
        updateView();
        if(checkWin(x, y, currentPlayer)){
            isGameStopped = true;
            showMessageDialog(Color.GREEN,"Победил " + currentPlayer + " игрок", Color.BLACK, 65);
        }
        if(!hasEmptyCell()){
            isGameStopped = true;
            showMessageDialog(Color.GRAY,"Ничья", Color.BLACK, 65);
        }
    }
    public boolean hasEmptyCell(){
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++) {
                if(model[x][y] == 0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onKeyPress(Key key) {
        if(isGameStopped && key == Key.SPACE){
            startGame();
            updateView();
        }
        if( key == Key.ESCAPE){
            startGame();
            updateView();
        }
    }
    public boolean checkFutureWin(int x, int y, int n){
        if (model[x][y] != 0){
            return false;
        }
        model[x][y] = n;
        if(checkWin(x, y, n)){
            model[x][y] = 0;
            return true;
        }
        model[x][y] = 0;
        return false;
    }
    public void computerTurn() {
        if (model[1][1] == 0) {
            setSignAndCheck(1, 1);
            return;
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (checkFutureWin(x, y, currentPlayer)) {
                    setSignAndCheck(x, y);
                    return;
                }
            }
        }

        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++) {
                if (checkFutureWin(x, y, 3 - currentPlayer)) {
                    setSignAndCheck(x, y);
                    return;
                }
            }
        }
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                if (model[x][y] == 0) {
                    setSignAndCheck(x, y);
                    return;
                }
            }
        }

    }

}