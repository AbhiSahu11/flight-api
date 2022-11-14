package nl.abnamro.api.flightsearch.service;

public class InvalidInputException extends IllegalArgumentException {

    public InvalidInputException(String msg){
        super(msg);
    }
}
