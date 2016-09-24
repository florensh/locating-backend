package com.fh.locating.signal;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

@RepositoryRestResource(excerptProjection = InlineImages.class)
public interface SignalRepository extends MongoRepository<Signal, String> {

	List<Signal> findByTimestampBetweenAndMacIn(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Param("start") DateTime start,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Param("end") DateTime end,
			@Param("macs") Collection<String> macs);

	List<Signal> findByTimestampBetween(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Param("start") DateTime start,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Param("end") DateTime end);

	List<Signal> findByMacIn(@Param("macs") Collection<String> macs);

}
