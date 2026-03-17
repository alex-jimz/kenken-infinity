package LogicTier.User;

import java.util.*;
import LogicTier.Statistics.Statistics;
import java.time.Duration;

public class User {
    // Data
    private String username;
    private String password;
    private Statistics stats;

    // Constructor without values
    public User() {
        username = null;
        password = null;
        stats = new Statistics();
    }

    // Constructor with name and password
    public User(String name, String pwd) {
        username = name;
        password = pwd;
        stats = new Statistics();
    }

    // Constructor with name, password and statistics
    public User(String name, String pwd, Vector<Integer> intStats, Vector<String> stringStats) {
        if (intStats == null || stringStats == null || intStats.size() != 8 || stringStats.size() != 2) {
            throw new IllegalArgumentException("Vectors must have exactly 8 and 2 elements respectively.");
        }
        username = name;
        password = pwd;
        if (intStats == null || stringStats == null || intStats.size() < 7 || stringStats.size() < 2) {
            stats = new Statistics();
        }
        else {
            stats = new Statistics(intStats.get(0), intStats.get(1), intStats.get(2), intStats.get(3),
        intStats.get(4), intStats.get(5), intStats.get(6), intStats.get(7), Duration.parse(stringStats.get(0)), Duration.parse(stringStats.get(1)));
        }
    }

    // Returns true if password is valid
    public static Boolean validPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        char ch;
        boolean capitalFlag = false;
        boolean specialFlag = false;
        boolean numberFlag = false;
        
        for (int i=0; i < password.length(); i++) {
            ch = password.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } 
            else if (!Character.isDigit(ch) && !Character.isLetter(ch) && !Character.isWhitespace(ch)) {
                specialFlag = true;
            }
            if (numberFlag && capitalFlag && specialFlag)
                return true;
        }
        return false;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Statistics getStats() {
        return stats;
    }

    public void setUsername(String name) {
        username = name;
    }

    public void setPassword(String pwd) {
        password = pwd;
    }

    // Returns true if pwd is the correct password of the user
    public boolean correctPassword(String pwd) {
        return username != null && password == pwd;
    }

    public void updateStatistics(int scoredPoints, Duration duration, boolean victory, boolean errors, boolean hints) {
        stats.updateStatistics(scoredPoints, duration, victory, errors, hints);
    }

    public int getPoints() {
       return stats.getTotalPoints();
    }

    public Vector<Integer> getIntegerStats() {
        return stats.getIntegerStats();
    }

    public Vector<String> getIntegerStatsStringFormat() {
        return ToVectorString(stats.getIntegerStats());
    }

    public Vector<String> getDurationStats() {
        return stats.getDurationStats();
    }

    // Converts a vector of integers to a vector of strings
    private static Vector<String> ToVectorString(Vector<Integer> v) {
        Vector<String> vS = new Vector<String>();
        if (!v.isEmpty()) {
            for (int i = 0; i < v.size(); ++i) {
                vS.add(String.valueOf(v.get(i)));
            }
        }
        return vS;
    }
}