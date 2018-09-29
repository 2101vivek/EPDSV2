package com.wellpoint.mde.constants;

import java.util.Arrays;
import java.util.List;

public enum NetEnum {

	NETPRNTREL("NETPRNTREL"),
	NETRMBREL("NETRMBREL"),
	NETNAME("NETNAME"),
	NETLEGNAME("NETLEGNAME"),
	NETLVLTYPE("NETLVLTYPE"),
	NETOWNSRC("NETOWNSRC"),
	PCPNETTYPCD("PCPNETTYPCD"),
	NATCTCTIND("NATCTCTIND"),
	NETRENTIND("NETRENTIND"),
	NETIDCODE("NETIDCODE"),
	NETAGREETYPE("NETAGREETYPE"),
	NETCNTRCTST("NETCNTRCTST"),
	NETRSTCTTYPE("NETRSTCTTYPE"),
	NETFOCUSTYPE("NETFOCUSTYPE"),
	NETPRODTYPE("NETPRODTYPE"),
	NETSTATUS("NETSTATUS"),
	NETPARIND("NETPARIND"),
	NETSTATCODE("NETSTATCODE"),
	NETSTALIND("NETSTALIND"),
	
	
	MNET("MNET"),
	MNTRL("MNTRL"),
	MNTRM("MNTRM"),
	
	DEFAULT("DEFAULT");
	
	private String value;
	
	/**
	 * Instantiates a new MemGetEnum enum.
	 *
	 * @param value the value
	 */
	private NetEnum(final String value) {
		this.value = value;
	}	
	
	/**
	 * Gets the MemGetEnum constants.
	 *
	 * @return the MemGetEnum constants
	 */
	public static List<NetEnum> getInitiateConstants() {
		return Arrays.asList(NetEnum.values());
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
	public static NetEnum getInitiateEnum(final String value) {
		NetEnum returnValue = DEFAULT;
		for (NetEnum netEnum : values()) {
			if (netEnum.value.equals(value)) {
				returnValue = netEnum;
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
	public static NetEnum getInitiateEnumIgnoreCase(final String value) {
		NetEnum returnValue = DEFAULT;
		String ignoreCase = value.toUpperCase();
		for (NetEnum netEnum : values()) {
			if (netEnum.value.equals(ignoreCase)) {
				returnValue = netEnum;
				break;
			}
		}
		return returnValue;
	}
}
