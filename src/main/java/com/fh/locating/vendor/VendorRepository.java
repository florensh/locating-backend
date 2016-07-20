package com.fh.locating.vendor;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VendorRepository extends MongoRepository<Vendor, String> {

	Vendor findByMacPrefix(String macPrefix);

}
