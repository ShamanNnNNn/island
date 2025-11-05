package com.example.island.animals;

import com.example.island.Island;
import com.example.island.Launcher;

import java.util.Random;

public class Rabbit extends Herbivore {
    private static final String TYPE = "Свинья";
    private static final double MAX_WEIGHT = 8.0;
    private static final double MIN_WEIGHT = 2.0;
    private static final double MAX_FOOD_REQUIRED = 4.0;

    private static final int MAX_AGE = 20;
    private static final double PLANT_NUTRITION_VALUE = 1.0;
    private static final int REPRODUCTION_AGE = 3;
    private static final int MAX_CHILDREN = 10;

    public Rabbit(String name, double weight, int age, Island island) {
        super(name, TYPE, weight, MAX_CHILDREN, MAX_FOOD_REQUIRED, island,
                MAX_AGE, PLANT_NUTRITION_VALUE, REPRODUCTION_AGE);
        setupVisualAppearance();
    }

    public Rabbit(String name, Island island) {
        this(name, MIN_WEIGHT + new Random().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT),
                1 + new Random().nextInt(5), island);
    }

    @Override
    protected void createChildren(int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            Rabbit babyRabbit = createRandomRabbit(island);
            Launcher.addRabbitToVisualMap(babyRabbit);
            babyRabbit.startLife();
        }
    }


    public static Rabbit createRandomRabbit(Island island) {
        String[] names = {"Свинья"};
        String name = names[new Random().nextInt(names.length)];
        return new Rabbit(name, island);
    }

    private void setupVisualAppearance() {
        this.getVisualRepresentation().setStyle("-fx-fill: #FF4500; -fx-stroke: black; -fx-stroke-width: 1;");
    }
}