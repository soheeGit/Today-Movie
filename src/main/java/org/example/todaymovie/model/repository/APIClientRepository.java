package org.example.todaymovie.model.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.http.HttpClient;

public interface APIClientRepository {
    HttpClient httpClient = HttpClient.newHttpClient();
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    ObjectMapper objectMapper = new ObjectMapper();
}