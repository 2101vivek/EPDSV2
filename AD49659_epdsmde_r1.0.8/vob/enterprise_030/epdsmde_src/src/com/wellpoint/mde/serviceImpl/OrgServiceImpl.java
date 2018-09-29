package com.wellpoint.mde.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.Constants;
import com.wellpoint.mde.constants.OrgEnum;
import com.wellpoint.mde.service.ProviderService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;


public class OrgServiceImpl extends AbstractServiceImpl implements ProviderService{
	HashMap<String, String> hm_PRF = new HashMap<String, String>();
	
	HashMap<String, String>hm_PSPT_ORGBRDCRT = new HashMap<String, String>();
	
	HashMap<String, String>hm_PSPT_SPC = new HashMap<String, String>();
	
	HashMap<String, String>hm_PSPT_TXNMY = new HashMap<String, String>();
	
	HashMap<String, String> hm_PADR = new HashMap<String, String>();
	
	HashMap<String, String>hm_ORGFACSTERE = new HashMap<String, String>();
	
	HashMap<String, String>hm_PREM_REMITSEG1 = new HashMap<String,String>();
	
	HashMap<String, String>hm_PREM_REMITSEG2 = new HashMap<String,String>();
	
	HashMap<String, String>hm_PREM_REMITSEG3 = new HashMap<String,String>();
	
	protected HashMap<String, String> hm_PALT = new HashMap<String, String>();
	
	protected HashMap<String,ArrayList<String>> hm_PALT_Alt_ID_Specialty = new HashMap<String,ArrayList<String>>();
	
	HashMap<String, String> hm_ALTSRCID_Slg = new HashMap<String, String>();
	
	HashMap<String, String> hm_ALTSRCID_Slg_V2 = new HashMap<String, String>();
	
	HashMap<String, String> hm_APADR = new HashMap<String, String>();
		
	int prem_remit_count=0;
	
	int prem_remit_details_count=0;
	
	int aprem_count=0;
	
	public HashMap<String, String> getHm_PRF() {
		return hm_PRF;
	}

	public String getSegmentDataforPATTS(MemRow memRow, long entRecNum) throws Exception {
		try {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		srcCode_postprocess = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		outputType = OrgEnum.PATTS.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(
											outputType, strDFCDC_evtctime,
											Long.toString(entRecNum), "EPDS V2",getString(memRow, "atteststate"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "attesteffectdt"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "attesttermdt"),
											getString(memRow, "attesttermrsn"), getString(memRow, "orgnpi"),
											getString(memRow, "orgmedicaid"), getString(memRow, "orgtxnmy"),
											getString(memRow, "md5key"), getString(memRow, "mds5addrtype"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
											getString(memRow, "fedtaxid"), getString(memRow, "fedtaxidtype"),
											getString(memRow, "providerid"), getString(memRow, "provreltypcd"),/*Added as per2.4*/
											ExtMemgetIxnUtils.getDateAsString(memRow, "provreleffectdt"),
											getString(memRow, "provnpi"), getString(memRow, "provmedicaid"),
											getString(memRow, "provtxnmy"), getString(memRow, "DFCDC_evtinitiator"),
											strDFCDC_evtctime, getString(memRow, "DFCDC_mAudRecno"),
											// passing trimmed sourccode and reltype for lookup- Source Individual Provider Identifier
											ExtMemgetIxnUtils.getNDelimiters(11),srcCode_postprocess, "   ",
											srcCodesDelimited);
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PATTS entrecno-"+entRecNum);
		}
		return strBlank;
	}

	public String getSegmentDataforPPPRF(MemRow memRow, long entRecNum) throws Exception {
		try {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PPPRF.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(
											outputType, strDFCDC_evtctime, Long.toString(entRecNum),
											"EPDS V2", getString(memRow, "md5key"), getString(memRow, "mds5addrtype"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
											getString(memRow, "patlimittype"), getString(memRow, "patlimitval"),
											getString(memRow,"patlimitvaltypefrm"),/*Added as per 2.5b*/
											getString(memRow, "pallimitvalto"), /*Added as per 2.4*/
											getString(memRow,"patlimitvaltypeto"),/*Added as per 2.5b*/
											ExtMemgetIxnUtils.getDateAsString(memRow, "patlimiteffdt"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "patlimittermdt"),
											getString(memRow, "patlimittermrsn"), getString(memRow, "DFCDC_evtinitiator"),
											strDFCDC_evtctime, getString(memRow, "DFCDC_mAudRecno"),
											ExtMemgetIxnUtils.getNDelimiters(11), srcCodesDelimited);
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PPPRF entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPOT(MemRow memRow, long entRecNum) throws Exception {
		try {
		outputType = OrgEnum.POT.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(
											outputType, getString(memRow, "DFCDC_evtctime"),
											Long.toString(entRecNum), "EPDS V2",
											getString(memRow, "billformcode"), getString(memRow, "specialtycode"),
											getString(memRow, "orgtypecode"),ExtMemgetIxnUtils.getNDelimiters(29),
											srcCodesDelimited);
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-POT entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPCRED(MemRow memRow, long entRecNum) throws Exception {
		try {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PCRED.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "attrval") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
		+ strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator")
		+ strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim + getString(memRow, "attrval2")
		+ ExtMemgetIxnUtils.getNDelimiters(29)+ strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PCRED entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPRNK(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PRNK.getValue();
		segmentData = outputType+ strDelim + strDFCDC_evtctime
		+ strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim 
		+getString(memRow, "attrval")+ strDelim +getString(memRow, "elemdesc")
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim 
		+getString(memRow, "termrsn")+ strDelim +getString(memRow, "DFCDC_evtinitiator")
		+ strDelim + strDFCDC_evtctime + strDelim +getString(memRow, "DFCDC_mAudRecno")
		+ ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited; 
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PRNK entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPDSTC(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PDSTC.getValue();
		segmentData = outputType+ strDelim + strDFCDC_evtctime
		+ strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim 
		+ getString(memRow, "attrval")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim 
		+ getString(memRow, "termrsn")+ strDelim +getString(memRow, "DFCDC_evtinitiator")
		+ strDelim + strDFCDC_evtctime + strDelim +getString(memRow, "DFCDC_mAudRecno")
		+ strDelim + getString(memRow,"MD5KEY")+ strDelim + getString(memRow,"MDS5ADDRTYPE")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"MDS5ADDREFFECTDT")
		+ ExtMemgetIxnUtils.getNDelimiters(11)+strDelim + srcCodesDelimited;	 
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PDSTC entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPCLMFRM(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PCLMFRM.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "attrval") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") 
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(30)+ strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PCLMFRM entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPNOTE(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		outputType = OrgEnum.PNOTE.getValue();
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		String note = ExtMemgetIxnUtils.rmvAllNewLine(getString(memRow, "note"));
		
		segmentData = outputType + strDelim + strDFCDC_evtctime 
		+ strDelim + entRecNum + strDelim + "EPDS V2" + ExtMemgetIxnUtils.getNDelimiters(6) 
		+ /*getString(memRow, "note")*/ note + strDelim + getString(memRow, "notetype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "entrydt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
		+ strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim
		//cff 2.7 new field :Applicable Source System for Note.    
		+ getString(memRow, "SRCIDENTIFIER") + ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PNOTE entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPSANC(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PSANC.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "sanctiontype") + strDelim + getString(memRow, "sanctionsrc") 
		+ strDelim + getString(memRow, "sanctionaction") + strDelim + getString(memRow, "sanctionrsn") 
		+ strDelim /*+ ExtMemgetIxnUtils.getDateAsString(memRow, "sanctionreportdt") + strDelim Removed the field as per 2.4*/ 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "sanctioneffectdt")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "sanctionenddt") + strDelim + getString(memRow, "sanctiontermrsn") 
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ExtMemgetIxnUtils.getNDelimiters(30) +strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PSANC entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPSNAC(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PSNAC.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "sanctype") + strDelim + getString(memRow, "sancsource") + strDelim 
		+ getString(memRow, "sancaction") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "sanctionreportdt"/*"sancreportdt" Changed for SIT1 2.4*/) + strDelim + getString(memRow, "claimacttype") 
		+ strDelim + getString(memRow, "claimactcrittype") + strDelim + getString(memRow, "claimactlow") + strDelim + getString(memRow, "claimacthigh") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "claimacteffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "sancclaimacteffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "sancclaimacttermdt") + strDelim + getString(memRow, "sancclaimacttermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(30)
		+ strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PSNAC entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPSTFLANG(MemRow memRow, long entRecNum) throws Exception {
		//filter
		//Additional filter check removed as per onsite instructions
		/*if(getString(memRow, "langcd").length()>0)*/
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PSTFLANG.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		+ getString(memRow, "langcd") + strDelim + getString(memRow, "langwritten") + strDelim + getString(memRow, "langspoken") 
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PSTFLANG entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPPGM(MemRow memRow, long entRecNum) throws Exception {
		//filter
		//Additional filter check removed as per onsite instructions
		/*if(getString(memRow, "attrval").length()>0)*/
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PPGM.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "attrval") + strDelim 
		//Changes for cff2.5c-Start
		+ getString(memRow,"attrval2")+ strDelim 
		+ getString(memRow,"codetermrsn")+ strDelim 
		//Changes for cff2.5c-End
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim 
		+ getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
		+ strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno") + ExtMemgetIxnUtils.getNDelimiters(30)
		+ strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PPGM entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPAOF(MemRow memRow, long entRecNum) throws Exception {
		//filter
		/*Additional filter check removed as per onsite instructions*/
		/*if(null!=ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") && ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt").length()>0 )*/
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
			outputType = OrgEnum.PAOF.getValue();
			//PAOF (renamed from PAOE)
			
			segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim + getString(memRow, "attrval") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim 
		+ getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ExtMemgetIxnUtils.getNDelimiters(12)+strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PAOF entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPOFSR(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.POFSR.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "attrval") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim 
		+ getString(memRow, "termrsn") + strDelim + strBlank + strDelim 
		+ strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-POFSR entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPDBA(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String networkRelFeilds;
		if(getString(memRow, "networkid").length()>0){
			networkRelFeilds = getString(memRow, "networkid") + strDelim + getString(memRow, "networkidtyp") + strDelim + 
						ExtMemgetIxnUtils.getDateAsString(memRow, "networkeffdt") + strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd) 
						+ Constants.NET;
		}
		else {
			networkRelFeilds = ExtMemgetIxnUtils.getNDelimiters(3);
		}
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PDBA.getValue();
		segmentData =  outputType + strDelim + strDFCDC_evtctime + strDelim
		+ entRecNum + strDelim + "EPDS V2" + strDelim + /*WLPRD00990801: mds5key to md5key*/getString(memRow, "md5key") + strDelim
		+ getString(memRow, "mds5addrtype") + strDelim 	+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")
		+ strDelim + getString(memRow, "relsrcprovid")	+ strDelim + getString(memRow, "reltype")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "releffdt")+ strDelim + /*getString(memRow, "networkid")
		+ strDelim + getString(memRow, "networkidtyp") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "networkeffdt")
		// new feild CFF 2.7 : Network Identifier Souce Code,Specialty Code Effective Date 
		+ strDelim + l_strSrcCd */ networkRelFeilds
		+ strDelim + getString(memRow, "spec1code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec1effdt")
		+ strDelim + getString(memRow, "spec2code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec2effdt")
		+ strDelim + getString(memRow, "spec3code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec3effdt")
		+ strDelim + getString(memRow, "spec4code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec4effdt")
		+ strDelim + getString(memRow, "spec5code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec5effdt")
		+ strDelim + getString(memRow, "busasname")	+ strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "busnmeffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "busnmtrmdt")
		+ strDelim + getString(memRow, "busnmtrmrsn")+  strDelim + getString(memRow, "DFCDC_evtinitiator")
		+ strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ strDelim + getString(memRow, "diridntfier")	+ strDelim + getString(memRow, "dirdispopt")	
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(12)+ srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PDBA entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPCNTC(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PCNTC.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "onmfirst") + strDelim 
		+ getString(memRow, "onmlast") + strDelim + getString(memRow, "onmmiddle") + strDelim + getString(memRow, "rolecd") 
		+ strDelim + getString(memRow, "titlecd") + strDelim + /*getString(memRow, "mds5addrtype") + strDelim+ Removed as per 2.4*/
		getString(memRow, "phicc") + strDelim + getString(memRow, "pharea") + strDelim + getString(memRow, "phnumber") + strDelim + getString(memRow, "phextn") 
		+ strDelim + getString(memRow, "teltype") + strDelim 
		/*+ ExtMemgetIxnUtils.getDateAsString(memRow, "teleffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "teltermdt") + strDelim + getString(memRow, "teltermrsn") + strDelim Removed as per 2.4 */
		+ getString(memRow, "fxarea") + strDelim + getString(memRow, "fxnumber") + strDelim + getString(memRow, "faxtype")
		/*+ ExtMemgetIxnUtils.getDateAsString(memRow, "faxeffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "faxtermdt") + strDelim + getString(memRow, "faxtermrsn")Removed as per 2.4*/ 
		+ strDelim + getString(memRow, "url") + strDelim + getString(memRow, "urltype")
		/*+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "urleffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "urltermdt") + strDelim + getString(memRow, "urltermrsn")*/ 
		+ strDelim + getString(memRow, "emailaddr") + strDelim + getString(memRow, "emailtype") + strDelim 
		/*+ ExtMemgetIxnUtils.getDateAsString(memRow, "emaileffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "emailtermdt") + strDelim + getString(memRow, "emailtermrsn")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cntceffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cntctermdt") + strDelim Removed as per 2.4*/ 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(60)+strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PCNTC entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPOFSCH(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.POFSCH.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "schedtype") 
		+ strDelim + getString(memRow, "schedday") + strDelim + getString(memRow, "schedtimezn") + strDelim 
		+ getString(memRow, "schedstarthr") + strDelim + getString(memRow, "schedendhr") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-POFSCH entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPOFTCH(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.POFTCH.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim + getString(memRow, "officetechtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "officetecheffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "officetechtermdt") + strDelim + getString(memRow, "officetechtermrsn") + strDelim 
		+ getString(memRow, "officesysname") + strDelim + getString(memRow, "vendorname") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "officesyseffdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "officesystermdt") + strDelim + getString(memRow, "officesystermrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-POFTCH entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPOFACS(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.POFACS.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim + getString(memRow, "ofcaccesstype") + strDelim + getString(memRow, "ofcaccesscd") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "ofcaccesseffdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "ofcaccessdt") + strDelim + getString(memRow, "ofcaccesstermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-POFACS entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPOFSRR(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.POFSRR.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2"  
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "attrval") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
		+ strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
		+ strDFCDC_evtctime	+ strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-POFSRR entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPREL(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),prop_relTypeCode);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);

		outputType = OrgEnum.PREL.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reladdrtermdt") + strDelim + getString(memRow, "reladdrtermrsn") 
		+ strDelim + /*memRow.getMemRecno()*/getString(memRow, "relmemidnum") + strDelim + getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "relattrval1") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")
		/*+ ExtMemgetIxnUtils.getNDelimiters(5)*/ /* 2.5b changes: 5 blank fields added: No Mappings present in the inbound PREL*/
		// CFF2.7 got mappings for the feilds 
		+ strDelim + getString(memRow, "relattrval2") + strDelim + getString(memRow, "relattrval3") 
		+ strDelim + getString(memRow, "relattrval4") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt")
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS,CPF,ACES..) + reltypecode */
		/*Changed for ENCLARITY bug-WLPRD00472537*/
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(60) +/* ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE"))*/srccode_lookup + strDelim + reltype_code
		+ strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PREL entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPALTROL(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PALTROL.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim +"EPDS V2"	+ strDelim 
		+ getString(memRow, "rolloversrccode") + strDelim + getString(memRow, "rolloversrcval") + strDelim 
		+ getString(memRow, "rolloversrctype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rolloversrcissuedt") + strDelim 
		// CFF 2.7 new feild 
		+ getString(memRow, "rollovertranseqno") + strDelim
		+ getString(memRow, "rolloverrecipntsrccode") + strDelim + getString(memRow, "rolloverrecipntsrcval")+ strDelim 
		+ getString(memRow, "rolloverrecipnttype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rolloverrecipntissuedt") + strDelim 
		+ getString(memRow, "rollovergrpprocind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rollovergrpprocdt") + strDelim 
		+ getString(memRow, "rolloverisgprocind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rolloverisgprocdt")+ strDelim 
		+ getString(memRow, "rollovercsgprocind") + strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "rollovercsgprocdt") + strDelim 
		+ getString(memRow, "rolloverltrtype") + strDelim + getString(memRow, "rolloveridcardind") + strDelim 
		+ getString(memRow, "rolloversenderid") + strDelim + getString(memRow, "rolloverreceipnt") + strDelim 
		+ getString(memRow, "rolloverreceipntperc") + strDelim + getString(memRow, "rolloverind") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "rollovereffectdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rollovertermdt")+ strDelim 
		+ getString(memRow, "rollovertermrsn")+ strDelim + getString(memRow, "DFCDC_evtinitiator")+ strDelim 
		+ strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")/*+ strDelim + getString(memRow, "networkid") + strDelim + getString(memRow, "networkidtype")*/+ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PALTROL entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPRGN(MemRow memRow, long entRecNum) throws Exception {
		try{
		outputType = OrgEnum.PRGN.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim +"EPDS V2"	
		+ strDelim + getString(memRow, "idissuer") + strDelim + getString(memRow, "idnumber") + strDelim 
		+ getString(memRow, "idtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "regiontypecd") + strDelim 
		+ getString(memRow, "regionid") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "regioneffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "regiontrmdt")+ strDelim + getString(memRow, "regiontrmrsn") + strDelim 
		+ getString(memRow, "hmositecode") + strDelim + ExtMemgetIxnUtils.getNDelimiters(12) + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PRGN entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPTAX(MemRow memRow, long entRecNum) throws Exception {
		try{
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PTAX.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2"  
		+ strDelim + getString(memRow, "idnumber") + strDelim + getString(memRow, "fedtaxid1099") + strDelim 
		+ getString(memRow, "idtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "idtermrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ExtMemgetIxnUtils.getNDelimiters(30)+strDelim + srcCodesDelimited;
		return segmentData;
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-PTAX entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	public String getSegmentDataforPBREL(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String sourceId = strBlank;
		if (getString(memRow, "relorgmemidnum").length()>0)
			sourceId = "EPDS V2";
		else
			sourceId = strBlank;

		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);

		outputType = OrgEnum.PBREL.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime  + strDelim + getString(memRow, "relmemidnum") + strDelim + "EPDS V2"  
		+ strDelim + entRecNum /*l_memIdNum*/ + strDelim + /*getString(memRow, "relmemsrccode")
		Commented as per onsite on July 17*/ "EPDS V2" + strDelim 
		+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "relorgmemidnum") + strDelim + /*getString(memRow, "relorgsrccode")
		Commented as per onsite on July 17*/ sourceId + strDelim 
		+ getString(memRow, "relorgreltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt") + strDelim + getString(memRow, "md5key") + strDelim 
		+ getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reladdrtermdt") + strDelim 
		+ getString(memRow, "reladdrtermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
		+ strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS,CPF,ACES..)*/
		//1st lookup
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30) +/*ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "relmemsrccode"))*/srccode_lookup+ strDelim +
		/*ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"))*/"BUS"
		//2nd Lookup
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "relorgsrccode"))+ strDelim +
		ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "relorgreltype"),prop_relTypeCode)+ strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	public String getSegmentDataforPOCON(MemRow memRow, long entRecNum) throws Exception {
		
		/**
		 * As per mail from onsite:
		 * Please map the field Provider Program System Of Record Code to MEMHEAD.SRCCODE 
		 * in RPTGP ,BECON and POCON 
		 */
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.POCON.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2"
		+ strDelim + getString(memRow, "taxid") + strDelim 
		/*+ getString(memRow, "programid") + strDelim + l_strSrcCd strBlank + strDelim */ //Deleted as per CFF2.5b
		+ getString(memRow, "contractid") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "contracteffectdt")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "contracttermdt") + strDelim + getString(memRow, "contracttermrsn") 
		+ strDelim + getString(memRow, "servicetabid") + strDelim + getString(memRow, "servicetabrmbarrangetype") 
		+ strDelim + strBlank + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "svctabideffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "svctabidtermdt") + strDelim + getString(memRow, "svctabidtermrsn") 
		+ strDelim + getString(memRow, "rmbarrangeid") + strDelim + getString(memRow, "rmbarrangeidtype") + strDelim + strBlank 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangeeffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarangetermdt") + strDelim 
		+ getString(memRow, "rmbarrangetermrsn") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cntrrmbarrangeeffectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "cntrrmbarangetermdt") + strDelim + getString(memRow, "cntrrmbarrangetermrsn") + strDelim 
		+ getString(memRow, "claimstimelyfiledays") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "claimstimelyfileeffectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "claimstimelyfiletermdt") + strDelim + getString(memRow, "claimstimelyfiletermrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ strDelim +ExtMemgetIxnUtils.getNDelimiters(12)+ srcCodesDelimited;
		return segmentData;
	}
	
	public String getSegmentDataforPGREL(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String sourceId = strBlank;
		if (getString(memRow, "provrelorgid").length()>0)
			sourceId = "EPDS V2";
		else
			sourceId = strBlank;

		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);

		outputType = OrgEnum.PGREL.getValue();
		segmentData = outputType+ strDelim + strDFCDC_evtctime + strDelim +getString(memRow, "relmemidnum")+ strDelim +"EPDS V2"
		+ strDelim + entRecNum /*l_memIdNum*/ + strDelim + /*getString(memRow, "relmemsrccode")
		Commented as per onsite on July 17*/
		"EPDS V2" + strDelim 
		//cff 2.8 changes
		+ getString(memRow, "grpdynamicind")+ strDelim 
		+ getString(memRow, "reltype")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim +getString(memRow, "termrsn")+ strDelim 
		+ getString(memRow, "remitmds5key")+ strDelim +getString(memRow, "remitaddrtype")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt")+ strDelim 
		// CFF 2.7 new feild
		+ getString(memRow, "remitaddrpayeenm")+ strDelim 
		+ getString(memRow, "provrelorgid") 
		+ strDelim +/*getString(memRow, "provrelorgsrc")
		Commented as per onsite on July 17*/ sourceId+ strDelim +getString(memRow, "provrelorgreltype")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgeffectdt") + strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgtermdt")
		+ strDelim +getString(memRow, "md5key")+ strDelim +getString(memRow, "mds5addrtype")+ strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt") 
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "reladdrtermdt")+ strDelim +getString(memRow, "reladdrtermrsn") 
		+ strDelim +getString(memRow, "provaltid")+ strDelim +getString(memRow, "provaltidtype") 
		+ strDelim +getString(memRow, "provaltidnm")+ strDelim +getString(memRow, "svctabid") 
		+ strDelim +getString(memRow, "rmbarrangetype")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "svctabideffectdt")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "svctabidtermdt")+ strDelim +getString(memRow, "svctabidtermrsn")
		+ strDelim + getString(memRow, "DFCDC_evtinitiator")+ strDelim + strDFCDC_evtctime
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS,CPF,ACES..)*/
		//1st lookup
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30)+ /*ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "relmemsrccode"))*/srccode_lookup+ strDelim +
		/*ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"))*/"GRP"
		//2nd Lookup 
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "provrelorgsrc"))+ strDelim +
		ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "provrelorgreltype"),prop_relTypeCode)+ strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	public String getSegmentDataforGRMB(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);

		outputType = OrgEnum.GRMB.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim +getString(memRow, "relmemidnum") + strDelim +"EPDS V2"
		+ strDelim + entRecNum /*l_memIdNum*/ + strDelim +getString(memRow, "relmemsrccode") + strDelim 
		+getString(memRow, "reltype") + strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim +getString(memRow, "termrsn") 
		+ strDelim +strBlank + strDelim +strBlank + strDelim +strBlank + strDelim +strBlank 
		+ strDelim +strBlank + strDelim +getString(memRow, "md5key") 
		+ strDelim +getString(memRow, "mds5addrtype") + strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbreladdreffdt") + strDelim 
		+getString(memRow, "grprmbid") + strDelim +getString(memRow, "rmbarrangetype") 
		+ strDelim + ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "RMB" //WLPRD01168543
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbeffectdt") + strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbtermdt") + strDelim +getString(memRow, "grprmbtermrsn") 
		+ strDelim +getString(memRow, "caremgmtmpmtier") + strDelim +getString(memRow, "ctrctid") 
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "ctrctideffectdt") + strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "ctrctidtermdt") 
		+ strDelim +getString(memRow, "ctrctidtermrsn") + strDelim +getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim +strDFCDC_evtctime + strDelim +getString(memRow, "DFCDC_mAudRecno")
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30) + srccode_lookup+ strDelim + "GRP" +strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	public String getSegmentDataforWREL(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		if(null!=hm_MemHead) {
			reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),prop_relTypeCode);
			
		}
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		srcCode_postprocess = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);

		outputType = OrgEnum.WREL.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "relmemidnum") + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key"/*"asaIdxno" changed as per onsite mail May 7*/) + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		// CFF 2.7 new feild 
		+ getString (memRow, "primpractind") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		// CFF 2.7 new feild 
		+ getString(memRow, "phnumber") + strDelim + getString(memRow, "fxnumber") + strDelim 
		+ entRecNum + strDelim + getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") 
		+ strDelim + getString(memRow, "relattrval1") + strDelim + getString(memRow, "relattrval2") 
		+ strDelim + getString(memRow, "relattrval3") + strDelim
		// CFF 2.7 new feild 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcptaxideffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "pcptaxidtrmdt") + strDelim
		+ getString(memRow, "pcptaxidtrmrsn") + strDelim					
		+ getString(memRow, "legacyid")/*Added for 2.4*/
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim +getString(memRow, "DFCDC_mAudRecno") 
		+ strDelim + getString(memRow, "relorgmemidnum") + strDelim + getString(memRow, "relorgsrccode") + strDelim + getString(memRow, "relorgreltype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt")
		//CFF 2.8  new field
		+ strDelim + getString(memRow, "svcareatypcd") + strDelim + getString(memRow, "cntybdraddrind")
		// passing trimmed sourccode and reltype for 1 lookup and passing 2 delimiters for 2nd lookup
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30) + /*ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"RELMEMSRCCODE"))*/srcCode_postprocess + strDelim + "HMO"
		+ ExtMemgetIxnUtils.getNDelimiters(2)					
		+ strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	public String getSegmentDataforGNET(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);

		outputType = OrgEnum.GNET.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ /*entRecNum*/getString(memRow, "relmemidnum") + strDelim + "EPDS V2" + strDelim + entRecNum + strDelim + getString(memRow,"RELMEMSRCCODE") + strDelim 
		+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")  + strDelim + getString(memRow, "termrsn") + strDelim
		// CFF 2.7 new feild
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		+ getString(memRow, "provrelorgid") + strDelim + getString(memRow, "provrelorgsrc") + strDelim
		+ getString(memRow, "provrelorgreltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgeffectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgtermdt") + strDelim + /*WLPRD00990801: mds5key to md5key*/ getString(memRow, "md5key") + strDelim
		+ getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		+ getString(memRow, "networkid")+ strDelim 
		//CFF 2.8 changes
		+ getString(memRow,"ntwkidtypecd") + strDelim 
		+  ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "NET" //WLPRD01168543
		+ strDelim + getString(memRow, "nwownerid") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "grpneteffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "grpnettermdt")  + strDelim + getString(memRow, "grpnettermrsn")
		// passing trimmed sourccode and reltype for 1 lookup and passing 2 delimiters for 2nd lookup
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30)+ /*ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"RELMEMSRCCODE"))*/srccode_lookup + strDelim + "GRP"
		+ ExtMemgetIxnUtils.getNDelimiters(2) 
		+ strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	public String getSegmentDataforWCON(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		srcCode_postprocess = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);

		outputType = OrgEnum.WCON.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "relmemidnum") + strDelim 
		+ "EPDS V2"	+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+ strDelim + /*getString(memRow, "relmemidnum")*/entRecNum + strDelim 
		+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")	+ strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "enrolovrrideind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "enroleffectdt")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "enroltermdt") + strDelim /* CFF 2.7 deleted + strBlank	+ strDelim*/ 
		+ getString(memRow, "hmocntrctcd") + strDelim 
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
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno") 
		// CFF 2.7 new feild : added mapping as per CQ :WLPRD00873914
		+ strDelim + getString(memRow, "relorgmemidnum") + strDelim + getString(memRow, "relorgreltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt")
		// passing trimmed sourccode and reltype for lookup - Source HMO Site Identifier and passing 2 delimiters for 2nd lookup
		+ExtMemgetIxnUtils.getNDelimiters(30)
		// passing trimmed sourccode and reltype for lookup - Source HMO Site Identifier
		+ strDelim + srcCode_postprocess + strDelim + "HMO"
		+ ExtMemgetIxnUtils.getNDelimiters(2)
		+ strDelim + srcCodesDelimited	;
		return segmentData;
	}
	
	public String getSegmentDataforPCLMACT(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PCLMACT.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "claimacttype") + strDelim + getString(memRow, "claimactreason") + strDelim 
		+ getString(memRow, "claimactcrittype") + strDelim + getString(memRow, "claimactlow") + strDelim + getString(memRow, "claimacthigh") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "claimacteffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "claimacttermdt") + strDelim + getString(memRow, "claimactermrsn") 
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ strDelim + srcCodesDelimited;
		return segmentData;
	}
	
	public String getSegmentDataforPTXN(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = OrgEnum.PTXN.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		+ getString(memRow, "taxonomycd") + strDelim + getString(memRow, "taxonomyorgcd") 
		+ strDelim + getString(memRow, "taxonomysetcd") /*+ strDelim + getString(memRow, "attestind") removed as per 2.4*/ 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
		+ strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
	}

	public String getSegmentDataforAPADR(MemRow memRow,long entRecNum) throws Exception
	{
		getMemHeadValues(memRow);
		outputType = OrgEnum.APADR.getValue();
		segmentData = outputType + strDelim + strBlank + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim 
		+ getString(memRow, "addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim +
		ExtMemgetIxnUtils.getNDelimiters(4) + l_strSrcCd + strDelim + l_memIdNum + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") +
		ExtMemgetIxnUtils.getNDelimiters(3) + "0" + ExtMemgetIxnUtils.getNDelimiters(32)+"0" + strDelim 
		//Dec Offcycle new feild
		+ getString(memRow, "primaryaddress")
		+ ExtMemgetIxnUtils.getNDelimiters(59)+ strDelim + srcCode_postprocess;
		return segmentData;
	}
	
	@Override
	public String buildPRFSegment(List<MemAttrRow> OrgPRFMemAttrList, long entRecNum, boolean EPDSV2_Flag) throws Exception{
		outputType = OrgEnum.PRF.getValue();
		String inActive = "Y";
		Date termDate = null;
		Date dtCurrentDate = new Date();
		boolean isV2MemrowPresent = false;
		for (MemRow memRow : OrgPRFMemAttrList){
			inActive = "Y";
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);

			try{
				switch(attrCode) {
				case ORGCATEGORY:	outputType = OrgEnum.PRF.getValue();
				segmentData = outputType+ strDelim + getString(memRow, "DFCDC_evtctime")+ strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim ;
				getHm_PRF().put("MEMHEAD", segmentData);

				segmentData = 	(getString(memRow, "attrval").length()<0 && EPDSV2_Flag ? "182" : getString(memRow, "attrval") )
				+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
				+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim ;
				getHm_PRF().put("ORGCATEGORY", segmentData);
				break;
				
				case INACTIVEORG: if (!isV2MemrowPresent || l_strSrcCd.equalsIgnoreCase(OrgEnum.EPDSV2ORG.getValue())) {
					termDate = memRow.getDate("termdt");
					if(null != termDate){
						inActive = (dtCurrentDate.compareTo(termDate) < 0 ? "N" : "Y");
					}
					segmentData = inActive + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
					+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") 
					+strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
					+ strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim;
					 if(l_strSrcCd.equalsIgnoreCase(OrgEnum.EPDSV2ORG.getValue())){
		            	isV2MemrowPresent = true;
		            	segmentData = getTermAfterValidation(memRow, "INACTIVEORGV2", segmentData , "termdt");
					 }
					 else if(!isV2MemrowPresent) {
		            	segmentData= getTermAfterValidation(memRow,"INACTIVEORG",segmentData,"termdt");
					 }
					getHm_PRF().put("INACTIVEORG", segmentData);
				}
				break;
				
				case ORGNAME: {
					segmentData= getString(memRow, "attrval") +strDelim ;
					getHm_PRF().put("ORGNAME", segmentData);

					segmentData = getString(memRow, "DFCDC_evtinitiator") + strDelim 
					+ getString(memRow, "DFCDC_evtctime") + strDelim 
					+ getString(memRow, "DFCDC_mAudRecno")+ strDelim;
					getHm_PRF().put("ORGNAME_2", segmentData);

					segmentData = getString(memRow, "DFCDC_evtinitiator") + strDelim 
					+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim;
					getHm_PRF().put("ORGNAME_1", segmentData);
				}
				break;
				
				case REMPAYIND: 	segmentData = getString(memRow, "attrval") + strDelim ;
				getHm_PRF().put("REMPAYIND", segmentData);
				break;
				
				case ORGTYPE: 		segmentData= /*getString(memRow, "attrval")*/strBlank 
				+ strDelim +getString(memRow, "DFCDC_evtinitiator") + strDelim 
				+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")
				+ ExtMemgetIxnUtils.getNDelimiters(30);//removing strBlank --check PRF data
				getHm_PRF().put("ORGENTTYPE", segmentData);
				break;
				
				case ORGNPIELIG: 	segmentData= getString(memRow, "codeval") 
				+strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
				+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim;
				getHm_PRF().put("ORGNPIELIG", segmentData);
				break;

				case ORGPARIND:		segmentData= getString(memRow, "codeval") + strDelim ;
				getHm_PRF().put("ORGPARIND", segmentData);
				break;
				case ORGCACTUSFCL: 	segmentData = getString(memRow, "attrval") + strDelim ;/*+ strDelim + srcCodesDelimited*/;
				getHm_PRF().put("ORGCACTUSFCL", segmentData);
				break;
				case ORGGBDCD:
					segmentData = getString(memRow, "attrval");
					getHm_PRF().put("ORGGBDCD", segmentData);
				//PRF-End
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getSegmentDataforPRF(entRecNum, EPDSV2_Flag);
	}
	
	@Override
	public String getSegmentDataforPRF(long entRecNum, boolean EPDSV2_Flag) {
		
		//write PRF only if any of the field is not null 
		if (null == hm_PRF.get("ORGCATEGORY")&&(null!=hm_PRF.get("INACTIVEORG") 
				||null!=hm_PRF.get("ORGNAME")||null!=hm_PRF.get("ORGENTTYPE")
				||null!=hm_PRF.get("ORGNPIELIG")||null!=hm_PRF.get("ORGPARIND")
				||null!= hm_PRF.get("REMPAYIND"))) {
			outputType = OrgEnum.PRF.getValue();
			segmentData = outputType+ strDelim + strBlank + strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim ;
			hm_PRF.put("MEMHEAD", segmentData);
			hm_PRF.put("ORGCATEGORY", (EPDSV2_Flag ? "182" : "") /*strBlank*/+ ExtMemgetIxnUtils.getNDelimiters(3)+ "0" + strDelim);
		}

		if(null ==hm_PRF.get("MEMHEAD")) {
			hm_PRF.put("MEMHEAD", ExtMemgetIxnUtils.getNDelimiters(4));
		}

		if (null == hm_PRF.get("INACTIVEORG")) {
			/*hm_PRF.put("INACTIVEORG", ExtMemgetIxnUtils.getNDelimiters(7));*/
			//CDC Indicator fix : default to: 0
			hm_PRF.put("INACTIVEORG", ExtMemgetIxnUtils.getNDelimiters(6)+  "0" + strDelim );
		}

		if (null == hm_PRF.get("ORGNAME")) {
			/*hm_PRF.put("ORGNAME", ExtMemgetIxnUtils.getNDelimiters(4));*/
			//CDC Indicator fix : default to: 0
			hm_PRF.put("ORGNAME", ExtMemgetIxnUtils.getNDelimiters(1)/*3)+"0" + strDelim*/ );
		}
		if (null == hm_PRF.get("ORGENTTYPE")) {
			/*hm_PRF.put("ORGENTTYPE", ExtMemgetIxnUtils.getNDelimiters(32)*/
			//CDC Indicator fix : default to: 0
			hm_PRF.put("ORGENTTYPE", ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim + ExtMemgetIxnUtils.getNDelimiters(29));
		}
		if (null == hm_PRF.get("ORGNPIELIG")) {
			/*hm_PRF.put("ORGNPIELIG", ExtMemgetIxnUtils.getNDelimiters(4));*/
			//CDC Indicator fix : default to: 0
			hm_PRF.put("ORGNPIELIG", ExtMemgetIxnUtils.getNDelimiters(3)+ "0" + strDelim );
		}
		if (null == hm_PRF.get("ORGPARIND")) {
			hm_PRF.put("ORGPARIND", ExtMemgetIxnUtils.getNDelimiters(1) );
		}
		if (null == hm_PRF.get("ORGNAME_1")) {
			/*hm_PRF.put("ORGNAME_1", ExtMemgetIxnUtils.getNDelimiters(3));
			 */					hm_PRF.put("ORGNAME_1", ExtMemgetIxnUtils.getNDelimiters(2) + "0" + strDelim );
		}
		//CFF 2.7 new hashmap
		if (null == hm_PRF.get("ORGNAME_2")) {
			hm_PRF.put("ORGNAME_2", ExtMemgetIxnUtils.getNDelimiters(2) + "0" + strDelim );
		}

		if (null == hm_PRF.get("REMPAYIND")) {
			hm_PRF.put("REMPAYIND", ExtMemgetIxnUtils.getNDelimiters(1));
		}

		if (null == hm_PRF.get("ORGCACTUSFCL")) {
			hm_PRF.put("ORGCACTUSFCL", ExtMemgetIxnUtils.getNDelimiters(1) /*strDelim + srcCodesDelimited*/);
		}
		if (null == hm_PRF.get("ORGGBDCD")) {
			hm_PRF.put("ORGGBDCD", ExtMemgetIxnUtils.getNDelimiters(0) /*strDelim + srcCodesDelimited*/);
		}
		
		segmentData = hm_PRF.get("MEMHEAD")+hm_PRF.get("ORGCATEGORY") + hm_PRF.get("INACTIVEORG") 
		+ hm_PRF.get("ORGNAME") + hm_PRF.get("REMPAYIND") + hm_PRF.get("ORGNAME_2")+ hm_PRF.get("ORGENTTYPE")+ hm_PRF.get("ORGNPIELIG") + hm_PRF.get("ORGPARIND") 
		+ hm_PRF.get("ORGNAME_1")+hm_PRF.get("ORGCACTUSFCL") + hm_PRF.get("ORGGBDCD") ;
		//logInfo("PRF: " + segmentData);
		
		if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
				/*.replace(srcCodesDelimited, strBlank)*/.length() > 0) {
			segmentData +=strDelim + ExtMemgetIxnUtils.getNDelimiters(59) + srcCodesDelimited;
			outputType = OrgEnum.PRF.getValue();
		}
		return segmentData;
	}

	@Override
	public Set<String> buildPSPTSegment(List<MemAttrRow> orgPSPTMemAttrList, long entRecNum) throws Exception {
		outputType = OrgEnum.PSPT.getValue();
		for (MemRow memRow : orgPSPTMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
				case ORGBRDCRT:	if(getString(memRow, "specialtycd").length()>0) {
								segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" 
								+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "specialtycd") + strDelim 
								+ getString(memRow, "primaryspec") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt") + strDelim + getString(memRow, "spectermrsn") + strDelim
								// CFF 2.4 changes start here
								+ getString(memRow, "boardcertcd") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "boardcertdt") + strDelim
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "boardcertexpirydt") + strDelim + getString(memRow, "agencyname") + strDelim
			
								/*// CFF 2.5b changes start here
								+ getString(memRow,"specboardstatus") changed CFF 3.5 field from boardcertstatus to specboardstatus+ strDelim + getString(memRow,"speclifecertind") changed CFF 3.5 field from boardcertlifeind to speclifecertind+ strDelim
								+ ExtMemgetIxnUtils.getDateAsString(memRow,"specrecertdt")changed CFF 3.5 field from boardrecertdt to specrecertdt
								*/
								// CFF 2.5b changes start here
								+ getString(memRow,"boardcertstatus")+ strDelim + getString(memRow,"boardcertlifeind") + strDelim
								+ ExtMemgetIxnUtils.getDateAsString(memRow,"boardrecertdt")+ strDelim + getString(memRow,"speccactuscredind")/*changed CFF 2.8 field from cactuscredind to speccactuscredind*/+ strDelim  
								// CFF 2.5b changes ends here			
			
								// CFF 2.4 changes end here
								+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim; 
			
								hm_PSPT_ORGBRDCRT.put(getString(memRow, "md5key") + "-" 
										+ getString(memRow, "mds5addrtype")+ "-"
										+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
										+ getString(memRow, "specialtycd")+"-"
										+ ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+"-"
										+ getString(memRow, "primaryspec") + "-"
										+ new Long(memRow.getMemRecno()).toString(), segmentData);
								}
								break;
				case SPLTYSVCORG: {
								String strDFCDC_evtinitiator = getString(memRow, "DFCDC_evtinitiator");
								String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
								String strDFCDC_mAudRecno = getString(memRow, "DFCDC_mAudRecno");
								segmentData = getString(memRow, "specialtysvc1") 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno 
								+ strDelim + getString(memRow, "specialtysvc2") 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno
								+ strDelim + getString(memRow, "specialtysvc3") 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno 
								+ strDelim + strBlank 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno 
								+ strDelim + strBlank 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno
								+ strDelim;
								hm_PSPT_SPC.put(getString(memRow, "md5key") + "-" 
										+ getString(memRow, "mds5addrtype")+ "-"
										+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
										+ getString(memRow, "specialty")+"-"+ new Long(memRow.getMemRecno()).toString(),segmentData);
								}
								break;
				case SPLTYTXNMORG:{
								String strDFCDC_evtinitiator = getString(memRow, "DFCDC_evtinitiator");
								String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
								String strDFCDC_mAudRecno = getString(memRow, "DFCDC_mAudRecno");
								segmentData = getString(memRow, "SPECTAXONOMY1") 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno 
								+ strDelim + getString(memRow, "SPECTAXONOMY2") 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno
								+ strDelim + getString(memRow, "SPECTAXONOMY3") 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno 
								+ strDelim + strBlank 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno 
								+ strDelim + strBlank 
								+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno
								+ strDelim /*+ srcCodesDelimited*/;
								hm_PSPT_TXNMY.put(getString(memRow, "md5key") + "-" 
										+ getString(memRow, "mds5addrtype")+ "-"
										+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
										+ getString(memRow, "specialty")+"-"+ new Long(memRow.getMemRecno()).toString(),segmentData);
								}
								break;
			}
		}
		
		return generatePSPTSegments(entRecNum);
		
	}
	
	public Set<String> generatePSPTSegments(long entRecNum) throws Exception {
		Set <String>PSPT_Keys = new HashSet<String>();
		Set<String> segmentDataSet = new HashSet<String>();
		//Required field check:
		//if hm_PSPT_ORGBRDCRT is present only then create PSPT segment
		if(null!= hm_PSPT_ORGBRDCRT && hm_PSPT_ORGBRDCRT.size()>0 ) {
			//get the keys
			PSPT_Keys = hm_PSPT_ORGBRDCRT.keySet();

			String split_keys[];
			for (Iterator<String> iterator = PSPT_Keys.iterator(); iterator.hasNext();) {
				String PSPT_Key = iterator.next();
				split_keys= PSPT_Key.split("-");
				//ORGBRDCRT
				if (null != hm_PSPT_ORGBRDCRT.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5] +"-" + split_keys[6]))
				{
					//SPLTYSVCORG	
					if (null == hm_PSPT_SPC.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6]))
					{
						segmentData = ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim
						+ ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim
						+ ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim ;

						hm_PSPT_SPC.put( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6], segmentData);
					}
					//SPLTYSVCORG-END

					//SPLTYTXNMORG 
					if (null == hm_PSPT_TXNMY.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6])) 
					{
						segmentData = ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" 
						+ strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim
						+ ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim /*+ srcCodesDelimited*/;
						hm_PSPT_TXNMY.put( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6],segmentData);
					}
					//SPLTYTXNMORG-END

					//create a full segment for PSPT
					segmentData = hm_PSPT_ORGBRDCRT.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6]) 
					+ hm_PSPT_SPC.get(  split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6]) 
					+ hm_PSPT_TXNMY.get(  split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6]) + ExtMemgetIxnUtils.getNDelimiters(60) + srcCodesDelimited;
					outputType = OrgEnum.PSPT.getValue();
					segmentDataSet.add(segmentData);
					
				}
			}
		}
		return segmentDataSet;
	}

	@Override
	public Set<String> buildPADRSegment(List<MemAttrRow> orgPADRMemAttrList, long entRecNum) throws Exception {
		outputType = OrgEnum.PADR.getValue();
		for (MemRow memRow : orgPADRMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			 case SERVLOCORG:
			 case ORGCORRLOC :
			 case ORGREMITLOC:
			 case ORG1099ADDR:
			 case ORGBILLADDR:
			 case ORGCAPADDR:
			 case ORGCSAADDR:
			 case ORGADDRNA:
			 case ORGCAPCKADDR:
			 case ORGPAYINADDR:
			 case ORGHMORELADR: {
								if(getString(memRow, "addrtype").length() > 0 
										&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") && ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt").length() > 0
										&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")&& ExtMemgetIxnUtils.getDateAsString(memRow, "termdt").length() > 0
										&& getString(memRow, "state").length() > 0
								/*&& getString(memRow, "zipcode").length() > 0*/) {
									String strDFCDC_evtinitiator = getString(memRow, "DFCDC_evtinitiator");
									String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
									String strDFCDC_mAudRecno = getString(memRow, "DFCDC_mAudRecno");
									segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
									+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
									+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "stline1") 
									+ strDelim + getString(memRow, "stline2") + strDelim + getString(memRow, "stline3") + strDelim + getString(memRow, "city") 
									+ strDelim + getString(memRow, "state") + strDelim + getString(memRow, "zipcode") + strDelim + getString(memRow, "zipextension") 
									+ strDelim + getString(memRow, "countycd") + strDelim + strBlank + strDelim + getString(memRow, "country") + strDelim + getString(memRow, "primaryaddress") 
									+ strDelim + strDFCDC_evtinitiator  + strDelim + strDFCDC_evtctime + strDelim 
									+ strDFCDC_mAudRecno + strDelim + getString(memRow, "attrval1") 
									+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") 
									+ strDelim + strDFCDC_evtinitiator + strDelim + strDFCDC_evtctime + strDelim + strDFCDC_mAudRecno + strDelim + getString(memRow, "suppresstdzn") 
									+ strDelim +getString(memRow, "addrstatefipscd") +	strDelim +getString(memRow, "addrcountyfipscd")+ strDelim
									+ getString(memRow, "geocode1") + strDelim + getString(memRow, "geocode2") + strDelim /*+ getString(memRow, "attrval3")+ strDelim - Doing Business as name - Deleted as per 2.5b*/;
				
									hm_PADR.put(getString(memRow, "md5key")+ getString(memRow, "addrtype")+ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+"-"+
											ExtMemgetIxnUtils.getDateAsString(memRow,"termdt") , segmentData);
								}
								break;
			 				}
			 case ORGFACSTERE: 	segmentData =/*getString(memRow,"attrval")+ strDelim + - Deleted as per 2.5 b*/ ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt")
								+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"termdt")/*+ strDelim + srcCodesDelimited;*/;
								hm_ORGFACSTERE.put(getString(memRow, "md5key")+ getString(memRow, "MDS5ADDRTYPE")+ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT") , segmentData);
							
								break;
						}
					}
		return generateSegmentsforPADR(hm_PADR, hm_ORGFACSTERE);
		
	}
	
	/**
	 * PADR multiple segments  for 2.4
	 * @param hm_PADR
	 * @param hm_ORGFACSTERE
	 * @param entRecNum
	 */
	public Set<String> generateSegmentsforPADR(HashMap<String,String>hm_PADR, HashMap<String,String>hm_ORGFACSTERE) throws Exception {
		Set <String>PADR_Keys = new HashSet<String>();
		Set<String> segmentDataSet = new HashSet<String>();
		String split_keys[];
		//If PADR hash map not created 
		if(!hm_PADR.isEmpty())
		{
			PADR_Keys = hm_PADR.keySet();
			for (Iterator<String> iterator = PADR_Keys.iterator(); iterator.hasNext();) 
			{
				String segment_ORGFACSTERE;
				String PADR_Key =  iterator.next();
				split_keys = PADR_Key.split("-");
				if(null!= hm_PADR.get(PADR_Key))
				{
					segment_ORGFACSTERE =hm_ORGFACSTERE.get(split_keys[0]);
					if(segment_ORGFACSTERE==null){
						segment_ORGFACSTERE = strDelim;
					}
					segmentData =hm_PADR.get(PADR_Key)+segment_ORGFACSTERE + ExtMemgetIxnUtils.getNDelimiters(60)+ strDelim + srcCodesDelimited;;
					outputType = OrgEnum.PADR.getValue();
					segmentDataSet.add(segmentData);
					
				}
			}
		}
		return segmentDataSet;

		}

	@Override
	public Set<String> buildPALTSegment(List<MemAttrRow> orgPALTMemAttrList, long entRecNum) throws Exception {
		outputType = OrgEnum.PALT.getValue();
		for (MemRow memRow : orgPALTMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			//PALT-Begin
			case ALTSYSIDSORG:
			case NPIORG:
			case UPINORG: 
			case MEDICAREORG: 
			case MEDICAIDORG: 
			case ENCLRTYIDORG: 
			case LICENSEORG: {
									//filter-Begin
									//Required fields added as per 2.5b
									if(/*getString(memRow, "idissuer").length() > 0*/ //Made Optional
											getString(memRow,"idnumber").length() > 0
											/*&& getString(memRow,"idtype").length() > 0*/
											&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") && ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate").length() > 0
											&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") && ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate").length() > 0)
										//Required fields added as per 2.5b
									{
										if(l_strAttrCode.equalsIgnoreCase("LICENSEORG")&& getString(memRow,"licensetype").length() > 0) {
											String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
											segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
											+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
											+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "idissuer") + strDelim
											+ getString(memRow, "idnumber") + strDelim + getString(memRow, "licensetype") /*getString(memRow, "idtype") idtype field not present for attrcode LICENSEORG */
											+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim 
											+ ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "termrsn") + strDelim 
											+ strBlank + strDelim + getString(memRow, "certnumber") + strDelim + getString(memRow, "status") 
											+ strDelim + getString(memRow, "agencyname") + strDelim 
											//mappings added in Aug release
											+ getString(memRow, "agencytypecd") + strDelim + getString(memRow, "agencyacrtxt") + strDelim 
											+ ExtMemgetIxnUtils.getDateAsString(memRow, "agencyeffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "agencytermdt") + strDelim 
											+ getString(memRow, "agencytrmrsn") + strDelim + getString(memRow, "agencyemail") + strDelim 
											+ getString(memRow, "agencyurl")	+ strDelim + getString(memRow, "agcytelarcd") + strDelim 
											+ getString(memRow, "agencytelnum")
											/* CFF 2.6 deleted + strDelim + getString(memRow, "relmemidnum") + strDelim 
											+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "releffectdt") + strDelim 
											+ ExtMemgetIxnUtils.getDateAsString(memRow, "reltermdt") + strDelim + getString(memRow, "reltermrsn") */+ strDelim 
											+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime 
											+ strDelim + getString(memRow, "DFCDC_mAudRecno")
											// CFF 2.6 New feild 
											+ strDelim + getString(memRow, "npiedsregind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"npiedseffectdt")+strDelim;
											//+ strDelim + srcCodesDelimited;
											String key = getString(memRow, "IDNUMBER");
											if (key.contains("-")) 
												key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
													hm_PALT.put(getString(memRow, "licensetype")+"-"  +key+"-" + getString(memRow, "md5key")+"-" 
													+ getString(memRow, "MDS5ADDRTYPE")+"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"
													+ getString(memRow,"memrecno")+"-"+ getString(memRow, "termrsn")+ "-"
													+ ExtMemgetIxnUtils.getDateAsString(memRow,"idissuedate") +"-"
													+ ExtMemgetIxnUtils.getDateAsString(memRow,"idexpdate"), segmentData );
											
										}
										else if(getString(memRow,"idtype").length() > 0) {
					
											String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
											segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
											+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
											+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "idissuer") 
											+ strDelim + getString(memRow, "idnumber") + strDelim + getString(memRow, "idtype") + strDelim 
											+ ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
											+ strDelim + getString(memRow, "idtermrsn") + strDelim + strBlank + strDelim 
											+ strBlank + strDelim + strBlank + strDelim 
											+ strBlank + strDelim  + getString(memRow, "agencytypecd") + strDelim + getString(memRow, "agencyacrtxt") + strDelim +
											 strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim 
											+ strBlank + strDelim + strBlank + strDelim + strBlank + strDelim 
											
												/* CFF 2.6 deleted + strDelim + getString(memRow, "relmemidnum")
												+ strDelim + getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "releffectdt") 
												+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reltermdt") + strDelim + getString(memRow, "reltermrsn") */
											+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
											+ strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
											// CFF 2.6 New feild 
											+ strDelim + getString(memRow, "npiedsregind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"npiedseffectdt")+strDelim;
											//+ strDelim + srcCodesDelimited;
											String key = getString(memRow, "IDNUMBER");
											if (key.contains("-")) 
												key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
													hm_PALT.put(getString(memRow, "IDTYPE")+"-" +key+"-" + 
															getString(memRow, "md5key")+"-" + 
															getString(memRow, "MDS5ADDRTYPE")+"-" + 
															ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"+ 
															getString(memRow,"memrecno")+"-"+ getString(memRow, "idtermrsn")+ "-"+
															ExtMemgetIxnUtils.getDateAsString(memRow,"idissuedate") +"-"+
															ExtMemgetIxnUtils.getDateAsString(memRow,"idexpdate") , segmentData );
											
										}
									}
								}
								break;
			case ORGALTIDSPEC: {
								String key = getString(memRow, "IDNUMBER");
								if (key.contains("-")) 
									key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
								String spec_key = getString(memRow, "IDTYPE")+"-" + key+"-" + 
													getString(memRow, "md5key")+"-" + 
													getString(memRow, "MDS5ADDRTYPE")+"-" + 
													ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT") +"-" + 
													getString(memRow,"memrecno");
								segmentData = getString(memRow, "specialtycd") + strDelim + 
												ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+ strDelim; 
								ArrayList<String>speciality_list = new ArrayList<String>();
								if(ExtMemgetIxnUtils.isNotEmpty(hm_PALT_Alt_ID_Specialty)) {
									if(hm_PALT_Alt_ID_Specialty.containsKey(spec_key)) {//if contain key
										speciality_list = hm_PALT_Alt_ID_Specialty.get(spec_key);
									}
								}
								speciality_list.add(segmentData);
								hm_PALT_Alt_ID_Specialty.put(spec_key, speciality_list);
								}
								break;
			}
		}
		return generateSegmentsforPALT();
	}
	
	private Set<String> generateSegmentsforPALT()  throws Exception
	{
		Set<String> segmentDataSet = new HashSet<String>();
		HashMap<String,String> hm_PALT_Alt_ID_Specialty1 = new HashMap<String,String>();
		if (null != hm_PALT && hm_PALT.size() > 0) {			
			if(null != hm_PALT_Alt_ID_Specialty && hm_PALT_Alt_ID_Specialty.size() > 0){			
				String ProvAltIDSpecialty1_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);//changing into array can help
				String ProvAltIDSpecialty2_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);//changing into array can help
				String ProvAltIDSpecialty3_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);//changing into array can help
				String ProvAltIDSpecialty4_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);//changing into array can help
				String ProvAltIDSpecialty5_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);//changing into array can help
				Set<String> hm_Org_Alt_ID_Specialty_keys = new HashSet<String>();
				hm_Org_Alt_ID_Specialty_keys = hm_PALT_Alt_ID_Specialty.keySet();
				for (Iterator<String> iterator1 = hm_Org_Alt_ID_Specialty_keys.iterator(); iterator1.hasNext();) {
					String hm_Org_Alt_ID_Specialty_key= (String) iterator1.next();
					//String[] split_keys1 ; 
					//split_keys1 = hm_Org_Alt_ID_Specialty_key.split("-");
					ArrayList<String> valueList = hm_PALT_Alt_ID_Specialty.get(hm_Org_Alt_ID_Specialty_key);
					int count = valueList.size();
					for (Iterator<String> iterator_value = valueList.iterator(); iterator_value.hasNext();) {
						String spec_info = (String) iterator_value.next();						
						switch (count)
						{
						case 1 : ProvAltIDSpecialty1_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
								 break;
						case 2 : ProvAltIDSpecialty2_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
								 break;
						case 3 : ProvAltIDSpecialty3_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
								 break;
						case 4 : ProvAltIDSpecialty4_segmentData1 = spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
								 break;
						case 5 : ProvAltIDSpecialty5_segmentData1 =spec_info!=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
								 break;
						}	
						count --;
					}
					segmentData = ProvAltIDSpecialty1_segmentData1 +ProvAltIDSpecialty2_segmentData1+ProvAltIDSpecialty3_segmentData1+ProvAltIDSpecialty4_segmentData1+ProvAltIDSpecialty5_segmentData1+ExtMemgetIxnUtils.getNDelimiters(60)+srcCodesDelimited;
					hm_PALT_Alt_ID_Specialty1.put( hm_Org_Alt_ID_Specialty_key, segmentData);
					segmentData = "";
				}
			}
			Set <String>PALT_Keys = new HashSet<String>();
			String split_keys[];						
			//get the keys
			PALT_Keys = new HashSet<String>(hm_PALT.keySet());
			//PALT_Keys = hm_PALT1.keySet();
			for (Iterator<String> iterator = PALT_Keys.iterator(); iterator.hasNext();) 
			{
				String PALT_Key = iterator.next();
				split_keys = PALT_Key.split("-");	
				String specialityKey = split_keys[0]+"-"+split_keys[1]+"-"+split_keys[2]+"-"+split_keys[3]+"-"+split_keys[4]+"-"+split_keys[5];
				if (null == hm_PALT_Alt_ID_Specialty.get(specialityKey))
				{
					segmentData = ExtMemgetIxnUtils.getNDelimiters(10)+ExtMemgetIxnUtils.getNDelimiters(60)+ srcCodesDelimited;
					hm_PALT_Alt_ID_Specialty1.put(specialityKey, segmentData);
				}						
				//create a full segment						
				segmentData=hm_PALT.get(PALT_Key) +
				hm_PALT_Alt_ID_Specialty1.get(specialityKey);
				outputType = OrgEnum.PALT.getValue();
				segmentDataSet.add(segmentData);
			}					
		}
		return segmentDataSet;
	}

	
	public String getSegmentDataforPREM(MemRow memRow, long entRecNum) throws Exception {
		segmentData = strBlank;
		if(getString(memRow, "remitaddrtype").length() > 0 
				&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") && ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt").length() > 0
				&& getString(memRow, "fedtaxid").length() > 0 
				&& getString(memRow, "fedtaxidtype").length() > 0 
				//Additional filter check removed as per onsite instructions
				//reverted so as to fix CQ:WLPRD00611337
				&& getString(memRow, "remitchecknm").length() > 0
				//2.5b changes-Begin
				&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegeffdt") && ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegeffdt").length() > 0
				&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegtermdt") && ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegtermdt").length() > 0){
			
			getMemHeadValues(memRow);
			String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
			srcCode_postprocess = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
			outputType = OrgEnum.PREM.getValue();
			segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
			+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "remitaddrtype") + strDelim 
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") + strDelim + getString(memRow, "fedtaxid") + strDelim 
			+ getString(memRow, "fedtaxid1099") + strDelim + getString(memRow, "fedtaxidtype") + strDelim 
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "fedtaxideffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "fedtaxidtermdt") 
			+ strDelim + getString(memRow, "fedtaxidtermrsn") + strDelim + getString(memRow, "altidsource") 
			+ strDelim + getString(memRow, "altidnumber") + strDelim + getString(memRow, "altidtype") + strDelim 
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "altidissuedt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "altidtermdt") + strDelim 
			+ getString(memRow, "altidissuer") + strDelim + getString(memRow, "payidsrc")+ strDelim + getString(memRow, "payid")
			+ strDelim + getString(memRow, "payidtype")
			+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "payidissuedt")
			+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "payidtermdt")
			+ strDelim + getString(memRow, "remitchecknm") + strDelim + getString(memRow, "eftroutenum") + strDelim /*Added for modified 2.4*/
			+ getString(memRow, "eftaccountnum") + strDelim + getString(memRow, "eremadvaddrtxt") 
			+ strDelim + getString(memRow, "lockboxaddrtxt") + strDelim + getString(memRow, "remitmethodcd") + strDelim 
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegeffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegtermdt") + strDelim 
			+ getString(memRow, "remitsegtermrsn") + strDelim + getString(memRow, "remadvmethodcd") + strDelim 
			/*+ getString(memRow, "taxexemptind") + strDelim + getString(memRow, "primarytaxind") + strDelim Deleted as per modified 2.4*/ 
			+ getString(memRow, "w9receipttype") 
			+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "w9receiptdt") + strDelim + getString(memRow, "payadjusttype") + strDelim 
			+ getString(memRow, "provsubscpaytype") + strDelim + getString(memRow, "remitlocid") + strDelim 
			+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime
			+ strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim + /*memRow.getMemRecno()*/getString(memRow, "relatedprovid")
			+ strDelim + getString(memRow,"relatedprovreltype")/*strBlank*/ + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relatedprovreleffectdt")/*strBlank*/ /*Added new fields for 2.4*/
			/*Added a new field to hold 
			 *the trimmed source code (for example FACETS,CPF,ACES..)*/
			+ strDelim + ExtMemgetIxnUtils.getNDelimiters(60) + srcCode_postprocess+ strDelim + " "  +strDelim + srcCodesDelimited; ;
		}
		return segmentData;
	}

	@Override
	public String getSegmentDataforPRMB(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);

		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),prop_relTypeCode);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		String rmbsrccode = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "RMB";
		String sptyEffectiveDate1="",sptyEffectiveDate2="";
		String sptyEffectiveDate3 ="",sptyEffectiveDate4="",sptyEffectiveDate5 ="";
		if (getString(memRow, "specialty1").length()>0){sptyEffectiveDate1 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality1effectdt");}
		if (getString(memRow, "specialty2").length()>0){sptyEffectiveDate2 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality2effectdt");}
		if (getString(memRow, "specialty3").length()>0){sptyEffectiveDate3 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality3effectdt");}
		if (getString(memRow, "specialty4").length()>0){sptyEffectiveDate4 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality4effectdt");}
		if (getString(memRow, "specialty5").length()>0){sptyEffectiveDate5 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality5effectdt");}
		//Changes done for 2.4
		outputType = OrgEnum.PRMB.getValue();
		segmentData = outputType+ strDelim +strDFCDC_evtctime+ 
		strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim +getString(memRow, "md5key")
		+ strDelim +getString(memRow, "mds5addrtype")+ strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+ strDelim 
		+getString(memRow, "relatedid")+ strDelim +getString(memRow, "reltype")
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "releffdt")+ strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "reltermdt")+ strDelim +getString(memRow, "reltermrsn")
		+ strDelim +getString(memRow, "specialty1")+ strDelim +sptyEffectiveDate1+ strDelim 
		+getString(memRow, "specialty2")+ strDelim +sptyEffectiveDate2+ strDelim 
		+getString(memRow, "specialty3")+ strDelim +sptyEffectiveDate3+ strDelim 
		+getString(memRow, "specialty4")+ strDelim +sptyEffectiveDate4+ strDelim 
		+getString(memRow, "specialty5")+ strDelim +sptyEffectiveDate5
		+ ExtMemgetIxnUtils.getNDelimiters(11) +rmbsrccode/*Reimbursement Identifier System of Record 
				field should have source name + RMB*/
		+ strDelim + /*rmbarrangeid+ strDelim 
				+REI+ strDelim +agreeid+ strDelim +AGR+ strDelim +panelid+ strDelim +PLI*/
		getString(memRow, "rmbarrangeid")+ strDelim +getString(memRow, "rmbarrangeidtype")+ strDelim +
		getString(memRow, "agreeid")+ strDelim +getString(memRow, "agreeidtype")+ strDelim +
		getString(memRow, "panelid")+ strDelim +getString(memRow, "panelidtype")+ strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangeeffdt")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangetermdt")
		+ strDelim +getString(memRow, "rmbarrangetermrsn")+ strDelim + getString(memRow,"wgscontcode")
		+ strDelim +ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "NET"
		/*replaced EPDSV2*/
		/*Network Identifier Source Code field should have source name + NET*/
		+ strDelim +getString(memRow, "networkid")+ strDelim 
		+getString(memRow, "networkidtype")+ strDelim +getString(memRow, "ctrctversion")+ strDelim 
		+getString(memRow, "ctrctinclusion")+ strDelim +getString(memRow, "reimbtiercode")+ strDelim 
		// new feild in 2.7 starts
		+ getString(memRow, "epdsv2ctrctdays") + strDelim
		//new feild in 2.7 ends
		+getString(memRow, "nwffscaptype")+ strDelim +getString(memRow, "DFCDC_evtinitiator")+ strDelim 
		+strDFCDC_evtctime+ strDelim +getString(memRow, "DFCDC_mAudRecno")+ strDelim 
		//new field in 2.8
		+getString(memRow, "emgphynparstind")+ strDelim + getString(memRow, "patlgypartstind")+ strDelim + getString(memRow, "anestpartstind")+ strDelim + getString(memRow, "radlgypartstind") + strDelim 

		+getString(memRow, "wmsndbgrpind")+ strDelim +getString(memRow, "wmsndbctrlcd")+ strDelim 
		+getString(memRow, "wmsndbctrlvarn")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "wmsndblastcrddt")
		+ strDelim + getString(memRow, "wmsndbstscd")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "wmsndbstsdt")
		+ strDelim + getString(memRow,"wmsndbtaxid")+ strDelim +getString(memRow, "acesspapipind")
		+ strDelim + getString(memRow, "acesspapipfrqind")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow,"acesspapipeffectdt")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"acesspapipenddt")+ strDelim
		//2.5b Changes -Begin
		+ getString(memRow, "prmptpyind")+ strDelim 
		+ getString(memRow, "prmptpctdiscamt")+ strDelim
		+ getString(memRow, "rmtmethcd")+ strDelim 
		//2.5b Changes -End
		+ l_strSrcCd+ strDelim +l_memIdNum + strDelim
		// new feild in 2.7 starts
		+ getString(memRow, "facetscaptype") + strDelim + getString(memRow, "facetsprefix")	+ strDelim 	
		+ getString(memRow, "facetscapreltype") + strDelim + getString(memRow, "facetscrprid") + strDelim 
		+ getString(memRow, "facetsadjper") + strDelim + getString(memRow, "facetscapcycleid") + strDelim 
		+ getString(memRow, "directoryind") + strDelim + getString(memRow, "dirdisplayopt")
		//new feild in 2.7 ends
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS,CPF,ACES..) + reltypecode*/
		//new field in 2.8
		+ strDelim + getString (memRow, "acptnewptind")
		+ strDelim  +/*Cff 3.8 changes*/getString(memRow, "relatedid") +strDelim+ ExtMemgetIxnUtils.getNDelimiters(59)+ /*srcCode_postprocess*/srccode_lookup+ strDelim + reltype_code + strDelim + srcCode_postprocess;

		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPNET(MemRow memRow,long entRecNum) throws Exception
	{
		/*Getting the values from MEMHEAD_MAP against each memRecno*/
		getMemHeadValues(memRow);
		
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),prop_relTypeCode);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		String netsrccode = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "NET";
		//Added for speciality and speciality effective date
		String sptyEffectiveDate1="",sptyEffectiveDate2="";
		String sptyEffectiveDate3 ="",sptyEffectiveDate4="",sptyEffectiveDate5 ="";
		//2.5b changes : changed the mapping of sptyEffectiveDate1..5 from nweffectdt to sptyEffectiveDate1..5
		if (getString(memRow, "specialty1").length()>0){sptyEffectiveDate1 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty1effdt");}
		if (getString(memRow, "specialty2").length()>0){sptyEffectiveDate2 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty2effdt");}
		if (getString(memRow, "specialty3").length()>0){sptyEffectiveDate3 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty3effdt");}
		if (getString(memRow, "specialty4").length()>0){sptyEffectiveDate4 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty4effdt");}
		if (getString(memRow, "specialty5").length()>0){sptyEffectiveDate5 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty5effdt");}
		//2.5b changes :End
		outputType = OrgEnum.PNET.getValue();
		segmentData = outputType+ strDelim +strDFCDC_evtctime+ strDelim +entRecNum+ strDelim +"EPDS V2"
		+ strDelim +getString(memRow, "md5key")+strDelim +getString(memRow, "mds5addrtype")
		+strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+strDelim 
		+ /*memRow.getMemRecno()*/getString(memRow, "relatedid")+ strDelim +getString(memRow, "reltype")
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "releffdt")+strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "reltermdt")
		+strDelim +getString(memRow, "reltermrsn")+strDelim +strBlank+strDelim 
		+getString(memRow, "specialty1")+strDelim + sptyEffectiveDate1/*ExtMemgetIxnUtils.getDateAsString(memRow, "nweffectdt")*/
		+strDelim +strBlank+strDelim +getString(memRow, "specialty2")+strDelim + sptyEffectiveDate2/*ExtMemgetIxnUtils.getDateAsString(memRow, "nweffectdt")*/
		+strDelim +strBlank+strDelim +getString(memRow, "specialty3")+strDelim + sptyEffectiveDate3/*ExtMemgetIxnUtils.getDateAsString(memRow, "nweffectdt")*/
		+strDelim +strBlank+strDelim +getString(memRow, "specialty4")+strDelim + sptyEffectiveDate4/*ExtMemgetIxnUtils.getDateAsString(memRow, "nweffectdt")*/
		+strDelim +strBlank+strDelim +getString(memRow, "specialty5")+strDelim + sptyEffectiveDate5 /*ExtMemgetIxnUtils.getDateAsString(memRow, "nweffectdt")*/
		+ExtMemgetIxnUtils.getNDelimiters(16) +netsrccode /*replaced EPDSV2*/
		/*Network Identifier Source Code field should have source name + NET*/
		+ strDelim +getString(memRow, "networkid")+strDelim 
		// CFF 2.7 new feild
		+ getString (memRow, "networkidtype") + strDelim 
		// CFF 2.7 new feild
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "nweffectdt")+strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "nwtermdt")
		+ strDelim +getString(memRow, "nwtermrsn")+strDelim +getString(memRow, "nwacceptpats")
		+ strDelim +getString(memRow, "nwacceptmdcdpats")+strDelim +getString(memRow, "nwacceptmdcrpats")
		+ strDelim +getString(memRow, "nwpatgenderpref")+strDelim +getString(memRow, "nwpatagepreffrom")
		+ strDelim +getString(memRow, "nwpatageprefto")+strDelim +getString(memRow, "nwparind")+strDelim 
		+ getString(memRow, "nwpcpspectype")+strDelim +getString(memRow, "nwtimeoffiledays")+strDelim 
		+ getString(memRow, "nwdirdisplayind")+strDelim +getString(memRow, "nwpcpcurrmemcnt")+strDelim 
		+ getString(memRow, "nwpcpmemcapcnt")+strDelim 
		//2.5c changes
		+ getString(memRow, "nwownerid")+strDelim
		+ getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim + strDFCDC_evtctime + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")+strDelim
		//CFF 2.8 changes
		+ getString(memRow, "emgphynparstind") + strDelim +getString(memRow, "patlgypartstind")+ strDelim 
		+ getString(memRow, "anestpartstind")+ strDelim + getString(memRow, "radlgypartstind")
		+ strDelim+getString(memRow, "acespar")+strDelim 
		+ getString(memRow, "acesaqiind")+strDelim +getString(memRow, "acesaqipercent")+strDelim 
		//2.5b changes -Begin
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "aprveffdt")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "aprvenddt")+strDelim
		+ getString(memRow, "aprvind")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcpneteffdt")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcpnetenddt")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcpmenteffdt")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcpmentenddt")+strDelim
		//2.5b changes -End
		+ getString(memRow ,"direcdispind") + strDelim //Added as per 2.4
		+ l_strSrcCd+strDelim +l_memIdNum + strDelim
		+ getString(memRow, "netparcd") 
		+ strDelim + getString(memRow,"agecattype") +/*Cff 3.8 changes*/strDelim +  getString(memRow, "relatedid")
		+ExtMemgetIxnUtils.getNDelimiters(59) + /*srcCode_postprocess*/srccode_lookup + strDelim + reltype_code+ strDelim +srcCode_postprocess;
		
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPWTH(MemRow memRow, long entRecNum) throws Exception {
		/*Getting the values from MEMHEAD_MAP against each memRecno*///taken from org
		getMemHeadValues(memRow);
		outputType = OrgEnum.PWTH.getValue();
		segmentData = outputType  + strDelim + getString(memRow, "DFCDC_evtctime")  + strDelim + entRecNum + strDelim + "EPDS V2"
		+ strDelim  +/*WLPRD00990801: mds5key to md5key */getString(memRow, "md5key")+ strDelim + getString(memRow,"mds5addrtype")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow,"mds5addreffectdt")+ strDelim  + ExtMemgetIxnUtils.getDateAsString(memRow,"termdt") + strDelim 
		+ getString(memRow,"termrsn")+ strDelim +getString(memRow,"stline1")+ strDelim  +getString(memRow,"stline2")
		+ strDelim + getString(memRow,"stline3")+ strDelim  + getString(memRow,"city")+  strDelim  
		+ getString(memRow,"state")+  strDelim  +getString(memRow,"zip")+  strDelim  +getString(memRow,"zipextension")
		+ strDelim + getString(memRow,"countycd")+  strDelim  + getString(memRow,"country") + strDelim 
		+ getString(memRow,"specialty1")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"SPECLTY1EFFECTDT")+ strDelim 
		+ getString(memRow,"specialty2")+  strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"SPECLTY2EFFECTDT")+  strDelim 
		+ getString(memRow,"specialty3")+  strDelim  +ExtMemgetIxnUtils.getDateAsString(memRow,"SPECLTY3EFFECTDT")+  strDelim  
		+ getString(memRow,"specialty4") + strDelim  + ExtMemgetIxnUtils.getDateAsString(memRow,"SPECLTY4EFFECTDT")+  strDelim  
		+ getString(memRow,"specialty5")+ strDelim  + ExtMemgetIxnUtils.getDateAsString(memRow,"SPECLTY5EFFECTDT")
		+ strDelim  + getString(memRow,"legcyrmbid1") + strDelim  +getString(memRow,"legcyrmbid1typcd") +  strDelim  
		+ getString(memRow,"legcyrmbid2")+ strDelim  + getString(memRow,"legcyrmbid2typcd")+ strDelim + getString(memRow,"legcyrmbid3")
		+ strDelim + getString(memRow,"legcyrmbid3typcd")+  strDelim  + ExtMemgetIxnUtils.getDateAsString(memRow,"legcyrmbeffdt")
		+ strDelim  + ExtMemgetIxnUtils.getDateAsString(memRow,"legcyrmbtrmdt")+  strDelim  + getString(memRow,"legcyrmbrsncd")
		+ strDelim  + getString(memRow,"wgscontcode")+  strDelim  + getString(memRow,"networkid")
		+ strDelim  + getString(memRow,"networkidtype")+  strDelim  + getString(memRow,"withhldperc")
		+ strDelim  + getString(memRow,"withhldtypecd")+  strDelim  + ExtMemgetIxnUtils.getDateAsString(memRow,"withhldeffdt")
		+ strDelim  + ExtMemgetIxnUtils.getDateAsString(memRow,"withhldtermdt")+  strDelim  + getString(memRow,"withhldrsncd")
		+ strDelim  + l_strSrcCd +  strDelim  + l_memIdNum
		+ strDelim +/*CFF 3.8 chnages*/ getString(memRow, "relmemidnum")+ strDelim+ ExtMemgetIxnUtils.getNDelimiters(29)+ srcCode_postprocess;
		
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforAPREL(MemRow memRow,long entRecNum) throws Exception {
		segmentData = strBlank;
		getMemHeadValues(memRow);
		if(	null!=l_strSrcCd && l_strSrcCd.length()>0
				&& null!=l_memIdNum && l_memIdNum.length()>0) {
			reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),prop_relTypeCode);
			srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"), l_strSrcCd);
			outputType = OrgEnum.APREL.getValue();
			segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim 
			+ "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt") 
			+ strDelim +getString(memRow, "relmemidnum") + strDelim + getString(memRow, "entrecno") + strDelim 
			+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim 
			+ getString(memRow, "relattrval1") + strDelim + l_strSrcCd+ strDelim + l_memIdNum + strDelim
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim 
			+ getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
			+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim 
			+getString(memRow, "DFCDC_evtinitiator") + strDelim 
			+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")
			+ strDelim + getString(memRow, "relattrval2") + strDelim + getString(memRow, "relattrval3") 
			+ strDelim + getString(memRow, "relattrval4") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt") 
			+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt")
			+ strDelim + ExtMemgetIxnUtils.getNDelimiters(60)+ srccode_lookup + strDelim 
			+ reltype_code +strDelim + srcCode_postprocess;
		}
		return segmentData;
	}

	@Override
	public String getSegmentDataforAPTXN(MemRow memRow,long entRecNum) throws Exception {
		segmentData = strBlank;
		getMemHeadValues(memRow);
		if(l_strSrcCd !=null && l_memIdNum !=null && l_strSrcCd .length()>0  && l_memIdNum.length()>0 ) {
			outputType = OrgEnum.APTXN.getValue(); 
			segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
			+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
			+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "taxonomycd")
			+ strDelim + getString(memRow, "taxonomyorgcd")+ strDelim + getString(memRow, "taxonomysetcd")
			+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn") + strDelim 
			+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
			+ getString(memRow, "DFCDC_mAudRecno") + strDelim + l_strSrcCd + strDelim + l_memIdNum
			+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")
			+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
			+ getString(memRow, "DFCDC_mAudRecno") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
			+ getString(memRow, "DFCDC_mAudRecno") + ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCode_postprocess;
			
		}
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforAPCLMACT(MemRow memRow,long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = OrgEnum.APCLMACT.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(
											outputType, 
											getString(memRow, "DFCDC_evtctime"),
											Long.toString(entRecNum), 
											"EPDS V2", 
											getString(memRow, "claimacttype"), 
											getString(memRow, "claimactreason"),
											getString(memRow, "claimactcrittype"), 
											getString(memRow, "claimactlow"),
											getString(memRow, "claimacthigh"), 
											ExtMemgetIxnUtils.getDateAsString(memRow, "claimacteffdt"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "claimacttermdt"), 
											getString(memRow, "claimactermrsn"),
											l_strSrcCd, l_memIdNum,
											ExtMemgetIxnUtils.getDateAsString(memRow, "claimacteffdt"), 
											ExtMemgetIxnUtils.getDateAsString(memRow, "claimacttermdt"), 
											getString(memRow, "claimactermrsn"), 
											getString(memRow, "DFCDC_evtinitiator"),
											getString(memRow, "DFCDC_evtctime"), 
											getString(memRow, "DFCDC_mAudRecno"),
											getString(memRow, "acesclaimstpdept"), 
											getString(memRow, "acesprovrestid"),
											getString(memRow, "acesprovreststat"), 
											getString(memRow, "DFCDC_evtinitiator"),
											getString(memRow, "DFCDC_evtctime"), 
											getString(memRow, "DFCDC_mAudRecno"),
											ExtMemgetIxnUtils.getNDelimiters(59), 
											srcCode_postprocess);
		
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforAPGREL(MemRow memRow,long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = OrgEnum.APGREL.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(
											outputType, getString(memRow, "DFCDC_evtctime"),
											getString(memRow, "relmemidnum"), "EPDS V2",
											Long.toString(entRecNum), l_memIdNum,
											l_strSrcCd, getString(memRow, "reltype"), 
											ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), 
											getString(memRow, "termrsn"), getString(memRow, "remitmds5key"),
											getString(memRow, "remitaddrtype"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt"), 
											getString(memRow, "provrelorgid"), getString(memRow, "provrelorgid"),
											getString(memRow, "provrelorgsrc"), getString(memRow, "provrelorgreltype"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgeffectdt"),
											getString(memRow, "md5key"), getString(memRow, "mds5addrtype"), 
											ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
											ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "relmemsrccode"))+ "GRP",
											getString(memRow, "relmemidnum"), strDelim, strBlank,
											getString(memRow, "DFCDC_evtinitiator"), getString(memRow, "DFCDC_evtctime"),
											getString(memRow, "DFCDC_mAudRecno"), getString(memRow, "provaltid"),
											getString(memRow, "provaltidtype"), getString(memRow, "provaltidnm"), 
											getString(memRow , "provgrpaltsrc"),
											ExtMemgetIxnUtils.getDateAsString(memRow , "provaltideffectdt"),
											getString(memRow, "DFCDC_evtinitiator"), getString(memRow, "DFCDC_evtctime"),
											getString(memRow, "DFCDC_mAudRecno"), ExtMemgetIxnUtils.getNDelimiters(59),
											srcCode_postprocess);
		return segmentData;
	}

	public String getSegmentDataforAPCNTC(MemRow memRow,long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = "APCNTC";
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "onmfirst") + strDelim 
		+ getString(memRow, "onmlast") + strDelim + getString(memRow, "onmmiddle") + strDelim + getString(memRow, "rolecd") 
		+ strDelim + getString(memRow, "titlecd") + strDelim + /*getString(memRow, "mds5addrtype") + strDelim+ Removed as per 2.4*/
		getString(memRow, "phicc") + strDelim + getString(memRow, "pharea") + strDelim + getString(memRow, "phnumber") + strDelim + getString(memRow, "phextn") 
		+ strDelim + getString(memRow, "teltype") + strDelim + getString(memRow, "fxarea") + strDelim + getString(memRow, "fxnumber") + strDelim + getString(memRow, "faxtype")
		+ strDelim + getString(memRow, "url") + strDelim + getString(memRow, "urltype")+ strDelim + getString(memRow, "emailaddr") + strDelim + getString(memRow, "emailtype") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim 
		+ l_strSrcCd + strDelim + l_memIdNum + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cntceffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cntctermdt") + strDelim + getString(memRow, "cntctermrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim +
		getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(60)+strDelim + srcCode_postprocess;
		return segmentData;
	}
	
	@Override
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> orgALTSRCIDMemAttrList, long entRecNum) throws Exception {
		outputType=OrgEnum.ALTSRCID.getValue();
		for (MemRow memRow : orgALTSRCIDMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
				case ORGTYPCD: {
						hm_ALTSRCID_Slg.put("ORGTYPCD-"+new Long(memRow.getMemRecno()).toString(),getString(memRow, "codeval")+ strDelim);
						break;
				}
				case INACTIVEORG: {
						segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") 
						+ strDelim + entRecNum + strDelim 
						+ "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim;
						hm_ALTSRCID_Slg.put("MemHead_1-" + new Long(memRow.getMemRecno()).toString(), segmentData);
		
						segmentData = ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
						+ strDelim + getString(memRow, "termrsn")+ strDelim;
						hm_ALTSRCID_Slg.put("INACTIVEORG-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
						break;
					}
				case ORGNAME: {
						segmentData = getString(memRow, "attrval") + strDelim + strBlank + strDelim 
						+ strBlank + strDelim + strBlank + strDelim 
						+ getString(memRow, "DFCDC_evtinitiator")  + strDelim 
						+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim;
						hm_ALTSRCID_Slg.put("ORGNAME_1-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
		
						segmentData = getString(memRow, "DFCDC_evtinitiator")  + strDelim 
						+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")/*+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd)*/;
						hm_ALTSRCID_Slg.put("ORGNAME_2-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
						break;
					}
				case ORGACESLGCY: {
						segmentData = getString(memRow, "npixwalk") + strDelim + getString(memRow, "requestedind1099")
						+ strDelim + getString(memRow, "forprofitind") + strDelim + getString(memRow, "paytoprovtamt")
						+ strDelim + getString(memRow, "paytoprovind") + strDelim + getString(memRow, "PROVSTUPST") /*Changed as per comment in 2.4*/ 
						+ strDelim + getString(memRow, "payeename") + strDelim + getString(memRow, "provacredidnum") 
						+ strDelim + getString(memRow, "provchaifind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"chaieffectdate") 
						+ strDelim + getString(memRow,"usgdateind")+ strDelim +getString(memRow,"usgdatecd")
						+ strDelim + getString(memRow,"parind")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow,"pareffectdt") 
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"parenddt")+ strDelim +getString(memRow,"pnlid")
						+ strDelim + getString(memRow, "provclasscd") 
						+ strDelim + getString(memRow, "provcorpttl") + strDelim + getString(memRow, "provelectcmrccd")
						+ strDelim + getString(memRow, "provinstttl") + strDelim + getString(memRow, "provliccatcd") 
						+ strDelim + getString(memRow, "provmiddlenm") + strDelim + getString(memRow, "profesttl") 
						+ strDelim + getString(memRow, "provstatus") + strDelim + getString(memRow, "provsufbusttl")
						+ strDelim + getString(memRow, "provfrstnm") + strDelim + getString(memRow, "provlstnm")
						+ strDelim + getString(memRow, "provmiddleinit") + strDelim + getString(memRow, "provsufnme") 
						+ strDelim + getString(memRow, "totantemmbrscurr")
						+ strDelim + getString(memRow, "provngtind")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"provngteffdt")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"provngtenddt")+ strDelim;
						hm_ALTSRCID_Slg.put("ORGACESLGCY-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
						break;
					}
				case ORGCPFLGCY: {
						segmentData = getString(memRow, "instpeergrpcd") + strDelim + getString(memRow, "instbussind")
						+ strDelim + getString(memRow, "instinpataccind") 
						+ strDelim + getString(memRow, "instctyhospind") + strDelim + getString(memRow, "instnmemind") 
						+ strDelim + getString(memRow, "insthmecareind") + strDelim + getString(memRow, "insthmdlysisind")
						+ strDelim + getString(memRow, "instotpatind") + strDelim + getString(memRow, "instsezareacd") 
						+ strDelim + getString(memRow, "instprtitind") + strDelim + getString(memRow, "ihstabrtnind")
						+ strDelim + getString(memRow, "ihstgovngovcd") + strDelim + getString(memRow, "ihsttypsvccd") 
						+ strDelim + getString(memRow, "ihstacrdtncd") + strDelim + getString(memRow, "ihstaffltncd") 
						+ strDelim + getString(memRow, "ihstemhcflg") + strDelim + getString(memRow, "ihstdrgcd")
						+ strDelim + getString(memRow, "ihstdrgexmptflg") + strDelim;
						hm_ALTSRCID_Slg.put("ORGCPFLGCY-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
						break;
					}
				case ORGCPMFLGCY: {
						segmentData = getString(memRow, "ctrlcd") + strDelim + getString(memRow, "provtypcd") 
						+ strDelim + getString(memRow, "subscrpayind") + strDelim + getString(memRow, "grprecind") 
						+ strDelim + getString(memRow, "loccd") + strDelim + getString(memRow, "medicareind")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "pcprpteffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "pcprptthrudt")
						+ strDelim + getString(memRow, "speccatcd1") + strDelim + getString(memRow, "speccatcd2") + strDelim 
						+ getString(memRow, "speccatcd3") + strDelim + getString(memRow, "speccatcd4") + strDelim 
						+ getString(memRow, "speccatcd5") + strDelim + getString(memRow, "speccatcd6") + strDelim 
						+ getString(memRow, "speccatcd7") + strDelim + getString(memRow, "speccatcd8") + strDelim 
						+ getString(memRow, "speccatcd9") + strDelim + getString(memRow, "speccatcd10") + strDelim 
						+ getString(memRow, "speccatcd11") + strDelim + getString(memRow, "speccatcd12") + strDelim 
						+ getString(memRow, "speccatcd13") + strDelim + getString(memRow, "speccatcd14") + strDelim 
						+ getString(memRow, "speccatcd15") + strDelim + getString(memRow, "speccatcd16") + strDelim 
						+ getString(memRow, "speccatcd17") + strDelim + getString(memRow, "speccatcd18") + strDelim 
						+ getString(memRow, "speccatcd19") + strDelim + getString(memRow, "speccatcd20") + strDelim
						+ getString(memRow,"NPIELGB")+ strDelim;
						hm_ALTSRCID_Slg.put("ORGCPMFLGCY-" + new Long(memRow.getMemRecno()).toString(), segmentData);
						break;
					}
				case ORGEPSBLGCY: {
						segmentData = getString(memRow, "tradsftynetcd") + strDelim 
						+ getString(memRow, "ipprnkval") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "ipseffdt") 
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "ipstermdt") + strDelim + getString(memRow, "ipstermrsn")
						+ strDelim + getString(memRow,"provorgdirabb")+ strDelim + getString(memRow,"pdcd")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pdeffdt")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow,"pdtermdt")
						+ strDelim + getString(memRow,"pdtermrsn")+ strDelim+ getString(memRow,"pdtypcd")
						+ strDelim + getString(memRow,"pdcd2")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pdcdeffdt2")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pdcdtrmdt2")+ strDelim+ getString(memRow,"pdcdtrmrsncd2")
						+ strDelim + getString(memRow,"pdtypcd2")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"provtxnmyeffdt")
						+ strDelim + getString(memRow,"dlgtdcredentcd")
						+ strDelim + getString(memRow,"wlcletreqind")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "wlcletsentdt")+ strDelim;
						hm_ALTSRCID_Slg.put("ORGEPSBLGCY-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
						break;
					}
				case ORGQCARELGCY: {
						segmentData = getString(memRow, "prv2025ind") + strDelim + getString(memRow, "prv2582ind")
						+ strDelim + getString(memRow, "prv2750ind") + strDelim + getString(memRow, "prv2755ind2") 
						+ strDelim + getString(memRow, "prv2755ind3") + strDelim + getString(memRow, "prv2755ind4")
						+ strDelim + getString(memRow, "prv2766ind") + strDelim + getString(memRow, "prv2791ind1") 
						+ strDelim + getString(memRow, "prv2791ind2") + strDelim + getString(memRow, "prv2791ind3") 
						+ strDelim + getString(memRow, "prv2791ind4") + strDelim + getString(memRow, "prv2791ind5")
						+ strDelim +getString(memRow, "prv2791ind6")+ strDelim +getString(memRow, "prv2791ind7")
						+ strDelim +getString(memRow, "prv2791ind8")+ strDelim +getString(memRow, "prv2791ind9")
						+ strDelim +getString(memRow, "prv2791ind10")
						+ strDelim + getString(memRow, "prv2792ind") + strDelim + getString(memRow, "prv2796ind1") 
						+ strDelim + getString(memRow, "prv2796ind2") + strDelim + getString(memRow, "prv2796ind3") 
						+ strDelim + getString(memRow, "prv2796ind4") + strDelim + getString(memRow, "prv2796ind5")
						+ strDelim + getString(memRow, "prv2798ind") 
						+ strDelim + getString(memRow, "prv2799ind") + strDelim + getString(memRow, "prv2614ind") 
						+ strDelim ;
						hm_ALTSRCID_Slg.put("ORGQCARELGCY-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
						break;
					}
			}
		}
		return generateSegmentsforALTSRCID(hm_ALTSRCID_Slg, entRecNum);
		
	}

	/**
	 * Source Level
	 * Genereate ALTSRCID segments for each memrecnos
	 * @param hm_ALTSRCID_Slg
	 * @param entRecNum
	 */
	private Set<String> generateSegmentsforALTSRCID(HashMap<String,String> hm_ALTSRCID_Slg,long entRecNum)throws Exception
	{
		Set <String>ALTSRCID_Keys = new HashSet<String>();
		Set<String> segmentDataSet = new HashSet<String>();
		//get the keys
		ALTSRCID_Keys = new HashSet<String>(hm_ALTSRCID_Slg.keySet());

		String split_keys[];
		String memrecno="";
		Set <String> ALTSRCID_memrecnos = new HashSet<String>();
		for (Iterator <String>iterator = ALTSRCID_Keys.iterator(); iterator
		.hasNext();) 
		{
			String ALTSRCID_key =iterator.next().toString();
			if(ALTSRCID_key.contains("INACTIVEORG")
					||ALTSRCID_key.contains("ORGACESLGCY")
					||ALTSRCID_key.contains("ORGNAME_1")
					||ALTSRCID_key.contains("ORGCPFLGCY")
					||ALTSRCID_key.contains("ORGTYPCD")
					||ALTSRCID_key.contains("ORGQCARELGCY")
					||ALTSRCID_key.contains("ORGCPMFLGCY")
					||ALTSRCID_key.contains("ORGEPSBLGCY")
					||ALTSRCID_key.contains("ORGNAME_2")
					||ALTSRCID_key.contains("MemHead_1"))
			{
				//split the keys to get memrecnos
				split_keys=ALTSRCID_key.split("-");
				memrecno=split_keys[1].trim();
				if(!ALTSRCID_memrecnos.contains(memrecno))
					ALTSRCID_memrecnos.add(memrecno);
			}

		}

		//generate for each memrecnos
		memrecno="";
		for (Iterator<String>iterator2 = ALTSRCID_memrecnos.iterator(); iterator2
		.hasNext();) 
		{
			//write ALTSRCID segment - BEGIN
			memrecno  = (String) iterator2.next();

			/*Getting the values from MEMHEAD_MAP against each memRecno*/
			if(null!=hm_MemHead)
			{
				MemHead temp_memHead ;
				temp_memHead = (MemHead)hm_MemHead.get(memrecno);
				l_strSrcCd = temp_memHead.getSrcCode();
				l_memIdNum = temp_memHead.getMemIdnum();
				srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
			}
			//

			if ((null == hm_ALTSRCID_Slg.get("INACTIVEORG-"+memrecno))&&(null!=hm_ALTSRCID_Slg.get("ORGNAME_1-"+memrecno)||
					null!=hm_ALTSRCID_Slg.get("ORGACESLGCY-"+memrecno)||null!=hm_ALTSRCID_Slg.get("ORGCPFLGCY-"+memrecno)
					||null!=hm_ALTSRCID_Slg.get("ORGCPMFLGCY-"+memrecno)||null!=hm_ALTSRCID_Slg.get("ORGEPSBLGCY-"+memrecno)
					||null!=hm_ALTSRCID_Slg.get("ORGQCARELGCY-"+memrecno)|| null!= hm_ALTSRCID_Slg.get("ORGTYPCD-"+memrecno)
					||null!=hm_ALTSRCID_Slg.get("ORGNAME_2-"+memrecno)))
			{
				outputType=OrgEnum.ALTSRCID.getValue();
				segmentData =  outputType + strDelim + strBlank+ strDelim + entRecNum + strDelim 
				+ "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim;
				hm_ALTSRCID_Slg.put("MemHead_1-"+memrecno, segmentData);
				/*segmentData = strBlank + strDelim;
				hm_ALTSRCID_Slg.put("MemHead_2-"+memrecno, segmentData);*/
				hm_ALTSRCID_Slg.put("INACTIVEORG-"+ memrecno, ExtMemgetIxnUtils.getNDelimiters(3));
			}

			if(null==hm_ALTSRCID_Slg.get("MemHead_1-"+memrecno))hm_ALTSRCID_Slg.put("MemHead_1-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(6));
			if (null == hm_ALTSRCID_Slg.get("ORGTYPCD-"+memrecno)) hm_ALTSRCID_Slg.put("ORGTYPCD-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(1));
			if(null==hm_ALTSRCID_Slg.get("INACTIVEORG-"+memrecno))hm_ALTSRCID_Slg.put("INACTIVEORG-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(3));
			if (null == hm_ALTSRCID_Slg.get("ORGNAME_1-"+memrecno)) hm_ALTSRCID_Slg.put("ORGNAME_1-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(6)+ "0" + strDelim);
			/*if (null == hm_ALTSRCID_Slg.get("MemHead_2-"+memrecno)) hm_ALTSRCID_Slg.put("MemHead_2-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(1));*/
			if (null == hm_ALTSRCID_Slg.get("ORGACESLGCY-"+memrecno)) hm_ALTSRCID_Slg.put("ORGACESLGCY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(33));
			if (null == hm_ALTSRCID_Slg.get("ORGCPFLGCY-"+memrecno)) hm_ALTSRCID_Slg.put("ORGCPFLGCY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(18));
			if (null == hm_ALTSRCID_Slg.get("ORGCPMFLGCY-"+memrecno)) hm_ALTSRCID_Slg.put("ORGCPMFLGCY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(29));
			if (null == hm_ALTSRCID_Slg.get("ORGEPSBLGCY-"+memrecno)) hm_ALTSRCID_Slg.put("ORGEPSBLGCY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(20));
			if (null == hm_ALTSRCID_Slg.get("ORGQCARELGCY-"+memrecno)) hm_ALTSRCID_Slg.put("ORGQCARELGCY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(26));
			if (null == hm_ALTSRCID_Slg.get("ORGNAME_2-"+memrecno)) hm_ALTSRCID_Slg.put("ORGNAME_2-"+memrecno,ExtMemgetIxnUtils.getNDelimiters(2)+ "0" /*+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd)*/);

			segmentData = hm_ALTSRCID_Slg.get("MemHead_1-"+memrecno) +hm_ALTSRCID_Slg.get("ORGTYPCD-"+memrecno)+ hm_ALTSRCID_Slg.get("INACTIVEORG-"+memrecno) + hm_ALTSRCID_Slg.get("ORGNAME_1-"+memrecno) 
			/*+ hm_ALTSRCID_Slg.get("MemHead_2-"+memrecno)*/ + hm_ALTSRCID_Slg.get("ORGACESLGCY-"+memrecno) +
			hm_ALTSRCID_Slg.get("ORGCPFLGCY-"+memrecno) + hm_ALTSRCID_Slg.get("ORGCPMFLGCY-"+memrecno) /*+ hm_ALTSRCID_Slg.get("ORGEPR6LGCY-"+memrecno)*/
			+ hm_ALTSRCID_Slg.get("ORGEPSBLGCY-"+memrecno) +
			hm_ALTSRCID_Slg.get("ORGQCARELGCY-"+memrecno) + hm_ALTSRCID_Slg.get("ORGNAME_2-"+memrecno);

			//logInfo("ALTSRCID_Slg: " + segmentData);
			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)/*.replace(ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd), strBlank)*/.length() > 0) 
			{
				segmentData +=strDelim +ExtMemgetIxnUtils.getNDelimiters(60)+ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				outputType = OrgEnum.ALTSRCID.getValue();
				segmentDataSet.add(segmentData);
			}
		}
		//write ALTSRCID segment - END
		return segmentDataSet;
	}
	
	@Override
	public Set<String> buildAPADRSegment(List<MemAttrRow> orgAPADRMemAttrList, List<String>EMEMADDR_Keys 
			,long entRecNum) throws Exception {
		outputType = OrgEnum.APADR.getValue();
		for (MemRow memRow : orgAPADRMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			String comb_key = getString(memRow, "md5key") + "-" + getString(memRow, "mds5addrtype") +"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")
			+ "-" + new Long(memRow.getMemRecno()).toString();

			switch(attrCode) {
			case ORGPADRLGCY: {	

				segmentData= outputType + strDelim + getString(memRow, "DFCDC_evtctime") 
				+ strDelim + entRecNum + strDelim + "EPDS V2" + strDelim
				+ getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+ strDelim 
				//CFF 2.7 new blank feilds
				+ ExtMemgetIxnUtils.getNDelimiters(4)
				+ l_strSrcCd + strDelim + l_memIdNum + strDelim;
				hm_APADR.put("ORGPADRLGCY-"+comb_key, segmentData);

				/*+ split_keys_comb_key [2] + strDelim 
				+ split_ememaddr_key[0] + strDelim + split_ememaddr_key[1] +*/

				segmentData= strDelim + getString(memRow, "DFCDC_evtinitiator") 
				+ strDelim + getString(memRow, "DFCDC_evtctime") 
				+ strDelim + getString(memRow, "DFCDC_mAudRecno")+strDelim
				+ getString(memRow, "lvlofcare") + strDelim + getString(memRow, "licbedcnt") + strDelim 
				+ getString(memRow, "maillblcnt") + strDelim + getString(memRow, "medicareclnind") + strDelim 
				+ getString(memRow, "medicarededbrdeligcnt") + strDelim + getString(memRow, "ocpcyrate") + strDelim 
				+ getString(memRow, "outstateind") + strDelim /*+ getString(memRow, "praclocdesg") + strDelim Removed as per 2.4*/
				+ getString(memRow, "praclocnme") + strDelim + getString(memRow, "provspeccopayind") + strDelim + getString(memRow, "provcntcprsnid") + strDelim 
				+ getString(memRow, "provlocstat") + strDelim + getString(memRow, "provoffpaymtcd") + strDelim 
				+ getString(memRow, "provprefspec") + strDelim + getString(memRow, "staffbedct") + strDelim 
				+ getString(memRow, "teachinstind") + strDelim + getString(memRow, "traumactrind") + strDelim 
				+ getString(memRow, "phyadbillgrp") + strDelim + getString(memRow, "r1099name1") + strDelim + getString(memRow, "econcd") 
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "econdt") + strDelim + getString(memRow, "hmorgncd") + strDelim 
				+ getString(memRow, "rgncd") + strDelim + getString(memRow, "terrcd") + strDelim + getString(memRow, "prv1182ind") + strDelim 
				+ getString(memRow, "prv2032ind") + strDelim + getString(memRow, "prv2033ind")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"provoptmnl")/*Changed to date format as per 2.5b*/
				+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow,"provoptmanackdt")// Added as per 2.4
				//CFF 2.7 new blank feilds
				+strDelim + strBlank 
				+ strDelim + getString(memRow, "DFCDC_evtinitiator") 
				+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")
				+ strDelim ;
				//Dec Offcycle new feild
				/*+ split_ememaddr_key[2] + ExtMemgetIxnUtils.getNDelimiters(4);*/
				hm_APADR.put("ORGPADRLGCY1-"+comb_key, segmentData);
			}
			break;

			case ORGFACHSANET:{
				segmentData =  getString(memRow, "attrval") + strDelim + getString(memRow, "elemdesc") +strDelim;
				hm_APADR.put("ORGFACHSANET-"+comb_key, segmentData);
			}
			break;

			case ORGDIRDISIND:{
				String comb_key1 = getString(memRow, "md5key") + "-" + getString(memRow, "mds5addrtype") +"-" + 
				ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+"-" + 
				getString(memRow, "attrval2") + "-" + new Long(memRow.getMemRecno()).toString();

				segmentData =  getString(memRow, "attrval") +strDelim;
				hm_APADR.put("ORGDIRDISIND-"+comb_key1, segmentData);
			}
			break;
			}
		}
			return generateSegmentsforAPADR(EMEMADDR_Keys,entRecNum);
	}

	private Set<String> generateSegmentsforAPADR(List<String>EMEMADDR_Keys,long entRecNum) {
		Set<String> segmentDataSet = new HashSet<String>();
		if(!EMEMADDR_Keys.isEmpty())
		{
			/*Set<String>EMEMADDR_Key_Set = new HashSet<String>();
			EMEMADDR_Key_Set=EMEMADDR_Keys.keySet();*/
			for (Iterator<String> iterator2 = EMEMADDR_Keys.iterator(); iterator2
			.hasNext();) 
			{
				String split_keys_EMEMADDR[],Comb_key,Display_Comb_key;
				String EMEMADDR_Key = iterator2.next();
				split_keys_EMEMADDR = EMEMADDR_Key.split("-");
				
				Comb_key = split_keys_EMEMADDR[0]+"-"+split_keys_EMEMADDR[1]+"-"+
				split_keys_EMEMADDR[2]+"-"+split_keys_EMEMADDR[6];
				Display_Comb_key = split_keys_EMEMADDR[0]+"-"+split_keys_EMEMADDR[1]+"-"+
				split_keys_EMEMADDR[2]+"-"+split_keys_EMEMADDR[5]+"-"+split_keys_EMEMADDR[6];
				
				String EMEMADDR_Key_memRecno=split_keys_EMEMADDR[6]/* EMEMADDR_Keys.get(EMEMADDR_Key)*/;

				/*Getting the values from MEMHEAD_MAP against each memRecno*/
				if(null!=hm_MemHead)
				{
					MemHead temp_memHead ;
					temp_memHead = (MemHead)hm_MemHead.get(EMEMADDR_Key_memRecno);
					l_strSrcCd = temp_memHead.getSrcCode();
					l_memIdNum = temp_memHead.getMemIdnum();
					srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				}
				//write APADR segment for records present in EMEMADDR
				if(null == hm_APADR.get("ORGPADRLGCY-"+Comb_key))
				{
					outputType = "APADR";
					segmentData= outputType + strDelim + strBlank
					+ strDelim + entRecNum + strDelim + "EPDS V2" + strDelim+ 
					split_keys_EMEMADDR[0] + strDelim + split_keys_EMEMADDR[1] + strDelim 
					+ split_keys_EMEMADDR[2]+ strDelim + ExtMemgetIxnUtils.getNDelimiters(4)+
					l_strSrcCd + strDelim + l_memIdNum + strDelim;
					hm_APADR.put("ORGPADRLGCY-"+Comb_key,segmentData);
					
					
					/*+split_keys_EMEMADDR[2] + strDelim + split_ememaddr_key[0] +
					strDelim + split_ememaddr_key[1]*/ 
					
					segmentData= ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim+
					ExtMemgetIxnUtils.getNDelimiters(31)+ "0" + strDelim ;
					/*split_ememaddr_key[2] + ExtMemgetIxnUtils.getNDelimiters(4)*/
					hm_APADR.put("ORGPADRLGCY1-"+Comb_key,segmentData);
				}

				if(null == hm_APADR.get("ORGFACHSANET-"+Comb_key))
				{
					segmentData =   ExtMemgetIxnUtils.getNDelimiters(2) ;
					hm_APADR.put("ORGFACHSANET-"+Comb_key,segmentData);
				}
				if(null == hm_APADR.get("ORGDIRDISIND-"+Display_Comb_key))
				{
					segmentData =   ExtMemgetIxnUtils.getNDelimiters(1) ;
					hm_APADR.put("ORGDIRDISIND-"+Display_Comb_key,segmentData);
				}

				segmentData = hm_APADR.get("ORGPADRLGCY-"+Comb_key)
				+ split_keys_EMEMADDR[2] + strDelim + split_keys_EMEMADDR[3] 
				+ strDelim + split_keys_EMEMADDR[4] 
				+hm_APADR.get("ORGPADRLGCY1-"+Comb_key)
				+ split_keys_EMEMADDR[5] + ExtMemgetIxnUtils.getNDelimiters(4)
				+ hm_APADR.get("ORGFACHSANET-"+Comb_key)
				+ hm_APADR.get("ORGDIRDISIND-"+Display_Comb_key);

				if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0) 
				{
					segmentData += ExtMemgetIxnUtils.getNDelimiters(53)+ ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					outputType = "APADR";
					segmentDataSet.add(segmentData);
				}
			}
		}
		return segmentDataSet;
	}

	@Override
	public Set<String> buildAPALTSegment(List<MemAttrRow> orgAPALTMemAttrList, long entRecNum,
			boolean APALTFlag) throws Exception {
		String l_memrecno ="";
		outputType = OrgEnum.APALT.getValue();
		HashMap<String, String> hm_APALT = new HashMap<String, String>();
		HashMap<String,ArrayList<String>> hm_APALT_Org_Alt_ID_Specialty = new HashMap<String,ArrayList<String>>();
		
		for (MemRow memRow : orgAPALTMemAttrList){
			getMemHeadValues(memRow);
			l_memrecno = new Long(memRow.getMemRecno()).toString();
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {

			case ALTSYSIDSORG:
			case NPIORG:
			case UPINORG: 
			case MEDICAREORG: 
			case MEDICAIDORG: 
			case ENCLRTYIDORG: 
			case LICENSEORG: {
				if(null!= l_strSrcCd && l_strSrcCd.length()>0
						&& null!=l_memIdNum && l_memIdNum.length()>0
						&& getString(memRow, "idnumber").length()>0
						&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") 
						&& ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate").length()>0
						&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
						&& ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate").length()>0) {
					srcCode_postprocess= ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),prop_relTypeCode);
					if(l_strAttrCode.equalsIgnoreCase("LICENSEORG") && getString(memRow, "licensetype").length()>0) {
						segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
							+ entRecNum + strDelim + "EPDS V2" + strDelim 
							+ getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "idissuer") + strDelim 
							+ getString(memRow, "idnumber")+ strDelim + getString(memRow, "licensetype")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") 
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "termrsn") 
							+ strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
							+ strDelim + getString(memRow, "termrsn") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
							+ getString(memRow, "DFCDC_evtctime") + strDelim 
							+ getString(memRow, "DFCDC_mAudRecno") + strDelim
							+ getString(memRow, "acesmedintname") + strDelim + getString(memRow, "acesmedpartind") + strDelim 
							+ getString(memRow , "wgscntrtiercd") + strDelim 
							+ getString(memRow, "epdsssbpaiid") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpapeffdt") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpaptermdt") + strDelim + getString(memRow, "epdsssbpaptermrsn") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbqlfdeffdt") + strDelim + getString(memRow, "epdsssbmdbtcd") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 	
							+ strDelim + getString(memRow, "DFCDC_mAudRecno")
							+ strDelim + getString (memRow, "proxtiercd")
							+ strDelim + getString (memRow, "proxmbrcnt")
							+ strDelim + getString (memRow, "proxmbrcapcnt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "proxmbrcntupddt")
							+ strDelim + getString (memRow, "proxfrmage")
							+ strDelim + getString (memRow, "proxtoage")
							+ strDelim + getString (memRow, "proxgendercd")
							+ strDelim + getString (memRow, "proxsrvcind")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxepreffdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxeprtermdt") + strDelim;
						String key = getString(memRow, "IDNUMBER");
						if (key.contains("-")) {
							key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
						}
						hm_APALT.put(getString(memRow, "idissuer")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+"-" +getString(memRow, "licensetype")+"-"  +key+"-" + getString(memRow, "md5key")+"-" + getString(memRow, "MDS5ADDRTYPE")+"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"+ l_memrecno , segmentData );	
					}
					else if(getString(memRow, "idtype").length()>0) {
						segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
							+ entRecNum + strDelim + "EPDS V2" + strDelim 
							+ getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "idissuer") + strDelim 
							+ getString(memRow, "idnumber")+ strDelim + getString(memRow, "idtype") +strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") 
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "idtermrsn") + strDelim 
							+ l_strSrcCd+ strDelim + l_memIdNum + strDelim
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
							+ strDelim + getString(memRow, "idtermrsn") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
							+ getString(memRow, "DFCDC_evtctime") + strDelim 
							+ getString(memRow, "DFCDC_mAudRecno") + strDelim
							+ getString(memRow, "acesmedintname")+ strDelim + getString(memRow, "acesmedpartind") + strDelim 
							+ getString(memRow , "wgscntrtiercd") + strDelim
							+ getString(memRow, "epdsssbpaiid") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpapeffdt") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpaptermdt") + strDelim + getString(memRow, "epdsssbpaptermrsn") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbqlfdeffdt") + strDelim + getString(memRow, "epdsssbmdbtcd") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
							+ strDelim + getString(memRow, "DFCDC_mAudRecno")
							+ strDelim + getString (memRow, "proxtiercd")
							+ strDelim + getString (memRow, "proxmbrcnt")
							+ strDelim + getString (memRow, "proxmbrcapcnt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "proxmbrcntupddt")
							+ strDelim + getString (memRow, "proxfrmage")
							+ strDelim + getString (memRow, "proxtoage")
							+ strDelim + getString (memRow, "proxgendercd")
							+ strDelim + getString (memRow, "proxsrvcind")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxepreffdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxeprtermdt") + strDelim;
						String key = getString(memRow, "IDNUMBER");
						if (key.contains("-")) {
							key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
						}
						hm_APALT.put(getString(memRow, "idissuer")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+"-" +getString(memRow, "IDTYPE")+"-"  +key+"-" + getString(memRow, "md5key")+"-" + getString(memRow, "MDS5ADDRTYPE")+"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"+ l_memrecno , segmentData );
					}
				}
			}
			break;
			case ORGALTIDSPEC: {
				String key = getString(memRow, "IDNUMBER");
				if (key.contains("-")) 
					key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
				String spec_key = getString(memRow, "idissuer")+"-" +
									ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")+"-" +
									ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+"-" +
									getString(memRow, "IDTYPE")+"-" + key+"-" + 
									getString(memRow, "md5key")+"-" + 
									getString(memRow, "MDS5ADDRTYPE")+"-" + 
									ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT") +"-" + l_memrecno;
				segmentData = getString(memRow, "specialtycd") + strDelim + 
								ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+ strDelim; 
				ArrayList<String>speciality_list = new ArrayList<String>();
				if(ExtMemgetIxnUtils.isNotEmpty(hm_APALT_Org_Alt_ID_Specialty)) {
					if(hm_APALT_Org_Alt_ID_Specialty.containsKey(spec_key)) { //if contain key 
						speciality_list = hm_APALT_Org_Alt_ID_Specialty.get(spec_key);
					}
				}	
				speciality_list.add(segmentData);
				hm_APALT_Org_Alt_ID_Specialty.put(spec_key, speciality_list);
				
			}
			break;
			}
		}
		return generateSegmentsforAPALT(hm_APALT,hm_APALT_Org_Alt_ID_Specialty);
	}
	
	private Set<String> generateSegmentsforAPALT(HashMap<String, String> hm_APALT,
			HashMap<String,ArrayList<String>> hm_APALT_Org_Alt_ID_Specialty)  throws Exception {
		
		HashMap<String,String> hm_APALT_Org_Alt_ID_Specialty1 = new HashMap<String,String>();
		Set<String> segmentDataSet = new HashSet<String>();
		if (null != hm_APALT && hm_APALT.size() > 0) {
			if(null != hm_APALT_Org_Alt_ID_Specialty && hm_APALT_Org_Alt_ID_Specialty.size() > 0) {			
				String ProvAltIDSpecialty1_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty2_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty3_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty4_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty5_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				Set<String> hm_Org_Alt_ID_Specialty_keys = new HashSet<String>();
				hm_Org_Alt_ID_Specialty_keys = hm_APALT_Org_Alt_ID_Specialty.keySet();
				for (Iterator<String> iterator1 = hm_Org_Alt_ID_Specialty_keys.iterator(); iterator1.hasNext();) {
					String hm_Org_Alt_ID_Specialty_key = (String) iterator1.next();
					String[] split_keys1 ; 
					split_keys1 = hm_Org_Alt_ID_Specialty_key.split("-");
					ArrayList<String> valueList = hm_APALT_Org_Alt_ID_Specialty.get(hm_Org_Alt_ID_Specialty_key);
					int count = valueList.size();
					for (Iterator<String> iterator_value = valueList.iterator(); iterator_value.hasNext();) {
						String spec_info = (String) iterator_value.next();						
						switch (count) {
						case 1 : 	ProvAltIDSpecialty1_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						case 2 : 	ProvAltIDSpecialty2_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						case 3 :	ProvAltIDSpecialty3_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						case 4: 	ProvAltIDSpecialty4_segmentData1 = spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						case 5 : 	ProvAltIDSpecialty5_segmentData1 =spec_info!=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						}	
						count --;
					}
					if(null!=hm_MemHead) {
						MemHead temp_memHead ;
						temp_memHead = (MemHead)hm_MemHead.get(split_keys1[split_keys1.length-1]);
						l_strSrcCd = temp_memHead.getSrcCode();
					}
					segmentData = ProvAltIDSpecialty1_segmentData1 +ProvAltIDSpecialty2_segmentData1+ProvAltIDSpecialty3_segmentData1+ProvAltIDSpecialty4_segmentData1+ProvAltIDSpecialty5_segmentData1 +ExtMemgetIxnUtils.getNDelimiters(60)+ ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					hm_APALT_Org_Alt_ID_Specialty1.put(AddSpliterToContent(split_keys1), segmentData);
					segmentData = "";
				}
			}
			Set <String>APALT_Keys = new HashSet<String>();
			String split_keys[];						
			//get the keys
			APALT_Keys = new HashSet<String>(hm_APALT.keySet());			
			for (Iterator<String> iterator = APALT_Keys.iterator(); iterator.hasNext();) {
				String APALT_Key = iterator.next();
				split_keys = APALT_Key.split("-");	
				if(null!=hm_MemHead) {
					MemHead temp_memHead ;
					temp_memHead = (MemHead)hm_MemHead.get(split_keys[split_keys.length-1]);
					l_strSrcCd = temp_memHead.getSrcCode();
					srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				}
				if (null == hm_APALT_Org_Alt_ID_Specialty.get(AddSpliterToContent(split_keys))) {
					segmentData = ExtMemgetIxnUtils.getNDelimiters(10)+ExtMemgetIxnUtils.getNDelimiters(60)+ ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					hm_APALT_Org_Alt_ID_Specialty1.put(AddSpliterToContent(split_keys), segmentData);
				}						
				//create a full segment						
				segmentData=hm_APALT.get(AddSpliterToContent(split_keys)) +
				hm_APALT_Org_Alt_ID_Specialty1.get(AddSpliterToContent(split_keys));
				outputType = OrgEnum.APALT.getValue();								
				segmentDataSet.add(segmentData);
			}
			
		}
		return segmentDataSet;
	}

	@Override
	public Set<String> buildAPREMSegment(List<MemAttrRow> orgAPREMMemAttrList, long entRecNum) throws Exception {
		String l_memrecno = "";
		outputType 	= 	OrgEnum.APREM.getValue();
		HashMap<String, String>hm_APREM_REMITSEG1 = new HashMap<String,String>();
		HashMap<String, String>hm_APREM_REMITSEG2 = new HashMap<String,String>();
		HashMap<String, String>hm_APREM_REMITSEG3 = new HashMap<String,String>();
		HashMap<String, String>hm_APREM_REMITSEG4 = new HashMap<String,String>();
		HashMap<String, String>hm_APREM_REMITSEG5 = new HashMap<String,String>();
		HashMap<String, String>hm_APREM_REMITSEG6= new HashMap<String,String>();
		
		for (MemRow memRow : orgAPREMMemAttrList){
			getMemHeadValues(memRow);
			l_memrecno = new Long(memRow.getMemRecno()).toString();
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {

			case REMITSEG: {
				if(getString(memRow, "remitaddrtype").length()>0
						&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") && ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt").length()>0
						&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegeffdt") && ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegeffdt").length()>0
						&& getString(memRow, "fedtaxid").length()>0
						&& getString(memRow, "fedtaxidtype").length()>0
						&& getString(memRow, "remitchecknm").length()>0
						&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegtermdt") && ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegtermdt").length()>0
						&& getString(memRow,"recStat").equalsIgnoreCase("A")) {
					String aprem_key = getString(memRow, "remitchecknm");
					if (aprem_key.contains("-")) {
						aprem_key = aprem_key.replaceAll("-", "");
					}
					if (aprem_key.contains("*")) {
						aprem_key = aprem_key.replaceAll("\\*", "");
					}

					srcCode_postprocess= ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);

					segmentData = 	outputType + strDelim + getString(memRow, "DFCDC_evtctime") 
									+ strDelim + entRecNum + strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") 
									+ strDelim + getString(memRow, "remitaddrtype") + strDelim 
									+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") + strDelim ;
					
					
					hm_APREM_REMITSEG1.put(aprem_key+"-"
							+ getString(memRow, "md5key")+"-"
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") + "-"
							+ l_memrecno + "-" + getString(memRow, "payid") + "*"
							+ aprem_count, segmentData);

					segmentData = getString(memRow, "fedtaxid") + strDelim + getString(memRow, "fedtaxid1099") + strDelim 
					+ getString(memRow, "fedtaxidtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "fedtaxideffdt") + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "fedtaxidtermdt") + strDelim + getString(memRow, "fedtaxidtermrsn") + strDelim 
					+ getString(memRow, "altidsource") + strDelim + getString(memRow, "altidnumber") + strDelim 
					+ getString(memRow, "altidtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "altidissuedt") + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "altidtermdt") + strDelim + getString(memRow, "altidissuer") + strDelim +getString(memRow, "payidsrc")+ strDelim + getString(memRow, "payid")+ strDelim
					+ getString(memRow, "payidtype")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "payidissuedt")+ strDelim +
					ExtMemgetIxnUtils.getDateAsString(memRow, "payidtermdt")+ strDelim + getString(memRow, "remitchecknm") + strDelim + getString(memRow, "remitlocid") + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegeffdt") + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegtermdt") + strDelim 
					+ getString(memRow, "relatedprovid") + strDelim + getString(memRow, "entrecno") 
					+ strDelim + getString(memRow,"relatedprovreltype")+ strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "relatedprovreleffectdt")  
					+ strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegeffdt")+strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegtermdt") + strDelim + getString(memRow, "remitsegtermrsn")  
					+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
					+ strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim + getString(memRow, "aceseftind") + strDelim 
					+ getString(memRow, "acesoprind") + strDelim + getString(memRow, "acespapersprs") + strDelim 
					+ getString(memRow, "acesovrind") + strDelim  ;
					
					hm_APREM_REMITSEG3.put(aprem_key+"-"
							+ getString(memRow, "md5key")+"-"
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") + "-"
							+ l_memrecno + "-" + getString(memRow, "payid") 
							 + "*"
							+ aprem_count, segmentData);
		

					segmentData=getString(memRow, "epdsssbtipodebtind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbtipolastdt")+ strDelim 
					+ getString(memRow, "epdsssbtipotidt") + strDelim 
					+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
					+ getString(memRow, "DFCDC_evtctime")+ strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim ;
					hm_APREM_REMITSEG5.put(aprem_key+"-"
							+ getString(memRow, "md5key")+"-"
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") + "-"
							+ l_memrecno + "-"
							 +getString(memRow, "payid") + "*"
							+ aprem_count, segmentData);

					segmentData = ExtMemgetIxnUtils.getNDelimiters(60)+srccode_lookup + strDelim + " " + strDelim  + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					hm_APREM_REMITSEG6.put(aprem_key+"-"
							+ getString(memRow, "md5key")+"-"
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") + "-"
							+ l_memrecno + "-"
							 + getString(memRow, "payid") + "*"
							+ aprem_count, segmentData);
					
					aprem_count++;
				}
			}
			break;
			case REMITSEGDTL: {
				String aprem_detail_key = getString(memRow, "remitchecknm");
				if (aprem_detail_key.contains("-")) {
					aprem_detail_key = aprem_detail_key.replaceAll("-", "");
				}
				if (aprem_detail_key.contains("*")) {
					aprem_detail_key = aprem_detail_key.replaceAll("\\*", "");
				}
				
				
                segmentData = getString(memRow, "remitindicator") + strDelim ;
				hm_APREM_REMITSEG2.put(aprem_detail_key+"-"+ getString(memRow, "md5key")+"-"
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
						+ l_memrecno+"-" + getString(memRow, "payid"), segmentData);
				
				segmentData = ExtMemgetIxnUtils.getDateAsString(memRow,"eftenroleffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"eftenrolenddt")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"oprenrleffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"oprenrlenddt")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pprsuprseffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pprsuprsenddt")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pprsuprsovrdeffdt")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pprsuprsovrdenddt")+ strDelim + getString(memRow, "cpmfdeviceid") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftadddt") + strDelim + getString(memRow, "cpmfeftaddfirst") 
				+ strDelim + getString(memRow, "cpmfeftaddlast") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftchngdt1") 
				+ strDelim + getString(memRow, "cpmfeftchngfirst1") + strDelim + getString(memRow, "cpmfeftchnglast1") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftchngdt2") + strDelim + getString(memRow, "cpmfeftchngfirst2") + strDelim 
				+ getString(memRow, "cpmfeftchnglast2") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftchngdt3") + strDelim 
				+ getString(memRow, "cpmfeftchngfirst3") + strDelim + getString(memRow, "cpmfeftchnglast3") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftchngdt4") + strDelim + getString(memRow, "cpmfeftchngfirst4") + strDelim 
				+ getString(memRow, "cpmfeftchnglast4") + strDelim + getString(memRow, "cpmfcomment1") + strDelim 
				+ getString(memRow, "cpmfcomment21") + strDelim + getString(memRow, "cpmfcomment22") + strDelim 
				+ getString(memRow, "cpmfcomment23") + strDelim + getString(memRow, "cpmfcomment24") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftrmteffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftrmttermdt") + strDelim ;
				
				
				hm_APREM_REMITSEG4.put(aprem_detail_key+"-"
						+ getString(memRow, "md5key")+"-"
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") +"-"
						+ l_memrecno + "-"
						  + getString(memRow, "payid"),
						 segmentData);
				// change in cff 3.1 defect fix
			}
			break;
			}
		}
		return generateSegmentsforAPREM(hm_APREM_REMITSEG1,hm_APREM_REMITSEG2,
				hm_APREM_REMITSEG3,hm_APREM_REMITSEG4,hm_APREM_REMITSEG5,hm_APREM_REMITSEG6);
	}
	
	/**
	 * Source Level
	 * Genereate APREM segments for each memrecnos
	 * @param hm_APREM
	 * @param entRecNum
	 */
	private Set<String> generateSegmentsforAPREM(HashMap<String, String>hm_APREM_REMITSEG1,
					HashMap<String, String>hm_APREM_REMITSEG2,
					HashMap<String, String>hm_APREM_REMITSEG3,
					HashMap<String, String>hm_APREM_REMITSEG4,
					HashMap<String, String>hm_APREM_REMITSEG5,
					HashMap<String, String>hm_APREM_REMITSEG6)throws Exception
	{
		Set <String>APREM_Keys = new HashSet<String>();
		Set<String> segmentDataSet = new HashSet<String>();
		String split_keys[],split_keys_star[];

		if(null!= hm_APREM_REMITSEG1 && hm_APREM_REMITSEG1.size()>0 )
		{
			APREM_Keys =hm_APREM_REMITSEG1.keySet();
			for (Iterator<String>iterator2 = APREM_Keys.iterator(); iterator2
			.hasNext();) 
			{
				String APREM_Key  = (String) iterator2.next();
				split_keys_star = APREM_Key.split("\\*");
				split_keys =split_keys_star[0].split("-");
				if(null!=hm_MemHead)
				{
					MemHead temp_memHead ;
					temp_memHead = (MemHead)hm_MemHead.get(split_keys[3]);
					srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(temp_memHead.getSrcCode());
				}
				if(null!=hm_APREM_REMITSEG1.get(APREM_Key))
				{
					if(null == hm_APREM_REMITSEG2.get(split_keys_star[0]))
					{
						hm_APREM_REMITSEG2.put(split_keys_star[0], ExtMemgetIxnUtils.getNDelimiters(1));
					}
					if(null == hm_APREM_REMITSEG4.get(split_keys_star[0]))
					{
						hm_APREM_REMITSEG4.put(split_keys_star[0], ExtMemgetIxnUtils.getNDelimiters(31));
					}
					//write APREM segment - BEGIN
					segmentData = hm_APREM_REMITSEG1.get(APREM_Key) + hm_APREM_REMITSEG2.get(split_keys_star[0])
					+ hm_APREM_REMITSEG3.get(APREM_Key)+ hm_APREM_REMITSEG4.get(split_keys_star[0])+ hm_APREM_REMITSEG5.get(APREM_Key)
					+ hm_APREM_REMITSEG6.get(APREM_Key);
					outputType = OrgEnum.APREM.getValue();
					segmentDataSet.add(segmentData);
				}
			}
		}
		return segmentDataSet;
	}

	@Override
	public Set<String> buildAPSPTSegment(List<MemAttrRow> orgAPSPTMemAttrList, long entRecNum) throws Exception {
		String l_memrecno = "";
		outputType = OrgEnum.APSPT.getValue();
		HashMap<String, String>hm_APSPT_ORGBRDCRT = new HashMap<String, String>();
		HashMap<String, String>hm_APSPT_ORGBRDCRT1 = new HashMap<String, String>();
		HashMap<String, String>hm_APSPT_TXNMY = new HashMap<String, String>();
		
		for (MemRow memRow : orgAPSPTMemAttrList){
			getMemHeadValues(memRow);
			l_memrecno = new Long(memRow.getMemRecno()).toString();
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			
			case ORGBRDCRT: {
				segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim 
				+ "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "specialtycd") + strDelim 
				+ getString(memRow, "primaryspec") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt") + strDelim + getString(memRow, "spectermrsn") + strDelim 
				+ getString(memRow, "speclgcycode")+ strDelim  // added new field for cff2.5b
				+ l_strSrcCd+ strDelim + l_memIdNum + strDelim
				//ChangeRequest Term dates not present on outbound files for A files WLPRD00793241
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt")	+ strDelim 
				+ getString(memRow, "spectermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
				+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim 
				+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
				+ getString(memRow, "DFCDC_evtctime") + strDelim 
				+ getString(memRow, "DFCDC_mAudRecno") + strDelim;

				hm_APSPT_ORGBRDCRT.put(getString(memRow, "md5key") + "-" 
						+ getString(memRow, "mds5addrtype")+ "-"
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
						+ getString(memRow, "specialtycd")+"-"+ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+"-"
						+ getString(memRow, "primaryspec") + "-"
						+ l_memrecno, segmentData);
				
				//Dec Offcycle new feild
				segmentData = getString(memRow, "boardcertcd") + strDelim ;
				hm_APSPT_ORGBRDCRT1.put(getString(memRow, "md5key") + "-" 
						+ getString(memRow, "mds5addrtype")+ "-"
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
						+ getString(memRow, "specialtycd")+"-"+ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+"-"
						+ getString(memRow, "primaryspec") + "-"
						+ l_memrecno, segmentData);
			}
			break;
			case SPLTYTXNMORG:	{
				segmentData = getString(memRow, "SPECTAXONOMY1") 
				+ strDelim  + getString(memRow, "SPECTAXONOMY2") + strDelim + getString(memRow, "SPECTAXONOMY3")
				+ ExtMemgetIxnUtils.getNDelimiters(3);

				hm_APSPT_TXNMY.put(getString(memRow, "md5key") + "-" 
						+ getString(memRow, "mds5addrtype")+ "-"
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
						+ getString(memRow, "specialty")+"-"+ l_memrecno,segmentData);
			}
			break;
			}
		}
		return generateSegmentsforAPSPT(hm_APSPT_ORGBRDCRT,hm_APSPT_ORGBRDCRT1,hm_APSPT_TXNMY);
	}
	
	/**
	 * Generate APSPT multiple segments after adding specialty & taxonomy
	 * @param entRecNum
	 * @throws Exception
	 */
	private Set<String> generateSegmentsforAPSPT(HashMap<String, String>hm_APSPT_ORGBRDCRT,
			HashMap<String, String>hm_APSPT_ORGBRDCRT1,	HashMap<String, String>hm_APSPT_TXNMY) throws Exception
	{
		Set <String>APSPT_Keys = new HashSet<String>();
		Set<String> segmentDataSet = new HashSet<String>();
		outputType = OrgEnum.APSPT.getValue();
		//Required field check:
		//if hm_APSPT_ORGBRDCRT is present only then create APSPT segment
		if(null!= hm_APSPT_ORGBRDCRT && hm_APSPT_ORGBRDCRT.size()>0 )
		{
			//get the keys
			APSPT_Keys = hm_APSPT_ORGBRDCRT.keySet();
			String split_keys[];
			for (Iterator<String> iterator = APSPT_Keys.iterator(); iterator.hasNext();) 
			{
				String APSPT_Key = iterator.next();
				split_keys= APSPT_Key.split("-");
				if(null!=hm_MemHead)
				{
					MemHead temp_memHead ;
					temp_memHead = (MemHead)hm_MemHead.get(split_keys[6]);
					l_strSrcCd = temp_memHead.getSrcCode();
					srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				}
				//ORGBRDCRT
				if (null != hm_APSPT_ORGBRDCRT.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5] +"-" + split_keys[6]))
				{
					//SPLTYTXNMORG 
					if (null == hm_APSPT_TXNMY.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6])) 
					{
						segmentData = ExtMemgetIxnUtils.getNDelimiters(5);
						hm_APSPT_TXNMY.put( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6],segmentData);
					}
					//SPLTYTXNMORG-END

					//create a full segment for PSPT
					segmentData = hm_APSPT_ORGBRDCRT.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6]) 
					+ hm_APSPT_TXNMY.get(  split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6])
					+ hm_APSPT_ORGBRDCRT1.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6])
					+ ExtMemgetIxnUtils.getNDelimiters(59)+ srcCode_postprocess;
					segmentDataSet.add(segmentData);
				}
			}
		}
		return segmentDataSet;
	}
	
	@Override
	public Set<String> buildALTSRCIDSegmentForV2(List<MemAttrRow> orgPRFMemAttrList, String epdsv2memrecno,
			long entrecno) throws Exception {
		outputType=OrgEnum.ALTSRCID.getValue();
		for (MemRow memRow : orgPRFMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
				case ORGTYPCD: {
						hm_ALTSRCID_Slg_V2.put("ORGTYPCD-"+epdsv2memrecno,getString(memRow, "codeval")+ strDelim);
						break;
				}
				case INACTIVEORG: {
						segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") 
						+ strDelim + getString(memRow, "entrecno") + strDelim 
						+ "EPDS V2" + strDelim + OrgEnum.EPDSV2ORG.getValue() + strDelim + epdsv2memrecno + strDelim;
						hm_ALTSRCID_Slg_V2.put("MemHead_1-" +epdsv2memrecno, segmentData);
		
						segmentData = ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
						+ strDelim + getString(memRow, "termrsn")+ strDelim;
						segmentData= getTermAfterValidation(memRow,"ALTSRCID",segmentData,"termdt");
						hm_ALTSRCID_Slg_V2.put("INACTIVEORG-"+epdsv2memrecno, segmentData);
						break;
					}
				case ORGNAME: {
						segmentData = getString(memRow, "attrval") + strDelim + strBlank + strDelim 
						+ strBlank + strDelim + strBlank + strDelim 
						+ getString(memRow, "DFCDC_evtinitiator")  + strDelim 
						+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim;
						hm_ALTSRCID_Slg_V2.put("ORGNAME_1-"+epdsv2memrecno, segmentData);
		
						segmentData = getString(memRow, "DFCDC_evtinitiator")  + strDelim 
						+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")/*+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd)*/;
						hm_ALTSRCID_Slg_V2.put("ORGNAME_2-"+epdsv2memrecno, segmentData);
						break;
					}
				case ORGACESLGCY: {
						segmentData = getString(memRow, "npixwalk") + strDelim + getString(memRow, "requestedind1099")
						+ strDelim + getString(memRow, "forprofitind") + strDelim + getString(memRow, "paytoprovtamt")
						+ strDelim + getString(memRow, "paytoprovind") + strDelim + getString(memRow, "PROVSTUPST") /*Changed as per comment in 2.4*/ 
						+ strDelim + getString(memRow, "payeename") + strDelim + getString(memRow, "provacredidnum") 
						+ strDelim + getString(memRow, "provchaifind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"chaieffectdate") 
						+ strDelim + getString(memRow,"usgdateind")+ strDelim +getString(memRow,"usgdatecd")
						+ strDelim + getString(memRow,"parind")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow,"pareffectdt") 
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"parenddt")+ strDelim +getString(memRow,"pnlid")
						+ strDelim + getString(memRow, "provclasscd") 
						+ strDelim + getString(memRow, "provcorpttl") + strDelim + getString(memRow, "provelectcmrccd")
						+ strDelim + getString(memRow, "provinstttl") + strDelim + getString(memRow, "provliccatcd") 
						+ strDelim + getString(memRow, "provmiddlenm") + strDelim + getString(memRow, "profesttl") 
						+ strDelim + getString(memRow, "provstatus") + strDelim + getString(memRow, "provsufbusttl")
						+ strDelim + getString(memRow, "provfrstnm") + strDelim + getString(memRow, "provlstnm")
						+ strDelim + getString(memRow, "provmiddleinit") + strDelim + getString(memRow, "provsufnme") 
						+ strDelim + getString(memRow, "totantemmbrscurr")
						+ strDelim + getString(memRow, "provngtind")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"provngteffdt")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"provngtenddt")+ strDelim;
						hm_ALTSRCID_Slg_V2.put("ORGACESLGCY-"+epdsv2memrecno, segmentData);
						break;
					}
				case ORGCPFLGCY: {
						segmentData = getString(memRow, "instpeergrpcd") + strDelim + getString(memRow, "instbussind")
						+ strDelim + getString(memRow, "instinpataccind") 
						+ strDelim + getString(memRow, "instctyhospind") + strDelim + getString(memRow, "instnmemind") 
						+ strDelim + getString(memRow, "insthmecareind") + strDelim + getString(memRow, "insthmdlysisind")
						+ strDelim + getString(memRow, "instotpatind") + strDelim + getString(memRow, "instsezareacd") 
						+ strDelim + getString(memRow, "instprtitind") + strDelim + getString(memRow, "ihstabrtnind")
						+ strDelim + getString(memRow, "ihstgovngovcd") + strDelim + getString(memRow, "ihsttypsvccd") 
						+ strDelim + getString(memRow, "ihstacrdtncd") + strDelim + getString(memRow, "ihstaffltncd") 
						+ strDelim + getString(memRow, "ihstemhcflg") + strDelim + getString(memRow, "ihstdrgcd")
						+ strDelim + getString(memRow, "ihstdrgexmptflg") + strDelim;
						hm_ALTSRCID_Slg_V2.put("ORGCPFLGCY-"+epdsv2memrecno, segmentData);
						break;
					}
				case ORGCPMFLGCY: {
						segmentData = getString(memRow, "ctrlcd") + strDelim + getString(memRow, "provtypcd") 
						+ strDelim + getString(memRow, "subscrpayind") + strDelim + getString(memRow, "grprecind") 
						+ strDelim + getString(memRow, "loccd") + strDelim + getString(memRow, "medicareind")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "pcprpteffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "pcprptthrudt")
						+ strDelim + getString(memRow, "speccatcd1") + strDelim + getString(memRow, "speccatcd2") + strDelim 
						+ getString(memRow, "speccatcd3") + strDelim + getString(memRow, "speccatcd4") + strDelim 
						+ getString(memRow, "speccatcd5") + strDelim + getString(memRow, "speccatcd6") + strDelim 
						+ getString(memRow, "speccatcd7") + strDelim + getString(memRow, "speccatcd8") + strDelim 
						+ getString(memRow, "speccatcd9") + strDelim + getString(memRow, "speccatcd10") + strDelim 
						+ getString(memRow, "speccatcd11") + strDelim + getString(memRow, "speccatcd12") + strDelim 
						+ getString(memRow, "speccatcd13") + strDelim + getString(memRow, "speccatcd14") + strDelim 
						+ getString(memRow, "speccatcd15") + strDelim + getString(memRow, "speccatcd16") + strDelim 
						+ getString(memRow, "speccatcd17") + strDelim + getString(memRow, "speccatcd18") + strDelim 
						+ getString(memRow, "speccatcd19") + strDelim + getString(memRow, "speccatcd20") + strDelim
						+ getString(memRow,"NPIELGB")+ strDelim;
						hm_ALTSRCID_Slg_V2.put("ORGCPMFLGCY-" +epdsv2memrecno, segmentData);
						break;
					}
				case ORGEPSBLGCY: {
						segmentData = getString(memRow, "tradsftynetcd") + strDelim 
						+ getString(memRow, "ipprnkval") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "ipseffdt") 
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "ipstermdt") + strDelim + getString(memRow, "ipstermrsn")
						+ strDelim + getString(memRow,"provorgdirabb")+ strDelim + getString(memRow,"pdcd")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pdeffdt")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow,"pdtermdt")
						+ strDelim + getString(memRow,"pdtermrsn")+ strDelim+ getString(memRow,"pdtypcd")
						+ strDelim + getString(memRow,"pdcd2")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pdcdeffdt2")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"pdcdtrmdt2")+ strDelim+ getString(memRow,"pdcdtrmrsncd2")
						+ strDelim + getString(memRow,"pdtypcd2")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"provtxnmyeffdt")
						+ strDelim + getString(memRow,"dlgtdcredentcd")
						+ strDelim + getString(memRow,"wlcletreqind")
						+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "wlcletsentdt")+ strDelim;
						hm_ALTSRCID_Slg_V2.put("ORGEPSBLGCY-"+ epdsv2memrecno, segmentData);
						break;
					}
				case ORGQCARELGCY: {
						segmentData = getString(memRow, "prv2025ind") + strDelim + getString(memRow, "prv2582ind")
						+ strDelim + getString(memRow, "prv2750ind") + strDelim + getString(memRow, "prv2755ind2") 
						+ strDelim + getString(memRow, "prv2755ind3") + strDelim + getString(memRow, "prv2755ind4")
						+ strDelim + getString(memRow, "prv2766ind") + strDelim + getString(memRow, "prv2791ind1") 
						+ strDelim + getString(memRow, "prv2791ind2") + strDelim + getString(memRow, "prv2791ind3") 
						+ strDelim + getString(memRow, "prv2791ind4") + strDelim + getString(memRow, "prv2791ind5")
						+ strDelim +getString(memRow, "prv2791ind6")+ strDelim +getString(memRow, "prv2791ind7")
						+ strDelim +getString(memRow, "prv2791ind8")+ strDelim +getString(memRow, "prv2791ind9")
						+ strDelim +getString(memRow, "prv2791ind10")
						+ strDelim + getString(memRow, "prv2792ind") + strDelim + getString(memRow, "prv2796ind1") 
						+ strDelim + getString(memRow, "prv2796ind2") + strDelim + getString(memRow, "prv2796ind3") 
						+ strDelim + getString(memRow, "prv2796ind4") + strDelim + getString(memRow, "prv2796ind5")
						+ strDelim + getString(memRow, "prv2798ind") 
						+ strDelim + getString(memRow, "prv2799ind") + strDelim + getString(memRow, "prv2614ind") 
						+ strDelim ;
						hm_ALTSRCID_Slg_V2.put("ORGQCARELGCY-"+ epdsv2memrecno, segmentData);
						break;
					}
			}
		}
		return generateSegmentsforALTSRCID(hm_ALTSRCID_Slg_V2, entrecno);
		
	}
	
	@Override
	public String getSegmentDataforAPDM(MemRow memRow,long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = OrgEnum.APDM.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "entrecno") + strDelim + "EPDS V2" + strDelim + getString(memRow, "elemdesc") + strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")
		+ strDelim + getString(memRow, "codetermrsn")  + strDelim +  l_strSrcCd + strDelim + l_memIdNum + strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")
		+ strDelim + getString(memRow, "codetermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno") +ExtMemgetIxnUtils.getNDelimiters(60)+ strDelim + srcCodesDelimited ;
			return segmentData;
	}
	
	@Override
	public String getSegmentDataforNASCOPCNTC(MemRow memRow, long entRecNum)
			throws Exception {
		try{
			getMemHeadValues(memRow);
			outputType = OrgEnum.NASCOPCNTC.getValue();
			if(getString(memRow,"phnumber").length() > 0 ){
				segmentData = outputType + strDelim + getString(memRow, "entrecno")  + strDelim + l_strSrcCd + 
				strDelim +l_memIdNum +strDelim + getString(memRow, "MD5KEY") + strDelim +
				getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+ strDelim +
				getString(memRow, "pharea")+ getString(memRow,"phnumber") +
				strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				return segmentData;
			}
		}catch(Exception e) {
			logInfo("Mde Error: Segment-NASCO_PCNTC entrecno-"+entRecNum);
		}
		return strBlank;
	}

	@Override
	public String getSegmentDataforQCAREAPADR(MemRow memRow, long entRecNum)
			throws Exception {
		try{
			getMemHeadValues(memRow);
			outputType = OrgEnum.QCARE_APADR.getValue();
			if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow,"COUNTYCD")) &&
					(getString(memRow,"addrtype").equalsIgnoreCase("178") ||
					getString(memRow,"addrtype").equalsIgnoreCase("179"))){
				segmentData = outputType + strDelim + getString(memRow, "entrecno")  + strDelim + l_strSrcCd + 
				strDelim +l_memIdNum +strDelim + getString(memRow, "MD5KEY") + strDelim +
				getString(memRow, "addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim +
				ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim +getString(memRow,"primaryaddress") +
				strDelim + getString(memRow,"COUNTYCD") + strDelim + getString(memRow,"ADDRCOUNTYFIPSCD")+
				strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				return segmentData;
			}
		}catch(Exception e) {
			logInfo("Mde Error: Segment-QCARE_APADR entrecno-"+entRecNum);
		}
		return strBlank;
	}

	@Override
	public String getSegmentDataforPALIAS(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSegmentDataforPEDU(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSegmentDataforPLANG(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSegmentDataforPTTL(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSegmentDataforWNET(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSegmentDataforAPTTL(MemRow memRow, long entRecNum) throws Exception {
		// TODO Auto-generated method stub
		   return null;
	}
}
