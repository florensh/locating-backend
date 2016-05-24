package com.fh.locating;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fh.locating.people.Person;
import com.fh.locating.people.PersonRepository;
import com.fh.locating.signal.Signal;
import com.fh.locating.signal.SignalRepository;

@RepositoryEventHandler
@Component
public class SignalEventHandler {

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

	@HandleAfterSave
	public void notifyViaPushover(Signal s) {

		DateTime ts = new DateTime(s.getTimestamp());

		List<Signal> signals = this.signalRepository
				.findByTimestampBetweenAndMacIn(ts.minusHours(2).toDate(),
						ts.toDate(), Arrays.asList(s.getMac()));

		if (signals == null || signals.isEmpty()) {
			Person p = this.personRepository.findByDeviceMac(s.getMac());
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

		RestTemplate template = new RestTemplate();

		template.postForObject(url, pMessage, String.class);

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
