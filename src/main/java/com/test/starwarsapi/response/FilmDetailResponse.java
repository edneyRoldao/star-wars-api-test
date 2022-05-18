package com.test.starwarsapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FilmDetailResponse implements Serializable {

    private Long version = 1L;
    private String description = "EMPTY";

    private String url;
    private String title;
    private String director;
    private String producer;
    private String edited;
    private String created;

    @JsonProperty("opening_crawl")
    private String openingCrawl;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("episode_id")
    private Long episodeId;

    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;

    public void updateVersion() {
        version++;
    }

}
