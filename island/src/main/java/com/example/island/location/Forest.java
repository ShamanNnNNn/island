package com.example.island.location;

public class Forest extends Location {
    public Forest(int x, int y) {
        super(x, y, "Лес", "#228B22");
    }

    @Override
    public boolean canPlaceAnimal() {
        return true;
    }
}