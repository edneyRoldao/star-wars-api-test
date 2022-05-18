package com.test.starwarsapi.api;

import com.test.starwarsapi.BaseTest;
import com.test.starwarsapi.exception.FilmNotFoundException;
import com.test.starwarsapi.exception.FilmValidationException;
import com.test.starwarsapi.response.FilmsResponse;
import com.test.starwarsapi.service.FilmService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
public class StarWarsResourceTest extends BaseTest {

    @Autowired
    MockMvc mvc;

    private static final String BASE_PATH = "/api/star-wars/";

    @MockBean
    private FilmService service;

    @Test
    @SneakyThrows
    void testGetAllFilms() {
        FilmsResponse films = loadFile(FilmsResponse.class, "films-response");
        when(service.getFilms()).thenReturn(films.getResults());

        mvc.perform(get(BASE_PATH.concat("films"))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    @SneakyThrows
    void testGetFilmDetail() {
        Long episodeId = 5L;
        FilmsResponse films = loadFile(FilmsResponse.class, "films-response");

        var film = films.getResults()
                .stream()
                .filter(f -> f.getEpisodeId().equals(episodeId))
                .findFirst()
                .get();

        when(service.getFilm(episodeId)).thenReturn(film);

        mvc.perform(get(BASE_PATH.concat("films/{id}"), episodeId)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.episode_id", is(5)));
    }

    @Test
    @SneakyThrows
    void whenGetFilmDetailAndEpisodeIdDoesNotExit_httpStatusResponseShouldBe404() {
        Long episodeId = 30L;

        when(service.getFilm(episodeId)).thenThrow(new FilmNotFoundException());

        mvc.perform(get(BASE_PATH.concat("films/{id}"), episodeId)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testUpdateFilmDetails() {
        Long episodeId = 5L;
        String description = "test update description";

        mvc.perform(patch(BASE_PATH.concat("films/{id}"), episodeId)
                .param("film-description", description))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void whenUpdateFilmDetailsAndEpisodeIdDoesNotExit_httpStatusResponseShouldBe404() {
        Long episodeId = 5L;
        String description = "test update description";

        doThrow(new FilmNotFoundException()).when(service).updateFilmDescription(episodeId, description);

        mvc.perform(patch(BASE_PATH.concat("films/{id}"), episodeId)
                .param("film-description", description))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void whenUpdateFilmDetailsAndValidationFail_httpStatusResponseShouldBe400() {
        Long episodeId = 5L;
        String description = "test update description";

        doThrow(new FilmValidationException("")).when(service).updateFilmDescription(episodeId, description);

        mvc.perform(patch(BASE_PATH.concat("films/{id}"), episodeId)
                .param("film-description", description))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

}
