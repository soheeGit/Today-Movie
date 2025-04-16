package org.example.todaymovie.model.repository;

import org.example.todaymovie.model.dto.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.example.todaymovie.util.ObjectMapperMixin.objectMapper;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Override
    public String callAPI(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        }else {
            throw new RuntimeException("Failed. HTTP error code : " + response.statusCode());
        }
    }

    public MovieInfoDTO getMovieInfo(MovieDTO movie, String imageUrl) throws IOException, InterruptedException {
        String action = "movie/searchMovieInfo";
        String format = "json";
        String url = "%s/%s.%s?key=%s&movieCd=%s".formatted(
                baseURL, action, format, key, movie.code());
        String responseBody = callAPI(url);
//        System.out.println(responseBody);
        MovieInfoResponse movieInfoResponse = objectMapper.readValue(responseBody, MovieInfoResponse.class);
        MovieInfoResponse.MovieInfo info =
                movieInfoResponse.movieInfoResult().movieInfo();
        return new MovieInfoDTO(movie,
                info.nations().stream().map(MovieInfoResponse.Nation::nationNm).toList(),
                info.genres().stream().map(MovieInfoResponse.Genre::genreNm).toList(),
                info.directors().stream().map(MovieInfoResponse.Director::peopleNm).toList(),
                info.actors().stream().map(MovieInfoResponse.Actor::peopleNm).toList(),
                Long.parseLong(info.showTm()),
                imageUrl
        );
    }

    public List<MovieDTO> getMovies(MovieParam param) throws IOException, InterruptedException {
        String action = "boxoffice/searchDailyBoxOfficeList";
        String format = "json";
        String url = "%s/%s.%s?key=%s&targetDt=%s".formatted(
                baseURL, action, format, key, param.targetDate());
        String responseBody = callAPI(url);
        MovieResponse movieResponse = objectMapper.readValue(responseBody, MovieResponse.class);
        return movieResponse.boxOfficeResult().dailyBoxOfficeList()
                .stream().map((v) -> new MovieDTO(Long.parseLong(v.rank()), v.movieCd(), v.movieNm(), v.openDt(), Long.parseLong(v.audiAcc()))).toList();
    }
}
