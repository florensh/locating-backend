package com.fh.locating.signal;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SignalRepository extends MongoRepository<Signal, String> {

}
