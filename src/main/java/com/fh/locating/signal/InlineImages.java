package com.fh.locating.signal;

import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.data.rest.core.config.Projection;

import com.fh.locating.image.Image;

@Projection(name = "inlineImages", types = { Signal.class })
public interface InlineImages {

	Set<Image> getImages();

	String getMac();

	Integer getRssi();

	String getSsid();

	DateTime getTimestamp();

	String getVendor();

	String getReceiverUuid();

}
