package com.example.island.animals;

import com.example.island.Island;
import com.example.island.Launcher;

import java.util.Random;

public class Fox extends Predator {
    private static final String TYPE = "Лиса";
    private static final double MAX_WEIGHT = 15.0;
    private static final double MIN_WEIGHT = 5.0;

    private static final int MAX_AGE = 15;
    private static final int HUNT_RADIUS = 2;
    private static final double HUNT_SUCCESS_CHANCE = 0.8;
    private static final double MAX_PREY_SIZE_RATIO = 1.5;
    private static final double BASE_FOOD_FROM_PREY = 80.0;
    private static final int MAX_CHILDREN = 6;
    private static final double MAX_FOOD_REQUIRED = 5.0;

    public Fox(String name, double weight, int age, Island island) {
        super(name, TYPE, weight, MAX_CHILDREN, MAX_FOOD_REQUIRED, island,
                MAX_AGE, HUNT_RADIUS, HUNT_SUCCESS_CHANCE, MAX_PREY_SIZE_RATIO, BASE_FOOD_FROM_PREY);
        setupVisualAppearance();
    }

    public Fox(String name, Island island) {
        this(name, MIN_WEIGHT + new Random().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT),
                1 + new Random().nextInt(5), island);
    }

    @Override
    protected void createChildren(int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            Fox babyFox = createRandomFox(island);
            Launcher.addFoxToVisualMap(babyFox);
            babyFox.startLife();
        }
    }

    public static Fox createRandomFox(Island island) {
        String[] names = {"Лиса"};
        String name = names[new Random().nextInt(names.length)];
        return new Fox(name, island);
    }

    private void setupVisualAppearance() {
        this.getVisualRepresentation().setStyle("-fx-fill: #FF4500; -fx-stroke: black; -fx-stroke-width: 1;");
    }
}