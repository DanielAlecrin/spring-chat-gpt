package br.com.alecrin.spring_chat_gpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class StudyNotesController {

    @Autowired
    private StudyNotesChatGPTService service;

    public Mono<String> createStudyNotes(@RequestBody String topic) {
        return service.createStudyNotes(topic)
        .map(response -> response.choices().get(0).text());
    }
    
}
