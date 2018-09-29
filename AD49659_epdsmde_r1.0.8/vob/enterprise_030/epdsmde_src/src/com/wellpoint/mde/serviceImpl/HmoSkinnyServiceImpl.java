package com.wellpoint.mde.serviceImpl;

import madison.mpi.MemRow;

import com.wellpoint.mde.constants.HmoEnum;
import com.wellpoint.mde.service.HmoSkinnyService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class HmoSkinnyServiceImpl extends AbstractServiceImpl implements HmoSkinnyService{

	@Override
	public String getSegmentDataforWCON(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = HmoEnum.WCON.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getCDCString(memRow, "DFCDC_evtctime"),
						Long.toString(entRecNum), l_memIdNum,
						ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd), ExtMemgetIxnUtils.getString(memRow, "md5key"),
						ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
						ExtMemgetIxnUtils.getNDelimiters(10), ExtMemgetIxnUtils.getString(memRow, "relmemidnum"),
						ExtMemgetIxnUtils.getString(memRow, "reltype"), ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"), 
						ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), ExtMemgetIxnUtils.getString(memRow, "termrsn"),
						ExtMemgetIxnUtils.getString(memRow, "locationcd"), ExtMemgetIxnUtils.getString(memRow, "pcpspecid"),
						ExtMemgetIxnUtils.getString(memRow, "pcptaxid"), ExtMemgetIxnUtils.getString(memRow, "legacyid"),
						ExtMemgetIxnUtils.getString(memRow, "relorgmemidnum"), ExtMemgetIxnUtils.getString(memRow, "relorgsrccode"),
						ExtMemgetIxnUtils.getString(memRow, "relorgreltype"), ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt"), ExtMemgetIxnUtils.getString(memRow, "enrolovrrideind"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "enroleffectdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "enroltermdt"),
						ExtMemgetIxnUtils.getString(memRow, "hmocntrctcd"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt"), ExtMemgetIxnUtils.getString(memRow, "hmocntrcttermrsn"),
						ExtMemgetIxnUtils.getString(memRow, "hmocntrctcapttn"), ExtMemgetIxnUtils.getString(memRow, "cntrctacceptpats"),
						ExtMemgetIxnUtils.getString(memRow, "cntrctenrollprefagelo"), ExtMemgetIxnUtils.getString(memRow, "cntrctenrollprefagehi"),
						ExtMemgetIxnUtils.getString(memRow, "cntrctenrollprefgndr"), ExtMemgetIxnUtils.getString(memRow, "cntrctenrollprefobsttrcs"),
						ExtMemgetIxnUtils.getString(memRow, "cntrctmemcapcnt"), ExtMemgetIxnUtils.getString(memRow, "cntrctmemcnt"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "cntrctmemuptdt"), ExtMemgetIxnUtils.getString(memRow, "cntrctpmgclaims"),
						ExtMemgetIxnUtils.getString(memRow, "cntrctmixercd"), ExtMemgetIxnUtils.getString(memRow, "cntrctdirdisplayind"),
						ExtMemgetIxnUtils.getString(memRow, "cntrctspclid"), ExtMemgetIxnUtils.getString(memRow, "nwhmoownerid"),
						ExtMemgetIxnUtils.getString(memRow, "hmopgmcd1"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt1"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt1"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn1"),
						ExtMemgetIxnUtils.getString(memRow, "hmopgmcd2"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt2"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt2"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn2"),
						ExtMemgetIxnUtils.getString(memRow, "hmopgmcd3"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt3"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt3"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn3"),
						ExtMemgetIxnUtils.getString(memRow, "hmopgmcd4"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt4"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt4"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn4"),
						ExtMemgetIxnUtils.getString(memRow, "hmopgmcd5"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt5"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt5"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn5"),
						ExtMemgetIxnUtils.getString(memRow, "relmemsrccode"), ExtMemgetIxnUtils.getNDelimiters(28));
		return segmentData;
	}

	@Override
	public String getSegmentDataforWREL(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = HmoEnum.WREL.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getCDCString(memRow, "DFCDC_evtctime"),
						Long.toString(entRecNum), l_memIdNum,
						ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd), getString(memRow, "relmemidnum"),
						ExtMemgetIxnUtils.getString(memRow, "reltype"), ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
						ExtMemgetIxnUtils.getDateAsString(memRow,"termdt"), ExtMemgetIxnUtils.getString(memRow, "termrsn"),
						ExtMemgetIxnUtils.getString(memRow,"relattrval1"), ExtMemgetIxnUtils.getString(memRow,"relattrval2"),
						ExtMemgetIxnUtils.getString(memRow,"relattrval3"), ExtMemgetIxnUtils.getDateAsString(memRow,"pcptaxideffdt"),
						ExtMemgetIxnUtils.getDateAsString(memRow,"pcptaxidtrmdt"), ExtMemgetIxnUtils.getString(memRow,"pcptaxidtrmrsn"),
						ExtMemgetIxnUtils.getString(memRow,"legacyid"), ExtMemgetIxnUtils.getString(memRow,"relorgmemidnum"),
						ExtMemgetIxnUtils.getString(memRow,"relorgsrccode"), ExtMemgetIxnUtils.getString(memRow,"relorgreltype"),
						ExtMemgetIxnUtils.getDateAsString(memRow,"relorgeffectdt"), 
						ExtMemgetIxnUtils.getDateAsString(memRow,"relorgtermdt"), ExtMemgetIxnUtils.getString(memRow,"md5key"),
						ExtMemgetIxnUtils.getString(memRow,"mds5addrtype"), ExtMemgetIxnUtils.getString(memRow,"primpractind"),
						ExtMemgetIxnUtils.getDateAsString(memRow,"mds5addreffectdt"), ExtMemgetIxnUtils.getDateAsString(memRow,"addrtermdt"),
						ExtMemgetIxnUtils.getString(memRow,"addrtermrsn"), ExtMemgetIxnUtils.getString(memRow,"stline1"),
						ExtMemgetIxnUtils.getString(memRow,"stline2"), ExtMemgetIxnUtils.getString(memRow,"stline3"),
						ExtMemgetIxnUtils.getString(memRow,"city"), ExtMemgetIxnUtils.getString(memRow,"state"),
						ExtMemgetIxnUtils.getString(memRow,"zip"), ExtMemgetIxnUtils.getString(memRow,"zipextension"),
						ExtMemgetIxnUtils.getString(memRow,"countycd"), ExtMemgetIxnUtils.getString(memRow,"country"),
						ExtMemgetIxnUtils.getString(memRow, "phnumber"), ExtMemgetIxnUtils.getString(memRow, "fxnumber"),
						ExtMemgetIxnUtils.getString(memRow, "pcpspecialistid"), ExtMemgetIxnUtils.getString(memRow, "svcareatypcd"),
						ExtMemgetIxnUtils.getString(memRow, "cntybdraddrind"), ExtMemgetIxnUtils.getString(memRow, "relmemsrccode"),
						ExtMemgetIxnUtils.getNDelimiters(28));
		return segmentData;
	}
	
	/*public String getSegmentDataforWNET(MemRow memRow, long entRecNum)
	throws Exception {
 getMemHeadValues(memRow);
 outputType = HmoEnum.WNET.getValue();
 segmentData = ExtMemgetIxnUtils.appendStr(outputType, getCDCString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), l_memIdNum,
				l_strSrcCd, ExtMemgetIxnUtils.getString(memRow, "md5key"),
				ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
				ExtMemgetIxnUtils.getNDelimiters(10), ExtMemgetIxnUtils.getString(memRow, "relmemidnum"),
				ExtMemgetIxnUtils.getString(memRow, "reltype"), ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"), 
				ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), ExtMemgetIxnUtils.getString(memRow, "termrsn"),
				ExtMemgetIxnUtils.getString(memRow, "locationcd"), ExtMemgetIxnUtils.getString(memRow, "pcpspecid"),
				ExtMemgetIxnUtils.getString(memRow, "pcptaxid"), ExtMemgetIxnUtils.getString(memRow, "legacyid"),
				ExtMemgetIxnUtils.getString(memRow, "relorgmemidnum"), ExtMemgetIxnUtils.getString(memRow, "relorgsrccode"),
				ExtMemgetIxnUtils.getString(memRow, "relorgreltype"), ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt"), ExtMemgetIxnUtils.getString(memRow, "enrolovrrideind"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "enroleffectdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "enroltermdt"),
				ExtMemgetIxnUtils.getString(memRow, "hmocntrctcd"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt"), ExtMemgetIxnUtils.getString(memRow, "hmocntrcttermrsn"),
				ExtMemgetIxnUtils.getString(memRow, "hmocntrctcapttn"), ExtMemgetIxnUtils.getString(memRow, "cntrctacceptpats"),
				ExtMemgetIxnUtils.getString(memRow, "cntrctenrollprefagelo"), ExtMemgetIxnUtils.getString(memRow, "cntrctenrollprefagehi"),
				ExtMemgetIxnUtils.getString(memRow, "cntrctenrollprefgndr"), ExtMemgetIxnUtils.getString(memRow, "cntrctenrollprefobsttrcs"),
				ExtMemgetIxnUtils.getString(memRow, "cntrctmemcapcnt"), ExtMemgetIxnUtils.getString(memRow, "cntrctmemcnt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "cntrctmemuptdt"), ExtMemgetIxnUtils.getString(memRow, "cntrctpmgclaims"),
				ExtMemgetIxnUtils.getString(memRow, "cntrctmixercd"), ExtMemgetIxnUtils.getString(memRow, "cntrctdirdisplayind"),
				ExtMemgetIxnUtils.getString(memRow, "cntrctspclid"), ExtMemgetIxnUtils.getString(memRow, "nwhmoownerid"),
				ExtMemgetIxnUtils.getString(memRow, "hmopgmcd1"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt1"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt1"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn1"),
				ExtMemgetIxnUtils.getString(memRow, "hmopgmcd2"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt2"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt2"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn2"),
				ExtMemgetIxnUtils.getString(memRow, "hmopgmcd3"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt3"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt3"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn3"),
				ExtMemgetIxnUtils.getString(memRow, "hmopgmcd4"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt4"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt4"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn4"),
				ExtMemgetIxnUtils.getString(memRow, "hmopgmcd5"), ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt5"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt5"), ExtMemgetIxnUtils.getString(memRow, "hmopgmtermrsn5"),
				ExtMemgetIxnUtils.getString(memRow, "relmemsrccode"), ExtMemgetIxnUtils.getNDelimiters(28));
return segmentData;
}*/

	
}
