package com.fh.locating;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fh.locating.people.Device;
import com.fh.locating.people.Person;
import com.fh.locating.people.PersonRepository;
import com.fh.locating.signal.Signal;
import com.fh.locating.signal.SignalRepository;

@RepositoryEventHandler
@Component
@ConditionalOnProperty(name = "pushover.url")
public class PushoverSender {

	private final Logger LOGGER = LoggerFactory.getLogger(PushoverSender.class);

	@Value("${pushover.token}")
	private String token;

	@Value("${pushover.user}")
	private String user;

	@Value("${pushover.url}")
	private String url;

	@Value("${pushover.title}")
	private String titleTemplate;

	@Value("${pushover.message}")
	private String messageTemplate;

	@Value("${pushover.awayTime}")
	private Integer awayTime;

	@Autowired
	private SignalRepository signalRepository;

	@Autowired
	private PersonRepository personRepository;

	private final static Map<String, Object> LOCK_CACHE = new HashMap<String, Object>();

	@HandleBeforeCreate
	public void notifyViaPushover(Signal s) {

		DateTime ts = s.getTimestamp();
		String mac = s.getMac();

		Object lock = PushoverSender.LOCK_CACHE.get(mac);

		if (lock == null) {
			lock = PushoverSender.LOCK_CACHE.put(mac, new Object());
		}

		synchronized (lock) {
			List<Signal> signals = this.signalRepository
					.findByTimestampBetweenAndMacIn(ts.minusHours(awayTime),
							ts, Arrays.asList(s.getMac()));
			if (signals == null || signals.isEmpty()) {
				Person person = this.personRepository.findByDevicesMac(mac);

				if (person != null) {
					Device device = person.getDeviceByMac(mac);

					if (Boolean.TRUE.equals(device.getEnabled())) {
						this.LOGGER.info("Sending to pushover");
						String deviceName = device.getName();

						send(person.getName(), deviceName);
					}

				}
			} else {
				this.LOGGER
						.info(String
								.format("Cancel - already sent within last %d hours to pushover!",
										awayTime));
			}
		}

	}

	private void send(String name, String device) {

		String title = String.format(this.titleTemplate, name);
		String message = String.format(this.messageTemplate, name, device);

		PushoverMessage pMessage = new PushoverMessage(this.token, this.user,
				title, message);

		URI uri;
		try {
			uri = new URI(this.url);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}

		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

		restTemplate.postForObject(uri.toString(), pMessage, String.class);

	}

	class PushoverMessage extends LinkedMultiValueMap<String, String> {

		private static final long serialVersionUID = 3623949040406242112L;

		public PushoverMessage(String token, String user, String title,
				String message) {

			add("token", token);
			add("user", user);
			add("title", title);
			add("message", message);
			add("html", "1");

		}

		public String getToken() {
			return super.getFirst("token");
		}

		public String getUser() {
			return super.getFirst("user");
		}

		public String getTitle() {
			return super.getFirst("title");
		}

		public String getMessage() {
			return super.getFirst("message");
		}

	}

}
