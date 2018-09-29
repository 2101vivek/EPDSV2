package com.wellpoint.mde.constants;

import java.util.Arrays;
import java.util.List;

public enum HmoEnum {

	HMOSTATUS("HMOSTATUS"),
	HMONAME("HMONAME"),
	HMOID("HMOID"),
	HMOSITEPRNT("HMOSITEPRNT"),
	HMOMNSATTYPE("HMOMNSATTYPE"),
	HMOIPAPMGTYP("HMOIPAPMGTYP"),
	PREVPCPSFX("PREVPCPSFX"),
	PREVSPCSFX("PREVSPCSFX"),
	HMOROUTEREG("HMOROUTEREG"),
	HMOSAFENET("HMOSAFENET"),
	HMONETADMIN("HMONETADMIN"),
	HMOCTRCTMGR("HMOCTRCTMGR"),
	HMOPROVSUMID("HMOPROVSUMID"),
	HMOPMPMIND("HMOPMPMIND"),
	AREAZIPTYPCD("AREAZIPTYPCD"),
	HMOSITECOPAY("HMOSITECOPAY"),
	HMOGRPDBA("HMOGRPDBA"),
	HMOSHAREDMGT("HMOSHAREDMGT"),
	HMOMEMREF("HMOMEMREF"),
	HMOSVCAREA("HMOSVCAREA"),
	HMOTAXID("HMOTAXID"),
	HMOSCHED("HMOSCHED"),
	HMOSVCTAB("HMOSVCTAB"),
	HMOENRPROT("HMOENRPROT"),
	HMOMEMROL("HMOMEMROL"),
	HMOADDR("HMOADDR"),
	HMOADRCNT("HMOADRCNT"),
	HMONOTE("HMONOTE"),
	HMOWREL("HMOWREL"),
	HMOCNTREL("HMOCNTREL"),
	HMOALTID("HMOALTID"),
	NPIHMO("NPIHMO"),
	HMOREGN("HMOREGN"),
	HMONET("HMONET"),
	HMOWPDM("HMOWPDM"),
	HMODATAMANG("HMODATAMANG"),
	/*--Segment Name--*/
	
	ALTSRCID("ALTSRCID"),
	WADR("WADR"),
	WALT("WALT"),
	WCNTC("WCNTC"),
	WENR("WENR"),
	WHM("WHM"),
	WMBREF("WMBREF"),
	WNOTE("WNOTE"),
	WROL("WROL"),
	WRGN("WRGN"),
	WSCH("WSCH"),
	WSHRMGN("WSHRMGN"),
	WSVAREA("WSVAREA"),
	WSVC("WSVC"),
	WTAX("WTAX"),
	WCON("WCON"),
	WREL("WREL"),
	WPDM("WPDM"),
	WNET("WNET"),
	
	/*--End of Segment Name--*/
	
	DEFAULT("DEFAULT");
	
	private String value;
	
	/**
	 * Instantiates a new MemGetEnum enum.
	 *
	 * @param value the value
	 */
	private HmoEnum(final String value) {
		this.value = value;
	}	
	
	/**
	 * Gets the MemGetEnum constants.
	 *
	 * @return the MemGetEnum constants
	 */
	public static List<HmoEnum> getInitiateConstants() {
		return Arrays.asList(HmoEnum.values());
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
	public static HmoEnum getInitiateEnum(final String value) {
		HmoEnum returnValue = DEFAULT;
		for (HmoEnum hmoEnum : values()) {
			if (hmoEnum.value.equals(value)) {
				returnValue = hmoEnum;
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
	public static HmoEnum getInitiateEnumIgnoreCase(final String value) {
		HmoEnum returnValue = DEFAULT;
		String ignoreCase = value.toUpperCase();
		for (HmoEnum hmoEnum : values()) {
			if (hmoEnum.value.equals(ignoreCase)) {
				returnValue = hmoEnum;
				break;
			}
		}
		return returnValue;
	}
}
