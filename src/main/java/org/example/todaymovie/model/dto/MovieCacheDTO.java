package org.example.todaymovie.model.dto;

import java.util.List;

public record MovieCacheDTO(String boxOfficeDate, List<MovieInfoDTO> data) {
}