package com.example.task4.service;

import com.example.task4.DTO.request.CreateUserDTO;
import com.example.task4.DTO.request.UpdateUserDTo;
import com.example.task4.model.User;
import com.example.task4.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.JstlUtils;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final List<User> users = new ArrayList<>();

    public User createUser(CreateUserDTO newUser){
        User user = new User();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        for (User user : users)
            if (user.getId().equals(id))
                return user;
        return null;
    }
    public Optional<User> login(String username, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password.equals(user.getPassword())) {  // Note: This is not secure, see below
                return Optional.of(user);
            }
        }
        throw new RuntimeException("Invalid username or password");
    }
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        if (optionalUser.isPresent()
 && verifyPassword(password, optionalUser.get().getPassword())) {
        return optionalUser;
    } else {
        return Optional.empty();
    }
    }

    private boolean verifyPassword(String plainPassword, String encodedPassword) {
        // Implement your own password verification logic here
        // For example, you could use a custom password hashing algorithm
        return plainPassword.equals(encodedPassword); // Replace with your actual verification logic
    }

    public void updateUser(UpdateUserDTo updateUserDto) {
        // Find user by username
        User user = userRepository.findByUsername(updateUserDto.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Update user details
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setEmail(updateUserDto.getEmail());

        // Save updated user
        userRepository.save(user);
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);  // Fetch the user by username
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getLoggedInUser() {
        Long loggedInUserId = 1L; // Example: assuming user ID is 1 for now

        // Fetch user by ID
        Optional<User> optionalUser = userRepository.findById(loggedInUserId);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            // Handle user not found (you can throw an exception or return null based on your preference)
            throw new RuntimeException("User not found");
        }
    }
    public User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }
}
