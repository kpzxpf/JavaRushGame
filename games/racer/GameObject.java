package com.javarush.games.racer;

import com.javarush.engine.cell.*;

public class GameObject {
    public int x;
    public int y;
    public int[][] matrix;
    public int width;
    public int height;
    public GameObject(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean isCollisionPossible(GameObject otherGameObject) {
        if (x > otherGameObject.x + otherGameObject.width || x + width < otherGameObject.x) {
            return false;
        }

        if (y > otherGameObject.y + otherGameObject.height || y + height < otherGameObject.y) {
            return false;
        }
        return true;
    }

    public boolean isCollision(GameObject gameObject) {
        if (!isCollisionPossible(gameObject)) {
            return false;
        }

        for (int carX = 0; carX < gameObject.width; carX++) {
            for (int carY = 0; carY < gameObject.height; carY++) {
                if (gameObject.matrix[carY][carX] != 0) {
                    if (isCollision(carX + gameObject.x, carY + gameObject.y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCollision(int x, int y) {
        for (int matrixX = 0; matrixX < width; matrixX++) {
            for (int matrixY = 0; matrixY < height; matrixY++) {
                if (matrix[matrixY][matrixX] != 0 && matrixX + this.x == x && matrixY + this.y == y) {
                    return true;
                }
            }
        }
        return false;
    }

    public GameObject(int x, int y, int[][] matrix){
        this.x = x;
        this.y = y;
        this.matrix = matrix;
        width = matrix[0].length;
        height = matrix.length;
    }
    public void draw(Game game){

        for (int y = 0; y < matrix.length; y++){
            for (int x = 0; x < matrix[0].length; x++){
                game.setCellColor(x + this.x, y + this.y, Color.values()[matrix[y][x]]);
            }
        }
    }
}
