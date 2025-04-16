package org.example.todaymovie.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieInfoResponse(MovieInfoResult movieInfoResult) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MovieInfoResult(MovieInfo movieInfo) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MovieInfo(
            String showTm, // 상영시간
            List<Nation> nations, // 국가
            List<Genre> genres, // 장르
            List<Director> directors, // 감독
            List<Actor> actors // 배우
    ) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Nation(String nationNm) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Genre(String genreNm) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Director(String peopleNm) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Actor(String peopleNm) {}
}