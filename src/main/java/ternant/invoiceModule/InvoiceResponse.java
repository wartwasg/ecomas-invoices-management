package ternant.invoiceModule;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponse {
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElement;
    private boolean isLast;
    private List<InvoicesDTO> invoices = new ArrayList<>();
}
