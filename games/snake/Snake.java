package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;


public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private Direction direction = Direction.LEFT;
    public boolean isAlive = true;

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
            Snake(int x, int y){
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }
    public void draw(Game game){
        for(GameObject gameObject : snakeParts){
            game.setCellValueEx(gameObject.x, gameObject.y,Color.NONE,
                    snakeParts.indexOf(gameObject) == 0 ? HEAD_SIGN : BODY_SIGN, !isAlive? Color.RED : Color.GREEN, 75);
        }
    }
    public void setDirection(Direction direction) {
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }

        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        } else if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        }

        this.direction = direction;
    }

    public void move(Apple apple){
        GameObject gameObject = createNewHead();
        if(gameObject.x < 0 || gameObject.x > 14 || gameObject.y < 0 || gameObject.y > 14){
            isAlive = false;
            return;
        }
        if(checkCollision(gameObject)){
            isAlive = false;
            return;
        }
        snakeParts.add(0,gameObject);
        if (gameObject.x == apple.x && gameObject.y == apple.y){
            apple.isAlive = false;
            return;
        }
        removeTail();
    }
    public GameObject createNewHead(){
        GameObject gameObject = snakeParts.get(0);
        switch (direction){
            case UP :
                return new GameObject(gameObject.x, gameObject.y - 1);
            case DOWN:
                return new GameObject(gameObject.x, gameObject.y + 1);
            case RIGHT:
                return new GameObject(gameObject.x + 1, gameObject.y);
            case LEFT:
                return new GameObject(gameObject.x - 1, gameObject.y);
            default:
                return null;
        }
    }
    public void removeTail(){
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject head){
        for(GameObject gameObject : snakeParts){
            if(gameObject.x == head.x && gameObject.y == head.y){
                return true;
            }
        }
        return false;
    }
    public int getLength(){
        return snakeParts.size();
    }
}
