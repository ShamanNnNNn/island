package com.example.island.animals;

import com.example.island.Island;
import com.example.island.Launcher;
import com.example.island.location.Location;

import java.util.List;
import java.util.Random;

public abstract class Herbivore extends Animal {
    protected int hungerLevel;
    protected int age;
    protected Random random;
    protected Island island;

    private final int maxAge;
    private final double plantNutritionValue;
    private final int reproductionAge;

    public Herbivore(String name, String type, double weight, int maxChildren, double maxFoodRequired,
                     Island island, int maxAge, double plantNutritionValue, int reproductionAge) {
        super(name, type, weight, maxChildren, maxFoodRequired);
        this.hungerLevel = 30;
        this.age = 1 + new Random().nextInt(5);
        this.island = island;
        this.random = new Random();
        this.maxAge = maxAge;
        this.plantNutritionValue = plantNutritionValue;
        this.reproductionAge = reproductionAge;

        if (island != null) {
            island.addAnimal(this);
            placeOnRandomLocation();
        }
    }

    @Override
    protected void liveCycle() {
        if (!isAlive) return;

        hungerLevel += 8 + random.nextInt(8);
        age++;

        System.out.println(name + ": голод=" + hungerLevel + ", возраст=" + age + " лет");

        if (age > maxAge && random.nextDouble() < 0.2) {
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
            if (age > reproductionAge && random.nextDouble() < 0.15) {
                reproduce();
            }
        }

        if (hungerLevel >= 100) {
            System.out.println(name + " умер от голода");
            die();
        }
    }

    protected void eatPlants() {
        if (currentLocation == null) return;

        System.out.println(name + " ищет растения в " + currentLocation.getName());

        double eatChance = getLocationEatChance(currentLocation.getName());

        if (random.nextDouble() < eatChance) {
            System.out.println(name + " нашел растения!");
            hungerLevel = Math.max(0, hungerLevel - (int)(plantNutritionValue * 40));
        } else {
            System.out.println(name + " не нашел растений");
            hungerLevel += 10;
        }
    }

    protected double getLocationEatChance(String locationName) {
        switch (locationName) {
            case "Лес": return 0.8;
            case "Поле": return 0.9;
            default: return 0.7;
        }
    }

    @Override
    protected boolean tryMoveRandomly() {
        if (currentLocation == null || island == null) return false;

        int currentX = currentLocation.getX();
        int currentY = currentLocation.getY();

        for (int attempt = 0; attempt < 5; attempt++) {
            int deltaX = random.nextInt(3) - 1;
            int deltaY = random.nextInt(3) - 1;

            if (deltaX == 0 && deltaY == 0) continue;

            int newX = currentX + deltaX;
            int newY = currentY + deltaY;

            if (isValidLocation(newX, newY)) {
                Location newLocation = island.getLocation(newX, newY);
                if (newLocation != null && move(newLocation)) {
                    int distance = Math.abs(deltaX) + Math.abs(deltaY);
                    hungerLevel += 1 + distance;
                    return true;
                }
            }
        }

        hungerLevel += 1;
        return false;
    }

    protected boolean isValidLocation(int x, int y) {
        return x >= 0 && x < island.getSize() && y >= 0 && y < island.getSize();
    }

    @Override
    public boolean canEat(Object food) {
        return false;
    }

    @Override
    public void reproduce() {
        if (!isAlive || age < reproductionAge) return;

        Animal partner = findReproductionPartner();
        if (partner == null) return;

        int childrenCount = 1 + random.nextInt(getMaxChildren() - 1);
        createChildren(childrenCount);

        System.out.println(name + " и " + partner.getName() + " принесли потомство: " +
                childrenCount + " детенышей--------------------------------");
    }

    protected Animal findReproductionPartner() {
        if (currentLocation == null || island == null) return null;

        int searchRadius = 3;

        for (int dx = -searchRadius; dx <= searchRadius; dx++) {
            for (int dy = -searchRadius; dy <= searchRadius; dy++) {
                int searchX = currentLocation.getX() + dx;
                int searchY = currentLocation.getY() + dy;

                if (isValidLocation(searchX, searchY)) {
                    Location searchLocation = island.getLocation(searchX, searchY);
                    if (searchLocation != null && searchLocation.canPlaceAnimal()) {
                        Animal partner = findPartnerInLocation(searchLocation);
                        if (partner != null) return partner;
                    }
                }
            }
        }
        return null;
    }

    protected Animal findPartnerInLocation(Location location) {
        List<Animal> animalsInLocation = island.getAnimalsInLocation(location);
        for (Animal animal : animalsInLocation) {
            if (animal.getClass().equals(this.getClass()) && animal != this && animal.isAlive()) {
                Herbivore otherHerbivore = (Herbivore) animal;
                if (otherHerbivore.getAge() >= reproductionAge && otherHerbivore.getHungerLevel() <= 60) {
                    System.out.println(name + " нашел партнера для размножения: " + otherHerbivore.getName());
                    return otherHerbivore;
                }
            }
        }
        return null;
    }

    protected abstract void createChildren(int childrenCount);

    @Override
    public double calculateFoodRequired() {
        double baseFood = getMaxFoodRequired() * (getWeight() / 200.0);
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

    @Override
    public String toString() {
        return String.format("%s '%s' (%d лет, %.1f кг) [голод: %d/100] в %s",
                getType(), name, age, weight, hungerLevel,
                currentLocation != null ? currentLocation.getName() : "неизвестно");
    }

    protected void placeOnRandomLocation() {
        if (island != null) {
            Location randomLocation = getRandomAvailableLocation();
            if (randomLocation != null) {
                setCurrentLocation(randomLocation);
            }
        }
    }

    protected Location getRandomAvailableLocation() {
        if (island == null) return null;

        for (int attempt = 0; attempt < 100; attempt++) {
            int x = random.nextInt(island.getSize());
            int y = random.nextInt(island.getSize());
            Location location = island.getLocation(x, y);
            if (location != null && location.canPlaceAnimal()) {
                return location;
            }
        }
        return null;
    }
}