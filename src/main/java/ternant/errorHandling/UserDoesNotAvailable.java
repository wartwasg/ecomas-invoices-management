package ternant.errorHandling;

public class UserDoesNotAvailable extends RuntimeException{
    public UserDoesNotAvailable(String s) {
        super(s);
    }
}
