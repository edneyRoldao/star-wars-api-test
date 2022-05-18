package com.test.starwarsapi.config;

import com.test.starwarsapi.client.StarWarsAPIClient;
import com.test.starwarsapi.response.FilmsResponse;
import com.test.starwarsapi.service.FilmService;
import com.test.starwarsapi.service.impl.FilmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilmSingleConfig {

    @Autowired
    private StarWarsAPIClient client;

    @Bean
    public FilmService filmServiceSingleton() {
        FilmsResponse filmsResponse = client.getAllFilms()
                .orElseThrow(() -> new RuntimeException("star wars API Unavailable"));

        return new FilmServiceImpl(filmsResponse);
    }

}
