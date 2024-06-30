package org.example.actions;


import org.example.persons.Fighter;

public class Hit extends FightAction {

    @Override
    public String getType() {
        return "Hit";
    }

    @Override
    public void realisation(Fighter attackingFighter, Fighter respondingFighter, String respondingFighterActionType) {
        switch (respondingFighterActionType) {
            case "Hit" -> {
                if (respondingFighter.isDebuffed() & attackingFighter.isDebuffed()) {
                    respondingFighter.setHealth((int) (respondingFighter.getHealth()-attackingFighter.getDamage() * 1.25 / 2) );

                }else if (respondingFighter.isDebuffed() & !attackingFighter.isDebuffed()) {
                    respondingFighter.setHealth((int) (respondingFighter.getHealth() -attackingFighter.getDamage() * 1.25) );
                }
                else if (!respondingFighter.isDebuffed() & attackingFighter.isDebuffed()) {
                    respondingFighter.setHealth( (respondingFighter.getHealth()-attackingFighter.getDamage() / 2));
                }
                else  {
                    respondingFighter.setHealth((respondingFighter.getHealth()-attackingFighter.getDamage())  );
                }
            }
            case "Block" -> {
                attackingFighter.setHealth(attackingFighter.getHealth()-attackingFighter.getDamage() / 2 );
            }
            case "Debuff" -> {
                respondingFighter.setHealth((int) (respondingFighter.getHealth() - attackingFighter.getDamage() * 1.15)  );
            }
            case "Heal" -> {
                respondingFighter.setHealth( respondingFighter.getHealth()-attackingFighter.getDamage() * 2 );
            }
        }
    }
}
