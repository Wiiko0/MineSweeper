package view;

import model.Board;
import model.CellPosition;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;

public final class BoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int CELL_SIZE = 40;
    private final ImageIcon flagIcon = ImageAssets.loadIcon("flag.png", CELL_SIZE, CELL_SIZE);
    private JButton[][] cells;

    public void createBoard(int size, MouseListener listener) {
        removeAll();
        setLayout(new GridLayout(size, size, 1, 1));
        int boardPixels = size * CELL_SIZE + size - 1;
        setPreferredSize(new java.awt.Dimension(boardPixels, boardPixels));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setBackground(Theme.DARK_PURPLE);
        cells = new JButton[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                JButton cell = createCell(row, column, listener);
                cells[row][column] = cell;
                add(cell);
            }
        }
        revalidate();
        repaint();
    }

    private JButton createCell(int row, int column, MouseListener listener) {
        JButton cell = new JButton();
        cell.setName(row + "," + column);
        cell.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        cell.setMargin(new Insets(0, 0, 0, 0));
        cell.setFocusPainted(false);
        cell.setPreferredSize(new java.awt.Dimension(CELL_SIZE, CELL_SIZE));
        cell.setBackground(Theme.HIDDEN_CELL);
        cell.addMouseListener(listener);
        return cell;
    }

    public void render(Board board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 0; column < board.getSize(); column++) {
                renderCell(board, new CellPosition(row, column));
            }
        }
    }

    private void renderCell(Board board, CellPosition position) {
        JButton cell = cells[position.row()][position.column()];
        cell.setEnabled(!board.isRevealed(position));
        cell.setText("");
        cell.setIcon(null);
        cell.setBorderPainted(true);
        cell.setContentAreaFilled(true);
        cell.setBorder(null);
        cell.setForeground(Theme.DARK_PURPLE);
        if (board.isRevealed(position)) {
            cell.setBackground(Color.WHITE);
            int adjacent = board.countAdjacentMines(position);
            if (adjacent > 0) {
                cell.setText(String.valueOf(adjacent));
                cell.setForeground(numberColor(adjacent));
            }
        } else if (board.isFlagged(position)) {
            cell.setBackground(Color.WHITE);
            cell.setBorderPainted(false);
            cell.setContentAreaFilled(false);
            cell.setIcon(flagIcon);
        } else {
            cell.setBackground(Theme.HIDDEN_CELL);
        }
    }

    private Color numberColor(int number) {
        Color[] colors = {Color.BLUE, new Color(0, 120, 0), Color.RED,
                new Color(0, 0, 130), Color.MAGENTA, Color.CYAN, Color.BLACK, Color.GRAY};
        return colors[number - 1];
    }
}
