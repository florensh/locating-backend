package com.fh.locating.signal;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

public interface SignalRepository extends MongoRepository<Signal, String> {

	List<Signal> findByTimestampBetweenAndMacIn(
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") @Param("start") Date start,
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") @Param("end") Date end,
			@Param("macs") Collection<String> macs);

	List<Signal> findByMacIn(@Param("macs") Collection<String> macs);

}
