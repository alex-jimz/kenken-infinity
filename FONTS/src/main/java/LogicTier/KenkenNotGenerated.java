package LogicTier;

//Exception that activates when trying to generate a Kenken but it has not been possible
public class KenkenNotGenerated extends CustomException {
    public KenkenNotGenerated(String message) {
        super(message);
    }

    public void handleException() {
        
    }
}
