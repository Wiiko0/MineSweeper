package controller;

import javax.swing.Timer;
import java.awt.event.ActionListener;

public final class GameTimer {
    private final Timer timer;
    private int seconds;

    public GameTimer(ActionListener tickListener) {
        timer = new Timer(1000, tickListener);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        stop();
        seconds = 0;
    }

    public void tick() {
        seconds++;
    }

    public int getSeconds() {
        return seconds;
    }
}
