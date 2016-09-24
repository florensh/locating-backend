package com.fh.locating;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

	void store(MultipartFile image);

}
