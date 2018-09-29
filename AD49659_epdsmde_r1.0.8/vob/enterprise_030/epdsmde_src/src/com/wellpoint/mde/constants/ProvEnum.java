package com.wellpoint.mde.constants;

import java.util.Arrays;
import java.util.List;

public enum ProvEnum {

	EPDSV2("EPDSV2"),
	PROVRMB("PROVRMB"),
	PROVNTWRK("PROVNTWRK"),
	PROVRSKWTHLD("PROVRSKWTHLD"),
	PROVTYPCD("PROVTYPCD"),
	PROVINACTIVE("PROVINACTIVE"),
	PROVNAMEEXT("PROVNAMEEXT"),
	PROVACESLGCY("PROVACESLGCY"),
	PROVCPFLGCY("PROVCPFLGCY"),
	PROVCPMFLGCY("PROVCPMFLGCY"),
	PROVEPSBLGCY("PROVEPSBLGCY"),
	PROVQCARELGY("PROVQCARELGY"),
	PROVEDUCTN("PROVEDUCTN"),
	PROVTTLSFX("PROVTTLSFX"),
	PROVPADRLGCY("PROVPADRLGCY"),
	PRVSN("PRVSN"),
	PROVREL("PROVREL"),
	PROVTXNMY("PROVTXNMY"),
	PROVGRPMEM("PROVGRPMEM"),
	PROVBRDCRT("PROVBRDCRT"),
	PROVSPTYTXNM("PROVSPTYTXNM"),
	PROVCLAIMACT("PROVCLAIMACT"),
	PROVALTSYSID("PROVALTSYSID"),
	PRVFACHSANET("PRVFACHSANET"),
	PRVDIRDISIND("PRVDIRDISIND"),
	NPI("NPI"),
	UPIN("UPIN"),
	DEAID("DEAID"),
	MEDICARE("MEDICARE"),
	MEDICAID("MEDICAID"),
	ENCLARITYID("ENCLARITYID"),
	PROVLICENSE("PROVLICENSE"),
	PRVALTIDSPEC("PRVALTIDSPEC"),
	
	PROVCATEGORY("PROVCATEGORY"),
	DOB("DOB"),
	GENDER("GENDER"),
	PRACTYPE("PRACTYPE"),
	SSN("SSN"),
	PROVETHNIC("PROVETHNIC"),
	PRVNPIELIG("PRVNPIELIG"),
	PARIND("PARIND"),
	PRVCACTUSFCL("PRVCACTUSFCL"),
	PRVGBDCD("PRVGBDCD"),
	PROVATTEST("PROVATTEST"),
	CREDSTATUS("CREDSTATUS"),
	PROVALIAS("PROVALIAS"),
	PROVNOTE("PROVNOTE"),
	PROVASANCTN("PROVASANCTN"),
	PRVSANCCLACT("PRVSANCCLACT"),
	PROVLANG("PROVLANG"),
	OFFSTAFFLANG("OFFSTAFFLANG"),
	PROVSPECPRG("PROVSPECPRG"),
	PROVSPLTYSVC("PROVSPLTYSVC"),
	PROVDIST("PROVDIST"),
	PROVRANK("PROVRANK"),
	PROVPRCSVC("PROVPRCSVC"),
	PROVEXPTAREA("PROVEXPTAREA"),
	SERVCONTACT("SERVCONTACT"),
	CORRCONTACT("CORRCONTACT"),
	PROVCSACNT("PROVCSACNT"),
	PRVPAYINCNTC("PRVPAYINCNTC"),
	PRVCAPCKCNTC("PRVCAPCKCNTC"),
	PROVCONTACT("PROVCONTACT"),
	PROV1099CNT("PROV1099CNT"),
	PROVBILLCNT("PROVBILLCNT"),
	PROVCAPCNT("PROVCAPCNT"),
	PROVCNTNA("PROVCNTNA"),
	PROVDBANAME("PROVDBANAME"),
	OFFPATLIM("OFFPATLIM"),
	PROVSCHED("PROVSCHED"),
	OFFTECH("OFFTECH"),
	OFFACCESS("OFFACCESS"),
	CORRLOCATION("CORRLOCATION"),
	SERVLOCATION("SERVLOCATION"),
	PROV1099ADDR("PROV1099ADDR"),
	PROVBILLADDR("PROVBILLADDR"),
	PROVCAPADDR("PROVCAPADDR"),
	PROVCSAADDR("PROVCSAADDR"),
	PROVADDRNA("PROVADDRNA"),
	PRVCAPCKADDR("PRVCAPCKADDR"),
	PRVPAYINADDR("PRVPAYINADDR"),
	PRVHMORELADR("PRVHMORELADR"),
	PRVFACSTERE("PRVFACSTERE"),
	PROVROLLID("PROVROLLID"),
	PROVREGN("PROVREGN"),
	PRVBUSENTREL("PRVBUSENTREL"),
	PROVGRPCTRCT("PROVGRPCTRCT"),
	PROVWREL("PROVWREL"),
	PRVGRPNET("PRVGRPNET"),
	PRVHMOCNTREL("PRVHMOCNTREL"),
	PROVWNET("PROVWNET"),
	INDDATAMANG("INDDATAMANG"),
	/*--Segment Name--*/
	
	QCARE_APADR("QCARE_APADR"),
	NASCOPCNTC("NASCOPCNTC"),
	APDM("APDM"),
	APADR("APADR"),
	APCNTC("APCNTC"),
	APGREL("APGREL"),
	APREL("APREL"),
	PNET("PNET"),
	PRMB("PRMB"),
	
	ALTSRCID("ALTSRCID"),
	APALT("APALT"),
	APCLMACT("APCLMACT"),
	APREM("APREM"),
	APTTL("APTTL"),
	APSPT("APSPT"),
	APTXN("APTXN"),
	GNET("GNET"),
	GRMB("GRMB"),
	PADR("PADR"),
	PALT("PALT"),
	PALTROL("PALTROL"),
	PAOF("PAOF"),
	PATTS("PATTS"),
	PALIAS("PALIAS"),
	PEDU("PEDU"),
	PLANG("PLANG"),
	PTTL("PTTL"),
	PBREL("PBREL"),
	PCLMACT("PCLMACT"),
	PCNTC("PCNTC"),
	PCRED("PCRED"),
	PDBA("PDBA"),
	PDSTC("PDSTC"),
	PGREL("PGREL"),
	PNOTE("PNOTE"),
	POCON("POCON"),
	POFACS("POFACS"),
	POFSCH("POFSCH"),
	POFSR("POFSR"),
	POFSRR("POFSRR"),
	POFTCH("POFTCH"),
	POT("POT"),
	PPGM("PPGM"),
	PPPRF("PPPRF"),
	PREL("PREL"),
	PREM("PREM"),
	PRF("PRF"),
	PRGN("PRGN"),
	PRNK("PRNK"),
	PSANC("PSANC"),
	PSNAC("PSNAC"),
	PSPT("PSPT"),
	PSTFLANG("PSTFLANG"),
	PTXN("PTXN"),
	PTAX("PTAX"),
	PWTH("PWTH"),
	WCON("WCON"),
	WREL("WREL"),
	WNET("WNET"),
	
	/*--End of Segment Name--*/
	
	DEFAULT("DEFAULT");
	
	private String value;
	
	/**
	 * Instantiates a new MemGetEnum enum.
	 *
	 * @param value the value
	 */
	private ProvEnum(final String value) {
		this.value = value;
	}	
	
	/**
	 * Gets the MemGetEnum constants.
	 *
	 * @return the MemGetEnum constants
	 */
	public static List<ProvEnum> getInitiateConstants() {
		return Arrays.asList(ProvEnum.values());
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
	public static ProvEnum getInitiateEnum(final String value) {
		ProvEnum returnValue = DEFAULT;
		for (ProvEnum provEnum : values()) {
			if (provEnum.value.equals(value)) {
				returnValue = provEnum;
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
	public static ProvEnum getInitiateEnumIgnoreCase(final String value) {
		ProvEnum returnValue = DEFAULT;
		String ignoreCase = value.toUpperCase();
		for (ProvEnum provEnum : values()) {
			if (provEnum.value.equals(ignoreCase)) {
				returnValue = provEnum;
				break;
			}
		}
		return returnValue;
	}
}
