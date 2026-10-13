package ternant.invoiceModule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ternant.userModule.User;

import java.util.UUID;

public interface InvoicesRepository extends JpaRepository<Invoice, UUID> {
    Invoice findByIdempotentKey(String idempotentKey);

    Page<Invoice> findByUser(User user, Pageable pageable);
}
