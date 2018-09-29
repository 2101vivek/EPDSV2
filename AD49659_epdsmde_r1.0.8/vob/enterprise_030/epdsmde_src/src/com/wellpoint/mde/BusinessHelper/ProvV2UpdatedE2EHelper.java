package com.wellpoint.mde.BusinessHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import madison.mpi.EntXeia;
import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;
import com.wellpoint.mde.constants.ProvEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.ProviderService;
import com.wellpoint.mde.serviceImpl.AbstractServiceImpl;
import com.wellpoint.mde.serviceImpl.ProvServiceImpl;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ProvV2UpdatedE2EHelper extends AbstractHelper<ProvEnum>{
	
	ProviderService provProviderService = new ProvServiceImpl();
	
	List<MemAttrRow> ProvPRFMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> ProvPATTSMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvPSPTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvPADRMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvPALTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvAPSPTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvAPALTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvALTSRCIDMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvAPADRMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> APADRKeyMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> E2EProvPRFMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> E2EProvPALTMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> E2EProvPSPTMemAttrList = new ArrayList<MemAttrRow>();
	
	Set<String> segmentDataSet = new HashSet<String>();

	Set<String> segmentDataQcareSet = new HashSet<String>();

	Set<String> segmentDataE2ESet = new HashSet<String>();
	
	List<String>EMEMADDR_Keys = new ArrayList<String>();
	
	Set<String> ProvNascoPcntcSet = new HashSet<String>();
	
	Set<String> ProvQcareApadrSet = new HashSet<String>();
	
	public ProvV2UpdatedE2EHelper(HashMap<String, String[]> hm_AudRow,HashMap<String, MemHead> hm_MemHead,List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
		setEntityProp(EntityProperties.getProvProperties());
	}
	
	private void initialize() {
		((AbstractSegment) provProviderService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) provProviderService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) provProviderService).setOutDataList(getOutDataList());
		((AbstractSegment) provProviderService).setSrcCodesDelimited(srcCodesDelimited);
		((AbstractSegment) provProviderService).setQcareAlternateIdMap(QcareAlternateIdMap);
		((AbstractSegment) provProviderService).setE2eLeagcyidMap(e2eLeagcyidMap);
		((AbstractSegment) provProviderService).setEpdsv2memrecno(epdsv2memrecno);
		((AbstractSegment) provProviderService).setAllSourceCodeSet(allSourceCodeSet);
		((AbstractServiceImpl) provProviderService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
		((AbstractServiceImpl) provProviderService).setSchool_name(ExtMemgetIxnUtils.createPropertyForSchoolName());
		((AbstractServiceImpl) provProviderService).setDegree_codes(ExtMemgetIxnUtils.createPropertyForDegreeCodes());
	}
	
	public void v2UpdatedProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		String srccode = "";
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof EntXeia) {
				generateEIDSegment(memRow, entRecNum);
			}
			if(memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				ProvEnum attrCode = ProvEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				srccode = memRow.getSrcCode();

				try{
					if(((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")){
						
						//Composite view with previous ECO merge rules prior to GA
						if (((MemAttrRow)memRow).getInt("rowInd") < 3) {
							generateCompositeSegments(attrCode, memRow, entRecNum);
						}
						//Composite view with new ECO with tier1 merge rules
						else if (((MemAttrRow)memRow).getInt("rowInd") == 3) {
							generateRetiredSourceSegments(attrCode, memRow, entRecNum);
						}
						//A segments for sources other than QCARE,EPDSV2 should generate from RowInd:5 memrows  
						else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
								!getEntityProp().get("QCARE").equalsIgnoreCase(srccode) &&
								!getEntityProp().get("EPDSV2").equalsIgnoreCase(srccode)
								//Commented the below check for the CQ WLPRD02490909 fix[Turn off P to A copy for R6]
								/*&& !getEntityProp().get("EPDS1").equalsIgnoreCase(srccode)*/) {
							generateSourceSegments(attrCode, memRow, entRecNum);
						}
						//Nasco segments
						else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
								(getEntityProp().get("QCARE").equalsIgnoreCase(srccode) ||
										getEntityProp().get("EPDSV2").equalsIgnoreCase(srccode))) {
							generateSourceNascoSegments(attrCode, memRow, entRecNum);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		v2UpdatedAPADR_Key_Logic(APADRKeyMemAttrList);
		buildOtherSegments(entRecNum);
	}
	
	private void generateRetiredSourceSegments(ProvEnum attrCode,MemRow memRow, 
			long entRecNum)throws Exception {

		switch(attrCode) {
		//E2E ALTSRCID
		case PROVTYPCD:
		case PROVINACTIVE:
		case PROVNAMEEXT:
		case PROVACESLGCY:
		case PROVCPFLGCY:
		case PROVCPMFLGCY:
		case PROVEPSBLGCY:
		case PROVQCARELGY:
		/*case PROVEDUCTN:
		case PROVTTLSFX:*/	E2EProvPRFMemAttrList.add((MemAttrRow) memRow);
							break;
		
		//E2E APSPT:
		case PROVBRDCRT:
		case PROVSPLTYSVC:
		case PROVSPTYTXNM:	E2EProvPSPTMemAttrList.add((MemAttrRow) memRow);
							break;
							
		//E2E APALT:
		case PROVALTSYSID:
		case NPI:
		case UPIN:
		case DEAID:
		case MEDICARE:
		case MEDICAID:
		case PROVLICENSE:
		case ENCLARITYID:
		case PRVALTIDSPEC: E2EProvPALTMemAttrList.add((MemAttrRow) memRow);
							break;
	   //E2E APDM:	
		case INDDATAMANG:	
							if (ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "elemdesc")) &&
									ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt")))  {
								outputType = ProvEnum.APDM.getValue();
								segmentData = provProviderService.getSegmentDataforAPDM(memRow, entRecNum);
								segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,8,9,76,true);
				
								for (String segmntData : segmentDataSet) {
									segmentData = segmntData;
									generateRow();
				
									//QCARE granularity
									segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,"",
											8,9,76,"Prov"); 
									generateSegments(segmentDataQcareSet, ProvEnum.APDM.getValue());
				
									//P to A Copy: Generating EPDS1 source record
									segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
											e2eLeagcyidMap, 8,9,76, false);
									generateSegments(segmentDataE2ESet, ProvEnum.APDM.getValue());
								}
							}
							break;
			
							
		//E2E APADR:
		case CORRLOCATION:
		case SERVLOCATION:
		case PROV1099ADDR:
		case PROVBILLADDR:
		case PROVCAPADDR:
		case PROVCSAADDR:
		case PROVADDRNA:
		case PRVCAPCKADDR:
		case PRVPAYINADDR:
		case PRVHMORELADR: 	//P to A Copy: Generating EPDSV2 source record
							outputType = ProvEnum.APADR.getValue();
							segmentData = provProviderService.getSegmentDataforAPADR(memRow, entRecNum);
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,11,12,111,true);
					
							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();
								
								//QCARE granularity
								String[] splitseg =  segmentData.split("~");					
								String comb_key = getCombAddresskey(splitseg[4], splitseg[5], splitseg[15]);
								segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
										11,12,111,"Prov"); 
								generateSegments(segmentDataQcareSet, ProvEnum.APADR.getValue());
								
								//P to A Copy: Generating EPDS1 source record
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData,allSourceCodeSet,
										e2eLeagcyidMap,11,12,111,false);
								generateSegments(segmentDataE2ESet, ProvEnum.APADR.getValue());
							}
							break;
							
		//E2E APCLMACT:
		case PROVCLAIMACT:	//P to A Copy: Generating EPDSV2 source record
							outputType = ProvEnum.APCLMACT.getValue();
							segmentData = provProviderService.getSegmentDataforAPCLMACT(memRow, entRecNum);
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,12,13,86,true);
					
							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();
					
								//QCARE granularity
								segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,"",
										12,13,86,"Prov"); 
								generateSegments(segmentDataQcareSet, ProvEnum.APCLMACT.getValue());
							
								//P to A Copy: Generating EPDS1 source record
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
										e2eLeagcyidMap, 12,13,86, false);
								generateSegments(segmentDataE2ESet, ProvEnum.APCLMACT.getValue());
							}
							break;
				
		//E2E APTXN:
		case PROVTXNMY:		//P to A Copy: Generating EPDSV2 source record
							outputType = ProvEnum.APTXN.getValue();
							segmentData = provProviderService.getSegmentDataforAPTXN(memRow, entRecNum);	
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,16,17,39,true);
					
							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();
					
								//QCARE granularity
								String[] splitseg =  segmentData.split("~");					
								String comb_key = getCombAddresskey(splitseg[4], splitseg[5], splitseg[12]);
								segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
										16,17,39,"Prov"); 
								generateSegments(segmentDataQcareSet, ProvEnum.APTXN.getValue());
								
								//P to A Copy: Generating EPDS1 source record
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
										e2eLeagcyidMap, 16,17,39, false);
								generateSegments(segmentDataE2ESet, ProvEnum.APTXN.getValue());
							}
							break;
			
		//E2E APCNTC:
		case SERVCONTACT:
		case CORRCONTACT:
		case PROVCSACNT:
		case PRVPAYINCNTC:
		case PRVCAPCKCNTC:
		case PROVCONTACT:
		case PROV1099CNT:
		case PROVBILLCNT:
		case PROVCAPCNT:
		case PROVCNTNA:		//Required fields check
							if(getString(memRow, "md5key").length() > 0 
								&& getString(memRow, "mds5addrtype").length() > 0
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"))){
									outputType = ProvEnum.APCNTC.getValue();
									segmentData = provProviderService.getSegmentDataforAPCNTC(memRow, entRecNum);
									segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,27,28,95,true);
							
									//P to A Copy: Generating EPDSV2 source record
									for (String segmntData : segmentDataSet) {
										segmentData = segmntData;
										generateRow();
							
										//QCARE granularity
										String[] splitseg =  segmentData.split("~");					
										String comb_key = getCombAddresskey(splitseg[4], splitseg[5], splitseg[31]);
										segmentDataSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
												27,28,95,"Prov"); 
										generateSegments(segmentDataSet, ProvEnum.APCNTC.getValue());
										
										//P to A Copy: Generating EPDS1 source record
										segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
												e2eLeagcyidMap, 27,28,95, false);
										generateSegments(segmentDataE2ESet, ProvEnum.APCNTC.getValue());
									}
							}
							break;
				
		//PEDU:
		case PROVEDUCTN:	outputType = ProvEnum.PEDU.getValue();
							segmentData = provProviderService.getSegmentDataforPEDU(memRow, entRecNum);
							generateRow();
							break;
							
		//PTTL:
		case PROVTTLSFX:	if(getString(memRow, "suffix").length()>0) {
								outputType = ProvEnum.PTTL.getValue();
								segmentData = provProviderService.getSegmentDataforPTTL(memRow, entRecNum);
								generateRow();
							}
							//APTTL WLPRD02162887  fix
		                    //P to A Copy: Generating EPDSV2 source record
							outputType = ProvEnum.APTTL.getValue();
							segmentData = provProviderService.getSegmentDataforAPTTL(memRow, entRecNum);
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,5,6,73,true);
					
							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();
					
								//QCARE granularity
								segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,"",
										5,6,73,"Prov"); 
								generateSegments(segmentDataQcareSet, ProvEnum.APTTL.getValue());
							
								//P to A Copy: Generating EPDS1 source record
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
										e2eLeagcyidMap,5,6,73, false);
								generateSegments(segmentDataE2ESet, ProvEnum.APTTL.getValue());
							}
							break;
							
		//PLANG:
		case PROVLANG:		outputType = ProvEnum.PLANG.getValue();
							segmentData = provProviderService.getSegmentDataforPLANG(memRow, entRecNum);
							generateRow();
							break;					
		}
	
	}

	private void generateSourceNascoSegments(ProvEnum attrCode,MemRow memRow, long entRecNum) throws Exception {
		//NASCO PCNTC-Begin
		switch(attrCode) {
		case SERVCONTACT:
		case CORRCONTACT:
		case PROVCSACNT:
		case PRVPAYINCNTC:
		case PRVCAPCKCNTC:
		case PROVCONTACT:
		case PROV1099CNT:
		case PROVBILLCNT:
		case PROVCAPCNT:
		case PROVCNTNA: 	outputType = ProvEnum.NASCOPCNTC.getValue();
							segmentData = provProviderService.getSegmentDataforNASCOPCNTC(memRow, entRecNum);
							if(ExtMemgetIxnUtils.isNotEmpty(segmentData)) {
								ProvNascoPcntcSet.add(segmentData);
								String[] split_keys = segmentData.split("~");
								if(ProvEnum.EPDSV2.getValue().equalsIgnoreCase(split_keys[8])){
									String comb_key = getCombAddresskey(split_keys[4], split_keys[5], getString(memRow, "CNTCTERMRSN"));
									segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,
											comb_key,2,3,8,"Prov"); 
									ProvNascoPcntcSet.addAll(segmentDataQcareSet);
								}
							}
							break;

		//QCARE_APADR
		case CORRLOCATION:
		case SERVLOCATION:
		case PROV1099ADDR:
		case PROVBILLADDR:
		case PROVCAPADDR:
		case PROVCSAADDR:
		case PROVADDRNA:
		case PRVCAPCKADDR:
		case PRVPAYINADDR:
		case PRVHMORELADR:	outputType = ProvEnum.QCARE_APADR.getValue();
							segmentData = provProviderService.getSegmentDataforQCAREAPADR(memRow, entRecNum);
							if(ExtMemgetIxnUtils.isNotEmpty(segmentData)) {
								ProvQcareApadrSet.add(segmentData);
								String[] split_keys = segmentData.split("~",-1);
								if(ProvEnum.EPDSV2.getValue().equalsIgnoreCase(split_keys[11])){
									String comb_key = getCombAddresskey(split_keys[4], split_keys[5], getString(memRow, "TERMRSN"));
									segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,
											comb_key,2,3,11,"Prov"); 
									ProvQcareApadrSet.addAll(segmentDataQcareSet);
								}
							}
							break;
		}
	}
	@Override
	protected void generateCompositeSegments(ProvEnum attrCode,MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		//PRF
		case PROVCATEGORY:
		case PROVINACTIVE:
		case PROVNAMEEXT:
		case DOB:
		case GENDER:
		case PRACTYPE:
		case SSN:
		case PROVETHNIC:
		case PRVNPIELIG:
		case PARIND:
		case PRVCACTUSFCL: 	
		case PRVGBDCD: ProvPRFMemAttrList.add((MemAttrRow) memRow);
							break;
		
		//PSPT:
		case PROVBRDCRT:
		case PROVSPLTYSVC:
		case PROVSPTYTXNM:	ProvPSPTMemAttrList.add((MemAttrRow) memRow);
							break;
		//PADR:
		case CORRLOCATION:
		case SERVLOCATION:
		case PROV1099ADDR:
		case PROVBILLADDR:
		case PROVCAPADDR:
		case PROVCSAADDR:
		case PROVADDRNA:
		case PRVCAPCKADDR:
		case PRVPAYINADDR:
		case PRVHMORELADR:
		case PRVFACSTERE: ProvPADRMemAttrList.add((MemAttrRow) memRow);
							break;
			
		//PALT:
		case PROVALTSYSID:
		case NPI:
		case UPIN:
		case DEAID:
		case MEDICARE:
		case MEDICAID:
		case ENCLARITYID:
		case PROVLICENSE:
		case PRVALTIDSPEC:	ProvPALTMemAttrList.add((MemAttrRow) memRow);
							break;
		
		//PATTS:
		case PROVATTEST:	outputType = ProvEnum.PATTS.getValue();
							segmentData = provProviderService.getSegmentDataforPATTS(memRow, entRecNum);
							generateRow();
							break;
			
			//PCRED:
		case CREDSTATUS:	outputType = ProvEnum.PCRED.getValue();
							segmentData = provProviderService.getSegmentDataforPCRED(memRow, entRecNum);
							generateRow();
							break;
			
			//PALIAS:
		case PROVALIAS:		outputType = ProvEnum.PALIAS.getValue();
							segmentData = provProviderService.getSegmentDataforPALIAS(memRow, entRecNum);
							generateRow();
							break;
		
		//PNOTE:
		case PROVNOTE:		outputType = ProvEnum.PNOTE.getValue();
							segmentData = provProviderService.getSegmentDataforPNOTE(memRow, entRecNum);
							generateRow();
							break;
			
		//PCLMACT:
		case PROVCLAIMACT:	outputType = ProvEnum.PCLMACT.getValue();
							segmentData = provProviderService.getSegmentDataforPCLMACT(memRow, entRecNum);
							generateRow();
							break;
				
        //PSANC:
		case PROVASANCTN:	outputType = ProvEnum.PSANC.getValue();
							segmentData = provProviderService.getSegmentDataforPSANC(memRow, entRecNum);
							generateRow();
							break;
				
		//PSNAC:
		case PRVSANCCLACT:	outputType = ProvEnum.PSNAC.getValue();
							segmentData = provProviderService.getSegmentDataforPSNAC(memRow, entRecNum);
							generateRow();
							break;
				
		//PSTFLANG:
		case OFFSTAFFLANG:	outputType = ProvEnum.PSTFLANG.getValue();
							segmentData = provProviderService.getSegmentDataforPSTFLANG(memRow, entRecNum);
							generateRow();
							break;
				
		//PPGM:
		case PROVSPECPRG:	outputType = ProvEnum.PPGM.getValue();
							segmentData = provProviderService.getSegmentDataforPPGM(memRow, entRecNum);
							generateRow();
							break;

		//PDSTC:
		case PROVDIST:		outputType = ProvEnum.PDSTC.getValue();
							segmentData = provProviderService.getSegmentDataforPDSTC(memRow, entRecNum);
							generateRow();
							break;
				
		//PRNK:
		case PROVRANK:		outputType = ProvEnum.PRNK.getValue();
							segmentData = provProviderService.getSegmentDataforPRNK(memRow, entRecNum);
							generateRow();
							break;
			
		//POFSR:
		case PROVPRCSVC:	outputType = ProvEnum.POFSR.getValue();
							segmentData = provProviderService.getSegmentDataforPOFSR(memRow, entRecNum);
							generateRow();
							break;
			
		//PAOF:
		case PROVEXPTAREA:	outputType = ProvEnum.PAOF.getValue();
							segmentData = provProviderService.getSegmentDataforPAOF(memRow, entRecNum);
							generateRow();
							break;
		
		//PTXN:
		case PROVTXNMY:		outputType = ProvEnum.PTXN.getValue();
							segmentData = provProviderService.getSegmentDataforPTXN(memRow, entRecNum);
							generateRow();
							break;
			
		//PPPRF:
		case OFFPATLIM:		outputType = ProvEnum.PPPRF.getValue();
							segmentData = provProviderService.getSegmentDataforPPPRF(memRow, entRecNum);
							generateRow();
							break;

		//POFSCH:
		case PROVSCHED:		outputType = ProvEnum.POFSCH.getValue();
							segmentData = provProviderService.getSegmentDataforPOFSCH(memRow, entRecNum);
							generateRow();
							break;

		//POFTCH:
		case OFFTECH:		outputType = ProvEnum.POFTCH.getValue();
							segmentData = provProviderService.getSegmentDataforPOFTCH(memRow, entRecNum);
							generateRow();
							break;

		//POFACS:
		case OFFACCESS:		outputType = ProvEnum.POFACS.getValue();
							segmentData = provProviderService.getSegmentDataforPOFACS(memRow, entRecNum);
							generateRow();
							break;
		
		//PCNTC:
		case SERVCONTACT:
		case CORRCONTACT:
		case PROVCSACNT:
		case PRVPAYINCNTC:
		case PRVCAPCKCNTC:
		case PROVCONTACT:
		case PROV1099CNT:
		case PROVBILLCNT:
		case PROVCAPCNT:
		case PROVCNTNA:		if(getString(memRow, "md5key").length() > 0 
								&& getString(memRow, "mds5addrtype").length() > 0
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt"))){
									outputType = ProvEnum.PCNTC.getValue();
									segmentData = provProviderService.getSegmentDataforPCNTC(memRow, entRecNum);
									generateRow();
							}
							break;
		//PDBA:
		case PROVDBANAME:	if(getString(memRow, "busasname").length()>0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "busnmeffdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "busnmtrmdt"))){
								outputType = ProvEnum.PDBA.getValue();
								segmentData = provProviderService.getSegmentDataforPDBA(memRow, entRecNum);
								generateRow();
							}
							break;
			
		//PALTROL:
		case PROVROLLID:	if(getString(memRow, "rolloversrcval").length() > 0
								&&getString(memRow, "rolloversrctype").length() > 0
								&&getString(memRow, "rolloverrecipntsrcval").length() > 0
								&&getString(memRow, "rolloverrecipnttype").length() > 0
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rolloversrcissuedt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rolloverrecipntissuedt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rollovereffectdt"))
								// CFF 2.7 new field required 
								&& getString(memRow, "rollovertranseqno").length() > 0 /*&& getString(memRow, "networkid").length() > 0*/)	{
									outputType = ProvEnum.PALTROL.getValue();
									segmentData = provProviderService.getSegmentDataforPALTROL(memRow, entRecNum);
									generateRow();
							}
							break;

		//PRGN:
		case PROVREGN:		if(	getString(memRow, "regiontypecd").length() > 0	&&getString(memRow, "regionid").length() > 0
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "regioneffdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "regiontrmdt"))) {
									outputType = ProvEnum.PRGN.getValue();
									segmentData = provProviderService.getSegmentDataforPRGN(memRow, entRecNum);
									generateRow();
							}
							break;
		// PBREL:
		case PRVBUSENTREL:	if(getString(memRow, "relmemidnum").length() > 0 
								&& getString(memRow, "relmemsrccode").length() > 0 
								&& getString(memRow, "reltype").length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))) {
									outputType = ProvEnum.PBREL.getValue();
									segmentData = provProviderService.getSegmentDataforPBREL(memRow, entRecNum);
									generateRow();
							}
							break;
				
		//GRMB:
		case PROVGRPCTRCT:	if( getString(memRow, "grprmbid")  .length() > 0 && getString(memRow, "rmbarrangetype") .length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbeffectdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbtermdt"))){
									outputType = ProvEnum.GRMB.getValue();
									segmentData = provProviderService.getSegmentDataforGRMB(memRow, entRecNum);
									generateRow();
							}
							break;

		//WREL:
		case PROVWREL:	if(getString(memRow, "relmemidnum") .length() > 0  && getString(memRow, "reltype") .length() > 0  
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))){
								outputType = ProvEnum.WREL.getValue();
								segmentData = provProviderService.getSegmentDataforWREL(memRow, entRecNum);
								generateRow();
						}
						break;
		//GNET:
		case PRVGRPNET:	if(getString(memRow, "networkid").length() > 0 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grpneteffectdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grpnettermdt"))){
								outputType = ProvEnum.GNET.getValue();
								segmentData = provProviderService.getSegmentDataforGNET(memRow, entRecNum);
								generateRow();
						}
						break;
		//WCON:
		case PRVHMOCNTREL:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "hmocntrctcd")) &&
								ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt")) &&
								ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt"))) {
									outputType = ProvEnum.WCON.getValue();
									segmentData = provProviderService.getSegmentDataforWCON(memRow, entRecNum);
									generateRow();
							}
							break;
							
		//WNET:
		case PROVWNET:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "hmocntrctcd")) &&
								ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt")) &&
								ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt"))) {
									outputType = ProvEnum.WNET.getValue();
									segmentData = provProviderService.getSegmentDataforWNET(memRow, entRecNum);
									generateRow();
							}
							break;
				
		//PREL:
		case PROVREL:	if(getString(memRow, "relmemidnum").length() > 0 && getString(memRow, "reltype") .length() > 0
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))){
								outputType = ProvEnum.PREL.getValue();
								segmentData = provProviderService.getSegmentDataforPREL(memRow, entRecNum);
								generateRow();
						}
		//E2E APREL:
						//P to A Copy: Generating EPDSV2 source record
						outputType = ProvEnum.APREL.getValue();
						segmentData = provProviderService.getSegmentDataforAPREL(memRow,entRecNum);
						if(ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2")){
							generateRow();
						}
				
						//QCARE granularity
						if(ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2") || 
								ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("QCARE")){
							String comb_key = getCombAddresskey(getString(memRow, "md5key"),getString(memRow, "mds5addrtype"), getString(memRow, "termrsn"));
							segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
									15,16,93,"Prov");
							generateSegments(segmentDataQcareSet, ProvEnum.APREL.getValue());
						}	
						
						//P to A Copy: Generating EPDS1 source record
						//Fixed as part of CQ:WLPRD01725314
						if(/*ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2") ||*/
							ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDS1")){
							segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
									e2eLeagcyidMap,15,16,93, false);
							generateSegments(segmentDataE2ESet, ProvEnum.APREL.getValue());	
						}
						break;

		// PGREL:
		case PROVGRPMEM:	if(getString(memRow, "relmemidnum").length() > 0 && getString(memRow, "relmemsrccode").length() > 0 
								&& getString(memRow, "reltype") .length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))){
									outputType = ProvEnum.PGREL.getValue();
									segmentData = provProviderService.getSegmentDataforPGREL(memRow, entRecNum);
									generateRow();
									
		//E2E APGREL:
									outputType = ProvEnum.APGREL.getValue();
									segmentData = provProviderService.getSegmentDataforAPGREL(memRow,entRecNum);
									String trimmedRelmemSrccode = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE"));
									
									//P to A Copy: Generating EPDSV2 source record
									if(trimmedRelmemSrccode.equalsIgnoreCase("EPDSV2") ||
											//CQ fix for the CQ:WLPRD02513995 
											(memRow.getSrcCode().equalsIgnoreCase(getEntityProp().get("EPDSV2")) && trimmedRelmemSrccode.equalsIgnoreCase("PI"))){
										
										generateRow();
									}
									
									//QCARE granularity
									//reverted for April 1st release -- uncommented for April 15th release
									if(trimmedRelmemSrccode.equalsIgnoreCase("EPDSV2") || trimmedRelmemSrccode.equalsIgnoreCase("QCARE") ||
											//CQ fix for the CQ:WLPRD02513995 
											((memRow.getSrcCode().equalsIgnoreCase(getEntityProp().get("EPDSV2")) || memRow.getSrcCode().equalsIgnoreCase(getEntityProp().get("QCARE")))
													&& trimmedRelmemSrccode.equalsIgnoreCase("PI"))){
										
										String comb_key = getCombAddresskey(getString(memRow, "md5key"),getString(memRow, "mds5addrtype"),getString(memRow, "termrsn"));
										segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
												6,5,98,"Prov");
										generateSegments(segmentDataQcareSet, ProvEnum.APGREL.getValue());
									}
									
									//P to A Copy: Generating EPDS1 source record
									else if(trimmedRelmemSrccode.equalsIgnoreCase("EPDS1") ||
											//CQ fix for the CQ:WLPRD02513995 
											(memRow.getSrcCode().equalsIgnoreCase(getEntityProp().get("EPDS1")) && trimmedRelmemSrccode.equalsIgnoreCase("PI"))){
										
										segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
												e2eLeagcyidMap,6,5,98,false);
										generateSegments(segmentDataE2ESet, ProvEnum.APGREL.getValue());	
									}
							}
							break;
		
		//E2E PRMB					
		case PROVRMB: //P to A Copy: Generating EPDSV2 source record
					outputType = ProvEnum.PRMB.getValue();
					segmentData = provProviderService.getSegmentDataforPRMB(memRow, entRecNum);
					segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,72,73,145,true);
		
					for (String segmntData : segmentDataSet) {
						segmentData = segmntData;
						generateRow();
		
						//QCARE granularity
						String[] splitseg =  segmentData.split("~");					
						if(splitseg[32].equalsIgnoreCase("QCARERMB")){
							String comb_key = getCombAddresskey(splitseg[4], splitseg[5], splitseg[41]);
							segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
									72,73,145,"Prov"); 
							generateSegments(segmentDataQcareSet, ProvEnum.PRMB.getValue());
						}
						
						//P to A Copy: Generating EPDS1 source record
						if(splitseg[32].equalsIgnoreCase("EPDS1RMB")){
							segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
									e2eLeagcyidMap,72,73,145, false);
							generateSegments(segmentDataE2ESet, ProvEnum.PRMB.getValue());
						}
					}
					break;
			
		//E2E PNET
		case PROVNTWRK:	//Required field check
					if (getString(memRow, "networkid").length() > 0) {
					outputType = ProvEnum.PNET.getValue();
					segmentData = provProviderService.getSegmentDataforPNET(memRow, entRecNum);		
					segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,79,80,144,true);
		
					//P to A Copy: Generating EPDSV2 source record
					for (String segmntData : segmentDataSet) {
						segmentData = segmntData;
						generateRow();
		
						//QCARE granularity
						String[] splitseg =  segmentData.split("~");					
						if(splitseg[42].equalsIgnoreCase("QCARENET")|| 
								(splitseg[42].equalsIgnoreCase("WMSNET") && splitseg[43].equalsIgnoreCase("UPPOGA00"))){
							String comb_key = getCombAddresskey(splitseg[4], splitseg[5], splitseg[47]);
							segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
									79,80,144,"Prov"); 
							generateSegments(segmentDataQcareSet, ProvEnum.PNET.getValue());
						}
						
						//P to A Copy: Generating EPDS1 source record
						if(splitseg[42].equalsIgnoreCase("EPDS1NET")){
							segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
									e2eLeagcyidMap,79,80,144, false);
							generateSegments(segmentDataE2ESet, ProvEnum.PNET.getValue());
						}
					}
				}
				break;
				
		//E2E PWTH
		case  PROVRSKWTHLD: //Required field check
						if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbeffdt")) 	
						&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbtrmdt")) 	
						&& getString(memRow, "withhldperc").length() > 0 
						&& getString(memRow, "withhldtypecd").length() > 0 
						&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "withhldeffdt"))) {
		
					outputType = ProvEnum.PWTH.getValue();
					segmentData = provProviderService.getSegmentDataforPWTH(memRow,entRecNum);
		
					//P to A Copy: Generating EPDSV2 source record	
					segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,45,46,77,true);
					for (String segmntData : segmentDataSet) {
						segmentData = segmntData;
						generateRow();
		
						//QCARE granularity
						String[] splitseg =  segmentData.split("~");					
						String comb_key = getCombAddresskey(splitseg[4], splitseg[5],splitseg[44]);
						segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
								45,46,77,"Prov"); 
						generateSegments(segmentDataQcareSet, ProvEnum.PWTH.getValue());
						
						//P to A Copy: Generating EPDS1 source record
						segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
								e2eLeagcyidMap,45,46,77,false);
						generateSegments(segmentDataE2ESet, ProvEnum.PWTH.getValue());
						}
					}
					break;
		}
	}

	@Override
	protected void generateSourceSegments(ProvEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		//PRMB
		case PROVRMB:	outputType = ProvEnum.PRMB.getValue();
						segmentData = provProviderService.getSegmentDataforPRMB(memRow,entRecNum);
						generateRow();
						break;
		//PNET
		case PROVNTWRK: if (getString(memRow, "networkid").length() > 0) {
							outputType = ProvEnum.PNET.getValue();
							segmentData = provProviderService.getSegmentDataforPNET(memRow,entRecNum);
							generateRow();
						}
						break;
		//PWTH
		case  PROVRSKWTHLD:	if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbeffdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbtrmdt"))
								&& getString(memRow, "withhldperc").length() > 0 && getString(memRow, "withhldtypecd").length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "withhldeffdt"))) {
									outputType = ProvEnum.PWTH.getValue();
									segmentData = provProviderService.getSegmentDataforPWTH(memRow,entRecNum);
									generateRow();
							}
							break;
		//APREL
		case PROVREL:	outputType = ProvEnum.APREL.getValue();
						segmentData = provProviderService.getSegmentDataforAPREL(memRow,entRecNum);
						generateRow();
						break;
	
		//APTXN
		case PROVTXNMY:	outputType = ProvEnum.APTXN.getValue();
						segmentData = provProviderService.getSegmentDataforAPTXN(memRow,entRecNum);
						generateRow();
						break;
		//APGREL
		case PROVGRPMEM:
						if(getString(memRow, "relmemidnum").length() > 0 && getString(memRow, "relmemsrccode").length() > 0 
							&& getString(memRow, "reltype") .length() > 0 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))){
						outputType = ProvEnum.APGREL.getValue();
						segmentData = provProviderService.getSegmentDataforAPGREL(memRow,entRecNum);
						generateRow();
						}
						break;
							
		//APCLMACT
		case PROVCLAIMACT:	outputType = ProvEnum.APCLMACT.getValue();
							segmentData = provProviderService.getSegmentDataforAPCLMACT(memRow,entRecNum);
							generateRow();
							break;
	
			 
		//APCNTC
		case SERVCONTACT:
		case CORRCONTACT:
		case PROVCSACNT:
		case PRVPAYINCNTC:
		case PRVCAPCKCNTC:
		case PROVCONTACT:
		case PROV1099CNT:
		case PROVBILLCNT:
		case PROVCAPCNT:
		case PROVCNTNA:		if(getString(memRow, "md5key").length()>0//Added the three required field checks as per 2.4
								&& getString(memRow, "mds5addrtype").length()>0
								&& ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt").length()>0) {
									outputType = ProvEnum.APCNTC.getValue();
									segmentData = provProviderService.getSegmentDataforAPCNTC(memRow,entRecNum);
									generateRow();
							}
							break;
		//ALTSRCID
		case PROVTYPCD:
		case PROVINACTIVE:
		case PROVNAMEEXT:
		case PROVACESLGCY:
		case PROVCPFLGCY:
		case PROVCPMFLGCY:
		case PROVEPSBLGCY:
		case PROVQCARELGY:
		case PROVEDUCTN:	ProvALTSRCIDMemAttrList.add((MemAttrRow) memRow);
							break;
		
		//APDAR	
		case PROVPADRLGCY:
		case PRVSN:
		case PRVFACHSANET:
		case PRVDIRDISIND : ProvAPADRMemAttrList.add((MemAttrRow) memRow);
							break;
		
		//APTTL-Begin
		case PROVTTLSFX:  	ProvALTSRCIDMemAttrList.add((MemAttrRow) memRow);	
						  	if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "suffix"))) {
						  		outputType = ProvEnum.APTTL.getValue();
						  		segmentData = provProviderService.getSegmentDataforAPTTL(memRow, entRecNum);
						  		generateRow();
						  	}
						  	break;
	 		
		//APSPT
		case PROVBRDCRT:
		case PROVSPTYTXNM:	ProvAPSPTMemAttrList.add((MemAttrRow) memRow);
							break;
		//APALT
		case PROVALTSYSID:
		case NPI:
		case UPIN:
		case DEAID:
		case MEDICARE:
		case MEDICAID:
		case ENCLARITYID:
		case PROVLICENSE:
		case PRVALTIDSPEC:	ProvAPALTMemAttrList.add((MemAttrRow) memRow);
							break;
		//APDM	
		case INDDATAMANG:
							if (ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "elemdesc")) &&
									 ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt")))  {
									outputType = ProvEnum.APDM.getValue();
									segmentData = provProviderService.getSegmentDataforAPDM(memRow,entRecNum);
									generateRow();
								}
							break;	
			
		//APADR key list
		case SERVLOCATION :
		case CORRLOCATION :
		case PROV1099ADDR :
		case PROVBILLADDR :
		case PROVCAPADDR :
		case PROVCSAADDR :
		case PROVADDRNA	:
		case PRVCAPCKADDR:
		case PRVPAYINADDR:
		case PRVHMORELADR: APADRKeyMemAttrList.add((MemAttrRow) memRow);
		}
	}
	
	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		
		//PRF
		outputType = ProvEnum.PRF.getValue();
		segmentData = provProviderService.buildPRFSegment(ProvPRFMemAttrList, entRecNum , EPDSV2_Flag);
		generateRow();
		
		//PSPT
		segmentDataSet = provProviderService.buildPSPTSegment(ProvPSPTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.PSPT.getValue());
		
		//PADR
		segmentDataSet = provProviderService.buildPADRSegment(ProvPADRMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.PADR.getValue());
		
		//PALT
		segmentDataSet = provProviderService.buildPALTSegment(ProvPALTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.PALT.getValue());
		
		//APADR
		segmentDataSet = provProviderService.buildAPADRSegment(ProvAPADRMemAttrList, EMEMADDR_Keys, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.APADR.getValue());
		
		//APSPT
		segmentDataSet = provProviderService.buildAPSPTSegment(ProvAPSPTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.APSPT.getValue());
		
		//APALT
		segmentDataSet = provProviderService.buildAPALTSegment(ProvAPALTMemAttrList, entRecNum, APALTFlag);
		generateSegments(segmentDataSet, ProvEnum.APALT.getValue());
		
		//ALTSRCID
		segmentDataSet = provProviderService.buildALTSRCIDSegment(ProvALTSRCIDMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.ALTSRCID.getValue());
		
		//NASCOPCNTC
		generateSegments(ProvNascoPcntcSet, ProvEnum.NASCOPCNTC.getValue());
		
		//QCARE_APADR
		generateSegments(ProvQcareApadrSet, ProvEnum.QCARE_APADR.getValue());
		
		//P to A Copy: Generating EPDSV2 source record for APSPT
		outputType = ProvEnum.APSPT.getValue();
		segmentDataSet = provProviderService.buildAPSPTSegment(E2EProvPSPTMemAttrList, entRecNum);
		for (String segmntData : segmentDataSet) {
			segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmntData,null,e2eLeagcyidMap,13,14,89,true);
			for (String segData : segmentDataE2ESet) {
				segmentData = segData ;
				generateRow();
				
				//QCARE granularity
				String[] comb_key_array = segData.split("~");
				String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5], comb_key_array[11]);
				segmentDataQcareSet = QcaregenerateSourceLevelSegments(segData,QcareAlternateIdMap,
						comb_key,13,14,89,"Prov");
				generateSegments(segmentDataQcareSet, ProvEnum.APSPT.getValue());
				
				//P to A Copy: Generating EPDS1 source record
				segmentDataQcareSet = E2EgenerateSourceLevelSegments(segmentData,allSourceCodeSet,
						e2eLeagcyidMap,13,14,89,false);
				generateSegments(segmentDataQcareSet, ProvEnum.APSPT.getValue());
			}
		}
		
		//P to A Copy: Generating EPDSV2 source record for ALTSRCID
		outputType = ProvEnum.ALTSRCID.getValue();
		segmentDataSet = provProviderService.buildALTSRCIDSegmentForV2(E2EProvPRFMemAttrList,epdsv2memrecno ,entRecNum);
		for (String segmntData : segmentDataSet) {
			segmentData  = segmntData;
			generateRow();
			
			//QCARE granularity
			segmentDataQcareSet = QcaregenerateAltsrcIdSegments(segmntData,QcareALTSRCIDMap,
					4,5,206,7,8,9,"Prov");
			generateSegments(segmentDataQcareSet, ProvEnum.ALTSRCID.getValue());
			
			//P to A Copy: Generating EPDS1 source record
			segmentDataQcareSet = E2EgenerateSourceLevelSegments(segmntData,allSourceCodeSet,
					e2eLeagcyidMap,4,5,206,false);
			generateSegments(segmentDataQcareSet, ProvEnum.ALTSRCID.getValue());
		}
		
		//P to A Copy: Generating EPDSV2 source record for APALT
		APALTFlag = true;
		outputType = ProvEnum.APALT.getValue();
		segmentDataSet = provProviderService.buildAPALTSegment(E2EProvPALTMemAttrList, entRecNum, APALTFlag);
		for (String segmntData : segmentDataSet) {
			segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmntData,null,e2eLeagcyidMap,13,14,113,true);
			for (String segData : segmentDataE2ESet) {
				
				boolean Epds1AltIdFlag = false;
				boolean NpiFlag = false;
				boolean qcareAltIdFlag = false;
				String[] comb_key_array = segData.split("~");
				
				String segment_Data = ExtMemgetIxnUtils.join(comb_key_array, "~", 0, 114);
				segmentData = segment_Data;
				generateRow();
				
				/*For PCP IDs & License numbers - When V2 creates the extract in MDE, 
				 * V2 will send license/PCP IDs for corresponding source systems 
				 * extracted to A segments not all licenses/PCP IDs for the Individual, 
				 * if there are more than 1.*/
				
				MemHead temp_memHead = (MemHead)hm_MemHead.get(comb_key_array[114]);
				if (null != temp_memHead){
					l_strSrcCd = temp_memHead.getSrcCode();
					l_memIdNum = temp_memHead.getMemIdnum();
				}

				if ((comb_key_array[7].equalsIgnoreCase("1945") || comb_key_array[7].equalsIgnoreCase("903"))
						&& (comb_key_array[9].equalsIgnoreCase("8016") || comb_key_array[9].equalsIgnoreCase("8023")
								|| comb_key_array[9].equalsIgnoreCase("8006") || comb_key_array[9].equalsIgnoreCase("879"))) {
					qcareAltIdFlag = true;
				}
				if(comb_key_array[7].equalsIgnoreCase("8005") && 
						(comb_key_array[9].equalsIgnoreCase("8006") || comb_key_array[9].equalsIgnoreCase("8023")
								|| comb_key_array[9].equalsIgnoreCase("8016") || comb_key_array[9].equalsIgnoreCase("879"))){
					Epds1AltIdFlag = true;
				}
				if(comb_key_array[9].equals("8014") || comb_key_array[9].equals("8015") || comb_key_array[9].equals("33")) {
					NpiFlag = true;
				}
				//EPDSV2 UI added PCP IDs & License numbers should be delivered to all R6 legacy id's available 
				if(l_strSrcCd.equalsIgnoreCase("EPDSV2")){
					if(!Epds1AltIdFlag){
						String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[17]);
						if(NpiFlag){
							segmentDataQcareSet = QcaregenerateSourceLevelSegmentsApalt(segment_Data,QcareAlternateIdMap, QcareALTSRCIDMap,
									comb_key,13,14,113,"Prov");
						}else {
						segmentDataQcareSet = QcaregenerateSourceLevelSegments(segment_Data,QcareAlternateIdMap,
								comb_key,13,14,113,"Prov");
						}
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
					}
					if(!qcareAltIdFlag){
						segmentDataQcareSet = E2EgenerateSourceLevelSegments(segment_Data,allSourceCodeSet,
								e2eLeagcyidMap,13,14,113,false);
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
					}
				}
				//PCP IDs & License (legacy) id of other source system's should not delivered to R6 
				else if(comb_key_array[9].equalsIgnoreCase("8023") || comb_key_array[9].equalsIgnoreCase("8006")
						||comb_key_array[9].equalsIgnoreCase("8016") || comb_key_array[9].equalsIgnoreCase("879") ){
					//Commented the below changes for CQ WLPRD02490909 fix[Turn off P to A copy for R6]
					/*if(comb_key_array[7].equalsIgnoreCase("8005")){
						String[] splitseg =  segment_Data.split("~");
						splitseg[13] = getEntityProp().get("EPDS1");
						splitseg[14] = l_memIdNum;
						splitseg[113] = "EPDS1";
						segmentData = ExtMemgetIxnUtils.join(splitseg, "~");
						generateRow();
					}
					else */if (comb_key_array[7].equalsIgnoreCase("1945") || comb_key_array[7].equalsIgnoreCase("903")){
						String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[17]);
						if(NpiFlag){
							segmentDataQcareSet = QcaregenerateSourceLevelSegmentsApalt(segment_Data,QcareAlternateIdMap, QcareALTSRCIDMap,
									comb_key,13,14,113,"Prov");
						}else {
						segmentDataQcareSet = QcaregenerateSourceLevelSegments(segment_Data,QcareAlternateIdMap,
								comb_key,13,14,113,"Prov");
						}
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
					}
					else if (!comb_key_array[7].equalsIgnoreCase("8005") && comb_key_array[9].equalsIgnoreCase("879")){
						//QCARE granularity
						String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[17]);
						segmentDataQcareSet = QcaregenerateSourceLevelSegments(segment_Data,QcareAlternateIdMap,
								comb_key,13,14,113,"Prov");
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
						
						//P to A Copy: Generating EPDS1 source record
						segmentDataQcareSet = E2EgenerateSourceLevelSegments(segment_Data,allSourceCodeSet,
								e2eLeagcyidMap,13,14,113,false);
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
						
					}
				}
				else if(!comb_key_array[9].equalsIgnoreCase("8006") && !comb_key_array[9].equalsIgnoreCase("8023")
						&& !comb_key_array[9].equalsIgnoreCase("8016") && !comb_key_array[9].equalsIgnoreCase("879")){
					String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[17]);
					if(NpiFlag){
						segmentDataQcareSet = QcaregenerateSourceLevelSegmentsApalt(segment_Data,QcareAlternateIdMap, QcareALTSRCIDMap,
								comb_key,13,14,113,"Prov");
					}else {
					segmentDataQcareSet = QcaregenerateSourceLevelSegments(segment_Data,QcareAlternateIdMap,
							comb_key,13,14,113,"Prov");
					}
					generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());

					segmentDataQcareSet = E2EgenerateSourceLevelSegments(segment_Data,allSourceCodeSet,
							e2eLeagcyidMap,13,14,113,false);
					generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
				}
			}
		}
	}
	
	public void v2UpdatedAPADR_Key_Logic(List<MemAttrRow> NascoAPADRMemAttrList) throws Exception {
		for (MemRow memRow : NascoAPADRMemAttrList) {
			String comb_Key;
			if(getString(memRow, "addrtype").length() > 0 
				&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) 
				&& getString(memRow, "MD5KEY").length() > 0) {		
				
					comb_Key = getString(memRow, "MD5KEY")+ "-" + 
								getString(memRow, "addrtype")+ "-" + 
								ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ "-" +								
								ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+"-" + 
								getString(memRow, "termrsn")+"-"+ 
								getString(memRow, "primaryaddress") +"-" +
								new Long(memRow.getMemRecno()).toString();
					
					EMEMADDR_Keys.add(comb_Key);
			}
		}	
	}
}
