package com.example.island.animals;

import com.example.island.Island;
import com.example.island.Launcher;

import java.util.Random;

public class Duck extends Herbivore {
    private static final String TYPE = "Утка";
    private static final double MAX_WEIGHT = 10.0;
    private static final double MIN_WEIGHT = 5.0;
    private static final double MAX_FOOD_REQUIRED = 8.0;

    private static final int MAX_AGE = 20;
    private static final double PLANT_NUTRITION_VALUE = 1.0;
    private static final int REPRODUCTION_AGE = 3;
    private static final int MAX_CHILDREN = 2;

    public Duck(String name, double weight, int age, Island island) {
        super(name, TYPE, weight, MAX_CHILDREN, MAX_FOOD_REQUIRED, island,
                MAX_AGE, PLANT_NUTRITION_VALUE, REPRODUCTION_AGE);
        setupVisualAppearance();
    }

    public Duck(String name, Island island) {
        this(name, MIN_WEIGHT + new Random().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT),
                1 + new Random().nextInt(5), island);
    }

    @Override
    protected void createChildren(int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            Duck babyDuck = createRandomDuck(island);
            Launcher.addDuckToVisualMap(babyDuck);
            babyDuck.startLife();
        }
    }

    public static Duck createRandomDuck(Island island) {
        String[] names = {"Утка"};
        String name = names[new Random().nextInt(names.length)];
        return new Duck(name, island);
    }

    private void setupVisualAppearance() {
        this.getVisualRepresentation().setStyle("-fx-fill: #CD5C5C; -fx-stroke: black; -fx-stroke-width: 1;");
    }
}