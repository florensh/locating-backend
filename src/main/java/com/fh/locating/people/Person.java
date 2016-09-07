package com.fh.locating.people;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Person {

	@Id
	private String id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<Device> devices = new ArrayList<Device>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Device getDeviceByMac(String mac) {
		if (mac == null) {
			throw new IllegalArgumentException("Device name must not be null");
		}

		for (Device d : this.devices) {
			if (d.getMac().equals(mac)) {
				return d;
			}
		}

		return null;
	}

}
