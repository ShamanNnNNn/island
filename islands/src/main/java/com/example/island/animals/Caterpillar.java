package com.example.island.animals;

import com.example.island.Island;
import com.example.island.Launcher;

import java.util.Random;

public class Caterpillar extends Herbivore {
    private static final String TYPE = "Гусеница";
    private static final double MAX_WEIGHT = 0.3;
    private static final double MIN_WEIGHT = 0.1;
    private static final double MAX_FOOD_REQUIRED = 0.1;

    private static final int MAX_AGE = 1;
    private static final double PLANT_NUTRITION_VALUE = 1.0;
    private static final int REPRODUCTION_AGE = 3;
    private static final int MAX_CHILDREN = 2;

    public Caterpillar(String name, double weight, int age, Island island) {
        super(name, TYPE, weight, MAX_CHILDREN, MAX_FOOD_REQUIRED, island,
                MAX_AGE, PLANT_NUTRITION_VALUE, REPRODUCTION_AGE);
        setupVisualAppearance();
    }

    public Caterpillar(String name, Island island) {
        this(name, MIN_WEIGHT + new Random().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT),
                1 + new Random().nextInt(5), island);
    }

    @Override
    protected void createChildren(int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            Caterpillar babyCaterpillar = createRandomCaterpillar(island);
            Launcher.addCaterpillarToVisualMap(babyCaterpillar);
            babyCaterpillar.startLife();
        }
    }


    public static Caterpillar createRandomCaterpillar(Island island) {
        String[] names = {"Гусеница"};
        String name = names[new Random().nextInt(names.length)];
        return new Caterpillar(name, island);
    }

    private void setupVisualAppearance() {
        this.getVisualRepresentation().setStyle("-fx-fill: #7FFF00; -fx-stroke: black; -fx-stroke-width: 1;");
    }
}