package com.fh.locating;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fh.locating.signal.Signal;
import com.fh.locating.vendor.Vendor;
import com.fh.locating.vendor.VendorRepository;

@RepositoryEventHandler
@Component
public class MacVendorReceiver {

	@Autowired
	private VendorRepository vendorRepository;

	@Value("${macvendorlookup.url}")
	private String url;

	@HandleBeforeCreate
	public void doBeforeCreate(Signal s) {

		Vendor v = this.vendorRepository.findByMacPrefix(s.getMac().substring(
				0, 8));
		if (v == null) {
			v = fromPublicAPI(s.getMac());
			this.vendorRepository.save(v);
		}
		s.setVendor(v.getName());

	}

	private Vendor fromPublicAPI(String mac) {
		// register form message converter
		final RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

		URI uri;
		try {
			uri = new URI(this.url);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}

		// create form parameters as a MultiValueMap
		final MultiValueMap<String, String> formVars = new LinkedMultiValueMap<String, String>();
		formVars.add("mac", mac);

		final String result = restTemplate.postForObject(uri.toString(),
				formVars, String.class);

		Vendor v = new Vendor();
		v.setMacPrefix(mac.substring(0, 8));
		v.setName(result);
		return v;
	}

}
