package ng.niger_bank.ebanking.payload.response;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BankResponse<T> {

    private String responseCode;

    private String responseMessage;

    private AccountInfoDto accountInfo;





    public BankResponse(String responseCode, String responseMessage, AccountInfoDto accountInfo) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.accountInfo = accountInfo;
    }

    public BankResponse(String responseMessage){
        this.responseMessage = responseMessage;
    }

    public BankResponse(String uploadSuccessfully, String fileUrl) {
        this.responseMessage = uploadSuccessfully;


    }
}
