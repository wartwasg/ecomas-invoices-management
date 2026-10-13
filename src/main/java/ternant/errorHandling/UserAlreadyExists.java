package ternant.errorHandling;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
