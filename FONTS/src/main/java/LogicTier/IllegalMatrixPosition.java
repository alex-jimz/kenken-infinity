package LogicTier;

public class IllegalMatrixPosition extends CustomException {
    public IllegalMatrixPosition(String message) {
        super(message);
    }

    @Override
    public void handleException() {
    }
}

