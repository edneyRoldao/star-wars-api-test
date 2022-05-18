package com.test.starwarsapi.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmsResponse implements Serializable {

    private Long count;
    private List<FilmDetailResponse> results;

}
