package org.example.todaymovie.model.repository;

import org.example.todaymovie.model.dto.MovieDTO;
import org.example.todaymovie.model.dto.MovieInfoDTO;
import org.example.todaymovie.model.dto.MovieParam;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

import static org.example.todaymovie.util.DotenvMixin.dotenv;

public interface MovieRepository {
    HttpClient httpClient = HttpClient.newHttpClient();

    final String baseURL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest";
    final String key = dotenv.get("MOVIE_KEY");

    public String callAPI(String url) throws IOException, InterruptedException;
    public MovieInfoDTO getMovieInfo(MovieDTO movie, String imageUrl) throws IOException, InterruptedException;
    public List<MovieDTO> getMovies(MovieParam param) throws IOException, InterruptedException;
}
