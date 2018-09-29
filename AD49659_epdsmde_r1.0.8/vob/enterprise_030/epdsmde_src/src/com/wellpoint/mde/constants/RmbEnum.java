package com.wellpoint.mde.constants;

import java.util.Arrays;
import java.util.List;

public enum RmbEnum {

	CNTPRVTYP("CNTPRVTYP"),
	RMBARNGTYPE("RMBARNGTYPE"),
	RMBARNGNAME("RMBARNGNAME"),
	RMBCAPTYPE("RMBCAPTYPE"),
	RMBSTATUS("RMBSTATUS"),
	RMBNETPAR("RMBNETPAR"),
	RMBFILEDAYS("RMBFILEDAYS"),
	RMBTIER("RMBTIER"),
	RMBGRPIND("RMBGRPIND"),
	RMBINCEXCL("RMBINCEXCL"),
	RMBPAR("RMBPAR"),
	RMBNETCOV("RMBNETCOV"),
	PNLCMPCD("PNLCMPCD"),
	PNLOONIND("PNLOONIND"),
	PDTCOMPCODE("PDTCOMPCODE"),
	NMSCTRTIND("NMSCTRTIND"),
	WMSCTRTIND("WMSCTRTIND"),
	SSBCTRCTIND("SSBCTRCTIND"),
	RMBCAPSVC("RMBCAPSVC"),
	
	MRMB("MRMB"),
	MRSVC("MRSVC"),
	
	DEFAULT("DEFAULT");
	
	private String value;
	
	/**
	 * Instantiates a new MemGetEnum enum.
	 *
	 * @param value the value
	 */
	private RmbEnum(final String value) {
		this.value = value;
	}	
	
	/**
	 * Gets the MemGetEnum constants.
	 *
	 * @return the MemGetEnum constants
	 */
	public static List<RmbEnum> getInitiateConstants() {
		return Arrays.asList(RmbEnum.values());
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
	public static RmbEnum getInitiateEnum(final String value) {
		RmbEnum returnValue = DEFAULT;
		for (RmbEnum rmbEnum : values()) {
			if (rmbEnum.value.equals(value)) {
				returnValue = rmbEnum;
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
	public static RmbEnum getInitiateEnumIgnoreCase(final String value) {
		RmbEnum returnValue = DEFAULT;
		String ignoreCase = value.toUpperCase();
		for (RmbEnum rmbEnum : values()) {
			if (rmbEnum.value.equals(ignoreCase)) {
				returnValue = rmbEnum;
				break;
			}
		}
		return returnValue;
	}
}
