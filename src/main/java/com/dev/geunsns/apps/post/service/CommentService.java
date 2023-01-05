package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.model.UserRole;
import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import com.dev.geunsns.apps.post.data.dto.comment.response.CommentResponse;
import com.dev.geunsns.apps.post.data.dto.comment.request.CommentAddRequest;
import com.dev.geunsns.apps.post.data.dto.comment.request.CommentUpdateRequest;
import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.repository.CommentRepository;
import com.dev.geunsns.apps.post.repository.PostRepository;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.repository.UserRepository;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentDto addComment(Long postId, String userName, CommentAddRequest commentAddRequest) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND, String.format("PostId #%d was not found.", postId)));

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, String.format("UserName %s was not found.", userName)));

        CommentEntity savedComment = commentRepository.save(CommentEntity.builder()
                        .post(postEntity)
                        .user(userEntity)
                        .comment(commentAddRequest.getComment())
                .build());

        CommentDto commentDto = CommentDto.toDto(savedComment);

        return commentDto;
    }

    @Transactional(readOnly = true) // 조회만 하기 때문에 readOnly = true
    public CommentDto getComment(Long commentId) {

        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.COMMENT_NOT_FOUND, String.format("CommentId #%d was not found.", commentId)));

        CommentDto commentDto = CommentDto.toDto(comment);

        return commentDto;
    }

    @Transactional(readOnly = true)
    public Page<CommentDto> getComments(Long postId, Pageable pageable) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() ->
                        new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                                String.format("PostId %d was not found.", postId)));

        return commentRepository.findAllByPost(postEntity, pageable)
                .map(CommentDto::toDto);
    }

    @Transactional
    public CommentDto updateComment(String userName, CommentUpdateRequest commentUpdateRequest, Long commentId) {
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND,
                        String.format("UserName @%s was not found", userName)));

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new PostAppException(PostAppErrorCode.COMMENT_NOT_FOUND,
                                "The Commnet was Not found in database"));

        commentEntity.getUser()
                .getUserName();

        if (!Objects.equals(commentEntity.getUser()
                .getUserName(), userName)) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "Author does not match");
        }

        commentEntity.updateComment(commentUpdateRequest.toEntity(commentUpdateRequest.getComment()));

        CommentDto commentDto = CommentDto.toDto(commentEntity);

        return commentDto;
    }

    @Transactional
    public CommentDto deleteComment(Long commentId, String userName, Collection<? extends GrantedAuthority> authorities) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                        String.format("Comment id #%d was not found", commentId)));

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND,
                        String.format("UserName %s was not found.", userName)));

        Long userId = userEntity.getId();

        if (!Objects.equals(commentEntity.getUser().getId(), userId) && !authorities.stream().findFirst().get().getAuthority().equals(UserRole.ROLE_ADMIN.toString())) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION, String.format("User #%s  do not have access to Comment %d.", userId, commentId));
        }

        try {
            commentRepository.delete(commentEntity);
        } catch (Exception e) {
            throw new PostAppException(PostAppErrorCode.DATABASE_ERROR);
        }

        CommentDto deletedComment = CommentDto.builder()
                .id(commentId)
                .build();

        return deletedComment;
    }
}
