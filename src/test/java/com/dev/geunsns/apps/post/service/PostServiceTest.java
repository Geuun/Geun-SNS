package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.request.PostAddRequest;
import com.dev.geunsns.apps.post.data.dto.post.request.PostUpdateRequest;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.repository.PostLikeRepository;
import com.dev.geunsns.apps.post.repository.PostRepository;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.repository.UserRepository;
import com.dev.geunsns.fixture.PostEntityFixture;
import com.dev.geunsns.fixture.TestUserFixture;
import com.dev.geunsns.fixture.UserEntityFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PostServiceTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private PostRepository postRepository = Mockito.mock(PostRepository.class);
    private PostLikeRepository postLikeRepository = Mockito.mock(PostLikeRepository.class);

    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, userRepository, postLikeRepository);
    }

    @Test
    @DisplayName("포스트 등록 성공")
    void addPost_Success() {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();

        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());

        PostEntity postEntity = PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        PostAddRequest postAddRequest = PostAddRequest.builder()
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .build();

        when(userRepository.findByUserName(testEntity.getUserName()))
                .thenReturn(Optional.of(user));

        when(postRepository.save(any()))
                .thenReturn(postEntity);

        assertDoesNotThrow(() -> postService.addPost(postAddRequest, user.getUserName()));
    }

    @Test
    @DisplayName("포스트 등록 실패 - 유저 없음")
    void addPost_Fail_1() {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();

        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());

        PostEntity postEntity = PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        PostAddRequest postAddRequest = PostAddRequest.builder()
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .build();

        when(userRepository.findByUserName(testEntity.getUserName()))
                .thenReturn(Optional.empty());

        when(postRepository.save(any()))
                .thenReturn(postEntity);

        Assertions.assertThrows(UserAppException.class, () -> postService.addPost(postAddRequest, user.getUserName()));
    }

    @Test
    @DisplayName("포스트 조회 성공")
    void getPost_Success() {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();

        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());

        PostEntity postEntity = PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        when(postRepository.findById(testEntity.getPostId()))
                .thenReturn(Optional.of(postEntity));

        PostDto postDto = postService.getPost(testEntity.getPostId());

        assertEquals(postDto.getId(), postEntity.getId());
        assertEquals(postDto.getTitle(), postEntity.getTitle());
        assertEquals(postDto.getBody(), postEntity.getBody());
        assertEquals(postDto.getUserName(), postEntity.getUser().getUserName());
    }

    @Test
    @DisplayName("포스트 조회 실패 - 유저 없음")
    void getPost_Fail_1() {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();

        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());

        when(postRepository.findById(testEntity.getPostId()))
                .thenReturn(Optional.empty());

        assertThrows(PostAppException.class, () -> postService.getPost(testEntity.getPostId()));
    }

    @Test
    @DisplayName("포스트 수정 실패 - 유저네임 존재 x")
    void updatePost_Fail1() {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();

        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());

        PostEntity postEntity = PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .build();

        when(userRepository.findByUserName(any()))
                .thenThrow(new PostAppException(PostAppErrorCode.POST_NOT_FOUND));

        assertThrows(PostAppException.class, () -> {
            userRepository.findByUserName(user.getUserName());
        });
    }

    @Test
    @DisplayName("포스트 수정 실패 - 수정 권한 없음")
    void updatePost_Fail2() {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();

        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());

        PostEntity postEntity = PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .build();

        when(postRepository.findById(testEntity.getPostId()))
                .thenReturn(Optional.empty());

        assertThrows(PostAppException.class, () -> {
            postService.updatePost(user.getUserName(), postEntity.getId(), postUpdateRequest, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        });
    }

    @Test
    @DisplayName("포스트 수정 실패 - 포스트 존재 x")
    void updatePost_Fail3() {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();

        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());

        PostEntity postEntity = PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .build();

        when(postRepository.findById(testEntity.getPostId()))
                .thenReturn(Optional.empty());

        assertThrows(PostAppException.class, () -> {
            postService.updatePost(user.getUserName(), postEntity.getId(), postUpdateRequest, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        });
    }

    @Test
    @DisplayName("포스트 삭제 성공 - Soft Delete")
    void deletePost_Success() {

        TestUserFixture.TestEntity testEntity = TestUserFixture.get();

        UserEntity user = UserEntityFixture.getUser(testEntity.getUserName(), testEntity.getPassword());

        PostEntity postEntity = PostEntityFixture.get(testEntity.getPostId(), testEntity.getTitle(), testEntity.getBody(), user);

        when(userRepository.findByUserName(testEntity.getUserName()))
                .thenReturn(Optional.of(user));

        when(postRepository.findById(testEntity.getPostId()))
                .thenReturn(Optional.of(postEntity));

        PostDto result = postService.deletePost(testEntity.getPostId(), testEntity.getUserName(), List.of(new SimpleGrantedAuthority("ROLE_USER")));

        Optional<PostEntity> deletedPostId = postRepository.findById(result.getId());

        assertTrue(deletedPostId.isPresent());
    }
}