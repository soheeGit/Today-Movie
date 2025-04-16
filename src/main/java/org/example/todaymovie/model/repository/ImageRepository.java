package org.example.todaymovie.model.repository;

import org.example.todaymovie.model.dto.ImageResponse;
import org.example.todaymovie.model.dto.MovieDTO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

@Repository
public class ImageRepository implements APIClientRepository {
    final String clientId = dotenv.get("NAVER_CLIENT_ID");
    final String clientSecret = dotenv.get("NAVER_CLIENT_SECRET");
    final Logger logger = Logger.getLogger(ImageRepository.class.getSimpleName());

    public String getImage(MovieDTO movie) throws IOException, InterruptedException {
        String baseURL = "https://openapi.naver.com/v1/search/image";
        String query = URLEncoder.encode("%s 포스터".formatted(movie.name()),
                StandardCharsets.UTF_8);
        String url = "%s?query=%s&display=1".formatted(baseURL, query);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        logger.info(response.body());
        if (response.statusCode() >= 400) {
            throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
        }
        return URLDecoder.decode(objectMapper.readValue(response.body(), ImageResponse.class).items().get(0).link(), StandardCharsets.UTF_8);
    }
}