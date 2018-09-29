package com.wellpoint.mde.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.OrgEnum;
import com.wellpoint.mde.service.OrgCleanupSkinnyService;
import com.wellpoint.mde.service.OrgSkinnyService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class OrgSkinnyServiceImpl extends AbstractServiceImpl implements OrgSkinnyService,OrgCleanupSkinnyService {
	
	private String srcCode = strBlank;
	
	public String getSrcCode() {
		return srcCode;
	}

	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
	}

	@Override
	public String getSegmentDataforPPPRF(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = OrgEnum.PPPRF.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType,getCDCString(memRow, "DFCDC_evtctime"),
				Long.toString(entRecNum), l_memIdNum, l_strSrcCd,
				ExtMemgetIxnUtils.getString(memRow, "md5key"), 
				ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),
				ExtMemgetIxnUtils.getNDelimiters(10), ExtMemgetIxnUtils.getString(memRow, "patlimittype"),
				ExtMemgetIxnUtils.getString(memRow, "patlimitval"),	ExtMemgetIxnUtils.getString(memRow, "patlimitvaltypefrm"),
				ExtMemgetIxnUtils.getString(memRow, "pallimitvalto"), ExtMemgetIxnUtils.getString(memRow, "patlimitvaltypeto"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "patlimiteffdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "patlimittermdt"),
				ExtMemgetIxnUtils.getString(memRow, "patlimittermrsn"));
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPNET(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = OrgEnum.PNET.getValue();
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
				ExtMemgetIxnUtils.getString(memRow, "agecattype"), /*Cff 3.8 changes*/ExtMemgetIxnUtils.getString(memRow, "relatedid"),ExtMemgetIxnUtils.getString(memRow, "relmemsrccode"),
				ExtMemgetIxnUtils.getString(memRow,"dirdisindflg"),
				ExtMemgetIxnUtils.getString(memRow, "nwacceptflg"));
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPRMB(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = OrgEnum.PRMB.getValue();
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
				ExtMemgetIxnUtils.getString(memRow, "acptnewptind"),/*Cff 3.8 changes*/ExtMemgetIxnUtils.getString(memRow, "relatedid"), ExtMemgetIxnUtils.getString(memRow, "relmemsrccode"));
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPREL(MemRow memRow, long entRecNum)
	throws Exception {
     getMemHeadValues(memRow);
	 outputType =  OrgEnum.PREL.getValue();
	 segmentData = ExtMemgetIxnUtils.appendStr(outputType, getCDCString(memRow, "DFCDC_evtctime"),Long.toString(entRecNum), l_memIdNum,
			 			l_strSrcCd,ExtMemgetIxnUtils.getString(memRow, "md5key"), ExtMemgetIxnUtils.getString(memRow, "mds5addrtype"), 
			 			ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"),ExtMemgetIxnUtils.getNDelimiters(10),ExtMemgetIxnUtils.getString(memRow, "phnumber"),
			 			ExtMemgetIxnUtils.getString(memRow, "fxnumber"),
			 			ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt"),ExtMemgetIxnUtils.getDateAsString(memRow, "reladdrtermdt"),
			 			ExtMemgetIxnUtils.getString(memRow, "reladdrtermrsn"),ExtMemgetIxnUtils.getString(memRow, "relmemidnum"),ExtMemgetIxnUtils.getString(memRow, "reltype"),
			 			ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"),ExtMemgetIxnUtils.getString(memRow, "termrsn"),
			 			ExtMemgetIxnUtils.getString(memRow, "relattrval1"),ExtMemgetIxnUtils.getString(memRow, "relattrval2"),ExtMemgetIxnUtils.getString(memRow, "relattrval3"),
			 			ExtMemgetIxnUtils.getString(memRow, "relattrval4"),ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt"),
			 			ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt"),ExtMemgetIxnUtils.getString(memRow, "relmemsrccode"));
	        return segmentData; 
	}
	
	
	
	@Override
	public String getSegmentDataforPSPT(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = OrgEnum.PSPT.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType,getCDCString(memRow, "DFCDC_evtctime"),Long.toString(entRecNum), l_memIdNum,
				l_strSrcCd,ExtMemgetIxnUtils.getString(memRow,"specialtycd"),ExtMemgetIxnUtils.getString(memRow,"primaryspec"),
				ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt"),ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt"),
				ExtMemgetIxnUtils.getString(memRow,"spectermrsn"),ExtMemgetIxnUtils.getString(memRow,"speclgcycode"));
		return segmentData; 
	}

	@Override
	public Set<String> buildPALTSegment(List<MemAttrRow> orgPALTMemAttrList, long entRecNum)
			throws Exception {
		outputType = OrgEnum.PALT.getValue();
		Map<String, String[]> paltMap = new HashMap<String, String[]>();
		Map<String,ArrayList<String>> specialityMap = new HashMap<String,ArrayList<String>>();
		
		for (MemRow memRow : orgPALTMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode().toUpperCase();
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
								if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow,"idnumber"))
									&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "idtype")) 
									&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate"))
									&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate"))){
										String[] paltSegment = new String[2];
										String type = strBlank;
										String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
										if(l_strAttrCode.equals(OrgEnum.LICENSEORG.getValue()) 
												&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "licensetype"))){
											
											type = ExtMemgetIxnUtils.getString(memRow, "licensetype");
											paltSegment[0] = ExtMemgetIxnUtils.appendStr(outputType, strDFCDC_evtctime, Long.toString(entRecNum),
													l_memIdNum, l_strSrcCd, ExtMemgetIxnUtils.getString(memRow,"idissuer"),
													ExtMemgetIxnUtils.getString(memRow,"idnumber"), ExtMemgetIxnUtils.getString(memRow,"idtype"),
													ExtMemgetIxnUtils.getDateAsString(memRow,"idissuedate"), ExtMemgetIxnUtils.getDateAsString(memRow,"idexpdate"),
													ExtMemgetIxnUtils.getString(memRow,"idtermrsn"), ExtMemgetIxnUtils.getString(memRow,"certnumber"),
													ExtMemgetIxnUtils.getString(memRow,"status"), ExtMemgetIxnUtils.getString(memRow,"agencyname"),
													ExtMemgetIxnUtils.getString(memRow,"agencytypecd"), ExtMemgetIxnUtils.getString(memRow,"agencyacrtxt"),
													ExtMemgetIxnUtils.getDateAsString(memRow,"agencyeffdt"), ExtMemgetIxnUtils.getDateAsString(memRow,"agencytermdt"),
													ExtMemgetIxnUtils.getString(memRow,"agencytrmrsn"), ExtMemgetIxnUtils.getString(memRow,"agencyemail"),
													ExtMemgetIxnUtils.getString(memRow,"agencyurl"), ExtMemgetIxnUtils.getString(memRow,"agcytelarcd"),
													ExtMemgetIxnUtils.getString(memRow,"agencytelnum"), ExtMemgetIxnUtils.getString(memRow,"md5key"), strBlank,
													ExtMemgetIxnUtils.getString(memRow,"mds5addrtype"), ExtMemgetIxnUtils.getDateAsString(memRow,"mds5addreffectdt"),
													ExtMemgetIxnUtils.getNDelimiters(11));
											
										}else{
											type = ExtMemgetIxnUtils.getString(memRow, "IDTYPE");
											paltSegment[0] = ExtMemgetIxnUtils.appendStr(outputType, strDFCDC_evtctime, Long.toString(entRecNum),
													l_memIdNum, l_strSrcCd, ExtMemgetIxnUtils.getString(memRow,"idissuer"),
													ExtMemgetIxnUtils.getString(memRow,"idnumber"), ExtMemgetIxnUtils.getString(memRow,"idtype"),
													ExtMemgetIxnUtils.getDateAsString(memRow,"idissuedate"), ExtMemgetIxnUtils.getDateAsString(memRow,"idexpdate"),
													ExtMemgetIxnUtils.getString(memRow,"idtermrsn"),  ExtMemgetIxnUtils.getNDelimiters(11),
													ExtMemgetIxnUtils.getString(memRow,"md5key"), strBlank, ExtMemgetIxnUtils.getString(memRow,"mds5addrtype"), 
													ExtMemgetIxnUtils.getDateAsString(memRow,"mds5addreffectdt"), ExtMemgetIxnUtils.getNDelimiters(11));
										}
										
										paltSegment[1] = ExtMemgetIxnUtils.appendStr(ExtMemgetIxnUtils.getString(memRow,"npiedsregind"),
												ExtMemgetIxnUtils.getDateAsString(memRow,"npiedseffectdt"), strBlank,
												strBlank, ExtMemgetIxnUtils.getString(memRow,"wgscntrtiercd"),
												ExtMemgetIxnUtils.getString(memRow,"proxtiercd"), ExtMemgetIxnUtils.getString(memRow,"proxmbrcnt"),
												ExtMemgetIxnUtils.getString(memRow,"proxmbrcapcnt"), ExtMemgetIxnUtils.getDateAsString(memRow,"proxmbrcntupddt"),
												ExtMemgetIxnUtils.getString(memRow,"proxfrmage"), ExtMemgetIxnUtils.getString(memRow,"proxtoage"),
												ExtMemgetIxnUtils.getString(memRow,"proxgendercd"), ExtMemgetIxnUtils.getString(memRow,"proxsrvcind"),
												ExtMemgetIxnUtils.getDateAsString(memRow,"proxepreffdt"), ExtMemgetIxnUtils.getDateAsString(memRow,"proxeprtermdt"),
												ExtMemgetIxnUtils.getString(memRow,"acesmedintname"), ExtMemgetIxnUtils.getString(memRow,"acesmedpartind"),
												ExtMemgetIxnUtils.getString(memRow,"epdsssbpaiid"), ExtMemgetIxnUtils.getDateAsString(memRow,"epdsssbpapeffdt"),
												ExtMemgetIxnUtils.getDateAsString(memRow,"epdsssbpaptermdt"), ExtMemgetIxnUtils.getString(memRow,"epdsssbpaptermrsn"),
												ExtMemgetIxnUtils.getDateAsString(memRow,"epdsssbqlfdeffdt"), ExtMemgetIxnUtils.getString(memRow,"epdsssbmdbtcd"));
										
										String idNumber = ExtMemgetIxnUtils.getString(memRow, "IDNUMBER");
										if (idNumber.contains("-")) {
											idNumber = idNumber.replaceAll("-", "*");
										}
										paltMap.put(type+"-"  + idNumber+"-" 
												+ ExtMemgetIxnUtils.getString(memRow, "md5key")+"-" 
												+ ExtMemgetIxnUtils.getString(memRow, "MDS5ADDRTYPE")+"-" 
												+ ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"
												+ ExtMemgetIxnUtils.getString(memRow,"memrecno") ,
												paltSegment );
								}
							}
								break;
			case ORGALTIDSPEC: {
								String idNumber = ExtMemgetIxnUtils.getString(memRow, "IDNUMBER");
								if (idNumber.contains("-")) {
									idNumber = idNumber.replaceAll("-", "*");
								}
								String spec_key = ExtMemgetIxnUtils.getString(memRow, "IDTYPE")+"-" + idNumber+"-" + 
													ExtMemgetIxnUtils.getString(memRow, "md5key")+"-" + 
													ExtMemgetIxnUtils.getString(memRow, "MDS5ADDRTYPE")+"-" + 
													ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT") +"-" + 
													ExtMemgetIxnUtils.getString(memRow,"memrecno");
								segmentData = ExtMemgetIxnUtils.appendStr(ExtMemgetIxnUtils.getString(memRow, "specialtycd"), ExtMemgetIxnUtils.getString(memRow, "primaryspec"),
												ExtMemgetIxnUtils.getString(memRow, "specorgcd"), ExtMemgetIxnUtils.getString(memRow, "specsetcd"),
												ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt"),
												ExtMemgetIxnUtils.getString(memRow, "spectermrsn"), ExtMemgetIxnUtils.getString(memRow, "boardcertcd"),
												ExtMemgetIxnUtils.getDateAsString(memRow, "boardcertdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "boardcertexpirydt"),
												ExtMemgetIxnUtils.getString(memRow, "agencyname"), strBlank); 
								ArrayList<String> specialityList = new ArrayList<String>();
								if(ExtMemgetIxnUtils.isNotEmpty(specialityMap)) {
									if(specialityMap.containsKey(spec_key)) {//if contain key
										specialityList = specialityMap.get(spec_key);
									}
								}
								specialityList.add(segmentData);
								specialityMap.put(spec_key, specialityList);
								}
								break;
			}
		}
		return generateSegmentsforPALT(paltMap, specialityMap);
	}
	
	private Set<String> generateSegmentsforPALT(Map<String, String[]> paltMap, Map<String,ArrayList<String>> specialityMap)  throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		HashMap<String,String> paltSpecialtyMap = new HashMap<String,String>();
		if (ExtMemgetIxnUtils.isNotEmpty(paltMap)) {			
			if(ExtMemgetIxnUtils.isNotEmpty(specialityMap)){
				
				Set<String> specialtyKeySet = specialityMap.keySet();
				for (Iterator<String> iterator1 = specialtyKeySet.iterator(); iterator1.hasNext();) {
					
					String specialtyKey = (String) iterator1.next();
					String[] speciality = new String[5];
					Arrays.fill(speciality, ExtMemgetIxnUtils.getNDelimiters(11));
					ArrayList<String> valueList = specialityMap.get(specialtyKey);
					int count = valueList.size();
					for (Iterator<String> iterator_value = valueList.iterator(); iterator_value.hasNext();) {
						String specialityInfo = (String) iterator_value.next();						
						if(count > 0 && count <= 5 && specialityInfo != null) {
							speciality[count - 1] = specialityInfo;
						}	
						count --;
					}
					segmentData = speciality[0]+speciality[1]+speciality[2]+speciality[3]+speciality[4];
					paltSpecialtyMap.put(specialtyKey, segmentData);
					segmentData = "";
				}
			}
			//get the keys
			Set <String>PALT_Keys = new HashSet<String>(paltMap.keySet());
			//PALT_Keys = hm_PALT1.keySet();
			for (Iterator<String> iterator = PALT_Keys.iterator(); iterator.hasNext();) {
				String PALT_Key = iterator.next();
				if (null == specialityMap.get(PALT_Key)) {
					segmentData = ExtMemgetIxnUtils.getNDelimiters(55);
					paltSpecialtyMap.put( PALT_Key, segmentData);
				}						
				String[] paltSegments = paltMap.get(PALT_Key);
				if(null != paltSegments){
					segmentData = paltSegments[0] + paltSpecialtyMap.get(PALT_Key)+ paltSegments[1];
					segmentDataSet.add(segmentData);
				}
			}					
		}
		return segmentDataSet;
	}

	@Override
	public Set<String> buildPREMSegment(List<MemAttrRow> orgPREMMemAttrList, long entRecNum) throws Exception {
		outputType 	= 	OrgEnum.PREM.getValue();
		Map<String, String[]> remitMap = new HashMap<String, String[]>();
		Map<String, String[]> remitDetailMap = new HashMap<String, String[]>();
		for (MemRow memRow : orgPREMMemAttrList){
			getMemHeadValues(memRow);
			String l_memrecno = new Long(memRow.getMemRecno()).toString();
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
				case REMITSEG:	{
					if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow,"remitaddrtype"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "remitlocid"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt")) 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddrtermdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "remitaddrline1"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "remitaddrcity"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "remitaddrstate"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "remitaddrzipcode"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "remitchecknm"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegeffdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegtermdt"))){
						String[] remitSegment = new String[2];
						String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
						remitSegment[0] = ExtMemgetIxnUtils.appendStr(outputType,strDFCDC_evtctime, Long.toString(entRecNum),
								l_memIdNum, l_strSrcCd, ExtMemgetIxnUtils.getNDelimiters(16), ExtMemgetIxnUtils.getString(memRow,"remitaddrtype"),
								ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddrtermdt"),
								ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddrtermrsn"), ExtMemgetIxnUtils.getString(memRow, "remitaddrline1"),
								ExtMemgetIxnUtils.getString(memRow, "remitaddrline2"), ExtMemgetIxnUtils.getString(memRow, "remitaddrline3"),
								ExtMemgetIxnUtils.getString(memRow, "remitaddrcity"), ExtMemgetIxnUtils.getString(memRow, "remitaddrstate"),
								ExtMemgetIxnUtils.getString(memRow, "remitaddrzipcode"), ExtMemgetIxnUtils.getString(memRow, "remitaddrzipextension"),
								ExtMemgetIxnUtils.getString(memRow, "remitaddrcountycd"), ExtMemgetIxnUtils.getString(memRow, "remitaddrcountrycd"),
								ExtMemgetIxnUtils.getString(memRow, "remitchecknm"), ExtMemgetIxnUtils.getString(memRow, "eftroutenum"),
								ExtMemgetIxnUtils.getString(memRow, "eftaccountnum"), ExtMemgetIxnUtils.getString(memRow, "eremadvaddrtxt"),
								ExtMemgetIxnUtils.getString(memRow, "lockboxaddrtxt"), ExtMemgetIxnUtils.getString(memRow, "remitmethodcd"),
								ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegeffdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "remitsegtermdt"),
								ExtMemgetIxnUtils.getString(memRow, "remitsegtermrsn"), ExtMemgetIxnUtils.getString(memRow, "remadvmethodcd"),strBlank);
						
						remitSegment[1] = ExtMemgetIxnUtils.appendStr(ExtMemgetIxnUtils.getString(memRow, "altidsource"), 
								ExtMemgetIxnUtils.getString(memRow, "altidnumber"), ExtMemgetIxnUtils.getString(memRow, "altidtype"),
								ExtMemgetIxnUtils.getDateAsString(memRow, "altidissuedt"), ExtMemgetIxnUtils.getDateAsString(memRow, "altidtermdt"),
								ExtMemgetIxnUtils.getString(memRow, "altidissuer"), ExtMemgetIxnUtils.getString(memRow, "payidsrc"),
								ExtMemgetIxnUtils.getString(memRow, "payid"), ExtMemgetIxnUtils.getString(memRow, "payidtype"),
								ExtMemgetIxnUtils.getDateAsString(memRow, "payidissuedt"), ExtMemgetIxnUtils.getDateAsString(memRow, "payidtermdt"),
								ExtMemgetIxnUtils.getString(memRow, "fedtaxid"), ExtMemgetIxnUtils.getString(memRow, "fedtaxid1099"),
								ExtMemgetIxnUtils.getString(memRow, "fedtaxidtype"), ExtMemgetIxnUtils.getDateAsString(memRow, "fedtaxideffdt"),
								ExtMemgetIxnUtils.getDateAsString(memRow, "fedtaxidtermdt"), ExtMemgetIxnUtils.getString(memRow, "fedtaxidtermrsn"),
								ExtMemgetIxnUtils.getString(memRow, "relatedprovid"), ExtMemgetIxnUtils.getString(memRow, "relatedprovreltype"),
								ExtMemgetIxnUtils.getDateAsString(memRow, "relatedprovreleffectdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "relatedprovreltermdt"),
								ExtMemgetIxnUtils.getString(memRow, "relatedprovreltermrsn"), ExtMemgetIxnUtils.getString(memRow, "w9receipttype"),
								ExtMemgetIxnUtils.getDateAsString(memRow, "w9receiptdt"), ExtMemgetIxnUtils.getString(memRow, "payadjusttype"),
								ExtMemgetIxnUtils.getString(memRow, "provsubscpaytype"), ExtMemgetIxnUtils.getString(memRow, "remitlocid"),
								ExtMemgetIxnUtils.getString(memRow, "aceseftind"), ExtMemgetIxnUtils.getString(memRow, "acesoprind"),
								ExtMemgetIxnUtils.getString(memRow, "acespapersprs"), ExtMemgetIxnUtils.getString(memRow, "acesovrind"),
								ExtMemgetIxnUtils.getNDelimiters(3), ExtMemgetIxnUtils.getString(memRow, "RELMEMSRCCODE"));
						
						String remitchecknm = ExtMemgetIxnUtils.getString(memRow, "remitchecknm");
						if (remitchecknm.contains("-") || remitchecknm.contains("*")) {
							remitchecknm = remitchecknm.replaceAll("[-\\*]", "");
						}
						String Key = remitchecknm+"-"+ ExtMemgetIxnUtils.getString(memRow, "md5key")+"-"
									+ ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") + "-"
									+ l_memrecno + "-" + ExtMemgetIxnUtils.getString(memRow, "payid");
						remitMap.put(Key, remitSegment);
					}
				}
								break;
				case REMITSEGDTL: {
					String[] remitDetailSegment = new String[2];
					remitDetailSegment[0] =  ExtMemgetIxnUtils.getString(memRow, "remitindicator") + strDelim;
						
					remitDetailSegment[1] = ExtMemgetIxnUtils.appendStr(ExtMemgetIxnUtils.getDateAsString(memRow, "eftenroleffdt"), 
							ExtMemgetIxnUtils.getDateAsString(memRow, "eftenrolenddt"), ExtMemgetIxnUtils.getDateAsString(memRow, "oprenrleffdt"),
							ExtMemgetIxnUtils.getDateAsString(memRow, "oprenrlenddt"), ExtMemgetIxnUtils.getDateAsString(memRow, "pprsuprseffdt"),
							ExtMemgetIxnUtils.getDateAsString(memRow, "pprsuprsenddt"), ExtMemgetIxnUtils.getDateAsString(memRow, "pprsuprsovrdeffdt"),
							ExtMemgetIxnUtils.getDateAsString(memRow, "pprsuprsovrdenddt"), ExtMemgetIxnUtils.getString(memRow, "cpmfdeviceid"),
							ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftadddt"), ExtMemgetIxnUtils.getString(memRow, "cpmfeftaddfirst"),
							ExtMemgetIxnUtils.getString(memRow, "cpmfeftaddlast"), ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftchngdt1"),
							ExtMemgetIxnUtils.getString(memRow, "cpmfeftchngfirst1"), ExtMemgetIxnUtils.getString(memRow, "cpmfeftchnglast1"),
							ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftchngdt2"), ExtMemgetIxnUtils.getString(memRow, "cpmfeftchngfirst2"),
							ExtMemgetIxnUtils.getString(memRow, "cpmfeftchnglast2"), ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftchngdt3"),
							ExtMemgetIxnUtils.getString(memRow, "cpmfeftchngfirst3"), ExtMemgetIxnUtils.getString(memRow, "cpmfeftchnglast3"),
							ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftchngdt4"), ExtMemgetIxnUtils.getString(memRow, "cpmfeftchngfirst4"),
							ExtMemgetIxnUtils.getString(memRow, "cpmfeftchnglast4"), ExtMemgetIxnUtils.getString(memRow, "cpmfcomment1"),
							ExtMemgetIxnUtils.getString(memRow, "cpmfcomment21"), ExtMemgetIxnUtils.getString(memRow, "cpmfcomment22"),
							ExtMemgetIxnUtils.getString(memRow, "cpmfcomment23"), ExtMemgetIxnUtils.getString(memRow, "cpmfcomment24"),
							ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftrmteffdt"), ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftrmttermdt"),
							ExtMemgetIxnUtils.getString(memRow, "cpmfeftchngfirst1"), ExtMemgetIxnUtils.getString(memRow, "cpmfeftchnglast1"),
							ExtMemgetIxnUtils.getDateAsString(memRow, "cpmfeftchngdt2"), ExtMemgetIxnUtils.getString(memRow, "cpmfeftchngfirst2"),strBlank);
					
					String remitchecknm = ExtMemgetIxnUtils.getString(memRow, "remitchecknm");
					if (remitchecknm.contains("-") || remitchecknm.contains("*")) {
						remitchecknm = remitchecknm.replaceAll("[-\\*]", "");
					}
					String Key = remitchecknm+"-"+ ExtMemgetIxnUtils.getString(memRow, "md5key")+"-"
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
								+ l_memrecno + "-" + ExtMemgetIxnUtils.getString(memRow, "payid");
					remitDetailMap.put(Key, remitDetailSegment);
				}
								break;
			}
		}
		return generateSegmentsforPREM(remitMap, remitDetailMap);
	}
	
	private Set<String> generateSegmentsforPREM(Map<String, String[]> remitMap, Map<String, String[]> remitDetailMap) throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		Set<String> remitKeys = new HashSet<String>(remitMap.keySet());
		for(String Key:remitKeys) {
			String[] remitDetailSegment = remitDetailMap.get(Key);
			if(null == remitDetailMap.get(Key)){
				remitDetailSegment[0] = strDelim;
				remitDetailSegment[1] = ExtMemgetIxnUtils.getNDelimiters(31);
				remitDetailMap.put(Key, remitDetailSegment);
			}
			String[] remitSegment = remitMap.get(Key);
			
			segmentData = remitSegment[0] + remitDetailSegment[0] + remitSegment[1] + remitDetailSegment[1];
			segmentDataSet.add(segmentData);
		}
		return segmentDataSet;
	}

	
}
