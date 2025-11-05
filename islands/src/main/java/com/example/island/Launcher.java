package com.example.island;

import com.example.island.animals.*;
import com.example.island.location.Location;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {

    private Island island;
    private Pane rootPane;
    private List<Wolf> wolves;
    private List<Fox> foxes;
    private List<Deer> deers;
    private List<Bear> bears;
    private List<Horse> horses;
    private List<Pig> pigges;
    private List<Rabbit> rabbits;
    private List<Sheap> sheps;
    private List<Buffalo> buffals;
    private List<Caterpillar> caterpillars;
    private List<Duck> ducks;
    private List<Eagle> eagles;
    private List<Goat> goates;
    private List<Mause> maise;
    private List<Snake> snakes;
    private static Pane staticRootPane;
    private static List<Wolf> staticWolves;
    private static List<Fox> staticFoxes;
    private static List<Deer> staticDeers;
    private static List<Bear> staticBears;
    private static List<Horse> staticHorses;
    private static List<Rabbit> staticRabbits;
    private static List<Sheap> staticSheaps;
    private static List<Pig> staticPigs;
    private static List<Buffalo> staticBuffals;
    private static List<Caterpillar> staticCaterpillars;
    private static List<Duck> staticDucks;
    private static List<Eagle> staticEagles;
    private static List<Goat> staticGoats;
    private static List<Mause> staticMaise;
    private static List<Snake> staticSnake;


    @Override
    public void start(Stage primaryStage) {
        island = new Island();
        wolves = new ArrayList<>();
        foxes = new ArrayList<>();
        deers = new ArrayList<>();
        bears = new ArrayList<>();
        horses = new ArrayList<>();
        pigges = new ArrayList<>();
        rabbits = new ArrayList<>();
        sheps = new ArrayList<>();
        buffals = new ArrayList<>();
        caterpillars = new ArrayList<>();
        ducks = new ArrayList<>();
        eagles = new ArrayList<>();
        goates = new ArrayList<>();
        maise = new ArrayList<>();
        snakes = new ArrayList<>();
        rootPane = new Pane();

        staticRootPane = rootPane;
        staticWolves = wolves;
        staticFoxes = foxes;
        staticDeers = deers;
        staticBears = bears;
        staticHorses = horses;
        staticRabbits = rabbits;
        staticSheaps = sheps;
        staticPigs = pigges;
        staticBuffals = buffals;
        staticCaterpillars = caterpillars;
        staticDucks = ducks;
        staticEagles = eagles;
        staticGoats = goates;
        staticMaise = maise;
        staticSnake = snakes;

        GridPane islandGrid = createIslandGrid();
        rootPane.getChildren().add(islandGrid);

        createAndStartWolves();
        createAndStartFox();
        createAndStartDeer();
        createAndStartBear();
        createAndStartHorse();
        createAndStartPig();
        createAndStartRabbit();
        createAndStartBuffalo();
        createAndStartCaterpillar();
        createAndStartDuck();
        createAndStartEagle();
        createAndStartGoat();
        createAndStartMause();
        createAndStartSnake();
        createAndStartSheap();

        Scene scene = new Scene(rootPane, 700, 700);
        primaryStage.setTitle("Остров"); // ✅ ОБНОВЛЕНО: название
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> stopAllAnimals());
    }

    public static Pane getRootPane() {
        return staticRootPane;
    }

    public static void addWolfToVisualMap(Wolf wolf) {
        if (staticRootPane != null && staticWolves != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(wolf.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(wolf.getVisualRepresentation());
                    staticWolves.add(wolf);
                    System.out.println("Детеныш волка добавлен на карту: " + wolf.getName());
                }
            });
        }
    }

    public static void addFoxToVisualMap(Fox fox) {
        if (staticRootPane != null && staticFoxes != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(fox.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(fox.getVisualRepresentation());
                    staticFoxes.add(fox);
                    System.out.println("Детеныш лисы добавлен на карту: " + fox.getName());
                }
            });
        }
    }

    public static void addBearToVisualMap(Bear bear) {
        if (staticRootPane != null && staticBears != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(bear.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(bear.getVisualRepresentation());
                    staticBears.add(bear);
                    System.out.println("Детеныш медведя добавлен на карту: " + bear.getName());
                }
            });
        }
    }
    public static void addHorseToVisualMap(Horse horse) {
        if (staticRootPane != null && staticHorses != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(horse.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(horse.getVisualRepresentation());
                    staticHorses.add(horse);
                    System.out.println("Детеныш лошади добавлен на карту: " + horse.getName());
                }
            });
        }
    }
    public static void addPigToVisualMap(Pig pig) {
        if (staticRootPane != null && staticDeers != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(pig.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(pig.getVisualRepresentation());
                    staticPigs.add(pig);
                    System.out.println("Детеныш свиньи добавлен на карту: " + pig.getName());
                }
            });
        }
    }
    public static void addRabbitToVisualMap(Rabbit rabbit) {
        if (staticRootPane != null && staticDeers != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(rabbit.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(rabbit.getVisualRepresentation());
                    staticRabbits.add(rabbit);
                    System.out.println("Детеныш кролика добавлен на карту: " + rabbit.getName());
                }
            });
        }
    }
    public static void addSheapToVisualMap(Sheap sheap) {
        if (staticRootPane != null && staticDeers != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(sheap.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(sheap.getVisualRepresentation());
                    staticSheaps.add(sheap);
                    System.out.println("Детеныш овцы добавлен на карту: " + sheap.getName());
                }
            });
        }
    }
    public static void addBuffaloToVisualMap(Buffalo buffalo) {
        if (staticRootPane != null && staticBuffals != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(buffalo.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(buffalo.getVisualRepresentation());
                    staticBuffals.add(buffalo);
                    System.out.println("Детеныш буйвола добавлен на карту: " + buffalo.getName());
                }
            });
        }
    }
    public static void addCaterpillarToVisualMap(Caterpillar caterpillar) {
        if (staticRootPane != null && staticCaterpillars != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(caterpillar.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(caterpillar.getVisualRepresentation());
                    staticCaterpillars.add(caterpillar);
                    System.out.println("Детеныш гусеницы добавлен на карту: " + caterpillar.getName());
                }
            });
        }
    }
    public static void addDuckToVisualMap(Duck duck) {
        if (staticRootPane != null && staticDucks != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(duck.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(duck.getVisualRepresentation());
                    staticDucks.add(duck);
                    System.out.println("Детеныш утки добавлен на карту: " + duck.getName());
                }
            });
        }
    }
    public static void addEagleToVisualMap(Eagle eagles) {
        if (staticRootPane != null && staticEagles != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(eagles.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(eagles.getVisualRepresentation());
                    staticEagles.add(eagles);
                    System.out.println("Детеныш орла добавлен на карту: " + eagles.getName());
                }
            });
        }
    }
    public static void addGoatToVisualMap(Goat goat) {
        if (staticRootPane != null && staticGoats != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(goat.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(goat.getVisualRepresentation());
                    staticGoats.add(goat);
                    System.out.println("Детеныш козы добавлен на карту: " + goat.getName());
                }
            });
        }
    }
    public static void addMauseToVisualMap(Mause mause) {
        if (staticRootPane != null && staticMaise != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(mause.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(mause.getVisualRepresentation());
                    staticMaise.add(mause);
                    System.out.println("Детеныш мышы добавлен на карту: " + mause.getName());
                }
            });
        }
    }
    public static void addSnakeToVisualMap(Snake snake) {
        if (staticRootPane != null && staticSnake != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(snake.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(snake.getVisualRepresentation());
                    staticSnake.add(snake);
                    System.out.println("Детеныш змеи добавлен на карту: " + snake.getName());
                }
            });
        }
    }
    public static void addDeerToVisualMap(Deer deer) {
        if (staticRootPane != null && staticDeers != null) {
            javafx.application.Platform.runLater(() -> {
                if (!staticRootPane.getChildren().contains(deer.getVisualRepresentation())) {
                    staticRootPane.getChildren().add(deer.getVisualRepresentation());
                    staticDeers.add(deer);
                    System.out.println("Детеныш оленя добавлен на карту: " + deer.getName());
                }
            });
        }
    }


    private GridPane createIslandGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(1);
        grid.setVgap(1);

        int size = island.getSize();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Location location = island.getLocation(x, y);
                Rectangle rect = new Rectangle(20, 20);
                rect.setStyle("-fx-fill: " + determineCellColor(location) + "; -fx-stroke: black;");
                grid.add(rect, x, y);
            }
        }

        return grid;
    }

    private void createAndAddWolf(Wolf wolf) {
        if (!wolves.contains(wolf)) {
            wolves.add(wolf);
        }

        if (!rootPane.getChildren().contains(wolf.getVisualRepresentation())) {
            rootPane.getChildren().add(wolf.getVisualRepresentation());
        }

        wolf.startLife();
        island.addAnimal(wolf);

        System.out.println("Волк создан и добавлен на карту: " + wolf.getName());
    }

    private void createAndAddFox(Fox fox) {
        if (!foxes.contains(fox)) {
            foxes.add(fox);
        }

        if (!rootPane.getChildren().contains(fox.getVisualRepresentation())) {
            rootPane.getChildren().add(fox.getVisualRepresentation());
        }

        fox.startLife();
        island.addAnimal(fox);

        System.out.println("Лиса создана и добавлена на карту: " + fox.getName());
    }

    private void createAndAddBear(Bear bear) {
        if (!bears.contains(bear)) {
            bears.add(bear);
        }

        if (!rootPane.getChildren().contains(bear.getVisualRepresentation())) {
            rootPane.getChildren().add(bear.getVisualRepresentation());
        }

        bear.startLife();
        island.addAnimal(bear);

        System.out.println("Медведь создан и добавлен на карту: " + bear.getName());
    }
    private void createAndAddHorse(Horse horse) {
        if (!horses.contains(horse)) {
            horses.add(horse);
        }

        if (!rootPane.getChildren().contains(horse.getVisualRepresentation())) {
            rootPane.getChildren().add(horse.getVisualRepresentation());
        }

        horse.startLife();
        island.addAnimal(horse);

        System.out.println("Лошадь создан и добавлен на карту: " + horse.getName());
    }
    private void createAndAddPig(Pig pig) {
        if (!pigges.contains(pig)) {
            pigges.add(pig);
        }

        if (!rootPane.getChildren().contains(pig.getVisualRepresentation())) {
            rootPane.getChildren().add(pig.getVisualRepresentation());
        }

        pig.startLife();
        island.addAnimal(pig);

        System.out.println("Свинья создан и добавлен на карту: " + pig.getName());
    }
    private void createAndAddRabbit(Rabbit rabbit) {
        if (!rabbits.contains(rabbit)) {
            rabbits.add(rabbit);
        }

        if (!rootPane.getChildren().contains(rabbit.getVisualRepresentation())) {
            rootPane.getChildren().add(rabbit.getVisualRepresentation());
        }

        rabbit.startLife();
        island.addAnimal(rabbit);

        System.out.println("Кролик создан и добавлен на карту: " + rabbit.getName());
    }
    private void createAndAddSheap(Sheap sheap) {
        if (!sheps.contains(sheap)) {
            sheps.add(sheap);
        }

        if (!rootPane.getChildren().contains(sheap.getVisualRepresentation())) {
            rootPane.getChildren().add(sheap.getVisualRepresentation());
        }

        sheap.startLife();
        island.addAnimal(sheap);

        System.out.println("Овца создан и добавлен на карту: " + sheap.getName());
    }
    private void createAndAddDeer(Deer deer) {
        if (!deers.contains(deer)) {
            deers.add(deer);
        }

        if (!rootPane.getChildren().contains(deer.getVisualRepresentation())) {
            rootPane.getChildren().add(deer.getVisualRepresentation());
        }

        deer.startLife();
        island.addAnimal(deer);

        System.out.println("Олень создан и добавлен на карту: " + deer.getName());
    }
    private void createAndAddBuffalo(Buffalo buffalo) {
        if (!buffals.contains(buffalo)) {
            buffals.add(buffalo);
        }

        if (!rootPane.getChildren().contains(buffalo.getVisualRepresentation())) {
            rootPane.getChildren().add(buffalo.getVisualRepresentation());
        }

        buffalo.startLife();
        island.addAnimal(buffalo);

        System.out.println("Буйвол создан и добавлен на карту: " + buffalo.getName());
    }
    private void createAndAddCaterpillar(Caterpillar caterpillar) {
        if (!caterpillars.contains(caterpillar)) {
            caterpillars.add(caterpillar);
        }

        if (!rootPane.getChildren().contains(caterpillar.getVisualRepresentation())) {
            rootPane.getChildren().add(caterpillar.getVisualRepresentation());
        }

        caterpillar.startLife();
        island.addAnimal(caterpillar);

        System.out.println("Гусеница создан и добавлен на карту: " + caterpillar.getName());
    }
    private void createAndAddDuck(Duck duck) {
        if (!ducks.contains(duck)) {
            ducks.add(duck);
        }

        if (!rootPane.getChildren().contains(duck.getVisualRepresentation())) {
            rootPane.getChildren().add(duck.getVisualRepresentation());
        }

        duck.startLife();
        island.addAnimal(duck);

        System.out.println("Утка создан и добавлен на карту: " + duck.getName());
    }
    private void createAndAddEagle(Eagle eagle) {
        if (!eagles.contains(eagle)) {
            eagles.add(eagle);
        }

        if (!rootPane.getChildren().contains(eagle.getVisualRepresentation())) {
            rootPane.getChildren().add(eagle.getVisualRepresentation());
        }

        eagle.startLife();
        island.addAnimal(eagle);

        System.out.println("Орем создан и добавлен на карту: " + eagle.getName());
    }
    private void createAndAddGoat(Goat goat) {
        if (!goates.contains(goat)) {
            goates.add(goat);
        }

        if (!rootPane.getChildren().contains(goat.getVisualRepresentation())) {
            rootPane.getChildren().add(goat.getVisualRepresentation());
        }

        goat.startLife();
        island.addAnimal(goat);

        System.out.println("Коза создан и добавлен на карту: " + goat.getName());
    }
    private void createAndAddMause(Mause mause) {
        if (!maise.contains(mause)) {
            maise.add(mause);
        }

        if (!rootPane.getChildren().contains(mause.getVisualRepresentation())) {
            rootPane.getChildren().add(mause.getVisualRepresentation());
        }

        mause.startLife();
        island.addAnimal(mause);

        System.out.println("Мыш создан и добавлен на карту: " + mause.getName());
    }
    private void createAndAddSnake(Snake snake) {
        if (!snakes.contains(snake)) {
            snakes.add(snake);
        }

        if (!rootPane.getChildren().contains(snake.getVisualRepresentation())) {
            rootPane.getChildren().add(snake.getVisualRepresentation());
        }

        snake.startLife();
        island.addAnimal(snake);

        System.out.println("Змей создан и добавлен на карту: " + snake.getName());
    }


    private void createAndStartWolves() {
        for (int i = 0; i < 2; i++) {
            Wolf wolf = Wolf.createRandomWolf(island);
            createAndAddWolf(wolf);
        }
        System.out.println("=== Создано волков: " + wolves.size() + " ===");
    }

    private void createAndStartFox() {
        for (int i = 0; i < 2; i++) {
            Fox fox = Fox.createRandomFox(island);
            createAndAddFox(fox);
        }
        System.out.println("=== Создано лис: " + foxes.size() + " ===");
    }

    private void createAndStartDeer() {
        for (int i = 0; i < 2; i++) {
            Deer deer = Deer.createRandomDeer(island);
            createAndAddDeer(deer);
        }
        System.out.println("=== Создано оленей: " + deers.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartBear() {
        for (int i = 0; i < 2; i++) {
            Bear bear = Bear.createRandomBear(island);
            createAndAddBear(bear);
        }
        System.out.println("=== Создано оленей: " + bears.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartHorse() {
        for (int i = 0; i < 2; i++) {
            Horse horse = Horse.createRandomHorse(island);
            createAndAddHorse(horse);
        }
        System.out.println("=== Создано оленей: " + horses.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartPig() {
        for (int i = 0; i < 2; i++) {
            Pig pig = Pig.createRandomPig(island);
            createAndAddPig(pig);
        }
        System.out.println("=== Создано свиней: " + deers.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartSheap() {
        for (int i = 0; i < 2; i++) {
            Sheap sheap = Sheap.createRandomSheap(island);
            createAndAddSheap(sheap);
        }
        System.out.println("=== Создано оленей: " + deers.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartRabbit() {
        for (int i = 0; i < 2; i++) {
            Rabbit rabbit = Rabbit.createRandomRabbit(island);
            createAndAddRabbit(rabbit);
        }
        System.out.println("=== Создано оленей: " + rabbits.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartBuffalo() {
        for (int i = 0; i < 2; i++) {
            Buffalo buffalo = Buffalo.createRandomBuffalo(island);
            createAndAddBuffalo(buffalo);
        }
        System.out.println("=== Создано оленей: " + rabbits.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartCaterpillar() {
        for (int i = 0; i < 2; i++) {
            Caterpillar caterpillar = Caterpillar.createRandomCaterpillar(island);
            createAndAddCaterpillar(caterpillar);
        }
        System.out.println("=== Создано оленей: " + rabbits.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartGoat() {
        for (int i = 0; i < 2; i++) {
            Goat goat = Goat.createRandomGoat(island);
            createAndAddGoat(goat);
        }
        System.out.println("=== Создано оленей: " + rabbits.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartEagle() {
        for (int i = 0; i < 2; i++) {
            Eagle eagle = Eagle.createRandomEagle(island);
            createAndAddEagle(eagle);
        }
        System.out.println("=== Создано оленей: " + rabbits.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartDuck() {
        for (int i = 0; i < 2; i++) {
            Duck duck = Duck.createRandomDuck(island);
            createAndAddDuck(duck);
        }
        System.out.println("=== Создано оленей: " + rabbits.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartMause() {
        for (int i = 0; i < 2; i++) {
            Mause mause = Mause.createRandomMause(island);
            createAndAddMause(mause);
        }
        System.out.println("=== Создано оленей: " + rabbits.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }
    private void createAndStartSnake() {
        for (int i = 0; i < 2; i++) {
            Snake snake = Snake.createRandomSnake(island);
            createAndAddSnake(snake);
        }
        System.out.println("=== Создано оленей: " + rabbits.size() + " ==="); // ✅ ИСПРАВЛЕНО: было "лис"
    }

    private void stopAllAnimals() {
        System.out.println("Останавливаем всех животных...");
        for (Wolf wolf : wolves) {
            wolf.stopLife();
        }
        for (Fox fox : foxes) {
            fox.stopLife();
        }
        for (Deer deer : deers) {
            deer.stopLife();
        }
    }

    private String determineCellColor(Location location) {
        if (location == null) {
            return "#4A90E2";
        }

        if (location instanceof com.example.island.location.Forest) {
            return "#228B22";
        } else if (location instanceof com.example.island.location.Field) {
            return "#90EE90";
        } else if (location instanceof com.example.island.location.Beach) {
            return "#F0E68C";
        } else if (location instanceof com.example.island.location.River) {
            return "#4169E1";
        } else {
            return "#4A90E2";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}