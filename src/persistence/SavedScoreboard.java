package persistence;

import controller.GameRecord;

import java.util.List;

public record SavedScoreboard(int wins, int losses, int bestTime, List<GameRecord> recentGames) {
}
