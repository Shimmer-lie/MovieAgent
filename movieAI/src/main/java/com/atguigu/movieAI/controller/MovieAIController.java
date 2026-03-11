package com.atguigu.movieAI.controller;

import com.atguigu.movieAI.assistant.MovieAgent;
import com.atguigu.movieAI.bean.ChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@Tag(name = "八千代电影")
@RestController
@RequestMapping("/movieai")
public class MovieAIController {

    @Autowired
    private MovieAgent movieAgent;

    @Operation(summary = "对话")
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm) {
        return movieAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }
}
