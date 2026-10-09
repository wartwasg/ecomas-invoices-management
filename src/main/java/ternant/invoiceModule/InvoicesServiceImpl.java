package ternant.invoiceModule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ternant.errorHandling.InvoiceAlreadyExists;

import java.time.Instant;

@Service
public class InvoicesServiceImpl implements InvoicesService{
    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InvoicesDTO createInvoice(InvoicesDTO invoicesDTO) {
        Invoice invoiceFromDB = invoicesRepository.findByIdempotentKey(invoicesDTO.getIdempotentKey());

        if(invoiceFromDB == null){
            Invoice invoiceCreated = modelMapper.map(invoicesDTO,Invoice.class);

            invoiceCreated.setStatus(InvoiceStatus.SENT);

            invoiceCreated.setCreatedAt(Instant.now());

            invoiceCreated.setUser(null);

            invoicesRepository.save(invoiceCreated);

            return modelMapper.map(invoiceCreated, InvoicesDTO.class);
        }
        else{
            throw new InvoiceAlreadyExists("Invoice already exists");
        }
    }
}
