package com.dev.geunsns.global.config.jpaauditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {
		// Auditing을 처리해주는 AuditorAware를 Bean으로 등록
		return new AuditorAwareImpl();
	}
}
