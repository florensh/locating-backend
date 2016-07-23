package com.fh.locating.signal;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Signal {

	@Id
	private String id;

	private String mac;

	@Indexed(name = "timeIndex")
	private DateTime timestamp;

	private Integer rssi;

	private String vendor;

	private String ssid;

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

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
