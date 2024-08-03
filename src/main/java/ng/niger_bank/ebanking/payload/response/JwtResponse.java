package ng.niger_bank.ebanking.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private Long id;

    private String lastName;

    private String firstName;

    private String profilePicture;

    private String email;

    private String gender;

    private String accessToken;

    private String tokenType = "Bearer";

}
