package ternant.invoiceModule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvoicesRepository extends JpaRepository<Invoice, UUID> {
    Invoice findByIdempotentKey(String idempotentKey);
}
