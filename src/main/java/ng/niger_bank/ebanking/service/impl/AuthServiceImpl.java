package ng.niger_bank.ebanking.service.impl;

import lombok.RequiredArgsConstructor;
import ng.niger_bank.ebanking.domain.entity.UserEntity;
import ng.niger_bank.ebanking.domain.enums.Role;
import ng.niger_bank.ebanking.payload.request.EmailDetails;
import ng.niger_bank.ebanking.payload.request.LoginRequest;
import ng.niger_bank.ebanking.payload.request.UserRequestDto;
import ng.niger_bank.ebanking.payload.response.AccountInfoDto;
import ng.niger_bank.ebanking.payload.response.ApiResponse;
import ng.niger_bank.ebanking.payload.response.BankResponse;
import ng.niger_bank.ebanking.payload.response.JwtResponse;
import ng.niger_bank.ebanking.repository.UserRepository;
import ng.niger_bank.ebanking.service.AuthService;
import ng.niger_bank.ebanking.service.EmailService;
import ng.niger_bank.ebanking.utils.AccountUtils;
import ng.niger_bank.ebanking.utils.EmailBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public BankResponse registerUser(UserRequestDto userRequestDto) {
        if(userRepository.existsByEmail(userRequestDto.getEmail())){
            BankResponse response = BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .build();

            return response;
        }

        UserEntity newUser = UserEntity.builder()
                .firstName(userRequestDto.getFirstName())
                .lastName(userRequestDto.getLastName())
                .email(userRequestDto.getEmail())
                .BVN(userRequestDto.getBVN())
                .accountBalance(BigDecimal.ONE)
                .bankName("Nigeria e-banking")
                .profilePicture("https://res.cloudinary.com/dpfqbb9pl/image/upload/v1701260428/maleprofile_ffeep9.png")
                .stateOfOrigin(userRequestDto.getStateOfOrigin())
                .address(userRequestDto.getAddress())
                .gender(userRequestDto.getGender())
                .password(userRequestDto.getPassword())
                .pin(userRequestDto.getPin())
                .otherName(userRequestDto.getOtherName())
                .accountNumber(AccountUtils.generateAccountNumber())
                .role(Role.USER)
                .status("ACTIVE")
                .build();


        UserEntity saveUser = userRepository.save(newUser);

        // add email Alert here
        EmailDetails emailDetail = EmailDetails.builder()
                .recipient(saveUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody(EmailBody.buildEmail(saveUser.getFirstName(), saveUser.getLastName()))
                .attachment("many things")
                .build();

        emailService.sendMimeEmailAlert(emailDetail);


        return BankResponse.builder()
                .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                .accountInfo(AccountInfoDto.builder()
                        .accountBalance(saveUser.getAccountBalance())
                        .accountNumber(saveUser.getAccountNumber())
                        .bankName(saveUser.getBankName())
                        .accountName(saveUser.getFirstName() +" "+ saveUser.getLastName() +" "+ saveUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(loginRequest.getEmail());

        boolean userExist = userRepository.existsByEmail(loginRequest.getEmail());
        if(!userExist){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email does not exist!");
        }

        EmailDetails loginAlert= EmailDetails.builder()
                .subject("You are logged in")
                .recipient(loginRequest.getEmail())
                .messageBody(EmailBody.buildEmailLogin(userEntityOptional.get().getFirstName(), userEntityOptional.get().getLastName()))
                .build();

        emailService.sendMimeEmailAlert(loginAlert);

        UserEntity user = userEntityOptional.get();

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        "Login Successfully",
                        JwtResponse.builder()
                                .accessToken("generate token here")
                                .tokenType("Bearer")
                                .email(user.getEmail())
                                .build()
                )
                );
    }


}
