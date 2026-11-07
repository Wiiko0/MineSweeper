package controller;

public record GameRecord(boolean won, int boardSize, int mineCount, int elapsedSeconds) {
    public GameRecord {
        if (boardSize < 5 || mineCount < 1 || mineCount > boardSize * boardSize - 9
                || elapsedSeconds < 0) {
            throw new IllegalArgumentException("Game record contains invalid values.");
        }
    }

    public String result() {
        return won ? "Won" : "Lost";
    }

    public String board() {
        return boardSize + " x " + boardSize;
    }

    public String time() {
        return elapsedSeconds + " seconds";
    }
}
