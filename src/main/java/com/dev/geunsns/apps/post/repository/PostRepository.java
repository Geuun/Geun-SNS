package com.dev.geunsns.apps.post.repository;

import com.dev.geunsns.apps.post.data.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

}
