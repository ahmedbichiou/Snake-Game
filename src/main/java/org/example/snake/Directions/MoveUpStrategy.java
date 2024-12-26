package org.example.snake.Directions;

import org.example.snake.GameUtils;

public class MoveUpStrategy implements DirectionStrategy {
    @Override
    public void move() {
        GameUtils.snake.get(0).y--;
    }
}