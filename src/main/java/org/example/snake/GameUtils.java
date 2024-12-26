package org.example.snake;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.snake.Directions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameUtils {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int CORNER_SIZE = 25;
    public static int SPEED = 5;
    public static int foodX = 0;
    public static int foodY = 0;
    public static int foodColor = 0;
    public static DirectionStrategy currentDirectionStrategy = new MoveLeftStrategy();
    public static List<Corner> snake = new ArrayList<>();
    private static final Random rand = new Random();
    public static final Color SCORE_COLOR = Color.WHITE;
    public static Image appleImage = new Image(GameUtils.class.getResourceAsStream("/apple.png"));
    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(WIDTH);
            foodY = rand.nextInt(HEIGHT);

            for (Corner c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }
            foodColor = rand.nextInt(5);
            SPEED++;
            break;
        }
    }

    public static void initializeSnake() {
        snake.add(new Corner(WIDTH / 2, HEIGHT / 2));
        snake.add(new Corner(WIDTH / 2, HEIGHT / 2));
        snake.add(new Corner(WIDTH / 2, HEIGHT / 2));
    }
    public static void changeDirection(Direction direction) {
        switch (direction) {
            case UP -> currentDirectionStrategy = new MoveUpStrategy();
            case DOWN -> currentDirectionStrategy = new MoveDownStrategy();
            case LEFT -> currentDirectionStrategy = new MoveLeftStrategy();
            case RIGHT -> currentDirectionStrategy = new MoveRightStrategy();
        }
    }
}
