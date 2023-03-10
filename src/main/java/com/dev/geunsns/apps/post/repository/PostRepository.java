package com.dev.geunsns.apps.post.repository;

import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAllByUser(UserEntity userEntity, Pageable pageable);
}
