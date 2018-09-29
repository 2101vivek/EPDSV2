package com.wellpoint.mde.constants;

import java.util.Arrays;
import java.util.List;

public enum BusEnum {

	BUSENTTAXID("BUSENTTAXID"),
	BUSENTALTID("BUSENTALTID"),
	BUSENTADDR("BUSENTADDR"),
	BUSENTCNTRCT("BUSENTCNTRCT"),
	BUSENTNAME("BUSENTNAME"),
	BUSENTTYPE("BUSENTTYPE"),
	RSKBEARORGNM("RSKBEARORGNM"),
	BUFEDTAX1099("BUFEDTAX1099"),
	BUSENTSTATUS("BUSENTSTATUS"),
	BUSENTSVCTAB("BUSENTSVCTAB"),
	BUSENTADRCNT("BUSENTADRCNT"),
	BUSENTGRPREL("BUSENTGRPREL"),
	
	APADR("APADR"),
	ALTSRCID("ALTSRCID"),
	
	BADR("BADR"),
	BALT("BALT"),
	BCNTC("BCNTC"),
	BECON("BECON"),
	BTAX("BTAX"),
	BUS("BUS"),
	BUSGRP("BUSGRP"),
	
	DEFAULT("DEFAULT");
	
	private String value;
	
	/**
	 * Instantiates a new BusEnum enum.
	 *
	 * @param value the value
	 */
	private BusEnum(final String value) {
		this.value = value;
	}	
	
	/**
	 * Gets the BusEnum constants.
	 *
	 * @return the BusEnum constants
	 */
	public static List<BusEnum> getInitiateConstants() {
		return Arrays.asList(BusEnum.values());
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
	 * @return the BusEnum enum
	 */
	public static BusEnum getInitiateEnum(final String value) {
		BusEnum returnValue = DEFAULT;
		for (BusEnum busEnum : values()) {
			if (busEnum.value.equals(value)) {
				returnValue = busEnum;
				break;
			}
		}
		return returnValue;
	}
	
	/**
	 * Gets the initiate enum ignoring the case.
	 *
	 * @param value the value
	 * @return the BusEnum enum
	 */
	public static BusEnum getInitiateEnumIgnoreCase(final String value) {
		BusEnum returnValue = DEFAULT;
		String ignoreCase = value.toUpperCase();
		for (BusEnum busEnum : values()) {
			if (busEnum.value.equals(ignoreCase)) {
				returnValue = busEnum;
				break;
			}
		}
		return returnValue;
	}
}
