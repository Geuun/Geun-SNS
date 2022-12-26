package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.data.entity.PostEntity;
import com.dev.geunsns.apps.post.exception.PostAppErrorCode;
import com.dev.geunsns.apps.post.exception.PostAppException;
import com.dev.geunsns.apps.post.repository.PostRepository;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.exception.UserAppErrorCode;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

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

	public PostDto viewPost(Integer id) {
		/**
		 *
		 */
		PostEntity postEntity =
			postRepository.findById(id)
						  .orElseThrow(() -> new PostAppException(PostAppErrorCode.POST_NOT_FOUND,
																  String.format("User #%d's post could not be found.", id)));

		return PostDto.toDto(postEntity);
	}

}
