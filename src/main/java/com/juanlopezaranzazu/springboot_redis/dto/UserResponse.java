package com.juanlopezaranzazu.springboot_redis.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.juanlopezaranzazu.springboot_redis.entity.User;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private User.UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
