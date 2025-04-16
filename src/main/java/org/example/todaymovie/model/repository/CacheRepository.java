package org.example.todaymovie.model.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.todaymovie.model.dto.MovieCacheDTO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Repository
public class CacheRepository implements APIClientRepository {
    final String baseURL = dotenv.get("SUPABASE_URL");
    final String key = dotenv.get("SUPABASE_KEY");

    public void save(MovieCacheDTO movieCacheDTO) throws IOException, InterruptedException {
        String action = "rest/v1/BOX_OFFICE";
        String url = "%s/%s".formatted(baseURL, action);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(movieCacheDTO)))
                .header("apikey", key)
                .header("Authorization", "Bearer %s".formatted(key))
                .header("Content-Type", "application/json")
                .header("Prefer", "return=minimal")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        if (response.statusCode() >= 400) {
            throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
        }
    }

    public MovieCacheDTO getCache(String date) throws Exception {
        String action = "rest/v1/BOX_OFFICE";
        String url = "%s/%s?select=*&boxOfficeDate=eq.%s".formatted(baseURL, action, date);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", key)
                .header("Authorization", "Bearer %s".formatted(key))
                .header("Range", "0")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        if (response.statusCode() == 200) {
            List<MovieCacheDTO> cacheList = objectMapper.readValue(
                    response.body(),
                    new TypeReference<>() {}
            );
            MovieCacheDTO cache = cacheList.get(0);

            if (cache.data().isEmpty()) {
                throw new RuntimeException("캐싱 없음");
            }
            return cache;
        }
        throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
    }
}