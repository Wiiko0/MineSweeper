package app;

import controller.MinesweeperController;
import javax.swing.SwingUtilities;
import view.MinesweeperView;

public final class MinesweeperApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MinesweeperView view = new MinesweeperView();
            new MinesweeperController(view);
            view.setVisible(true);
        });
    }
}
