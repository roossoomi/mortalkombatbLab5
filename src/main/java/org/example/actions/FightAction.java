package org.example.actions;

import org.example.persons.Fighter;

/**
 * Абстрактный класс {@code FightAction} представляет собой действие в бою между двумя бойцами.
 * Подклассы {@code FightAction} должны реализовать конкретный тип действия и его реализацию (Атака, блок, дебафф, восстановление здоровья).
 */
public abstract class FightAction {

    /**
     * Возвращает тип боевого действия.
     */
    public abstract String getType();

    /**
     * Реализует боевое действие.
     * Этот метод должен быть реализован подклассами для определения того, как действие влияет на атакующего и отвечающего бойцов.
     *
     * @param attackingFighter           боец, выполняющий действие.
     * @param respondingFighter          боец, отвечающий на действие.
     * @param respondingFighterActionType строка, представляющая тип действия, предпринятого отвечающим бойцом.
     */
    public abstract void realisation(Fighter attackingFighter, Fighter respondingFighter, String respondingFighterActionType);
}
