package org.example.snake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.snake.Directions.MoveDownStrategy;
import org.example.snake.Directions.MoveLeftStrategy;
import org.example.snake.Directions.MoveRightStrategy;
import org.example.snake.Directions.MoveUpStrategy;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            GameUtils.newFood();

            VBox root = new VBox();
            Canvas canvas = new Canvas(GameUtils.WIDTH * GameUtils.CORNER_SIZE, GameUtils.HEIGHT * GameUtils.CORNER_SIZE);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            root.getChildren().add(canvas);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        GameLogic.tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / GameUtils.SPEED) {
                        lastTick = now;
                        GameLogic.tick(gc);
                    }
                }
            }.start();

            Scene scene = new Scene(root, GameUtils.WIDTH * GameUtils.CORNER_SIZE, GameUtils.HEIGHT * GameUtils.CORNER_SIZE);

            // Controls
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                switch (key.getCode()) {
                    case W -> GameUtils.currentDirectionStrategy = new MoveUpStrategy();
                    case A -> GameUtils.currentDirectionStrategy = new MoveLeftStrategy();
                    case S -> GameUtils.currentDirectionStrategy = new MoveDownStrategy();
                    case D -> GameUtils.currentDirectionStrategy = new MoveRightStrategy();
                }
            });

            // Add initial snake parts
            GameUtils.initializeSnake();

            // Display score at start
            gc.setFill(GameUtils.SCORE_COLOR);
            gc.fillText("Score: " + GameState.getInstance().getScore(), 10, 30);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Snake Game");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
