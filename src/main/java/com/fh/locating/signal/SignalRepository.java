package com.fh.locating.signal;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface SignalRepository extends MongoRepository<Signal, String> {

	List<Signal> findByTimestampBetweenAndMacIn(@Param("start") Date start,
			@Param("end") Date end, @Param("macs") Collection<String> macs);

}
