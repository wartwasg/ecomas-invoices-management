package ternant.errorHandling;

public class WrongUsernameOrPasswordException extends RuntimeException {
    public WrongUsernameOrPasswordException(String wrongUsernameOrPassword) {
        super(wrongUsernameOrPassword);
    }
}
