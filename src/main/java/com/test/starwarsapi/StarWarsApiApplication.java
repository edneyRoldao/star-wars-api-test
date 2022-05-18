package com.test.starwarsapi;

import com.test.starwarsapi.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarWarsApiApplication implements CommandLineRunner {

	@Autowired
	private FilmService filmService;

	public static void main(String[] args) {
		SpringApplication.run(StarWarsApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var r = filmService.getFilms();
		System.out.println(r);
	}

}
