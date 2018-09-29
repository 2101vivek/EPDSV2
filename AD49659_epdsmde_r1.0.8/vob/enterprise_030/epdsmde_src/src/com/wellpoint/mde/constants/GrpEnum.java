package com.wellpoint.mde.constants;

import java.util.Arrays;
import java.util.List;

public enum GrpEnum {

	GPMDE("GPMDE"),
	GRPNAME("GRPNAME"),
	GRPTYPE("GRPTYPE"),
	GRPID("GRPID"),
	GRPSTAT("GRPSTAT"),
	GRPOWNNAME("GRPOWNNAME"),
	GRPLOCGROUP("GRPLOCGROUP"),
	GRPLOCGRPTYP("GRPLOCGRPTYP"),
	GRPCONTRACT("GRPCONTRACT"),
	GRPFAMSWEEP("GRPFAMSWEEP"),
	GRPMKTBU("GRPMKTBU"),
	GRPMEMSUP("GRPMEMSUP"),
	GRPFUNDTYPE("GRPFUNDTYPE"),
	GRPNETARNGMT("GRPNETARNGMT"),
	GRPPRODTYPE("GRPPRODTYPE"),
	GRPSVCLOCZIP("GRPSVCLOCZIP"),
	GRPSVCEMPGRP("GRPSVCEMPGRP"),
	GRPALTID("GRPALTID"),
	GRPRMB("GRPRMB"),
	GRPNTWRK("GRPNTWRK"),
	GRPGBDCD("GRPGBDCD"),
	GRPROLLID("GRPROLLID"),
	
	ALTSRCID("ALTSRCID"),
	GALT("GALT"),
	GNET("GNET"),
	GRMB("GRMB"),
	GRP("GRP"),
	GRPPGM("GRPPGM"),
	GALTRO("GALTRO"),
	DEFAULT("DEFAULT");
	
	private String value;
	
	/**
	 * Instantiates a new BusEnum enum.
	 *
	 * @param value the value
	 */
	private GrpEnum(final String value) {
		this.value = value;
	}	
	
	/**
	 * Gets the BusEnum constants.
	 *
	 * @return the BusEnum constants
	 */
	public static List<GrpEnum> getInitiateConstants() {
		return Arrays.asList(GrpEnum.values());
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
	public static GrpEnum getInitiateEnum(final String value) {
		GrpEnum returnValue = DEFAULT;
		for (GrpEnum grpEnum : values()) {
			if (grpEnum.value.equals(value)) {
				returnValue = grpEnum;
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
	public static GrpEnum getInitiateEnumIgnoreCase(final String value) {
		GrpEnum returnValue = DEFAULT;
		String ignoreCase = value.toUpperCase();
		for (GrpEnum grpEnum : values()) {
			if (grpEnum.value.equals(ignoreCase)) {
				returnValue = grpEnum;
				break;
			}
		}
		return returnValue;
	}
}
