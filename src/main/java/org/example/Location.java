package org.example;
import org.example.persons.Enemy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Location {
   // private JFrame fightFrame;
    public int currentLocation = 1;
    public int currentEnemyNumber = 0;
    ArrayList<Enemy> currentEnemiesList = new ArrayList<>();
    Enemy[] fullEnemiesList = null;
    public int locationSize;

    public void setFullEnemiesList(Enemy[] list) {
        fullEnemiesList = list;
    }

    public ArrayList<Enemy> getEnemiesAtLocation() {
        return currentEnemiesList;
    }

    public void setEnemiesAtLocation(int i) {
        currentEnemiesList = new ArrayList<>();
        Enemy enemy = null;
        Random random = new Random();
        locationSize = 1 +  random.nextInt(3);
        for (int j = 0; j < locationSize; j++) {
            int k = (int) (Math.random() * 4);
            switch (k) {
                case 0 -> {
                    enemy = fullEnemiesList[0];
                    enemy.setIcon("baraka.gif");
                }
                case 1 -> {
                    enemy = fullEnemiesList[1];
                    enemy.setIcon("liu_kang.gif");
                }
                case 2 -> {
                    enemy = fullEnemiesList[2];
                    enemy.setIcon("sub_zero.gif");
                }
                case 3 -> {
                    enemy = fullEnemiesList[3];
                    enemy.setIcon("sonya_blade.gif");
                }
            }
            currentEnemiesList.add(enemy);
        }
    }

    public void resetLocation(boolean a, int i) {
        if (a) {
            currentLocation += 1 ;
            currentEnemyNumber = 0;
            setEnemiesAtLocation(i);
            changeBackgroundColor();
        }
        else {
            currentLocation = 1;
            currentEnemyNumber = 0;
            setEnemiesAtLocation(0);
            //System.out.println("77777");
        }
    }
    private void changeBackgroundColor() {
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.BLACK, Color.CYAN, Color.MAGENTA, Color.YELLOW};
        Color currentColor = GUI.fightFrame.getContentPane().getBackground();
        Color randomColor;
        do {
            randomColor = colors[(int) (Math.random() * colors.length)];
        } while (randomColor.equals(currentColor));
        GUI.fightFrame.getContentPane().setBackground(randomColor);
    }


    public int getCurrentLocation() {
        return currentLocation;
    }

    public int getCurrentEnemyNumber() {
        return currentEnemyNumber;
    }

    public Enemy getCurrentEnemy() {
        Enemy enemy = null;
        if (currentEnemyNumber != locationSize) {
            currentEnemyNumber += 1;
            return currentEnemiesList.get(currentEnemyNumber - 1);
        } else {
            currentEnemyNumber = 0;
            enemy = fullEnemiesList[4];
            enemy.setIcon("boss.gif");
            return enemy;
        }
    }
}
