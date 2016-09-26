package com.fh.locating.image;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

public class Image {

	@Id
	private String id;

	private String imageUrl;

	private DateTime timestamp;

	private String cameraIdentification;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String url) {
		this.imageUrl = url;
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
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
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
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
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
