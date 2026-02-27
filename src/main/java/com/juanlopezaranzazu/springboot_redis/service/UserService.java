package com.juanlopezaranzazu.springboot_redis.service;

import com.juanlopezaranzazu.springboot_redis.dto.*;
import com.juanlopezaranzazu.springboot_redis.entity.User;

public interface UserService {

    UserResponse create(CreateUserRequest request);

    UserResponse findById(Long id);

    PagedResponse<UserResponse> findAll(int page, int size, String name, User.UserStatus status);

    UserResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);
}
