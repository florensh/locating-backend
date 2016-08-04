package com.fh.locating.signal;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Signal {

	@Id
	private String id;

	private String mac;

	private String receiverUuid;

	@Indexed(name = "timeIndex")
	private DateTime timestamp;

	private Integer rssi;

	private String vendor;

	private String ssid;

	public String getId() {
		return id;
	}

	public String getMac() {
		return mac;
	}

	public String getReceiverUuid() {
		return receiverUuid;
	}

	public Integer getRssi() {
		return rssi;
	}

	public String getSsid() {
		return ssid;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public String getVendor() {
		return vendor;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public void setReceiverUuid(String receiverUuid) {
		this.receiverUuid = receiverUuid;
	}

	public void setRssi(Integer rssi) {
		this.rssi = rssi;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}
