package ternant.errorHandling;

public class InvoiceAlreadyExists extends RuntimeException {
    public InvoiceAlreadyExists(String message) {
        super(message);
    }
}
