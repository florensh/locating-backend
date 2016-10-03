package com.fh.locating;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@ConditionalOnProperty(name = "cloudinary.api_key", matchIfMissing = true)
public class DummyImageStorageService implements ImageStorageService {

	@Override
	public void store(MultipartFile image) {
		// TODO Auto-generated method stub

	}

}
