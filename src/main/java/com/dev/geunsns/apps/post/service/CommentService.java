package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import com.dev.geunsns.apps.post.data.dto.comment.CommentRequest;
import com.dev.geunsns.apps.post.data.dto.comment.CommentResponse;
import com.dev.geunsns.apps.post.data.entity.CommentEntity;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.repository.CommentRepository;
import com.dev.geunsns.apps.post.repository.PostRepository;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.repository.UserRepository;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse addComment(Long postId, String userName, String comment) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                        String.format("PostId #%d was not found.", postId)));

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND,
                        String.format("UserName %s was not found.", userName)));

        CommentEntity savedcomment = CommentEntity
                .builder()
                .comment(comment)
                .post(postEntity)
                .user(userEntity)
                .build();
        commentRepository.save(savedcomment);

        CommentResponse commentResponse = CommentResponse.builder()
                .id(savedcomment.getId())
                .comment(comment)
                .postId(postId)
                .userName(userName)
                .createdAt(savedcomment.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")))
                .build();
        return commentResponse;
    }

    public Page<CommentDto> getComments(Long postId, Pageable pageable) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() ->
                        new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                                String.format("PostId %d was not found.", postId)));

        return commentRepository.findAllByPost(postEntity, pageable)
                .map(CommentDto::toDto);
    }

    @Transactional
    public CommentResponse updateComment(Long postId, String userName, CommentRequest commentRequest, Long commentId) {
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.COMMENT_NOT_FOUND,
                        String.format("UserName %s was not found", userName)));

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND,
                        String.format("UserName %s was not found.", userName)));

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new PostAppException(PostAppErrorCode.DATABASE_ERROR,
                                "The Commnet was Not found in database"));

        commentEntity.getUser()
                .getUserName();

        if (!Objects.equals(commentEntity.getUser()
                .getUserName(), userName)) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "Author does not match");
        }

        commentEntity.updateComment(commentRequest.toEntity(commentRequest.getComment()));
        CommentResponse commentResponse = CommentResponse.builder()
                .comment(commentEntity.getComment())
                .build();
        return commentResponse;
    }

    @Transactional
    public Boolean deleteComment(Long id, String userName) {
        CommentEntity entity = commentRepository.findById(id)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
                        String.format("Comment id #%d was not found", id)));

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND,
                        String.format("UserName %s was not found.", userName)));

        Long userId = userEntity.getId();

        if (!Objects.equals(entity.getUser()
                .getId(), userId)) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION, String.format("User #%s  do not have access to Comment %d.", userId, id));
        }

        commentRepository.delete(entity);

        return true;
    }
}
