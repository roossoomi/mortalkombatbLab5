package org.example;

import org.example.actions.FightAction;
import org.example.persons.Enemy;
import org.example.persons.Fighter;
import org.example.persons.Player;

import javax.swing.*;

public class Mediator {

    private final JLabel enemyHealthLabel;
    private final JLabel playerHealthLabel;
    private final JLabel pointsValueLabel;
    private final JLabel experienceValueLabel;
    private final JLabel playerLevelLabel;
    private final JLabel enemyLevelLabel;
    private final JLabel playerDamageValueLabel;
    private final JLabel endRoundLabel;
    private final JLabel enemyDebuffLabel;
    private final JLabel victoryLabel;
    private final JLabel endGameWithoutLadderTitlleLabel;
    private final JLabel playerActionLabel;
    private final JLabel playerDebuffLabel;
    private final JLabel enemyActionLabel;
    private final JLabel GIFLabel;
    
    private final JProgressBar playerHealthBar;
    private final JProgressBar enemyHealthBar;

    private final JDialog endFightDialog;
    private final JDialog endGameDialog;
    private final JDialog endGameWithoutLadderDialog;
    private final JDialog cantUseItemDialog;
    private final JDialog itemsBagDialog;

    private final JFrame fightFrame;

    private final JRadioButton firstItemButton;
    private final JRadioButton secondItemButton;
    private final JLabel thirdItemButton;

    private final JTextField enterNameField;

    private final JTable recordsTable;

    public Mediator(JLabel enemyHealthLabel, JLabel playerHealthLabel, JDialog endFightDialog, JLabel pointsValueLabel, JLabel experienceValueLabel,
            JLabel playerLevelLabel, JLabel enemyLevelLabel, JLabel playerDamageValueLabel, JLabel endRoundLabel, JProgressBar playerHealthBar,
            JProgressBar enemyHealthBar, JDialog endGameDialog, JDialog endGameWithoutLadderDialog, JFrame fightFrame, JLabel enemyDebuffLabel,
            JLabel victoryLabel, JLabel endGameWithoutLadderTitlleLabel, JLabel playerActionLabel, JLabel playerDebuffLabel, JLabel enemyActionLabel,
            JRadioButton firstItemButton, JRadioButton secondItemButton, JLabel thirdItemButton, JTextField enterNameField, JTable recordsTable,
            JDialog cantUseItemDialog, JDialog itemsBagDialog, JLabel GIFLabel) {

        this.enemyHealthLabel = enemyHealthLabel;
        this.playerHealthLabel = playerHealthLabel;
        this.endFightDialog = endFightDialog;
        this.pointsValueLabel = pointsValueLabel;
        this.experienceValueLabel = experienceValueLabel;
        this.playerLevelLabel = playerLevelLabel;
        this.enemyLevelLabel = enemyLevelLabel;
        this.playerDamageValueLabel = playerDamageValueLabel;
        this.endRoundLabel = endRoundLabel;
        this.playerHealthBar = playerHealthBar;
        this.enemyHealthBar = enemyHealthBar;
        this.endGameDialog = endGameDialog;
        this.endGameWithoutLadderDialog = endGameWithoutLadderDialog;
        this.fightFrame = fightFrame;
        this.enemyDebuffLabel = enemyDebuffLabel;
        this.victoryLabel = victoryLabel;
        this.endGameWithoutLadderTitlleLabel = endGameWithoutLadderTitlleLabel;
        this.playerActionLabel = playerActionLabel;
        this.playerDebuffLabel = playerDebuffLabel;
        this.enemyActionLabel = enemyActionLabel;
        this.firstItemButton = firstItemButton;
        this.secondItemButton = secondItemButton;
        this.thirdItemButton = thirdItemButton;
        this.enterNameField = enterNameField;
        this.recordsTable = recordsTable;
        this.cantUseItemDialog = cantUseItemDialog;
        this.itemsBagDialog = itemsBagDialog;
        this.GIFLabel = GIFLabel;
    }

    public void setActionLabels(Fighter enemy, Fighter player, FightAction enemyAction, FightAction playerAction) {
        playerActionLabel.setText(player.getName() + " uses " + playerAction.getType());
        enemyActionLabel.setText(enemy.getName() + " use " + enemyAction.getType());
    }

    public void setDebuffLabel(Fighter player, boolean a) {
        if (a) {
            switch (player.getName()) {
                case "You" ->
                    playerDebuffLabel.setText(player.getName() + " are debuffed for " + player.getMovesWithDebuff()+ " turns");
                default ->
                    enemyDebuffLabel.setText(player.getName() + " is debuffed for " + player.getMovesWithDebuff()+ " turns");
            }
        } else {
            switch (player.getName()) {
                case "You" ->
                    playerDebuffLabel.setText("");
                default ->
                    enemyDebuffLabel.setText("");
            }
        }
    }

    public void setHealthBar(Fighter player) {
        switch (player.getName()) {
            case "You" -> {
                if (player.getHealth() >= 0) {
                    playerHealthBar.setValue(player.getHealth());
                } else {
                    playerHealthBar.setValue(0);
                }
            }
            default -> {
                if (player.getHealth() >= 0) {
                    enemyHealthBar.setValue(player.getHealth());
                } else {
                    enemyHealthBar.setValue(0);
                }
            }
        }
    }
    public void setPlayerMaxHealthBar(Player player){
        playerHealthBar.setMaximum(player.getMaxHealth());
    }
    public void setEnemyMaxHealthBar(Enemy enemy){
        enemyHealthBar.setMaximum(enemy.getMaxHealth());
    }

    public void revive(Player player, Item[] items) {
        playerHealthLabel.setText(player.getHealth() + "/" + player.getMaxHealth());
        thirdItemButton.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
        playerActionLabel.setText("Вы воскресли");
        //System.out.println("Воскресли");
    }

    public void gameEnding(String text, boolean a) {
        if (a) {
            endGameDialog.setVisible(true);
            endGameDialog.setBounds(150, 150, 600, 500);
            victoryLabel.setText(text);
        } else {
            endGameWithoutLadderDialog.setVisible(true);
            endGameWithoutLadderDialog.setBounds(150, 150, 600, 500);
            endGameWithoutLadderTitlleLabel.setText(text);
        }
        fightFrame.dispose();
    }

    public void setEndFightDialog() {
        endFightDialog.setVisible(true);
        endFightDialog.setBounds(300, 150, 700, 600);
    }

    public void setRoundEndText(String text) {
        endRoundLabel.setText(text);
    }
    
    public void openCantUseItemDialog(){
        cantUseItemDialog.setVisible(true);
        cantUseItemDialog.setBounds(300, 200, 400, 300);
    }
    public void setBagText( Item[] items){
        firstItemButton.setText(items[0].getName()+", "+items[0].getCount()+" шт");
        secondItemButton.setText(items[1].getName()+", "+items[1].getCount()+" шт");
        thirdItemButton.setText(items[2].getName()+", "+items[2].getCount()+" шт");
    }
    public void setRoundTexts(Player player, Enemy enemy) {
        if (enemy.getHealth() >= 0) {
            enemyHealthLabel.setText((enemy.getHealth()) + "/" + (enemy.getMaxHealth()));
        } else {
            enemyHealthLabel.setText("0/" + (enemy.getMaxHealth()));
        }
        if (player.getHealth() >= 0) {
            playerHealthLabel.setText((player.getHealth()) + "/" + (player.getMaxHealth()));
        } else {
            playerHealthLabel.setText("0/" + (player.getMaxHealth()));
        }
    }
    public void setNewRoundTexts(Player player, Enemy enemy, Item[] items){
        playerActionLabel.setText("");
        enemyActionLabel.setText("");
        pointsValueLabel.setText(Integer.toString(player.getPoints()));
        experienceValueLabel.setText(Integer.toString(player.getExperience()) + "/" + player.getNextLevelExperience());
        playerLevelLabel.setText(Integer.toString((player.getLevel())+1) + " level");
        enemyLevelLabel.setText(Integer.toString(enemy.getLevel()) + " level");
        playerHealthLabel.setText(Integer.toString(player.getMaxHealth()) + "/" + Integer.toString(player.getMaxHealth()));
        enemyHealthLabel.setText(Integer.toString(enemy.getMaxHealth()) + "/" + Integer.toString(enemy.getMaxHealth()));
        playerDamageValueLabel.setText(Integer.toString(player.getDamage()));
        setBagText(items);
    }
    
}
