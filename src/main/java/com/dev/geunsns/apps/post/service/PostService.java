package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.post.data.dto.comment.CommentDto;
import com.dev.geunsns.apps.post.data.dto.comment.CommentRequest;
import com.dev.geunsns.apps.post.data.dto.comment.CommentResponse;
import com.dev.geunsns.apps.post.data.dto.post.PostDetailResponse;
import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.dto.post.PostRequest;
import com.dev.geunsns.apps.post.data.dto.post.PostResponse;
import com.dev.geunsns.apps.post.data.entity.CommentEntity;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PostDto addPost(String title, String body, String userName) {
        /**
         * 작성 성공 -> postId와 함께 200 반환
         * 작성 실패 -> UserNamed
         */
        log.info(String.format("addPost - title: %s, body: %s, userName: %s", title, body, userName));
        UserEntity userEntity =
                userRepository.findByUserName(userName)
                        .orElseThrow(() -> new UserAppException(UserAppErrorCode.NOT_FOUND,
                                String.format("UserName %s's post could not be found.",
                                        userName)));

        PostEntity savedPostEntity = postRepository.save(new PostEntity(title, body, userEntity));

        PostDto postDto = PostDto.builder()
                .id(savedPostEntity.getId())
                .build();

        return postDto;
    }

    public PostDetailResponse getPost(String userName, Long postId){

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(()->new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND,userName+"없습니다.!"));

        PostEntity post = postRepository.findById(postId).orElseThrow(()->new PostAppException(PostAppErrorCode.POST_NOT_FOUND,"게시글이 존재하지않습니다."));

        PostDetailResponse postDetailResponse = PostDetailResponse.builder()
                .id(postId)
                .title(post.getTitle())
                .body(post.getBody())
                .lastModifiedAt(post.getLastModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")))
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")))
                .userName(post.getUser().getUserName())
                .build();
        return postDetailResponse;
    }

    public Page<PostDto> getPostList(Pageable pageable) {
        Page<PostEntity> postEntities = postRepository.findAll(pageable);
        Page<PostDto> postDtos = PostDto.toDtoList(postEntities);

        return postDtos;
    }

    @Transactional
    public PostResponse modifyPost(String userName, Long postId, PostRequest postRequest){

        UserEntity userEntity = userRepository.findByUserName(userName)
                                  .orElseThrow(()->new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, "UserName %s was not found."));

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()->new PostAppException(PostAppErrorCode.POST_NOT_FOUND,"UserName %s's post could not be found."));

        if(!Objects.equals(postEntity.getUser().getUserName(),userEntity.getUserName())) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION, "User has no permission with post");
        }

        postEntity.update(postRequest.toEntity(postRequest.getTitle(), postRequest.getBody()));
        PostResponse postResponse = PostResponse.builder().postId(postId).message("SUCCESS").build();
        return postResponse;
    }

    @Transactional
    public PostResponse deletePost(Long postId, String userName) {
        System.out.println("Delete Service Tes1");
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND, String.format("postId %d was not found", postId)));
        System.out.println("Delete PostEntity");

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, String.format("UserName %s was not found.", userName)));


        if (!Objects.equals(postEntity.getUser().getUserName(), userName)) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION, String.format("User #%s  do not have access to Post %d.", userName, postId)); }

        postRepository.delete(postEntity);

        PostResponse postResponse = PostResponse.builder()
                                                .message("SUCCESS")
                                                .postId(postId)
                                                .build();
        return postResponse;
    }

    @Transactional
    public CommentResponse addComment (Long postId, String userName, String comment) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new PostAppException(PostAppErrorCode.POST_NOT_FOUND,String.format("PostId #%d was not found.", postId)));

        UserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(() ->
                new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, String.format("UserName %s was not found.",userName)));

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
                .createdAt(savedcomment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")))
                .build();
        return commentResponse;
    }

    public Page<CommentDto> getComments (Long postId, Pageable pageable) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new PostAppException(PostAppErrorCode.POST_NOT_FOUND,String.format("PostId %d was not found.", postId)));

        return commentRepository.findAllByPost(postEntity,pageable).map(CommentDto::toDto);
    }

    @Transactional
    public CommentResponse updateComment(Long postId, String userName, CommentRequest commentRequest, Long commentId) {
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.COMMENT_NOT_FOUND, String.format("UserName %s was not found", userName)));

        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, String.format("UserName %s was not found.", userName)));

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() ->
                new PostAppException(PostAppErrorCode.DATABASE_ERROR,"The Commnet was Not found in database"));

        commentEntity.getUser().getUserName();

        if (!Objects.equals(commentEntity.getUser().getUserName(), userName)) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION,"Author does not match");
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
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND, String.format("Comment id #%d was not found", id)));

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new PostAppException(PostAppErrorCode.USERNAME_NOT_FOUND, String.format("UserName %s was not found.", userName)));

        Long userId = userEntity.getId();

        if (!Objects.equals(entity.getUser().getId(), userId)) {
            throw new PostAppException(PostAppErrorCode.INVALID_PERMISSION, String.format("User #%s  do not have access to Comment %d.", userId, id));
        }

        commentRepository.delete(entity);

        return true;
    }
}
