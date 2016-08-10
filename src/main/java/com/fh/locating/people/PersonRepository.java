package com.fh.locating.people;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends MongoRepository<Person, String> {

	Person findByDevicesMacAndDevicesEnabled(String mac, boolean enabled);

	Person findByDevicesMac(String mac);

}
