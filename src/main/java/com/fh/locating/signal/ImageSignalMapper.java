package com.fh.locating.signal;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fh.locating.image.Image;
import com.fh.locating.image.ImageRepository;

@Component
public class ImageSignalMapper {
	private Logger LOGGER = LoggerFactory.getLogger(ImageSignalMapper.class);

	private static final int LOOKUP_TIME_RANGE = 3000 * 60;

	private static final int MAP_TIME_RANGE = 120000;

	@Autowired
	private SignalRepository signals;

	@Autowired
	private ImageRepository images;

	@Scheduled(fixedDelay = LOOKUP_TIME_RANGE - 60000)
	public void map() {

		LOGGER.info("Mapping images to signals!");

		DateTime start = DateTime.now().minus(LOOKUP_TIME_RANGE);
		DateTime end = DateTime.now();

		List<Signal> signals = this.signals.findByTimestampBetween(start, end);
		List<Image> images = this.images.findByTimestampBetween(start, end);

		for (Signal s : signals) {
			for (Image i : images) {
				DateTime rangeBegin = s.getTimestamp()
						.minus(MAP_TIME_RANGE / 2);
				DateTime rangeEnd = s.getTimestamp().plus(MAP_TIME_RANGE / 2);

				if (i.getTimestamp().isAfter(rangeBegin)
						&& i.getTimestamp().isBefore(rangeEnd)) {
					s.addImage(i);
					this.signals.save(s);

				}
			}
		}

	}
}
