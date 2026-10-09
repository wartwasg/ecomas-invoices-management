package ternant.invoiceModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoicesDTO {
    private UUID invoiceId;
    private BigDecimal amount;
    private InvoiceStatus status;
    private String idempotentKey;
}
