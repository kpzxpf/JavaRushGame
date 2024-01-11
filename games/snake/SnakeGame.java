package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    private void createGame(){
        isGameStopped = false;
        score = 0;
        setScore(score);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);

    }

    private void drawScene(){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++){
                setCellValueEx(x, y, Color.BURLYWOOD, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if(!apple.isAlive){
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if(!snake.isAlive){
            gameOver();
        }
        if(snake.getLength() > GOAL){
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key){
            case UP :
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case SPACE:
                if(isGameStopped){
                    createGame();
                }
        }
    }
    private void createNewApple(){
        while (true){
            int rndX = getRandomNumber(WIDTH);
            int rndY = getRandomNumber(HEIGHT);
            if(!snake.checkCollision(new GameObject(rndX, rndY))){
                apple = new Apple(rndX, rndY);
                break;
            }
        }
    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "Вы проиграли", Color.BLACK, 65);
    }
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "Вы выйграли", Color.BLACK, 65);
    }
}
