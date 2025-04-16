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
        try {
            MovieCacheDTO cache = cacheRepository.getCache(nowDateStr);
            logger.info("캐싱 불러오기 성공");
            return cache.data();
        } catch (Exception e) {
            logger.error("캐싱 불러오기 실패");
            List<MovieInfoDTO> data = getMovies(nowDateStr).stream().map((v) -> {
                try {
                    String imageUrl = imageRepository.getImage(v);
                    return movieRepository.getMovieInfo(v, imageUrl);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }).toList();
            cacheRepository.save(new MovieCacheDTO(nowDateStr, data));
            return data;
        }
    }
}
