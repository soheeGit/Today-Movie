package org.example.todaymovie.service;

import org.example.todaymovie.model.dto.MovieDTO;
import org.example.todaymovie.model.dto.MovieInfoDTO;
import org.example.todaymovie.model.repository.MovieRepository;
import org.example.todaymovie.model.repository.MovieRepositoryImpl;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    public List<MovieDTO> getMovies(String date) throws IOException, InterruptedException;
    public List<MovieInfoDTO> getMovieInfos() throws IOException, InterruptedException;
}
