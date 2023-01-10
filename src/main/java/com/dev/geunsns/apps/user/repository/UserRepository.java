package com.dev.geunsns.apps.user.repository;

import com.dev.geunsns.apps.user.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Business Logic (Duplicate check)
    Optional<UserEntity> findByUserName(String userName);
}
