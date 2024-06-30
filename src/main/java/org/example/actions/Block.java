package org.example.actions;

import org.example.persons.Fighter;

public class Block extends FightAction {

    @Override
    public String getType() {
        return "Block";
    }

    @Override
    public void realisation(Fighter attackingFighter, Fighter respondingFighter, String respondingFighterActionType) {
        
    }
}
