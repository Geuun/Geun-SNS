package com.dev.geunsns.apps.post.repository;

import com.dev.geunsns.apps.post.data.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyFeedRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByUserName(String userName, Pageable pageable);
}
