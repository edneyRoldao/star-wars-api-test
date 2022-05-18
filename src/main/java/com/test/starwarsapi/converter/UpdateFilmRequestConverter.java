package com.test.starwarsapi.converter;

import com.test.starwarsapi.exception.FilmValidationException;
import com.test.starwarsapi.request.UpdateFilmRequest;
import com.test.starwarsapi.response.FilmDetailResponse;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class UpdateFilmRequestConverter implements BiFunction<UpdateFilmRequest, FilmDetailResponse, FilmDetailResponse> {

    public static final String ERROR_MESSAGE = "No film fields have changed. At least one field should be updated.";

    @Override
    public FilmDetailResponse apply(UpdateFilmRequest request, FilmDetailResponse response) {
        if (Objects.isNull(request))
            throw new FilmValidationException(ERROR_MESSAGE);

        boolean wasNotUpdated = true;

        if (isNotBlank(request.getTitle()) && !request.getTitle().equalsIgnoreCase(response.getTitle())) {
            response.setTitle(request.getTitle());
            wasNotUpdated = false;
        }

        if (isNotBlank(request.getDirector()) && !request.getDirector().equalsIgnoreCase(response.getDirector())) {
            response.setDirector(request.getDirector());
            wasNotUpdated = false;
        }

        if (isNotBlank(request.getProducer()) && !request.getProducer().equalsIgnoreCase(response.getProducer())) {
            response.setProducer(request.getProducer());
            wasNotUpdated = false;
        }

        if (isNotBlank(request.getDescription()) && !request.getDescription().equalsIgnoreCase(response.getDescription())) {
            response.setDescription(request.getDescription());
            wasNotUpdated = false;
        }

        if (isNotBlank(request.getOpeningCrawl()) && !request.getOpeningCrawl().equalsIgnoreCase(response.getOpeningCrawl())) {
            response.setOpeningCrawl(request.getOpeningCrawl());
            wasNotUpdated = false;
        }

        if (wasNotUpdated) {
            throw new FilmValidationException(ERROR_MESSAGE);
        }

        response.updateVersion();
        response.setEdited(LocalDateTime.now().toString());

        return response;
    }

}
