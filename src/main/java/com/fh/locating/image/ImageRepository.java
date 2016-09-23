package com.fh.locating.image;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

public interface ImageRepository extends MongoRepository<Image, String> {

	List<Image> findByTimestampBetween(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Param("start") DateTime start,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Param("end") DateTime end);

}
