package com.javarush.games.minesweeper;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private int countMinesOnField;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private int countFlags;
    private boolean isGameStopped = false;
    private int countClosedTiles = SIDE * SIDE;
    private int score;


    private GameObject[][] gameField = new GameObject[SIDE][SIDE];

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);
                setCellValue(x, y, "");
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }

    private void countMineNeighbors() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                GameObject gameObject = gameField[y][x];
                if (!gameObject.isMine) {
                    for (GameObject neighbor : getNeighbors(gameObject)) {
                        if (neighbor.isMine) {
                            gameObject.countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }
    private void openTile(int x, int y){
            if(isGameStopped){
                return;
            }
            if(gameField[y][x].isFlag){
                return;
            }
            if(gameField[y][x].isOpen){
                return;
            }
            if (gameField[y][x].isMine) {
                setCellValueEx(x, y, Color.RED, MINE);
                gameOver();
            }
            if(countMinesOnField == countClosedTiles - 1  && !gameField[y][x].isMine){
                win();
            }
            gameField[y][x].isOpen = true;
            countClosedTiles--;
            if(gameField[y][x].isOpen && !gameField[y][x].isMine){
                score += 5;
                setScore(score);
            }
            if (gameField[y][x].isMine) {
                setCellValue(x, y, MINE);
            } else if (!gameField[y][x].isMine && gameField[y][x].countMineNeighbors == 0) {
                setCellValue(x, y, "");
                List<GameObject> result = getNeighbors(gameField[y][x]);
                for (GameObject obj : result) {
                    if (!obj.isOpen) {
                        openTile(obj.x, obj.y);
                    }
                }
            } else if (!gameField[y][x].isMine && gameField[y][x].countMineNeighbors != 0) {
                setCellNumber(x, y, gameField[y][x].countMineNeighbors);
            } else {
                setCellNumber(x, y, gameField[y][x].countMineNeighbors);
            }
            setCellColor(x, y, Color.WHITE);

    }
    private void markTile(int x, int y){
        if(!isGameStopped) {
            if (!gameField[y][x].isOpen && countFlags != 0 && !gameField[y][x].isFlag) {
                gameField[y][x].isFlag = true;
                countFlags--;
                setCellValue(x, y, FLAG);
                setCellColor(x, y, Color.AQUAMARINE);
            } else if (gameField[y][x].isFlag && !gameField[y][x].isOpen) {
                gameField[y][x].isFlag = false;
                countFlags++;
                setCellValue(x, y, "");
                setCellColor(x, y, Color.ORANGE);
            }
        }
    }
    private  void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.RED, "ВЫ ПРОИГРАЛИ", Color.BLACK, 50);
    }

    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "ВЫ ВЫЙГРАЛИ", Color.BLACK, 50);
    }
    private void restart(){
        isGameStopped = false;
        countMinesOnField = 0;
        countFlags = 0;
        countClosedTiles = SIDE * SIDE;
        score = 0;
        setScore(score);
        createGame();
    }
    @Override
    public void onMouseLeftClick(int x, int y) {
        if(isGameStopped){
            restart();
            return;
        }
        openTile(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }
}
