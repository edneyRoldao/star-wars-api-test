package com.test.starwarsapi.converter;

import com.test.starwarsapi.request.UpdateFilmRequest;
import com.test.starwarsapi.response.FilmDetailResponse;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class UpdateFilmRequestConverter implements BiFunction<UpdateFilmRequest, FilmDetailResponse, FilmDetailResponse> {

    @Override
    public FilmDetailResponse apply(UpdateFilmRequest request, FilmDetailResponse filmDetailResponse) {
        if (Objects.isNull(request))
            return filmDetailResponse;

        boolean wasUpdated = false;

        if (isNotBlank(request.getTitle())) {
            filmDetailResponse.setTitle(request.getTitle());
            wasUpdated = true;
        }

        if (isNotBlank(request.getDirector())) {
            filmDetailResponse.setDirector(request.getDirector());
            wasUpdated = true;
        }

        if (isNotBlank(request.getProducer())) {
            filmDetailResponse.setProducer(request.getProducer());
            wasUpdated = true;
        }

        if (isNotBlank(request.getDescription())) {
            filmDetailResponse.setDescription(request.getDescription());
            wasUpdated = true;
        }

        if (isNotBlank(request.getOpeningCrawl())) {
            filmDetailResponse.setOpeningCrawl(request.getOpeningCrawl());
            wasUpdated = true;
        }

        if (wasUpdated) {
            filmDetailResponse.setEdited(LocalDateTime.now());
            filmDetailResponse.setVersion(filmDetailResponse.getVersion() + 1);
        }

        return filmDetailResponse;
    }

}
