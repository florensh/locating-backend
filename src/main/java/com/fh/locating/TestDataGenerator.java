package com.fh.locating;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fh.locating.image.Image;
import com.fh.locating.image.ImageRepository;
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
	private ImageRepository images;

	@Autowired
	private SignalRepository signalRepository;

	String[][] people = { { "Hans", "b8:09:8a:ff:ff:ff", "iPhone" },
			{ "Klaus", "b1:09:8a:ff:ff:ff", "iPad" },
			{ "Horst", "b2:09:8a:ff:ff:ff", "HTC" } };

	Object[][] signals = { { "b8:09:8a:ff:ff:ff", "uuid", -74, "test_wlan" },
			{ "b1:09:8a:ff:ff:ff", "uuid", -74, "test_wlan" },
			{ "b2:09:8a:ff:ff:ff", "uuid", -74, "test_wlan" } };

	Object[][] imgs = { {
			"http://www.leo-bw.de/media/lmz_bilddatenbank_02/current/generated/9001-12000/52284.jpg.pv.jpg",
			"uuid" } };

	@PostConstruct
	public void createData() {
		createPeople();

	}

	@Scheduled(fixedDelay = 5000)
	private void createSignals() {
		this.LOGGER.info("Creating signals!");
		for (int i = 0; i < signals.length; i++) {
			Signal sig = new Signal();
			sig.setMac((String) signals[i][0]);
			sig.setReceiverUuid((String) signals[i][1]);
			sig.setTimestamp(new DateTime());
			sig.setRssi((int) signals[i][2]);
			sig.setSsid((String) signals[i][3]);

			this.signalRepository.save(sig);
		}
	}

	@Scheduled(fixedDelay = 5000)
	private void createImages() {
		this.LOGGER.info("Creating images!");
		for (int i = 0; i < imgs.length; i++) {
			Image img = new Image();
			img.setUrl((String) imgs[i][0]);
			img.setCameraIdentification((String) imgs[i][1]);
			img.setTimestamp(DateTime.now());

			this.images.save(img);
		}
	}

	private void createPeople() {
		this.LOGGER.info("Creating people!");
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
