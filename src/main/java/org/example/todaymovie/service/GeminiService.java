package org.example.todaymovie.service;

import com.google.genai.Client;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
    final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    final Client client = Client.builder().apiKey(dotenv.get("GEMINI_KEY")).build();
    public String callGemini(String prompt) throws Exception {
        String model = "gemini-2.0-flash";
        return client.models.generateContent(model, prompt, null).text();
    }
}