package com.juanlopezaranzazu.springboot_redis.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juanlopezaranzazu.springboot_redis.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE " +
       "(:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%'))) AND " +
       "(:status IS NULL OR u.status = :status)")
    Page<User> findAllWithFilters(
        @Param("name") String name,
        @Param("status") User.UserStatus status,
        Pageable pageable
    );
}

