package com.chatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.chatbot.service.BotService;

@Controller
public class BotController {

	@Autowired
    private BotService botService;
	
    @GetMapping
    public String home() {
        return "index";
    }
    
    
    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody String question) {
        try {
            return ResponseEntity.ok(botService.askQuestion(question));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Sorry, I can't process your question right now, please upload the related topic document");
        }
    }
}
