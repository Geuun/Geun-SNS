package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.repository.PostRepository;
import com.dev.geunsns.apps.user.data.entity.UserEntity;
import com.dev.geunsns.apps.user.exception.UserAppErrorCode;
import com.dev.geunsns.apps.user.exception.UserAppException;
import com.dev.geunsns.apps.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyFeedService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public Page<PostDto> getMyFeed(Pageable pageable, String userName) {

        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserAppException(UserAppErrorCode.NOT_FOUND, String.format("UserName %s was not found.", userName)));

        return postRepository.findAllByUser(user, pageable).map(PostDto::toDto);
    }
}
