package com.wellpoint.mde.serviceImpl;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.ProvEnum;
import com.wellpoint.mde.service.ProviderSkinnyService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;


public class ProvSkinnyServiceImpl extends AbstractServiceImpl implements ProviderSkinnyService{

	private String srcCode = strBlank;
	
	public String getSrcCode() {
		return srcCode;
	}

	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
	}
	
	@Override
	public String getSegmentDataforWCON(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.WCON.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getCDCString(memRow, "DFCDC_evtctime"),
						Long.toString(entRecNum), ExtMemgetIxnUtils.getString(memRow, "relmemidnum"),
						ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "relmemsrccode")), ExtMemgetIxnUtils.getString(memRow, "md5key"),
						ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
						ExtMemgetIxnUtils.getNDelimiters(10), l_memIdNum,
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
						l_strSrcCd, ExtMemgetIxnUtils.getNDelimiters(28));
		return segmentData;
	}

	@Override
	public String getSegmentDataforWREL(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.WREL.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getCDCString(memRow, "DFCDC_evtctime"),
						Long.toString(entRecNum), getString(memRow, "relmemidnum"),
						ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "relmemsrccode")), l_memIdNum,
						ExtMemgetIxnUtils.getString(memRow, "reltype"), ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), ExtMemgetIxnUtils.getString(memRow, "termrsn"),
						ExtMemgetIxnUtils.getString(memRow, "relattrval1"), ExtMemgetIxnUtils.getString(memRow, "relattrval2"),
						ExtMemgetIxnUtils.getString(memRow, "relattrval3"), ExtMemgetIxnUtils.getDateAsString(memRow, "pcptaxideffdt"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "pcptaxidtrmdt"), ExtMemgetIxnUtils.getString(memRow, "pcptaxidtrmrsn"),
						ExtMemgetIxnUtils.getString(memRow, "legacyid"), ExtMemgetIxnUtils.getString(memRow, "relorgmemidnum"),
						ExtMemgetIxnUtils.getString(memRow, "relorgsrccode"), ExtMemgetIxnUtils.getString(memRow, "relorgreltype"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt"),
						ExtMemgetIxnUtils.getString(memRow, "md5key"), ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"),
						ExtMemgetIxnUtils.getString(memRow, "primpractind"), ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
						ExtMemgetIxnUtils.getDateAsString(memRow, "addrtermdt"), ExtMemgetIxnUtils.getString(memRow, "addrtermrsn"),
						ExtMemgetIxnUtils.getString(memRow, "stline1"), ExtMemgetIxnUtils.getString(memRow, "stline2"),
						ExtMemgetIxnUtils.getString(memRow, "stline3"), ExtMemgetIxnUtils.getString(memRow, "city"),
						ExtMemgetIxnUtils.getString(memRow, "state"), ExtMemgetIxnUtils.getString(memRow, "zip"),
						ExtMemgetIxnUtils.getString(memRow, "zipextension"), ExtMemgetIxnUtils.getString(memRow, "countycd"),
						ExtMemgetIxnUtils.getString(memRow, "country"), ExtMemgetIxnUtils.getString(memRow, "phnumber"),
						ExtMemgetIxnUtils.getString(memRow, "fxnumber"), ExtMemgetIxnUtils.getString(memRow, "pcpspecialistid"),
						ExtMemgetIxnUtils.getString(memRow, "svcareatypcd"), ExtMemgetIxnUtils.getString(memRow, "cntybdraddrind"),
						l_strSrcCd, ExtMemgetIxnUtils.getNDelimiters(28));
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPREL(MemRow memRow, long entRecNum)
	throws Exception {
     getMemHeadValues(memRow);
	 outputType = ProvEnum.PREL.getValue();
	 segmentData = ExtMemgetIxnUtils.appendStr(outputType, getCDCString(memRow, "DFCDC_evtctime"),Long.toString(entRecNum), l_memIdNum,
			 			l_strSrcCd, ExtMemgetIxnUtils.getString(memRow, "md5key"), ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
			 			ExtMemgetIxnUtils.getNDelimiters(10),ExtMemgetIxnUtils.getString(memRow, "phnumber"),ExtMemgetIxnUtils.getString(memRow, "fxnumber"),
			 			ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt"),ExtMemgetIxnUtils.getDateAsString(memRow, "reladdrtermdt"),
			 			ExtMemgetIxnUtils.getString(memRow, "reladdrtermrsn"),ExtMemgetIxnUtils.getString(memRow, "relmemidnum"),ExtMemgetIxnUtils.getString(memRow, "reltype"),
			 			ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"),ExtMemgetIxnUtils.getString(memRow, "termrsn"),
			 			ExtMemgetIxnUtils.getString(memRow, "relattrval1"),ExtMemgetIxnUtils.getString(memRow, "relattrval2"),ExtMemgetIxnUtils.getString(memRow, "relattrval3"),
			 			ExtMemgetIxnUtils.getString(memRow, "relattrval4"),ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt"),
			 			ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt"),ExtMemgetIxnUtils.getString(memRow, "relmemsrccode"));
	        return segmentData; 
	}
	
	@Override
	public String getSegmentDataforPPPRF(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PPPRF.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType,getCDCString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), l_memIdNum, l_strSrcCd,
				ExtMemgetIxnUtils.getString(memRow, "md5key"), 
				ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
				ExtMemgetIxnUtils.getNDelimiters(10), ExtMemgetIxnUtils.getString(memRow, "patlimittype"),
				ExtMemgetIxnUtils.getString(memRow, "patlimitval"), ExtMemgetIxnUtils.getString(memRow, "patlimitvaltypefrm"),
				ExtMemgetIxnUtils.getString(memRow, "pallimitvalto"), ExtMemgetIxnUtils.getString(memRow, "patlimitvaltypeto"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "patlimiteffdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "patlimittermdt"),
				ExtMemgetIxnUtils.getString(memRow, "patlimittermrsn"));
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPNET(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PNET.getValue();
		srcCode =  ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType,getCDCString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), l_memIdNum, l_strSrcCd,
				ExtMemgetIxnUtils.getString(memRow, "md5key"), 
				ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
				ExtMemgetIxnUtils.getNDelimiters(10), ExtMemgetIxnUtils.getString(memRow, "relatedid"),
				ExtMemgetIxnUtils.getString(memRow, "reltype"), ExtMemgetIxnUtils.getDateAsString(memRow, "releffdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "reltermdt"), ExtMemgetIxnUtils.getString(memRow, "reltermrsn"),
				ExtMemgetIxnUtils.getString(memRow, "networkid"), ExtMemgetIxnUtils.getString(memRow, "networkidtype"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "nweffectdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "nwtermdt"),
				ExtMemgetIxnUtils.getString(memRow, "nwtermrsn"), ExtMemgetIxnUtils.getString(memRow, "nwacceptpats"),
				ExtMemgetIxnUtils.getString(memRow, "nwacceptmdcdpats"), ExtMemgetIxnUtils.getString(memRow, "nwacceptmdcrpats"),
				ExtMemgetIxnUtils.getString(memRow, "nwpatgenderpref"), ExtMemgetIxnUtils.getString(memRow, "nwpatagepreffrom"),
				ExtMemgetIxnUtils.getString(memRow, "nwpatageprefto"), ExtMemgetIxnUtils.getString(memRow, "nwparind"),
				ExtMemgetIxnUtils.getString(memRow, "nwpcpspectype"), ExtMemgetIxnUtils.getString(memRow, "nwtimeoffiledays"),
				ExtMemgetIxnUtils.getString(memRow, "nwdirdisplayind"), ExtMemgetIxnUtils.getString(memRow, "nwpcpcurrmemcnt"),
				ExtMemgetIxnUtils.getString(memRow, "nwpcpmemcapcnt"), ExtMemgetIxnUtils.getString(memRow, "nwownerid"),
				ExtMemgetIxnUtils.getString(memRow, "emgphynparstind"), ExtMemgetIxnUtils.getString(memRow, "patlgypartstind"),
				ExtMemgetIxnUtils.getString(memRow, "anestpartstind"), ExtMemgetIxnUtils.getString(memRow, "radlgypartstind"),
				ExtMemgetIxnUtils.getString(memRow, "specialty1"), ExtMemgetIxnUtils.getDateAsString(memRow, "specialty1effdt"),
				ExtMemgetIxnUtils.getString(memRow, "specialty2"), ExtMemgetIxnUtils.getDateAsString(memRow, "specialty2effdt"),
				ExtMemgetIxnUtils.getString(memRow, "specialty3"), ExtMemgetIxnUtils.getDateAsString(memRow, "specialty3effdt"),
				ExtMemgetIxnUtils.getString(memRow, "specialty4"), ExtMemgetIxnUtils.getDateAsString(memRow, "specialty4effdt"),
				ExtMemgetIxnUtils.getString(memRow, "specialty5"), ExtMemgetIxnUtils.getDateAsString(memRow, "specialty5effdt"),
				ExtMemgetIxnUtils.getString(memRow, "acespar"), ExtMemgetIxnUtils.getString(memRow, "acesaqiind"),
				ExtMemgetIxnUtils.getString(memRow, "acesaqipercent"), ExtMemgetIxnUtils.getDateAsString(memRow, "aprveffdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "aprvenddt"), ExtMemgetIxnUtils.getString(memRow, "aprvind"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "pcpneteffdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "pcpnetenddt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "pcpmenteffdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "pcpmentenddt"),
				ExtMemgetIxnUtils.getString(memRow, "direcdispind"), ExtMemgetIxnUtils.getString(memRow, "netparcd"),
				ExtMemgetIxnUtils.getString(memRow, "agecattype"), /*Cff 3.8 changes*/ExtMemgetIxnUtils.getString(memRow, "relatedid"),
				ExtMemgetIxnUtils.getString(memRow, "relmemsrccode"),ExtMemgetIxnUtils.getString(memRow,"dirdisindflg"),
				ExtMemgetIxnUtils.getString(memRow, "nwacceptflg"));
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPRMB(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PRMB.getValue();
		srcCode =  ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getCDCString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), l_memIdNum,l_strSrcCd,ExtMemgetIxnUtils.getString(memRow, "md5key"),
				ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"), 
				ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
				ExtMemgetIxnUtils.getNDelimiters(10), ExtMemgetIxnUtils.getString(memRow, "relatedid"),
				ExtMemgetIxnUtils.getString(memRow, "reltype"),ExtMemgetIxnUtils.getDateAsString(memRow, "releffdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "reltermdt"),ExtMemgetIxnUtils.getString(memRow, "reltermrsn"),
				ExtMemgetIxnUtils.getString(memRow, "rmbarrangeid"), ExtMemgetIxnUtils.getString(memRow, "rmbarrangeidtype"),
				ExtMemgetIxnUtils.getString(memRow, "agreeid"), ExtMemgetIxnUtils.getString(memRow, "agreeidtype"),
				ExtMemgetIxnUtils.getString(memRow, "panelid"), ExtMemgetIxnUtils.getString(memRow, "panelidtype"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangeeffdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangetermdt"), ExtMemgetIxnUtils.getString(memRow, "rmbarrangetermrsn"),
				ExtMemgetIxnUtils.getString(memRow, "wgscontcode"), ExtMemgetIxnUtils.getString(memRow, "networkid"),
				ExtMemgetIxnUtils.getString(memRow, "networkidtype"), ExtMemgetIxnUtils.getString(memRow, "ctrctversion"),
				ExtMemgetIxnUtils.getString(memRow, "ctrctinclusion"), ExtMemgetIxnUtils.getString(memRow, "reimbtiercode"),
				ExtMemgetIxnUtils.getString(memRow, "nwffscaptype"), ExtMemgetIxnUtils.getString(memRow, "emgphynparstind"),
				ExtMemgetIxnUtils.getString(memRow, "patlgypartstind"), ExtMemgetIxnUtils.getString(memRow, "anestpartstind"),
				ExtMemgetIxnUtils.getString(memRow, "radlgypartstind"), ExtMemgetIxnUtils.getString(memRow, "specialty1"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "speciality1effectdt"), ExtMemgetIxnUtils.getString(memRow, "specialty2"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "speciality2effectdt"), ExtMemgetIxnUtils.getString(memRow, "specialty3"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "speciality3effectdt"), ExtMemgetIxnUtils.getString(memRow, "specialty4"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "speciality4effectdt"), ExtMemgetIxnUtils.getString(memRow, "specialty5"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "speciality5effectdt"), ExtMemgetIxnUtils.getString(memRow, "wmsndbgrpind"),
				ExtMemgetIxnUtils.getString(memRow, "wmsndbctrlcd"), ExtMemgetIxnUtils.getString(memRow, "wmsndbctrlvarn"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "wmsndblastcrddt"), ExtMemgetIxnUtils.getString(memRow, "wmsndbstscd"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "wmsndbstsdt"), ExtMemgetIxnUtils.getString(memRow, "wmsndbtaxid"),
				ExtMemgetIxnUtils.getString(memRow, "acesspapipind"), ExtMemgetIxnUtils.getString(memRow, "acesspapipfrqind"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "acesspapipeffectdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "acesspapipenddt"),
				ExtMemgetIxnUtils.getString(memRow, "prmptpyind"), ExtMemgetIxnUtils.getString(memRow, "prmptpctdiscamt"),
				ExtMemgetIxnUtils.getString(memRow, "rmtmethcd"), ExtMemgetIxnUtils.getString(memRow, "facetscaptype"),
				ExtMemgetIxnUtils.getString(memRow, "facetsprefix"), ExtMemgetIxnUtils.getString(memRow, "facetscapreltype"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "facetscrprid"), ExtMemgetIxnUtils.getString(memRow, "facetsadjper"),				
				ExtMemgetIxnUtils.getString(memRow, "facetscapcycleid"), ExtMemgetIxnUtils.getString(memRow, "epdsv2ctrctdays"),
				ExtMemgetIxnUtils.getString(memRow, "directoryind"), ExtMemgetIxnUtils.getString(memRow, "dirdisplayopt"),
				ExtMemgetIxnUtils.getString(memRow, "acptnewptind"),/*Cff 3.8 changes*/ExtMemgetIxnUtils.getString(memRow, "relatedid"),ExtMemgetIxnUtils.getString(memRow, "relmemsrccode"));
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPSPT(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PSPT.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType,getCDCString(memRow, "DFCDC_evtctime"),Long.toString(entRecNum), l_memIdNum,
				l_strSrcCd,ExtMemgetIxnUtils.getString(memRow,"specialtycd"),ExtMemgetIxnUtils.getString(memRow,"primaryspec"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt"),ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt"),
				ExtMemgetIxnUtils.getString(memRow,"spectermrsn"),ExtMemgetIxnUtils.getString(memRow,"speclgcycode"));
		return segmentData; 
	}
}
