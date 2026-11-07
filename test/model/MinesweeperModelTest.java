package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MinesweeperModelTest {
    private static final BoardConfiguration CONFIGURATION = new BoardConfiguration(10, 10);
    private MinesweeperModel model;

    @BeforeEach
    void setUp() {
        model = new MinesweeperModel();
        model.newGame(CONFIGURATION);
    }

    @Test
    void newGameCreatesReadyBoard() {
        assertEquals(GameState.READY, model.getState());
        assertEquals(CONFIGURATION.size(), model.getBoard().getSize());
        assertEquals(CONFIGURATION.mineCount(), model.getBoard().getMineCount());
    }

    @Test
    void firstRevealIsNeverAMine() {
        CellPosition firstCell = new CellPosition(0, 0);

        ActionResult result = model.reveal(firstCell);

        assertNotEquals(ActionResult.LOST, result);
        assertNotEquals(GameState.LOST, model.getState());
        assertTrue(model.getBoard().isRevealed(firstCell));
    }

    @Test
    void flagPlacedBeforeFirstRevealIsPreserved() {
        CellPosition flaggedCell = new CellPosition(9, 9);

        assertEquals(ActionResult.FLAG_CHANGED, model.toggleFlag(flaggedCell));
        model.reveal(new CellPosition(0, 0));

        assertTrue(model.getBoard().isFlagged(flaggedCell));
        assertEquals(1, model.getBoard().getFlagsPlaced());
    }

    @Test
    void cannotPlaceMoreFlagsThanMines() {
        model.newGame(new BoardConfiguration(5, 1));
        CellPosition firstFlag = new CellPosition(4, 4);
        CellPosition secondFlag = new CellPosition(3, 4);

        assertEquals(ActionResult.FLAG_CHANGED, model.toggleFlag(firstFlag));
        assertEquals(ActionResult.FLAG_LIMIT_REACHED, model.toggleFlag(secondFlag));
        assertEquals(1, model.getBoard().getFlagsPlaced());
    }

    @Test
    void revealingEverySafeCellWinsTheGame() {
        model.reveal(new CellPosition(0, 0));

        for (int row = 0; row < CONFIGURATION.size(); row++) {
            for (int column = 0; column < CONFIGURATION.size(); column++) {
                CellPosition cell = new CellPosition(row, column);
                if (!model.getBoard().isMine(cell)) {
                    model.reveal(cell);
                }
            }
        }

        assertEquals(GameState.WON, model.getState());
        assertEquals(ActionResult.IGNORED, model.reveal(new CellPosition(0, 0)));
    }
}
