package com.bassagou.spring_openai_demo.controllers;

import com.bassagou.spring_openai_demo.outputs.Movie;
import com.bassagou.spring_openai_demo.outputs.MovieList;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIAgentStructuredAgentController {
    private ChatClient chatClient;

    public AIAgentStructuredAgentController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }
@GetMapping("/askAgent")
    public MovieList askLLM(@RequestParam String query){
        String systMessage= """
                Vous ete specialiste dans le domaine du cinema
                Repond a la question de l'utilisateur a ce propos
                """;
      return  chatClient.prompt()
              .system(systMessage)
              .user(query)
              .call()
              .entity(MovieList.class);
    }
}
