package com.fh.locating;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DummyImageStorageService implements ImageStorageService {

	@Override
	public void store(MultipartFile image) {
		// TODO Auto-generated method stub

	}

}
