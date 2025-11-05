package com.example.island.location;

public class Field extends Location {
    public Field(int x, int y) {
        super(x, y, "Поле", "#90EE90");
    }

    @Override
    public boolean canPlaceAnimal() {
        return true;
    }
}