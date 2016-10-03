package com.fh.locating;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@Profile("prod")
public class ProdConfig {

	// @Bean
	// public ImageStorageService imageStorageService() {
	// return new CloudinaryUploader();
	// }

}
