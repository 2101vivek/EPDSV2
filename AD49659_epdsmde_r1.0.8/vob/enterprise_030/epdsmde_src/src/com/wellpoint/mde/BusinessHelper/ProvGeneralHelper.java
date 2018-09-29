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

public class ProvGeneralHelper extends AbstractHelper<ProvEnum>{
	
	ProviderService provProviderService = new ProvServiceImpl();
	
	List<MemAttrRow> ProvPRFMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> ProvPATTSMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvPSPTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvPADRMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvPALTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvPREMMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvAPREMMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvAPSPTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvAPALTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> ProvALTSRCIDMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> ProvAPADRMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> APADRKeyMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> QcareAPADRMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> NascoPCNTCMemAttrList = new ArrayList<MemAttrRow>();
	
	List<String>EMEMADDR_Keys = new ArrayList<String>();
	
	Set<String> ProvNascoPcntcSet = new HashSet<String>();
	
	Set<String> ProvQcareApadrSet = new HashSet<String>();
	
	public ProvGeneralHelper(HashMap<String, String[]> hm_AudRow,HashMap<String, MemHead> hm_MemHead,List<outData> outDataList) {
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
		((AbstractServiceImpl) provProviderService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
		((AbstractServiceImpl) provProviderService).setSchool_name(ExtMemgetIxnUtils.createPropertyForSchoolName());
		((AbstractServiceImpl) provProviderService).setDegree_codes(ExtMemgetIxnUtils.createPropertyForDegreeCodes());
	}
	
	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof EntXeia) {
				generateEIDSegment(memRow, entRecNum);
			}
			if (memRow instanceof MemAttrRow)
			{
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				ProvEnum attrCode = ProvEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				try{
					if (((MemAttrRow)memRow).getInt("rowInd") != 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A"))	{
						generateCompositeSegments(attrCode, memRow, entRecNum);
					}
	
					else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateSourceSegments(attrCode, memRow, entRecNum);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logInfo(entRecNum + e.toString());
				}
			}
		}
		SimpleAPADR_Key_Logic(APADRKeyMemAttrList);
		buildOtherSegments(entRecNum);
	}
	
	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		
		outputType = ProvEnum.PRF.getValue();
		segmentData = provProviderService.buildPRFSegment(ProvPRFMemAttrList, entRecNum , EPDSV2_Flag);
		generateRow();
		
		segmentDataSet = provProviderService.buildAPADRSegment(ProvAPADRMemAttrList, EMEMADDR_Keys, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.APADR.getValue());
		
		segmentDataSet = provProviderService.buildPSPTSegment(ProvPSPTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.PSPT.getValue());
		
		segmentDataSet = provProviderService.buildPADRSegment(ProvPADRMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.PADR.getValue());
		
		segmentDataSet = provProviderService.buildPALTSegment(ProvPALTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.PALT.getValue());
		
		segmentDataSet = provProviderService.buildAPSPTSegment(ProvAPSPTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.APSPT.getValue());
		
		segmentDataSet = provProviderService.buildALTSRCIDSegment(ProvALTSRCIDMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvEnum.ALTSRCID.getValue());
		
		segmentDataSet = provProviderService.buildAPALTSegment(ProvAPALTMemAttrList, entRecNum, APALTFlag);
		generateSegments(segmentDataSet, ProvEnum.APALT.getValue());
		
		//NASCOPCNTC
		generateSegments(ProvNascoPcntcSet, ProvEnum.NASCOPCNTC.getValue());
		
		//QCARE_APADR
		generateSegments(ProvQcareApadrSet, ProvEnum.QCARE_APADR.getValue());
		
	}

	@Override
	protected void generateCompositeSegments(ProvEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		//PRF
		
		case PROVCATEGORY:
		case PROVINACTIVE:
		//case	attrval:
		case PROVNAMEEXT:
		case DOB:
		case GENDER:
		case PRACTYPE:
		case SSN:
		case PROVETHNIC:
		case PRVNPIELIG:
		case PARIND:
		case PRVCACTUSFCL:
		case PRVGBDCD:	ProvPRFMemAttrList.add((MemAttrRow) memRow);
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
		
			//PTTL:
		case PROVTTLSFX:	{
							if(getString(memRow, "suffix").length()>0) {
								outputType = ProvEnum.PTTL.getValue();
								segmentData = provProviderService.getSegmentDataforPTTL(memRow, entRecNum);
								generateRow();
							}
							break;
						}
			
			//PNOTE:
		case PROVNOTE:		outputType = ProvEnum.PNOTE.getValue();
							segmentData = provProviderService.getSegmentDataforPNOTE(memRow, entRecNum);
							generateRow();
							break;
			
			//PEDU:
		case PROVEDUCTN:	outputType = ProvEnum.PEDU.getValue();
							segmentData = provProviderService.getSegmentDataforPEDU(memRow, entRecNum);
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
				
		//PLANG:
		case PROVLANG:		outputType = ProvEnum.PLANG.getValue();
							segmentData = provProviderService.getSegmentDataforPLANG(memRow, entRecNum);
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
								// CFF 2.7 new feild required 
								&& getString(memRow, "rollovertranseqno").length() > 0  /* && getString(memRow, "networkid").length() > 0*/ )	{
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
						break;

		// PGREL:
		case PROVGRPMEM:	if(getString(memRow, "relmemidnum").length() > 0 && getString(memRow, "relmemsrccode").length() > 0 
								&& getString(memRow, "reltype") .length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))){
									outputType = ProvEnum.PGREL.getValue();
									segmentData = provProviderService.getSegmentDataforPGREL(memRow, entRecNum);
									generateRow();
							}
							break;
		//PWTH
		/*case  PROVRSKWTHLD:	if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbeffdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbtrmdt")) 
								&& getString(memRow, "withhldperc").length() > 0 && getString(memRow, "withhldtypecd").length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "withhldeffdt"))) {
									outputType = ProvEnum.PWTH.getValue();
									segmentData = provProviderService.getSegmentDataforPWTH(memRow, entRecNum);
									generateRow();
							}
							break;*/
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
		//PWTH-Begin
		case  PROVRSKWTHLD:	if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbeffdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbtrmdt"))
								&& getString(memRow, "withhldperc").length() > 0 && getString(memRow, "withhldtypecd").length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "withhldeffdt"))) {
									outputType = ProvEnum.PWTH.getValue();
									segmentData = provProviderService.getSegmentDataforPWTH(memRow,entRecNum);
									generateRow();
							}
							break;
		//APREL-Begin
		case PROVREL:	outputType = ProvEnum.APREL.getValue();
						segmentData = provProviderService.getSegmentDataforAPREL(memRow,entRecNum);
						generateRow();
						break;
	
		//APTXN-Begin
		case PROVTXNMY:	outputType = ProvEnum.APTXN.getValue();
						segmentData = provProviderService.getSegmentDataforAPTXN(memRow,entRecNum);
						generateRow();
						break;
		//APGREL-Begin
		case PROVGRPMEM:	{if(getString(memRow, "relmemidnum").length() > 0 && getString(memRow, "relmemsrccode").length() > 0 
								&& getString(memRow, "reltype") .length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))){
									outputType = ProvEnum.APGREL.getValue();
									segmentData = provProviderService.getSegmentDataforAPGREL(memRow,entRecNum);
									generateRow();
							}
							break;
						}
		//APCLMACT
		case PROVCLAIMACT:	outputType = ProvEnum.APCLMACT.getValue();
							segmentData = provProviderService.getSegmentDataforAPCLMACT(memRow,entRecNum);
							generateRow();
							break;
	
			 
		//APCNTC-Begin
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
							if(getEntityProp().get("QCARE").equalsIgnoreCase(memRow.getSrcCode())) {
								outputType = ProvEnum.NASCOPCNTC.getValue();
								segmentData = provProviderService.getSegmentDataforNASCOPCNTC(memRow, entRecNum);
								ProvNascoPcntcSet.add(segmentData);
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
        case PROVEDUCTN: 	ProvALTSRCIDMemAttrList.add((MemAttrRow) memRow);
        					break;

		//APDAR	
		case PROVPADRLGCY:
		case PRVSN:
		case PRVFACHSANET:
		case PRVDIRDISIND: ProvAPADRMemAttrList.add((MemAttrRow) memRow);
							break;
							
		//APTTL-Begin
		case PROVTTLSFX:	ProvALTSRCIDMemAttrList.add((MemAttrRow) memRow);	
							if(ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "suffix"))) {
								outputType = ProvEnum.APTTL.getValue();
								segmentData = provProviderService.getSegmentDataforAPTTL(memRow, entRecNum);
								generateRow();
							}
							break;
	 		
		//APSPT-Begin
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
		
		//QCARE_APADR
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
							if(getEntityProp().get("QCARE").equalsIgnoreCase(memRow.getSrcCode())) {
								outputType = ProvEnum.QCARE_APADR.getValue();
								segmentData = provProviderService.getSegmentDataforQCAREAPADR(memRow,entRecNum);
								ProvQcareApadrSet.add(segmentData);
							}
							break;
		
		}
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
