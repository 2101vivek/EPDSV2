package com.wellpoint.mde.constants;

import java.util.Arrays;
import java.util.List;

public enum ProvPgmEnum {

	PROGTYPE("PROGTYPE"),
	PROGADMIN("PROGADMIN"),
	PROGNAME("PROGNAME"),
	PROGSTATUS("PROGSTATUS"),
	PRGVERNUM("PRGVERNUM"),
	PRGCATCD("PRGCATCD"),
	VALBASPRGID("VALBASPRGID"),
	
	ALTSRCID("ALTSRCID"),
	MPPRG("MPPRG"),
	
	DEFAULT("DEFAULT");
	
	private String value;
	
	/**
	 * Instantiates a new MemGetEnum enum.
	 *
	 * @param value the value
	 */
	private ProvPgmEnum(final String value) {
		this.value = value;
	}	
	
	/**
	 * Gets the MemGetEnum constants.
	 *
	 * @return the MemGetEnum constants
	 */
	public static List<ProvPgmEnum> getInitiateConstants() {
		return Arrays.asList(ProvPgmEnum.values());
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public final String getValue() {
		return value;
	}

	@Override
	public final String toString() {
		return this.getValue();
	}	

	/**
	 * Gets the initiate enum.
	 *
	 * @param value the value
	 * @return the MemGetEnum enum
	 */
	public static ProvPgmEnum getInitiateEnum(final String value) {
		ProvPgmEnum returnValue = DEFAULT;
		for (ProvPgmEnum provPgmEnum : values()) {
			if (provPgmEnum.value.equals(value)) {
				returnValue = provPgmEnum;
				break;
			}
		}
		return returnValue;
	}
	
	/**
	 * Gets the initiate enum ignoring the case.
	 *
	 * @param value the value
	 * @return the MemGetEnum enum
	 */
	public static ProvPgmEnum getInitiateEnumIgnoreCase(final String value) {
		ProvPgmEnum returnValue = DEFAULT;
		String ignoreCase = value.toUpperCase();
		for (ProvPgmEnum provPgmEnum : values()) {
			if (provPgmEnum.value.equals(ignoreCase)) {
				returnValue = provPgmEnum;
				break;
			}
		}
		return returnValue;
	}
}
