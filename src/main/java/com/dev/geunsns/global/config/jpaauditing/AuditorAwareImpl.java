package com.dev.geunsns.global.config.jpaauditing;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext()
															 .getAuthentication();

		String userName = "";

		if (authentication != null) {
			/**
			 * 로그인한 userName을 조회
			 * -> 등록자와 수정자로 지정
			 */
			userName = authentication.getName();
		}

		return Optional.of(userName);
	}
}
