package com.juanlopezaranzazu.springboot_redis.dto;


import com.juanlopezaranzazu.springboot_redis.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Email(message = "Formato de email inválido")
    private String email;

    private String phone;

    private User.UserStatus status;
}
