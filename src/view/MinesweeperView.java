package view;

import controller.GameStatistics;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.MinesweeperModel;

public final class MinesweeperView extends JFrame {
    private static final long serialVersionUID = 1L;
    private final CardLayout scenes = new CardLayout();
    private final JPanel sceneContainer = new JPanel(scenes);
    private final MainMenuPanel mainMenu = new MainMenuPanel();
    private final NewGamePanel newGame = new NewGamePanel();
    private final BoardPanel board = new BoardPanel();
    private final EndGamePanel winGame = new EndGamePanel();
    private final EndGamePanel lossGame = new EndGamePanel();
    private final ScoreboardScene scoreboard = new ScoreboardScene();
    private final JLabel status = new JLabel("Click a cell to start", SwingConstants.CENTER);
    private final JLabel timer = new JLabel("Time: 0", SwingConstants.CENTER);

    public MinesweeperView() {
        super("Minesweeper");
        setIconImage(ImageAssets.loadIcon("minesweeperIcon.png", 32, 32).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        Theme.stylePanel(sceneContainer);

        sceneContainer.add(mainMenu, Scene.MAIN_MENU.name());
        sceneContainer.add(newGame, Scene.NEW_GAME.name());
        sceneContainer.add(createGameScene(), Scene.GAME.name());
        sceneContainer.add(scoreboard, Scene.SCOREBOARD.name());
        sceneContainer.add(winGame, Scene.WIN.name());
        sceneContainer.add(lossGame, Scene.LOSS.name());
        setContentPane(sceneContainer);
    }

    private JPanel createGameScene() {
        JPanel game = new JPanel(new BorderLayout(8, 8));
        Theme.stylePanel(game);
        game.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        game.add(status, BorderLayout.NORTH);
        JPanel boardHolder = new JPanel(new GridBagLayout());
        Theme.stylePanel(boardHolder);
        boardHolder.add(board, new GridBagConstraints());
        game.add(boardHolder, BorderLayout.CENTER);
        game.add(timer, BorderLayout.SOUTH);
        status.setForeground(Theme.DARK_PURPLE);
        timer.setForeground(Theme.DARK_PURPLE);
        return game;
    }

    public void showScene(Scene scene) {
        scenes.show(sceneContainer, scene.name());
        pack();
    }

    public void onMainMenuNewGame(ActionListener listener) {
        mainMenu.onNewGame(listener);
    }

    public void onMainMenuScoreboard(ActionListener listener) {
        mainMenu.onScoreboard(listener);
    }

    public void onExit(ActionListener listener) {
        mainMenu.onExit(listener);
    }

    public void onPlay(ActionListener listener) {
        newGame.onPlay(listener);
    }

    public void onNewGameBack(ActionListener listener) {
        newGame.onBack(listener);
    }

    public void onEndGameNewGame(ActionListener listener) {
        winGame.onNewGame(listener);
        lossGame.onNewGame(listener);
    }

    public void onEndGameMainMenu(ActionListener listener) {
        winGame.onMainMenu(listener);
        lossGame.onMainMenu(listener);
    }

    public void onScoreboardBack(ActionListener listener) {
        scoreboard.onBack(listener);
    }

    public model.BoardConfiguration getSelectedConfiguration() {
        return newGame.getConfiguration();
    }

    public void createBoard(int size, MouseListener listener) {
        board.createBoard(size, listener);
    }

    public void render(MinesweeperModel model) {
        board.render(model.getBoard());
    }

    public void showStatus(String message) {
        status.setText(message);
    }

    public void showTime(int seconds) {
        timer.setText("Time: " + seconds);
    }

    public void showEndGame(boolean won, int seconds) {
        if (won) {
            winGame.showResult(true, seconds);
        } else {
            lossGame.showResult(false, seconds);
        }
        showScene(won ? Scene.WIN : Scene.LOSS);
    }

    public void showScoreboard(GameStatistics statistics) {
        scoreboard.showStatistics(statistics);
    }

}
