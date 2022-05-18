package com.test.starwarsapi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFilmRequest implements Serializable {

    private String title;
    private String director;
    private String producer;
    private String description;
    private String openingCrawl;

}
