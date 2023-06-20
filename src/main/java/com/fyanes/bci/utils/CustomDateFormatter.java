package com.fyanes.bci.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomDateFormatter {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy hh:mm:ss a", Locale.US);

	public static String convertDateToString(LocalDateTime date) {
		return date != null ? date.format(FORMATTER) : null;
	}
}
