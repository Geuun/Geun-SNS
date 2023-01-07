package com.dev.geunsns.apps.alarm.data.dto.response;

import com.dev.geunsns.apps.alarm.data.dto.AlarmDto;
import com.dev.geunsns.apps.alarm.data.entity.AlarmEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmResponse {

    private String message;
    private List<AlarmDto> content;
    private Pageable pageable;

    @Builder
    public AlarmResponse(String message, List<AlarmDto> content, Pageable pageable) {
        this.message = message;
        this.content = content;
        this.pageable = pageable;
    }
}
