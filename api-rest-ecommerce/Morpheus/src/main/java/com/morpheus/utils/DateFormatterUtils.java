package com.morpheus.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtils {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	// de un instant a un string
	public static String localDateToString(Instant instant) {
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localDateTime.format(formatter);
	}

	// de un instant a localdatetime
	public static LocalDateTime instantToLocalDateTime(Instant instant) {
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localDateTime;
	}

	// de un string a localdatetime
	public static LocalDateTime localDateTime(String date) {
		LocalDateTime localdateTime = LocalDateTime.parse(date, formatter);
		return localdateTime;
	}

	// de un string a instant
	public static Instant stringToInstant(String date) {
		LocalDateTime localdateTime = LocalDateTime.parse(date, formatter);
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = localdateTime.atZone(zoneId);
		return zonedDateTime.toInstant();
	}
}
