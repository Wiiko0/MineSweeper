package model;

public record BoardConfiguration(int size, int mineCount) {
    public BoardConfiguration {
        if (size < 5) {
            throw new IllegalArgumentException("Board size must be at least 5");
        }
        if (mineCount < 1 || mineCount > size * size - 9) {
            throw new IllegalArgumentException("Mine count does not fit the board");
        }
    }
}
