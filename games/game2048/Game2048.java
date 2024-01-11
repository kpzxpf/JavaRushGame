package com.javarush.games.game2048;

import com.javarush.engine.cell.*;

public class Game2048 extends Game {
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }
     private void createGame(){
         gameField = new int[SIDE][SIDE];
         score = 0;
         setScore(score);
         createNewNumber();
         createNewNumber();
     }
    private void drawScene(){
        for(int y = 0; y < SIDE; y++){
            for(int x = 0; x < SIDE; x++){
                setCellColoredNumber(x, y, gameField[y][x]);
            }
        }
    }
    private void createNewNumber(){
        if(getMaxTileValue() == 2048){
            win();
        }
        while(true) {
            int x = getRandomNumber(SIDE);
            int y = getRandomNumber(SIDE);
            int num = getRandomNumber(10);
            if (gameField[y][x] == 0) {
                if (num == 9) {
                    gameField[y][x] = 4;
                }
                if (num != 9) {
                    gameField[y][x] = 2;
                }
                break;
            }else {
                continue;
            }
        }
    }
    private Color getColorByValue(int value){
        switch (value){
            case 0:
                return Color.WHITE;
            case 2:
                return Color.ORANGE;
            case 4:
                return Color.GREEN;
            case 8:
                return Color.ALICEBLUE;
            case 16:
                return Color.ANTIQUEWHITE;
            case 32:
                return Color.BEIGE;
            case 64:
                return Color.BLANCHEDALMOND;
            case 128:
                return Color.BLUE;
            case 256:
                return Color.CADETBLUE;
            case 512:
                return Color.CHOCOLATE;
            case 1024:
                return Color.RED;
            case 2048:
                return Color.GOLD;
            default:
                return Color.NONE;
        }
    }
    private void setCellColoredNumber(int x, int y, int value){
        Color color = getColorByValue(value);
        String str = value > 0 ? String.valueOf(value) : "";
        setCellValueEx(x, y, color, str);
    }
    private boolean compressRow(int[] row){
        int temp = 0;
        boolean result = false;
        for(int i = 0; i < row.length; i++){
            if(row[i] != 0){
                if(i != temp) {
                    row[temp] = row[i];
                    row[i] = 0;
                    result = true;
                }
                temp++;
            }
        }
        return result;
    }
    private boolean mergeRow(int[] row){
        boolean result = false;
        for(int i = 0; i < SIDE - 1; i++){
            if (row[i] != 0 && row[i] == row[i + 1]){
            row[i] += row[i + 1];
            row[i + 1] = 0;
            score += row[i];
            setScore(score);
            result = true;

            }
        }
        return result;
    }

    @Override
    public void onKeyPress(Key key) {
        if(isGameStopped){
            if(key == Key.SPACE){
                createGame();
                drawScene();
                isGameStopped = false;
            }
        }
        if (!canUserMove()){
            gameOver();
            return;
        }
        if(!isGameStopped){
            switch (key){
                case LEFT :
                    moveLeft();
                    break;
                case RIGHT:
                    moveRight();
                    break;
                case UP:
                    moveUp();
                    break;
                case DOWN:
                    moveDown();
            }
        }
    }

    private void moveUp() {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        drawScene();
    }

    private void moveRight() {
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        drawScene();
    }

    private void moveDown() {
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        drawScene();
    }

    private void moveLeft() {
        boolean isChange = false;
        for(int[] row : gameField){
            boolean isCompress = compressRow(row);
            boolean isMerge = mergeRow(row);
            if(isMerge){
                compressRow(row);
            }
            if(isCompress || isMerge){
                isChange = true;
            }
        }
        if(isChange){
            createNewNumber();
        }
        drawScene();
    }
    private  void  rotateClockwise(){
        int[][] arrayTemp = new int[SIDE][SIDE];
        for(int i =  SIDE - 1; i >= 0; i--){
            for(int j = SIDE - 1; j >= 0; j--){
                arrayTemp[j][SIDE - 1 - i] = gameField[i][j];
            }
        }
        gameField = arrayTemp;
    }
    private int getMaxTileValue(){
        int maxValue = 0;
        for(int i = 0; i < SIDE; i++){
            for(int j = 0; j < SIDE; j++){
                if(gameField[j][i] > maxValue){
                    maxValue = gameField[j][i];
                }
            }
        }
        return maxValue;
    }
    private boolean canUserMove() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                if (gameField[y][x] == 0) {
                    return true;
                } else if (y < SIDE - 1 && gameField[y][x] == gameField[y + 1][x]) {
                    return true;
                } else if ((x < SIDE - 1) && gameField[y][x] == gameField[y][x + 1]) {
                    return true;
                }
            }
        }
        return false;
    }
    private void gameOver(){
        showMessageDialog(Color.RED, "Потрачено", Color.BLACK, 60);
        isGameStopped = true;
    }
    private void win(){
        showMessageDialog(Color.GREEN, "Вы Выйграли", Color.BLACK, 60);
        isGameStopped = true;
    }
}
