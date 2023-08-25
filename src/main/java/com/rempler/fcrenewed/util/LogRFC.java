package com.rempler.fcrenewed.util;

import com.rempler.fcrenewed.FCRenewed;

public class LogRFC {

	public static void debug(String msg) {

		FCRenewed.LOGGER.debug(msg);
	}
	
	public static void info(String msg) {

		FCRenewed.LOGGER.info(msg);
	}
	
	public static void error(String msg) {

		FCRenewed.LOGGER.error(msg);
	}
}
