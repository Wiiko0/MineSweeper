package view;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public final class Theme {
    public static final Color PLUM = new Color(106, 78, 118);
    public static final Color DARK_PURPLE = new Color(62, 47, 72);
    public static final Color MAUVE = new Color(190, 163, 198);
    public static final Color BACKGROUND = new Color(248, 245, 249);
    public static final Color WHITE = Color.WHITE;
    public static final Color HIDDEN_CELL = new Color(222, 211, 228);

    private Theme() {
    }

    public static void styleButton(AbstractButton button) {
        button.setBackground(PLUM);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(MAUVE, 1, true));
        button.setFont(button.getFont().deriveFont(Font.BOLD));
    }

    public static void stylePanel(JComponent component) {
        component.setBackground(BACKGROUND);
        component.setForeground(DARK_PURPLE);
    }

    public static void styleSlider(JSlider slider) {
        slider.setBackground(BACKGROUND);
        slider.setForeground(PLUM);
        slider.setUI(new BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics graphics) {
                Graphics2D g = (Graphics2D) graphics.create();
                g.setColor(slider.isEnabled() ? MAUVE : new Color(218, 211, 220));
                int trackY = trackRect.y + trackRect.height / 2 - 2;
                g.fillRoundRect(trackRect.x, trackY, trackRect.width, 4, 4, 4);
                g.dispose();
            }

            @Override
            public void paintThumb(Graphics graphics) {
                Graphics2D g = (Graphics2D) graphics.create();
                g.setColor(slider.isEnabled() ? PLUM : new Color(190, 182, 193));
                g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
                g.setColor(BACKGROUND);
                g.drawOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
                g.dispose();
            }
        });
    }

    public static void styleLabel(JLabel label) {
        label.setForeground(DARK_PURPLE);
    }
}
