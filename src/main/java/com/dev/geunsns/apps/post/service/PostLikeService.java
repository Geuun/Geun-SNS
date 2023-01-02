package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.data.entity.PostLikeEntity;
import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.repository.PostLikeRepository;
import com.dev.geunsns.apps.post.repository.PostRepository;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.exception.UserAppErrorCode;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public boolean checkPostLike(Long postId, String userName) {
        // 게시물이 있는지 Check
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND, String.format("PostId %d was not found", postId)));

        // User 확인
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserAppException(UserAppErrorCode.NOT_FOUND, String.format("UserName @%s was not found", userName)));

        Optional<PostLikeEntity> byPostAndUser = postLikeRepository.findByUserAndPost(user, post);

//        byPostAndUser.ifPresentOrElse(
//                postLikeEntity -> {
//                    postLikeRepository.delete(postLikeEntity);
//                    post.updateLikeCnt(); // Like Cnt Update
//                },
//                () -> {
//                    PostLikeEntity postLikeEntity = PostLikeEntity.builder()
//                            .user(user)
//                            .post(post)
//                            .build();
//
//                    postLikeRepository.save(postLikeEntity);
//                    post.updateLikeCnt(); // Like Cnt Update
//                }
//        );

        if (byPostAndUser.isEmpty()) {
            PostLikeEntity postLikeEntity = PostLikeEntity.builder()
                    .user(user)
                    .post(post)
                    .build();

            postLikeRepository.save(postLikeEntity);
            post.updateLikeCnt(); // Like Cnt Update

            return true;
        } else {
            postLikeRepository.delete(byPostAndUser.get());
            post.updateLikeCnt(); // Like Cnt Update

            return false;
        }
    }
}
