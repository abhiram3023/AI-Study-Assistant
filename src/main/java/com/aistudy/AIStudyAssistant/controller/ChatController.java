package com.aistudy.AIStudyAssistant.controller;

import com.aistudy.AIStudyAssistant.model.User;
import com.aistudy.AIStudyAssistant.service.GroqService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    @Autowired
    private GroqService groqService;

    @GetMapping("/chat")
    public String chatPage(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        return "chat";
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String question,
                       HttpSession session,
                       Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        String response = groqService.getResponse(question);

        model.addAttribute("question", question);
        model.addAttribute("response", response);

        return "chat";
    }
}