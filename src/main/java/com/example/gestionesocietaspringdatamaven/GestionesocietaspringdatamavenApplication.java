package com.example.gestionesocietaspringdatamaven;

import com.example.gestionesocietaspringdatamaven.service.BatteriaDiTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionesocietaspringdatamavenApplication implements CommandLineRunner {

	@Autowired
	private BatteriaDiTestService batteriaDiTestService;

	public static void main(String[] args) {
		SpringApplication.run(GestionesocietaspringdatamavenApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("################ START   #################");
		System.out.println("################ eseguo i test  #################");
		batteriaDiTestService.testInserisceSocieta();
		batteriaDiTestService.testFindByExample();


		System.out.println("################ FINE - PASSED  #################");
	}
}
