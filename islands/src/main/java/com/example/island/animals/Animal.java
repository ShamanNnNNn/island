package com.example.island.animals;

import com.example.island.location.Location;
import javafx.scene.shape.Circle;

public abstract class Animal implements Runnable {
    protected final String name;
    protected final String type;
    protected double weight;
    protected int maxChildren;
    protected double maxFoodRequired;
    protected boolean isAlive;
    protected Location currentLocation;
    protected Thread thread;
    protected Circle visualRepresentation;
    protected volatile boolean running = true;

    public Animal(String name, String type, double weight, int maxChildren, double maxFoodRequired) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.maxChildren = maxChildren;
        this.maxFoodRequired = maxFoodRequired;
        this.isAlive = true;
        createVisualRepresentation();
    }

    private void createVisualRepresentation() {
        this.visualRepresentation = new Circle(5);
        this.visualRepresentation.setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 1;");
    }

    public void startLife() {
        if (thread == null || !thread.isAlive()) {
            this.running = true;
            this.thread = new Thread(this);
            this.thread.setDaemon(true);
            this.thread.start();
        }
    }

    public void stopLife() {
        this.running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void run() {
        System.out.println(name + " начинает свою жизнь...");

        while (running && isAlive) {
            try {
                liveCycle();
                Thread.sleep(1000 + (int)(Math.random() * 2000));

            } catch (InterruptedException e) {
                System.out.println(name + " прерван");
                break;
            }
        }

        System.out.println(name + " завершил свою жизнь");
    }

    protected abstract void liveCycle();

    public boolean move(Location newLocation) {
        if (!isAlive || newLocation == null) return false;

        if (!newLocation.canPlaceAnimal()) {
            System.out.println(name + " не может переместиться в " + newLocation.getName() + " - недоступная локация");
            return false;
        }

        this.currentLocation = newLocation;
        System.out.println(name + " переместился в " + newLocation.getName() + " [" + newLocation.getX() + "," + newLocation.getY() + "]");

        updateVisualPosition();
        return true;
    }

    public void updateVisualPosition() {
        if (visualRepresentation != null && currentLocation != null) {
            javafx.application.Platform.runLater(() -> {
                double cellSize = 20.0; // Размер клетки
                double centerOffset = cellSize / 2.0; // Центр клетки

                double pixelX = currentLocation.getX() * cellSize + centerOffset;
                double pixelY = currentLocation.getY() * cellSize + centerOffset;

                visualRepresentation.setCenterX(pixelX);
                visualRepresentation.setCenterY(pixelY);
                visualRepresentation.setVisible(true);
            });
        }
    }

    protected boolean tryMoveRandomly() {
        if (currentLocation == null) return false;

        int currentX = currentLocation.getX();
        int currentY = currentLocation.getY();

        int deltaX = (int)(Math.random() * 3) - 1;
        int deltaY = (int)(Math.random() * 3) - 1;

        if (deltaX == 0 && deltaY == 0) {
            deltaX = Math.random() > 0.5 ? 1 : -1;
        }

        int newX = currentX + deltaX;
        int newY = currentY + deltaY;


        return false;
    }

    public Circle getVisualRepresentation() {
        return visualRepresentation;
    }

    public abstract boolean canEat(Object food);
    public abstract void reproduce();
    public abstract double calculateFoodRequired();

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location location) {
        if (location != null && location.canPlaceAnimal()) {
            this.currentLocation = location;
            updateVisualPosition();
        } else {
            System.out.println("Нельзя разместить " + name + " в локации " +
                    (location != null ? location.getName() : "null"));
        }
    }

    public void die() {
        this.isAlive = false;
        this.running = false;
        System.out.println(name + " умер");

        javafx.application.Platform.runLater(() -> {
            if (visualRepresentation != null) {
                visualRepresentation.setVisible(false);
            }
        });
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxChildren() {
        return maxChildren;
    }

    public double getMaxFoodRequired() {
        return maxFoodRequired;
    }



    @Override
    public String toString() {
        return String.format("%s '%s' (%.1f кг) [%s] в %s",
                type, name, weight,
                isAlive ? "жив" : "мертв",
                currentLocation != null ? currentLocation.getName() : "неизвестно");
    }
}