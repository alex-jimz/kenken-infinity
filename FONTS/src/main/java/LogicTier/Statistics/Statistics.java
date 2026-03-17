package LogicTier.Statistics;

import java.util.*;

import LogicTier.IllegalScore;

import java.time.Duration;

public class Statistics {
    private int totalPoints;
    private int averagePointsPerGame;
    private int bestPoints;
    private int gamesWon;
    private int gamesLost;
    private int gamesWonWithoutErrors;
    private int gamesWonWithoutHints;
    private int gamesTotal;
    private Duration bestTime;
    private Duration averageTime;
    
    public Statistics() {
        this.totalPoints = 0;
        this.averagePointsPerGame = 0;
        this.bestPoints = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.gamesWonWithoutErrors = 0;
        this.gamesWonWithoutHints = 0;
        this.gamesTotal = 0;
        this.bestTime = Duration.ZERO;
        this.averageTime = Duration.ZERO;
    }

    public Statistics(int totalPoints, int averagePointsPerGame, int bestPoints, int gamesWon,
                      int gamesLost, int gamesWonWithoutErrors, int gamesWonWithoutHints, int gamesTotal, Duration bestTime,
                      Duration averageTime) {
        this.totalPoints = totalPoints;
        this.averagePointsPerGame = averagePointsPerGame;
        this.bestPoints = bestPoints;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesWonWithoutErrors = gamesWonWithoutErrors;
        this.gamesWonWithoutHints = gamesWonWithoutHints;
        this.gamesTotal = gamesTotal;
        this.bestTime = bestTime;
        this.averageTime = averageTime;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getAveragePointsPerGame() {
        return averagePointsPerGame;
    }

    public int getBestPoints() {
        return bestPoints;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getGamesWonWithoutErrors() {
        return gamesWonWithoutErrors;
    }

    public int getGamesWonWithoutHints() {
        return gamesWonWithoutHints;
    }

    public int getGamesTotal() {
        return gamesTotal;
    }

    public Duration getBestTime() {
        return bestTime;
    }

    public Duration getAverageTime() {
        return averageTime;
    }

    public void updateStatistics(int scoredPoints, Duration duration, boolean victory, boolean errors, boolean hints) {
        if (scoredPoints < 0 || scoredPoints > 100) {
            throw new IllegalScore("scoredPoints parameters is not valid.");
        }
        ++gamesTotal;
        if (victory) {

            ++gamesWon;
            totalPoints += scoredPoints;
            if (scoredPoints > bestPoints) bestPoints = scoredPoints;
            averagePointsPerGame = ((averagePointsPerGame*(gamesWon-1)) + scoredPoints)/(gamesWon);
            Duration aux = averageTime.multipliedBy(gamesWon-1);
            aux = aux.plus(duration);
            averageTime = aux.dividedBy(gamesWon);
            int compareTimes = bestTime.compareTo(duration);
            if (compareTimes > 0 || gamesWon == 1) bestTime = duration;
            if (!errors) ++gamesWonWithoutErrors;
            if (!hints) ++gamesWonWithoutHints;

        }
        else ++gamesLost;

    }

    public Vector<Integer> getIntegerStats() {
        Vector<Integer> integerStats = new Vector<>();
        integerStats.add(totalPoints);
        integerStats.add(averagePointsPerGame);
        integerStats.add(bestPoints);
        integerStats.add(gamesWon);
        integerStats.add(gamesLost);
        integerStats.add(gamesWonWithoutErrors);
        integerStats.add(gamesWonWithoutHints);
        integerStats.add(gamesTotal);
        return integerStats;
    }

    public Vector<String> getDurationStats() {
        Vector<String> durationStats = new Vector<>();
        durationStats.add(bestTime.toString());
        durationStats.add(averageTime.toString());
        return durationStats;
    }
}
