package com.aistudy.AIStudyAssistant.controller;

import com.aistudy.AIStudyAssistant.model.User;
import com.aistudy.AIStudyAssistant.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model) {
        user.setName(user.getName() == null ? null : user.getName().trim());
        user.setEmail(user.getEmail() == null ? null : user.getEmail().trim().toLowerCase());

        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "This email is already registered.");
            return "signup";
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            model.addAttribute("error", "Could not create account. Please try again.");
            return "signup";
        }

        return "redirect:/login?signupSuccess";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userRepository.findByEmail(email.trim().toLowerCase());

        if (user != null && user.getPassword() != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Invalid email or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}