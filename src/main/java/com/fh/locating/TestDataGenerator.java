package com.fh.locating;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fh.locating.people.Device;
import com.fh.locating.people.Person;
import com.fh.locating.people.PersonRepository;
import com.fh.locating.signal.Signal;
import com.fh.locating.signal.SignalRepository;

@Component
@Profile("dev")
public class TestDataGenerator {

	private Logger LOGGER = LoggerFactory.getLogger(TestDataGenerator.class);

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private SignalRepository signalRepository;

	String[][] people = { { "Hans", "b8:09:8a:ff:ff:ff", "Phone" } };

	Object[][] signals = { { "b8:09:8a:ff:ff:ff", "uuid", new DateTime(), -74,
			"test_wlan" } };

	@PostConstruct
	public void createData() {
		this.LOGGER.info("Creating test data!");
		createPeople();
		createSignals();

	}

	private void createSignals() {
		for (int i = 0; i < signals.length; i++) {
			Signal sig = new Signal();
			sig.setMac((String) signals[i][0]);
			sig.setReceiverUuid((String) signals[i][1]);
			sig.setTimestamp((DateTime) signals[i][2]);
			sig.setRssi((int) signals[i][3]);
			sig.setSsid((String) signals[i][4]);

			this.signalRepository.save(sig);
		}
	}

	private void createPeople() {
		for (int i = 0; i < people.length; i++) {
			Device d = new Device();
			Person p = new Person();
			p.setName(people[i][0]);
			p.setDevices(Arrays.asList(d));
			d.setMac(people[i][1]);
			d.setName(people[i][2]);

			this.personRepository.save(p);
		}
	}

}
