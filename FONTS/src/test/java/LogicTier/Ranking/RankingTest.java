package LogicTier.Ranking;

import org.junit.Test;

import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import LogicTier.TestRunner.TestRunner;

public class RankingTest {

    public static void main(String[] args) {
        TestRunner.runTestClass(RankingTest.class);
    }

    @Test
    public void testAddPlayer() {
        Ranking ranking = new Ranking();
        ranking.addPlayer("player1", 10);
        assertEquals(10, ranking.getPoints("player1"));
    }

    @Test
    public void testRemovePlayer() {
        Ranking ranking = new Ranking();
        ranking.addPlayer("player1", 10);
        ranking.removePlayer("player1");
        assertNull(ranking.getRanking().get(("player1")));
    }

    @Test
    public void testUpdatePoints() {
        Ranking ranking = new Ranking();
        ranking.addPlayer("player1", 10);
        ranking.updatePoints("player1", 5);
        assertEquals(5, ranking.getPoints("player1"));
    }

    @Test
    public void testGetRanking() {
        Ranking ranking = new Ranking();
        ranking.addPlayer("player1", 10);
        ranking.addPlayer("player2", 20);
        ranking.addPlayer("player3", 30);
        Map<String, Integer> rankingMap = ranking.getRanking();

        int i = 0;
        for (Map.Entry<String, Integer> entry : rankingMap.entrySet()) {
            if (i == 0) {
                assertEquals("player3", entry.getKey());
                assertEquals(Integer.valueOf(30), entry.getValue());
            } else if (i == 1) {
                assertEquals("player2", entry.getKey());
                assertEquals(Integer.valueOf(20), entry.getValue());
            } else if (i == 2) {
                assertEquals("player1", entry.getKey());
                assertEquals(Integer.valueOf(10), entry.getValue());
            }
            ++i;
        }

        ranking.updatePoints("player1", 40);

        i = 0;
        for (Map.Entry<String, Integer> entry : ranking.getRanking().entrySet()) {
            if (i == 0) {
                assertEquals("player1", entry.getKey());
                assertEquals(Integer.valueOf(40), entry.getValue());
            } else if (i == 1) {
                assertEquals("player3", entry.getKey());
                assertEquals(Integer.valueOf(30), entry.getValue());
            } else if (i == 2) {
                assertEquals("player2", entry.getKey());
                assertEquals(Integer.valueOf(20), entry.getValue());
            }
            ++i;
        }

        ranking.removePlayer("player3");
        i = 0;
        for (Map.Entry<String, Integer> entry : ranking.getRanking().entrySet()) {
            if (i == 0) {
                assertEquals("player1", entry.getKey());
                assertEquals(Integer.valueOf(40), entry.getValue());
            } else if (i == 1) {
                assertEquals("player2", entry.getKey());
                assertEquals(Integer.valueOf(20), entry.getValue());
            }
            ++i;
        }
    }

    @Test
    public void testGetRankingHashMap() {
        Ranking ranking = new Ranking();
        ranking.addPlayer("player1", 10);
        ranking.addPlayer("player2", 20);
        ranking.addPlayer("player3", 30);
        Map<String, Integer> rankingMap = ranking.getRankingHashMap();

        int j= 0;
        for (Map.Entry<String, Integer> entry : rankingMap.entrySet()) {
            if (j == 0) {
                assertEquals("player1", entry.getKey());
                assertEquals(Integer.valueOf(10), entry.getValue());
            } else if (j == 1) {
                assertEquals("player2", entry.getKey());
                assertEquals(Integer.valueOf(20), entry.getValue());
            } else if (j == 2) {
                assertEquals("player3", entry.getKey());
                assertEquals(Integer.valueOf(30), entry.getValue());
            }
            ++j;
        }
    }

    @Test
    public void testGetVoidRanking() {
        Ranking ranking = new Ranking();
        Map<String, Integer> rankingMap = ranking.getRanking();
        assertEquals(0, rankingMap.size());
    }

    @Test
    public void testOrderOfEqualPointsPlayers(){
        Ranking ranking = new Ranking();
        ranking.addPlayer("aleix", 10);
        ranking.addPlayer("marc", 10);
        ranking.addPlayer("xavier", 10);
        Map<String, Integer> rankingMap = ranking.getRanking();

        int i = 0;
        for (Map.Entry<String, Integer> entry : rankingMap.entrySet()) {
            if (i == 0) {
                assertEquals("aleix", entry.getKey());
                assertEquals(Integer.valueOf(10), entry.getValue());
            } else if (i == 1) {
                assertEquals("marc", entry.getKey());
                assertEquals(Integer.valueOf(10), entry.getValue());
            } else if (i == 2) {
                assertEquals("xavier", entry.getKey());
                assertEquals(Integer.valueOf(10), entry.getValue());
            }
            ++i;
        }
    }

}
