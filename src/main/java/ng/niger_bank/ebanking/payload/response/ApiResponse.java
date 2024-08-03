package ng.niger_bank.ebanking.payload.response;

import lombok.*;
import ng.niger_bank.ebanking.utils.DateUtils;

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private String message;

    private T data;

    private String responseTime;

    public ApiResponse(String message, T data){

        this.message = message;
        this.data = data;
        this.responseTime = DateUtils.dateToString(LocalDateTime.now());
    }

    public ApiResponse(String message){
        this.message = message;
        this.responseTime = "something about time";
    }


}
