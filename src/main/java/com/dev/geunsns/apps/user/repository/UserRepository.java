package com.dev.geunsns.apps.user.repository;

import com.dev.geunsns.apps.user.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Business Logic (Duplicate check)
    Optional<User> findByUserName(String userName);
}
