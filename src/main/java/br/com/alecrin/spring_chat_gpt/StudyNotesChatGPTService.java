package br.com.alecrin.spring_chat_gpt;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import reactor.core.publisher.Mono;

@Service
public class StudyNotesChatGPTService {

    private WebClient webClient;

    public StudyNotesChatGPTService(WebClient.Builder builder, @Value("${openai.api.key}") String apiKey) {
        this.webClient = builder
        .baseUrl("https://api.openai.com/v1/chat/completion")
        .defaultHeader("ContentType","application/json")
        .defaultHeader("Authorization", "Bearer %s".formatted(apiKey))
        .build();
    }

    public Mono<ChatGPTResponse> createStudyNotes(String topic) {
        ChatGPTRequest request = createStudyRequest(topic);

        return webClient.post().bodyValue(request).retrieve().bodyToMono(ChatGPTResponse.class);
    }
        
    private ChatGPTRequest createStudyRequest(String topic) {
        String question = "Quais são os pontos chaves que devo estudar sobre o seguinte assunto: " + topic;
        

        return new ChatGPTRequest("text_davinci-003", question, 0.3, 2000, 1.0, 0.0, 0.0);
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record ChatGPTRequest(String model,
    String prompt,
    Double temperature,
    Integer maxTokens,
    Double topP,
    Double frequencyPenalty,
    Double presencePenalty) {

}

record ChatGPTResponse(List<Choice> choices) {

}

record Choice(String text) {}
