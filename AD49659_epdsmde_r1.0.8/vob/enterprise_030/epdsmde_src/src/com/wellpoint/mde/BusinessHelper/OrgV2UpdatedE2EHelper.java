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
import com.wellpoint.mde.constants.OrgEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.ProviderService;
import com.wellpoint.mde.serviceImpl.AbstractServiceImpl;
import com.wellpoint.mde.serviceImpl.OrgServiceImpl;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class OrgV2UpdatedE2EHelper extends AbstractHelper<OrgEnum>{

	ProviderService orgProviderService = new OrgServiceImpl();

	List<MemAttrRow> OrgPRFMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgPSPTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgPADRMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> APADRKeyMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgPALTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgPREMMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgAPREMMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgAPSPTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgAPALTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgALTSRCIDMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgAPADRMemAttrList = new ArrayList<MemAttrRow>();

	List<String>EMEMADDR_Keys = new ArrayList<String>();

	Set<String> segmentDataSet = new HashSet<String>();

	Set<String> segmentDataQcareSet = new HashSet<String>();

	Set<String> segmentDataE2ESet = new HashSet<String>();
	
	Set<String> OrgNascoPcntcSet = new HashSet<String>();
	
	Set<String> OrgQcareApadrSet = new HashSet<String>();

	public OrgV2UpdatedE2EHelper(HashMap<String, String[]> hm_AudRow,HashMap<String, MemHead> hm_MemHead,List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
		setEntityProp(EntityProperties.getOrgProperties());
	}

	private void initialize() {
		
		((AbstractSegment) orgProviderService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) orgProviderService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) orgProviderService).setOutDataList(getOutDataList());
		((AbstractSegment) orgProviderService).setSrcCodesDelimited(srcCodesDelimited);
		((AbstractSegment) orgProviderService).setQcareAlternateIdMap(QcareAlternateIdMap);
		((AbstractSegment) orgProviderService).setE2eLeagcyidMap(e2eLeagcyidMap);
		((AbstractSegment) orgProviderService).setEpdsv2memrecno(epdsv2memrecno);
		((AbstractSegment) orgProviderService).setAllSourceCodeSet(allSourceCodeSet);
		((AbstractServiceImpl) orgProviderService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
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
				OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				srccode = memRow.getSrcCode();

				try{
					//Composite view
					if (((MemAttrRow)memRow).getInt("rowInd") < 3 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateCompositeSegments(attrCode, memRow, entRecNum);
					}
					//A segments for sources other than QCARE,EPDSV2 should generate from RowInd:5 memrows  
					else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
							!getEntityProp().get("QCARE").equalsIgnoreCase(srccode) &&
							!getEntityProp().get("EPDSV2").equalsIgnoreCase(srccode) &&
						//Commented the below check for the CQ WLPRD02490909 fix[Turn off P to A copy for R6] 	
						  /*!getEntityProp().get("EPDS1").equalsIgnoreCase(srccode) && */ 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateSourceSegments(attrCode, memRow, entRecNum);
					}
					//Nasco segments
					else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
							(getEntityProp().get("QCARE").equalsIgnoreCase(srccode) ||
							 getEntityProp().get("EPDSV2").equalsIgnoreCase(srccode))&&
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateSourceNascoSegments(attrCode, memRow, entRecNum);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		v2UpdatedAPADR_Key_Logic(APADRKeyMemAttrList);
		buildOtherSegments(entRecNum);
	}	

	@Override
	protected void generateCompositeSegments(OrgEnum attrCode, MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {

		//PRF
		case ORGCATEGORY:
		case INACTIVEORG:
		case ORGNAME:
		case REMPAYIND:
		case ORGTYPE:
		case ORGNPIELIG:
		case ORGPARIND:
		case ORGCACTUSFCL:	
		//For ALTSRCID for V2
		case ORGTYPCD:
		case ORGACESLGCY:
		case ORGCPFLGCY:
		case ORGCPMFLGCY:
		case ORGEPSBLGCY:
		case ORGQCARELGCY: 
		case ORGGBDCD: OrgPRFMemAttrList.add((MemAttrRow) memRow);
							break;

		//PSPT
		case ORGBRDCRT:
		case SPLTYSVCORG: 
		case SPLTYTXNMORG: 	OrgPSPTMemAttrList.add((MemAttrRow) memRow);
							break;

		//PADR-Begin
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
		case ORGHMORELADR:	//P to A Copy: Generating EPDSV2 source record
							outputType = OrgEnum.APADR.getValue();
							segmentData = orgProviderService.getSegmentDataforAPADR(memRow, entRecNum);		
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,11,12,111,true);
				
							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();
				
								//QCARE granularity
								String[] splitseg =  segmentData.split("~");					
								String comb_key = getCombAddresskey(splitseg[4], splitseg[5],splitseg[15]);
								segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
										11,12,111,"Org"); 
								generateSegments(segmentDataQcareSet, OrgEnum.APADR.getValue());
								
								//P to A Copy: Generating EPDS1 source record
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData,allSourceCodeSet,
										e2eLeagcyidMap,11,12,111,false);
								generateSegments(segmentDataE2ESet, OrgEnum.APADR.getValue());
							}
							
		case ORGFACSTERE:	OrgPADRMemAttrList.add((MemAttrRow) memRow);
							break;

		//PALT
		case ALTSYSIDSORG:
		case NPIORG:
		case UPINORG: 
		case MEDICAREORG: 
		case MEDICAIDORG: 
		case ENCLRTYIDORG: 
		case LICENSEORG: 
		case ORGALTIDSPEC:	OrgPALTMemAttrList.add((MemAttrRow) memRow);
							break;

		//PREM
		case REMITSEG:		outputType = OrgEnum.PREM.getValue();
							segmentData = orgProviderService.getSegmentDataforPREM(memRow, entRecNum);
							generateRow();
							
		case REMITSEGDTL:	OrgPREMMemAttrList.add((MemAttrRow) memRow);
							break;

		//PATTS
		case ORGATTEST:		outputType = OrgEnum.PATTS.getValue();
							segmentData = orgProviderService.getSegmentDataforPATTS(memRow, entRecNum);
							generateRow();
							break;
		
		//PPPRF-Begin (renamed from PPLIM)
		case OFFPTLIMORG: 	outputType = OrgEnum.PPPRF.getValue();
							segmentData = orgProviderService.getSegmentDataforPPPRF(memRow, entRecNum);
							generateRow();
							break;
							
		//POT
		case ORGNTYPE: 		if(getString(memRow, "billformcode").length()>0 
								&& getString(memRow, "specialtycode").length()>0
								&& getString(memRow, "orgtypecode").length()>0){
									outputType = OrgEnum.POT.getValue();
									segmentData = orgProviderService.getSegmentDataforPOT(memRow, entRecNum);
									generateRow();
							}
							break;
							
		//PCRED-Begin
		case ORGCREDSTAT: 	outputType = OrgEnum.PCRED.getValue();
							segmentData = orgProviderService.getSegmentDataforPCRED(memRow, entRecNum) ;
							generateRow();
							break;
							
		//PRNK-Begin
		case ORGRANK: 		outputType = OrgEnum.PRNK.getValue();
							segmentData = orgProviderService.getSegmentDataforPRNK(memRow, entRecNum);
							generateRow();
							break;
							
		//PDSTC-Begin
		case ORGDIST: 		outputType = OrgEnum.PDSTC.getValue();
							segmentData = orgProviderService.getSegmentDataforPDSTC(memRow, entRecNum);	
							generateRow();
							break;
							
		//PCLMFRM-Begin
		case BILLFORM:		outputType = OrgEnum.PCLMFRM.getValue();
							segmentData = orgProviderService.getSegmentDataforPCLMFRM(memRow, entRecNum);
							generateRow();
							break;
							
		//PNOTE-Begin
		case ORGNOTE:		outputType = OrgEnum.PNOTE.getValue();
							segmentData = orgProviderService.getSegmentDataforPNOTE(memRow, entRecNum);
							generateRow();
							break;
							
		//PSANC-Begin
		case SANCTIONORG: 	outputType = OrgEnum.PSANC.getValue();
							segmentData = orgProviderService.getSegmentDataforPSANC(memRow, entRecNum);
							generateRow();
							break;
							
		//PSNAC-Begin
		case ORGSANCCLACT: 	outputType = OrgEnum.PSNAC.getValue();
							segmentData = orgProviderService.getSegmentDataforPSNAC(memRow, entRecNum);
							generateRow();
							break;
							
		//PSTFLANG-Begin
		case OFFSTFLNGORG: 	outputType = OrgEnum.PSTFLANG.getValue();
							segmentData = orgProviderService.getSegmentDataforPSTFLANG(memRow, entRecNum);
							generateRow();
							break;
							
		//2.5b CFF changes
		//PPGM (renamed from PSPLP)-Begin
		case ORGSPECPRG: 	outputType = OrgEnum.PPGM.getValue();
							segmentData = orgProviderService.getSegmentDataforPPGM(memRow, entRecNum);
							generateRow();
							break;
							
		//PAOF-Begin
		//PAOF (renamed from PAOE)
		case ORGEXPRTAREA: 	outputType = OrgEnum.PAOF.getValue();
							segmentData = orgProviderService.getSegmentDataforPAOF(memRow, entRecNum);
							generateRow();
							break;
		/**
		 * CFF 2.5b Changes
		 * Added new segment "POS"
		 */
		//POFSR
		case ORGPRCOFCSVC: 	outputType = OrgEnum.POFSR.getValue();
							segmentData = orgProviderService.getSegmentDataforPOFSR(memRow, entRecNum);
							generateRow();
							break;
		/*POFSRV segment not generating along with POS with 
		 * the else if condition hence added above with POS
		 *
		 */
		//PDBA-Begin
		case ORGDBANAME: if(getString(memRow, "busasname").length()>0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "busnmeffdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "busnmtrmdt"))){
							outputType = OrgEnum.PDBA.getValue();
							segmentData = orgProviderService.getSegmentDataforPDBA(memRow, entRecNum);
							generateRow();
						}
						break;
		/**
		 * 2.4 changes
		 * Added filter condition for required fields:
		 * mds5key,mds5addrtype,mds5addreffectdt
		 * Added field: faxtype
		 * Deletion of 15 fields:
		 * E.g :teleffectdt,teltermdt,teltermrsn...
		 */
		//PCNTC-Begin
		case ORGCONTACT: 
		case SERVCNTCORG: 
		case ORGCORRLOCCT: 
		case REMITCNTCORG:
		case ORGCSACNTCT:
		case ORGPAYINCNTC: 
		case ORGCAPCKCNTC:
		case ORG1099CNTCT:
		case ORGBILLCNTCT:
		case ORGCAPCNTCT:
		case ORGCNTCTNA: //Required fields check
						if(getString(memRow, "md5key").length()>0
								&& getString(memRow, "mds5addrtype").length()>0
								&& ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt").length()>0) {
							outputType = OrgEnum.PCNTC.getValue();
							segmentData = orgProviderService.getSegmentDataforPCNTC(memRow, entRecNum);
							generateRow();

							//P to A Copy: Generating EPDSV2 source record
							outputType = OrgEnum.APCNTC.getValue();
							segmentData = orgProviderService.getSegmentDataforAPCNTC(memRow, entRecNum);
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,27,28,95,true);
					
							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();
					
								//QCARE granularity
								String[] splitseg =  segmentData.split("~");					
								String comb_key = getCombAddresskey(splitseg[4], splitseg[5], splitseg[31]);
								segmentDataSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
										27,28,95,"Org"); 
								generateSegments(segmentDataSet, OrgEnum.APCNTC.getValue());
								
								//P to A Copy: Generating EPDS1 source record
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
										e2eLeagcyidMap, 27,28,95, false);
								generateSegments(segmentDataE2ESet, OrgEnum.APCNTC.getValue());
							}
						}
						break;
		/**
		 * 2.4 changes
		 * Added a field : pallimitvalto
		 * 2.5b changes Added :patlimitvaltypefrm,patlimitvaltypeto
		 */

		//POFSCH-Begin
		case SCHEDORG: 		outputType = OrgEnum.POFSCH.getValue();
							segmentData = orgProviderService.getSegmentDataforPOFSCH(memRow, entRecNum);
							generateRow();
							break;
		//POFTCH-Begin
		case OFFTECHORG: 	outputType = OrgEnum.POFTCH.getValue();
							segmentData = orgProviderService.getSegmentDataforPOFTCH(memRow, entRecNum);
							generateRow();
							break;
		//POFACS-Begin
		case OFFACCORG:		outputType = OrgEnum.POFACS.getValue();
							segmentData = orgProviderService.getSegmentDataforPOFACS(memRow, entRecNum);
							generateRow();
							break;
		//POFSR-Begin
		case OFFRSTRCTSVC: 	outputType = OrgEnum.POFSRR.getValue();
							segmentData = orgProviderService.getSegmentDataforPOFSRR(memRow, entRecNum);
							generateRow();
							break;
		/**
		 * 2.5b changes
		 * 5 blank fields added: No Mappings present in the inbound PREL
		 */
		//PREL-Begin
		case ORGREL:  if(getString(memRow, "relmemidnum").length() > 0
								&& getString(memRow, "reltype").length() > 0
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))) {
							outputType = OrgEnum.PREL.getValue();
							segmentData = orgProviderService.getSegmentDataforPREL(memRow, entRecNum);
							generateRow();
						}
				
						//P to A Copy: Generating EPDSV2 source record
						outputType = OrgEnum.APREL.getValue();
						segmentData = orgProviderService.getSegmentDataforAPREL(memRow,entRecNum);
						if(ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2")){
							generateRow();
						}
				
						//QCARE granularity
						if(ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2") || 
								ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("QCARE")){
							String comb_key = getCombAddresskey(getString(memRow, "md5key"),getString(memRow, "mds5addrtype"),getString(memRow, "termrsn"));
							segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
									15,16,93,"Org");
							//segmentDataQcareSet = processRelmemIdNum(segmentDataQcareSet);
							generateSegments(segmentDataQcareSet, OrgEnum.APREL.getValue());
						}
				
						//P to A Copy: Generating EPDS1 source record
						if(/*ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2") || */
								ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDS1")){
							segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
									e2eLeagcyidMap,15,16,93, false);
							generateSegments(segmentDataE2ESet, OrgEnum.APREL.getValue());
						}
						
						break;
		/**
		 * CFF 2.5 b -New segment added
		 */
		//PALTROL-Begin
		case ORGROLLID: if(getString(memRow, "rolloversrcval").length() > 0
								&& getString(memRow, "rolloversrctype").length() > 0
								&& getString(memRow, "rolloverrecipntsrcval").length() > 0
								&& getString(memRow, "rolloverrecipnttype").length() > 0
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rolloversrcissuedt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rolloverrecipntissuedt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rollovereffectdt"))
								// CFF 2.7 new field required 
								&& getString(memRow, "rollovertranseqno").length() > 0 /*&& getString(memRow, "networkid").length() > 0*/) {
							outputType = OrgEnum.PALTROL.getValue();
							segmentData = orgProviderService.getSegmentDataforPALTROL(memRow, entRecNum);
							generateRow();
						}
						break;
						
		//PRGN start
		/*CFF 2.7 new composite segment PRGN
		 * */
		case ORGREGN: 	if(	getString(memRow, "regiontypecd").length() > 0	&&getString(memRow, "regionid").length() > 0
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "regioneffdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "regiontrmdt"))) { 
							outputType = OrgEnum.PRGN.getValue();
							segmentData = orgProviderService.getSegmentDataforPRGN(memRow, entRecNum);
							generateRow();
						}
						break;
						
		//PTAX-Begin
		case TAXIDORG:	if(getString(memRow, "idnumber").length() > 0 && getString(memRow, "idtype").length() > 0) {
							outputType = OrgEnum.PTAX.getValue();
							segmentData = orgProviderService.getSegmentDataforPTAX(memRow, entRecNum);
							generateRow();
						}
						break;
						
		//PBREL-Begin
		case ORGBUSENTREL:	if( getString(memRow, "relmemidnum").length() > 0 
								&& getString(memRow, "relmemsrccode").length() > 0 
								&& getString(memRow, "reltype").length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))) {
							outputType = OrgEnum.PBREL.getValue();
							segmentData = orgProviderService.getSegmentDataforPBREL(memRow, entRecNum);
							generateRow();
						}
						break;
		/**
		 * 2.5b CFF changes to delete fields:
		 * Provider Program Identifier,Provider Program System Of Record Code
		 */
		//POCON-Begin
		case ORGCNTRCT: 	/**
		 * Provider Program System Of Record Code is required 
		 * but no mapping present
		 */
							if(getString(memRow, "taxid").length() > 0)	{
								outputType = OrgEnum.POCON.getValue();
								segmentData = orgProviderService.getSegmentDataforPOCON(memRow, entRecNum);
								generateRow();
							}
							break;
							
		//PGREL-Begin
		case ORGGRPMEM: // Required fields check
					if(getString(memRow, "relmemidnum").length() > 0 
							&& getString(memRow, "relmemsrccode").length() > 0
							&& getString(memRow, "reltype").length() > 0 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))) {
						outputType = OrgEnum.PGREL.getValue();
						segmentData = orgProviderService.getSegmentDataforPGREL(memRow, entRecNum);
						generateRow();
		
						//P to A Copy: Generating EPDSV2 source record
						outputType = OrgEnum.APGREL.getValue();
						segmentData = orgProviderService.getSegmentDataforAPGREL(memRow,entRecNum);
						String trimmedRelmemSrccode = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE"));
						
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
									6,5,98,"Org");
							generateSegments(segmentDataQcareSet, OrgEnum.APGREL.getValue());
						}
						
						//P to A Copy: Generating EPDS1 source record
						else if(trimmedRelmemSrccode.equalsIgnoreCase("EPDS1") ||
								//CQ fix for the CQ:WLPRD02513995 
								(memRow.getSrcCode().equalsIgnoreCase(getEntityProp().get("EPDS1")) && trimmedRelmemSrccode.equalsIgnoreCase("PI"))){
							
							segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
									e2eLeagcyidMap,6,5,98,false);
							generateSegments(segmentDataE2ESet, OrgEnum.APGREL.getValue());	
						}
					}
					break;
							
		//GRMB-Begin
		case ORGGRPCTRCT: 	/**
		 * EXTRA REQUIRED FEID IN CFF:Reimbursement Arrangement Type System of Record Code no mapping specified
		 */
							if(getString(memRow, "grprmbid").length()>0 && getString(memRow, "rmbarrangetype").length()>0
									&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbeffectdt"))
									&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbtermdt"))) {
								outputType = OrgEnum.GRMB.getValue();
								segmentData = orgProviderService.getSegmentDataforGRMB(memRow, entRecNum);
								generateRow();
							}
							break;
							
			/**
			 * 2.4 changes
			 * Added field 'HMO Legacy ID'
			 */
			//WREL-Begin(Org HMO Site Relationship)
		case ORGWREL: 		if(getString(memRow, "relmemidnum").length()>0 && getString(memRow, "reltype").length()>0
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))) {
							outputType = OrgEnum.WREL.getValue();
							segmentData = orgProviderService.getSegmentDataforWREL(memRow, entRecNum);
							generateRow();
						}
						break;
		/**
		 * New Segment added for 2.5c CFF
		 */
		//GNET-Begin(Org Grouping Network)
		case ORGGRPNET: 	if(getString(memRow, "networkid").length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grpneteffectdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grpnettermdt"))) {
							outputType = OrgEnum.GNET.getValue();
							segmentData = orgProviderService.getSegmentDataforGNET(memRow, entRecNum);
							generateRow();
						}
						break;
						
		//WCON-Begin
		//2.5c Delta Load : WCON Changed to a single attribute segment. 
		case ORGHMOCNTREL:	if(getString(memRow, "hmocntrctcd").length()>0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"hmocntrcttermdt"))) {
							outputType = OrgEnum.WCON.getValue();
							segmentData = orgProviderService.getSegmentDataforWCON(memRow, entRecNum);
							generateRow();
						}
						break;
						
		//PCLMACT				
		case ORGCLAIMACT: 	outputType = OrgEnum.PCLMACT.getValue();
							segmentData = orgProviderService.getSegmentDataforPCLMACT(memRow, entRecNum);
							generateRow();
					
							//P to A Copy: Generating EPDSV2 source record for APCLMACT
							outputType = OrgEnum.APCLMACT.getValue();
							segmentData = orgProviderService.getSegmentDataforAPCLMACT(memRow, entRecNum);
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,12,13,86,true);
					
							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();
					
								//QCARE granularity
								segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,"",
										12,13,86,"Org"); 
								generateSegments(segmentDataQcareSet, OrgEnum.APCLMACT.getValue());
								
								//P to A Copy: Generating EPDS1 source record
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
										e2eLeagcyidMap, 12,13,86, false);
								generateSegments(segmentDataE2ESet, OrgEnum.APCLMACT.getValue());
							}
							break;
							
		//PTXN
		case TAXNMYORG: outputType = OrgEnum.PTXN.getValue();
						segmentData = orgProviderService.getSegmentDataforPTXN(memRow, entRecNum);
						generateRow();
						
						//P to A Copy: Generating EPDSV2 source record for APTXN
						outputType = OrgEnum.APTXN.getValue();
						segmentData = orgProviderService.getSegmentDataforAPTXN(memRow, entRecNum);	
						segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,16,17,39,true);
				
						for (String segmntData : segmentDataSet) {
							segmentData = segmntData;
							generateRow();
				
							//QCARE granularity
							String[] splitseg =  segmentData.split("~");					
							String comb_key = getCombAddresskey(splitseg[4], splitseg[5], splitseg[12]);
							segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
									16,17,39,"Org"); 
							generateSegments(segmentDataQcareSet, OrgEnum.APTXN.getValue());
							
							//P to A Copy: Generating EPDS1 source record
							segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
									e2eLeagcyidMap, 16,17,39, false);
							generateSegments(segmentDataE2ESet, OrgEnum.APTXN.getValue());
						}
						break;
						
		//PRMB
		case ORGRMB: //P to A Copy: Generating EPDSV2 source record   
					outputType = OrgEnum.PRMB.getValue();
					segmentData = orgProviderService.getSegmentDataforPRMB(memRow, entRecNum);
					segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,72,73,145,true);
		
					for (String segmntData : segmentDataSet) {
						segmentData = segmntData;
						generateRow();
		
						//QCARE granularity
						String[] splitseg =  segmentData.split("~");					
						if(splitseg[32].equalsIgnoreCase("QCARERMB")){
							String comb_key = getCombAddresskey(splitseg[4], splitseg[5], splitseg[41]);
							segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
									72,73,145,"Org"); 
							generateSegments(segmentDataQcareSet, OrgEnum.PRMB.getValue());
						}
						
						//P to A Copy: Generating EPDS1 source record
						if(splitseg[32].equalsIgnoreCase("EPDS1RMB")){
							segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
									e2eLeagcyidMap,72,73,145, false);
							generateSegments(segmentDataE2ESet, OrgEnum.PRMB.getValue());
						}
					}
					break;

		//PNET
		case ORGNTWRK:	//Required field check
					if (getString(memRow, "networkid").length() > 0) {
						outputType = OrgEnum.PNET.getValue();
						segmentData = orgProviderService.getSegmentDataforPNET(memRow, entRecNum);
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
										79,80,144,"Org"); 
								generateSegments(segmentDataQcareSet, OrgEnum.PNET.getValue());
							}
							
							//P to A Copy: Generating EPDS1 source record
							if(splitseg[42].equalsIgnoreCase("EPDS1NET")){
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
										e2eLeagcyidMap,79,80,144, false);
								generateSegments(segmentDataE2ESet, OrgEnum.PNET.getValue());
							}
						}
					}
					break;

		//PWTH
		case  ORGRSKWTHLD:	//Required field check
							if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbeffdt")) 	
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbtrmdt")) 	
								&& getString(memRow, "withhldperc").length() > 0 
								&& getString(memRow, "withhldtypecd").length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "withhldeffdt"))) {
				
							outputType = OrgEnum.PWTH.getValue();
							segmentData = orgProviderService.getSegmentDataforPWTH(memRow,entRecNum);
				
							//P to A Copy: Generating EPDSV2 source record	
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,45,46,77,true);
							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();
				
								//QCARE granularity
								String[] splitseg =  segmentData.split("~");					
								String comb_key = getCombAddresskey(splitseg[4], splitseg[5], splitseg[44]);
								segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,comb_key,
										45,46,77,"Org"); 
								generateSegments(segmentDataQcareSet, OrgEnum.PWTH.getValue());
								
								//P to A Copy: Generating EPDS1 source record
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
										e2eLeagcyidMap,45,46,77,false);
								generateSegments(segmentDataE2ESet, OrgEnum.PWTH.getValue());
							}
						}
						break;
		//APDM
		case ORGDATAMANG:
						if (ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "elemdesc")) &&
								ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt")))  {
							outputType = OrgEnum.APDM.getValue();
							segmentData = orgProviderService.getSegmentDataforAPDM(memRow, entRecNum);
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,8,9,76,true);
							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();
			
								//QCARE granularity
								segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,"",
										8,9,76,"Org"); 
								generateSegments(segmentDataQcareSet, OrgEnum.APDM.getValue());
			
								//P to A Copy: Generating EPDS1 source record
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
										e2eLeagcyidMap, 8,9,76, false);
								generateSegments(segmentDataE2ESet, OrgEnum.APDM.getValue());
							}
						}
							break;
					}
		}

	@Override
	protected void generateSourceSegments(OrgEnum attrCode, MemRow memRow, long entRecNum) throws Exception{
		switch(attrCode) {
		//PRMB
		case ORGRMB: 		outputType = OrgEnum.PRMB.getValue();
							segmentData = orgProviderService.getSegmentDataforPRMB(memRow, entRecNum);
							generateRow();
							break;
							
		//PNET					
		case ORGNTWRK: 		if (getString(memRow, "networkid").length() > 0) {
								outputType = OrgEnum.PNET.getValue();
								segmentData = orgProviderService.getSegmentDataforPNET(memRow, entRecNum);
								generateRow();
							}
							break;
							
		//PWTH					
		case  ORGRSKWTHLD:	if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbeffdt")) 	
									&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbtrmdt")) 	
									&& getString(memRow, "withhldperc").length() > 0 
									&& getString(memRow, "withhldtypecd").length() > 0 
									&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "withhldeffdt"))) {
					
								outputType = OrgEnum.PWTH.getValue();
								segmentData = orgProviderService.getSegmentDataforPWTH(memRow,entRecNum);
								generateRow();
							}
							break;
							
		// APREL
		case ORGREL:	outputType = OrgEnum.APREL.getValue();
						segmentData = orgProviderService.getSegmentDataforAPREL(memRow,entRecNum);
						generateRow();
						break;
						
		// APTXN-Begin
		case TAXNMYORG: outputType = OrgEnum.APTXN.getValue();
						segmentData = orgProviderService.getSegmentDataforAPTXN(memRow, entRecNum);
						generateRow();
						break;
						
			// APGREL-Begin
		case ORGGRPMEM: if(getString(memRow, "relmemidnum").length() > 0 
								&& getString(memRow, "relmemsrccode").length() > 0 
								&& getString(memRow, "reltype") .length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))){
							outputType = OrgEnum.APGREL.getValue();
							segmentData = orgProviderService.getSegmentDataforAPGREL(memRow,entRecNum);
							generateRow();
						}
						break;
						
		// APCLMACT
		case ORGCLAIMACT: 	
							outputType = OrgEnum.APCLMACT.getValue();
							segmentData = orgProviderService.getSegmentDataforAPCLMACT(memRow, entRecNum);
							generateRow();
							break;

		// APREM-Begin
		case REMITSEG:
		case REMITSEGDTL: 	OrgAPREMMemAttrList.add((MemAttrRow) memRow);
							break;
							
		// APSPT-Begin
		case ORGBRDCRT:
		case SPLTYTXNMORG: 	OrgAPSPTMemAttrList.add((MemAttrRow) memRow);
							break;
							
		// ALTSRCID-Begin
		case ORGTYPCD:
		case INACTIVEORG:
		case ORGNAME:
		case ORGACESLGCY:
		case ORGCPFLGCY:
		case ORGCPMFLGCY:
		case ORGEPSBLGCY:
		case ORGQCARELGCY: 	OrgALTSRCIDMemAttrList.add((MemAttrRow) memRow);
							break;
			//APDM
		case ORGDATAMANG:
							if (ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "elemdesc")) &&
									 ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt")))  {
									outputType = OrgEnum.APDM.getValue();
									segmentData = orgProviderService.getSegmentDataforAPDM(memRow,entRecNum);
									generateRow();
								}
							break;
			
		// APADR-Begin
		case ORGPADRLGCY: 	
		case ORGFACHSANET: 
		case ORGDIRDISIND: OrgAPADRMemAttrList.add((MemAttrRow) memRow);
							break;
							
		// APALT-Begin
		case ALTSYSIDSORG:
		case NPIORG:
		case UPINORG:
		case MEDICAREORG:
		case MEDICAIDORG:
		case ENCLRTYIDORG:
		case LICENSEORG:
		case ORGALTIDSPEC: 	OrgAPALTMemAttrList.add((MemAttrRow) memRow);
							break;

		//APCNTC-Begin
		case ORGCONTACT: 
		case SERVCNTCORG: 
		case ORGCORRLOCCT: 
		case REMITCNTCORG:
		case ORGCSACNTCT:
		case ORGPAYINCNTC: 
		case ORGCAPCKCNTC:
		case ORG1099CNTCT:
		case ORGBILLCNTCT:
		case ORGCAPCNTCT:
		case ORGCNTCTNA: 
							if(getString(memRow, "md5key").length()>0 && getString(memRow, "mds5addrtype").length()>0
									&& ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt").length()>0) {
								outputType = OrgEnum.APCNTC.getValue();
								segmentData = orgProviderService.getSegmentDataforAPCNTC(memRow, entRecNum);
								generateRow();
							}
							break;

		//APADR
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
		case ORGHMORELADR:
		case ORGFACSTERE:	APADRKeyMemAttrList.add((MemAttrRow) memRow);
							break;
		}
	}		

	private void generateSourceNascoSegments(OrgEnum attrCode,MemRow memRow, long entRecNum) throws Exception{
		//NASCO PCNTC-Begin
		switch(attrCode) {
		case ORGCONTACT: 
		case SERVCNTCORG: 
		case ORGCORRLOCCT: 
		case REMITCNTCORG:
		case ORGCSACNTCT:
		case ORGPAYINCNTC: 
		case ORGCAPCKCNTC:
		case ORG1099CNTCT:
		case ORGBILLCNTCT:
		case ORGCAPCNTCT:
		case ORGCNTCTNA: 	outputType = OrgEnum.NASCOPCNTC.getValue();
							segmentData = orgProviderService.getSegmentDataforNASCOPCNTC(memRow, entRecNum);
							if(ExtMemgetIxnUtils.isNotEmpty(segmentData)) {
								OrgNascoPcntcSet.add(segmentData);
								String[] split_keys = segmentData.split("~");
								if(OrgEnum.EPDSV2.getValue().equalsIgnoreCase(split_keys[8])){
									String comb_key = getCombAddresskey(split_keys[4], split_keys[5], ExtMemgetIxnUtils.getString(memRow, "CNTCTERMRSN"));
									segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,
											comb_key,2,3,8,"Org"); 
									OrgNascoPcntcSet.addAll(segmentDataQcareSet);
								}
							}
							break;

		//QCARE_APADR
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
		case ORGHMORELADR:
		case ORGFACSTERE:	outputType = OrgEnum.QCARE_APADR.getValue();
							segmentData = orgProviderService.getSegmentDataforQCAREAPADR(memRow, entRecNum);
							if(ExtMemgetIxnUtils.isNotEmpty(segmentData)) {
								OrgQcareApadrSet.add(segmentData);
								String[] split_keys = segmentData.split("~");
								if(OrgEnum.EPDSV2.getValue().equalsIgnoreCase(split_keys[11])){
									String comb_key = getCombAddresskey(getString(memRow, "MD5KEY"), getString(memRow, "addrtype"), getString(memRow, "TERMRSN"));
									segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmentData,QcareAlternateIdMap,
											comb_key,2,3,11,"Org"); 
									OrgQcareApadrSet.addAll(segmentDataQcareSet);
								}
							}
							break;
		}
	}

	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();

		//PRF
		outputType = OrgEnum.PRF.getValue();
		segmentData = orgProviderService.buildPRFSegment(OrgPRFMemAttrList, entRecNum , EPDSV2_Flag);
		generateRow();

		//PSPT
		segmentDataSet = orgProviderService.buildPSPTSegment(OrgPSPTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.PSPT.getValue());		

		//PADR
		segmentDataSet = orgProviderService.buildPADRSegment(OrgPADRMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.PADR.getValue());	

		//PALT
		segmentDataSet = orgProviderService.buildPALTSegment(OrgPALTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.PALT.getValue());	

		//APREM
		segmentDataSet = orgProviderService.buildAPREMSegment(OrgAPREMMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.APREM.getValue());	
		
		//APSPT
		segmentDataSet = orgProviderService.buildAPSPTSegment(OrgAPSPTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.APSPT.getValue());	
		
		//ALTSRCID
		segmentDataSet = orgProviderService.buildALTSRCIDSegment(OrgALTSRCIDMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.ALTSRCID.getValue());
		
		//APADR
		segmentDataSet = orgProviderService.buildAPADRSegment(OrgAPADRMemAttrList, EMEMADDR_Keys, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.APADR.getValue());

		//APALT
		segmentDataSet = orgProviderService.buildAPALTSegment(OrgAPALTMemAttrList, entRecNum, APALTFlag);
		generateSegments(segmentDataSet, OrgEnum.APALT.getValue());	
		
		//NASCOPCNTC
		generateSegments(OrgNascoPcntcSet, OrgEnum.NASCOPCNTC.getValue());
		
		//QCARE_APADR
		generateSegments(OrgQcareApadrSet, OrgEnum.QCARE_APADR.getValue());
		
		//P to A Copy: Generating EPDSV2 source record for APREM
		outputType = OrgEnum.APREM.getValue();
		segmentDataSet = orgProviderService.buildAPREMSegment(OrgPREMMemAttrList, entRecNum);
		for (String segmntData : segmentDataSet) {
			segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmntData,null,e2eLeagcyidMap,33,34,144,true);
			for (String segData : segmentDataE2ESet) {
				segmentData = segData ;
				generateRow();
				
				//QCARE granularity
				String[] comb_key_array = segData.split("~");
				String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[37]);
				segmentDataQcareSet = QcaregenerateSourceLevelSegments(segData,QcareAlternateIdMap,
						comb_key,33,34,144,"Org");
				generateSegments(segmentDataQcareSet, OrgEnum.APREM.getValue());
				
				//P to A Copy: Generating EPDS1 source record
				segmentDataQcareSet = E2EgenerateSourceLevelSegments(segmentData,allSourceCodeSet,
						e2eLeagcyidMap,33,34,144,false);
				generateSegments(segmentDataQcareSet, OrgEnum.APREM.getValue());
			}
		}
		
		//P to A Copy: Generating EPDSV2 source record for APSPT
		outputType = OrgEnum.APSPT.getValue();
		segmentDataSet = orgProviderService.buildAPSPTSegment(OrgPSPTMemAttrList, entRecNum);
		for (String segmntData : segmentDataSet) {
			segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmntData,null,e2eLeagcyidMap,13,14,89,true);
			for (String segData : segmentDataE2ESet) {
				segmentData = segData ;
				generateRow();
				
				//QCARE granularity
				String[] comb_key_array = segData.split("~");
				String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[11]);
				segmentDataQcareSet = QcaregenerateSourceLevelSegments(segData,QcareAlternateIdMap,
						comb_key,13,14,89,"Org");
				generateSegments(segmentDataQcareSet, OrgEnum.APSPT.getValue());
				
				//P to A Copy: Generating EPDS1 source record
				segmentDataQcareSet = E2EgenerateSourceLevelSegments(segmentData,allSourceCodeSet,
						e2eLeagcyidMap,13,14,89,false);
				generateSegments(segmentDataQcareSet, OrgEnum.APSPT.getValue());
			}
		}
		
		//P to A Copy: Generating EPDSV2 source record for ALTSRCID
		outputType = OrgEnum.ALTSRCID.getValue();
		segmentDataSet = orgProviderService.buildALTSRCIDSegmentForV2(OrgPRFMemAttrList,epdsv2memrecno ,entRecNum);
		for (String segmntData : segmentDataSet) {
			segmentData  = segmntData;
			generateRow();
			
			//QCARE granularity
			segmentDataQcareSet = QcaregenerateAltsrcIdSegments(segmntData,QcareALTSRCIDMap,
					4,5,206,7,8,9,"Org");
			generateSegments(segmentDataQcareSet, OrgEnum.ALTSRCID.getValue());
			
			//P to A Copy: Generating EPDS1 source record
			segmentDataQcareSet = E2EgenerateSourceLevelSegments(segmntData,allSourceCodeSet,
					e2eLeagcyidMap,4,5,206,false);
			generateSegments(segmentDataQcareSet, OrgEnum.ALTSRCID.getValue());
		}
		
		//P to A Copy: Generating EPDSV2 source record for APALT
		outputType = OrgEnum.APALT.getValue();
		segmentDataSet = orgProviderService.buildAPALTSegment(OrgPALTMemAttrList, entRecNum, APALTFlag);
		for (String segmntData : segmentDataSet) {
			segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmntData,null,e2eLeagcyidMap,13,14,113,true);
			for (String segData : segmentDataE2ESet) {
				segmentData = segData ;
				generateRow();
				
				//QCARE granularity
				String[] comb_key_array = segData.split("~");
				String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5], comb_key_array[12]);
				segmentDataQcareSet = QcaregenerateSourceLevelSegments(segData,QcareAlternateIdMap,
						comb_key,13,14,113,"Org");
				generateSegments(segmentDataQcareSet, OrgEnum.APALT.getValue());
				
				//P to A Copy: Generating EPDS1 source record
				segmentDataQcareSet = E2EgenerateSourceLevelSegments(segmentData,allSourceCodeSet,
						e2eLeagcyidMap,13,14,113,false);
				generateSegments(segmentDataQcareSet, OrgEnum.APALT.getValue());
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

