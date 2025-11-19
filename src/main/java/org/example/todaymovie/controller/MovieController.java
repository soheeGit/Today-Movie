package org.example.todaymovie.controller;

import jakarta.servlet.http.HttpSession;
import org.example.todaymovie.model.dto.MovieInfoDTO;
import org.example.todaymovie.service.GeminiService;
import org.example.todaymovie.service.MovieService;
import org.example.todaymovie.service.MovieServiceImpl;
import org.example.todaymovie.util.MyLogger;
import org.example.todaymovie.util.NowDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MovieController {
    MyLogger logger = new MyLogger(MovieController.class.getName());
    final MovieService movieService;
    final GeminiService geminiService;
    
    // Gemini API 동시 호출 방지
    private volatile String cachedRecommendation = null;
    private volatile String cachedDate = null;
    private final Object geminiLock = new Object();

    public MovieController(MovieServiceImpl movieService, GeminiService geminiService) {
        this.movieService = movieService;
        this.geminiService = geminiService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) throws Exception {
        String nowDateStr = NowDate.str();
        List<MovieInfoDTO> movies = movieService.getMovieInfos();

        // 날짜가 바뀌었거나 추천이 없을 때만 Gemini 호출
        if (cachedRecommendation == null || !nowDateStr.equals(cachedDate)) {
            synchronized (geminiLock) {
                // 다시 한 번 확인 (다른 스레드가 이미 생성했을 수 있음)
                if (cachedRecommendation == null || !nowDateStr.equals(cachedDate)) {
                    try {
                        String prompt = "%s, 앞의 데이터를 바탕으로 영화를 추천하고 그 중에 서로 다른 영화 3개만 최종적으로 작성. 생각의 과정을 노출하지 않고 결과만. no markdown, just plain-text and emoji, in korean language. 마크다운 문법이 있는지 마지막으로 체크하여 있다면 제거. 마크다운이 있는지 마지막으로 한 번 더 점검하여 제거!".formatted(movies.toString());
                        cachedRecommendation = geminiService.callGemini(prompt);
                        cachedDate = nowDateStr;
                        logger.info("Gemini API 호출 성공");
                    } catch (Exception e) {
                        logger.error("Gemini API 호출 실패: " + e.getMessage());
                        // API 실패 시 기본 메시지 제공
                        cachedRecommendation = "🎬 오늘의 박스오피스 TOP 3\n\n" +
                                "현재 AI 추천 서비스가 일시적으로 제한되어 있습니다.\n" +
                                "아래 박스오피스 순위를 참고해주세요! 😊";
                        cachedDate = nowDateStr;
                    }
                }
            }
        }
        
        model.addAttribute("movies", movies);
        model.addAttribute("recommendation", cachedRecommendation);
        return "index";
    }
}
