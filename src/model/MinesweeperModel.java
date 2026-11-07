package model;

public final class MinesweeperModel {
    private final BoardFactory boardFactory = new BoardFactory();
    private Board board;
    private BoardConfiguration configuration;
    private GameState state = GameState.READY;

    public void newGame(BoardConfiguration configuration) {
        this.configuration = configuration;
        board = boardFactory.create(configuration);
        state = GameState.READY;
    }

    public ActionResult reveal(CellPosition cell) {
        if (isFinished() || board.isRevealed(cell) || board.isFlagged(cell)) {
            return ActionResult.IGNORED;
        }
        if (state == GameState.READY) {
            Board initialBoard = board;
            board = boardFactory.createForFirstMove(configuration, cell);
            copyFlags(initialBoard);
            state = GameState.PLAYING;
        }
        if (board.isMine(cell)) {
            state = GameState.LOST;
            return ActionResult.LOST;
        }
        board.reveal(cell);
        if (board.hasWon()) {
            state = GameState.WON;
            return ActionResult.WON;
        }
        return ActionResult.REVEALED;
    }

    public ActionResult toggleFlag(CellPosition cell) {
        if (isFinished() || board.isRevealed(cell)) {
            return ActionResult.IGNORED;
        }
        if (!board.isFlagged(cell) && board.getFlagsPlaced() >= board.getMineCount()) {
            return ActionResult.FLAG_LIMIT_REACHED;
        }
        board.toggleFlag(cell);
        return ActionResult.FLAG_CHANGED;
    }

    private boolean isFinished() {
        return state == GameState.WON || state == GameState.LOST;
    }

    private void copyFlags(Board initialBoard) {
        for (int row = 0; row < initialBoard.getSize(); row++) {
            for (int column = 0; column < initialBoard.getSize(); column++) {
                CellPosition cell = new CellPosition(row, column);
                if (initialBoard.isFlagged(cell)) {
                    board.toggleFlag(cell);
                }
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public GameState getState() {
        return state;
    }
}
