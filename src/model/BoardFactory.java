package model;

import java.util.Random;

public final class BoardFactory {
    private final Random random;

    public BoardFactory() {
        this(new Random());
    }

    BoardFactory(Random random) {
        this.random = random;
    }

    public Board create(BoardConfiguration configuration) {
        return new Board(configuration, new boolean[configuration.size()][configuration.size()]);
    }

    public Board createForFirstMove(
            BoardConfiguration configuration, CellPosition safeCell) {
        boolean[][] mines = new boolean[configuration.size()][configuration.size()];
        int placed = 0;
        while (placed < configuration.mineCount()) {
            int row = random.nextInt(configuration.size());
            int column = random.nextInt(configuration.size());
            if (mines[row][column] || isSafeZone(safeCell, row, column)) {
                continue;
            }
            mines[row][column] = true;
            placed++;
        }
        return new Board(configuration, mines);
    }

    private boolean isSafeZone(CellPosition safeCell, int row, int column) {
        return Math.abs(row - safeCell.row()) <= 1
                && Math.abs(column - safeCell.column()) <= 1;
    }
}
