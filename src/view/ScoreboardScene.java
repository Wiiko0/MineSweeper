package view;

import controller.GameStatistics;
import controller.GameRecord;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

public final class ScoreboardScene extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JLabel scores = new JLabel("", JLabel.CENTER);
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Result", "Board", "Bombs", "Time"}, 0) {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable gamesTable = new JTable(tableModel);
    private final JButton back = new JButton("Main menu");

    public ScoreboardScene() {
        Theme.stylePanel(this);
        setLayout(new BorderLayout(12, 12));
        JLabel title = new JLabel("Scoreboard", JLabel.CENTER);
        title.setForeground(Theme.DARK_PURPLE);
        add(title, BorderLayout.NORTH);
        gamesTable.setFillsViewportHeight(true);
        gamesTable.setAutoCreateRowSorter(true);
        gamesTable.setBackground(Theme.WHITE);
        gamesTable.setForeground(Theme.DARK_PURPLE);
        gamesTable.getTableHeader().setBackground(Theme.PLUM);
        gamesTable.getTableHeader().setForeground(Theme.WHITE);
        add(new JScrollPane(gamesTable), BorderLayout.CENTER);
        JPanel footer = new JPanel(new BorderLayout(8, 8));
        footer.add(scores, BorderLayout.CENTER);
        footer.add(back, BorderLayout.EAST);
        Theme.stylePanel(footer);
        Theme.styleButton(back);
        add(footer, BorderLayout.SOUTH);
    }

    public void showStatistics(GameStatistics statistics) {
        String best = statistics.getBestTime() == Integer.MAX_VALUE
                ? "-" : statistics.getBestTime() + " seconds";
        scores.setText("<html><center>Wins: " + statistics.getWins()
                + "<br>Losses: " + statistics.getLosses()
                + "<br>Best time: " + best + "</center></html>");

        tableModel.setRowCount(0);
        for (GameRecord game : statistics.getRecentGames()) {
            tableModel.addRow(new Object[]{
                    game.result(), game.board(), game.mineCount(), game.time()
            });
        }
    }

    public void onBack(ActionListener listener) {
        back.addActionListener(listener);
    }
}
