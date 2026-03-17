package DataTier;

import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Reader {

    private static String PATH = "./Data/";

    public Reader(){}

    //Note: if no precondition is specified, then there isn't

    //Kenken
    // Returns the attribute board of a Kenken with id idKenken
    public Vector<Vector<Integer>> getKenkenBoard(int idKenken) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Kenken.json")))
        {
            JSONArray kenkenList = (JSONArray) jsonParser.parse(reader);
            if(!kenkenList.isEmpty() && idKenken <= kenkenList.size()){
                JSONObject kenken = (JSONObject) kenkenList.get(idKenken);
                if(!((JSONArray) kenken.get("board")).isEmpty()){
                    return writeMatrix((JSONArray) kenken.get("board"));
                }
                
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new Vector<Vector<Integer>>();
    }
    // Returns the attribute solution of a Kenken with id idKenken and with size dim
    public int[][] getSolution(int idKenken, int dim) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Kenken.json")))
        {
            JSONArray kenkenList = (JSONArray) jsonParser.parse(reader);
            if(!kenkenList.isEmpty() && idKenken <= kenkenList.size()){
                JSONObject kenken = (JSONObject) kenkenList.get(idKenken);
                if(!((JSONArray) kenken.get("board")).isEmpty())
                    return writeMatrixArray((JSONArray) kenken.get("solution"), dim);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new int[0][0];
    }

    // Returns true if an id is already used by another Kenken
    public boolean kenkenExists(int idkenken) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Kenken.json")))
        {
            JSONArray kenkens = (JSONArray) jsonParser.parse(reader);
            if(!kenkens.isEmpty()){
                for(int i = 0; i < kenkens.size(); ++i){
                    if (Integer.valueOf(((String)((JSONObject) kenkens.get(i)).get("idKenken"))) == idkenken){
                        return true;
                    }
                }
            }
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return false;
    }
    //Returns a vector that has in the i-essim possition the dimension of the Kenken with id i
    public Vector<Integer> getKenkens() throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Kenken.json")))
        {
            JSONArray kenkenList = (JSONArray) jsonParser.parse(reader);
            Vector<Integer> Kenkens = new Vector<Integer>();
            if(!kenkenList.isEmpty()){
                
                for(int i = 0; i< kenkenList.size(); ++i){
                    Kenkens.add((int)(long)((JSONObject) kenkenList.get(i)).get("size"));
                }
                
            }
            return Kenkens;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Returns true if a Kenken with the same hashCode already exists in persistence
    public boolean repeatedKenken(int hashCode)throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Kenken.json")))
        {
            JSONArray kenkenList = (JSONArray) jsonParser.parse(reader);
            if(!kenkenList.isEmpty()){
                for(int i = 0; i< kenkenList.size(); ++i){
                   if((int)(long)((JSONObject) kenkenList.get(i)).get("hashCode") == hashCode)
                        return true;
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    // Returns the attribute RegionBoard of a Kenken with id idKenken
    public Vector<Vector<Integer>> getRegionBoard(int idKenken) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Kenken.json")))
        {
            JSONArray kenkenList = (JSONArray) jsonParser.parse(reader);
            if(!kenkenList.isEmpty() && idKenken <= kenkenList.size()){
                JSONObject kenken = (JSONObject) kenkenList.get(idKenken);
                if(!((JSONArray) kenken.get("RegionBoard")).isEmpty()){
                    return writeMatrix((JSONArray) kenken.get("RegionBoard"));
                }
                
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new Vector<Vector<Integer>>();
    }
    // Returns the attribute RegionInfo of a Kenken with id idKenken
    public Vector<Vector<Integer>> getRegionInfo(int idKenken) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Kenken.json")))
        {
            JSONArray kenkenList = (JSONArray) jsonParser.parse(reader);
            if(!kenkenList.isEmpty() && idKenken <= kenkenList.size()){
                JSONObject kenken = (JSONObject) kenkenList.get(idKenken);
                if(!((JSONArray) kenken.get("RegionInfo")).isEmpty()){
                    return writeMatrix((JSONArray) kenken.get("RegionInfo"));
                }
                
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new Vector<Vector<Integer>>();
    }
    // Returns the number of Kenkens in persistence
    public int getnumKenken() throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Kenken.json")))
        {
            JSONArray kenkenList = (JSONArray) jsonParser.parse(reader);
            if(!kenkenList.isEmpty()){
                return kenkenList.size();
            }
            else{
                return 0;
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Game
    // Returns the attribute board of the Game played by user and with Kenken id idGame
    public Vector<Vector<Vector<Integer>>> getGameBoard(int idGame, String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Game.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                JSONObject games = (JSONObject) users.get(user);
                if(!games.isEmpty() && games.containsKey(String.valueOf(idGame)))
                    return translateFromJSONArrayTensor((JSONArray) ((JSONObject) games.get(String.valueOf(idGame))).get("board"));
            }
            
            
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new Vector<Vector<Vector<Integer>>>();
    }
    // Returns true if there is already a Game in persistence played by user and with a Kenken with id idGame
    public Boolean gameExists(String user, int idGame) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Game.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                JSONObject games = (JSONObject) users.get(user);
                if(!games.isEmpty() && games.containsKey(String.valueOf(idGame)))
                    return games.containsKey(String.valueOf(idGame));
            }
            
            
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return false;
    }
    // Returns the attribute lives of the Game played by user and with Kenken id idGame
    public int getGameLives(int idGame, String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Game.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                JSONObject games = (JSONObject) users.get(user);
                if(!games.isEmpty() && games.containsKey(String.valueOf(idGame)))
                    return (int)(long) ((JSONObject) games.get(String.valueOf(idGame))).get("lives");
            }
            
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return -1;
    }
    // Returns the attribute hints of the Game played by user and with Kenken id idGame
    public int getGameHints(int idGame, String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Game.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                JSONObject games = (JSONObject) users.get(user);
                if(!games.isEmpty() && games.containsKey(String.valueOf(idGame)))
                    return (int)(long) ((JSONObject) games.get(String.valueOf(idGame))).get("hints");
            }
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return -1;
    }
    // Returns the attribute freePositions of the Game played by user and with Kenken id idGame
    public Vector<Integer> getGameFreePositions(int idGame, String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Game.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                JSONObject games = (JSONObject) users.get(user);
                if(!games.isEmpty() && games.containsKey(String.valueOf(idGame)))
                    return translateFromJSONArray((JSONArray) ((JSONObject) games.get(String.valueOf(idGame))).get("freePositions"));
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new Vector<Integer>();
    }
    // Returns the attribute time of the Game played by user and with Kenken id idGame
    public String getGameTime(int idGame, String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Game.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                JSONObject games = (JSONObject) users.get(user);
                    if(!games.isEmpty() && games.containsKey(String.valueOf(idGame)))
                        return (String)((JSONObject) games.get(String.valueOf(idGame))).get("time");
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return "";
    }
    // Returns a vector with the Kenken id of the Games that user has in persistence
    public Vector<Integer> getGameList(String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Game.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                JSONObject games = (JSONObject) users.get(user);
                Vector<Integer> v = new Vector<Integer>();
                games.keySet().forEach(keyStr ->
                {
                    v.add(Integer.valueOf((String) keyStr));
                });
                return v;
            }
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new Vector<Integer>();
    }
    //User
    // Returns the attibute password of the User user
    public String getUserPassword(String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("User.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user))
                return ((String) ((JSONObject) users.get(user)).get("password"));
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return "";
    }
    // Returns the attibute intStats of the User user
    public Vector<Integer> getUserIntStats(String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("User.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user))
                return translateFromJSONArray(((JSONArray) (((JSONObject) users.get(user)).get("intStats"))));
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new Vector<Integer>();
    }
    // Returns the attibute stringStats of the User user
    public Vector<String> getUserStringStats(String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("User.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user))
                return translateFromJSONArrayString(((JSONArray) ((JSONObject) users.get(user)).get("stringStats")));
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new Vector<String>();
    }
    // Returns true if the User with name user already exists in persistence
    public Boolean existsUser(String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("User.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
                return (users.containsKey(user));
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }

    //Ranking
    // Returns LinkedHashMap<String,Integer> containing the ranking
    public LinkedHashMap<String,Integer> loadRanking() throws FileNotFoundException, IOException, ParseException{
        LinkedHashMap<String,Integer> hM = new LinkedHashMap<String,Integer>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Ranking.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(!users.isEmpty()){
                users.keySet().forEach( key-> {
                    hM.put((String) key,  (int)(long) users.get(key));
                });
            }
           
            return hM;
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }

    //Data
    // Returns vector containing the rules
    public Vector<String> readRules() throws FileNotFoundException, IOException{
        Vector<String> lines = new Vector<>();
        try {
            FileReader fileReader = new FileReader(PATH.concat("Rules.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } 
        return lines;
    }

    //Private
    //Given a 3-tensor JSONArray, returns a 3-tensor Vector<Integer> with the same content
    private Vector<Vector<Vector<Integer>>> translateFromJSONArrayTensor(JSONArray js){
        Vector<Vector<Vector<Integer>>> v = new Vector<Vector<Vector<Integer>>>();
        if(!js.isEmpty())
            for(int i = 0; i < js.size(); ++i){
                Vector<Vector<Integer>> vvI = new Vector<Vector<Integer>>();
                if(!((JSONArray) js.get(i)).isEmpty())
                    for(int j = 0; j < ((JSONArray) js.get(i)).size(); ++j){
                        Vector<Integer> vI = new Vector<Integer>();
                        if(!((JSONArray) ((JSONArray) js.get(i)).get(j)).isEmpty())
                            for(int k = 0; k < ((JSONArray) ((JSONArray) js.get(i)).get(j)).size(); ++k){
                                
                                vI.add((int) ((long) ((JSONArray) ((JSONArray) js.get(i)).get(j)).get(k)));
                            }
                        vvI.add(vI);
                    }
                v.add(vvI);
            }
        return v;
    }
    //Given a 1-tensor JSONArray, returns a 1-tensor Vector<String> with the same content
    private Vector<String> translateFromJSONArrayString(JSONArray js){
        Vector<String> v = new Vector<String>();
        if(!js.isEmpty())
            for(int i = 0; i < js.size(); ++i){
                v.add((String) js.get(i));
            }
        return v;
    }
    //Given a 1-tensor JSONArray, returns a 1-tensor Vector<Integer> with the same content
    private Vector<Integer> translateFromJSONArray(JSONArray js){ 
        Vector<Integer> v = new Vector<Integer>();
        if(!js.isEmpty())
            for(int i = 0; i < js.size(); ++i){
                v.add((int) (long) js.get(i));
            }
        return v;
    }
    //Given a 2-tensor JSONArray, returns a 2-tensor Vector<Integer> with the same content
    private Vector<Vector<Integer>> writeMatrix(JSONArray rowList){
        Vector<Vector<Integer>> Matrix = new Vector<Vector<Integer>>();
        if(!rowList.isEmpty())
            for(int i = 0; i < rowList.size(); ++i){
                Matrix.add(new Vector<Integer>());
                if(!((JSONArray) rowList.get(i)).isEmpty())
                    for(int j = 0; j < ((JSONArray)rowList.get(i)).size(); ++j){
                        Matrix.get(i).add(((int) (long)((JSONArray) rowList.get(i)).get(j)));
                    }
            }
        return Matrix;
    }
    //Given a 2-tensor JSONArray, returns a 2-tensor int array with the same content
    private int[][] writeMatrixArray(JSONArray rowList, int dim){
        int[][] Matrix = new int[dim][dim];
        if(!rowList.isEmpty())
            for(int i = 0; i < rowList.size(); ++i){
                if(!((JSONArray)rowList.get(i)).isEmpty())
                    for(int j = 0; j < ((JSONArray)rowList.get(i)).size(); ++j){
                        Matrix[i][j] = (((int)(long) ((JSONArray) rowList.get(i)).get(j)));
                    }
            }
        return Matrix;
    }
}
