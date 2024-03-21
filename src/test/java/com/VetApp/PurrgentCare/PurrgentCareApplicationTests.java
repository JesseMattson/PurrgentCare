package com.VetApp.PurrgentCare;

import static org.assertj.core.api.Assertions.assertThat;

import com.VetApp.PurrgentCare.controllers.PersonController;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import com.VetApp.PurrgentCare.services.PersonServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PurrgentCareApplicationTests {

	@Autowired
	private PersonController personController;

	@Autowired
	private PersonServiceInterface personService;

	@Autowired
	private PersonRepository personRepository;

	@Test
	void contextLoads() throws Exception {
		assertThat(personController).isNotNull();
		assertThat(personService).isNotNull();
		assertThat(personRepository).isNotNull();
	}

}
