package com.example.island.animals;

import com.example.island.Island;
import com.example.island.Launcher;

import java.util.Random;

public class Buffalo extends Herbivore {
    private static final String TYPE = "Буйвол";
    private static final double MAX_WEIGHT = 300.0;
    private static final double MIN_WEIGHT = 150.0;
    private static final double MAX_FOOD_REQUIRED = 8.0;

    private static final int MAX_AGE = 20;
    private static final double PLANT_NUTRITION_VALUE = 1.0;
    private static final int REPRODUCTION_AGE = 3;
    private static final int MAX_CHILDREN = 2;

    public Buffalo(String name, double weight, int age, Island island) {
        super(name, TYPE, weight, MAX_CHILDREN, MAX_FOOD_REQUIRED, island,
                MAX_AGE, PLANT_NUTRITION_VALUE, REPRODUCTION_AGE);
        setupVisualAppearance();
    }

    public Buffalo(String name, Island island) {
        this(name, MIN_WEIGHT + new Random().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT),
                1 + new Random().nextInt(5), island);
    }

    @Override
    protected void createChildren(int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            Buffalo babyBuffalo = createRandomBuffalo(island);
            Launcher.addBuffaloToVisualMap(babyBuffalo);
            babyBuffalo.startLife();
        }
    }

    public static Buffalo createRandomBuffalo(Island island) {
        String[] names = {"Буйвол"};
        String name = names[new Random().nextInt(names.length)];
        return new Buffalo(name, island);
    }

    private void setupVisualAppearance() {
        this.getVisualRepresentation().setStyle("-fx-fill: #FF4500; -fx-stroke: black; -fx-stroke-width: 1;");
    }
}