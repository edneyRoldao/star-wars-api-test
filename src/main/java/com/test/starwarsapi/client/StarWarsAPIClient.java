package com.test.starwarsapi.client;

import com.test.starwarsapi.response.FilmsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@FeignClient(name = "star-wars-api", url = "${app.star-wars-api.host}")
public interface StarWarsAPIClient {

    @GetMapping("/api/films")
    Optional<FilmsResponse> getAllFilms();

}
