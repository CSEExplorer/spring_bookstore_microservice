package com.bookstore.account_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.account_service.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}

