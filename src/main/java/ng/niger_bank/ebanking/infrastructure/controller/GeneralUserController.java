package ng.niger_bank.ebanking.infrastructure.controller;


import lombok.RequiredArgsConstructor;
import ng.niger_bank.ebanking.payload.response.BankResponse;
import ng.niger_bank.ebanking.service.GeneralUserService;
import ng.niger_bank.ebanking.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class GeneralUserController {

    private final GeneralUserService generalUserService;

    @PutMapping("/{id}/profile-pics")
    public ResponseEntity<BankResponse<String>> profileUpload(@PathVariable Long id, @RequestParam MultipartFile profilePic){

        if (profilePic.getSize() > AppConstant.MAX_FILE_SIZE) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new BankResponse<>("file exceed the required size"));
        }

        return generalUserService.uploadProfilePics(profilePic, id);
    }




}
