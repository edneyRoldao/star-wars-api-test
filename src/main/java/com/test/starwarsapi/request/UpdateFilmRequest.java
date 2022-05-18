package com.test.starwarsapi.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateFilmRequest implements Serializable {

    private String title;
    private String director;
    private String producer;
    private String description;
    private String openingCrawl;

}
