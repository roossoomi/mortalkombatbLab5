package org.example.actions;


import org.example.persons.Fighter;

public class Debuff extends FightAction {

    @Override
    public String getType() {
        return "Debuff";
    }

    @Override
    public void realisation(Fighter attackingFighter, Fighter respondingFighter, String respondingFighterActionType) {
       if("Block".equals(respondingFighterActionType)) {
                if (Math.random()<0.75){
                    respondingFighter.setMovesWithDebuff(attackingFighter.getLevel());
                }
            }
    }
}
