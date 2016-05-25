package com.fh.locating;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fh.locating.people.Person;
import com.fh.locating.people.PersonRepository;
import com.fh.locating.signal.Signal;
import com.fh.locating.signal.SignalRepository;

@RepositoryEventHandler
@Component
public class SignalEventHandler {

	private final Logger LOGGER = LoggerFactory
			.getLogger(SignalEventHandler.class);

	@Value("${pushover.token}")
	private String token;

	@Value("${pushover.user}")
	private String user;

	@Value("${pushover.url}")
	private String url;

	@Autowired
	private SignalRepository signalRepository;

	@Autowired
	private PersonRepository personRepository;

	@HandleBeforeCreate
	public void notifyViaPushover(Signal s) {

		DateTime ts = s.getTimestamp();

		List<Signal> signals = this.signalRepository
				.findByTimestampBetweenAndMacIn(ts.minusHours(2), ts,
						Arrays.asList(s.getMac()));

		if (signals == null || signals.isEmpty()) {
			this.LOGGER.info("Sending to pushover");
			Person p = this.personRepository.findByDevicesMac(s.getMac());
			String d = p.getDeviceByMac(s.getMac());

			send(p.getName(), d);
		}

	}

	private void send(String name, String device) {

		String title = String.format("Person detected", name);
		String message = String.format(
				"<b>%s</b> is at home with device <u>%s</u>", name, device);

		PushoverMessage pMessage = new PushoverMessage(this.token, this.user,
				title, message);

		URI uri;
		try {
			uri = new URI(this.url);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}

		// register form message converter
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

		// create form parameters as a MultiValueMap
		final MultiValueMap<String, String> formVars = new LinkedMultiValueMap<String, String>();
		formVars.add("token", pMessage.getToken());
		formVars.add("user", pMessage.getUser());
		formVars.add("title", pMessage.getTitle());
		formVars.add("message", pMessage.getMessage());

		final String result = restTemplate.postForObject(uri.toString(),
				formVars, String.class);

	}

	class PushoverMessage {

		private String token;

		private String user;

		private String title;

		private String message;

		public PushoverMessage(String token, String user, String title,
				String message) {

			this.token = token;
			this.user = user;
			this.title = title;
			this.message = message;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

}
