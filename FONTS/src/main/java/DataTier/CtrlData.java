package DataTier;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.json.simple.parser.ParseException;

public class CtrlData {
    private Writer writer;
    private Reader reader;
    public CtrlData(){
        writer = new Writer();
        reader = new Reader();
    }

    //Note: if no precondition is specified, then there isn't

//Reader
    //Kenken
    // Returns the attribute board of a Kenken with id idKenken
    public Vector<Vector<Integer>> getKenkenBoard(int idKenken) throws FileNotFoundException, IOException, ParseException{
        try{
            return reader.getKenkenBoard(idKenken);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }

    // Returns the attribute solution of a Kenken with id idKenken and with size dim
    public int[][] getSolution(int idKenken, int dim)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.getSolution(idKenken, dim);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }

    // Returns true if an id is already used by another Kenken
    public boolean kenkenExists(int idKenken)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.kenkenExists(idKenken);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Returns a vector that has in the i-essim possition the dimension of the Kenken with id i
    public Vector<Integer> getKenkens() throws FileNotFoundException, IOException, ParseException{
        try{
            return reader.getKenkens();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Returns true if a Kenken with the same hashCode already exists in persistence
    public boolean repeatedKenken(int hashCode)throws FileNotFoundException, IOException, ParseException{
        try{
            return reader.repeatedKenken(hashCode);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attribute RegionBoard of a Kenken with id idKenken
    public Vector<Vector<Integer>> getRegionBoard(int idKenken) throws FileNotFoundException, IOException, ParseException{
        try{
            return reader.getRegionBoard(idKenken);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attribute RegionInfo of a Kenken with id idKenken
    public Vector<Vector<Integer>> getRegionInfo(int idKenken) throws FileNotFoundException, IOException, ParseException{
        try{
            return reader.getRegionInfo(idKenken);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the number of Kenkens in persistence
    public int getnumKenken() throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.getnumKenken();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Game
    // Returns true if there is already a Game in persistence played by user and with a Kenken with id idGame
    public Boolean gameExists(String user, int idGame)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.gameExists(user,idGame);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attribute board of the Game played by user and with Kenken id idGame
    public Vector<Vector<Vector<Integer>>> getGameBoard(int idGame, String user)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.getGameBoard(idGame, user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attribute lives of the Game played by user and with Kenken id idGame
    public int getGameLives(int idGame, String user)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.getGameLives(idGame, user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attribute hints of the Game played by user and with Kenken id idGame
    public int getGameHints(int idGame, String user)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.getGameHints(idGame, user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attribute freePositions of the Game played by user and with Kenken id idGame
    public Vector<Integer> getGameFreePositions(int idGame, String user)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.getGameFreePositions(idGame, user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attribute time of the Game played by user and with Kenken id idGame
    public String getGameTime(int idGame, String user)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.getGameTime(idGame, user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns a vector with the Kenken id of the Games that user has in persistence
    public Vector<Integer> getGameList(String user)throws FileNotFoundException, IOException, ParseException{
        try{
            return reader.getGameList(user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //User
    // Returns the attibute password of the User user
    public String getUserPassword(String user)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.getUserPassword(user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attibute intStats of the User user
    public Vector<Integer> getUserIntStats(String user)throws FileNotFoundException, IOException, ParseException{
        try{
        
        return reader.getUserIntStats(user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attibute stringStats of the User user
    public Vector<String> getUserStringStats(String user)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.getUserStringStats(user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns true if the User with name user already exists in persistence
    public Boolean existsUser(String user)throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.existsUser(user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Ranking
    // Returns LinkedHashMap<String,Integer> containing the ranking
    public LinkedHashMap<String,Integer> loadRanking()throws FileNotFoundException, IOException, ParseException{
        try{
        return reader.loadRanking();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Data
    // Returns vector containing the rules
    public Vector<String> readRules()throws FileNotFoundException, IOException{
        try{
        return reader.readRules();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } 
    }
//Writer

    //Kenken
    //Stores the attibutes of a Kenken in persistence
    public void addKenken(int idKenken, int size, Vector<Vector<Integer>> board, int[][] solution, int hashCode, int[][] RegionBoard, int[][] RegionInfo) throws FileNotFoundException, IOException, ParseException{
        try{
        writer.addKenken(idKenken,size, board,solution, hashCode, RegionBoard, RegionInfo);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }

    //User
    //Stores the attibutes of a user in persistence
    public void registerUser(String user, String password, Vector<Integer> intStats, Vector<String> stringStats) throws FileNotFoundException, IOException, ParseException{

        try{
        writer.registerUser(user, password, intStats, stringStats);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Updates the stats attibutes of the User user in persistence
    public void updateUser(String user, Vector<Integer> intStats, Vector<String> stringStats) throws FileNotFoundException, IOException, ParseException{
        try{
        writer.updateUser(user, intStats, stringStats);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Deletes the User user from persistence
    public void eraseUser(String user) throws FileNotFoundException, IOException, ParseException{
        try{
        writer.eraseUser(user);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    
    //Game
    //Stores the attributes of a game in persistence
    public void saveGame(String user, int idGame, Vector<Vector<Vector<Integer>>> board, int lives, int hints, String time, Vector<Integer> freePositions) throws FileNotFoundException, IOException, ParseException{
        try{
        writer.saveGame(user, idGame, board, lives, hints, time, freePositions);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Deletes the game played by User user and with Kenken id idGame from persistence
    public void eraseGame(String user, int idGame) throws FileNotFoundException, IOException, ParseException{
        try{
        writer.eraseGame(user, idGame);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Ranking
    //Stores the ranking in persistence
    public void saveRanking(LinkedHashMap<String,Integer> hM) throws FileNotFoundException, IOException, ParseException{
        try{
            writer.saveRanking(hM);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
}
