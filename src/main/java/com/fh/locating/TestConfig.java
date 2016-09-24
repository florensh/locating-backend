package com.fh.locating;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@Profile("test")
public class TestConfig {

	@Bean
	public ImageStorageService imageStorageService() {
		return new DummyImageStorageService();
	}

}
