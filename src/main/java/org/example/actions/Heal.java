package org.example.actions;

import org.example.persons.Fighter;

public class Heal extends FightAction {

    @Override
    public String getType() {
        return "Heal";
    }

    @Override
    public void realisation(Fighter attackingFighter, Fighter respondingFighter, String respondingFighterActionType) {
        switch (respondingFighterActionType) {
            case "Hit" -> {
            }
            case "Block" -> {
                attackingFighter.setHealth((attackingFighter.getMaxHealth() - attackingFighter.getHealth()) / 2 + attackingFighter.getHealth());
            }
            case "Debuff" -> {
            }
            case "Heal" -> {
            }
        }
    }
}
