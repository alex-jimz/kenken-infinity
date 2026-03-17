package LogicTier.Statistics;

import static org.junit.Assert.*;

import org.junit.*;

import LogicTier.IllegalScore;

import java.time.Duration;
import LogicTier.TestRunner.TestRunner;

public class StatisticsTest {

    public static void main(String[] args) {
        TestRunner.runTestClass(StatisticsTest.class);
    }

    @Test 
    public void testStatisticsCreator() {
        Statistics s = new Statistics();
        assertEquals(0, s.getAveragePointsPerGame());
        assertEquals(0, s.getBestPoints());
        assertEquals(Duration.ZERO, s.getBestTime());
        assertEquals(0, s.getGamesWonWithoutErrors());
    }

    @Test
    public void testStatisticsModified() {
        Statistics s = new Statistics();
        //1 Game - 1 Won
        s.updateStatistics(75, Duration.parse("PT15M8S"), true, true, false);
        assertEquals(75, s.getTotalPoints());
        assertEquals(75, s.getAveragePointsPerGame());
        assertEquals(75, s.getBestPoints());
        assertEquals(1, s.getGamesWon());
        assertEquals(0, s.getGamesLost());
        assertEquals(0, s.getGamesWonWithoutErrors());
        assertEquals(1, s.getGamesWonWithoutHints());
        assertEquals(1, s.getGamesTotal());
        assertEquals(Duration.parse("PT15M8S"), s.getBestTime());
        assertEquals(Duration.parse("PT15M8S"), s.getAverageTime());

        //2 Games - 2 Won
        s.updateStatistics(85, Duration.parse("PT4M36S"), true, false, false);
        assertEquals(160, s.getTotalPoints());
        assertEquals(80, s.getAveragePointsPerGame());
        assertEquals(85, s.getBestPoints());
        assertEquals(2, s.getGamesWon());
        assertEquals(0, s.getGamesLost());
        assertEquals(1, s.getGamesWonWithoutErrors());
        assertEquals(2, s.getGamesWonWithoutHints());
        assertEquals(2, s.getGamesTotal());
        assertEquals(Duration.parse("PT4M36S"), s.getBestTime());
        assertEquals(Duration.parse("PT9M52S"), s.getAverageTime());

        //3 Games - 2 Won
        s.updateStatistics(0, Duration.parse("PT1H36S"), false, true, true);
        assertEquals(160, s.getTotalPoints());
        assertEquals(80, s.getAveragePointsPerGame());
        assertEquals(85, s.getBestPoints());
        assertEquals(2, s.getGamesWon());
        assertEquals(1, s.getGamesLost());
        assertEquals(1, s.getGamesWonWithoutErrors());
        assertEquals(2, s.getGamesWonWithoutHints());
        assertEquals(3, s.getGamesTotal());
        assertEquals(Duration.parse("PT4M36S"), s.getBestTime());
        assertEquals(Duration.parse("PT9M52S"), s.getAverageTime());

        //4 Games - 2 Won
        s.updateStatistics(0, Duration.parse("PT36S"), false, true, false);
        assertEquals(160, s.getTotalPoints());
        assertEquals(80, s.getAveragePointsPerGame());
        assertEquals(85, s.getBestPoints());
        assertEquals(2, s.getGamesWon());
        assertEquals(2, s.getGamesLost());
        assertEquals(1, s.getGamesWonWithoutErrors());
        assertEquals(2, s.getGamesWonWithoutHints());
        assertEquals(4, s.getGamesTotal());
        assertEquals(Duration.parse("PT4M36S"), s.getBestTime());
        assertEquals(Duration.parse("PT9M52S"), s.getAverageTime());

        //5 Games - 3 Won
        s.updateStatistics(35, Duration.parse("PT8M"), true, true, true);
        assertEquals(195, s.getTotalPoints());
        assertEquals(65, s.getAveragePointsPerGame());
        assertEquals(85, s.getBestPoints());
        assertEquals(3, s.getGamesWon());
        assertEquals(2, s.getGamesLost());
        assertEquals(1, s.getGamesWonWithoutErrors());
        assertEquals(2, s.getGamesWonWithoutHints());
        assertEquals(5, s.getGamesTotal());
        assertEquals(Duration.parse("PT4M36S"), s.getBestTime());
        assertEquals(Duration.parse("PT9M14.666666666S"), s.getAverageTime());
    }

    @Test
    public void testStatisticsInvalidParameters() {
        Statistics s = new Statistics();
        try {
            s.updateStatistics(-85, Duration.parse("PT8M"), true, true, true);
            fail("Expected exception was not thrown");
        }
        catch (IllegalScore e) {
            assertEquals("scoredPoints parameters is not valid.", e.getMessage());
        }

        try {
            s.updateStatistics(150, Duration.parse("PT8M"), true, true, true);
            fail("Expected exception was not thrown");
        }
        catch (IllegalScore e) {
            assertEquals("scoredPoints parameters is not valid.", e.getMessage());
        }
    }
}
