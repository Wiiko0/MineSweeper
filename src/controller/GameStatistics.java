package controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class GameStatistics {
    private static final int MAX_HISTORY_SIZE = 10;

    private final Deque<GameRecord> recentGames = new ArrayDeque<>();
    private int wins;
    private int losses;
    private int bestTime = Integer.MAX_VALUE;

    public void recordGame(GameRecord game) {
        if (recentGames.size() == MAX_HISTORY_SIZE) {
            recentGames.removeLast();
        }
        recentGames.addFirst(game);
        if (game.won()) {
            wins++;
            bestTime = Math.min(bestTime, game.elapsedSeconds());
        } else {
            losses++;
        }
    }

    public void restore(int wins, int losses, int bestTime, List<GameRecord> games) {
        if (wins < 0 || losses < 0
                || (bestTime < 0 && bestTime != Integer.MAX_VALUE)
                || games.size() > MAX_HISTORY_SIZE) {
            throw new IllegalArgumentException("Saved statistics contain invalid values.");
        }
        recentGames.clear();
        recentGames.addAll(games);
        this.wins = wins;
        this.losses = losses;
        this.bestTime = bestTime;
    }

    public List<GameRecord> getRecentGames() {
        return new ArrayList<>(recentGames);
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getBestTime() {
        return bestTime;
    }
}
