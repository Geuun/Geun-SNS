package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.model.UserRole;
import com.dev.geunsns.apps.post.data.dto.post.PostDetailResponse;
import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.PostRequest;
import com.dev.geunsns.apps.post.data.dto.post.PostResponse;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.repository.CommentRepository;
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

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostDto addPost(PostRequest postRequest, String userName) {
        /**
         * 작성 성공 -> postId와 함께 200 반환
         * 작성 실패 -> UserNamed
         */
        log.info(String.format("addPost - title: %s, body: %s, userName: %s", postRequest.getTitle(), postRequest.getBody(), userName));
        UserEntity userEntity =
                userRepository.findByUserName(userName).orElseThrow(()
                        -> new UserAppException(UserAppErrorCode.NOT_FOUND, String.format("UserName %s's post could not be found.", userName)));

        PostEntity savedPostEntity = postRepository.save(new PostEntity(postRequest.getTitle(), postRequest.getBody(), userEntity));

        PostDto postDto = PostDto.builder()
                .id(savedPostEntity.getId())
                .build();

        return postDto;
    }

    @Transactional
    public PostDetailResponse getPost(String userName, Long postId) {

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, userName + "없습니다.!"));

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND, "게시글이 존재하지않습니다."));

        PostDetailResponse postDetailResponse = PostDetailResponse.builder()
                .id(postId)
                .title(post.getTitle())
                .body(post.getBody())
                .lastModifiedAt(post.getLastModifiedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")))
                .createdAt(post.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")))
                .userName(post.getUser()
                        .getUserName())
                .build();
        return postDetailResponse;
    }

    @Transactional
    public Page<PostDto> getPostList(Pageable pageable) {

        Page<PostEntity> postEntities = postRepository.findAll(pageable);
        Page<PostDto> postDtos = PostDto.toDtoList(postEntities);

        return postDtos;
    }

    @Transactional
    public PostResponse modifyPost(String userName, Long postId, PostRequest postRequest) {

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, "UserName %s was not found."));

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                        "UserName %s's post could not be found."));

        if (!Objects.equals(postEntity.getUser()
                .getUserName(), userEntity.getUserName())) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "User has no permission with post");
        }

        postEntity.updatePost(postRequest.toEntity(postRequest.getTitle(), postRequest.getBody()));
        PostResponse postResponse = PostResponse.builder()
                .postId(postId)
                .message("SUCCESS")
                .build();
        return postResponse;
    }

    @Transactional
    public Long deletePost(Long postId, String userName, Collection<? extends GrantedAuthority> authorities) {
        // Post check
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                        String.format("postId %d was not found", postId)));

        // 작성유저 일치 여부 확인 and Admin 권한 확인
        if (!Objects.equals(postEntity.getUser().getUserName(), userName)
        || !authorities.stream().findFirst().get().getAuthority().equals(UserRole.ROLE_ADMIN.toString())) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION,
                    String.format("User #%s  do not have access to Post %d.", userName, postId));
        }

        try {
            postRepository.delete(postEntity);
        } catch (Exception e) {
            throw new PostAppException(PostAppErrorCode.DATABASE_ERROR);
        }

        return postEntity.getId();
    }
}
