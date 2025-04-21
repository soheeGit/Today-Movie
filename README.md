# Today-Movie
**오늘의 영화 추천 서비스**

이 프로젝트는 영화진흥위원회 API를 기반으로 한국 박스오피스 순위와 AI 영화 추천 서비스를 제공합니다. 사용자에게 일일 영화 순위를 제공하고, AI 모델을 활용하여 추천 영화를 제시합니다.

## 📚 기술 스택

- **Java**  
  프로젝트의 주요 언어로 사용되었습니다. Java 17 버전을 기준으로 개발되었습니다.

- **Spring Framework**  
  백엔드 개발에 사용되었습니다. Spring을 활용하여 API 호출 및 데이터를 처리합니다.

- **JSP (JavaServer Pages)**  
  프론트엔드 템플릿 엔진으로 사용되었으며, HTML 페이지를 동적으로 렌더링합니다.

- **Bootstrap 5**  
  웹 페이지의 스타일링을 위해 사용된 CSS 프레임워크입니다. 반응형 웹 디자인을 제공하며, 페이지의 사용자 인터페이스(UI)를 구성하는 데 도움을 주었습니다.

## 🚀 기능

- **박스오피스 순위**  
  영화진흥위원회 API를 활용하여 매일 업데이트되는 한국 박스오피스 순위를 제공합니다.

- **AI 영화 추천**  
  영화 데이터를 기반으로 AI가 추천하는 영화를 사용자에게 제공합니다.

- **디자인 및 인터페이스**  
  부트스트랩을 활용한 반응형 웹 디자인을 통해 다양한 화면 크기에서 잘 작동하는 UI를 제공합니다.

## 🛠 설치 및 실행 방법

1. **프로젝트 클론**  
   이 저장소를 클론합니다.
   ```bash
   git clone https://github.com/soheeGit/Today-Movie.git
   cd today-movie
   ```

2. **필요한 라이브러리 설치**  
   Spring 환경에서 필요한 라이브러리를 설치합니다.

3. **애플리케이션 실행**  
   Spring Boot 애플리케이션을 실행합니다.

4. **웹 브라우저에서 확인**  
   로컬 서버에서 실행 후, 브라우저에서 `http://localhost:8080`을 열어 결과를 확인합니다.

## 🔑 환경 변수 설정
### `src/main/resources/.env` 파일에 다음과 같은 값을 설정해 주세요.
```
# https://www.kobis.or.kr/kobisopenapi/homepg/apikey/ckUser/findApikeyList.do
MOVIE_KEY=???
# https://aistudio.google.com/apikey
GEMINI_KEY=???
# https://supabase.com/dashboard/project/???/settings/api
SUPABASE_URL=https://???.supabase.co
SUPABASE_KEY=???
# https://developers.naver.com/apps/#/list
NAVER_CLIENT_ID=???
NAVER_CLIENT_CLEINT=???
```

## 실행 예시
<img width="371" alt="스크린샷 2025-04-21 오후 3 49 00" src="https://github.com/user-attachments/assets/d45490b6-48d1-41cc-8959-72dc72405e04" />
<img width="346" alt="스크린샷 2025-04-21 오후 3 49 10" src="https://github.com/user-attachments/assets/85bb9a25-ada5-4b98-826f-4690a96e531f" />



