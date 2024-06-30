package org.example.actions;


import org.example.persons.Fighter;

public abstract class FightAction {
    public abstract String getType();
    public abstract void realisation(Fighter attackingFighter, Fighter respondingFighter, String respondingFighterActionType);
}
