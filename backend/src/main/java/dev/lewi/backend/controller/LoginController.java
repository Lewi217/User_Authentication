package dev.lewi.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the view name for the login page
    }

    // Add this method to handle logout
    @GetMapping("/logout")
    public String logout() {
        // Add any logout logic here if needed
        return "redirect:/login?logout=true"; // Redirect to login page after logout
    }
}
