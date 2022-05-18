package com.test.starwarsapi.service.impl;

import com.test.starwarsapi.aspect.annotation.Logger;
import com.test.starwarsapi.converter.UpdateFilmRequestConverter;
import com.test.starwarsapi.exception.FilmNotFoundException;
import com.test.starwarsapi.exception.FilmValidationException;
import com.test.starwarsapi.request.UpdateFilmRequest;
import com.test.starwarsapi.response.FilmDetailResponse;
import com.test.starwarsapi.response.FilmsResponse;
import com.test.starwarsapi.service.FilmService;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class FilmServiceImpl implements FilmService {

    private final FilmsResponse films;

    public FilmServiceImpl(FilmsResponse films) {
        this.films = films;
    }

    @Logger
    @Override
    public List<FilmDetailResponse> getFilms() {
        return films.getResults();
    }

    @Logger
    @Override
    public FilmDetailResponse getFilm(Long episodeId) {
        return getFilteredFilm(episodeId);
    }

    @Logger
    @Override
    public void updateFilmDescription(Long episodeId, String description) {
        var film = getFilteredFilm(episodeId);

        validateUpdateDescription(description, film);

        film.setDescription(description);
        film.setEdited(LocalDateTime.now().toString());
        film.updateVersion();
    }

    @Logger
    @Override
    public void updateFilmDetails(Long episodeId, UpdateFilmRequest request) {
        var film = getFilteredFilm(episodeId);
        UpdateFilmRequestConverter converter = new UpdateFilmRequestConverter();
        var filmUpdated = converter.apply(request, film);
    }

    private void validateUpdateDescription(String description, FilmDetailResponse film) {
        if (StringUtils.isBlank(description))
            throw new FilmValidationException("The film description should not be empty");

        if (description.equalsIgnoreCase(film.getDescription()))
            throw new FilmValidationException("The film description should not be the same as the old one");
    }

    private FilmDetailResponse getFilteredFilm(Long id) {
        return films.getResults()
                .stream()
                .filter(f -> f.getEpisodeId().equals(id))
                .findFirst()
                .orElseThrow(FilmNotFoundException::new);
    }

}
