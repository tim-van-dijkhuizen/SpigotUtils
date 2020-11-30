package nl.timvandijkhuizen.spigotutils.input;

public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 8180696511264638878L;
    
    public InvalidInputException(String error) {
        super(error);
    }

}
