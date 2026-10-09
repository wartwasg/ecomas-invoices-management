package ternant.invoiceModule;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import ternant.userModule.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID invoiceId;

    @PositiveOrZero(message = "amount cannot be negative")
    private BigDecimal amount;

    @Column(updatable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Size(min = 10,max = 10,message = "idempotent key should be 10 characters")
    @Column(unique = true)
    private String idempotentKey;
}
