package ternant.invoiceModule;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ternant.authConfig.AuthUtil;
import ternant.errorHandling.InvoiceAlreadyExists;
import ternant.errorHandling.InvoiceDoesNotExist;
import ternant.userModule.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoicesServiceImpl implements InvoicesService{
    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    AuthUtil authUtil;

    @Override
    public InvoicesDTO createInvoice(InvoicesDTO invoicesDTO) {
        Invoice invoiceFromDB = invoicesRepository.findByIdempotentKey(invoicesDTO.getIdempotentKey());

        if(invoiceFromDB == null){
            Invoice invoiceCreated = modelMapper.map(invoicesDTO,Invoice.class);

            invoiceCreated.setStatus(InvoiceStatus.SENT);

            invoiceCreated.setCreatedAt(Instant.now());

            invoiceCreated.setUser(authUtil.getUser());

            invoiceCreated.setIdempotentKey(invoicesDTO.getIdempotentKey());

            return modelMapper.map(invoicesRepository.save(invoiceCreated), InvoicesDTO.class);
        }
        else{
            throw new InvoiceAlreadyExists("Invoice already exists");
        }
    }

    @Override
    public InvoiceResponse getInvoices(Integer pageNumber, Integer pageSize, String orderBy, String order) {

        Sort sort = order.equalsIgnoreCase("ASC") ?
                     Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Invoice> invoicePage = invoicesRepository.findAll(pageable);

        List<Invoice> invoicesList = invoicePage.getContent();

        List<InvoicesDTO> invoicesDTOS = invoicesList.stream()
                .map(invoice -> modelMapper.map(invoice, InvoicesDTO.class))
                .toList();

        return InvoiceResponse.builder()
                .invoices(invoicesDTOS)
                .isLast(invoicePage.isLast())
                .pageNumber(invoicePage.getNumber())
                .pageSize(invoicePage.getSize())
                .totalElement(invoicePage.getTotalElements())
                .build();
    }

    @Override
    public InvoicesDTO deleteInvoice(UUID id) {

        Invoice invoiceInDb = invoicesRepository.findById(id)
                .orElseThrow(()-> new InvoiceDoesNotExist("Invoice does not exist"));

        invoicesRepository.delete(invoiceInDb);

        return modelMapper.map(invoiceInDb,InvoicesDTO.class);
    }

    @Override
    public InvoiceResponse getInvoicesByLoggedInUser(Integer pageNumber, Integer pageSize, String orderBy, String order) {
        User user = authUtil.getUser();

        Sort sort = order.equalsIgnoreCase("ASC") ?
                Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Invoice> invoicePage = invoicesRepository.findByUser(user,pageable);

        List<Invoice> invoicesList = invoicePage.getContent();

        List<InvoicesDTO> invoicesDTOS = invoicesList.stream()
                .map(invoice -> modelMapper.map(invoice, InvoicesDTO.class))
                .toList();

        return InvoiceResponse.builder()
                .invoices(invoicesDTOS)
                .isLast(invoicePage.isLast())
                .pageNumber(invoicePage.getNumber())
                .pageSize(invoicePage.getSize())
                .totalElement(invoicePage.getTotalElements())
                .build();
    }
}
