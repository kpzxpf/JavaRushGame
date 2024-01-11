package com.javarush.games.minigames.mini05;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Color;

/* 
Цвета радуги
*/

public class RainbowGame extends Game {

    @Override
    public void initialize() {
        setScreenSize(10,7);
        for(int i = 0; i < 10; i++){
            setCellColor(i,0,Color.RED);
        }
        for(int i = 0; i < 10; i++){
            setCellColor(i,1,Color.ORANGE);
        }
        for(int i = 0; i < 10; i++){
            setCellColor(i,2,Color.YELLOW);
        }
        for(int i = 0; i < 10; i++){
            setCellColor(i,3,Color.GREEN);
        }
        for(int i = 0; i < 10; i++){
            setCellColor(i,4,Color.BLUE);
        }
        for(int i = 0; i < 10; i++){
            setCellColor(i,5,Color.INDIGO);
        }
        for(int i = 0; i < 10; i++){
            setCellColor(i,6,Color.VIOLET);
        }
    }
}
