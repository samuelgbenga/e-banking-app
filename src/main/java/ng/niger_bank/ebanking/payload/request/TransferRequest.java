package ng.niger_bank.ebanking.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {

    private String destinationAccountNumber;

    private String sourceAccountNumber;

    private BigDecimal amount;



}
