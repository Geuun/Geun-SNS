package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.alarm.data.entity.AlarmEntity;
import com.dev.geunsns.apps.alarm.data.model.AlarmType;
import com.dev.geunsns.apps.alarm.repository.AlarmRepository;
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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    public boolean addAndRemoveLike(Long postId, String userName) {
        // 게시물이 있는지 Check
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND, String.format("PostId %d was not found", postId)));

        // User 확인
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserAppException(UserAppErrorCode.NOT_FOUND, String.format("UserName @%s was not found", userName)));

        Optional<PostLikeEntity> byPostAndUser = postLikeRepository.findByUserAndPost(user, post);

        if (byPostAndUser.isEmpty()) {
            PostLikeEntity postLikeEntity = PostLikeEntity.builder()
                    .user(user)
                    .post(post)
                    .build();

            postLikeRepository.save(postLikeEntity);

            // alarm
            alarmRepository.save(AlarmEntity.builder()
                    .alarmType(AlarmType.NEW_LIKE_ON_POST)
                    .targetId(post.getId())
                    .fromUserId(user.getId())
                    .text(String.format("@%s님이 @%s님의 게시물에 좋아요를 눌렀습니다.", user.getUserName(), post.getUser().getUserName()))
                    .user(post.getUser())
                    .build()
            );

            return true;
        } else {
            postLikeRepository.delete(byPostAndUser.get());

            return false;
        }
    }

    public Integer getLikeCount(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND, String.format("PostId %d was not found", postId)));

        return postLikeRepository.countByPost(post);
    }
}
