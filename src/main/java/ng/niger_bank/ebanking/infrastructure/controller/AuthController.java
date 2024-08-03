package ng.niger_bank.ebanking.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ng.niger_bank.ebanking.payload.request.LoginRequest;
import ng.niger_bank.ebanking.payload.request.UserRequestDto;
import ng.niger_bank.ebanking.payload.response.ApiResponse;
import ng.niger_bank.ebanking.payload.response.BankResponse;
import ng.niger_bank.ebanking.payload.response.JwtResponse;
import ng.niger_bank.ebanking.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register-user")
    public BankResponse createUserAccount(@Valid @RequestBody UserRequestDto userRequestDto){

        return authService.registerUser(userRequestDto);
    }

    @PostMapping("/login-user")
    public ResponseEntity<?> loginAccount(@Valid @RequestBody LoginRequest loginRequest){

        return authService.loginUser(loginRequest);
    }

}
