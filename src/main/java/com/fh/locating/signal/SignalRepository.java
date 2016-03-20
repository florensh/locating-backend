package com.fh.locating.signal;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SignalRepository extends MongoRepository<Signal, String> {

	List<Signal> findByTimestampBetweenAndMacIn(Date start, Date end,
			Collection<String> macs);

}
