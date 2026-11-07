package model;

public final class Board {
    private final int size;
    private final int mineCount;
    private final boolean[][] mines;
    private final boolean[][] revealed;
    private final boolean[][] flagged;
    private int revealedCount;
    private int flagsPlaced;

    Board(BoardConfiguration configuration, boolean[][] mines) {
        size = configuration.size();
        mineCount = configuration.mineCount();
        this.mines = mines;
        revealed = new boolean[size][size];
        flagged = new boolean[size][size];
    }

    public boolean reveal(CellPosition cell) {
        if (!isInBounds(cell) || revealed[cell.row()][cell.column()]
                || flagged[cell.row()][cell.column()]) {
            return false;
        }
        revealed[cell.row()][cell.column()] = true;
        revealedCount++;
        if (countAdjacentMines(cell) == 0) {
            revealNeighbors(cell);
        }
        return true;
    }

    public boolean toggleFlag(CellPosition cell) {
        if (!isInBounds(cell) || revealed[cell.row()][cell.column()]) {
            return false;
        }
        flagged[cell.row()][cell.column()] = !flagged[cell.row()][cell.column()];
        flagsPlaced += flagged[cell.row()][cell.column()] ? 1 : -1;
        return true;
    }

    private void revealNeighbors(CellPosition cell) {
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                reveal(new CellPosition(cell.row() + rowOffset, cell.column() + columnOffset));
            }
        }
    }

    private boolean isInBounds(CellPosition cell) {
        return cell.row() >= 0 && cell.row() < size
                && cell.column() >= 0 && cell.column() < size;
    }

    public int countAdjacentMines(CellPosition cell) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                CellPosition neighbor = new CellPosition(
                        cell.row() + rowOffset, cell.column() + columnOffset);
                if (isInBounds(neighbor) && mines[neighbor.row()][neighbor.column()]) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getSize() {
        return size;
    }

    public int getMineCount() {
        return mineCount;
    }

    public int getFlagsPlaced() {
        return flagsPlaced;
    }

    public boolean isMine(CellPosition cell) {
        return mines[cell.row()][cell.column()];
    }

    public boolean isRevealed(CellPosition cell) {
        return revealed[cell.row()][cell.column()];
    }

    public boolean isFlagged(CellPosition cell) {
        return flagged[cell.row()][cell.column()];
    }

    public boolean hasWon() {
        return revealedCount == size * size - mineCount;
    }
}
