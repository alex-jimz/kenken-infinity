package DataTier;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.FileWriter;

import java.util.*;

public class Writer {
    
    private static String PATH = "./Data/";

    public Writer(){}

    //Note: if no precondition is specified, then there isn't

    //Kenken
    //Stores the attibutes of a Kenken in persistence
    public void addKenken(int idKenken,int size, Vector<Vector<Integer>> board, int[][] solution, int hashCode, int[][] RegionBoard, int[][] RegionInfo) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Kenken.json")))
        {
            JSONArray kenkenList = (JSONArray) jsonParser.parse(reader);
            
            //JSONArray boardList = readMatrix(board);
            JSONArray solutionList = readMatrixArray(solution);
            JSONArray RegionBoardList = readMatrixArray(RegionBoard);
            JSONArray RegionInfoList = readMatrixArray(RegionInfo);
            JSONObject KenKen = new JSONObject();
            KenKen.put("idKenken", String.valueOf(idKenken));
            KenKen.put("size", size);
            KenKen.put("board", board);
            KenKen.put("solution", solutionList);
            KenKen.put("hashCode", hashCode);
            KenKen.put("RegionBoard", RegionBoardList);
            KenKen.put("RegionInfo", RegionInfoList);
            
            kenkenList.add(KenKen);
            try {
                FileWriter file = new FileWriter(PATH.concat("Kenken.json"));
                file.write(kenkenList.toJSONString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  catch (FileNotFoundException e) {
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
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("User.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);

            JSONObject properties = new JSONObject();
            properties.put("password", password);
            properties.put("intStats", intStats);
            properties.put("stringStats", stringStats);

            users.put(user, properties);
            try {
                FileWriter file = new FileWriter(PATH.concat("User.json"));
                file.write(users.toJSONString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Updates the stats attibutes of the User user in persistence
    public void updateUser(String user, Vector<Integer> intStats, Vector<String> stringStats) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("User.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                JSONObject properties = (JSONObject) users.get(user);
                properties.put("intStats", intStats);
                properties.put("stringStats", stringStats);
            }

            try {
                FileWriter file = new FileWriter(PATH.concat("User.json"));
                file.write(users.toJSONString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Deletes the User user from persistence
    public void eraseUser(String user) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("User.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                users.remove(user);
            }
            try {
                FileWriter file = new FileWriter(PATH.concat("User.json"));
                file.write(users.toJSONString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  catch (FileNotFoundException e) {
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
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Game.json")))
        { 
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(!users.containsKey(user)){
                //JSONArray games = new JSONArray();
                JSONObject games = new JSONObject();
                JSONObject properties = new JSONObject();
                properties.put("board", board);
                properties.put("lives", lives);
                properties.put("hints", hints);
                properties.put("freePositions", freePositions);
                properties.put("time", time);
                games.put(String.valueOf(idGame), properties);
                users.put(user, games);
            }
            else{
                JSONObject games = (JSONObject) users.get(user);
                JSONObject properties = new JSONObject();
                properties.put("board", board);
                properties.put("lives", lives);
                properties.put("hints", hints);
                properties.put("freePositions", freePositions);
                properties.put("time", time);
                games.put(String.valueOf(idGame), properties);
                
            }
            
            try {
                FileWriter file = new FileWriter(PATH.concat("Game.json"));
                file.write(users.toJSONString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Deletes the game played by User user and with Kenken id idGame from persistence
    public void eraseGame(String user, int idGame) throws FileNotFoundException, IOException, ParseException{
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Game.json")))
        {
            JSONObject users = (JSONObject) jsonParser.parse(reader);
            if(users.containsKey(user)){
                JSONObject games = (JSONObject) users.get(user);
                if(!games.isEmpty()){
                    games.remove(String.valueOf(idGame));
                }
            }
            try {
                FileWriter file = new FileWriter(PATH.concat("Game.json"));
                file.write(users.toJSONString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  catch (FileNotFoundException e) {
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
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(PATH.concat("Ranking.json")))
        {
            JSONObject ranking = (JSONObject) jsonParser.parse(reader);
            for (Map.Entry<String,Integer> mapElement : hM.entrySet()) {
                ranking.put(mapElement.getKey(), mapElement.getValue());
            }
            
            try {
                FileWriter file = new FileWriter(PATH.concat("Ranking.json"));
                file.write(ranking.toJSONString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }
    //Private
    //Given an int[][], returns a JSONArray with the same content and same dimension
    private JSONArray readMatrixArray(int[][] Matrix){
        JSONArray rowList = new JSONArray();
        for(int i = 0; i < Matrix.length; ++i){
            JSONArray columnList = new JSONArray();
            for(int j = 0; j < (Matrix[i]).length; ++j){
                columnList.add( Matrix[i][j]);
            }
            rowList.add(columnList);
        }
        return rowList;
    }
    
    

}

