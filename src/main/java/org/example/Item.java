package org.example;

public class Item {

    private String name;
    private int count;

    public Item(String n, int c) {
        this.name = n;
        this.count = c;
    }

    public void setName(String s) {
        this.name = s;
    }

    public void setCount(int c) {
        this.count = +c;
    }

    public String getName() {
        return this.name;
    }

    public int getCount() {
        return this.count;
    }
}
