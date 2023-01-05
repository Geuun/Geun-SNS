package com.dev.geunsns.apps.post.service;

import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import com.dev.geunsns.apps.post.repository.MyFeedRepository;
import com.dev.geunsns.apps.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyFeedService {
    private final UserRepository userRepository;
    private final MyFeedRepository myFeedRepository;

//    public Page<PostDto> getMyFeed(String userName, Pageable pageable) {
//
//    }
}
