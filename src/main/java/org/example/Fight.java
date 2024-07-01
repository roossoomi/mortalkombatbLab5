package org.example;

import org.example.actions.*;
import org.example.persons.*;

import java.util.ArrayList;

/**
 * Класс Fight представляет собой логику боя между игроком (Player) и врагом (Enemy).
 * Он управляет ходом игры, действиями игрока и врага, проверкой состояний и завершением раундов.
 */
public class Fight {

    private Mediator mediator;
    private Player player;
    private Enemy enemy;
    public Location location = new Location();
    public ArrayList<FightAction> actionsList = new ArrayList<>() {
        {
            add(new Hit());
            add(new Block());
            add(new Debuff());
            add(new Heal());
        }
    };

    /**
     * Устанавливает объект посредника (Mediator) для взаимодействия с графическим интерфейсом.
     *
     * @param mediator объект посредника
     */
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Устанавливает игрока (Player) для текущего боя.
     *
     * @param player объект игрока
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Устанавливает врага (Enemy) для текущего боя.
     *
     * @param enemy объект врага
     */
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    /**
     * Возвращает текущего игрока.
     *
     * @return объект игрока
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Возвращает текущего врага.
     *
     * @return объект врага
     */
    public Enemy getEnemy() {
        return this.enemy;
    }

    /**
     * Выполняет действие игрока в бою.
     *
     * @param enemyAction действие врага
     * @param playerAction действие игрока
     */
    public void playerMove(FightAction enemyAction, FightAction playerAction) {
        mediator.setActionLabels(enemy, player, enemyAction, playerAction);
        playerAction.realisation(player, enemy, enemyAction.getType());
    }

    /**
     * Выполняет действие врага в бою.
     *
     * @param enemyAction действие врага
     * @param playerAction действие игрока
     */
    public void enemyMove(FightAction enemyAction, FightAction playerAction) {
        mediator.setActionLabels(player, enemy, enemyAction, playerAction);
        playerAction.realisation(enemy, player, enemyAction.getType());
    }

    /**
     * Проверяет наличие дебаффов у игрока и врага, управляет их состоянием.
     */
    public void checkDebuff() {
        if (!enemy.isDebuffed()) {
            mediator.setDebuffLabel(enemy, false);
        }
        if (enemy.isDebuffed()) {
            mediator.setDebuffLabel(enemy, true);
            enemy.loseDebuffTurn();
        }
        if (!player.isDebuffed()) {
            mediator.setDebuffLabel(player, false);
        }
        if (player.isDebuffed()) {
            mediator.setDebuffLabel(player, true);
            player.loseDebuffTurn();
        }
    }

    /**
     * Выполняет действие удара (Hit) игроком в зависимости от выбранного действия.
     *
     * @param actionIndex индекс выбранного действия (0 - блок, 1 - удар, 2 - дебафф)
     * @param results список результатов для обновления
     * @param locationsNumber номер текущей локации
     * @param enemiesList массив врагов
     */
    public void hit(int actionIndex, ArrayList<Result> results, int locationsNumber, Enemy[] enemiesList) {
        GameActions action = new GameActions();
        FightAction enemyAction = action.chooseEnemyAction(enemy, new ArrayList<>(actionsList));
        switch (actionIndex) {
            case 0:
                playerMove(enemyAction, actionsList.get(1));
                if (enemy.getHealth() > 0) {
                    enemyMove(actionsList.get(1), enemyAction);
                }
                break;
            case 1:
                playerMove(enemyAction, actionsList.get(0));
                if (enemy.getHealth() > 0) {
                    enemyMove(actionsList.get(0), enemyAction);
                }
                break;
            case 2:
                playerMove(enemyAction, actionsList.get(2));
                if (enemy.getHealth() > 0) {
                    enemyMove(actionsList.get(2), enemyAction);
                }
                break;
            default:
                break;
        }
        mediator.setRoundTexts(player, enemy);
        checkDebuff();
        mediator.setHealthBar(player);
        mediator.setHealthBar(enemy);
        checkDeath(results, locationsNumber, enemiesList);
    }

    /**
     * Проверяет условия окончания боя (смерть игрока или врага) и завершает раунд соответствующим образом.
     *
     * @param results список результатов
     * @param locationsNumber номер текущей локации
     * @param enemiesList массив врагов
     */
    public void checkDeath(ArrayList<Result> results, int locationsNumber, Enemy[] enemiesList) {
        if (player.getHealth() <= 0 && player.getItems()[2].getCount() > 0) {
            player.setHealth((int) (player.getMaxHealth() * 0.05));
            player.getItems()[2].setCount(player.getItems()[2].getCount() - 1);
            mediator.setHealthBar(player);
            mediator.revive(player, player.getItems());
            System.out.println("Воскресли");
        }
        if (player.getHealth() <= 0 || enemy.getHealth() <= 0) {
            if (location.getCurrentLocation() == locationsNumber && "Boss".equals(enemy.getName())) {
                location.resetLocation(false, 1);
                endFinalRound(results, enemiesList);
            } else {
                endRound(results, enemiesList);
            }
        }
    }

    /**
     * Завершает раунд боя, обрабатывает результаты и переходит к следующему раунду или локации.
     *
     * @param results список результатов
     * @param enemiesList массив врагов
     */
    public void endRound(ArrayList<Result> results, Enemy[] enemiesList) {
        GameActions action = new GameActions();
        mediator.setEndFightDialog();
        if (player.getHealth() > 0) {
            mediator.setRoundEndText("Вы выиграли!");
            mediator.setGIF(true);
            if ("Boss".equals(enemy.getName())) {
                action.addItems(40, 30, 10, player.getItems());
                action.addPointsBoss(player);
                location.resetLocation(true, player.getLevel());
            } else {
                action.addItems(25, 15, 5, player.getItems());
                action.addPoints(player);
            }
        } else {
            mediator.setRoundEndText(enemy.getName() + " обыграл");
            endFinalRound(results, enemiesList);
            mediator.setGIF(false);
        }
    }

    /**
     * Сбрасывает состояние игры и перезапускает новую игру.
     *
     * @param enemiesList массив врагов
     */
    public void reset(Enemy[] enemiesList) {
        GameActions action = new GameActions();
        if (player == null) {
            player = new Player(0, 80, 160);
        }
        player.setDamage(16);
        player.setHealth(80);
        player.setMaxHealth(80);
        action.resetEnemies(enemiesList);
        player.setLevel(0);
        player.setPoints(0);
        player.setExperience(0);
        player.setNextLevelExperience(40);
        location.setFullEnemiesList(enemiesList);
        location.resetLocation(false, player.getLevel());
    }

    /**
     * Создает массив врагов для текущей игры.
     *
     * @return массив врагов
     */
    public Enemy[] setEnemies() {
        Enemy[] enemies = new Enemy[5];
        enemies[0] = new Enemy("Kitana", 1, 100, 12);
        enemies[1] = new Enemy("Sub Zero", 1, 60, 16);
        enemies[2] = new Enemy("Milina", 1, 70, 20);
        enemies[3] = new Enemy("Sonya Blade", 1, 80, 16);
        enemies[4] = new Enemy("Boss", 3, 100, 30);
        return enemies;
    }

    /**
     * Завершает финальный раунд игры, обновляет результаты и отображает финальное окно.
     *
     * @param results список результатов
     * @param enemiesList массив врагов
     */
    public void endFinalRound(ArrayList<Result> results, Enemy[] enemiesList) {
        GameActions action = new GameActions();
        action.resetEnemies(enemiesList);
        String text = "Победа не на вашей стороне";
        if (player.getHealth() > 0) {
            action.addPoints(player);
            text = "Победа на вашей стороне";
            MusicDialog dialog = new MusicDialog(null, "Победа", true);
            dialog.setVisible(true);
        }
        boolean top = false;
        if (results == null || results.isEmpty()) {
            top = true;
        } else {
            int a = 0;
            for (Result result : results) {
                if (player.getPoints() < result.getPoints()) {
                    a++;
                }
            }
            if (a < 10) {
                top = true;
            }
        }
        mediator.gameEnding(text, top);
    }

    /**
     * Начинает новый раунд боя, восстанавливает здоровье игрока и врага до максимального значения.
     */
    public void newRound() {
        mediator.setPlayerMaxHealthBar(player);
        mediator.setEnemyMaxHealthBar(enemy);
        player.setHealth(player.getMaxHealth());
        enemy.setHealth(enemy.getMaxHealth());
        mediator.setHealthBar(player);
        mediator.setHealthBar(enemy);
    }
}
