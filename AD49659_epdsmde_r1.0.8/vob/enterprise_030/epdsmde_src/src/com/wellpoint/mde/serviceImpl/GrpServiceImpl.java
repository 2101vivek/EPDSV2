package com.wellpoint.mde.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.GrpEnum;
import com.wellpoint.mde.service.GroupingService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class GrpServiceImpl extends AbstractServiceImpl implements GroupingService{

	HashMap<String, String> hm_GRP = new HashMap<String, String>();
	
	HashMap<String, String> hm_ALTSRCID_Slg = new HashMap<String, String>();
	
	@Override
	public String getSegmentDataforGALT(MemRow memRow, long entRecNum)
			throws Exception {
		outputType = GrpEnum.GALT.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
					Long.toString(entRecNum) , "EPDS V2", getString(memRow, "idissuer"),
					getString(memRow, "idnumber"), getString(memRow, "idtype"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate"),
					getString(memRow, "idtermrsn"), ExtMemgetIxnUtils.getNDelimiters(29), srcCodesDelimited);
		return segmentData;
	}
	
	/*@Override
	public String getSegmentDataforGALTRO(MemRow memRow, long entRecNum)
			throws Exception {
		outputType = GrpEnum.GALTRO.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"), 
					Long.toString(entRecNum), "EPDS V2", getString(memRow, "rolloversrccode"), 
					getString(memRow, "rolloversrcval"),  getString(memRow, "rolloversrctype"), 
					ExtMemgetIxnUtils.getDateAsString(memRow, "rolloversrcissuedt"),
					getString(memRow, "rollovertranseqno"), getString(memRow, "rolloverrecipntsrccode"),
					getString(memRow, "rolloverrecipntsrcval"),getString(memRow, "rolloverrecipnttype"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "rolloverrecipntissuedt"), 
					getString(memRow, "rollovergrpprocind"),ExtMemgetIxnUtils.getDateAsString(memRow, "rollovergrpprocdt"),
					getString(memRow, "rolloverisgprocind"),ExtMemgetIxnUtils.getDateAsString(memRow, "rolloverisgprocdt"),
					getString(memRow, "rollovercsgprocind"),ExtMemgetIxnUtils.getDateAsString(memRow, "rollovercsgprocdt"),
					getString(memRow, "rolloverltrtype"),getString(memRow, "rolloveridcardind"),
					getString(memRow, "rolloversenderid"),getString(memRow, "rolloverreceipnt"),
					getString(memRow, "rolloverreceipntperc"),getString(memRow, "rolloverind"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "rollovereffectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "rollovertermdt"),
					getString(memRow, "rollovertermrsn"),getString(memRow, "DFCDC_evtinitiator"), 
					getString(memRow, "DFCDC_evtctime"),getString(memRow, "DFCDC_mAudRecno"),
					getString(memRow, "networkid"),getString(memRow, "networkidtype"),
					ExtMemgetIxnUtils.getNDelimiters(11),srcCodesDelimited);
		return segmentData;
	}*/
	
	@Override
	public String getSegmentDataforGPMDE(MemRow memRow, long entRecNum)
			throws Exception {
		outputType = GrpEnum.GPMDE.getValue();
		getMemHeadValues(memRow);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"), 
					Long.toString(entRecNum), "EPDS V2", getString(memRow, "attrval"), 
					ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), 
					getString(memRow, "termrsn"), srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforGRPPGM(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"), l_strSrcCd);
		outputType = GrpEnum.GRPPGM.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"), 
					Long.toString(entRecNum), "EPDS V2", getString(memRow, "busentid"),
					getString(memRow, "busenttaxid"), getString(memRow, "busentcontractid"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "busentcontracteffecdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "busentcontracttermdt"),
					getString(memRow, "busentcontracttermrsn"), getString(memRow, "orgid"),
					getString(memRow, "orgtaxid"), getString(memRow, "orgcontractid"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "orgcontracteffectdt"),
					getString(memRow, "busaddrid"), getString(memRow, "busaddrtype"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "busaddreffectdt"),
					getString(memRow,"programid"), getString(memRow, "cntrctstatecd"),
					(getString(memRow,"relmemsrccode").length()>0 ? getString(memRow,"relmemsrccode") : l_strSrcCd),
					ExtMemgetIxnUtils.getDateAsString(memRow,"programideffectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow,"programidtermdt"),
					getString(memRow,"programidtermrsn"), getString(memRow,"grprmbid"),
					getString(memRow,"rmbarrangetypecd"), 
					ExtMemgetIxnUtils.getDateAsString(memRow,"grprmbeffectdt"),
					getString(memRow, "innovpaymtypecd1"), getString(memRow, "payentitycd1"),
					ExtMemgetIxnUtils.getDateAsString(memRow,"innovpayeffectdt1"),
					ExtMemgetIxnUtils.getDateAsString(memRow,"innovpaytermdt1"),
					getString(memRow, "innovpaytermrsn1"), getString(memRow, "innovpaymtypecd2"),
					getString(memRow, "payentitycd2"), 
					ExtMemgetIxnUtils.getDateAsString(memRow,"innovpayeffectdt2"),
					ExtMemgetIxnUtils.getDateAsString(memRow,"innovpaytermdt2"), 
					getString(memRow, "innovpaytermrsn2"),
					getString(memRow, "DFCDC_evtinitiator"), getString(memRow, "DFCDC_evtctime"), 
					getString(memRow, "DFCDC_mAudRecno"), ExtMemgetIxnUtils.getNDelimiters(63),
					srccode_lookup, "BE", "ORG", "PPRG", srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforGRMB(MemRow memRow, long entRecNum)throws Exception {
		outputType = GrpEnum.GRMB.getValue();
		getMemHeadValues(memRow);
		if (getString(memRow, "RELMEMSRCCODE").length()>0){
			srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")); 
		}
		reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),getProp_relTypeCode());
		
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
					Long.toString(entRecNum), "EPDS V2", getString(memRow,"relmemidnum"),
					getString(memRow, "relmemsrccode"), getString(memRow, "reltype"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"),
					getString(memRow, "termrsn"), ExtMemgetIxnUtils.getNDelimiters(4),
					getString(memRow, "md5key"), getString(memRow, "mds5addrtype"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbreladdreffdt"),
					getString(memRow, "grprmbid"), getString(memRow, "rmbarrangetype"),
					ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "RMB",
					ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbeffectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbtermdt"),
					getString(memRow, "grprmbtermrsn"), getString(memRow, "caremgmtmpmtier"),
					getString(memRow, "ctrctid"), ExtMemgetIxnUtils.getDateAsString(memRow, "ctrctideffectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "ctrctidtermdt"), getString(memRow, "ctrctidtermrsn"),
					getString(memRow, "DFCDC_evtinitiator"), getString(memRow, "DFCDC_evtctime"),
					getString(memRow, "DFCDC_mAudRecno"), ExtMemgetIxnUtils.getNDelimiters(29),
					srcCode_postprocess, reltype_code, srcCodesDelimited);
	return segmentData;
	}
	
	@Override
	public String getSegmentDataforGNET(MemRow memRow, long entRecNum)throws Exception {
		outputType = GrpEnum.GNET.getValue();
		getMemHeadValues(memRow);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"), l_strSrcCd);
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
					Long.toString(entRecNum), "EPDS V2", getString(memRow, "relmemidnum"),
					getString(memRow, "relmemsrccode"), getString(memRow, "reltype"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), getString(memRow, "termrsn"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
					getString(memRow, "provrelorgid"), getString(memRow, "provrelorgsrc"),
					getString(memRow, "provrelorgreltype"), ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgeffectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgtermdt"),
					getString(memRow, "MD5KEY"), getString(memRow, "mds5addrtype"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
					getString(memRow, "networkid"), getString(memRow,"ntwkidtypecd"),
					ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "NET" ,
					getString(memRow, "nwownerid"), ExtMemgetIxnUtils.getDateAsString(memRow, "grpneteffectdt"),
					ExtMemgetIxnUtils.getDateAsString(memRow, "grpnettermdt"), getString(memRow, "grpnettermrsn"),
					ExtMemgetIxnUtils.getNDelimiters(29), srccode_lookup,
					ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),getProp_relTypeCode()),
					ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"provrelorgsrc")),
					"ORG", srcCodesDelimited);
		return segmentData;
	}
	
	@Override
	public String buildGRPSegment(List<MemAttrRow> grpGRPMemAttrList,
			long entRecNum) throws Exception {
		outputType = GrpEnum.GRP.getValue();
		for (MemRow memRow : grpGRPMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			GrpEnum attrCode = GrpEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			
			case GRPNAME:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "attrval"))) {
								segmentData = outputType + strDelim +getString(memRow, "DFCDC_evtctime")+ strDelim + entRecNum 
								+ strDelim + "EPDS V2" + strDelim + getString(memRow, "attrval")+ strDelim;
								hm_GRP.put("GRPNAME", segmentData);
							
								segmentData= getString(memRow, "DFCDC_evtinitiator") + strDelim 
								+ getString(memRow, "DFCDC_evtctime")+ strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim ;
								hm_GRP.put("GRPNAME_1", segmentData);
							}
							break;
			
			case GRPTYPE:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "codeval"))) {
								segmentData =getString(memRow, "codeval")+ strDelim;
								hm_GRP.put("GRPTYPE",segmentData);
							}
							break;
			
			case GRPID:		segmentData =getString(memRow, "attrval")+ strDelim ;
							hm_GRP.put("GRPID",segmentData);
							break;
			
			case GRPSTAT:	segmentData = getString(memRow, "attrval")+ strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
							+ strDelim + getString(memRow, "termrsn")+ strDelim;
							hm_GRP.put("GRPSTAT",segmentData);
							break;
							
			case GRPOWNNAME:segmentData=getString(memRow, "attrval")+ strDelim;
							hm_GRP.put("GRPOWNNAME",segmentData);
							break;
							
			case GRPLOCGROUP:
							segmentData = getString(memRow, "codeval")+ strDelim;
							hm_GRP.put("GRPLOCGROUP",segmentData);
							break;
			
			case GRPLOCGRPTYP:
							segmentData = getString(memRow, "codeval")+ strDelim;
							hm_GRP.put("GRPLOCGRPTYP",segmentData);
							break;
			
			case GRPFAMSWEEP:
							segmentData= getString(memRow, "attrval")+ strDelim + 
							ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + 
							ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")+ strDelim;
							hm_GRP.put("GRPFAMSWEEP",segmentData);
							break;
			
			case GRPMKTBU:	segmentData= getString(memRow, "attrval")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")+ strDelim;
							hm_GRP.put("GRPMKTBU",segmentData);
							break;
			
			case GRPMEMSUP:	segmentData= getString(memRow, "attrval")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")+ strDelim;
							hm_GRP.put("GRPMEMSUP",segmentData);
							break;
			
			case GRPFUNDTYPE:
							segmentData= getString(memRow, "attrval")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")+ strDelim ;
							hm_GRP.put("GRPFUNDTYPE",segmentData);
							break;
			
			case GRPNETARNGMT:
							segmentData= getString(memRow, "attrval")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")+ strDelim;
							hm_GRP.put("GRPNETARNGMT",segmentData);
							break;
			
			case GRPPRODTYPE:
							segmentData =getString(memRow, "attrval")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")+ strDelim;
							hm_GRP.put("GRPPRODTYPE",segmentData);
							break;
			
			case GRPSVCLOCZIP:
							segmentData= getString(memRow, "attrval")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")+ strDelim;
							hm_GRP.put("GRPSVCLOCZIP",segmentData);
							break;
			
			case GRPSVCEMPGRP:
							segmentData= getString(memRow, "grouptype")+ strDelim + getString(memRow, "groupnumber")+ strDelim 
							+ getString(memRow, "inclexclusionind")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")+ strDelim;
							hm_GRP.put("GRPSVCEMPGRP",segmentData);
							break;
			case GRPGBDCD:
				      segmentData=getString(memRow, "attrval");
				      hm_GRP.put("GRPGBDCD",segmentData);
				break;
				
			}
		}
		return generateSegmentsForGRP(entRecNum);
	}
	
	private String generateSegmentsForGRP(long entRecNum) throws Exception {
		outputType = GrpEnum.GRP.getValue();
		if(null != hm_GRP.get("GRPNAME") && null!=hm_GRP.get("GRPTYPE")) {
			if(null ==hm_GRP.get("GRPNAME"))hm_GRP.put("GRPNAME", ExtMemgetIxnUtils.getNDelimiters(5));
			if(null ==hm_GRP.get("GRPNAME_1"))hm_GRP.put("GRPNAME_1", ExtMemgetIxnUtils.getNDelimiters(2) + "0" + strDelim);
			if(null ==hm_GRP.get("GRPTYPE"))hm_GRP.put("GRPTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
			if(null ==hm_GRP.get("GRPID"))hm_GRP.put("GRPID", ExtMemgetIxnUtils.getNDelimiters(1));
			if(null ==hm_GRP.get("GRPOWNNAME"))hm_GRP.put("GRPOWNNAME", ExtMemgetIxnUtils.getNDelimiters(1));
			if(null ==hm_GRP.get("GRPSTAT"))hm_GRP.put("GRPSTAT", ExtMemgetIxnUtils.getNDelimiters(4));
			if(null ==hm_GRP.get("GRPLOCGROUP"))hm_GRP.put("GRPLOCGROUP", ExtMemgetIxnUtils.getNDelimiters(1));
			if(null ==hm_GRP.get("GRPLOCGRPTYP"))hm_GRP.put("GRPLOCGRPTYP", ExtMemgetIxnUtils.getNDelimiters(1));

			if(null ==hm_GRP.get("GRPFAMSWEEP"))hm_GRP.put("GRPFAMSWEEP", ExtMemgetIxnUtils.getNDelimiters(4));
			if(null ==hm_GRP.get("GRPMKTBU"))hm_GRP.put("GRPMKTBU", ExtMemgetIxnUtils.getNDelimiters(4));
			if(null ==hm_GRP.get("GRPMEMSUP"))hm_GRP.put("GRPMEMSUP", ExtMemgetIxnUtils.getNDelimiters(4));
			if(null ==hm_GRP.get("GRPFUNDTYPE"))hm_GRP.put("GRPFUNDTYPE", ExtMemgetIxnUtils.getNDelimiters(4));
			if(null ==hm_GRP.get("GRPNETARNGMT"))hm_GRP.put("GRPNETARNGMT", ExtMemgetIxnUtils.getNDelimiters(4));
			if(null ==hm_GRP.get("GRPPRODTYPE"))hm_GRP.put("GRPPRODTYPE", ExtMemgetIxnUtils.getNDelimiters(4));
			if(null ==hm_GRP.get("GRPSVCLOCZIP"))hm_GRP.put("GRPSVCLOCZIP", ExtMemgetIxnUtils.getNDelimiters(4));
			if(null ==hm_GRP.get("GRPSVCEMPGRP"))hm_GRP.put("GRPSVCEMPGRP", ExtMemgetIxnUtils.getNDelimiters(6));
			if(null ==hm_GRP.get("GRPGBDCD"))hm_GRP.put("GRPGBDCD", ExtMemgetIxnUtils.getNDelimiters(0));

			segmentData = 
				hm_GRP.get("GRPNAME")+
				hm_GRP.get("GRPTYPE") + 
				hm_GRP.get("GRPID") + 
				hm_GRP.get("GRPOWNNAME") + 
				hm_GRP.get("GRPSTAT")+ 
				hm_GRP.get("GRPLOCGROUP")+
				hm_GRP.get("GRPLOCGRPTYP")+
				hm_GRP.get("GRPFAMSWEEP") + 
				hm_GRP.get("GRPMKTBU")+ 
				hm_GRP.get("GRPMEMSUP")+
				hm_GRP.get("GRPFUNDTYPE")+
				hm_GRP.get("GRPNETARNGMT") +
				hm_GRP.get("GRPPRODTYPE") + 
				hm_GRP.get("GRPSVCLOCZIP") + 
				hm_GRP.get("GRPSVCEMPGRP")+ 
				hm_GRP.get("GRPNAME_1")+
				hm_GRP.get("GRPGBDCD");

			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0) {
				segmentData += ExtMemgetIxnUtils.getNDelimiters(29) + strDelim + srcCodesDelimited;
			}
			return segmentData;
		}
		return strBlank;
	}
	
	@Override
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> grpGRPMemAttrList,
			long entRecNum) throws Exception {
		outputType=GrpEnum.ALTSRCID.getValue();
		for (MemRow memRow : grpGRPMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			GrpEnum attrCode = GrpEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			
			case GRPNAME:	segmentData = getString(memRow, "attrval") + strDelim + strBlank + strDelim 
							+ strBlank + strDelim + strBlank + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator")  + strDelim 
							+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim;
							hm_ALTSRCID_Slg.put("GRPNAME_1-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
							
							segmentData = getString(memRow, "DFCDC_evtinitiator")  + strDelim 
							+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno");
							hm_ALTSRCID_Slg.put("GRPNAME_2-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
							break;
							
			case GRPTYPE:	hm_ALTSRCID_Slg.put("GRPTYPE-"+new Long(memRow.getMemRecno()).toString(),
							getString(memRow, "codeval")+ strDelim);
							break;
			case GRPSTAT:	segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") 
							+ strDelim + entRecNum + strDelim 
							+ "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim;
							hm_ALTSRCID_Slg.put("MemHead_1-" + new Long(memRow.getMemRecno()).toString(), segmentData);
							
							segmentData = ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
							+ strDelim + getString(memRow, "termrsn")+ strDelim;
							hm_ALTSRCID_Slg.put("GRPSTAT-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
							break;
			}
		}
		
		return generateSegmentsforALTSRCID(hm_ALTSRCID_Slg, entRecNum);
	}
	
	private Set<String> generateSegmentsforALTSRCID(HashMap<String,String> hm_ALTSRCID_Slg,long entRecNum) throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		Set <String>ALTSRCID_Keys = new HashSet<String>();
		ALTSRCID_Keys = new HashSet<String>(hm_ALTSRCID_Slg.keySet());
		outputType = GrpEnum.ALTSRCID.getValue();
		String split_keys[];
		String memrecno="";
		Set <String> ALTSRCID_memrecnos = new HashSet<String>();
		for (Iterator <String>iterator = ALTSRCID_Keys.iterator(); iterator
				.hasNext();) {
			String ALTSRCID_key =iterator.next().toString();
			if(ALTSRCID_key.contains("GRPSTAT") ||ALTSRCID_key.contains("GRPTYPE")
					||ALTSRCID_key.contains("GRPNAME_1") || ALTSRCID_key.contains("GRPNAME_2")
					||ALTSRCID_key.contains("MemHead_1")) {
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
				.hasNext();) {
			//write ALTSRCID segment - BEGIN
			memrecno  = (String) iterator2.next();
			if(null!=hm_MemHead) {
				MemHead temp_memHead ;
				temp_memHead = (MemHead)hm_MemHead.get(memrecno);
				l_strSrcCd = temp_memHead.getSrcCode();
				l_memIdNum = temp_memHead.getMemIdnum();
				srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
			}
			if ((null == hm_ALTSRCID_Slg.get("GRPSTAT-"+memrecno)) && (null!=hm_ALTSRCID_Slg.get("GRPTYPE-"+memrecno)||
					null!=hm_ALTSRCID_Slg.get("GRPNAME_1-"+memrecno) || null!=hm_ALTSRCID_Slg.get("GRPNAME_2-"+memrecno))) {
				segmentData = outputType + strDelim + strBlank+ strDelim + entRecNum + strDelim 
				+ "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim;
				hm_ALTSRCID_Slg.put("MemHead_1-"+memrecno, segmentData);
				hm_ALTSRCID_Slg.put("GRPSTAT-"+ memrecno, ExtMemgetIxnUtils.getNDelimiters(3));
			}
			
			if (null==hm_ALTSRCID_Slg.get("MemHead_1-"+memrecno))hm_ALTSRCID_Slg.put("MemHead_1-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(6));
			if (null == hm_ALTSRCID_Slg.get("GRPTYPE-"+memrecno)) hm_ALTSRCID_Slg.put("GRPTYPE-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(1));
			if (null==hm_ALTSRCID_Slg.get("GRPSTAT-"+memrecno))hm_ALTSRCID_Slg.put("GRPSTAT-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(3));
			if (null == hm_ALTSRCID_Slg.get("GRPNAME_1-"+memrecno)) hm_ALTSRCID_Slg.put("GRPNAME_1-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(6)+ "0" + strDelim);
			/*if (null == hm_ALTSRCID_Slg.get("MemHead_2-"+memrecno)) hm_ALTSRCID_Slg.put("MemHead_2-"+memrecno, getNDelimiters(1));*/
			if (null == hm_ALTSRCID_Slg.get("GRPNAME_2-"+memrecno)) hm_ALTSRCID_Slg.put("GRPNAME_2-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(2)+ "0"/*+ strDelim + getSrcCodeforPostProcess(l_strSrcCd)*/ );
			
			segmentData = hm_ALTSRCID_Slg.get("MemHead_1-"+memrecno) +hm_ALTSRCID_Slg.get("GRPTYPE-"+memrecno)+ hm_ALTSRCID_Slg.get("GRPSTAT-"+memrecno) + hm_ALTSRCID_Slg.get("GRPNAME_1-"+memrecno) 
						+ /*2.7 code change*/ ExtMemgetIxnUtils.getNDelimiters(126)+ hm_ALTSRCID_Slg.get("GRPNAME_2-"+memrecno);

			//logInfo("ALTSRCID_Slg: " + segmentData);
			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0) {
				segmentData +=strDelim + ExtMemgetIxnUtils.getNDelimiters(60)+ srcCode_postprocess;
				segmentDataSet.add(segmentData);
			}
		}
		//write ALTSRCID segment - END
		return segmentDataSet;
	}

}
