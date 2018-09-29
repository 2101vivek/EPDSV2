//2.5c - ReSync
package com.wellpoint.mde;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;
import madison.mpi.RowIterator;

import com.wellpoint.mde.BusinessHelper.OrgGeneralHelper;
import com.wellpoint.mde.BusinessHelper.OrgV2UpdatedE2EHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.constants.OrgEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;


public class ProcessOrganizationMemget extends AbstractMemgetIxn {

	//private OrgV2UpdatedQcareHelper orgV2UpdatedQcareHelper;
	private OrgGeneralHelper orgGeneralHelper;
	private OrgV2UpdatedE2EHelper orgV2updatedE2EHelper;
	
	private static final String IDTERMRSN = "IDTERMRSN";
	private static final String MD5KEY = "MD5KEY";
	private static final String MDS5ADDRTYPE = "MDS5ADDRTYPE";

	private static final String l_segCodeFilter = "MEMHEAD,AUDHEAD,ENTXEIA,MEMATTR,EMEMATRCDTMD,EMEMATRCODE,EMEMATTST,EMEMATRTIMED," +
	"EMEMPROVNOTE,EMEMCLAIMACT,EMEMSANCTION,EMEMSANCCA,EMEMLANG," +
	"EMEMSPLTYBRD,EMEMSPTYSVC,EMEMSPTYTXNM,EMEMCONTACT,EMEMTAXNMY," +
	"EMEMOFCPTLIM,EMEMSCHED,EMEMOFCTECH,EMEMOFCACC,EMEMOFCSVCRT," +
	"EMEMADDR,EMEMREL,EMEMIDENT,EMEMLICENSE,EMEMREMIT,EMEMCONTRACT," +
	"EMEMGRPREL,EMEMGRPRMB,EMEMRMB,EMEMNET,EMEMACESLGCY,EMEMCPFLGCY," +
	"EMEMCPMFLGCY,EMEMEPDSLGCY,EMEMQCARELGY,EMEMPADRLGCY,"+
	//2.5c additions
	"EMEMROLLID,EMEMREMITDTL,EMEMGRPREL,EMEMGRPNET,EMEMHMOCTRCT,EMEMHMOREL," +
	//Added for POT Converted Code
	"EMEMDBANAME,EMEMRSKWIHLD,EMEMREGION,EMEMORGTYPE,EMEMIDSPEC";
	private static final String l_recStatFilter = "A";

	ArrayList<String> qcareMemrecnoList;
	ArrayList<String> legacyIdList;
	MemRowList outMemListforDual;
	HashMap<String,ArrayList<MemRow>> generalMap;
	
	private void addToAlternateIdMap(MemRow memRow, String uniqueKey) throws Exception{
		ArrayList<String> keyValueList = new ArrayList<String>();
		/* 
		 * removed 297 check CQ:WLPRD02003140
		 * Adding CIE as reason code in case of CIE record else reason code is blank */
		String reasonCode = ExtMemgetIxnUtils.getString(memRow, IDTERMRSN).equals(CIE_CODE)? CIE: EMPTY;
		String combKey = ExtMemgetIxnUtils.getString(memRow, MD5KEY)+ HYPHEN + ExtMemgetIxnUtils.getString(memRow, MDS5ADDRTYPE)+ HYPHEN 
		+ reasonCode;
		
		if(QcareAlternateIdMap != null) {
			if(!QcareAlternateIdMap.isEmpty()){
				if(QcareAlternateIdMap.containsKey(combKey)){
					keyValueList = QcareAlternateIdMap.get(combKey);
				}
			}
			keyValueList.add(uniqueKey);
			QcareAlternateIdMap.put(combKey,keyValueList);	
		}
	}
	
	private void addToAltSrcIdMap(Date effectDate, Date termDate, String termReason, String md5key, String uniqueKey) throws Exception{
		if(null != effectDate && null != termDate){
			String altSrcIdDate = ExtMemgetIxnUtils.getDateAsString(effectDate)+ "-" + ExtMemgetIxnUtils.getDateAsString(termDate) 
			+ "-" + termReason + "-" + md5key
			+ "-" + uniqueKey + "-" + epdsv2memrecno;
			QcareALTSRCIDMap.put(altSrcIdDate,uniqueKey);
		}
	}
	
	private void collect_srcodes_and_legacyids(MemRowList outMemList) throws Exception{
		qcareMemrecnoList = new ArrayList<String>();
		outMemListforDual = new MemRowList();
		for (RowIterator iter = outMemList.rows(); iter.hasMoreRows();) {
			MemRow memRow = (MemRow) iter.nextRow();
			if (memRow instanceof MemHead) {
				//collecting srccode and memidnum for E2E.
				String srcodeKey = memRow.getSrcCode();
				ArrayList<String> memidnumList = new ArrayList<String>();
				if(e2eLeagcyidMap != null) {
					if(!e2eLeagcyidMap.isEmpty()){
						if(e2eLeagcyidMap.containsKey(srcodeKey)){
							memidnumList = e2eLeagcyidMap.get(srcodeKey);
						}
					}
					memidnumList.add(memRow.getMemIdnum());
					e2eLeagcyidMap.put(srcodeKey,memidnumList);	
				}
				//for getting memrecno of Qcare
				if ((OrgEnum.QCAREORG.getValue()).equals(srcodeKey.toUpperCase())) {
					String key_memRecNo = new Long(memRow.getMemHead().getMemRecno()).toString();
					qcareMemrecnoList.add(key_memRecNo);
				}
			}
			
			if(memRow instanceof MemAttrRow) {
				if(memRow.getInt("rowInd")==0 
						&& (OrgEnum.ALTSYSIDSORG.getValue()).equalsIgnoreCase(((MemAttrRow) memRow).getAttrCode())
						&&  ExtMemgetIxnUtils.getString(memRow, "IDISSUER").length() > 0 && ExtMemgetIxnUtils.getString(memRow, "IDISSUER").equals("1945")){
					outMemListforDual.addRow(memRow);
				}
			}
		}
		//for getting memrecno of EPDSv2 to build altsrcid
		ArrayList<String> epdsv2MemrecnoList = e2eLeagcyidMap.get("EPDSV2ORG"); 
		if (null != epdsv2MemrecnoList) {
			epdsv2memrecno = epdsv2MemrecnoList.get(0);
		}
	}
	
	private void performDualSubmitCheck(ArrayList<String> legacyIdList2) throws Exception{
		generalMap = new HashMap<String, ArrayList<MemRow>>();
		for (RowIterator iter = outMemListforDual.rows(); iter.hasMoreRows();) {
			MemRow memRow = (MemRow) iter.nextRow();
			if (memRow instanceof MemAttrRow){
				if(ExtMemgetIxnUtils.getString(memRow, "IDTYPE").length() > 0 
						&& (ExtMemgetIxnUtils.getString(memRow, "IDTYPE").equalsIgnoreCase("8017") 
								|| ExtMemgetIxnUtils.getString(memRow, "IDTYPE").equalsIgnoreCase("8016")
								|| ExtMemgetIxnUtils.getString(memRow, "IDTYPE").equalsIgnoreCase("879"))
								&& (memRow.getAsString("memRecno").equals(epdsv2memrecno) 
										|| qcareMemrecnoList.contains(memRow.getAsString("memRecno")))){

					if(isDualSubmitter()){
						for (String legacyId : legacyIdList2) {
							String subString[] = legacyId.split("/");
							if ((subString[0].equalsIgnoreCase (ExtMemgetIxnUtils.getString(memRow, "idnumber"))
									|| subString[1].equalsIgnoreCase (ExtMemgetIxnUtils.getString(memRow, "idnumber")))
									&& !ExtMemgetIxnUtils.getString(memRow, "IDTYPE").equalsIgnoreCase("879")){
								addToAlternateIdMap(memRow, legacyId);
							}
							else if(ExtMemgetIxnUtils.getString(memRow, "IDTYPE").equalsIgnoreCase("879") &&
									legacyId.equalsIgnoreCase(ExtMemgetIxnUtils.getString(memRow, "idnumber"))){
								addToGeneralMap(legacyId,memRow);
							}
						}
					}
					else if(ExtMemgetIxnUtils.getString(memRow, "IDTYPE").equalsIgnoreCase("8017")){
						String legacyId = ExtMemgetIxnUtils.getString(memRow, "IDNUMBER");
						addToAlternateIdMap(memRow, legacyId);
						addToGeneralMap(legacyId,memRow);
						legacyIdList2.add(legacyId);
					}
				}
			}
		}
		altsrcidForDual(generalMap,legacyIdList2);
	}
	
	private void addToGeneralMap(String string, MemRow memRow) throws Exception {
		ArrayList<MemRow> memRowList = new ArrayList<MemRow>();
		if(generalMap != null) {
			if(!generalMap.isEmpty()){
				if(generalMap.containsKey(string)){
					memRowList = generalMap.get(string);
				}
			}
			memRowList.add(memRow);
			generalMap.put(string,memRowList);	
		}
	}

	private void altsrcidForDual(HashMap<String, ArrayList<MemRow>> altidMemRowMap,
			ArrayList<String> legacyList) throws Exception {
		if(ExtMemgetIxnUtils.isNotEmpty(legacyList) && ExtMemgetIxnUtils.isNotEmpty(altidMemRowMap)){
			for (String legacyid : legacyList) {
				String LegacyId = legacyid;
				ArrayList<MemRow> rowList =  altidMemRowMap.get(LegacyId);
				boolean activePresent = false;
				boolean termedPresent = false;
				Date altEffctDate = null;
				Date altTermDate = null;
				String termReasonCode = null;
				String md5Key = null;
				if(null != rowList){
					for (MemRow memrow : rowList) {
						String reasonCode = ExtMemgetIxnUtils.getString(memrow, "idtermrsn").trim();
						if(activePresent || ExtMemgetIxnUtils.isEmpty(reasonCode)){
							if(ExtMemgetIxnUtils.isEmpty(reasonCode)) {
								if(!activePresent || 
										memrow.getDate("idissuedate").before(altEffctDate)) {
									altEffctDate = memrow.getDate("idissuedate");
								}
								if(!activePresent ||
										memrow.getDate("idexpdate").after(altTermDate)) {
									altTermDate = memrow.getDate("idexpdate");
									termReasonCode = reasonCode;
									md5Key = ExtMemgetIxnUtils.getString(memrow, "MD5KEY");
								}
								activePresent = true;
							}
						}
						else if(termedPresent || (ExtMemgetIxnUtils.isNotEmpty(reasonCode) && !reasonCode.equals("297"))){
							if((ExtMemgetIxnUtils.isNotEmpty(reasonCode) && !reasonCode.equals("297"))) {
								if(!termedPresent || 
										memrow.getDate("idissuedate").before(altEffctDate)) {
									altEffctDate = memrow.getDate("idissuedate");
								}
								if(!termedPresent ||
										memrow.getDate("idexpdate").after(altTermDate)) {
									altTermDate = memrow.getDate("idexpdate");
									termReasonCode = reasonCode;
									md5Key = ExtMemgetIxnUtils.getString(memrow, "MD5KEY");
								}
								termedPresent = true;
							}
						}
						else if(reasonCode.equals("297") && !activePresent && !termedPresent){
							if(null == altEffctDate || 
									memrow.getDate("idissuedate").after(altEffctDate)) { // take the latest CIE records
								altEffctDate = memrow.getDate("idissuedate");
								altTermDate = memrow.getDate("idexpdate");
								termReasonCode = reasonCode;
								md5Key = ExtMemgetIxnUtils.getString(memrow, "MD5KEY");
							}
						}
					}
				}
				addToAltSrcIdMap(altEffctDate, altTermDate, termReasonCode, md5Key, legacyid);
			}
		}
	}


	public List<outData> processMemRows(MemRowList outMemList, AudRowList outAudList, long entRecNum) throws Exception {

		//init();
		//get the values for hm_audRow
		getHm_AudRowlist(outAudList);

		//iteration added for checking PRF required fields
		boolean prfflag=false;
		boolean orgCategory_attrval_flag = false;
		boolean inactiveOrg_attrval_flag = false;
		boolean remitsegOrg_flag =false;
		try{
			for (MemRow memRowforPRF : outMemList.listToArray()) {
				//if 3 required fields are present break and
				//proceed with the segment generation
				if(orgCategory_attrval_flag && inactiveOrg_attrval_flag
						&& remitsegOrg_flag) {
					prfflag = true;
					break;
				}

				if (memRowforPRF instanceof MemHead) {
					allSourceCodeSet.add(memRowforPRF.getSrcCode());
				}				

				if (memRowforPRF instanceof MemAttrRow) {
					String l_strAttrCode = ((MemAttrRow)memRowforPRF).getAttrCode().toUpperCase();
					if (l_strAttrCode.equals(OrgEnum.ORGCATEGORY.getValue())) {
						//null is never returned by getString() 
						//method hence checked with the length
						if(ExtMemgetIxnUtils.getString(memRowforPRF, "attrval").length()>0) {
							orgCategory_attrval_flag = true;
							continue;
						}
						else if(ExtMemgetIxnUtils.getString(memRowforPRF, "attrval").length()==0) {
							prfflag=false;
							break;
						}	
					}

					else if(l_strAttrCode.equals(OrgEnum.INACTIVEORG.getValue())) {
						if(ExtMemgetIxnUtils.getString(memRowforPRF, "attrval").length()>0) {
							inactiveOrg_attrval_flag = true;
							continue;
						}
						else if(ExtMemgetIxnUtils.getString(memRowforPRF, "attrval").length()==0) {
							prfflag=false;
							break;
						}	
					}
					//Fix for SEV1 defect 
					//red rule:reject a provider if there is no remit 
					//address found for an Organisation
					if(l_strAttrCode.equals(OrgEnum.REMITSEG.getValue())) {
						//if REMITSEG is present	
						remitsegOrg_flag = true;
						continue;
					}
					else if(orgCategory_attrval_flag && inactiveOrg_attrval_flag
							&& remitsegOrg_flag) {
						prfflag=true;
					}
				}
			}//End iteration
			if(orgCategory_attrval_flag && inactiveOrg_attrval_flag && remitsegOrg_flag) 
				prfflag=true;
			// PRFFlag is made true for EPDSV2 added records 
			if(allSourceCodeSet.size()==1 && allSourceCodeSet.contains("EPDSV2ORG") && inactiveOrg_attrval_flag
					&& remitsegOrg_flag) {
				prfflag = true;
				allSourceCodeSet.clear();
			}
		}
		catch (Exception ex){
			logInfo("Error getting ORGCATEGORY/INACTIVEORG/REMITSEG-attrval");
			ex.printStackTrace();
		}

		//Generate segments only if prfflag = true
		if(prfflag) {
			
			//Collecting the srcCodes for pre-fixing each segment and hm_MemHead.
			getSrcCodes(outMemList);

			if (allSourceCodeSet.contains("EPDSV2ORG")) {
				EPDSV2_UpdatedFlag = true;
				
				if (allSourceCodeSet.contains("QCAREORG")) {
					Memhead_QCARE_Flag = true;
				}
				
				/*if(allSourceCodeSet.contains("EPDS1ORG")) {
					E2EFlag = true;
				}*/
			}

			if (EPDSV2_UpdatedFlag) {
				
				collect_srcodes_and_legacyids(outMemList);
				legacyIdList = new ArrayList<String>(); 
				
				for (MemRow memRow : outMemList.listToArray()) {
					if (memRow instanceof MemAttrRow) {
						//for Qcare granularity
						String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode().toUpperCase();
						if (((MemAttrRow) memRow).getInt("rowInd") == 0 && (memRow.getAsString("memRecno").equals(epdsv2memrecno) 
								|| qcareMemrecnoList.contains(memRow.getAsString("memRecno")))
								&& l_strAttrCode.equals(OrgEnum.ALTSYSIDSORG.getValue())
								&& ((MemAttrRow) memRow).getString("recStat").equalsIgnoreCase("A")) {
							
							if(ExtMemgetIxnUtils.getString(memRow, "IDTYPE").length() > 0 && ExtMemgetIxnUtils.getString(memRow, "IDTYPE").equals("879")
									&& ExtMemgetIxnUtils.getString(memRow, "IDISSUER").length() > 0 && ExtMemgetIxnUtils.getString(memRow, "IDISSUER").equals("1945")
									&& ExtMemgetIxnUtils.getString(memRow, "IDNUMBER").contains("/")) {
								DualSubmitter = true;
								String legacyId = ExtMemgetIxnUtils.getString(memRow, "IDNUMBER");
								legacyIdList.add(legacyId);
							}
						}//End If Qcare granularity (QCARE_EPDSV2_Flag)
					} //  End if of memRow
				} // end of for
				performDualSubmitCheck(legacyIdList);
			}//end of E2E & QCARE true Flag
			setSourceCodesDelimited();
			/*
			 * Calls to various helper class
			 * */
			if(EPDSV2_UpdatedFlag) {
				initializeOrgV2UpdatedE2EHelper();
				orgV2updatedE2EHelper.v2UpdatedProcessMemrow(outMemList, entRecNum);
			}
			else {
				initializeGeneralHelper();
				orgGeneralHelper.SimpleProcessMemrow(outMemList, entRecNum);
			}
		}//End -if
		return getOutDataList();
	}

	private void initializeGeneralHelper() {
		orgGeneralHelper = new OrgGeneralHelper(hm_AudRow,hm_MemHead,getOutDataList());
		orgGeneralHelper.setSrcCodesDelimited(srcCodesDelimited);
		orgGeneralHelper.setEPDSV2_Flag(EPDSV2_Flag);
	}

	private void initializeOrgV2UpdatedE2EHelper() {
		orgV2updatedE2EHelper = new OrgV2UpdatedE2EHelper(hm_AudRow,hm_MemHead,getOutDataList());
		orgV2updatedE2EHelper.setSrcCodesDelimited(srcCodesDelimited);
		orgV2updatedE2EHelper.setEPDSV2_Flag(EPDSV2_Flag);
		orgV2updatedE2EHelper.setQcareAlternateIdMap(QcareAlternateIdMap);
		orgV2updatedE2EHelper.setEpdsv2memrecno(epdsv2memrecno);
		orgV2updatedE2EHelper.setE2eLeagcyidMap(e2eLeagcyidMap);
		orgV2updatedE2EHelper.setQcareALTSRCIDMap(QcareALTSRCIDMap);
		orgV2updatedE2EHelper.setAllSourceCodeSet(allSourceCodeSet);
		orgV2updatedE2EHelper.setDualSubmitter(DualSubmitter);
	}
	
	@Override
	public void setMemGetProp(IxnMemGet memGet) {
		memGet.setCvwName("ProviderOrgMDE");/*ProviderOrgMDE*/
		memGet.setSegCodeFilter(l_segCodeFilter);
		memGet.setRecStatFilter(l_recStatFilter);
		memGet.setMemType("ORGANIZATION");
		memGet.setEntType("org");
		memGet.setMemStatFilter("A");
	}
}
