package org.example.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameLogic {
    private static final Image background = new Image(GameLogic.class.getResourceAsStream("/background.jpg"));
    private static final Image apple = new Image(GameLogic.class.getResourceAsStream("/apple.png")); // Load apple image

    public static void tick(GraphicsContext gc) {
        gc.drawImage(background, 0, 0, GameUtils.WIDTH * GameUtils.CORNER_SIZE, GameUtils.HEIGHT * GameUtils.CORNER_SIZE);
        // Check if the game is over using GameState
        if (GameState.getInstance().isGameOver()) {


            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 100, 250);

            // Display final score
            gc.setFont(new Font("", 30));
            gc.fillText("Final Score: " + GameState.getInstance().getScore(), 100, 300);

            return;
        }

        // Move snake
        for (int i = GameUtils.snake.size() - 1; i >= 1; i--) {
            GameUtils.snake.get(i).x = GameUtils.snake.get(i - 1).x;
            GameUtils.snake.get(i).y = GameUtils.snake.get(i - 1).y;
        }

// Move the snake using the current direction strategy
        GameUtils.currentDirectionStrategy.move();

// After moving, check if the snake is out of bounds or collided with itself
        if (GameUtils.snake.get(0).x < 0 || GameUtils.snake.get(0).x >= GameUtils.WIDTH ||
                GameUtils.snake.get(0).y < 0 || GameUtils.snake.get(0).y >= GameUtils.HEIGHT) {
            GameState.getInstance().setGameOver(true); // Use GameState for wall collision
        }

        // Check if the snake eats food
        if (GameUtils.foodX == GameUtils.snake.get(0).x && GameUtils.foodY == GameUtils.snake.get(0).y) {
            GameUtils.snake.add(new Corner(-1, -1));  // Add a new segment to the snake
            GameUtils.newFood();  // Generate new food
            GameState.getInstance().incrementScore();  // Increment score using GameState
        }

        // Check for self-collision
        for (int i = 1; i < GameUtils.snake.size(); i++) {
            if (GameUtils.snake.get(0).x == GameUtils.snake.get(i).x &&
                    GameUtils.snake.get(0).y == GameUtils.snake.get(i).y) {
                GameState.getInstance().setGameOver(true); // Use GameState
            }
        }

        // Draw background
        gc.setFill(Color.BLACK);
        gc.drawImage(background, 0, 0, GameUtils.WIDTH * GameUtils.CORNER_SIZE, GameUtils.HEIGHT * GameUtils.CORNER_SIZE);
        // Draw score from GameState
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + GameState.getInstance().getScore(), 10, 30); // Use GameState's score

        // Draw food as an apple
        gc.drawImage(apple, GameUtils.foodX * GameUtils.CORNER_SIZE, GameUtils.foodY * GameUtils.CORNER_SIZE, GameUtils.CORNER_SIZE, GameUtils.CORNER_SIZE);


        // Draw snake
        for (Corner c : GameUtils.snake) {
            gc.setFill(Color.LIGHTGREEN);
            gc.fillRect(c.x * GameUtils.CORNER_SIZE, c.y * GameUtils.CORNER_SIZE, GameUtils.CORNER_SIZE - 1, GameUtils.CORNER_SIZE - 1);
            gc.setFill(Color.GREEN);
            gc.fillRect(c.x * GameUtils.CORNER_SIZE, c.y * GameUtils.CORNER_SIZE, GameUtils.CORNER_SIZE - 2, GameUtils.CORNER_SIZE - 2);
        }
    }
}
