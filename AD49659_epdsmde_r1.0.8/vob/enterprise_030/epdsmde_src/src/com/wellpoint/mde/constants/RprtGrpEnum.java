package com.wellpoint.mde.constants;

import java.util.Arrays;
import java.util.List;

public enum RprtGrpEnum {

	RPTGRPTYPE("RPTGRPTYPE"),
	RPTGRPSTAT("RPTGRPSTAT"),
	RPTGRPNAME("RPTGRPNAME"),
	RPTGRPID("RPTGRPID"),
	RPTPRVPROGID("RPTPRVPROGID"),
	RPTGRPREL("RPTGRPREL"),
	
	ALTSRCID("ALTSRCID"),
	RPTRL("RPTRL"),
	RPTGP("RPTGP"),
	
	DEFAULT("DEFAULT");
	
	private String value;
	
	/**
	 * Instantiates a new BusEnum enum.
	 *
	 * @param value the value
	 */
	private RprtGrpEnum(final String value) {
		this.value = value;
	}	
	
	/**
	 * Gets the BusEnum constants.
	 *
	 * @return the BusEnum constants
	 */
	public static List<RprtGrpEnum> getInitiateConstants() {
		return Arrays.asList(RprtGrpEnum.values());
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
	public static RprtGrpEnum getInitiateEnum(final String value) {
		RprtGrpEnum returnValue = DEFAULT;
		for (RprtGrpEnum rprtGrpEnum : values()) {
			if (rprtGrpEnum.value.equals(value)) {
				returnValue = rprtGrpEnum;
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
	public static RprtGrpEnum getInitiateEnumIgnoreCase(final String value) {
		RprtGrpEnum returnValue = DEFAULT;
		String ignoreCase = value.toUpperCase();
		for (RprtGrpEnum rprtGrpEnum : values()) {
			if (rprtGrpEnum.value.equals(ignoreCase)) {
				returnValue = rprtGrpEnum;
				break;
			}
		}
		return returnValue;
	}
}
