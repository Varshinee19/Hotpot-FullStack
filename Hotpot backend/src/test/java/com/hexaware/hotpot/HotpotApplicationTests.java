package com.hexaware.hotpot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class HotpotApplicationTests {

	@Test
	void contextLoads() {
	}


}
