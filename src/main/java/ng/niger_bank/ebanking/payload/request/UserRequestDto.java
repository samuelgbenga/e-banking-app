package ng.niger_bank.ebanking.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ng.niger_bank.ebanking.domain.enums.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "First Name Must Not Be Blank")
    @Size(min = 2, max= 125, message="Must be two character long")
    private String firstName;

    @NotBlank(message = " Must Not Be Blank")
    @Size(min = 2, max= 125, message="Must be two character long")
    private String lastName;

    private String otherName;

    @NotBlank(message = "Email must not be blank")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email address")
    private String email;

    private String password;

    private String gender;

    private String address;

    private String stateOfOrigin;

    private String phoneNumber;

    private String BVN;

    private String pin;

    private String bankName;

    private String profilePicture;

    private String status;

    private Role role;
}
