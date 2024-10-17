package com.example.task4.DTO.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserDTO {

    String firstName;
    String lastName;
    String username;
    String email;
    String password;



}
