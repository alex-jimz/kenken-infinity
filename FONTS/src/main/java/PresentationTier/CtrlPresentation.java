package PresentationTier;

import LogicTier.CtrlLogic.CtrlLogic;
import LogicTier.GameAlreadyExists;
import LogicTier.KenkenAlreadyInPersistence;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.awt.Toolkit;
import java.awt.Image;

public class CtrlPresentation {
    private CtrlLogic ctrlLogic;
    private MainView mainView;
    private MusicPlayer music;
    private final String PATH = "./Data";
    private GameView gameView;

    public CtrlPresentation() {
        try {
            ctrlLogic = new CtrlLogic();
            mainView = new MainView(this);
            gameView = null;
            Image icon = Toolkit.getDefaultToolkit().getImage(PATH+"/Icons/kenkenLogoUnitedAppEntranceNoTitle.png");
            mainView.setIconImage(icon);    
            mainView.setVisible(true);
            music = new MusicPlayer(PATH+"/Music/Galactic Games.wav");
            music.setVolume(0.5f);
            music.startMusic();
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        mainView.setAppEntrancePanel();
    }

    public void logOut() {
        ctrlLogic.logout();
    }

    public void setMainView(){
        gameView.setVisible(false);
        mainView.setVisible(true);
        mainView.setMainMenuPanel();
    }

    public void  startGameView(Vector<Vector<Integer>> board1, int kenkenId){

        mainView.setVisible(false);
        gameView = new GameView(this, board1, kenkenId, this.getDuration());
        Image icon = Toolkit.getDefaultToolkit().getImage(PATH+"/Icons/kenkenLogoUnitedAppEntranceNoTitle.png");
        gameView.setIconImage(icon);
        gameView.setVisible(true);
        

    }

    public void continueGameView(Vector<Vector<Integer>> board, Vector<Vector<Vector<Integer>>> partialSol, int kenkenId){

        mainView.setVisible(false);
        gameView = new GameView(this, board, kenkenId, partialSol, this.getDuration());
        Image icon = Toolkit.getDefaultToolkit().getImage(PATH+"/Icons/kenkenLogoUnitedAppEntranceNoTitle.png");
        gameView.setIconImage(icon);
        gameView.setVisible(true);



    }

    public void eliminateProfile() {

        try {
            ctrlLogic.eraseUser();
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
    }

    public boolean logIntoApp(String username, String password) {
        boolean success = false;
        try {
            success = ctrlLogic.login(username, password);
        }
        catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());

        }
        return success;
    }

    public Vector<Integer> getIntStats() {
        return ctrlLogic.consultIntegerStats();
    }

    public Vector<String> getStringStats() {
        return ctrlLogic.consultStringStats();
    }

    public boolean fillSquare(int x, int y, int value) {
        boolean success = false;
        try {
           success = ctrlLogic.fillSquare(x, y, value);

         } catch (IOException | ParseException e) {
           mainView.showErrorMessage(e.getMessage());
       }
        return success;
    }

    public Vector<Integer> getSquareValue(int x, int y) {
        return ctrlLogic.getCoordinate(x, y);
    }

    public void closeGame() {
        try {
            ctrlLogic.closeGame();
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
    }

    public void deleteGame(int gameID) {
        try {
            ctrlLogic.deleteGame(gameID);
        } catch (IOException | ParseException e ) {
            mainView.showErrorMessage(e.getMessage());
        }
    }
    public void fillNote(int i, int j, int note) {
        ctrlLogic.fillNote(i,j,note);
    }


    public Vector<Integer> askForHint() {
        try {
            return ctrlLogic.askHint();
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return null;
    }

    public void restartGame() {
         ctrlLogic.restartGame();
    }

    public boolean  isGameEnded(){
        return ctrlLogic.gameIsOver();
    }

    public Integer getLivesRemaining(){
        return ctrlLogic.getNumLives();
    }

    public Integer getHintsRemaining(){
        return ctrlLogic.getNumHints();
    }

    public Vector<Vector<Vector<Integer>>> continueGame(int idKk){
        try {
            return ctrlLogic.continueGame(idKk);
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return null;
    }

    public boolean registerIntoApp(String username, String password, String confirmPassword) {

        boolean success = false;
        try {
            success = ctrlLogic.register(username, password, confirmPassword);
        }
        catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return success;
    }

    public LinkedHashMap<String, Integer> getRanking() {
        return ctrlLogic.consultRanking();
    }

    public String getRules() {
        Vector<String> rules = new Vector<String>();

        try{
            rules = ctrlLogic.consultRules();
        } catch (IOException e) {
            mainView.showErrorMessage(e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        for (String rule : rules) {
            sb.append(rule).append("\n");
        }
        return sb.toString().trim();
    }

    public String getFormatExplanation() {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH+"/FormatExplanation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }

    public void startMusic() {
        music.startMusic();
    }

    public void stopMusic() {
        music.stopMusic();
    }

    public void setMusicVolume(float volume) {
        music.setVolume(volume);
    }

    public boolean importKenken(String filepath) {
        try {
            return ctrlLogic.importKenken(filepath);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        catch (KenkenAlreadyInPersistence er) {
            throw er;
        }
    }

    public Vector<Integer> getAvailableKenken(){
        return ctrlLogic.listKenkenLibrary();
    }

    public boolean  newGame(int id) {

        boolean available = true;
        try {

            ctrlLogic.newGame(id);

        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        } catch (GameAlreadyExists e){
            available = false;
        }

        return available;
    }

    public void pauseGame() {
            ctrlLogic.pauseGame();

    }

    public void resumeGame() {

        ctrlLogic.resumeGame();

    }

    public Vector<Vector<Integer>> getBoard(int id){
        Vector<Vector<Integer>> board = new Vector<Vector<Integer>>();
        try {
            board = ctrlLogic.getKenkenBoard(id);
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return board;
    }

    public int[] getOpIds(){
        return ctrlLogic.getOpIds();
    }

    public String[] getOpSymbols(){
        return ctrlLogic.getOpSymbols();
    }

    public String[] getOpNames(){
        return ctrlLogic.getOpNames();
    }

    public Vector<Integer> listGames() {
        try {
            return ctrlLogic.getGameList();
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return null;
    }

    public Vector<Vector<Integer>> getRegionBoard(int idKenken) {
        try {
            return ctrlLogic.getRegionBoard(idKenken);
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return null;
    }

    public Vector<Vector<Integer>> getRegionInfo(int idKenken) {
        try {
            return ctrlLogic.getRegionInfo(idKenken);
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return null;
    }

    public int generatePersonalizedKenken(Vector<String> inputFile) {
        try {
            return ctrlLogic.generatePersonalizedKenken(inputFile);
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return -1;
    }

    public int generateRandomKenken(int dim, Vector<Integer> operations) {
        try {
            return ctrlLogic.generateRandomKenken(dim, operations);
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return -1;
    }

    public int getKenkenSize(int idKenken) {
        try {
            return ctrlLogic.getKenkenSize(idKenken);
        } catch (IOException | ParseException e) {
            mainView.showErrorMessage(e.getMessage());
        }
        return -1;
    }

    public String[] getMusicOptions() {
        String musicFolderPath = PATH+"/Music/";
        List<String> musicFileNames = new ArrayList<>();
        File musicFolder = new File(musicFolderPath);
        if (musicFolder.exists() && musicFolder.isDirectory()) {
            File[] files = musicFolder.listFiles();
            Arrays.sort(files);
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                    musicFileNames.add(fileNameWithoutExtension);
                }
            }
        } else {
            mainView.showErrorMessage("Invalid Music directory path or directory does not exist.");
        }
        return musicFileNames.toArray(new String[0]);
    }

    public void selectMusic(String filename) {
        try {
            music.changeMusic(PATH + "/Music/" + filename + ".wav");
        } catch (Exception e) {
            mainView.showErrorMessage(e.getMessage());
        }
    }

    public void clearFile(String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("");
            writer.close();
        } catch (IOException ex) {
            String message = "Error occurred while clearing the file: " + ex.getMessage();
            mainView.showErrorMessage(message);
        }
    }

    public void writeToFile(String filePath, String userInput) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(userInput);
            writer.close();
        } catch (IOException ex) {
            String message = "Error occurred while writing to file: " + ex.getMessage();
            mainView.showErrorMessage(message);
        }
    }

    public String getDuration() {
        return ctrlLogic.getDuration();
    }

    public Vector<Integer> getUserPos() {
        return ctrlLogic.getUserPos();
    }

    public String getUsername() {
        return ctrlLogic.getUsername();
    }

    public void createGeneratorView(int size) {
        GeneratorView gridView = new GeneratorView(this, size);
        Image icon = Toolkit.getDefaultToolkit().getImage(PATH+"/Icons/kenkenLogoUnitedAppEntranceNoTitle.png");
        gridView.setIconImage(icon);
        gridView.setVisible(true);
        mainView.setVisible(false);
    }

    public void navigateToMainMenuFromGenerator() {
        mainView.setVisible(true);
        mainView.setMainMenuPanel();
    }

    public int getGamePoints(){
        return ctrlLogic.getGamePoints();
    }

    public int getRkSize(){
        return ctrlLogic.getRkSize();
    }
}
