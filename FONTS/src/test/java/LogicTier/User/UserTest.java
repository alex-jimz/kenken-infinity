package LogicTier.User;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;

import LogicTier.TestRunner.TestRunner;

public class UserTest {

    public static void main(String[] args) {
        TestRunner.runTestClass(UserTest.class);
    }

    @Test
    public void testDefaultConstructor() {
        User user = new User();
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNotNull(user.getStats());
    }

    @Test
    public void testConstructorWithNameAndPassword() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User(username, password);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertNotNull(user.getStats());
    }

    @Test
    public void testConstructorWithValidStats() {
        String username = "testUser";
        String password = "testPassword";
        Vector<Integer> intStats = new Vector<>();
        intStats.add(1); intStats.add(2); intStats.add(3); intStats.add(4);
        intStats.add(5); intStats.add(6); intStats.add(7); intStats.add(8);
        Vector<String> stringStats = new Vector<>();
        stringStats.add("PT1M"); stringStats.add("PT2M");
        
        User user = new User(username, password, intStats, stringStats);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertNotNull(user.getStats());
        assertEquals(intStats, user.getIntegerStats());
        assertEquals(stringStats, user.getDurationStats());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidStats() {
        String username = "testUser";
        String password = "testPassword";
        Vector<Integer> intStats = new Vector<>();
        Vector<String> stringStats = new Vector<>();
        // Vector with incorrect size
        intStats.add(1); intStats.add(2); intStats.add(3); intStats.add(4);
        intStats.add(5); intStats.add(6); intStats.add(7); intStats.add(8); intStats.add(9);
        stringStats.add("PT1M");
        
        new User(username, password, intStats, stringStats);
    }

    @Test
    public void testCorrectPassword() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User(username, password);
        assertTrue(user.correctPassword(password));
    }

    @Test
    public void testIncorrectPassword() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User(username, password);
        assertFalse(user.correctPassword("wrongPassword"));
    }
}
