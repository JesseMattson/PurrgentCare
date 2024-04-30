package com.VetApp.PurrgentCare;

import com.VetApp.PurrgentCare.controllers.PersonController;
import com.VetApp.PurrgentCare.controllers.PetController;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import com.VetApp.PurrgentCare.repositories.PetRepository;
import com.VetApp.PurrgentCare.services.PersonServiceInterface;
import com.VetApp.PurrgentCare.services.PetServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PurrgentCareApplicationTests {

	@Autowired
	private PersonController personController;
	@Autowired
	private PetController petController;

	@Autowired
	private PersonServiceInterface personService;
	@Autowired
	private PetServiceImplementation petService;


	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private PetRepository petRepository;

	@Test
	void contextLoads() throws Exception {
		assertThat(personController).isNotNull();
		assertThat(personService).isNotNull();
		assertThat(personRepository).isNotNull();
		assertThat(petController).isNotNull();
		assertThat(petService).isNotNull();
		assertThat(petController).isNotNull();
		}





	}
