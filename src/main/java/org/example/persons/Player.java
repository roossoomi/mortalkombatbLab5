package org.example.persons;

import org.example.Item;

public class Player extends Fighter {

    private int points;
    private int experience;
    private int nextLevelExperience;
    private Item[] items;

    public Player(int level, int maxhealth, int damage) {
        super(level, maxhealth, damage);
        points = 0;
        experience = 0;
        nextLevelExperience = 40;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getNextLevelExperience() {
        return nextLevelExperience;
    }

    public void setNextLevelExperience(int nextLevelExperience) {
        this.nextLevelExperience = nextLevelExperience;
    }

    public Item[] getItems() {
        return items;
    }


    public void setItems(Item[] items) {
        this.items = items;
    }

    @Override
    public String getName() {
        return "You";
    }

}
