package LogicTier.CtrlLogic;

import java.util.*;

import org.json.simple.parser.ParseException;

import java.time.Duration;

import java.io.File;  
import java.io.FileNotFoundException;
import java.io.IOException;

import DataTier.CtrlData;
import LogicTier.GameAlreadyExists;
import LogicTier.IllegalCellSelection;
import LogicTier.IllegalGameAction;
import LogicTier.IllegalMatrixPosition;
import LogicTier.KenkenAlreadyInPersistence;
import LogicTier.KenkenNotGenerated;
import LogicTier.GSolver.GSolver;
import LogicTier.Game.Game;
import LogicTier.Generator.Generator;
import LogicTier.Kenken.Kenken;
import LogicTier.Operation.Operation;
import LogicTier.Ranking.Ranking;
import LogicTier.Region.Region;
import LogicTier.Translator.Translator;
import LogicTier.User.User;
import LogicTier.Verifier.Verifier;
import LogicTier.Coordinates.Coordinates;

public class CtrlLogic {
    static private int numKenken;
    private Vector<Integer> Kenkens;

    private User user;
    private Game game;
    private Verifier verifier;
    private Translator translator;
    private CtrlData ctrlData;
    private Ranking ranking;

    private int gamePoints;

    //Note: if no precondition is specified, then there isn't

    //Creator of the class
    public CtrlLogic() throws FileNotFoundException, IOException, ParseException{
        ctrlData = new CtrlData();
        user = null;
        game = null;
        gamePoints = 0;
        try{
            numKenken = ctrlData.getnumKenken();
            Kenkens = ctrlData.getKenkens();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        translator = new Translator();
        verifier = new Verifier();
        ranking = new Ranking(ctrlData.loadRanking());
    }

    //Logs in an existing user
    public Boolean login(String username, String password) throws FileNotFoundException, IOException, ParseException{
        try{
        if(user == null && password.equals(ctrlData.getUserPassword(username))){
            try{
                user = new User(username, password, ctrlData.getUserIntStats(username), ctrlData.getUserStringStats(username));
            }
            catch(IllegalArgumentException e){
                throw e;
            }
            return true;
        }
        return false;
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        
    }
    
    //Creates and logs in a new user
    //The new user is created and loaded to persistence and to the ranking
    public Boolean register(String username, String password, String confirmPassword) throws FileNotFoundException, IOException, ParseException{
        try{
            if(user == null && !username.contains(" ") && !username.equals("") && password.equals(confirmPassword) && User.validPassword(password) && !ctrlData.existsUser(username)){
                user = new User(username, password);
                ctrlData.registerUser(username, password, user.getIntegerStats(), user.getDurationStats());
                ranking.addPlayer(username, 0);
                ctrlData.saveRanking(ranking.getRankingHashMap());
                return true;
            }
            return false;
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
            
    }

    //Logs out a new user
    public Boolean logout(){
        user = null;
        return true;
    }

    //Erases the profile of the current user
    public void eraseUser() throws FileNotFoundException, IOException, ParseException{
        if(user != null){
            String username = user.getUsername();
            try{
                ctrlData.eraseUser(username);
            }
            catch (FileNotFoundException e) {
                throw new FileNotFoundException();
            } catch (IOException e) {
                throw new IOException();
            } catch (ParseException e) {
                throw new ParseException(e.getErrorType());
            }
            ranking.removePlayer(username);
            ctrlData.saveRanking(ranking.getRankingHashMap());
            user = null;
        }
        
    }

    //Imports a new Kenken
    /*After getting the string format from the selected file, it is verfied by the verfier
     * and translated to Region format by the translator. Then the solver checks if it has a unique solution,
     * in that case the hints provided match the solution and the new kenken is loaded to persistence.
    */
    public boolean importKenken(String fileName) throws FileNotFoundException, IOException, ParseException, KenkenAlreadyInPersistence{
        Vector<String> inputFile = new Vector<String>();
        try {
            File kenken = new File(fileName);
            Scanner myReader = new Scanner(kenken);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              inputFile.add(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("Error while reading import file.");
            e.printStackTrace();
          }

          if(inputFile != null && inputFile.size() > 0 && verifier.verify(inputFile)){
            Vector<Vector<Integer>> inputKenken = verifier.getIntegerFormat();
            int hashCode = inputKenken.hashCode();
            if(!ctrlData.repeatedKenken(hashCode)){
                Vector<Region> regions = translator.translateKenken(inputKenken);
                int dim = translator.getN();

                GSolver solver = new GSolver(regions, dim);
                solver.solve();

                if(solver.isSolved() && solver.isUnique()){
                    int[][] solution = solver.getSolution();
                    Vector<Vector<Integer>> hints = verifier.getHintsPositions();
                    boolean hintsMatch = true;
                    if (hints.size() != 0 ){
                        for (int i = 0; i < hints.size(); ++i){
                            int x = hints.get(i).get(0);
                            int y = hints.get(i).get(1);
                            int value = hints.get(i).get(2);
                            if (solution[x][y] != value){
                                hintsMatch = false;
                                break;
                            }

                        }
                    }
                    if (hintsMatch){
                        try{
                            
                            ctrlData.addKenken(numKenken,dim, inputKenken, solution, hashCode, translator.getRegionBoard(), translator.getRegionInfo());
                            Kenkens.add(dim);
                            ++numKenken;
                            return true;
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
            }
            else{
                throw new KenkenAlreadyInPersistence("Kenken already in persistence");
            }

        }
        return false;
    }
    //Imports a new Kenken
    /*After getting the string format from the presentation layer, it is verfied by the verfier
     * and translated to Region format by the translator. Then the solver checks if it has a unique solution,
     * in that case the hints provided match the solution and the new kenken is loaded to persistence.
    */
    public int generatePersonalizedKenken(Vector<String> inputFile) throws FileNotFoundException, IOException, ParseException, KenkenAlreadyInPersistence{
          if(inputFile != null && inputFile.size() > 0 && verifier.verify(inputFile)){
            Vector<Vector<Integer>> inputKenken = verifier.getIntegerFormat();
            int hashCode = inputKenken.hashCode();
            if(!ctrlData.repeatedKenken(hashCode)){
                Vector<Region> regions = translator.translateKenken(inputKenken);
                int dim = translator.getN();

                GSolver solver = new GSolver(regions, dim);
                solver.solve();

                if(solver.isSolved() && solver.isUnique()){
                    int[][] solution = solver.getSolution();
                    Vector<Vector<Integer>> hints = verifier.getHintsPositions();
                        boolean hintsMatch = true;
                        if (hints.size() != 0 ){
                            for (int i = 0; i < hints.size(); ++i){
                                int x = hints.get(i).get(0);
                                int y = hints.get(i).get(1);
                                int value = hints.get(i).get(2);
                                if (solution[x][y] != value){
                                        hintsMatch = false;
                                break;
                            }

                        }
                    }
                    if (hintsMatch){
                        try{
                            
                            ctrlData.addKenken(numKenken,dim, inputKenken, solution, hashCode, translator.getRegionBoard(), translator.getRegionInfo());
                            Kenkens.add(dim);
                            ++numKenken;
                            return numKenken - 1;
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
            }
            else{
                throw new KenkenAlreadyInPersistence("Kenken already in persistence");
            }
        }
        return -1;
        
    }
    //Getter for RegionBoard atribute of Kenken from persistence
    public Vector<Vector<Integer>> getRegionBoard(int idKenken) throws FileNotFoundException, IOException, ParseException{
        try{
            return ctrlData.getRegionBoard(idKenken);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }

    //Getter for RegionBoard atribute of Kenken from persistence
    public Vector<Vector<Integer>> getRegionInfo(int idKenken) throws FileNotFoundException, IOException, ParseException{
        try{
            return ctrlData.getRegionInfo(idKenken);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }

    //Getter of the operation ids
    public int[] getOpIds(){
        return Operation.getOperationIds();
    }

    //Getter of the operation symbols
    public String[] getOpSymbols(){
        return Operation.getOperationSymbols();
    }

    //Getter of the operation names
    public String[] getOpNames(){
        return Operation.getOperationNames();
    }

    //Creates a new game for the current user with the kenken with id idKenken
    /* After saving the game currently playing in persistence, a new game instance is created*/
    public void newGame(int idKenken) throws FileNotFoundException, IOException, ParseException, GameAlreadyExists{
       try{
           gamePoints = 0;
            if(user != null && game != null){
                ctrlData.saveGame(user.getUsername(), game.getGameId(), game.getBoardForCtrl(), game.getLives(), game.getHints(), game.getDurationForCtrl(), game.getFreePositionsForCtrl());
            }
            if(user != null && ctrlData.kenkenExists(idKenken)){
                if(!ctrlData.gameExists(user.getUsername(), idKenken)){
                    Vector<Vector<Integer>> Kenken = ctrlData.getKenkenBoard(idKenken);
                    Vector<Region> regions = translator.translateKenken(Kenken);
                    int dim = translator.getN();
                    Kenken kk = new Kenken(idKenken, dim,  regions, ctrlData.getSolution(idKenken, dim));
                    game = new Game(kk, user);
                }
                else{
                    throw new GameAlreadyExists("Game Already Exists");
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        
    }

    //Creates a new game for the current user with the kenken with id idKenken
    /* After saving the game currently playing in persistence, a new game instance is created using the parameters saved in persistence*/
    public Vector<Vector<Vector<Integer>>> continueGame(int idGame) throws FileNotFoundException, IOException, ParseException{
        try{
            if(user != null && game != null)
                ctrlData.saveGame(user.getUsername(), game.getGameId(), game.getBoardForCtrl(), game.getLives(), game.getHints(), game.getDurationForCtrl(), game.getFreePositionsForCtrl());
            
            
            if(user != null && ctrlData.gameExists(user.getUsername(), idGame) && ctrlData.kenkenExists(idGame)){
                gamePoints = 0;
                Vector<Vector<Integer>> Kenken = ctrlData.getKenkenBoard(idGame);
                Vector<Region> regions = translator.translateKenken(Kenken);
                int dim = translator.getN();
                Kenken kk = new Kenken(idGame, dim,  regions, ctrlData.getSolution(idGame, dim));
                Vector<Vector<Vector<Integer>>> Board = ctrlData.getGameBoard(idGame, user.getUsername());
                game = new Game(kk, user, Board, 
                                        ctrlData.getGameLives(idGame, user.getUsername()), 
                                        ctrlData.getGameHints(idGame, user.getUsername()),
                                        ctrlData.getGameFreePositions(idGame, user.getUsername()), 
                                        ctrlData.getGameTime(idGame, user.getUsername()));
                                        
                return Board;
            }
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
        return new Vector<Vector<Vector<Integer>>>();
    }

    //The game with id gameId of the current user is deleted from persistence
    public void deleteGame(int gameId) throws FileNotFoundException, IOException, ParseException{
        if(user != null){
            try{
                ctrlData.eraseGame(user.getUsername(), gameId);
                 game = null;
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

    //Given the coordinate (i, j) fills said position in the Kenken board of the current game with the value x
    public boolean fillSquare(int i, int j, int x) throws FileNotFoundException, IOException, ParseException{
        if(user != null && game != null){
            try {
                boolean isCorrect = game.setCell(i, j, x);
                if(game.gameIsOver()){
                    try{
                        gamePoints = game.pointsGame();
                        ctrlData.eraseGame(user.getUsername(), game.getGameId());
                        ranking.updatePoints(user.getUsername(), user.getPoints());
                        ctrlData.updateUser(user.getUsername(), user.getIntegerStats(), user.getDurationStats());
                        ctrlData.saveRanking(ranking.getRankingHashMap());
                        game = null;
                    }
                    catch (FileNotFoundException e) {
                        throw new FileNotFoundException();
                    } catch (IOException e) {
                        throw new IOException();
                    } catch (ParseException e) {
                        throw new ParseException(e.getErrorType());
                    }
                }
                return isCorrect;
            } catch (IllegalMatrixPosition e) {
                throw e;
            }
            catch (IllegalCellSelection e) {
                throw e;
            }
            catch (IllegalGameAction e) {
                throw e;
            }
        }
        return false; 
    }

    //Given the coordinate (i, j) fills said position's notes in the Kenken board of the current game with the value x
    public void fillNote(int i, int j, int x){
        if(game != null) {
            try {
                game.setNote(i, j, x);
            } catch (IllegalMatrixPosition e) {
                throw e;
            }
            catch (IllegalCellSelection e) {
                throw e;
            }
            catch (IllegalGameAction e) {
                throw e;
            }
        }
    }

    //Returns true if the current game is over
    public boolean gameIsOver(){
        if(game != null)
            return game.gameIsOver();
        return true;
    }

    //Returns the content of the coordinate (x,y) of the current game
    public Vector<Integer> getCoordinate(int x, int y){
        if(game != null)
            return game.getCoordinateAsVector(x, y);
        return new Vector<Integer>();
    }

    //Returns the coordinate of the cell filled by the asked hint 
    public Vector<Integer> askHint() throws FileNotFoundException, IOException, ParseException{
        if(game != null){
            Vector<Integer> v = new Vector<Integer>();
            Coordinates c = game.askHint();
            if (c != null) {
                v.add(c.getX());
                v.add(c.getY());
            }
            
            if(game.gameIsOver()){
                try{
                    gamePoints = game.pointsGame();
                    ctrlData.eraseGame(user.getUsername(), game.getGameId());
                    ranking.updatePoints(user.getUsername(), user.getPoints());
                    ctrlData.updateUser(user.getUsername(), user.getIntegerStats(), user.getDurationStats());
                    ctrlData.saveRanking(ranking.getRankingHashMap());
                    game = null;
                }
                catch (FileNotFoundException e) {
                    throw new FileNotFoundException();
                } catch (IOException e) {
                    throw new IOException();
                } catch (ParseException e) {
                    throw new ParseException(e.getErrorType());
                }
            }
            return v;
        }
        return new Vector<Integer>();
    }
    //Pauses the current game
    public void pauseGame(){
        if(game != null)
            game.pauseGame();
    }

    //Saves the current game in persistence and closes it
    public void closeGame() throws FileNotFoundException, IOException, ParseException{
        if(user != null && game != null)
            try{
                ctrlData.saveGame(user.getUsername(), game.getGameId(), game.getBoardForCtrl(), game.getLives(), game.getHints(), game.getDurationForCtrl(), game.getFreePositionsForCtrl());
            }
            catch (FileNotFoundException e) {
                throw new FileNotFoundException();
            } catch (IOException e) {
                throw new IOException();
            } catch (ParseException e) {
                throw new ParseException(e.getErrorType());
            }
        game = null;
    }

    //Restarts the current game
    public void restartGame(){
        if(game != null)
            game.restartGame();
    }

    //Unpauses the current game
    public void resumeGame(){
        if(game != null)
            game.resumeGame();
    }
    //Returns a vector containing in the i-essim position the Kenkenid of the i-essim Game of the User
    public Vector<Integer> getGameList() throws FileNotFoundException, IOException, ParseException{
        if(user != null){
            try{
                return ctrlData.getGameList(user.getUsername());
            }
            catch (FileNotFoundException e) {
                throw new FileNotFoundException();
            } catch (IOException e) {
                throw new IOException();
            } catch (ParseException e) {
                throw new ParseException(e.getErrorType());
            }
        }
        return new Vector<Integer>();
    }

    //Returns the size of the kenken with id idKenken
    public int getKenkenSize(int idKenken) throws FileNotFoundException, IOException, ParseException{
        if(Kenkens.size() > idKenken && idKenken >= 0)
            return Kenkens.get(idKenken);
        else{
            return -1;
        }
    }

    //Returns the number of hints available in the current game
    public Integer getNumHints(){
        if(game != null)
            return game.getHints();
        return -1;
    }

    //Returns the number of lives available in the current game
    public Integer getNumLives(){
        if(game != null)
           return game.getLives();
        return -1;
    }
    
    //Imports a new Kenken
    /*After getting the integer format from the generator, it is process by the translator to get the RegionBoard and RegionInfo atributes.
     *Then the new kenken is loaded to persistence and the id of the new kenken is returned. */
    public int generateRandomKenken(int dim, Vector<Integer> operations) throws FileNotFoundException, IOException, ParseException, KenkenAlreadyInPersistence{
        int[][] solution = Generator.latinSquare(dim);
        Vector<Vector<Integer>> inputKenken = Generator.generateRandom(dim, operations, solution);
        if(inputKenken != null){
            int hashCode = inputKenken.hashCode();
            if(!ctrlData.repeatedKenken(hashCode)){
                try{
                    translator.translateKenken(inputKenken);
                    ctrlData.addKenken(numKenken,dim, inputKenken, solution, hashCode, translator.getRegionBoard(), translator.getRegionInfo());
                    Kenkens.add(dim);
                    ++numKenken;
                    return numKenken - 1;
                }
                catch (FileNotFoundException e) {
                    throw new FileNotFoundException();
                } catch (IOException e) {
                    throw new IOException();
                } catch (ParseException e) {
                    throw new ParseException(e.getErrorType());
                }
            }
            else{
                throw new KenkenAlreadyInPersistence("Kenken already in persistence");
            }
        }
        else{
            throw new KenkenNotGenerated("Kenken not generated");
        }
    }
    
    //Returns a LinkedHashMap with the current ranking
    public LinkedHashMap<String,Integer> consultRanking(){
        return ranking.getRanking();
    }

    //Returns the Stats of the current user in String format
    public Vector<Vector<String>> consultStats(){
        Vector<Vector<String>> vS = new Vector<Vector<String>>();
        if(user != null){
            vS.add(user.getIntegerStatsStringFormat());
            vS.add(user.getDurationStats());
        }
        return vS;
    }

    //Getter of the integer stats of the user
    public Vector<Integer> consultIntegerStats(){
        if(user != null)
            return user.getIntegerStats();
        return new Vector<Integer>();
    }

    //Getter of the string stats of the user
    public Vector<String> consultStringStats(){
        if(user != null)
            return user.getDurationStats();
        return new Vector<String>();
    }

    //Returns the rules from persistence
    public Vector<String> consultRules() throws FileNotFoundException, IOException{
        try{
            return ctrlData.readRules();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        }    
    }

    //Returns the atribute kenken from the class
    public Vector<Integer> listKenkenLibrary(){
        return Kenkens;
    }

    //Returns the board of the kenken wit id index
    public Vector<Vector<Integer>> getKenkenBoard(int index) throws FileNotFoundException, IOException, ParseException{
        try{
            return ctrlData.getKenkenBoard(index);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException();
        } catch (ParseException e) {
            throw new ParseException(e.getErrorType());
        }
    }

    //Returns the atribute duration of the current game
    public String getDuration(){
        if(game != null) return game.getDurationForCtrl();
        return (Duration.ZERO).toString();
    }

    //Returns the user position in the ranking
    public Vector<Integer> getUserPos(){
        if(user != null)
            return ranking.getUser(user.getUsername());
        return new Vector<Integer>();
    }

     //Returns the user name
    public String getUsername(){
        if(user != null)
            return user.getUsername();
        return "";
    }

    public int getGamePoints(){
        return gamePoints;
    }

    public int getRkSize(){
        return ranking.getRankingHashMap().size();
    }

}
