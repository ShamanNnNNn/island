package com.example.island.animals;
import com.example.island.Island;
import com.example.island.Launcher;
import com.example.island.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Horse extends Animal{
    private static final String TYPE = "Олень";
    private static final double MAX_WEIGHT = 400.0;
    private static final double MIN_WEIGHT = 100.0;
    private static final double MAX_FOOD_REQUIRED = 8.0;

    private int hungerLevel;
    private int age;
    private Random random;
    private Island island;

    public Horse(String name, double weight, int age, Island island) {
        super(name, TYPE, weight, 2, MAX_FOOD_REQUIRED);
        this.hungerLevel = 30;
        this.age = age;
        this.island = island;
        this.random = new Random();
        setupVisualAppearance();
        placeOnRandomLocation();

        if (island != null) {
            island.addAnimal(this);
        }
    }

    public Horse(String name, Island island) {
        this(name,
                MIN_WEIGHT + new Random().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT),
                1 + new Random().nextInt(5),
                island);
    }

    @Override
    protected void liveCycle() {
        if (!isAlive) return;

        hungerLevel += 8 + random.nextInt(8);
        age++;

        System.out.println(name + ": голод=" + hungerLevel + ", возраст=" + age + " лет");

        if (age > 20 && random.nextDouble() < 0.2) {
            System.out.println(name + " умер от старости");
            die();
            return;
        }

        if (hungerLevel > 70) {
            eatPlants();
        } else if (hungerLevel > 40) {
            if (random.nextDouble() < 0.6) {
                eatPlants();
            } else {
                tryMoveRandomly();
            }
        } else {
            if (random.nextDouble() < 0.5) {
                tryMoveRandomly();
            }
            if (age > 3 && random.nextDouble() < 0.15) {
                reproduce();
            }
        }

        if (hungerLevel >= 100) {
            System.out.println(name + " умер от голода");
            die();
        }
    }

    private void eatPlants() {
        if (currentLocation == null) return;

        System.out.println(name + " ищет растения в " + currentLocation.getName());

        double eatChance = 0.7;
        if (currentLocation.getName().equals("Лес")) eatChance = 0.8;
        if (currentLocation.getName().equals("Поле")) eatChance = 0.9;

        if (random.nextDouble() < eatChance) {
            System.out.println(name + " нашел вкусные растения!");
            hungerLevel = Math.max(0, hungerLevel - 40);
        } else {
            System.out.println(name + " не нашел достаточно растений");
            hungerLevel += 10;
        }
    }

    private Location getRandomAvailableLocation() {
        if (island == null) return null;

        int attempts = 0;
        int maxAttempts = 100;

        while (attempts < maxAttempts) {
            int x = random.nextInt(island.getSize());
            int y = random.nextInt(island.getSize());
            Location location = island.getLocation(x, y);

            if (location != null && location.canPlaceAnimal()) {
                return location;
            }
            attempts++;
        }

        System.out.println("Не удалось найти подходящую локацию для " + name);
        return null;
    }

    private void placeOnRandomLocation() {
        if (island != null) {
            Location randomLocation = getRandomAvailableLocation();
            if (randomLocation != null) {
                setCurrentLocation(randomLocation);
                System.out.println(name + " размещен в локации: " + randomLocation.getName() +
                        " [" + randomLocation.getX() + "," + randomLocation.getY() + "]");
            } else {
                System.out.println("ВНИМАНИЕ: " + name + " не смог найти подходящую локацию!");
            }
        }
    }

    @Override
    protected boolean tryMoveRandomly() {
        if (currentLocation == null || island == null) return false;

        int currentX = currentLocation.getX();
        int currentY = currentLocation.getY();

        int maxAttempts = 5;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int deltaX = random.nextInt(3) - 1;
            int deltaY = random.nextInt(3) - 1;

            if (deltaX == 0 && deltaY == 0) {
                continue;
            }

            int newX = currentX + deltaX;
            int newY = currentY + deltaY;

            if (newX >= 0 && newX < island.getSize() && newY >= 0 && newY < island.getSize()) {
                Location newLocation = island.getLocation(newX, newY);
                if (newLocation != null && move(newLocation)) {
                    int distance = Math.abs(deltaX) + Math.abs(deltaY);
                    hungerLevel += 1 + distance;

                    System.out.println(name + " переместился в " + newLocation.getName() +
                            " [" + newX + "," + newY + "] (расстояние: " + distance + " клеток)");
                    return true;
                }
            }
        }

        hungerLevel += 1;
        System.out.println(name + " пытался переместиться, но не нашел подходящей локации");
        return false;
    }

    @Override
    public boolean canEat(Object food) {
        return false;
    }

    @Override
    public void reproduce() {
        if (!isAlive || age < 3) {
            return;
        }

        Horse partner = findReproductionPartner();
        if (partner == null) {
            return;
        }


        int childrenCount = 1 + random.nextInt(2);
        createChildren(childrenCount);

        System.out.println(name + " и " + partner.getName() + " принесли потомство: " + childrenCount + " оленят--------------------------------");


    }

    private Horse findReproductionPartner() {
        if (currentLocation == null || island == null) return null;

        int searchRadius = 3;

        for (int dx = -searchRadius; dx <= searchRadius; dx++) {
            for (int dy = -searchRadius; dy <= searchRadius; dy++) {
                int searchX = currentLocation.getX() + dx;
                int searchY = currentLocation.getY() + dy;

                if (searchX >= 0 && searchX < island.getSize() &&
                        searchY >= 0 && searchY < island.getSize()) {

                    Location searchLocation = island.getLocation(searchX, searchY);

                    if (searchLocation == null || !searchLocation.canPlaceAnimal()) {
                        continue;
                    }

                    List<Animal> animalsInLocation = island.getAnimalsInLocation(searchLocation);

                    for (Animal animal : animalsInLocation) {
                        if (animal instanceof Horse && animal != this && animal.isAlive()) {
                            Horse otherHorse = (Horse) animal;

                            if (otherHorse.getAge() >= 3 && otherHorse.getHungerLevel() <= 60) {
                                System.out.println(name + " нашел партнера для размножения: " +
                                        otherHorse.getName() + " на расстоянии " +
                                        Math.max(Math.abs(dx), Math.abs(dy)) + " клеток");
                                return otherHorse;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private void createChildren(int childrenCount) {
        for (int i = 0; i < childrenCount; i++) {
            Horse babyHorse = createBabyHorse();
            Launcher.addHorseToVisualMap(babyHorse);
            babyHorse.startLife();
            System.out.println("Родился новый конь: " + babyHorse.getName());
        }
    }

    private Horse createBabyHorse() {
        String[] names = {"Шустрый", "Боксер"};
        String babyName = names[random.nextInt(names.length)] + " от " + name;

        return new Horse(babyName, 30.0 + random.nextDouble() * 20, 0, island);
    }

    @Override
    public double calculateFoodRequired() {
        double baseFood = maxFoodRequired * (weight / 200.0);
        if (hungerLevel > 70) baseFood *= 1.2;
        if (age > 15) baseFood *= 0.9;
        return Math.min(baseFood, 12.0);
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public int getAge() {
        return age;
    }

    public static Horse createRandomHorse(Island island) {
        String[] names = {"Шустрый", "Боксер"};
        String name = names[new Random().nextInt(names.length)];
        return new Horse(name, island);
    }

    @Override
    public String toString() {
        return String.format("Олень '%s' (%d лет, %.1f кг) [голод: %d/100] в %s",
                name, age, weight, hungerLevel,
                currentLocation != null ? currentLocation.getName() : "неизвестно");
    }

    private void setupVisualAppearance() {
        this.getVisualRepresentation().setStyle("-fx-fill: #CD5C5C; -fx-stroke: black; -fx-stroke-width: 1;");
    }
}
