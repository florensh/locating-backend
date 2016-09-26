package com.fh.locating;

import java.io.IOException;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fh.locating.image.Image;
import com.fh.locating.image.ImageRepository;

public class CloudinaryUploader implements ImageStorageService {

	private Logger LOGGER = LoggerFactory.getLogger(CloudinaryUploader.class);

	@Autowired
	private ImageRepository imageRepository;

	@Value("${cloudinary.api_key}")
	private String apiKey;

	@Value("${cloudinary.api_secret}")
	private String apiSecret;

	@Value("${cloudinary.cloud_name}")
	private String cloudName;

	@Override
	public void store(MultipartFile image) {

		String fileName = image.getOriginalFilename();
		String[] arr = fileName.split("_");
		String cameraId = arr[0];
		String isoTimestamp = arr[1];
		DateTime timestamp = new DateTime(isoTimestamp);

		Map uploadResult = null;

		Cloudinary c = new Cloudinary(ObjectUtils.asMap("api_key", apiKey,
				"api_secret", apiSecret, "cloud_name", cloudName));

		try {
			uploadResult = c.uploader().upload(image.getBytes(),
					ObjectUtils.asMap("resource_type", "auto"));

			LOGGER.info(String.format(
					"Image has been uploaded to cloudinary. url is %s",
					(String) uploadResult.get("secure_url")));

			Image img = new Image();

			String url = (String) uploadResult.get("secure_url");
			if (url == null || url.isEmpty()) {
				url = (String) uploadResult.get("url");
			}

			img.setUrl((String) uploadResult.get(uploadResult.get("secure_url")));
			img.setTimestamp(timestamp);
			img.setCameraIdentification(cameraId);

			this.imageRepository.save(img);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
