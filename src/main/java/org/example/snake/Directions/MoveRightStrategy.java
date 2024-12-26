package org.example.snake.Directions;

import org.example.snake.GameUtils;

public class MoveRightStrategy implements DirectionStrategy {
    @Override
    public void move() {
        GameUtils.snake.get(0).x++;
    }
}