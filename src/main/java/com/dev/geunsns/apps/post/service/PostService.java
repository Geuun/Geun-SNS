package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.model.UserRole;
import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.request.PostAddRequest;
import com.dev.geunsns.apps.post.data.dto.post.request.PostUpdateRequest;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.repository.PostLikeRepository;
import com.dev.geunsns.apps.post.repository.PostRepository;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.exception.UserAppErrorCode;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * TODO:
 * 기본적으로 Entity 를 Dto 로 바꾼 뒤
 * Response 에 할당하기
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public PostDto addPost(PostAddRequest postAddRequest, String userName) {
        /**
         * 작성 성공 -> postId와 함께 200 반환
         * 작성 실패 -> UserNamed
         */
        log.info(String.format("addPost - title: %s, body: %s, userName: %s", postAddRequest.getTitle(), postAddRequest.getBody(), userName));
        UserEntity userEntity =
                userRepository.findByUserName(userName).orElseThrow(()
                        -> new UserAppException(UserAppErrorCode.NOT_FOUND, String.format("UserName %s's post could not be found.", userName)));

        // postAddRequest.getTitle(), postAddRequest.getBody(), userEntity
        PostEntity savedPostEntity = postRepository.save(PostEntity.builder()
                .title(postAddRequest.getTitle())
                .body(postAddRequest.getBody())
                .user(userEntity)
                .build());

        PostDto postDto = PostDto.builder()
                .id(savedPostEntity.getId())
                .build();

        return postDto;
    }

    @Transactional(readOnly = true)
    public PostDto getPost(Long postId) {

//        UserEntity userEntity = userRepository.findByUserName(userName)
//                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, String.format("UserName %s was not found.", userName)));

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND, String.format("PostId %s was not found.", postId)));

        PostDto postDto = PostDto.toDto(post);

        return postDto;
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getPostList(Pageable pageable) {

        Page<PostEntity> postEntities = postRepository.findAll(pageable);
        Page<PostDto> postList = PostDto.toDtoList(postEntities);

        return postList;
    }

    @Transactional
    public PostDto updatePost(String userName, Long postId, PostUpdateRequest postUpdateRequest, Collection<? extends GrantedAuthority> authorities) {

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, String.format("UserName %s was not found.", userName)));

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                        String.format("PostId %s was not found.", postId)));

        if (!Objects.equals(postEntity.getUser().getUserName(), userEntity.getUserName())
                && !authorities.contains(UserRole.ROLE_ADMIN)) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "User has no permission with post");
        }

        postEntity.updatePost(postUpdateRequest.toEntity(postUpdateRequest.getTitle(), postUpdateRequest.getBody()));

        PostDto updatedPost = PostDto.builder()
                .id(postId)
                .title(postUpdateRequest.getTitle())
                .body(postUpdateRequest.getBody())
                .build();

        return updatedPost;
    }

//    @Transactional
    public PostDto deletePost(Long postId, String userName, Collection<? extends GrantedAuthority> authorities) {
        // Post check
        PostEntity savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                        String.format("postId %d was not found", postId)));

        // 작성유저 일치 여부 확인 || Admin 권한 확인
        if (!Objects.equals(savedPost.getUser().getUserName(), userName) && !authorities.stream().findFirst().get().getAuthority().equals(UserRole.ROLE_ADMIN.toString())) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION,
                    String.format("User #%s  do not have access to Post %d.", userName, postId));
        }

        // Post Delete
        try {
            postRepository.delete(savedPost);
        } catch (Exception e) {
            throw new PostAppException(PostAppErrorCode.DATABASE_ERROR);
        }

        PostDto deletedPost = PostDto.builder()
                .id(postId)
                .build();

        return deletedPost;
    }

    public PostDto getLikeCount(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                        String.format("PostId %s was not found.", postId)));

        Integer likeCount = postLikeRepository.countByPost(postEntity);

        PostDto postDto = PostDto.builder()
                .id(postId)
                .postLikeCount(likeCount)
                .build();

        return postDto;
    }
}
