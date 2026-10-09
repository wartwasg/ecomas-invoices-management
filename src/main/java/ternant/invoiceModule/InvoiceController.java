package ternant.invoiceModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoicesService invoicesService;

    @PostMapping
    @PreAuthorize("hasAnyRole('REGULAR_MANAGER','TOP_MANAGER')")
    public ResponseEntity<?> createInvoice(@RequestBody InvoicesDTO invoicesDTO){

        InvoicesDTO invoice = invoicesService.createInvoice(invoicesDTO);

        return new ResponseEntity<InvoicesDTO>(invoicesDTO, HttpStatus.CREATED);
    }
}
