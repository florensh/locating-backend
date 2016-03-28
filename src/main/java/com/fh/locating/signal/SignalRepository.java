package com.fh.locating.signal;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public interface SignalRepository extends MongoRepository<Signal, String> {

	List<Signal> findByTimestampBetweenAndMacIn(
			@DateTimeFormat(iso = ISO.DATE_TIME) @Param("start") Date start,
			@DateTimeFormat(iso = ISO.DATE_TIME) @Param("end") Date end,
			@Param("macs") Collection<String> macs);

	List<Signal> findByMacIn(@Param("macs") Collection<String> macs);

}
