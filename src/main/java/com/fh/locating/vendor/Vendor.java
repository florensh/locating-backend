package com.fh.locating.vendor;

import org.springframework.data.annotation.Id;

public class Vendor {

	@Id
	private String id;

	private String macPrefix;

	private String name;

	public String getId() {
		return id;
	}

	public String getMacPrefix() {
		return macPrefix;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMacPrefix(String macPrefix) {
		this.macPrefix = macPrefix;
	}

	public void setName(String name) {
		this.name = name;
	}

}
