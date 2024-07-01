package org.example;

import org.example.persons.Enemy;
import org.example.persons.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Класс, представляющий игровую логику и управление игрой.
 */
public class Game {

    /**
     * Объект для выполнения игровых действий.
     */
    public GameActions action = new GameActions();

    /**
     * Объект для управления битвами между персонажами.
     */
    public Fight fight = new Fight();

    /**
     * Список результатов игры.
     */
    private final ArrayList<Result> results = new ArrayList<>();

    /**
     * Массив врагов в текущей игре.
     */
    private final Enemy enemies[] = new Enemy[5];

    /**
     * Объект для работы с Excel-файлами.
     */
    private final ExcelHandler excelHandler = new ExcelHandler();

    /**
     * Конструктор класса Game.
     *
     * @throws IOException если происходит ошибка ввода-вывода при создании объекта ExcelHandler
     */
    public Game() throws IOException {
    }

    /**
     * Устанавливает список врагов для текущей игры.
     */
    public void setEnemies() {
        enemies[0] = new Enemy("Kitana", 1, 100, 12);
        enemies[1] = new Enemy("Sub-Zero", 1, 60, 16);
        enemies[2] = new Enemy("Milina", 1, 70, 20);
        enemies[3] = new Enemy("Sonya Blade", 1, 80, 16);
        enemies[4] = new Enemy("Boss", 3, 100, 30);
        fight.location.setFullEnemiesList(enemies);
    }

    /**
     * Возвращает массив врагов текущей игры.
     *
     * @return массив врагов
     */
    public Enemy[] getEnemies() {
        return this.enemies;
    }

    /**
     * Создает нового игрока в зависимости от выбранного режима.
     *
     * @param mediator посредник для установки связей с интерфейсом
     * @param items    массив предметов игрока
     * @param mode     режим создания игрока (1 - обычный, 2 - хилое здоровье, 3 - смертельный удар)
     * @return новый объект игрока
     */
    public Player newPlayer(Mediator mediator, Item[] items, int mode) {
        Player player = null;
        switch (mode) {
            case 1:
                player = new Player(0, 80, 16);
                break;
            case 2:
                player = new Player(0, 1, 16);
                break;
            case 3:
                player = new Player(0, 1000, 200);
                break;
        }

        if (player != null) {
            mediator.setHealthBar(player);
            mediator.setPlayerMaxHealthBar(player);
            player.setItems(items);
        }

        return player;
    }

    /**
     * Завершает игру и добавляет результат игрока в таблицу результатов.
     *
     * @param player игрок, чьи результаты нужно добавить
     * @param text   текстовое поле с именем игрока
     * @param table  таблица для отображения результатов
     */
    public void endGameTop(Player player, JTextField text, JTable table) throws IOException, URISyntaxException {
        results.add(new Result(text.getText(), player.getPoints()));
        results.sort(Comparator.comparing(Result::getPoints).reversed());
        writeToTable(table);
        excelHandler.writeToExcel(results);
    }

    /**
     * Возвращает список результатов текущей игры.
     */
    public ArrayList<Result> getResults() {
        return this.results;
    }

    /**
     * Загружает результаты игры из Excel-файла.
     */
    public void loadResultsFromExcel() throws IOException, URISyntaxException {
        results.addAll(excelHandler.readFromExcel());
    }

    /**
     * Записывает результаты игры в указанную таблицу.
     *
     * @param table таблица для отображения результатов
     */
    public void writeToTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < results.size(); i++) {
            if (i < 10) {
                model.setValueAt(results.get(i).getName(), i, 0);
                model.setValueAt(results.get(i).getPoints(), i, 1);
            }
        }
    }
}
