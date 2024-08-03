package ng.niger_bank.ebanking.infrastructure.config;


import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private final String CLOUD_NAME = "dbrqyy9fu";

    private final String SECRETE_KEY = "584298145817731";

    private final String SECRETE_ID = "_Ut_lwFBT1cbcjiiyXu8mCDiTl0";

    @Bean
    public Cloudinary cloudinary(){

        Map<String, String> config = new HashMap<>();

        config.put("cloud_name", CLOUD_NAME);

        config.put("api_key", SECRETE_KEY);

        config.put("api_secret", SECRETE_ID);

        return new Cloudinary(config);

    }



}
