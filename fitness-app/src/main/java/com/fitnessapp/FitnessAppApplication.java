package com.fitnessapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //odavde kreće aplikacija
public class FitnessAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessAppApplication.class, args); 
		//Ovo pokreće celu aplikaciju
	}

}
