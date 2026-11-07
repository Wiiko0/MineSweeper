package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

public final class EndGamePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JLabel message = new JLabel();
    private final JButton newGame = new JButton("New game");
    private final JButton mainMenu = new JButton("Main menu");

    public EndGamePanel() {
        Theme.stylePanel(this);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        add(message, constraints);
        message.setForeground(Theme.DARK_PURPLE);
        Theme.styleButton(newGame);
        Theme.styleButton(mainMenu);
        constraints.gridy = 1;
        add(newGame, constraints);
        constraints.gridy = 2;
        add(mainMenu, constraints);
    }

    public void showResult(boolean won, int seconds) {
        message.setText(won ? "You won in " + seconds + " seconds!" : "Boom! You lost.");
        message.setFont(message.getFont().deriveFont(24f));
    }

    public void onNewGame(ActionListener listener) {
        newGame.addActionListener(listener);
    }

    public void onMainMenu(ActionListener listener) {
        mainMenu.addActionListener(listener);
    }
}
