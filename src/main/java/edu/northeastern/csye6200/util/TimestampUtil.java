package edu.northeastern.csye6200.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimestampUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimestampUtil.class);

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static long getTimestampFromDateString(String dateString) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Date date = format.parse(dateString);
		long timestamp = date.getTime();
		return timestamp;
	}
	
	public static long getTimestampFromLocalDate(LocalDate localDate) {
		long dateInMillis = TimeUnit.DAYS.toMillis(localDate.toEpochDay());
		return dateInMillis;
	}
	
	public static String getDateStringFromTimestamp(long timestamp) {
		Date date = new Date(timestamp);
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		String result = format.format(date);
		return result;
	}
	
	public static void main(String[] args) {
		LocalDate localDate = LocalDate.now();
		long timestamp = TimestampUtil.getTimestampFromLocalDate(localDate);
		LOGGER.trace("timestamp: {}", timestamp);
	}
	
}
