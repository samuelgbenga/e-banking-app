package ng.niger_bank.ebanking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@WebMvcTest
class EbankingApplicationTests {

	@Test
	void contextLoads() {
		assertThat(true).isTrue();
	}

}
