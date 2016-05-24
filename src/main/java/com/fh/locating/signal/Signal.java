package com.fh.locating.signal;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

public class Signal {

	@Id
	private String id;

	private String mac;

	private DateTime timestamp;

	private Integer rssi;

	public Integer getRssi() {
		return rssi;
	}

	public void setRssi(Integer rssi) {
		this.rssi = rssi;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

}
