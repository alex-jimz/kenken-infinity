package LogicTier;

public abstract class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public abstract void handleException();

}

/*class IllegalMatrixPosition extends CustomException {
    public IllegalMatrixPosition(String message) {
        super(message);
    }

    @Override
    public void handleException() {
    }
}*/

/*class IllegalCellSelection extends CustomException {
    public IllegalCellSelection(String message) {
        super(message);
    }

    public void handleException() {
        
    }
}*/

/*class IllegalScore extends CustomException {
    public IllegalScore(String message) {
        super(message);
    }

    public void handleException() {
        
    }
}*/

/*class IllegalGameAction extends CustomException {
    public IllegalGameAction(String message) {
        super(message);
    }

    public void handleException() {
        
    }
}*/