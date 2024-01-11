package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;


public class MoonLanderGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private Rocket rocket;
    private GameObject landscape;
    private  boolean isUpPressed;
    private  boolean isLeftPressed;
    private  boolean isRightPressed;
    private GameObject platform;
    private boolean isGameStopped;
    private int score;


    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    private void createGame(){
        score = 1000;
        isUpPressed = false;
        isLeftPressed = false;
        isRightPressed = false;
        isGameStopped = false;
        setTurnTimer(50);
        createGameObjects();
        drawScene();
    }

    @Override
    public void onTurn(int step) {
        if(score > 0){
            score--;
        }
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
        setScore(score);
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if(key == Key.UP){
            isUpPressed = true;
        }
        if(key == Key.LEFT){
            isLeftPressed = true;
            isRightPressed = false;
        }
        if(key == Key.RIGHT){
            isLeftPressed = false;
            isRightPressed = true;
        }
        if(key == Key.SPACE && isGameStopped){
            createGame();
        }
    }

    @Override
    public void onKeyReleased(Key key) {
        if(key == Key.UP){
            isUpPressed = false;
        }
        if(key == Key.LEFT){
            isLeftPressed = false;
        }
        if(key == Key.RIGHT){
            isRightPressed = false;
        }
        if(key == Key.SPACE && isGameStopped){
            createGame();
        }
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x < ShapeMatrix.LANDSCAPE[0].length && x >= 0 && y < ShapeMatrix.LANDSCAPE[0].length && y >= 0){
            super.setCellColor(x, y, color);
        }
    }

    private void createGameObjects(){
        rocket = new Rocket(WIDTH / 2, 0);
        platform = new GameObject(23, MoonLanderGame.HEIGHT - 1, ShapeMatrix.PLATFORM);
        landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
    }
    private void drawScene(){
        for (int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++){
                setCellColor(j, i, Color.DARKBLUE);
            }
        }
        rocket.draw(this);
        landscape.draw(this);
    }
    private void check(){
        if(rocket.isCollision(platform) && rocket.isStopped()){
            win();
        } else if (rocket.isCollision(landscape)) {
            gameOver();
        }
    }
    private void win(){
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "ПОБЕДА", Color.BLACK, 65);
        stopTurnTimer();
    }
    private void gameOver(){
        rocket.crash();
        isGameStopped = true;
        showMessageDialog(Color.RED, "ПОРАЖЕНИЕ", Color.BLACK, 65);
        stopTurnTimer();
        score = 0;
    }
}
