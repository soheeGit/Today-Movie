package org.example.todaymovie.controller;

import jakarta.servlet.http.HttpSession;
import org.example.todaymovie.model.dto.MovieInfoDTO;
import org.example.todaymovie.service.GeminiService;
import org.example.todaymovie.service.MovieService;
import org.example.todaymovie.service.MovieServiceImpl;
import org.example.todaymovie.util.NowDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MovieController {
    final MovieService movieService;
    final GeminiService geminiService;

    public MovieController(MovieServiceImpl movieService, GeminiService geminiService) {
        this.movieService = movieService;
        this.geminiService = geminiService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) throws Exception {
//        List<MovieDTO> movies = movieService.getMovies();
        String nowDateStr = NowDate.str();
        List<MovieInfoDTO> movies = movieService.getMovieInfos();

        if (session.getAttribute("lastUpdateDate") == null || !session.getAttribute("lastUpdateDate").equals(nowDateStr)) {
            String prompt = "%s, 앞의 데이터를 바탕으로 영화를 추천하고 그 중에 서로 다른 영화 3개만 최종적으로 작성. 생각의 과정을 노출하지 않고 결과만. no markdown, just plain-text and emoji, in korean language. 마크다운 문법이 있는지 마지막으로 체크하여 있다면 제거. 마크다운이 있는지 마지막으로 한 번 더 점검하여 제거!".formatted(movies.toString());
            String recommendation = geminiService.callGemini(prompt);
            session.setAttribute("lastUpdateDate", nowDateStr);
            session.setAttribute("recommendation", recommendation);
        }
        model.addAttribute("movies", movies);
        model.addAttribute("recommendation", session.getAttribute("recommendation"));
        return "index";
    }
}