package com.test.starwarsapi.api;

import com.test.starwarsapi.aspect.annotation.Logger;
import com.test.starwarsapi.request.UpdateFilmRequest;
import com.test.starwarsapi.response.FilmDetailResponse;
import com.test.starwarsapi.service.FilmService;
import com.test.starwarsapi.util.SwaggerMessages;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = {"Star Wars API"})
@RequestMapping("api/star-wars")
public class StarWarsResource {

    private final FilmService filmService;

    @Logger
    @GetMapping("films")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = SwaggerMessages.GET_ALL_FILMS)
    public List<FilmDetailResponse> getAll() {
        return filmService.getFilms();
    }

    @Logger
    @GetMapping("films/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = SwaggerMessages.GET_FILM)
    public FilmDetailResponse getFilmDetail(@PathVariable Long id) {
        return filmService.getFilm(id);
    }

    @Logger
    @PatchMapping("films/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = SwaggerMessages.UPDATE_FILM_DESC)
    public void updateFilmDescription(@PathVariable Long id,
                                      @RequestParam(value = "film-description") String description) {
        filmService.updateFilmDescription(id, description);
    }

    @Logger
    @PutMapping("films/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = SwaggerMessages.UPDATE_FILM_DETAILS)
    public void updateFilmDetails(@PathVariable Long id, @RequestBody UpdateFilmRequest request) {
        filmService.updateFilmDetails(id, request);
    }

}
