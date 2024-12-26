package org.example.snake;

public class GameState {
    private static GameState instance = new GameState();
    private boolean gameOver = false;
    private int score = 0;

    private GameState() {} // Private constructor to implement singleton pattern

    public static GameState getInstance() {
        return instance;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public void reset() {
        this.score = 0;
        this.gameOver = false;
    }
}
