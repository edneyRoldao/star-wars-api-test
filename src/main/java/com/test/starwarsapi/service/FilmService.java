package com.test.starwarsapi.service;

import com.test.starwarsapi.request.UpdateFilmRequest;
import com.test.starwarsapi.response.FilmDetailResponse;

import java.util.List;

public interface FilmService {

    List<FilmDetailResponse> getFilms();

    FilmDetailResponse getFilm(Long episodeId);

    void updateFilmDescription(Long episodeId, String description);

    void updateFilmDetails(Long episodeId, UpdateFilmRequest request);

}
