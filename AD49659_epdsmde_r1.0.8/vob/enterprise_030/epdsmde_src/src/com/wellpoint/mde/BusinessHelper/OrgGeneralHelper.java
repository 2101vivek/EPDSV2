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

public class OrgGeneralHelper extends AbstractHelper<OrgEnum>{
	
	ProviderService orgProviderService = new OrgServiceImpl();
	
	List<MemAttrRow> OrgPRFMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> OrgPSPTMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> OrgPADRMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> OrgPALTMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> OrgAPREMMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> OrgAPSPTMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> OrgAPALTMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> OrgALTSRCIDMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> OrgAPADRMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> APADRKeyMemAttrList = new ArrayList<MemAttrRow>();
	
	List<String>EMEMADDR_Keys = new ArrayList<String>();
	
	Set<String> OrgNascoPcntcSet = new HashSet<String>();
	
	Set<String> OrgQcareApadrSet = new HashSet<String>();
	
	public OrgGeneralHelper(HashMap<String, String[]> hm_AudRow,HashMap<String, MemHead> hm_MemHead,List<outData> outDataList) {
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
		((AbstractServiceImpl) orgProviderService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
	}
	
	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof EntXeia) {
				generateEIDSegment(memRow, entRecNum);
			}
			if(memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
				try{
					//Composite view
					if (((MemAttrRow)memRow).getInt("rowInd") != 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateCompositeSegments(attrCode, memRow, entRecNum);
					}
					else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateSourceSegments(attrCode, memRow, entRecNum);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		SimpleAPADR_Key_Logic(APADRKeyMemAttrList);
		buildOtherSegments(entRecNum);
	}	
	
	@Override
	protected void generateCompositeSegments(OrgEnum attrCode, MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		
		case ORGCATEGORY:
		case INACTIVEORG:
		case ORGNAME:
		case REMPAYIND:
		case ORGTYPE:
		case ORGNPIELIG:
		case ORGPARIND:
		case ORGCACTUSFCL:
		case ORGGBDCD:	OrgPRFMemAttrList.add((MemAttrRow) memRow);
							break;

		case ORGBRDCRT:
		case SPLTYSVCORG: 
		case SPLTYTXNMORG: OrgPSPTMemAttrList.add((MemAttrRow) memRow);
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
		case ORGHMORELADR:
		case ORGFACSTERE:	OrgPADRMemAttrList.add((MemAttrRow) memRow);
							break;
	 					
		case ALTSYSIDSORG:
		case NPIORG:
		case UPINORG: 
		case MEDICAREORG: 
		case MEDICAIDORG: 
		case ENCLRTYIDORG: 
		case LICENSEORG: 
		case ORGALTIDSPEC:	OrgPALTMemAttrList.add((MemAttrRow) memRow);
							break;
		 
		case REMITSEG:
						outputType = OrgEnum.PREM.getValue();
						segmentData = orgProviderService.getSegmentDataforPREM(memRow, entRecNum);
						generateRow();
						break;
			
		case ORGATTEST:	outputType = OrgEnum.PATTS.getValue();
						segmentData = orgProviderService.getSegmentDataforPATTS(memRow, entRecNum);
						generateRow();
						break;
		//PPPRF-Begin (renamed from PPLIM)
		case OFFPTLIMORG: 	outputType = OrgEnum.PPPRF.getValue();
							segmentData = orgProviderService.getSegmentDataforPPPRF(memRow, entRecNum);
							generateRow();
							break;
		case ORGNTYPE: if(getString(memRow, "billformcode").length()>0 
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
		case ORGCNTCTNA: if(getString(memRow, "md5key").length()>0//Added the three required field checks as per 2.4
							&& getString(memRow, "mds5addrtype").length()>0
							&& ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt").length()>0) {
								outputType = OrgEnum.PCNTC.getValue();
								segmentData = orgProviderService.getSegmentDataforPCNTC(memRow, entRecNum);
								generateRow();
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
							// CFF 2.7 new feild required 
							&& getString(memRow, "rollovertranseqno").length() > 0/* && getString(memRow, "networkid").length() > 0*/ ) {
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
		case TAXIDORG:		if(getString(memRow, "idnumber").length() > 0 && getString(memRow, "idtype").length() > 0) {
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
		case ORGGRPMEM: 	if(getString(memRow, "relmemidnum").length() > 0 
								&& getString(memRow, "relmemsrccode").length() > 0
								&& getString(memRow, "reltype").length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))) {
									outputType = OrgEnum.PGREL.getValue();
									segmentData = orgProviderService.getSegmentDataforPGREL(memRow, entRecNum);
									generateRow();
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
		case ORGWREL: 	if(getString(memRow, "relmemidnum").length()>0 && getString(memRow, "reltype").length()>0
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
		case ORGCLAIMACT: 	outputType = OrgEnum.PCLMACT.getValue();
							segmentData = orgProviderService.getSegmentDataforPCLMACT(memRow, entRecNum);
							generateRow();
							break;
		
		case TAXNMYORG: 	outputType = OrgEnum.PTXN.getValue();
							segmentData = orgProviderService.getSegmentDataforPTXN(memRow, entRecNum);
							generateRow();
							break;
		}
		
	}

	@Override
	protected void generateSourceSegments(OrgEnum attrCode, MemRow memRow, long entRecNum) throws Exception{
		switch(attrCode) {
		case ORGRMB: 
					outputType = OrgEnum.PRMB.getValue();
					segmentData = orgProviderService.getSegmentDataforPRMB(memRow, entRecNum);
					generateRow();
					break;
		case ORGNTWRK: 	if (getString(memRow, "networkid").length() > 0) {
							outputType = OrgEnum.PNET.getValue();
							segmentData = orgProviderService.getSegmentDataforPNET(memRow, entRecNum);
							generateRow();
						}
						break;
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
		// APREL-Begin
		case ORGREL:	 {	
							outputType = OrgEnum.APREL.getValue();
							segmentData = orgProviderService.getSegmentDataforAPREL(memRow,entRecNum);
							generateRow();
							break;
							}
		// APTXN-Begin
		case TAXNMYORG: 
						outputType = OrgEnum.APTXN.getValue();
						segmentData = orgProviderService.getSegmentDataforAPTXN(memRow, entRecNum);
						generateRow();
						break;
		// APGREL-Begin
		case ORGGRPMEM: {	if(getString(memRow, "relmemidnum").length() > 0 
								&& getString(memRow, "relmemsrccode").length() > 0 
								&& getString(memRow, "reltype") .length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))){
									outputType = OrgEnum.APGREL.getValue();
									segmentData = orgProviderService.getSegmentDataforAPGREL(memRow,entRecNum);
									generateRow();
							}
						break;
						}
		// APCLMACT-Begin
		case ORGCLAIMACT: 	
							outputType = OrgEnum.APCLMACT.getValue();
							segmentData = orgProviderService.getSegmentDataforAPCLMACT(memRow, entRecNum);
							generateRow();
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
		case ORGCNTCTNA: if(getString(memRow, "md5key").length()>0//Added the three required field checks as per 2.4
							&& getString(memRow, "mds5addrtype").length()>0
							&& ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt").length()>0) {
								outputType = OrgEnum.APCNTC.getValue();
								segmentData = orgProviderService.getSegmentDataforAPCNTC(memRow, entRecNum);
								generateRow();
						}
		
							if(getEntityProp().get("QCARE").equalsIgnoreCase(memRow.getSrcCode())) {
								outputType = OrgEnum.NASCOPCNTC.getValue();
								segmentData = orgProviderService.getSegmentDataforNASCOPCNTC(memRow, entRecNum);
								OrgNascoPcntcSet.add(segmentData);
							}
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
	//APDM
		case ORGDATAMANG:
							 if (ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "elemdesc")) &&
									 ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt")))  {
									outputType = OrgEnum.APDM.getValue();
									segmentData = orgProviderService.getSegmentDataforAPDM(memRow,entRecNum);
									generateRow();
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
		case ORGFACSTERE:	APADRKeyMemAttrList.add((MemAttrRow) memRow);
							if(getEntityProp().get("QCARE").equalsIgnoreCase(memRow.getSrcCode())) {
								outputType = OrgEnum.QCARE_APADR.getValue();
								segmentData = orgProviderService.getSegmentDataforQCAREAPADR(memRow, entRecNum);
								OrgQcareApadrSet.add(segmentData);
							}
							break;
		}
	}

	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		
		outputType = OrgEnum.PRF.getValue();
		segmentData = orgProviderService.buildPRFSegment(OrgPRFMemAttrList, entRecNum , EPDSV2_Flag);
		generateRow();
		
		segmentDataSet = orgProviderService.buildPSPTSegment(OrgPSPTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.PSPT.getValue());		
		
		segmentDataSet = orgProviderService.buildPADRSegment(OrgPADRMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.PADR.getValue());
		
		segmentDataSet = orgProviderService.buildPALTSegment(OrgPALTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.PALT.getValue());
		
		segmentDataSet = orgProviderService.buildAPREMSegment(OrgAPREMMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.APREM.getValue());
		
		segmentDataSet = orgProviderService.buildAPSPTSegment(OrgAPSPTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.APSPT.getValue());
		
		segmentDataSet = orgProviderService.buildALTSRCIDSegment(OrgALTSRCIDMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.ALTSRCID.getValue());
		
		segmentDataSet = orgProviderService.buildAPADRSegment(OrgAPADRMemAttrList, EMEMADDR_Keys, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.APADR.getValue());
		
		segmentDataSet = orgProviderService.buildAPALTSegment(OrgAPALTMemAttrList, entRecNum, APALTFlag);
		generateSegments(segmentDataSet, OrgEnum.APALT.getValue());
		
		generateSegments(OrgNascoPcntcSet, OrgEnum.NASCOPCNTC.getValue());
		
		generateSegments(OrgQcareApadrSet, OrgEnum.QCARE_APADR.getValue());
		
	}

	public void SimpleAPADR_Key_Logic(List<MemAttrRow> NascoAPADRMemAttrList) throws Exception {
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

