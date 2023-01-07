package com.dev.geunsns.apps.post.data.dto.post.response;

import com.dev.geunsns.apps.alarm.data.dto.AlarmDto;
import com.dev.geunsns.apps.alarm.data.entity.AlarmEntity;
import com.dev.geunsns.apps.post.data.dto.post.PostDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListGetResponse {

    private String message;
    private List<PostDto> content;
    private Pageable pageable;

    @Builder
    public PostListGetResponse(String message, List<PostDto> content, Pageable pageable) {
        this.message = message;
        this.content = content;
        this.pageable = pageable;
    }

    public static Page<AlarmDto> toListResponse(Page<AlarmEntity> alarmEntityList) {
        return alarmEntityList.map(AlarmDto::toDto);
    }
}
