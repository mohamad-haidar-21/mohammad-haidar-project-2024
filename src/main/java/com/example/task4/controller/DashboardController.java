package com.example.task4.controller;

import com.example.task4.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login"; // Redirect to login if user is not found
        }
        model.addAttribute("user", user);
        return "dashboard"; // Return the dashboard view
    }
}
