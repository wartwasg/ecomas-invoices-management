package ternant.errorHandling;

public class InvoiceDoesNotExist extends RuntimeException {
    public InvoiceDoesNotExist(String invoiceDoesNotExist) {
        super(invoiceDoesNotExist);
    }
}
