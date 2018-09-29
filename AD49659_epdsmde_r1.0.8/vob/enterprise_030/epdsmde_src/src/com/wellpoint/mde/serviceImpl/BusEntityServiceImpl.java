package com.wellpoint.mde.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.BusEnum;
import com.wellpoint.mde.service.BusEntityService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class BusEntityServiceImpl extends AbstractServiceImpl implements BusEntityService{

	@Override
	public String getSegmentDataforBALT(MemRow memRow, long entRecNum)
			throws Exception {
		outputType = BusEnum.BALT.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType,
				getString(memRow, "DFCDC_evtctime"), Long.toString(entRecNum),
				"EPDS V2" , getString(memRow, "idissuer"), 
				getString(memRow, "idnumber"), getString(memRow, "idtype"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate"),
				getString(memRow, "idtermrsn"), ExtMemgetIxnUtils.getNDelimiters(11),
				srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforBADR(MemRow memRow, long entRecNum)
	throws Exception {
		outputType = BusEnum.BADR.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType,
				getString(memRow, "DFCDC_evtctime"), Long.toString(entRecNum),
				"EPDS V2", getString(memRow, "md5key"),
				getString(memRow, "addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"),
				getString(memRow, "termrsn"), getString(memRow, "stline1"),
				getString(memRow, "stline2"), getString(memRow, "stline3"),
				getString(memRow, "city"), getString(memRow, "state"),
				getString(memRow, "zipcode"), getString(memRow, "zipextension"),
				getString(memRow, "countycd"), getString(memRow, "country"),
				getString(memRow, "DFCDC_evtinitiator"), getString(memRow, "DFCDC_evtctime"),
				getString(memRow, "DFCDC_mAudRecno"), getString(memRow,"suppresstdzn"),
				ExtMemgetIxnUtils.getNDelimiters(29), srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforBECON(MemRow memRow, long entRecNum)
	throws Exception {
		outputType = BusEnum.BECON.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2", getString(memRow,"taxid"),
				getString(memRow,"contractid"), ExtMemgetIxnUtils.getDateAsString(memRow, "contracteffectdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "contracttermdt"), getString(memRow,"contracttermrsn"),
				getString(memRow,"servicetabid"), getString(memRow,"servicetabrmbarrangetype"), strBlank,
				ExtMemgetIxnUtils.getDateAsString(memRow, "svctabideffectdt"), 
				ExtMemgetIxnUtils.getDateAsString(memRow, "svctabidtermdt"),
				getString(memRow,"svctabidtermrsn"), getString(memRow,"rmbarrangeid"), 
				getString(memRow,"rmbarrangeidtype"),
				ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd)+ "RMB" ,
				ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangeeffectdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarangetermdt"), getString(memRow,"rmbarrangetermrsn"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "cntrrmbarrangeeffectdt"), 
				ExtMemgetIxnUtils.getDateAsString(memRow, "cntrrmbarangetermdt"),
				getString(memRow,"cntrrmbarrangetermrsn"), getString(memRow,"claimstimelyfiledays"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "claimstimelyfileeffectdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "claimstimelyfiletermdt"),
				getString(memRow,"claimstimelyfiletermrsn"), getString(memRow, "DFCDC_evtinitiator"),
				getString(memRow, "DFCDC_evtctime"), getString(memRow, "DFCDC_mAudRecno"),
				ExtMemgetIxnUtils.getNDelimiters(11), srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforBUSGRP(MemRow memRow, long entRecNum)
	throws Exception {
		outputType = BusEnum.BUSGRP.getValue();
		getMemHeadValues(memRow);					
		if (getString(memRow, "RELMEMSRCCODE").length()>0){
			srccode_lookup = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")); 
		}
		else{
			srccode_lookup = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
		}
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2", getString(memRow, "elemdesc"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), getString(memRow, "termrsn"),
				ExtMemgetIxnUtils.getNDelimiters(11), srccode_lookup, 
				"GRP", srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforAPADR(MemRow memRow, long entRecNum)
	throws Exception {
		outputType=BusEnum.APADR.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType,getString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), "EPDS V2",
				getString(memRow, "md5key"), getString(memRow,"addrtype"),
				ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt"), 
				ExtMemgetIxnUtils.getNDelimiters(3), l_strSrcCd, l_memIdNum,
				ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt"),
				ExtMemgetIxnUtils.getDateAsString(memRow,"termdt"), 
				getString(memRow,"termrsn"), getString(memRow, "DFCDC_evtinitiator"),
				getString(memRow, "DFCDC_evtctime"), getString(memRow, "DFCDC_mAudRecno"),
				ExtMemgetIxnUtils.getNDelimiters(28), 
				getString(memRow, "DFCDC_evtinitiator"), getString(memRow, "DFCDC_evtctime"),
				getString(memRow, "DFCDC_mAudRecno"), ExtMemgetIxnUtils.getNDelimiters(59),
				ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd));
		return segmentData;
	}
	
	@Override
	public String buildBUSSegment(List<MemAttrRow> busBUSMemAttrList,
			long entRecNum) throws Exception {
		outputType = BusEnum.BUS.getValue();
		HashMap<String, String> hm_BUS = new HashMap<String, String>();
		for (MemRow memRow : busBUSMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			BusEnum attrCode = BusEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case BUSENTNAME:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "attrval"))) {
									segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" + strDelim;
									hm_BUS.put("MEMHEAD", segmentData);
								
									segmentData = getString(memRow, "attrval") + strDelim;
									hm_BUS.put("BUSENTNAME_1", segmentData);
								
									segmentData = getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")/*+ strDelim + srcCodesDelimited*/;
									hm_BUS.put("BUSENTNAME_2", segmentData);
								}
								break;
			
			case BUSENTTYPE: 	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "codeval"))) {
									segmentData = getString(memRow, "codeval") + strDelim;
									hm_BUS.put("BUSENTTYPE", segmentData);
								}
								break;

			case RSKBEARORGNM:	segmentData = getString(memRow, "attrval") + strDelim;
								hm_BUS.put("RSKBEARORGNM", segmentData);
								break;
								
			case BUFEDTAX1099:	segmentData = getString(memRow, "attrval") + strDelim;
								hm_BUS.put("BUFEDTAX1099", segmentData);
								break;
			
			case BUSENTSTATUS:	segmentData = getString(memRow,"attrval") 
								+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim 
								+getString(memRow, "termrsn")+ strDelim; 
								hm_BUS.put("BUSENTSTATUS", segmentData);
								break;
								
			case BUSENTSVCTAB:	segmentData = getString(memRow,"attrval") 
								+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
								+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
								+ strDelim + getString(memRow,"termrsn") + strDelim; 
								hm_BUS.put("BUSENTSVCTAB", segmentData);
								break;
			}
		}
		return generateSegmentsforBUS(hm_BUS, entRecNum);
	}
	
	private String generateSegmentsforBUS(HashMap<String, String> hm_BUS, long entRecNum) throws Exception {
		outputType = BusEnum.BUS.getValue();
		segmentData = strBlank;
		if(null != hm_BUS.get("BUSENTNAME_1") && null != hm_BUS.get("BUSENTTYPE")){
		if (null != hm_BUS.get("BUSENTNAME_1")
				|| null != hm_BUS.get("BUSENTTYPE")
				|| null != hm_BUS.get("RSKBEARORGNM")
				|| null != hm_BUS.get("BUFEDTAX1099")
				|| null != hm_BUS.get("BUSENTSTATUS")
				|| null != hm_BUS.get("BUSENTSVCTAB")
				|| null != hm_BUS.get("BUSENTNAME_2")) {

			if (null == hm_BUS.get("BUSENTNAME_1")) {
				segmentData = outputType + strDelim + strBlank + strDelim
						+ entRecNum + strDelim + "EPDS V2" + strDelim;
				hm_BUS.put("MEMHEAD", segmentData);
				hm_BUS.put("BUSENTNAME_1", ExtMemgetIxnUtils.getNDelimiters(1));
			}
		} else {
			hm_BUS.put("MEMHEAD", ExtMemgetIxnUtils.getNDelimiters(4));
			hm_BUS.put("BUSENTNAME_1", ExtMemgetIxnUtils.getNDelimiters(1));
		}
		if (null == hm_BUS.get("BUSENTTYPE"))
			hm_BUS.put("BUSENTTYPE", ExtMemgetIxnUtils.getNDelimiters(1));

		if (null == hm_BUS.get("RSKBEARORGNM"))
			hm_BUS.put("RSKBEARORGNM", ExtMemgetIxnUtils.getNDelimiters(1));

		if (null == hm_BUS.get("BUFEDTAX1099"))
			hm_BUS.put("BUFEDTAX1099", ExtMemgetIxnUtils.getNDelimiters(1));

		if (null == hm_BUS.get("BUSENTSTATUS"))
			hm_BUS.put("BUSENTSTATUS", ExtMemgetIxnUtils.getNDelimiters(4));

		if (null == hm_BUS.get("BUSENTSVCTAB"))
			hm_BUS.put("BUSENTSVCTAB", ExtMemgetIxnUtils.getNDelimiters(4));

		if (null == hm_BUS.get("BUSENTNAME_2"))
			hm_BUS.put("BUSENTNAME_2", ExtMemgetIxnUtils.getNDelimiters(2)
					+ "0");

		segmentData = hm_BUS.get("MEMHEAD") + hm_BUS.get("BUSENTNAME_1")
				+ hm_BUS.get("BUSENTTYPE") + hm_BUS.get("RSKBEARORGNM")
				+ hm_BUS.get("BUFEDTAX1099") + hm_BUS.get("BUSENTSTATUS")
				+ hm_BUS.get("BUSENTSVCTAB") + hm_BUS.get("BUSENTNAME_2");

		if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
				.length() > 0) {
			segmentData = segmentData + ExtMemgetIxnUtils.getNDelimiters(30)
					+ strDelim + srcCodesDelimited;
			return segmentData;
		}
		}
		return strBlank;
	}
	
	@Override
	public String buildBCNTCSegment(List<MemAttrRow> busBCNTCMemAttrList,
			long entRecNum) throws Exception {
		outputType = BusEnum.BCNTC.getValue();
		HashMap<String, String> hm_BCNTC = new HashMap<String, String>();
		for (MemRow memRow : busBCNTCMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			BusEnum attrCode = BusEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case BUSENTADDR:	 segmentData= outputType + strDelim + getString(memRow, "DFCDC_evtctime") 
								 + strDelim + entRecNum + strDelim + "EPDS V2" + strDelim;
								 hm_BCNTC.put("MEMHEAD",segmentData);
			    
								 segmentData = getString(memRow, "md5key") + strDelim + getString(memRow, "addrtype") 
								 	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim;
								 hm_BCNTC.put("BUSENTADDR",segmentData);
								 break;
			case BUSENTADRCNT:	segmentData = getString(memRow, "onmfirst") + strDelim 
								+ getString(memRow, "onmlast") + strDelim + getString(memRow, "onmmiddle") + strDelim 
								+ getString(memRow, "rolecd") + strDelim + getString(memRow, "titlecd") 
								+ strDelim + getString(memRow, "phicc") + strDelim 
								+ getString(memRow, "pharea") + strDelim + getString(memRow, "phnumber") + strDelim 
								+ getString(memRow, "phextn") + strDelim + getString(memRow, "teltype") + strDelim 
								+ getString(memRow, "fxarea") + strDelim 
								+ getString(memRow, "fxnumber") + strDelim 
								+ getString(memRow, "faxtype") + strDelim 
								+ getString(memRow, "url") + strDelim + getString(memRow, "urltype") + strDelim
								+ getString(memRow, "emailaddr") + strDelim 
								+ getString(memRow, "emailtype") + strDelim  
								+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
								+ getString(memRow, "DFCDC_mAudRecno");
								
								hm_BCNTC.put("BUSENTADRCNT", segmentData);
								break;
			}
		}
		return generateSegmentsforBCNTC(hm_BCNTC, entRecNum);
	}
	
	private String generateSegmentsforBCNTC(HashMap<String, String> hm_BCNTC,long entRecNum) throws Exception {
		outputType = BusEnum.BCNTC.getValue();
		if( hm_BCNTC.get("BUSENTADDR") != null || hm_BCNTC.get("BUSENTADRCNT") != null )
		{
			if (null == hm_BCNTC.get("BUSENTADDR"))
			{
				outputType = "BCNTC";
				segmentData = outputType + strDelim + strBlank + strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim ;
				hm_BCNTC.put("MEMHEAD", segmentData);	
				hm_BCNTC.put("BUSENTADDR", ExtMemgetIxnUtils.getNDelimiters(3));				
			}
					
		}
		else{
			hm_BCNTC.put("MEMHEAD", ExtMemgetIxnUtils.getNDelimiters(4));	
			hm_BCNTC.put("BUSENTADDR", ExtMemgetIxnUtils.getNDelimiters(3));	
		   }
		if (null == hm_BCNTC.get("BUSENTADRCNT")) {
			segmentData = ExtMemgetIxnUtils.getNDelimiters(19) + "0"/*+ strDelim + srcCodesDelimited*/;
			hm_BCNTC.put("BUSENTADRCNT",segmentData);
			
		}
		segmentData = hm_BCNTC.get("MEMHEAD")+ hm_BCNTC.get("BUSENTADDR") + hm_BCNTC.get("BUSENTADRCNT") ;
		if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0) {
			segmentData = segmentData + ExtMemgetIxnUtils.getNDelimiters(12) + strDelim + srcCodesDelimited;
			return segmentData;
		}
		return strBlank;
	}
	
	@Override
	public String buildBTAXSegment(List<MemAttrRow> busBTAXMemAttrList,
			long entRecNum) throws Exception {
		outputType = BusEnum.BTAX.getValue();
		HashMap<String, String> hm_BTAX = new HashMap<String, String>();
		for (MemRow memRow : busBTAXMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			BusEnum attrCode = BusEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case BUSENTTAXID:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "idnumber")) &&
									ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "idtype"))){
								segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2"  
								+ strDelim + getString(memRow, "idnumber") + strDelim;
								hm_BTAX.put("BUSENTTAXID_1",segmentData);
								
								segmentData = getString(memRow, "idtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "idtermrsn") + strDelim 
								+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
								+ strDelim + getString(memRow, "DFCDC_mAudRecno")
								+ ExtMemgetIxnUtils.getNDelimiters(30)+ strDelim + srcCodesDelimited;
								hm_BTAX.put("BUSENTTAXID_2",segmentData);
								}
								break;
								
			case BUFEDTAX1099:	segmentData = getString(memRow, "attrval") + strDelim;
								hm_BTAX.put("BUFEDTAX1099",segmentData);
								break;
			}
		}
		return generateSegmentsforBTAX(hm_BTAX, entRecNum);
	}

	private String generateSegmentsforBTAX(HashMap<String, String> hm_BTAX, long entRecNum) {
		outputType = BusEnum.BTAX.getValue();
		if(null != hm_BTAX.get("BUSENTTAXID_1")) { 
			if (null == hm_BTAX.get("BUFEDTAX1099")) {
				hm_BTAX.put("BUFEDTAX1099", ExtMemgetIxnUtils.getNDelimiters(1));
			}
			segmentData = hm_BTAX.get("BUSENTTAXID_1")+ hm_BTAX.get("BUFEDTAX1099") + hm_BTAX.get("BUSENTTAXID_2");
			return segmentData;
		}
		return strBlank;
	}
	
	@Override
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> busALTSRCIDMemAttrList,
			long entRecNum) throws Exception {
		outputType = BusEnum.ALTSRCID.getValue();
		HashMap<String, String> hm_ALTSRCID = new HashMap<String, String>();
		for (MemRow memRow : busALTSRCIDMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			BusEnum attrCode = BusEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case BUSENTNAME:	segmentData = getString(memRow, "attrval") + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim +  
								getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
								+ getString(memRow, "DFCDC_mAudRecno") + strDelim;
								hm_ALTSRCID.put("BUSENTNAME_1-"+new Long(memRow.getMemRecno()).toString(), segmentData);
    	  
								segmentData=getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
								+ strDelim + getString(memRow, "DFCDC_mAudRecno");
								hm_ALTSRCID.put("BUSENTNAME_2-"+new Long(memRow.getMemRecno()).toString(), segmentData);
								break;
								
			case BUSENTTYPE:	segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
								+ strDelim + "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim  ;
								hm_ALTSRCID.put("MEMHEAD-"+new Long(memRow.getMemRecno()).toString(), segmentData);
				         	 
								segmentData = getString(memRow, "codeval") + strDelim ;
								hm_ALTSRCID.put("BUSENTTYPE-"+new Long(memRow.getMemRecno()).toString(), segmentData);
								break;
								
			case BUSENTSTATUS:	segmentData = ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim ;
		    					hm_ALTSRCID.put("BUSENTSTATUS-"+new Long(memRow.getMemRecno()).toString(), segmentData);
		    					break;
			}
		}
		return generateSegmentsforALTSRCID(hm_ALTSRCID, entRecNum);
	}
	
	private Set<String> generateSegmentsforALTSRCID(HashMap<String, String> hm_ALTSRCID, long entRecNum) {
		outputType = BusEnum.ALTSRCID.getValue();
		Set<String> segmentDataSet = new HashSet<String>();

		Set <String>ALTSRCID_Keys = new HashSet<String>();
		//get the keys
		ALTSRCID_Keys = new HashSet<String>(hm_ALTSRCID.keySet());
		
		String split_keys[];
		String memrecno="";
		Set <String> ALTSRCID_memrecnos = new HashSet<String>();
		for (Iterator <String>iterator = ALTSRCID_Keys.iterator(); iterator
				.hasNext();) 
		{
			String ALTSRCID_key =iterator.next().toString();
			if(ALTSRCID_key.contains("BUSENTTYPE")
					||ALTSRCID_key.contains("BUSENTSTATUS")
					||ALTSRCID_key.contains("BUSENTNAME")
					||ALTSRCID_key.contains("MEMHEAD"))
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
			
			/*Getting the values from MEMHEAD_MAP against each memRecno*/
			if(null!=hm_MemHead)
			{
				MemHead temp_memHead ;
				temp_memHead = (MemHead)hm_MemHead.get(memrecno);
				l_strSrcCd = temp_memHead.getSrcCode();
				srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				l_memIdNum = temp_memHead.getMemIdnum();
			}
			//

			if(null != hm_ALTSRCID.get("BUSENTTYPE-"+memrecno)
					|| null != hm_ALTSRCID.get("BUSENTSTATUS-"+memrecno) 
					|| null != hm_ALTSRCID.get("BUSENTNAME_1-"+memrecno)|| null != hm_ALTSRCID.get("BUSENTNAME_2-"+memrecno) 
					 )
			{
				if (null == hm_ALTSRCID.get("BUSENTTYPE-"+memrecno)) 
			{
				outputType = "ALTSRCID";
				segmentData = outputType+ strDelim + strBlank + strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim +
				l_strSrcCd + strDelim + l_memIdNum + strDelim   ;
				hm_ALTSRCID.put("MEMHEAD-"+memrecno, segmentData);
				segmentData =  strBlank	+ strDelim  ;
				hm_ALTSRCID.put("BUSENTTYPE-"+memrecno, segmentData);
			}
			}
				else{
					hm_ALTSRCID.put("MEMHEAD-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(6));
					hm_ALTSRCID.put("BUSENTTYPE-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(1));
				}
				if (null == hm_ALTSRCID.get("BUSENTSTATUS-"+memrecno)) hm_ALTSRCID.put("BUSENTSTATUS-"+memrecno,  ExtMemgetIxnUtils.getNDelimiters(3)) ;
				
				if (null == hm_ALTSRCID.get("BUSENTNAME_1-"+memrecno)) {
					segmentData = ExtMemgetIxnUtils.getNDelimiters(6) + "0" +  strDelim ;
					hm_ALTSRCID.put("BUSENTNAME_1-"+memrecno, segmentData);
				}
				if (null == hm_ALTSRCID.get("BUSENTNAME_2-"+memrecno)) {
					segmentData = ExtMemgetIxnUtils.getNDelimiters(2) + "0"/*+ strDelim + getSrcCodeforPostProcess(l_strSrcCd)*/ ;
					hm_ALTSRCID.put("BUSENTNAME_2-"+memrecno, segmentData);
				}
				segmentData = hm_ALTSRCID.get("MEMHEAD-"+memrecno) +hm_ALTSRCID.get("BUSENTTYPE-"+memrecno) 
				              + hm_ALTSRCID.get("BUSENTSTATUS-"+memrecno) + hm_ALTSRCID.get("BUSENTNAME_1-"+memrecno)
				              + ExtMemgetIxnUtils.getNDelimiters(126) +hm_ALTSRCID.get("BUSENTNAME_2-"+memrecno);
				
				if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0) {
					segmentData = segmentData + ExtMemgetIxnUtils.getNDelimiters(60)+ strDelim + srcCode_postprocess;
					segmentDataSet.add(segmentData);
				}
			
		}
		return segmentDataSet;
	}
}
