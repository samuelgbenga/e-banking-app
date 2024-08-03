package ng.niger_bank.ebanking.service.impl;

import lombok.RequiredArgsConstructor;
import ng.niger_bank.ebanking.domain.entity.UserEntity;
import ng.niger_bank.ebanking.payload.response.BankResponse;
import ng.niger_bank.ebanking.repository.UserRepository;
import ng.niger_bank.ebanking.service.FileUploadService;
import ng.niger_bank.ebanking.service.GeneralUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GeneralUserServiceImpl implements GeneralUserService {

    private final FileUploadService fileUploadService;

    private final UserRepository userRepository;


    @Override
    public ResponseEntity<BankResponse<String>> uploadProfilePics(MultipartFile multipartFile, Long userId) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        String fileUrl = null;


        try {
            if(userEntityOptional.isPresent()){
                fileUrl = fileUploadService.uploadFile(multipartFile);

                UserEntity userEntity = userEntityOptional.get();

                //update the profile picture
                userEntity.setProfilePicture(fileUrl);

                userRepository.save(userEntity);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(
           new BankResponse<>("Upload Successfully", fileUrl)
        );


    }
}
