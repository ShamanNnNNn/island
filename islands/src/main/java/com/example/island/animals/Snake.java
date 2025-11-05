package com.example.island.animals;

import com.example.island.Island;
import com.example.island.Launcher;

import java.util.Random;

public class Snake extends Predator {
    private static final String TYPE = "Змея";
    private static final double MAX_WEIGHT = 5.0;
    private static final double MIN_WEIGHT = 2.0;

    private static final int MAX_AGE = 10;
    private static final int HUNT_RADIUS = 2;
    private static final double HUNT_SUCCESS_CHANCE = 0.8;
    private static final double MAX_PREY_SIZE_RATIO = 1.5;
    private static final double BASE_FOOD_FROM_PREY = 80.0;
    private static final int MAX_CHILDREN = 6;
    private static final double MAX_FOOD_REQUIRED = 5.0;

    public Snake(String name, double weight, int age, Island island) {
        super(name, TYPE, weight, MAX_CHILDREN, MAX_FOOD_REQUIRED, island,
                MAX_AGE, HUNT_RADIUS, HUNT_SUCCESS_CHANCE, MAX_PREY_SIZE_RATIO, BASE_FOOD_FROM_PREY);
        setupVisualAppearance();
    }

    public Snake(String name, Island island) {
        this(name, MIN_WEIGHT + new Random().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT),
                1 + new Random().nextInt(5), island);
    }

    @Override
    protected void createChildren(int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            Snake babySnake = createRandomSnake(island);
            Launcher.addSnakeToVisualMap(babySnake);
            babySnake.startLife();
        }
    }


    public static Snake createRandomSnake(Island island) {
        String[] names = {"Змея"};
        String name = names[new Random().nextInt(names.length)];
        return new Snake(name, island);
    }

    private void setupVisualAppearance() {
        this.getVisualRepresentation().setStyle("-fx-fill: #808000; -fx-stroke: black; -fx-stroke-width: 1;");
    }
}