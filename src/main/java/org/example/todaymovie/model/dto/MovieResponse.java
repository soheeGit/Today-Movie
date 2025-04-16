package org.example.todaymovie.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieResponse(BoxOfficeResult boxOfficeResult) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record BoxOfficeResult(List<DailyBoxOfficeList> dailyBoxOfficeList) { }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DailyBoxOfficeList(String rank, String movieCd, String movieNm, String openDt, String audiAcc) { }
}
