package org.example.todaymovie.model.repository;

import org.example.todaymovie.model.dto.MovieCacheDTO;
import org.example.todaymovie.model.dto.MovieInfoDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CacheRepository {
    // 로컬 인메모리 캐시로 변경
    private final Map<String, MovieCacheDTO> cache = new ConcurrentHashMap<>();

    public void save(MovieCacheDTO movieCacheDTO) {
        cache.put(movieCacheDTO.boxOfficeDate(), movieCacheDTO);
        System.out.println("캐시 저장 완료: " + movieCacheDTO.boxOfficeDate());
    }

    public MovieCacheDTO getCache(String date) throws Exception {
        MovieCacheDTO cacheData = cache.get(date);
        
        if (cacheData == null) {
            throw new RuntimeException("캐싱 없음");
        }
        
        if (cacheData.data().isEmpty()) {
            throw new RuntimeException("캐싱 없음");
        }
        
        return cacheData;
    }
}
