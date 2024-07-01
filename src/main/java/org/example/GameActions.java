package org.example;

import org.example.actions.FightAction;
import org.example.persons.Enemy;
import org.example.persons.Player;

import java.util.ArrayList;

/**
 * Класс, содержащий игровые действия и логику игры.
 */
public class GameActions {

    private final int experience_for_next_level[] = {40, 90, 180, 260, 410, 1000};

    /**
     * Выбирает действие противника на основе его имени.
     *
     * @param enemy враг, для которого выбирается действие
     * @param list  список доступных действий
     * @return выбранное действие противника
     */
    public FightAction chooseEnemyAction(Enemy enemy, ArrayList<FightAction> list) {
        switch (enemy.getName()) {
            case "Sub-Zero" -> {
                return list.get((int) (Math.random() * 3));
            }
            case "Boss" -> {
                list.remove(2); // Убираем действие "Debuff" для босса
                return list.get((int) (Math.random() * 3));
            }
            default -> {
                return list.get((int) (Math.random() * 2));
            }
        }
    }

    /**
     * Добавляет опыт и очки игроку после победы в битве.
     *
     * @param player игрок, которому добавляются очки и опыт
     */
    public void addPoints(Player player) {
        int additionalExperience = 20 + 5 * player.getLevel();
        int additionalPoints = 20 + 5 * player.getLevel() + player.getHealth() / 4;

        player.setExperience(player.getExperience() + additionalExperience);
        player.setPoints(player.getPoints() + additionalPoints);
    }

    /**
     * Проверяет, достиг ли игрок нужного количества опыта для перехода на следующий уровень.
     *
     * @param player игрок, у которого проверяется опыт
     * @return true, если игрок достиг нужного опыта для уровня, иначе false
     */
    public boolean checkExperience(Player player) {
        return player.getExperience() >= player.getNextLevelExperience();
    }

    /**
     * Повышает уровень игрока и обновляет параметры всех врагов.
     *
     * @param player  игрок, которому повышается уровень
     * @param enemies массив врагов, чьи параметры обновляются
     */
    public void levelUp(Player player, Enemy[] enemies) {
        player.setLevel(player.getLevel() + 1);
        int i = 0;
        while (player.getNextLevelExperience() >= experience_for_next_level[i]) {
            i++;
        }
        player.setNextLevelExperience(experience_for_next_level[i]);
        for (Enemy enemy : enemies) {
            newHealthEnemy(enemy, player);
        }
    }

    /**
     * Добавляет опыт и очки игроку после победы над боссом.
     *
     * @param player игрок, которому добавляются очки и опыт
     */
    public void addPointsBoss(Player player) {
        player.setExperience(50);
        player.setPoints(65 + player.getHealth() / 2);
    }

    /**
     * Добавляет случайно выбранный предмет игроку на основе вероятности.
     *
     * @param k1    вероятность получения первого предмета в процентах
     * @param k2    вероятность получения второго предмета в процентах
     * @param k3    вероятность получения третьего предмета в процентах
     * @param items массив предметов, к которым может быть применена операция
     */
    public void addItems(int k1, int k2, int k3, Item[] items) {
        double i = Math.random();
        if (i < k1 * 0.01) {
            items[0].setCount(1);
        }
        if (i >= k1 * 0.01 & i < (k1 + k2) * 0.01) {
            items[1].setCount(1);
        }
        if (i >= (k1 + k2) * 0.01 & i < (k1 + k2 + k3) * 0.01) {
            items[2].setCount(1);
        }
    }

    /**
     * Увеличивает максимальное здоровье игрока на основе его текущего уровня.
     *
     * @param player игрок, которому увеличивается максимальное здоровье
     */
    public void addHealthPlayer(Player player) {
        player.setMaxHealth(20 + 5 * player.getLevel() + player.getMaxHealth());
    }

    /**
     * Увеличивает урон игрока на основе его текущего уровня.
     *
     * @param player игрок, у которого увеличивается урон
     */
    public void addDamagePlayer(Player player) {
        player.setDamage(2 + player.getLevel() + player.getDamage());
    }

    /**
     * Обновляет здоровье и урон врага на основе уровня игрока.
     *
     * @param enemy  враг, чьи параметры обновляются
     * @param player игрок, уровень которого используется для вычислений
     */
    public void newHealthEnemy(Enemy enemy, Player player) {
        enemy.setMaxHealth((int) enemy.getMaxHealth() * (35 - 3 * player.getLevel()) / 100 + enemy.getMaxHealth());
        enemy.setDamage((int) enemy.getDamage() * (20 + player.getLevel()) / 100 + enemy.getDamage());
        enemy.setLevel(enemy.getLevel() + 1);
    }

    /**
     * Использует выбранный предмет игроком.
     *
     * @param player   игрок, который использует предмет
     * @param items    массив предметов игрока
     * @param name     имя предмета для использования
     * @param mediator посредник для взаимодействия с интерфейсом
     */
    public void useItem(Player player, Item[] items, String name, Mediator mediator) {
        boolean a = false;
        switch (name) {
            case "First item" -> {
                if (items[0].getCount() > 0) {
                    player.setHealth((int) (player.getMaxHealth() * 0.25) + player.getHealth());
                    items[0].setCount(-1);
                } else {
                    a = true;
                    mediator.openCantUseItemDialog();
                }
            }
            case "Second item" -> {
                if (items[1].getCount() > 0) {
                    player.setHealth((int) (player.getMaxHealth() * 0.5) + player.getHealth());
                    items[1].setCount(-1);
                } else {
                    a = true;
                    mediator.openCantUseItemDialog();
                }
            }
            case "Third item" -> {
                a = true;
                mediator.openCantUseItemDialog();
            }
        }
    }

    /**
     * Сбрасывает параметры всех врагов до начальных значений.
     *
     * @param enemiesList массив врагов, параметры которых сбрасываются
     */
    public void resetEnemies(Enemy[] enemiesList) {
        for (Enemy enemy : enemiesList) {
            enemy.setLevel(1);
            switch (enemy.getName()) {
                case "Sub-Zero" -> {
                    enemy.setLevel(1);
                    enemy.setDamage(16);
                    enemy.setMaxHealth(60);
                }
                case "Sonya Blade" -> {
                    enemy.setLevel(1);
                    enemy.setDamage(16);
                    enemy.setMaxHealth(80);
                }
                case "Boss" -> {
                    enemy.setLevel(1);
                    enemy.setDamage(30);
                    enemy.setMaxHealth(100);
                }
                case "Milina" -> {
                    enemy.setLevel(1);
                    enemy.setDamage(20);
                    enemy.setMaxHealth(70);
                }
                case "Kitana" -> {
                    enemy.setLevel(1);
                    enemy.setDamage(12);
                    enemy.setMaxHealth(100);
                }
            }
        }
    }
}
