package ternant.invoiceModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ternant.config.AppConstants;

import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoicesService invoicesService;

    @PostMapping
    @PreAuthorize("hasAnyRole('REGULAR_MANAGER','TOP_MANAGER')")
    public ResponseEntity<?> createInvoice(@RequestBody InvoicesDTO invoicesDTO){

        InvoicesDTO invoice = invoicesService.createInvoice(invoicesDTO);

        return new ResponseEntity<InvoicesDTO>(invoice, HttpStatus.CREATED);
    }
    @GetMapping
    @PreAuthorize("hasRole('TOP_MANAGER')")
    public ResponseEntity<?> getAllInvoices(
            @RequestParam(name = "page",required = false,defaultValue = AppConstants.pageNumber) Integer pageNumber,
            @RequestParam(name = "size",required = false,defaultValue = AppConstants.pageSize) Integer pageSize,
            @RequestParam(name = "orderby",required = false,defaultValue = AppConstants.orderBy) String orderBy,
            @RequestParam(name = "order",required = false,defaultValue = AppConstants.order) String order){

         InvoiceResponse invoiceResponse = invoicesService.getInvoices(pageNumber,pageSize,orderBy,order);

         return new ResponseEntity<InvoiceResponse>(invoiceResponse,HttpStatus.OK);
    }
    @DeleteMapping("/id/{id}")
    @PreAuthorize("hasRole('TOP_MANAGER')")
    public ResponseEntity<?> deleteInvoice(@PathVariable UUID id){
        InvoicesDTO invoiceDeleted = invoicesService.deleteInvoice(id);

        return new ResponseEntity<InvoicesDTO>(invoiceDeleted,HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('TOP_MANAGER','REGULAR_MANAGER')")
    public ResponseEntity<?> getInvoiceByLoggedUser(
            @RequestParam(name = "page",required = false,defaultValue = AppConstants.pageNumber) Integer pageNumber,
            @RequestParam(name = "size",required = false,defaultValue = AppConstants.pageSize) Integer pageSize,
            @RequestParam(name = "orderby",required = false,defaultValue = AppConstants.orderBy) String orderBy,
            @RequestParam(name = "order",required = false,defaultValue = AppConstants.order) String order
    ){
        InvoiceResponse invoiceResponse = invoicesService.getInvoicesByLoggedInUser(pageNumber,pageSize,orderBy,order);

        return new ResponseEntity<InvoiceResponse>(invoiceResponse,HttpStatus.OK);
    }
}
