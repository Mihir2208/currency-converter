package edu.northeastern.csye6200;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		LOGGER.info("Launching Main with args: {}", new Object[] {args});
		App.main(args);
	}
}
