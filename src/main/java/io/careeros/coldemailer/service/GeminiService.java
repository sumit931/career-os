package io.careeros.coldemailer.service;

import io.careeros.coldemailer.config.GeminiProperties;
import io.careeros.coldemailer.dto.request.GeminiRequest;
import io.careeros.coldemailer.dto.request.GeminiRequest.Content;
import io.careeros.coldemailer.dto.request.GeminiRequest.GenerationConfig;
import io.careeros.coldemailer.dto.request.GeminiRequest.Part;
import io.careeros.coldemailer.dto.response.GeminiResponse;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class GeminiService {

  private static final String GEMINI_BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/";
  private static final String JSON_MIME_TYPE = "application/json";

  private final GeminiProperties properties;
  private final RestClient googleRestClient;
  private final ObjectMapper objectMapper;

  @SneakyThrows
  public List<String> generateFollowups(String subject, String initialBody, int count) {
    GeminiRequest request = buildRequest(subject, initialBody, count);

    String url = GEMINI_BASE_URL + properties.model() + ":generateContent?key=" + properties.apiKey();

    GeminiResponse response = googleRestClient.post()
        .uri(url)
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .body(GeminiResponse.class);

    String json = response.extractText();
    return Arrays.asList(objectMapper.readValue(json, String[].class));
  }

  private GeminiRequest buildRequest(String subject, String initialBody, int count) {
    String prompt = """
        You are a senior professional writing follow-up emails after a cold outreach. Your goal is to write
        follow-ups that feel genuinely human — not templated, not robotic — and convey authentic urgency
        without being pushy or desperate.

        Initial email subject: %s
        Initial email body:
        %s

        Generate exactly %d follow-up email bodies with these rules:
        - Write in first person, as if a real person is following up naturally
        - Each email must be short (3-5 sentences max) — busy people don't read long emails
        - Show honest, genuine interest in connecting — not urgency or desperation. The tone should feel
          like someone who genuinely cares about the opportunity and is reaching out because they mean it,
          not because they are chasing a response
        - Each follow-up should approach from a slightly different angle (value reminder, specific question,
          offer to help, acknowledge they're busy)
        - Never use filler phrases like "I hope this email finds you well", "Just checking in",
          "Touching base", "Circle back", or "Per my last email"
        - No subject line — body only
        - Sound like a human, not an AI

        Return ONLY a JSON array of exactly %d strings, each string being the plain-text body of one follow-up email.
        """.formatted(subject, initialBody, count, count);

    return new GeminiRequest(
        List.of(new Content(List.of(new Part(prompt)))),
        new GenerationConfig(JSON_MIME_TYPE)
    );
  }
}
