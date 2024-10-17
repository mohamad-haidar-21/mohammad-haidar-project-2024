package com.example.task4.DTO.request;

import com.example.task4.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTo {

        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String password;

    public UpdateUserDTo(User user) {
    }
}
