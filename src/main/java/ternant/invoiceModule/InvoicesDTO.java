package ternant.invoiceModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ternant.userModule.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoicesDTO {

    private UUID invoiceId;

    private BigDecimal amount;

    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    private User user;

    @Size(min = 10,max = 10,message = "idempotent key should be 10 characters")
    private String idempotentKey;
}
