package ng.niger_bank.ebanking.service;

import ng.niger_bank.ebanking.payload.request.LoginRequest;
import ng.niger_bank.ebanking.payload.request.UserRequestDto;
import ng.niger_bank.ebanking.payload.response.ApiResponse;
import ng.niger_bank.ebanking.payload.response.BankResponse;
import ng.niger_bank.ebanking.payload.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    BankResponse registerUser(UserRequestDto userRequestDto);

    ResponseEntity<?> loginUser(LoginRequest loginRequest);
}
