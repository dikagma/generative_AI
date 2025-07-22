package com.bassagou.spring_openai_demo.controllers;

import com.bassagou.spring_openai_demo.outputs.CinModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MultiModalController {
    private ChatClient chatClient;
    @Value("classpath:/images/ci.jpeg")
    private Resource image;

    @Value("classpath:/images/im.jpeg")
    private Resource image2;

    public MultiModalController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }
    @GetMapping("/describe")
    public CinModel describeImage(){
     return chatClient.prompt()
             .system("Donne une description de l'image manuscrite fournie")
             .user(
                     u-> u.text("")
                             .media(MediaType.IMAGE_JPEG, image)
             ).call().entity(CinModel.class);
    }


    @GetMapping("/askImage")
    public String askImage(@RequestParam String question){
        return chatClient.prompt()
                .system("Repond a la question de l'utilisateur a propos de l'image")
                .user(
                        u-> u.text(question)
                                .media(MediaType.IMAGE_JPEG, image2)
                ).call().content();
    }


    @PostMapping(value = "/askUploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String askImage(@RequestParam String question, @RequestParam(name="file") MultipartFile file)
            throws IOException {
        byte[] bytes= file.getBytes();

        return chatClient.prompt()
                .system("Repond a la question de l'utilisateur a propos de l'image")
                .user(
                        u-> u.text(question)
                                .media(MediaType.IMAGE_JPEG, new ByteArrayResource(bytes))
                ).call().content();
    }
}
