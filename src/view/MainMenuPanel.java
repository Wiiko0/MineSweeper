package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

public final class MainMenuPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JButton newGame = new JButton("New game");
    private final JButton scoreboard = new JButton("Scoreboard");
    private final JButton exit = new JButton("Exit");

    public MainMenuPanel() {
        Theme.stylePanel(this);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(8, 8, 8, 8);

        JLabel title = new JLabel("MINESWEEPER", SwingConstants.CENTER);
        title.setForeground(Theme.DARK_PURPLE);
        title.setFont(title.getFont().deriveFont(30f));
        constraints.gridy = 0;
        add(title, constraints);
        addButton(newGame, constraints, 1);
        addButton(scoreboard, constraints, 2);
        addButton(exit, constraints, 3);
    }

    private void addButton(JButton button, GridBagConstraints constraints, int row) {
        Theme.styleButton(button);
        constraints.gridy = row;
        add(button, constraints);
    }

    public void onNewGame(ActionListener listener) {
        newGame.addActionListener(listener);
    }

    public void onScoreboard(ActionListener listener) {
        scoreboard.addActionListener(listener);
    }

    public void onExit(ActionListener listener) {
        exit.addActionListener(listener);
    }
}
