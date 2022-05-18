package com.test.starwarsapi.service.impl;

import com.test.starwarsapi.BaseTest;
import com.test.starwarsapi.converter.UpdateFilmRequestConverter;
import com.test.starwarsapi.exception.FilmNotFoundException;
import com.test.starwarsapi.exception.FilmValidationException;
import com.test.starwarsapi.request.UpdateFilmRequest;
import com.test.starwarsapi.response.FilmsResponse;
import com.test.starwarsapi.service.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmServiceImplTest extends BaseTest {

    private FilmService service;

    @BeforeEach
    void setup() {
        FilmsResponse films = loadFile(FilmsResponse.class, "films-response");
        service = new FilmServiceImpl(films);
    }

    @Test
    void whenGetFilms_shouldReturnAllFilms() {
        var response = service.getFilms();
        assertNotNull(response);
        assertEquals(6, response.size());
    }

    @Test
    void whenGetFilmsByIdFound_shouldReturnFilm() {
        Long episodeId = 1L;
        var film = service.getFilm(episodeId);
        assertNotNull(film);
        assertEquals(episodeId, film.getEpisodeId());
    }

    @Test
    void whenGetFilmsByIdNotFound_shouldThrowFilmNotFoundException() {
        FilmNotFoundException exception = assertThrows(FilmNotFoundException.class, () -> {
           Long episodeId = 11L;
           service.getFilm(episodeId);
        });

        assertNotNull(exception);
    }

    @Test
    void whenUpdateFilmDescriptionAndEverythingIsRight_shouldUpdateFilmDescription() {
        Long episodeId = 1L;
        String description = "new description test";
        var film = service.getFilm(episodeId);

        Long oldVersion = film.getVersion();
        String oldDescription = film.getDescription();
        String oldTitle = film.getTitle();

        service.updateFilmDescription(episodeId, description);

        assertTrue(film.getVersion() > oldVersion);
        assertNotEquals(oldDescription, film.getDescription());
        assertEquals(description, film.getDescription());
        assertEquals(oldTitle, film.getTitle());
    }

    @Test
    void whenUpdateFilmDescriptionAndFilmNotFound_shouldThrowFilmNotFoundException() {
        FilmNotFoundException exception = assertThrows(FilmNotFoundException.class, () -> {
            Long episodeId = 11L;
            String description = "new description test";
            service.updateFilmDescription(episodeId, description);
        });

        assertNotNull(exception);
    }

    @Test
    void whenUpdateFilmDescriptionAndDescriptionIsEmpty_shouldThrowFilmValidationException() {
        FilmValidationException exception = assertThrows(FilmValidationException.class, () -> {
            Long episodeId = 2L;
            String description = "";
            service.updateFilmDescription(episodeId, description);
        });

        assertNotNull(exception);
        assertEquals("The film description should not be empty", exception.getMessage());
    }

    @Test
    void whenUpdateFilmDescriptionAndDescriptionIsNull_shouldThrowFilmValidationException() {
        FilmValidationException exception = assertThrows(FilmValidationException.class, () -> {
            Long episodeId = 2L;
            service.updateFilmDescription(episodeId, null);
        });

        assertNotNull(exception);
        assertEquals("The film description should not be empty", exception.getMessage());
    }

    @Test
    void whenUpdateFilmDescriptionAndDescriptionIsTheSameAsOldOne_shouldThrowFilmValidationException() {
        FilmValidationException exception = assertThrows(FilmValidationException.class, () -> {
            Long episodeId = 2L;
            var film = service.getFilm(episodeId);
            String description = film.getDescription();
            service.updateFilmDescription(episodeId, description);
        });

        assertNotNull(exception);
        assertEquals("The film description should not be the same as the old one", exception.getMessage());
    }

    @Test
    void whenUpdateFilmDetailsWithTheRightRequest_shouldUpdate() {
        Long episodeId = 3L;
        UpdateFilmRequest request = loadFile(UpdateFilmRequest.class, "update-film-request");

        var film = service.getFilm(episodeId);
        String oldTitle = film.getTitle();
        String oldDirector = film.getDirector();
        String oldDescription = film.getDescription();
        Long oldVersion = film.getVersion();

        service.updateFilmDetails(episodeId, request);

        assertEquals(request.getTitle(), film.getTitle());
        assertEquals(request.getDirector(), film.getDirector());
        assertEquals(oldDescription, film.getDescription());
        assertNotEquals(oldTitle, film.getTitle());
        assertNotEquals(oldDirector, film.getDirector());
        assertTrue(oldVersion < film.getVersion());
    }

    @Test
    void whenUpdateFilmDetailsAndFilmNotFound_shouldThrowFilmNotFoundException() {
        FilmNotFoundException exception = assertThrows(FilmNotFoundException.class, () -> {
            Long episodeId = 20L;
            UpdateFilmRequest request = new UpdateFilmRequest();
            request.setProducer("test update producer");
            service.updateFilmDetails(episodeId, request);
        });

        assertNotNull(exception);
    }

    @Test
    void whenUpdateFilmDetailsAndRequestIsNull_shouldThrowFilmValidationException() {
        FilmValidationException exception = assertThrows(FilmValidationException.class, () -> {
            Long episodeId = 5L;
            service.updateFilmDetails(episodeId, null);
        });

        assertNotNull(exception);
        assertEquals(UpdateFilmRequestConverter.ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void whenUpdateFilmDetailsAndNoFieldsHaveChanged_shouldThrowFilmValidationException() {
        FilmValidationException exception = assertThrows(FilmValidationException.class, () -> {
            Long episodeId = 5L;
            String emp = "";
            UpdateFilmRequest request = new UpdateFilmRequest(emp, emp, emp, emp, emp);
            service.updateFilmDetails(episodeId, request);
        });

        assertNotNull(exception);
        assertEquals(UpdateFilmRequestConverter.ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void whenUpdateFilmDetailsAndFieldChangedIsTheSameAsTheOldOne_shouldThrowFilmValidationException() {
        FilmValidationException exception = assertThrows(FilmValidationException.class, () -> {
            Long episodeId = 5L;
            UpdateFilmRequest request = new UpdateFilmRequest();

            var film = service.getFilm(episodeId);
            request.setDescription(film.getDescription());

            service.updateFilmDetails(episodeId, request);
        });

        assertNotNull(exception);
        assertEquals(UpdateFilmRequestConverter.ERROR_MESSAGE, exception.getMessage());
    }

}
