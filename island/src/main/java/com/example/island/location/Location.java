package com.example.island.location;

public abstract class Location {
    protected final int x;
    protected final int y;
    protected final String name;
    protected final String color;

    public Location(int x, int y, String name, String color) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.color = color;
    }

    public abstract boolean canPlaceAnimal();

    // Геттеры
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("%s [%d,%d]", name, x, y);
    }
}