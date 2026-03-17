package LogicTier;

//Exception that activates when trying to add a Kenken to Persistence that already exists
public class KenkenAlreadyInPersistence extends CustomException {
    public KenkenAlreadyInPersistence(String message) {
        super(message);
    }

    public void handleException() {
        
    }
}
