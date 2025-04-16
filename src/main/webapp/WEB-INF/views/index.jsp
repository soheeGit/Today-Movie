<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.todaymovie.model.dto.MovieInfoDTO" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="영화진흥위원회 API 기반 일일 박스오피스 순위와 AI 영화 추천 서비스">
    <meta name="keywords" content="영화, 추천, 박스오피스, 영화 순위, 한국 영화, AI 추천">
    <meta name="author" content="영화 추천 서비스">
    <meta name="robots" content="index, follow">

    <!-- Open Graph / 소셜 미디어 -->
    <%
        List<MovieInfoDTO> movies = (List<MovieInfoDTO>) request.getAttribute("movies");
        String ogImageUrl = "";
        if (movies != null && !movies.isEmpty() && movies.get(0).imgUrl() != null && !movies.get(0).imgUrl().isEmpty()) {
            ogImageUrl = movies.get(0).imgUrl();
        }
    %>
    <meta property="og:title" content="오늘의 영화 추천 | 박스오피스 순위">
    <meta property="og:description" content="한국 박스오피스 기준 일일 영화 순위와 AI 추천 서비스">
    <meta property="og:type" content="website">
    <meta property="og:locale" content="ko_KR">
    <% if (!ogImageUrl.isEmpty()) { %>
    <meta property="og:image" content="<%= ogImageUrl %>">
    <% } else { %>
    <meta property="og:image" content="<%= request.getContextPath() %>/asset/movie.jpeg">
    <% } %>

    <!-- favicon -->
    <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/asset/favicon-96x96.png" sizes="96x96" />
    <link rel="icon" type="image/svg+xml" href="<%= request.getContextPath() %>/asset/favicon.svg" />
    <link rel="shortcut icon" href="<%= request.getContextPath() %>/asset/favicon.ico" />
    <link rel="apple-touch-icon" sizes="180x180" href="<%= request.getContextPath() %>/asset/apple-touch-icon.png" />
    <link rel="manifest" href="<%= request.getContextPath() %>/asset/site.webmanifest" />

    <!-- 기타 메타 태그 -->
    <meta name="theme-color" content="#6a11cb">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <title>오늘의 영화 추천 서비스</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <link href="<%= request.getContextPath() %>/asset/style.css" rel="stylesheet">
</head>
<body>
<!-- Header -->
<header class="header text-center animate__animated animate__fadeIn">
    <div class="container">
        <h1 class="animate__animated animate__fadeInDown animate__delay-1s">오늘의 영화 추천</h1>
        <p class="animate__animated animate__fadeInUp animate__delay-1s">한국 박스오피스 기준 일일 영화 순위와 AI 추천</p>
    </div>
</header>

<div class="container">
    <!-- 영화 목록 섹션 -->
    <h2 class="section-title mb-4 animate__animated animate__fadeIn animate__delay-1s">박스오피스 순위</h2>
    <div class="row">
        <%
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.KOREA);

            if (movies != null) {
                int delay = 2;
                for (MovieInfoDTO movie : movies) {
        %>
        <div class="col-md-6 col-lg-4 mb-4 animate__animated animate__fadeIn" style="animation-delay: <%= delay * 0.1 %>s;">
            <div class="card movie-card">
                <!-- 포스터 이미지 with JavaScript fallback -->
                <div class="movie-poster-container">
                    <img
                            src="<%= movie.imgUrl() != null && !movie.imgUrl().isEmpty() ? movie.imgUrl() : request.getContextPath() + "/asset/default.jpeg" %>"
                            alt="<%= movie.movie().name() %> 포스터"
                            class="movie-poster"
                            onerror="this.src='<%= request.getContextPath() %>/asset/default.jpeg';">
                    <div class="movie-rank-overlay"><%= movie.movie().rank() %></div>
                    <div class="movie-info-overlay">
                        <h5 class="movie-overlay-title"><%= movie.movie().name() %></h5>
                        <p>
                            <% for (String genre : movie.genres()) { %>
                            <span class="badge bg-secondary badge-genre"><%= genre %></span>
                            <% } %>
                        </p>
                        <div class="movie-overlay-audience">
                            <span class="movie-audience-count"><%= numberFormat.format(movie.movie().audience()) %>명</span>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <h5 class="movie-title">
                        <%= movie.movie().name() %>
                    </h5>

                    <!-- 개봉일 및 상영시간 -->
                    <p class="movie-info">
                        <strong>개봉일:</strong> <%= movie.movie().openDate() %><br>
                        <strong>상영시간:</strong> <%= movie.time() %>분
                    </p>

                    <!-- 국가 -->
                    <p class="movie-info">
                        <strong>국가:</strong>
                        <% for (String nation : movie.nations()) { %>
                        <span class="badge bg-success badge-nation"><%= nation %></span>
                        <% } %>
                    </p>

                    <!-- 장르 -->
                    <p class="movie-info">
                        <strong>장르:</strong>
                        <% for (String genre : movie.genres()) { %>
                        <span class="badge bg-secondary badge-genre"><%= genre %></span>
                        <% } %>
                    </p>

                    <!-- 감독 -->
                    <p class="movie-info">
                        <strong>감독:</strong>
                        <% for (String director : movie.directors()) { %>
                        <span class="badge bg-info badge-director"><%= director %></span>
                        <% } %>
                    </p>

                    <!-- 배우 (최대 3명만 표시) -->
                    <p class="movie-info">
                        <strong>출연:</strong><br>
                        <%
                            List<String> actors = movie.actors();
                            int actorCount = Math.min(actors.size(), 3);
                            for (int i = 0; i < actorCount; i++) {
                        %>
                        <span class="badge bg-warning text-dark badge-actor"><%= actors.get(i) %></span>
                        <% }
                            if (actors.size() > 3) {
                        %>
                        <span class="badge bg-light text-dark">외 <%= actors.size() - 3 %>명</span>
                        <% } %>
                    </p>

                    <!-- 누적 관객 수 -->
                    <p class="movie-audience mt-3">
                        <strong>누적 관객수:</strong> <span class="audience-number"><%= numberFormat.format(movie.movie().audience()) %>명</span>
                    </p>
                </div>
            </div>
        </div>
        <%
                    delay++;
                }
            }
        %>
    </div>

    <!-- 영화 추천 섹션 -->
    <div class="recommendation-section animate__animated animate__fadeIn animate__delay-1s">
        <h2 class="recommendation-title">AI의 영화 추천</h2>
        <div class="recommendation-content">
            <%= request.getAttribute("recommendation") %>
        </div>
    </div>

</div>

<!-- Footer -->
<footer class="text-center animate__animated animate__fadeIn">
    <div class="container">
        <p>© <%= java.time.Year.now().getValue() %> 영화 추천 서비스 | 영화진흥위원회 오픈API 활용</p>
    </div>
</footer>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Add scroll reveal effect to movie cards
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('show');
                    observer.unobserve(entry.target);
                }
            });
        }, {
            threshold: 0.1
        });

        document.querySelectorAll('.movie-card').forEach(card => {
            observer.observe(card);
        });

        // Counter animation for audience numbers
        document.querySelectorAll('.audience-number').forEach(element => {
            const finalValue = parseInt(element.textContent.replace(/[^0-9]/g, ''));

            if (finalValue > 10000) {
                // Only animate for large numbers
                let startValue = 0;
                const duration = 2000;
                const step = Math.ceil(finalValue / (duration / 20));

                const counter = setInterval(() => {
                    startValue += step;
                    if (startValue > finalValue) {
                        element.textContent = new Intl.NumberFormat('ko-KR').format(finalValue) + '명';
                        clearInterval(counter);
                    } else {
                        element.textContent = new Intl.NumberFormat('ko-KR').format(startValue) + '명';
                    }
                }, 20);
            }
        });
    });
</script>
</body>
</html>