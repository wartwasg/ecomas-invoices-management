package ternant.invoiceModule;

import java.util.UUID;

public interface InvoicesService {
    InvoicesDTO createInvoice(InvoicesDTO invoicesDTO);

    InvoiceResponse getInvoices(Integer pageNumber, Integer pageSize, String orderBy, String order);

    InvoicesDTO deleteInvoice(UUID id);

    InvoiceResponse getInvoicesByLoggedInUser(Integer pageNumber, Integer pageSize, String orderBy, String order);
}
