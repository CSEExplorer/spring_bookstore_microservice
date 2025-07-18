package com.bookstore.account_service.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.account_service.entity.User;
import com.bookstore.account_service.entity.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);
}
