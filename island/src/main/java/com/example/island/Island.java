package com.example.island;

import com.example.island.animals.Animal;
import com.example.island.animals.Wolf;
import com.example.island.location.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Island {
    private static final int ISLAND_SIZE = 32;
    private Location[][] locations;
    private Random random;
    private List<Animal> animals;

    public Island() {
        this.random = new Random();
        this.locations = new Location[ISLAND_SIZE][ISLAND_SIZE];
        this.animals = new ArrayList<>();
        initializeIsland();
    }

    private void initializeIsland() {
        for (int x = 0; x < ISLAND_SIZE; x++) {
            for (int y = 0; y < ISLAND_SIZE; y++) {
                locations[x][y] = createLocation(x, y);
            }
        }
    }

    private Location createLocation(int x, int y) {
        double center = ISLAND_SIZE / 2.0;
        double distance = Math.sqrt(Math.pow(x - center, 2) + Math.pow(y - center, 2));
        double maxDistance = center * 1.2;
        double normalizedDistance = distance / maxDistance;

        if (normalizedDistance > 0.8) {
            return new River(x, y);
        } else if (normalizedDistance > 0.7) {
            return new Beach(x, y);
        } else if (normalizedDistance > 0.6) {
            return random.nextDouble() < 0.7 ? new Field(x, y) : new Forest(x, y);
        } else {
            return random.nextDouble() < 0.6 ? new Forest(x, y) : new Field(x, y);
        }
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < ISLAND_SIZE && y >= 0 && y < ISLAND_SIZE) {
            return locations[x][y];
        }
        return null;
    }

    public int getSize() {
        return ISLAND_SIZE;
    }

    public Location[][] getLocations() {
        return locations;
    }

    public List<Animal> getAnimalsInLocation(Location location) {
        if (location == null || animals == null) {
            return new ArrayList<>();
        }

        return animals.stream()
                .filter(animal -> animal != null &&
                        animal.isAlive() &&
                        animal.getCurrentLocation() != null &&
                        animal.getCurrentLocation().getX() == location.getX() &&
                        animal.getCurrentLocation().getY() == location.getY())
                .collect(Collectors.toList());
    }

    public List<Animal> getAllAnimals() {
        if (animals == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(animals);
    }

    public List<Wolf> getWolvesInLocation(Location location) {
        return getAnimalsInLocation(location).stream()
                .filter(animal -> animal instanceof Wolf)
                .map(animal -> (Wolf) animal)
                .collect(Collectors.toList());
    }

    public void addAnimal(Animal animal) {
        if (animals != null && animal != null) {
            animals.add(animal);
            System.out.println("✅ Добавлено животное: " + animal.getName());
        }
    }

    public Location getRandomAnimalLocation() {
        int attempts = 0;
        while (attempts < 100) {
            int x = random.nextInt(ISLAND_SIZE);
            int y = random.nextInt(ISLAND_SIZE);
            Location location = locations[x][y];

            if (location != null && location.canPlaceAnimal()) {
                return location;
            }
            attempts++;
        }
        return null;
    }

    public Location getRandomLocation() {
        int x = random.nextInt(ISLAND_SIZE);
        int y = random.nextInt(ISLAND_SIZE);
        return locations[x][y];
    }

    public void printIslandStatistics() {
        int forestCount = 0;
        int beachCount = 0;
        int fieldCount = 0;
        int riverCount = 0;

        for (int x = 0; x < ISLAND_SIZE; x++) {
            for (int y = 0; y < ISLAND_SIZE; y++) {
                Location location = locations[x][y];
                if (location instanceof Forest) {
                    forestCount++;
                } else if (location instanceof Beach) {
                    beachCount++;
                } else if (location instanceof Field) {
                    fieldCount++;
                } else if (location instanceof River) {
                    riverCount++;
                }
            }
        }
    }
}