package org.example;

import org.example.persons.Enemy;
import org.example.persons.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;

public class Game {

    public GameActions action = new GameActions();
    public Fight fight = new Fight();
    private final ArrayList<Result> results = new ArrayList<>();
    private final Enemy enemies[] = new Enemy[5];
    private final ExcelHandler excelHandler = new ExcelHandler();

    public Game() {
    }

    public void setEnemies() {
        enemies[0] = new Enemy("Kitana", 1, 100, 12);
        enemies[1] = new Enemy("Sub-Zero", 1, 60, 16);
        enemies[2] = new Enemy("Milina", 1, 70, 20);
        enemies[3] = new Enemy("Sonya Blade", 1, 80, 16);
        enemies[4] = new Enemy("Boss", 3, 100, 30);
        fight.location.setFullEnemiesList(enemies);
    }

    public Enemy[] getEnemies() {
        return this.enemies;
    }

    public Player newPlayer(Mediator mediator, Item[] items) {
        Player player = new Player(0, 80, 160);
        mediator.setHealthBar(player);
        mediator.setPlayerMaxHealthBar(player);
        player.setItems(items);
        return player;
    }

    public void endGameTop(Player player, JTextField text, JTable table) throws IOException, URISyntaxException {
        results.add(new Result(text.getText(), player.getPoints()));
        results.sort(Comparator.comparing(Result::getPoints).reversed());
        writeToTable(table);
        excelHandler.writeToExcel(results);
    }

    public ArrayList<Result> getResults() {
        return this.results;
    }

    public void loadResultsFromExcel() throws IOException, URISyntaxException {
        results.addAll(excelHandler.readFromExcel());
    }

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
