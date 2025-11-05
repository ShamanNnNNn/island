package com.example.island.animals;

import com.example.island.Island;
import com.example.island.Launcher;

import java.util.Random;

public class Deer extends Herbivore {
    private static final String TYPE = "олень";
    private static final double MAX_WEIGHT = 100.0;
    private static final double MIN_WEIGHT = 50.0;
    private static final double MAX_FOOD_REQUIRED = 8.0;

    private static final int MAX_AGE = 20;
    private static final double PLANT_NUTRITION_VALUE = 1.0;
    private static final int REPRODUCTION_AGE = 3;
    private static final int MAX_CHILDREN = 2;

    public Deer(String name, double weight, int age, Island island) {
        super(name, TYPE, weight, MAX_CHILDREN, MAX_FOOD_REQUIRED, island,
                MAX_AGE, PLANT_NUTRITION_VALUE, REPRODUCTION_AGE);
        setupVisualAppearance();
    }

    public Deer(String name, Island island) {
        this(name, MIN_WEIGHT + new Random().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT),
                1 + new Random().nextInt(5), island);
    }

    @Override
    protected void createChildren(int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            Deer babyDeer = createRandomDeer(island);
            Launcher.addDeerToVisualMap(babyDeer);
            babyDeer.startLife();
        }
    }


    public static Deer createRandomDeer(Island island) {
        String[] names = {"Олень"};
        String name = names[new Random().nextInt(names.length)];
        return new Deer(name, island);
    }

    private void setupVisualAppearance() {
        this.getVisualRepresentation().setStyle("-fx-fill: #E9967A; -fx-stroke: black; -fx-stroke-width: 1;");
    }
}