package org.example.todaymovie.service;

import org.example.todaymovie.model.dto.MovieCacheDTO;
import org.example.todaymovie.model.dto.MovieDTO;
import org.example.todaymovie.model.dto.MovieInfoDTO;
import org.example.todaymovie.model.dto.MovieParam;
import org.example.todaymovie.model.repository.CacheRepository;
import org.example.todaymovie.model.repository.ImageRepository;
import org.example.todaymovie.model.repository.MovieRepository;
import org.example.todaymovie.model.repository.MovieRepositoryImpl;
import org.example.todaymovie.util.MyLogger;
import org.example.todaymovie.util.NowDate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    MyLogger logger = new MyLogger(MovieServiceImpl.class.getName());
    final MovieRepository movieRepository;
    final CacheRepository cacheRepository;
    final ImageRepository imageRepository;
    
    // 동시 요청 방지를 위한 락
    private final Object cacheLock = new Object();

    public MovieServiceImpl(MovieRepositoryImpl movieRepository, CacheRepository cacheRepository, ImageRepository imageRepository) {
        this.movieRepository = movieRepository;
        this.cacheRepository = cacheRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<MovieDTO> getMovies(String date) throws IOException, InterruptedException {
        MovieParam param = new MovieParam(date);
        return movieRepository.getMovies(param);
    }

    @Override
    public List<MovieInfoDTO> getMovieInfos() throws IOException, InterruptedException {
        String nowDateStr = NowDate.str();
        
        // 먼저 캐시 확인 (락 없이)
        try {
            MovieCacheDTO cache = cacheRepository.getCache(nowDateStr);
            logger.info("캐싱 불러오기 성공");
            return cache.data();
        } catch (Exception e) {
            // 캐시가 없을 때만 동기화 블록 진입
            synchronized (cacheLock) {
                // 다시 한 번 캐시 확인 (다른 스레드가 이미 생성했을 수 있음)
                try {
                    MovieCacheDTO cache = cacheRepository.getCache(nowDateStr);
                    logger.info("캐싱 불러오기 성공 (대기 후)");
                    return cache.data();
                } catch (Exception ignored) {
                    // 캐시가 여전히 없으면 API 호출
                    logger.info("API 호출 시작");
                    List<MovieInfoDTO> data = getMovies(nowDateStr).stream().map((v) -> {
                        try {
                            String imageUrl = imageRepository.getImage(v);
                            return movieRepository.getMovieInfo(v, imageUrl);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }).toList();
                    cacheRepository.save(new MovieCacheDTO(nowDateStr, data));
                    logger.info("캐시 저장 완료");
                    return data;
                }
            }
        }
    }
}
