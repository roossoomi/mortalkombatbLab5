package org.example.persons;

import org.example.persons.Fighter;

public class Enemy extends Fighter {

    private String name;

    public Enemy(String name, int level, int maxhealth, int damage) {
        super(level, maxhealth, damage);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
