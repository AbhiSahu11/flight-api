package nl.abnamro.api.flightsearch.error;

public class InvalidInputException extends IllegalArgumentException {

    public InvalidInputException(String msg){
        super(msg);
    }
}
