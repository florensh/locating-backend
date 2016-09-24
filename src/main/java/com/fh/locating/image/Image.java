package com.fh.locating.image;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

public class Image {

	@Id
	private String id;

	private String url;

	private DateTime timestamp;

	private String cameraIdentification;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cameraIdentification == null) ? 0 : cameraIdentification
						.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (cameraIdentification == null) {
			if (other.cameraIdentification != null)
				return false;
		} else if (!cameraIdentification.equals(other.cameraIdentification))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getCameraIdentification() {
		return cameraIdentification;
	}

	public void setCameraIdentification(String cameraIdentification) {
		this.cameraIdentification = cameraIdentification;
	}

}
