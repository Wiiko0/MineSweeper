package controller;

import model.ActionResult;
import model.BoardConfiguration;
import model.CellPosition;
import model.GameState;
import model.MinesweeperModel;
import persistence.SavedScoreboard;
import persistence.ScoreboardRepository;
import view.MinesweeperView;
import view.Scene;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public final class MinesweeperController {
    private final MinesweeperView view;
    private final MinesweeperModel model = new MinesweeperModel();
    private final GameStatistics statistics = new GameStatistics();
    private final ScoreboardRepository scoreboardRepository = new ScoreboardRepository();
    private final GameTimer timer;

    public MinesweeperController(MinesweeperView view) {
        this.view = view;
        timer = new GameTimer(event -> handleTimerTick());
        loadScoreboard();
        connectSceneActions();
        view.showScoreboard(statistics);
        view.showScene(Scene.MAIN_MENU);
    }

    private void loadScoreboard() {
        try {
            SavedScoreboard saved = scoreboardRepository.load();
            statistics.restore(saved.wins(), saved.losses(), saved.bestTime(), saved.recentGames());
        } catch (IOException | IllegalArgumentException exception) {
            showPersistenceError("Could not load the saved scoreboard.", exception);
        }
    }

    private void saveScoreboard() {
        try {
            scoreboardRepository.save(statistics);
        } catch (IOException exception) {
            showPersistenceError("Could not save the scoreboard.", exception);
        }
    }

    private void showPersistenceError(String message, Exception exception) {
        JOptionPane.showMessageDialog(
                view,
                message + "\n" + exception.getMessage(),
                "Scoreboard error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void handleTimerTick() {
        timer.tick();
        view.showTime(timer.getSeconds());
    }

    private void connectSceneActions() {
        view.onMainMenuNewGame(event -> view.showScene(Scene.NEW_GAME));
        view.onMainMenuScoreboard(event -> {
            view.showScoreboard(statistics);
            view.showScene(Scene.SCOREBOARD);
        });
        view.onExit(event -> System.exit(0));
        view.onNewGameBack(event -> view.showScene(Scene.MAIN_MENU));
        view.onPlay(event -> startNewGame());
        view.onEndGameNewGame(event -> view.showScene(Scene.NEW_GAME));
        view.onEndGameMainMenu(event -> view.showScene(Scene.MAIN_MENU));
        view.onScoreboardBack(event -> view.showScene(Scene.MAIN_MENU));
    }

    private void startNewGame() {
        timer.reset();
        view.showTime(0);
        BoardConfiguration configuration = view.getSelectedConfiguration();
        model.newGame(configuration);
        view.createBoard(model.getBoard().getSize(), new CellMouseListener());
        view.render(model);
        view.showStatus("Click a cell to start");
        view.showScene(Scene.GAME);
    }

    private void handleReveal(CellPosition cell) {
        var result = model.reveal(cell);
        if (result == ActionResult.IGNORED) {
            return;
        }
        if (result == ActionResult.REVEALED) {
            if (model.getState() == GameState.PLAYING) {
                timer.start();
            }
            view.showStatus("Game in progress");
        } else if (result == ActionResult.LOST) {
            timer.stop();
            statistics.recordGame(new GameRecord(
                    false,
                    model.getBoard().getSize(),
                    model.getBoard().getMineCount(),
                    timer.getSeconds()));
            saveScoreboard();
            view.showEndGame(false, timer.getSeconds());
        } else if (result == ActionResult.WON) {
            timer.stop();
            statistics.recordGame(new GameRecord(
                    true,
                    model.getBoard().getSize(),
                    model.getBoard().getMineCount(),
                    timer.getSeconds()));
            saveScoreboard();
            view.showEndGame(true, timer.getSeconds());
            return;
        }
        view.render(model);
    }

    private void handleFlag(CellPosition cell) {
        var result = model.toggleFlag(cell);
        if (result == ActionResult.FLAG_LIMIT_REACHED) {
            view.showStatus("You can only place " + model.getBoard().getMineCount() + " flags.");
        } else if (result == ActionResult.FLAG_CHANGED) {
            int minesLeft = model.getBoard().getMineCount() - model.getBoard().getFlagsPlaced();
            view.showStatus("Mines left: " + minesLeft);
            view.render(model);
        }
    }

    private final class CellMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            String[] coordinates = event.getComponent().getName().split(",");
            int row = Integer.parseInt(coordinates[0]);
            int column = Integer.parseInt(coordinates[1]);
            CellPosition cell = new CellPosition(row, column);
            if (SwingUtilities.isRightMouseButton(event)) {
                handleFlag(cell);
            } else if (SwingUtilities.isLeftMouseButton(event)) {
                handleReveal(cell);
            }
        }
    }
}
