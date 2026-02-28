package com.juanlopezaranzazu.springboot_redis.service;

import com.juanlopezaranzazu.springboot_redis.dto.*;
import com.juanlopezaranzazu.springboot_redis.entity.User;
import com.juanlopezaranzazu.springboot_redis.repository.UserRepository;
import com.juanlopezaranzazu.springboot_redis.exception.EmailAlreadyExistsException;
import com.juanlopezaranzazu.springboot_redis.exception.UserNotFoundException;
import com.juanlopezaranzazu.springboot_redis.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @CacheEvict(value = "usersList", allEntries = true)
    public UserResponse create(CreateUserRequest request) {
        log.debug("Creando usuario con email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);

        log.info("Usuario creado con id: {}", saved.getId());
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "users", key = "#id")
    public UserResponse findById(Long id) {
        log.debug("Buscando usuario id: {} (posiblemente desde cache)", id);

        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
        value = "usersList",
        key = "#page + '-' + #size + '-' + #name + '-' + #status"
    )
    public PagedResponse<UserResponse> findAll(int page, int size, String name, User.UserStatus status) {
        log.debug("Listando usuarios - página: {}, tamaño: {}", page, size);

        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> userPage = userRepository.findAllWithFilters(name, status, pageable);

        return PagedResponse.<UserResponse>builder()
                .content(userPage.getContent().stream().map(userMapper::toResponse).toList())
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();
    }

    @Override
    @CachePut(value = "users", key = "#id")
    @CacheEvict(value = "usersList", allEntries = true)
    public UserResponse update(Long id, UpdateUserRequest request) {
        log.debug("Actualizando usuario id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException(request.getEmail());
            }
        }

        userMapper.updateEntity(request, user);
        User updated = userRepository.save(user);

        log.info("Usuario id: {} actualizado correctamente", id);
        return userMapper.toResponse(updated);
    }

    @Override
    @CacheEvict(value = {"users", "usersList"}, key = "#id", allEntries = true)
    public void delete(Long id) {
        log.debug("Eliminando usuario id: {}", id);

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
        log.info("Usuario id: {} eliminado correctamente", id);
    }
}
