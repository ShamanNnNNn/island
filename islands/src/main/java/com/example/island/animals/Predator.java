package com.example.island.animals;

import com.example.island.Island;
import com.example.island.Launcher;
import com.example.island.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Predator extends Animal {
    protected int hungerLevel;
    protected int age;
    protected Random random;
    protected Island island;

    private final int maxAge;
    private final int huntRadius;
    private final double huntSuccessChance;
    private final double maxPreySizeRatio;
    private final double baseFoodFromPrey;

    public Predator(String name, String type, double weight, int maxChildren, double maxFoodRequired,
                    Island island, int maxAge, int huntRadius, double huntSuccessChance,
                    double maxPreySizeRatio, double baseFoodFromPrey) {
        super(name, type, weight, maxChildren, maxFoodRequired);
        this.hungerLevel = 30;
        this.age = 1 + new Random().nextInt(5);
        this.island = island;
        this.random = new Random();
        this.maxAge = maxAge;
        this.huntRadius = huntRadius;
        this.huntSuccessChance = huntSuccessChance;
        this.maxPreySizeRatio = maxPreySizeRatio;
        this.baseFoodFromPrey = baseFoodFromPrey;

        if (island != null) {
            island.addAnimal(this);
            placeOnRandomLocation();
        }
    }

    @Override
    protected void liveCycle() {
        if (!isAlive) return;

        hungerLevel += 4 + random.nextInt(10);
        age++;

        System.out.println(name + ": голод=" + hungerLevel + ", возраст=" + age + " лет");

        if (age > maxAge && random.nextDouble() < 0.3) {
            System.out.println(name + " умер от старости");
            die();
            return;
        }

        boolean moved = tryMoveRandomly();
        if (moved) {
            System.out.println(name + " переместился");
        }

        if (hungerLevel > 80) {
            hunt();
        } else if (hungerLevel > 50) {
            if (random.nextDouble() < 0.7) hunt();
        } else {
            if (age > 2 && random.nextDouble() < 0.2) reproduce();
        }

        if (hungerLevel >= 100) {
            System.out.println(name + " умер от голода");
            die();
        }
    }

    protected void hunt() {
        if (currentLocation == null || island == null) return;

        System.out.println(name + " ищет добычу в радиусе " + huntRadius + " клеток");

        List<Animal> potentialPrey = findPotentialPrey();

        if (!potentialPrey.isEmpty()) {
            Animal prey = potentialPrey.get(random.nextInt(potentialPrey.size()));
            attackPrey(prey);
        } else {
            System.out.println(name + " не нашел подходящей добычи");
            hungerLevel += 5;
        }
    }

    protected List<Animal> findPotentialPrey() {
        List<Animal> potentialPrey = new ArrayList<>();

        for (int dx = -huntRadius; dx <= huntRadius; dx++) {
            for (int dy = -huntRadius; dy <= huntRadius; dy++) {
                int searchX = currentLocation.getX() + dx;
                int searchY = currentLocation.getY() + dy;

                if (isValidLocation(searchX, searchY)) {
                    Location searchLocation = island.getLocation(searchX, searchY);
                    if (searchLocation != null && searchLocation.canPlaceAnimal()) {
                        addPreyFromLocation(searchLocation, potentialPrey);
                    }
                }
            }
        }
        return potentialPrey;
    }

    protected boolean isValidLocation(int x, int y) {
        return x >= 0 && x < island.getSize() && y >= 0 && y < island.getSize();
    }

    protected void addPreyFromLocation(Location location, List<Animal> potentialPrey) {
        List<Animal> animalsInLocation = island.getAnimalsInLocation(location);
        for (Animal animal : animalsInLocation) {
            if (animal != this && animal.isAlive() && canEat(animal)) {
                potentialPrey.add(animal);
                System.out.println("  - Нашел добычу: " + animal.getName());
            }
        }
    }

    protected void attackPrey(Animal prey) {
        System.out.println(name + " атакует " + prey.getName() + "!");

        if (random.nextDouble() < huntSuccessChance) {
            System.out.println(name + " успешно поймал " + prey.getName() + "!");
            prey.die();

            double foodValue = baseFoodFromPrey;
            hungerLevel = Math.max(0, hungerLevel - (int)(foodValue * 60));

            System.out.println(name + " съел " + prey.getName() + " и утолил голод на " +
                    (int)(foodValue * 60) + " единиц");
        } else {
            System.out.println(name + " не смог поймать " + prey.getName() + "!");
            hungerLevel += 3;
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

    @Override
    public boolean canEat(Object food) {
        if (food instanceof Animal) {
            Animal animal = (Animal) food;
            return !getType().equals(animal.getType()) && animal.isAlive() &&
                    animal.getWeight() < this.weight * maxPreySizeRatio;
        }
        return false;
    }

    @Override
    public void reproduce() {
        Animal partner = findReproductionPartner();
        if (partner != null) {
            int childrenCount = 1 + random.nextInt(getMaxChildren() - 1);
            createChildren(childrenCount);
            System.out.println(name + " и " + partner.getName() + " принесли потомство: " +
                    childrenCount + " детенышей");
        }
    }

    protected Animal findReproductionPartner() {
        if (currentLocation == null || island == null) return null;

        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
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
                Predator otherPredator = (Predator) animal;
                if (otherPredator.getAge() >= 2 && otherPredator.getHungerLevel() <= 70) {
                    System.out.println(name + " нашел партнера для размножения: " + otherPredator.getName());
                    return otherPredator;
                }
            }
        }
        return null;
    }

    protected abstract void createChildren(int childrenCount);

    @Override
    public double calculateFoodRequired() {
        double baseFood = getMaxFoodRequired() * (getWeight() / 50.0);
        if (hungerLevel > 70) baseFood *= 1.3;
        if (age > 10) baseFood *= 0.8;
        return Math.min(baseFood, 8.0);
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