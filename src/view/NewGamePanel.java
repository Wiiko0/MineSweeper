package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import model.BoardConfiguration;

public final class NewGamePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JRadioButton beginner = new JRadioButton("Beginner (9 x 9, 10 mines)", true);
    private final JRadioButton intermediate = new JRadioButton("Intermediate (16 x 16, 40 mines)");
    private final JRadioButton expert = new JRadioButton("Expert (20 x 20, 80 mines)");
    private final JRadioButton custom = new JRadioButton("Custom");
    private final JSlider sizeSlider = new JSlider(5, 20, 9);
    private final JSlider mineSlider = new JSlider(1, 10, 10);
    private final JLabel sizeValue = new JLabel();
    private final JLabel mineValue = new JLabel();
    private final JPanel customPanel = new JPanel(new GridLayout(2, 3, 8, 8));
    private final JButton play = new JButton("Play");
    private final JButton back = new JButton("Back");

    public NewGamePanel() {
        Theme.stylePanel(this);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        JLabel title = new JLabel("Choose difficulty", JLabel.CENTER);
        title.setForeground(Theme.DARK_PURPLE);
        title.setFont(title.getFont().deriveFont(24f));
        add(title, BorderLayout.NORTH);

        JPanel difficulties = new JPanel(new GridLayout(4, 1, 8, 8));
        Theme.stylePanel(difficulties);
        ButtonGroup group = new ButtonGroup();
        group.add(beginner);
        group.add(intermediate);
        group.add(expert);
        group.add(custom);
        difficulties.add(beginner);
        difficulties.add(intermediate);
        difficulties.add(expert);
        difficulties.add(custom);
        add(difficulties, BorderLayout.CENTER);

        customPanel.setBorder(BorderFactory.createTitledBorder("Custom board"));
        Theme.stylePanel(customPanel);
        Theme.styleSlider(sizeSlider);
        Theme.styleSlider(mineSlider);
        Theme.styleButton(play);
        Theme.styleButton(back);
        beginner.setBackground(Theme.BACKGROUND);
        intermediate.setBackground(Theme.BACKGROUND);
        expert.setBackground(Theme.BACKGROUND);
        custom.setBackground(Theme.BACKGROUND);
        beginner.setForeground(Theme.DARK_PURPLE);
        intermediate.setForeground(Theme.DARK_PURPLE);
        expert.setForeground(Theme.DARK_PURPLE);
        custom.setForeground(Theme.DARK_PURPLE);
        customPanel.add(new JLabel("Board size"));
        customPanel.add(sizeSlider);
        customPanel.add(sizeValue);
        customPanel.add(new JLabel("Mines"));
        customPanel.add(mineSlider);
        customPanel.add(mineValue);
        JPanel bottom = new JPanel(new BorderLayout(8, 8));
        Theme.stylePanel(bottom);
        bottom.add(customPanel, BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        Theme.stylePanel(buttons);
        buttons.add(back);
        buttons.add(play);
        bottom.add(buttons, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);
        for (java.awt.Component component : customPanel.getComponents()) {
            if (component instanceof JLabel label) {
                Theme.styleLabel(label);
            }
        }

        updateMineLimit();
        updateLabels();
        updateSliderState();
        custom.addActionListener(event -> updateSliderState());
        beginner.addActionListener(event -> updateSliderState());
        intermediate.addActionListener(event -> updateSliderState());
        expert.addActionListener(event -> updateSliderState());
        sizeSlider.addChangeListener(event -> {
            updateMineLimit();
            updateLabels();
        });
        mineSlider.addChangeListener(event -> updateLabels());
    }

    private void updateSliderState() {
        boolean customSelected = custom.isSelected();
        sizeSlider.setEnabled(customSelected);
        mineSlider.setEnabled(customSelected);
    }

    private void updateMineLimit() {
        mineSlider.setMaximum(Math.max(1, sizeSlider.getValue() * sizeSlider.getValue() - 9));
        if (mineSlider.getValue() > mineSlider.getMaximum()) {
            mineSlider.setValue(mineSlider.getMaximum());
        }
    }

    private void updateLabels() {
        sizeValue.setText(String.valueOf(sizeSlider.getValue()));
        mineValue.setText(String.valueOf(mineSlider.getValue()));
    }

    public void onPlay(ActionListener listener) {
        play.addActionListener(listener);
    }

    public void onBack(ActionListener listener) {
        back.addActionListener(listener);
    }

    public BoardConfiguration getConfiguration() {
        if (beginner.isSelected()) {
            return new BoardConfiguration(9, 10);
        }
        if (intermediate.isSelected()) {
            return new BoardConfiguration(16, 40);
        }
        if (expert.isSelected()) {
            return new BoardConfiguration(20, 80);
        }
        return new BoardConfiguration(sizeSlider.getValue(), mineSlider.getValue());
    }
}
