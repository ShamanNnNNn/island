package com.example.island.location;

public class River extends Location{
    public River(int x, int y){
        super(x, y, "Река", "#1E90FF");
    }


    @Override
    public boolean canPlaceAnimal() {
        return false;
    }
}
