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
public class DebitRequest {

    private String accountNumber;

    private BigDecimal amount;

}
