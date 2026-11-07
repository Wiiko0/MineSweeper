package persistence;

import controller.GameRecord;
import controller.GameStatistics;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public final class ScoreboardRepository {
    private static final String HEADER = "MINESWEEPER_SCOREBOARD_V1";
    private final Path scoreboardFile;

    public ScoreboardRepository() {
        scoreboardFile = Path.of(System.getProperty("user.home"), ".minesweeper", "scoreboard.txt");
    }

    public SavedScoreboard load() throws IOException {
        if (Files.notExists(scoreboardFile)) {
            return new SavedScoreboard(0, 0, Integer.MAX_VALUE, List.of());
        }

        List<String> lines = Files.readAllLines(scoreboardFile, StandardCharsets.UTF_8);
        if (lines.size() < 4 || !HEADER.equals(lines.get(0))) {
            throw new IOException("The scoreboard file has an invalid format.");
        }

        int wins = parseValue(lines.get(1), "wins");
        int losses = parseValue(lines.get(2), "losses");
        int bestTime = parseValue(lines.get(3), "bestTime");
        List<GameRecord> games = new ArrayList<>();
        for (int index = 4; index < lines.size(); index++) {
            if (!lines.get(index).isBlank()) {
                games.add(parseGame(lines.get(index)));
            }
        }
        return new SavedScoreboard(wins, losses, bestTime, games);
    }

    public void save(GameStatistics statistics) throws IOException {
        Path directory = scoreboardFile.getParent();
        Files.createDirectories(directory);

        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        lines.add("wins=" + statistics.getWins());
        lines.add("losses=" + statistics.getLosses());
        lines.add("bestTime=" + statistics.getBestTime());
        for (GameRecord game : statistics.getRecentGames()) {
            lines.add("game=" + (game.won() ? "won" : "lost")
                    + "," + game.boardSize()
                    + "," + game.mineCount()
                    + "," + game.elapsedSeconds());
        }

        Path temporaryFile = scoreboardFile.resolveSibling("scoreboard.tmp");
        Files.write(temporaryFile, lines, StandardCharsets.UTF_8);
        try {
            Files.move(temporaryFile, scoreboardFile,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException exception) {
            Files.move(temporaryFile, scoreboardFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private int parseValue(String line, String key) throws IOException {
        String prefix = key + "=";
        if (!line.startsWith(prefix)) {
            throw new IOException("The scoreboard file is missing " + key + ".");
        }
        try {
            int value = Integer.parseInt(line.substring(prefix.length()));
            if (value < 0) {
                throw new IOException("The scoreboard contains a negative " + key + ".");
            }
            return value;
        } catch (NumberFormatException exception) {
            throw new IOException("The scoreboard contains an invalid " + key + ".", exception);
        }
    }

    private GameRecord parseGame(String line) throws IOException {
        String[] parts = line.split("=", 2);
        if (parts.length != 2 || !"game".equals(parts[0])) {
            throw new IOException("The scoreboard contains an invalid game entry.");
        }
        String[] values = parts[1].split(",");
        if (values.length != 4 || (!"won".equals(values[0]) && !"lost".equals(values[0]))) {
            throw new IOException("The scoreboard contains an invalid game entry.");
        }
        try {
            return new GameRecord(
                    "won".equals(values[0]),
                    Integer.parseInt(values[1]),
                    Integer.parseInt(values[2]),
                    Integer.parseInt(values[3]));
        } catch (IllegalArgumentException exception) {
            throw new IOException("The scoreboard contains an invalid game entry.", exception);
        }
    }
}
