package com.example.island.animals;
import com.example.island.Island;
import com.example.island.Launcher;
import com.example.island.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Bear extends Animal{
    private static final String TYPE = "Медведь";
    private static final double MAX_WEIGHT = 600.0;
    private static final double MIN_WEIGHT = 80.0;

    private int hungerLevel;
    private int age;
    private Random random;
    private Island island;

    public Bear(String name, double weight, int age, Island island) {
        super(name, TYPE, weight, 6, 5.0);
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

    public Bear(String name, Island island) {
        this(name,
                MIN_WEIGHT + new Random().nextDouble() * (MAX_WEIGHT - MIN_WEIGHT),
                1 + new Random().nextInt(5),
                island);
    }

    @Override
    protected void liveCycle() {
        if (!isAlive) return;

        hungerLevel += 4 + random.nextInt(10);
        age++;

        System.out.println(name + ": голод=" + hungerLevel + ", возраст=" + age + " лет");

        if (age > 15 && random.nextDouble() < 0.3) {
            System.out.println(name + " умер от старости");
            die();
            return;
        }

        boolean moved = tryMoveRandomly();
        if (moved) {
            System.out.println(name + " переместился как часть обычного цикла");
        }

        if (hungerLevel > 80) {
            hunt();
        } else if (hungerLevel > 50) {
            if (random.nextDouble() < 0.7) {
                hunt();
            }
        } else {
            if (age > 2 && random.nextDouble() < 0.2) {
                reproduce();
            }
        }

        if (hungerLevel >= 100) {
            System.out.println(name + " умер от голода");
            die();
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

    private void hunt() {
        if (currentLocation == null || island == null) return;

        System.out.println(name + " ищет добычу вокруг локации " + currentLocation.getName() +
                " [" + currentLocation.getX() + "," + currentLocation.getY() + "]");

        int huntRadius = 2;
        List<Animal> potentialPrey = new ArrayList<>();

        for (int dx = -huntRadius; dx <= huntRadius; dx++) {
            for (int dy = -huntRadius; dy <= huntRadius; dy++) {
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
                        if (animal != this && animal.isAlive() && canEat(animal)) {
                            potentialPrey.add(animal);
                            System.out.println("  - Нашел добычу: " + animal.getName() +
                                    " в локации [" + searchX + "," + searchY + "]");
                        }
                    }
                }
            }
        }

        if (!potentialPrey.isEmpty()) {
            Animal prey = potentialPrey.get(random.nextInt(potentialPrey.size()));
            attackPrey(prey);
        } else {
            System.out.println(name + " не нашел подходящей добычи в радиусе " + huntRadius + " клеток");
            hungerLevel += 5;
        }
    }

    private void attackPrey(Animal prey) {
        System.out.println(name + " атакует " + prey.getName() + "!");

        System.out.println(name + " успешно поймал " + prey.getName() + "!");

        prey.die();

        double foodValue = 80.0;
        hungerLevel = Math.max(0, hungerLevel - (int)(foodValue * 60));

        System.out.println(name + " съел " + prey.getName() + " и утолил голод на " +
                (int)(foodValue * 60) + " единиц");
    }

    @Override
    protected boolean tryMoveRandomly() {
        if (currentLocation == null || island == null) return false;

        int currentX = currentLocation.getX();
        int currentY = currentLocation.getY();

        int maxAttempts = 5;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int deltaX = random.nextInt(3) - 1; // -1, 0, или 1
            int deltaY = random.nextInt(3) - 1; // -1, 0, или 1

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
        if (food instanceof Animal) {
            Animal animal = (Animal) food;
            return !(animal instanceof Bear) && animal.isAlive();
        }
        return false;
    }

    @Override
    public void reproduce() {
        Bear partner = findReproductionPartner();
        if (partner == null) {
            return;
        }

        int childrenCount = 2 + random.nextInt(maxChildren - 1);
        createChildren(childrenCount);

        System.out.println(name + " и " + partner.getName() + " принесли потомство: " + childrenCount + " волчат--------------------------------");
    }

    private Bear findReproductionPartner() {
        if (currentLocation == null || island == null) return null;

        int searchRadius = 2;

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
                        if (animal instanceof Bear && animal != this && animal.isAlive()) {
                            Bear otherBear = (Bear) animal;

                            if (otherBear.getAge() >= 2 && otherBear.getHungerLevel() <= 70) {
                                System.out.println(name + " нашел партнера для размножения: " +
                                        otherBear.getName() + " на расстоянии " +
                                        Math.max(Math.abs(dx), Math.abs(dy)) + " клеток");
                                return otherBear;
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
            Bear babyBear = createBabyBear();
            Launcher.addBearToVisualMap(babyBear);
            babyBear.startLife();
            System.out.println("Родился новый волк: " + babyBear.getName());
        }
    }

    private Bear createBabyBear() {
        String[] names = {"Бурый", "Сильный", "Косолапый"};
        String babyName = names[random.nextInt(names.length)] + " от " + name;

        return new Bear(babyName, 10.0 + random.nextDouble() * 5, 0, island);
    }

    @Override
    public double calculateFoodRequired() {
        double baseFood = maxFoodRequired * (weight / 50.0);
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

    public static Bear createRandomBear(Island island) {
        String[] names = {"Бурый", "Сильный", "Косолапый"};
        String name = names[new Random().nextInt(names.length)];
        return new Bear(name, island);
    }

    @Override
    public String toString() {
        return String.format("медведь '%s' (%d лет, %.1f кг) [голод: %d/100] в %s",
                name, age, weight, hungerLevel,
                currentLocation != null ? currentLocation.getName() : "неизвестно");
    }

    private void setupVisualAppearance() {
        this.getVisualRepresentation().setStyle("-fx-fill: #800000; -fx-stroke: black; -fx-stroke-width: 1;");
    }
}
