package ternant.errorHandling;

public class JwtTokenIsNotAvailable extends RuntimeException {
    public JwtTokenIsNotAvailable(String message) {
        super(message);
    }
}
