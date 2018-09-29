package com.wellpoint.mde.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.HmoEnum;
import com.wellpoint.mde.service.HmoService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class HmoServiceImpl extends AbstractServiceImpl implements HmoService{
	
	@Override
	public String getSegmentDataforWSHRMGN(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WSHRMGN.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2", getString(memRow, "attrval"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"), 
				ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"),
				getString(memRow, "termrsn"), getString(memRow, "DFCDC_evtinitiator"),
				getString(memRow, "DFCDC_evtctime"), getString(memRow, "DFCDC_mAudRecno"),
				ExtMemgetIxnUtils.getNDelimiters(11), srcCodesDelimited) ;
		
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWMBREF(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WMBREF.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2", getString(memRow, "attrval2"),
				getString(memRow, "attrval"), ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), getString(memRow, "termrsn"),
				getString(memRow, "DFCDC_evtinitiator"), getString(memRow, "DFCDC_evtctime"),
				getString(memRow, "DFCDC_mAudRecno"), ExtMemgetIxnUtils.getNDelimiters(11),
				srcCodesDelimited) ;
		return segmentData;
	}
	/* ADDED FOR WPDM */
	@Override
	public String getSegmentDataforWPDM(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WPDM.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2", getString(memRow, "attrval2"),
				getString(memRow, "attrval"), ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), getString(memRow, "termrsn"),
				getString(memRow, "DFCDC_evtinitiator"), getString(memRow, "DFCDC_evtctime"),
				getString(memRow, "DFCDC_mAudRecno"), ExtMemgetIxnUtils.getNDelimiters(11),
				srcCodesDelimited) ;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWSVAREA(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WSVAREA.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2", getString(memRow, "attrval"), 
				ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"), 
				ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), 
				getString(memRow, "termrsn"), getString(memRow, "DFCDC_evtinitiator"),
				getString(memRow, "DFCDC_evtctime"), getString(memRow, "DFCDC_mAudRecno"),
				ExtMemgetIxnUtils.getNDelimiters(11), srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWTAX(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WTAX.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2", getString(memRow, "idnumber"), getString(memRow,"idtype"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate"), 
				ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate"),
				getString(memRow, "idtermrsn"), getString(memRow, "DFCDC_evtinitiator"),
				getString(memRow, "DFCDC_evtctime"), getString(memRow, "DFCDC_mAudRecno"),
				ExtMemgetIxnUtils.getNDelimiters(11), srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWSCH(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WSCH.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2", getString(memRow, "md5key"),
				getString(memRow,"mds5addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
				getString(memRow, "schedtype"), getString(memRow, "schedday"),
				getString(memRow, "schedtimezn"), getString(memRow, "schedstarthr"),
				getString(memRow, "schedendhr"), getString(memRow, "DFCDC_evtinitiator"),
				getString(memRow, "DFCDC_evtctime"), getString(memRow, "DFCDC_mAudRecno"),
				ExtMemgetIxnUtils.getNDelimiters(11), srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWSVC(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WSVC.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2", getString(memRow, "servicetabid"),
				getString(memRow, "servicetabreimbarrangetype"), getString(memRow, "servicetabkey"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "servicetabeffdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "servicetabtermdt"), getString(memRow, "servicetabtermrsn"),
				getString(memRow, "cntrctcd"), ExtMemgetIxnUtils.getDateAsString(memRow, "cntrcteffdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "cntrcttermdt"), getString(memRow, "cntrcttermrsn"),
				getString(memRow, "DFCDC_evtinitiator"), getString(memRow, "DFCDC_evtctime"),
				getString(memRow, "DFCDC_mAudRecno"), ExtMemgetIxnUtils.getNDelimiters(11), srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWENR(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WENR.getValue();
		getMemHeadValues(memRow);
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" + strDelim + getString(memRow, "enrollprottype") + strDelim + 
        getString(memRow, "enrollprotdiaglimitclasscd") + strDelim + getString(memRow, "enrollprotamnt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "enrollproeffdt")
        + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "enrollprottermdt") + strDelim +
		  getString(memRow, "enrollprottermrsn")  + strDelim + getString(memRow,"cntrctcd") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"cntrcteffdt") 
		  + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"cntrcttermdt")+ strDelim + 
		  getString(memRow,"cntrcttermrsn")+ strDelim + getString(memRow, "enrollprotclmcntrtcd") + strDelim + getString(memRow, "enrollprotenctcntrtcd") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + 
		  getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWROL(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WROL.getValue();
		getMemHeadValues(memRow);
		segmentData = outputType  + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" + strDelim +
	              getString(memRow, "hmorollgrouprocessind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmorollgroupprocessdt") + strDelim + getString(memRow, "hmorollisgprocessind") + strDelim + 
	              ExtMemgetIxnUtils.getDateAsString(memRow, "hmorollisgprocessdt") + strDelim + getString(memRow, "hmorollcsgprocessind") 
	              + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmorollcsgprocessdt") + strDelim +
	              getString(memRow, "hmorolllettertype") + strDelim + getString(memRow, "hmorollidcardind") + strDelim + getString(memRow, "hmorollsenderid") + strDelim + getString(memRow, "hmorollrecipient") + strDelim + 
	              getString(memRow, "hmorollreceipientpercent") + strDelim + getString(memRow,"hmorolloverrideind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmorolleffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmorolltermdt") + strDelim + 
	              getString(memRow, "hmorolltermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim + 
	              getString(memRow, "hmorollctrctcd") + strDelim + getString(memRow, "hmoreimbidtypecd") + strDelim + strBlank +	strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmorollctrcteffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmorollctrcttermdt")	+ strDelim + getString(memRow, "hmorollctrcttermrsn")
	              /*2.7 code change starts here*/
	              + strDelim + getString(memRow, "hmorolltranseqno")
	              /*2.7 code change end here*/
	             + ExtMemgetIxnUtils.getNDelimiters(12) + strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWADR(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WADR.getValue();
		getMemHeadValues(memRow);
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime")/* strBlank */+ strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow,"md5key")/* getString(memRow, "mds5key")*//* memRow.getMemRecno()*/ + strDelim + getString(memRow, "addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim 
		+ getString(memRow, "termrsn") + strDelim + getString(memRow, "stline1") + strDelim + getString(memRow, "stline2") + strDelim 
		+ getString(memRow, "stline3") + strDelim + getString(memRow, "city") + strDelim + getString(memRow, "state") + strDelim 
		+ getString(memRow, "zipcode") + strDelim + getString(memRow, "zipextension") + strDelim + getString(memRow, "countycd")
		+ strDelim + getString(memRow, "country") + strDelim + getString(memRow, "attrval1") + strDelim + getString(memRow, "attrval2") + strDelim +
		getString(memRow,"areaziptypecode")/*strBlank*/ + strDelim /* + memRow.getMemRecno()*/ 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")/*strBlank */+ strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")+ strDelim + getString(memRow,"suppresstdzn")	+ strDelim + getString(memRow,"attrval3")
		/* CFF 2.5 changes here*/+strDelim + getString(memRow,"wgsaddrloccode") + ExtMemgetIxnUtils.getNDelimiters(42) +strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWCNTC(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WCNTC.getValue();
		getMemHeadValues(memRow);
		segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim + getString(memRow, "onmfirst")+ strDelim + getString(memRow, "onmlast")+ strDelim + getString(memRow, "onmmiddle")
		+ strDelim + getString(memRow, "rolecd")+ strDelim + getString(memRow, "titlecd")+ strDelim + /*getString(memRow, "contactelectype") this field has deleted for HMO */
		/*+ strDelim */ getString(memRow, "phicc")+ strDelim + getString(memRow, "pharea")+ strDelim + getString(memRow, "phnumber")
		+ strDelim + getString(memRow, "phextn")+ strDelim + getString(memRow, "teltype")/* CFF 2.4 changes here*/+ strDelim + getString(memRow, "fxarea")	
		+ strDelim + getString(memRow, "fxnumber")+ strDelim +/* strBlank*/ getString(memRow,"faxtype") +  strDelim + getString(memRow, "url") + strDelim + getString(memRow, "urltype") 	
		+ strDelim + getString(memRow, "emailaddr") + strDelim + getString(memRow, "emailtype") +  strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
		+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(30) + strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWNOTE(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WNOTE.getValue();
		getMemHeadValues(memRow);
		String noteReplacedWithCommaForEnter = getString(memRow, "note");
		if(getString(memRow, "note").length()>0 && getString(memRow, "note").toLowerCase().contains("\r\n".toLowerCase())){
			noteReplacedWithCommaForEnter = getString(memRow, "note").replaceAll("\r\n", ", ");
		}
		segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + /*getString(memRow, "note")*/ noteReplacedWithCommaForEnter + strDelim + getString(memRow, "notetype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "entrydt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim + strBlank + strDelim
		+ strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + 
		  getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWREL(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WREL.getValue();
		getMemHeadValues(memRow);
		
		if (getString(memRow, "RELMEMSRCCODE").length()>0){
			srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")); 
		}
		else{
			srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
		}						
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ entRecNum + strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim //- Need to map HMO Site Address Identifier to md5key instead of asaIdxno
		+ getString(memRow, "mds5addrtype") + strDelim + /*2.7 code change*/getString(memRow, "primpractind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		/*2.7 code change start here*/
		+ strDelim + getString(memRow, "phnumber") + strDelim + getString(memRow, "fxnumber")
		/*2.7 code change start here*/
		+ strDelim +/*memRow.getMemRecno()*/getString(memRow, "relmemidnum")+ strDelim 
		+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "relattrval1") + strDelim + getString(memRow, "relattrval2") + strDelim 
		+ getString(memRow, "relattrval3") + strDelim
		/*2.7 code change start here*/
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcptaxideffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "pcptaxidtrmdt") + strDelim + getString(memRow, "pcptaxidtrmrsn")
		/*2.7 code change end here*/
		// CFF 2.4 changes start here
		+ strDelim
		+ getString(memRow, "legacyid")
		// CFF 2.4 changes end here
		+ strDelim /*+ strBlank + strDelim + strBlank + strDelim 
		+ getString(memRow, "memrecno")*/ 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")
		+ strDelim + getString(memRow, "relorgmemidnum") + strDelim 
		+ getString(memRow, "relorgsrccode") + strDelim + getString(memRow, "relorgreltype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt")
		//CFF 2.8  changes
		+ strDelim + getString(memRow, "svcareatypcd") + strDelim + getString(memRow, "cntybdraddrind")
		// passing trimmed sourccode and reltype for 2 lookups
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30)+ /*getSrcCodeforPostProcess(getString(memRow,"RELMEMSRCCODE"))*/srcCode_postprocess + strDelim + ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"), getProp_relTypeCode())
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"relorgsrccode")) + strDelim + "ORG"	
		+ strDelim + srcCodesDelimited;	
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWCON(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WCON.getValue();
		getMemHeadValues(memRow);
		if (getString(memRow, "RELMEMSRCCODE").length()>0){
			srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")); 
		}
		else{
			srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
		}
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim 
		+ "EPDS V2"	+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+ strDelim + getString(memRow, "relmemidnum") + strDelim 
		+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")	+ strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "enrolovrrideind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "enroleffectdt")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "enroltermdt") + strDelim /* CFF 2.7 deleted + strBlank	+ strDelim */+ getString(memRow, "hmocntrctcd") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt")+ strDelim 
		+ getString(memRow, "hmocntrcttermrsn")	+ strDelim + getString(memRow, "hmocntrctcapttn") + strDelim 
		+ getString(memRow, "cntrctacceptpats")	+ strDelim +  getString(memRow, "cntrctenrollprefagelo")+ strDelim 
		+ getString(memRow, "cntrctenrollprefagehi")+ strDelim +  getString(memRow, "cntrctenrollprefgndr")	+ strDelim 
		+ getString(memRow, "cntrctenrollprefobsttrcs") + strDelim + getString(memRow, "cntrctmemcapcnt")+ strDelim 
		+ getString(memRow, "cntrctmemcnt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cntrctmemuptdt") + strDelim 
		+ getString(memRow, "cntrctpmgclaims") + strDelim + getString(memRow, "cntrctmixercd")+ strDelim 
		+ getString(memRow, "cntrctdirdisplayind")+ strDelim + getString(memRow, "cntrctspclid")+ strDelim
		+ getString(memRow, "nwhmoownerid")	+ strDelim +  getString(memRow, "hmopgmcd1") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt1")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt1") + strDelim 
		+ getString(memRow, "hmopgmtermrsn1") + strDelim + getString(memRow, "hmopgmcd2") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt2")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt2")+ strDelim 
		+ getString(memRow, "hmopgmtermrsn2") + strDelim + getString(memRow, "hmopgmcd3") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt3")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt3")+ strDelim 
		+ getString(memRow, "hmopgmtermrsn3") + strDelim + getString(memRow, "hmopgmcd4") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt4")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt4")+ strDelim 
		+ getString(memRow, "hmopgmtermrsn4") + strDelim + getString(memRow, "hmopgmcd5") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt5")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt5")+ strDelim 
		+ getString(memRow, "hmopgmtermrsn5") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") 
		// CFF 2.7 new feild : added mapping as per CQ :WLPRD00873914
		+ strDelim + getString(memRow, "relorgmemidnum") + strDelim + getString(memRow, "relorgreltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt")+ ExtMemgetIxnUtils.getNDelimiters(30)
		// passing trimmed sourccode and reltype for lookup - Related Source Provider Identifier
		+ strDelim + /*getSrcCodeforPostProcess(getString(memRow,"RELMEMSRCCODE"))*/ srcCode_postprocess+ strDelim +  ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),getProp_relTypeCode())
		// passing trimmed sourccode and reltype for lookup - HMO Site Relationship Related Source Provider Identifier
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"relorgsrccode")) + strDelim + "ORG"	
		+ strDelim + srcCodesDelimited	;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWNET(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WNET.getValue();
		getMemHeadValues(memRow);
		if (getString(memRow, "RELMEMSRCCODE").length()>0){
			srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")); 
		}
		else{
			srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
		}
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim 
		+ "EPDS V2"	+ strDelim + getString(memRow, "relmemidnum") + strDelim 
		+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")	+ strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "hmocntrctcd") + strDelim + netIdType+ strDelim+netIdSource+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt")+ strDelim 
		+ getString(memRow, "hmocntrcttermrsn")	+ strDelim  
		+ getString(memRow, "cntrctacceptpats")	+ strDelim 
		+ getString(memRow, "cntrctdirdisplayind")+ strDelim + getString(memRow, "cntrctspclid")+ strDelim
		+ getString(memRow, "nwhmoownerid")	+ strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") 
		// CFF 2.7 new feild : added mapping as per CQ :WLPRD00873914
		+ strDelim + getString(memRow, "relorgmemidnum") + strDelim + getString(memRow, "relorgreltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt")+ ExtMemgetIxnUtils.getNDelimiters(30)
		// passing trimmed sourccode and reltype for lookup - Related Source Provider Identifier
		+ strDelim + /*getSrcCodeforPostProcess(getString(memRow,"RELMEMSRCCODE"))*/ srcCode_postprocess+ strDelim +  ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),getProp_relTypeCode())
		// passing trimmed sourccode and reltype for lookup - HMO Site Relationship Related Source Provider Identifier
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"relorgsrccode")) + strDelim + "ORG"	
		+ strDelim + srcCodesDelimited	;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWALT(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WALT.getValue();
		getMemHeadValues(memRow);
		segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime")+ strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "idissuer") + strDelim + getString(memRow, "idtype") 
		+ strDelim + getString(memRow, "idnumber") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")/*changes for CFF 2.5 start*//*+ strDelim + getString(memRow,"idissuer")*//*changes for CFF 2.5 end*/ + strDelim 
		/*2.7 code change start*/+ getString(memRow, "idtermrsn") + strDelim /*2.7 code change end*/
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno") + strDelim 
		/*2.7 code change start*/
		+ getString(memRow, "benefitcd") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "medicaidqlfdeffdt") + ExtMemgetIxnUtils.getNDelimiters(12)+strDelim 
		/*2.7 code change end here*/
		+ srcCodesDelimited;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforWRGN(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		outputType = HmoEnum.WRGN.getValue();
		getMemHeadValues(memRow);
		segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime")+ strDelim + entRecNum + strDelim + "EPDS V2" 	
		+ strDelim + getString(memRow, "regiontypecd") + strDelim + getString(memRow, "regionid") 
		+ strDelim + getString(memRow, "hzipuptpcd") + strDelim + getString(memRow, "hmoinarind") + strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "regioneffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "regiontrmdt") + strDelim 
		+ getString(memRow, "regiontrmrsn") + ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim 
		+ srcCodesDelimited;
		return segmentData;
	}
	
	@Override
	public String buildWHMSegment(List<MemAttrRow> hmoWHMMemAttrList,
			long entRecNum) throws Exception {
		outputType = HmoEnum.WHM.getValue();
		int whm_count = 1;
		HashMap<String, String> hm_WHM = new HashMap<String, String>();
		HashMap<String, String> hm_HMOSITECOPAY = new HashMap<String, String>();
		for (MemRow memRow : hmoWHMMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			HmoEnum attrCode = HmoEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case HMONAME:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "attrval"))) {
								segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim +"EPDS V2"+ strDelim ;
								hm_WHM.put("MEMHEAD", segmentData);
							
								segmentData = getString(memRow, "attrval") + strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim ;
								hm_WHM.put("HMONAME", segmentData);
							}
							break;
							
			case HMOID:		segmentData = getString(memRow, "attrval") + strDelim ; 
							hm_WHM.put("HMOID", segmentData);
							break;
							
			case HMOSITEPRNT: segmentData = getString(memRow, "attrval") + strDelim ; 
							hm_WHM.put("HMOSITEPRNT", segmentData);
							break;
							
			case HMOMNSATTYPE: segmentData= getString(memRow, "codeval") + strDelim;
							hm_WHM.put("HMOMNSATTYPE", segmentData);
							break;
							
			case HMOIPAPMGTYP: segmentData= getString(memRow, "codeval") + strDelim ;
							hm_WHM.put("HMOIPAPMGTYP", segmentData);
							break;
							
			case PREVPCPSFX: segmentData= getString(memRow, "attrval") + strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
							hm_WHM.put("PREVPCPSFX", segmentData);
							break;
							
			case PREVSPCSFX: segmentData= getString(memRow, "attrval") + strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
							hm_WHM.put("PREVSPCSFX", segmentData);
							break;
							
			case HMOSTATUS:	segmentData= ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim +
							ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim + getString(memRow, "termrsn")
							+ strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim ;
							hm_WHM.put("HMOSTATUS", segmentData);
							break;
							
			case HMOROUTEREG: segmentData= getString(memRow, "codeval") + strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim ;
							hm_WHM.put("HMOROUTEREG", segmentData);
							break;
							
			case HMOSAFENET: segmentData= getString(memRow, "codeval") + strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim ;
							hm_WHM.put("HMOSAFENET", segmentData);
							break;
							
			case HMONETADMIN: segmentData= getString(memRow, "codeval") +  strDelim + strBlank + strDelim + strBlank + strDelim + "0"+ strDelim;
							hm_WHM.put("HMONETADMIN", segmentData);
							break;
							
			case HMOCTRCTMGR: segmentData= getString(memRow, "codeval") + strDelim  + strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
							hm_WHM.put("HMOCTRCTMGR", segmentData);
							break;
							
			case HMOPROVSUMID: segmentData= getString(memRow, "attrval") + strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim ;
							hm_WHM.put("HMOPROVSUMID", segmentData);
							break;
							
			case HMOPMPMIND: segmentData= getString(memRow, "attrval") + strDelim +  getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim +
				            getString(memRow, "DFCDC_mAudRecno")+ strDelim;
							hm_WHM.put("HMOPMPMIND", segmentData);
							break;
							
			case AREAZIPTYPCD: segmentData= getString(memRow, "attrval")  ;
							hm_WHM.put("AREAZIPTYPCD", segmentData);
							break;
							
			case HMOGRPDBA:	segmentData= strDelim + getString(memRow, "attrval"); 
							hm_WHM.put("HMOGRPDBA" , segmentData);
							break;
							
			case HMOSITECOPAY: segmentData= strDelim + getString(memRow, "attrval") 
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim +
							getString(memRow, "termrsn") /*+ strDelim +srcCodesDelimited */; /*+ getString(memRow, "attrval") + strDelim + getDateAsString(memRow, "effectdt")+ strDelim + getDateAsString(memRow, "termdt")
							+ strDelim + getString(memRow,"termrsn");*/
							hm_HMOSITECOPAY.put("HMOSITECOPAY" + "-" + whm_count , segmentData);
							whm_count++;
							break;
		  case HMODATAMANG: 
							//if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt")) ) {
							
							segmentData= strDelim + getString(memRow, "elemdesc") 
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim +
							getString(memRow, "codetermrsn");
							hm_WHM.put("HMODATAMANG" , segmentData);
						//}
						break;
			}
		}
		return generateSegmentsforWHM(hm_WHM, hm_HMOSITECOPAY, entRecNum);
	}

	private String generateSegmentsforWHM(HashMap<String, String> hm_WHM, HashMap<String, String> hm_HMOSITECOPAY,
			long entRecNum) {
		outputType = HmoEnum.WHM.getValue();
	if( hm_WHM.get("HMONAME") != null /*&& hm_WHM.get("HMODATAMANG") != null*/ )
	    {
			if (null == hm_WHM.get("HMOID"))
				hm_WHM.put("HMOID", ExtMemgetIxnUtils.getNDelimiters(1));
			if (null == hm_WHM.get("HMOSITEPRNT"))
				hm_WHM.put("HMOSITEPRNT", ExtMemgetIxnUtils.getNDelimiters(1));
			if (null == hm_WHM.get("HMOMNSATTYPE")) 
				hm_WHM.put("HMOMNSATTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
			
			if (null == hm_WHM.get("HMOIPAPMGTYP")) 
				hm_WHM.put("HMOIPAPMGTYP", ExtMemgetIxnUtils.getNDelimiters(1));
			
			if (null == hm_WHM.get("PREVPCPSFX"))
				hm_WHM.put("PREVPCPSFX", ExtMemgetIxnUtils.getNDelimiters(3)+"0"+strDelim);
			
			if (null == hm_WHM.get("PREVSPCSFX")) 
				hm_WHM.put("PREVSPCSFX", ExtMemgetIxnUtils.getNDelimiters(3)+"0"+strDelim);
			
			if (null == hm_WHM.get("HMOSTATUS")) 
				hm_WHM.put("HMOSTATUS", ExtMemgetIxnUtils.getNDelimiters(3)+"0"+ ExtMemgetIxnUtils.getNDelimiters(4)+"0"+ ExtMemgetIxnUtils.getNDelimiters(4)+"0"+ strDelim);
		
			if (null == hm_WHM.get("HMOROUTEREG"))
				hm_WHM.put("HMOROUTEREG", ExtMemgetIxnUtils.getNDelimiters(3)+"0"+strDelim);
			
			if (null == hm_WHM.get("HMOSAFENET")) 
				hm_WHM.put("HMOSAFENET", ExtMemgetIxnUtils.getNDelimiters(3)+"0"+strDelim);
			
			if (null == hm_WHM.get("HMONETADMIN")) 
				hm_WHM.put("HMONETADMIN", ExtMemgetIxnUtils.getNDelimiters(3)+"0"+strDelim);
			
			if (null == hm_WHM.get("HMOCTRCTMGR"))
				hm_WHM.put("HMOCTRCTMGR", ExtMemgetIxnUtils.getNDelimiters(3)+"0"+strDelim);
			
			if (null == hm_WHM.get("HMOPROVSUMID")) 
				hm_WHM.put("HMOPROVSUMID", ExtMemgetIxnUtils.getNDelimiters(3)+"0"+strDelim);
			
			if (null == hm_WHM.get("HMOPMPMIND")) 
				hm_WHM.put("HMOPMPMIND", ExtMemgetIxnUtils.getNDelimiters(3)+"0" + strDelim);
		/*	hm_WHM.put("HMOPMPMIND", getNDelimiters(3)+"0");*/
			if (null == hm_WHM.get("AREAZIPTYPCD")) 
				hm_WHM.put("AREAZIPTYPCD", "" );
			if(null==hm_WHM.get("HMOSITECOPAY"))
				hm_WHM.put("HMOSITECOPAY", ExtMemgetIxnUtils.getNDelimiters(8)/*+ strDelim + srcCodesDelimited*/);
			if(null==hm_WHM.get("HMODATAMANG"))
				hm_WHM.put("HMODATAMANG", ExtMemgetIxnUtils.getNDelimiters(4));
			
			/**
			 * build WHM - HMOSITECOPAY Program 1 to 2  -begin
			 */
			if(null!=hm_HMOSITECOPAY && hm_HMOSITECOPAY.size()>0)
			{
				
				String HMOSite_Program1_segmentData= ExtMemgetIxnUtils.getNDelimiters(4);
				String HMOSite_Program2_segmentData= ExtMemgetIxnUtils.getNDelimiters(4)/*+ strDelim + srcCodesDelimited*/;
				
				
				Set <String>HMOSITECOPAY_Keys = new HashSet<String>();
				HMOSITECOPAY_Keys = hm_HMOSITECOPAY.keySet();
				for (Iterator<String> iterator = HMOSITECOPAY_Keys.iterator(); iterator.hasNext();) 
				{
					String HMOSITECOPAY_Key = iterator.next();
					String split_keys[];
					split_keys= HMOSITECOPAY_Key.split("-");
					int count = Integer.parseInt(split_keys[1]);
					switch (count)
					{
					case 1: HMOSite_Program1_segmentData =(hm_HMOSITECOPAY.get(HMOSITECOPAY_Key)!=null)?hm_HMOSITECOPAY.get(HMOSITECOPAY_Key): ExtMemgetIxnUtils.getNDelimiters(4);
							break;
					case 2: HMOSite_Program2_segmentData =(hm_HMOSITECOPAY.get(HMOSITECOPAY_Key)!=null)?hm_HMOSITECOPAY.get(HMOSITECOPAY_Key): ExtMemgetIxnUtils.getNDelimiters(4)/*+ strDelim + srcCodesDelimited*/;
							break;
					
					}
				}//end for - iterating hm_HMOSITECOPAY
				
				//building HMOSITECOPAY attribute
				segmentData = HMOSite_Program1_segmentData + HMOSite_Program2_segmentData ;
				
				
				hm_WHM.put("HMOSITECOPAY",segmentData);
				
			}
			//build WHM - HMOSITECOPAY Program 1 to 2  -end
			
			/*2.7 code change starts here*/
			if(null==hm_WHM.get("HMOGRPDBA"))
				hm_WHM.put("HMOGRPDBA", ExtMemgetIxnUtils.getNDelimiters(1));	
			/*2.7 code change ends here*/
			
			segmentData = hm_WHM.get("MEMHEAD") + hm_WHM.get("HMONAME") + hm_WHM.get("HMOID") + hm_WHM.get("HMOSITEPRNT") 
			 + hm_WHM.get("HMOMNSATTYPE")+  hm_WHM.get("HMOIPAPMGTYP") + hm_WHM.get("PREVPCPSFX") +
			 hm_WHM.get("PREVSPCSFX") + hm_WHM.get("HMOSTATUS") + hm_WHM.get("HMOROUTEREG")+  hm_WHM.get("HMOSAFENET")
			 + hm_WHM.get("HMONETADMIN") + hm_WHM.get("HMOCTRCTMGR") + hm_WHM.get("HMOPROVSUMID")+  hm_WHM.get("HMOPMPMIND") + hm_WHM.get("AREAZIPTYPCD") + hm_WHM.get("HMOSITECOPAY")+ hm_WHM.get("HMOGRPDBA") + hm_WHM.get("HMODATAMANG");
			//			logInfo("WHM: " + segmentData);
			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)/*.replace(srcCodesDelimited, strBlank)*/.length() > 0 ) {
				
				segmentData += ExtMemgetIxnUtils.getNDelimiters(56)+strDelim + srcCodesDelimited;
				return segmentData;
			}
		}
		return strBlank;
	}
	
	@Override
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> hmoALTSRCIDMemAttrList,
			long entRecNum) throws Exception {
		outputType = HmoEnum.ALTSRCID.getValue();
		HashMap<String, String> hm_ALTSRCID_Slg = new HashMap<String, String>();
		for (MemRow memRow : hmoALTSRCIDMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			HmoEnum attrCode = HmoEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case HMONAME:	segmentData = getString(memRow, "attrval") + strDelim + strBlank + strDelim + strBlank +strDelim + strBlank +strDelim
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
							+ getString(memRow, "DFCDC_mAudRecno") + ExtMemgetIxnUtils.getNDelimiters(127)
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")
							/*+ strDelim + getSrcCodeforPostProcess(l_strSrcCd)*/;
				
							hm_ALTSRCID_Slg.put("HMONAME-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
							break;
							
			case HMOSTATUS:	segmentData =outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
							+ strDelim + "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim + strBlank + strDelim;
							hm_ALTSRCID_Slg.put("MEMHEAD-"+ new Long (memRow.getMemRecno()).toString(), segmentData);
				
							segmentData = ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim;
							hm_ALTSRCID_Slg.put("HMOSTATUS-"+ new Long (memRow.getMemRecno()).toString(), segmentData);
							break;
			
			}
		}
		return generateSegmentsforALTSRCID(hm_ALTSRCID_Slg,entRecNum);
	}
	
	private Set<String> generateSegmentsforALTSRCID(HashMap<String,String> hm_ALTSRCID_Slg,long entRecNum)throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		Set <String>ALTSRCID_Keys = new HashSet<String>();
		outputType = HmoEnum.ALTSRCID.getValue();
		ALTSRCID_Keys = new HashSet<String>(hm_ALTSRCID_Slg.keySet());
		
		String split_keys[];
		String memrecno="";
		Set <String> ALTSRCID_memrecnos = new HashSet<String>();
		for (Iterator <String>iterator = ALTSRCID_Keys.iterator(); iterator
				.hasNext();) 
		{
			String ALTSRCID_key =iterator.next().toString();
			if(ALTSRCID_key.contains("HMOSTATUS")
					||ALTSRCID_key.contains("HMONAME"))
			{
				//split the keys to get memrecnos
				split_keys=ALTSRCID_key.split("-");
				memrecno=split_keys[1].trim();
				ALTSRCID_memrecnos.add(memrecno);
			}
		}
		memrecno="";
		for (Iterator<String>iterator2 = ALTSRCID_memrecnos.iterator(); iterator2
		.hasNext();) 
		{
			//write ALTSRCID segment - BEGIN
			memrecno  = (String) iterator2.next();
			if(null!=hm_MemHead)
			{
				MemHead temp_memHead ;
				temp_memHead = (MemHead)hm_MemHead.get(memrecno);
				l_strSrcCd = temp_memHead.getSrcCode();
				l_memIdNum = temp_memHead.getMemIdnum();
				l_strCaudrecno= new Long(temp_memHead.getCaudRecno()).toString();
				l_maudRecNo = new Long(temp_memHead.getMaudRecno()).toString();

			}
			if(null != hm_ALTSRCID_Slg.get("HMONAME-"+memrecno)
					|| null != hm_ALTSRCID_Slg.get("HMOSTATUS-"+memrecno) )
			{
				if (null == hm_ALTSRCID_Slg.get("MEMHEAD-"+memrecno)) 
				{
					segmentData = outputType+ strDelim + strBlank + strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim +
					l_strSrcCd + strDelim + l_memIdNum + strDelim + strBlank + strDelim;
					hm_ALTSRCID_Slg.put("MEMHEAD-"+memrecno, segmentData);
				}
				if (null == hm_ALTSRCID_Slg.get("HMOSTATUS-"+memrecno)) 
				{
					segmentData = ExtMemgetIxnUtils.getNDelimiters(3);
					hm_ALTSRCID_Slg.put("HMOSTATUS-"+memrecno, segmentData);
				}
				if (null==hm_ALTSRCID_Slg.get("HMONAME-"+memrecno))
				{
					segmentData = ExtMemgetIxnUtils.getNDelimiters(6)+ "0" + ExtMemgetIxnUtils.getNDelimiters(129)+ "0"	/*+ strDelim + getSrcCodeforPostProcess(l_strSrcCd)*/;
					hm_ALTSRCID_Slg.put("HMONAME-"+ memrecno, segmentData);
				}
				
				segmentData = hm_ALTSRCID_Slg.get("MEMHEAD-"+memrecno) +hm_ALTSRCID_Slg.get("HMOSTATUS-"+memrecno) 
				              + hm_ALTSRCID_Slg.get("HMONAME-"+memrecno) ;
				
				if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)/*.replace(getSrcCodeforPostProcess(l_strSrcCd), strBlank)*/.length() > 0) 
				{
					segmentData +=strDelim + ExtMemgetIxnUtils.getNDelimiters(60)+ ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					segmentDataSet.add(segmentData);
				}
			}
			
		}//end of for 
		return segmentDataSet;
	}
}
