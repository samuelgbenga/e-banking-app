package ng.niger_bank.ebanking.service;

import ng.niger_bank.ebanking.payload.response.BankResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface GeneralUserService {

    ResponseEntity<BankResponse<String>> uploadProfilePics(MultipartFile multipartFile, Long userId);
}
