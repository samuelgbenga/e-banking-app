package ng.niger_bank.ebanking.payload.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String recipient;

    private String messageBody;

    private String attachment;

    private String subject;

}
