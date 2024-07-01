package org.example;

import org.example.persons.Enemy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Класс, представляющий локацию игры с врагами.
 */
public class Location {

    public int currentLocation = 1;
    public int currentEnemyNumber = 0;
    ArrayList<Enemy> currentEnemiesList = new ArrayList<>();
    Enemy[] fullEnemiesList = null;
    public int locationSize;

    /**
     * Устанавливает полный список врагов для локации.
     *
     * @param list массив всех доступных врагов
     */
    public void setFullEnemiesList(Enemy[] list) {
        fullEnemiesList = list;
    }

    /**
     * Возвращает список текущих врагов в локации.
     *
     * @return список текущих врагов
     */
    public ArrayList<Enemy> getEnemiesAtLocation() {
        return currentEnemiesList;
    }

    /**
     * Устанавливает врагов в текущей локации.
     *
     * @param i индекс текущей локации
     */
    public void setEnemiesAtLocation(int i) {
        currentEnemiesList = new ArrayList<>();
        Enemy enemy = null;
        Random random = new Random();
        locationSize = 1 + random.nextInt(3);
        for (int j = 0; j < locationSize; j++) {
            int k = random.nextInt(4);
            switch (k) {
                case 0 -> {
                    enemy = fullEnemiesList[0];
                    enemy.setIcon("kitana.gif");
                }
                case 1 -> {
                    enemy = fullEnemiesList[1];
                    enemy.setIcon("milina.gif");
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

    /**
     * Сбрасывает текущую локацию после победы над врагами или поражения.
     *
     * @param a флаг, указывающий на успешное завершение битвы
     * @param i индекс текущей локации
     */
    public void resetLocation(boolean a, int i) {
        if (a) {
            currentLocation++;
            currentEnemyNumber = 0;
            setEnemiesAtLocation(i);
            changeBackgroundColor();
        } else {
            currentLocation = 1;
            currentEnemyNumber = 0;
            setEnemiesAtLocation(0);
        }
    }

    /**
     * Меняет фоновый цвет окна битвы на случайный цвет из списка.
     */
    private void changeBackgroundColor() {
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.BLACK, Color.CYAN, Color.MAGENTA, Color.YELLOW};
        Color currentColor = GUI.fightFrame.getContentPane().getBackground();
        Color randomColor;
        do {
            randomColor = colors[new Random().nextInt(colors.length)];
        } while (randomColor.equals(currentColor));
        GUI.fightFrame.getContentPane().setBackground(randomColor);
    }

    /**
     * Возвращает текущий номер локации.
     *
     * @return текущий номер локации
     */
    public int getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Возвращает текущий номер врага в локации.
     *
     * @return текущий номер врага
     */
    public int getCurrentEnemyNumber() {
        return currentEnemyNumber;
    }

    /**
     * Возвращает текущего врага в локации. Если враги закончились, возвращает босса.
     *
     * @return текущий враг или босс, если враги закончились
     */
    public Enemy getCurrentEnemy() {
        Enemy enemy = null;
        if (currentEnemyNumber != locationSize) {
            currentEnemyNumber++;
            return currentEnemiesList.get(currentEnemyNumber - 1);
        } else {
            currentEnemyNumber = 0;
            enemy = fullEnemiesList[4];
            enemy.setIcon("boss.gif");
            return enemy;
        }
    }
}
