package com.example.task4.controller;

import com.example.task4.DTO.request.CreateUserDTO;
import com.example.task4.DTO.request.LoginRequestDto;
import com.example.task4.DTO.request.UpdateUserDTo;

import com.example.task4.model.User;
import com.example.task4.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("createUserDto", new CreateUserDTO());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute CreateUserDTO user, Model model) {
        userService.createUser(user);
        model.addAttribute("user", user);
        return "dashboard";  // Redirect to a dashboard or home page after registration
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new User());
        return "login";  // login.html (Thymeleaf template)
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginRequestDto loginDto, Model model, HttpSession session) {
        Optional<User> loggedInUser = userService.login(loginDto.getUsername(), loginDto.getPassword());
        if (loggedInUser.isPresent()) {
            // Store the logged-in user in the session
            session.setAttribute("user", loggedInUser.get());
            return "redirect:/dashboard";  // Redirect to dashboard after login
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";  // Show the login page again
        }
    }
    @GetMapping("/profile")
    public String viewProfile(@RequestParam String username, Model model) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            // Handle error, user not found
            return "error"; // or some error page
        }
        // Populate DTO with existing user details
        UpdateUserDTo updateUserDto = new UpdateUserDTo(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );

        model.addAttribute("user", user);
        model.addAttribute("updateUserDto", updateUserDto);
        return "profile";  // profile.html (Thymeleaf template)
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam String username, Model model) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            // Handle error, user not found
            return "error"; // or some error page
        }

        UpdateUserDTo updateUserDto = new UpdateUserDTo(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );

        model.addAttribute("updateUserDto", updateUserDto);
        return "updateProfile";  // updateProfile.html (Thymeleaf template)
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute UpdateUserDTo updateUserDto, Model model) {
        try {
            userService.updateUser(updateUserDto);
            return "redirect:/profile?username=" + updateUserDto.getUsername();
        } catch (Exception e) {
            model.addAttribute("error", "Error updating user: " + e.getMessage());
            return "profile";
        }
    }
    }

