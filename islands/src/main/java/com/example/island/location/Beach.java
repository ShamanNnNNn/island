package com.example.island.location;

public class Beach extends Location {
    public Beach(int x, int y) {
        super(x, y, "Пляж", "#F0E68C");
    }

    @Override
    public boolean canPlaceAnimal() {
        return true;
    }
}