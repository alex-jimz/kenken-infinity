package LogicTier.Ranking;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;



public class Ranking {

    private LinkedHashMap<String, Integer> ranking;


    public  Ranking() {
        ranking = new LinkedHashMap<>();
    }
    public Ranking(LinkedHashMap<String, Integer> ranking) {
        //creator setting the ranking to the given ranking
        this.ranking = ranking;
    }

    public void addPlayer(String playerName, int points) {
        //sets the points of the player to the given points
        ranking.put(playerName, points);

    }

    public void removePlayer(String playerName) {
        //removes the player from the ranking
        ranking.remove(playerName);

    }

    public void updatePoints(String playerName, int points) {
        //updates the points of the player to the given points
        ranking.put(playerName, points);

    }
    public int getPoints(String playerName) {
        //returns the points of the player
        return ranking.get(playerName);
    }

    public LinkedHashMap<String, Integer> getRankingHashMap() {
        return ranking;
    }

    public LinkedHashMap<String, Integer> getRanking() {

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(ranking.entrySet());

        //sorts the entries of the map. First by points and then by name
        Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                
                if (entry2.getValue().compareTo(entry1.getValue()) != 0){
                    return entry2.getValue().compareTo(entry1.getValue());
                } else {
                    return entry1.getKey().compareTo(entry2.getKey());
                }
            }

        });

        //for compacity reasons we only return the top 50 players in the ranking
        ranking.clear();
        LinkedHashMap<String, Integer> rk = new LinkedHashMap<>();
        int i = 0;
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            if(i < 50) {
                rk.put(entry.getKey(), entry.getValue());
                ranking.put(entry.getKey(), entry.getValue());
            }else {
                ranking.put(entry.getKey(), entry.getValue());
            }
            ++i;
        }

        return rk;
    }

    public Vector<Integer> getUser (String username) {

        Vector<Integer> userPos = new Vector<>(2);
        int i  = 1;
        for (Map.Entry<String, Integer> entry : ranking.entrySet()) {
            if (entry.getKey().equals(username)) {
                userPos.add(i);
                userPos.add(entry.getValue());
                return userPos;
            }
            ++i;
        }
        return userPos;
    }
}