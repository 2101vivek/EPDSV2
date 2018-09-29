package com.wellpoint.mde.CR209;

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

import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.constants.ProvEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ProcessProvMemget extends AbstractMemgetIxn{
	
	private ProvApaltHelper provApaltHelper;
	
	private ProvApaltE2EHelper provApaltE2EHelper;
	
	String l_segCodeFilter = "MEMHEAD,AUDHEAD,MEMATTR,EMEMIDENT,EMEMIDSPEC,EMEMLICENSE,EMEMNET,EMEMADDR,EMEMATRTIMED,EMEMATRCDTMD";
	String l_recStatFilter = "A";
	
	private static final String IDTERMRSN = "IDTERMRSN";
	private static final String MD5KEY = "MD5KEY";
	private static final String MDS5ADDRTYPE = "MDS5ADDRTYPE";
	private static final String MDS5ADDREFFDT ="mds5addreffectdt";
	
	ArrayList<String> memrecnoList;
	ArrayList<String> legacyIdList;
	MemRowList outMemListforDual;
	HashMap<String,ArrayList<MemRow>> generalMap;
	
	private void addToAlternateIdMap(MemRow memRow, String uniqueKey) throws Exception{
		ArrayList<String> keyValueList = new ArrayList<String>();
		//removed 297 check CQ:WLPRD02003140
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

		String keyValue = ExtMemgetIxnUtils.getString(memRow, MD5KEY)+ HYPHEN + ExtMemgetIxnUtils.getString(memRow, MDS5ADDRTYPE) + HYPHEN + ExtMemgetIxnUtils.getDateAsString(memRow, MDS5ADDREFFDT) ;
		keyValueList = new ArrayList<String>();
		if(QcareAddressMap != null) {
			if(!QcareAddressMap.isEmpty()){
				if(QcareAddressMap.containsKey(uniqueKey)){
					keyValueList = QcareAddressMap.get(uniqueKey);
				}
			}
			keyValueList.add(keyValue);
			QcareAddressMap.put(uniqueKey,keyValueList);	
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
	
	private void performDualSubmitCheck(ArrayList<String> legacyIdList2) throws Exception{
		generalMap = new HashMap<String, ArrayList<MemRow>>();
		for (RowIterator iter = outMemListforDual.rows(); iter.hasMoreRows();) {
			MemRow memRow = (MemRow) iter.nextRow();
			if (memRow instanceof MemAttrRow){
				if(ExtMemgetIxnUtils.getString(memRow, "IDTYPE").length() > 0 
					&& (memRow.getAsString("memRecno").equals(epdsv2memrecno) 
							|| memrecnoList.contains(memRow.getAsString("memRecno")))){
					
					if(ExtMemgetIxnUtils.getString(memRow, "IDTYPE").equalsIgnoreCase("8016")){
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
	
	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {

		getHm_AudRowlist(outAudList);
		getSrcCodes(outMemList);

			if(allSourceCodeSet.contains("EPDSV2")) {
				EPDSV2_UpdatedFlag = true;
				
				if(allSourceCodeSet.contains("QCARE")) {
					Memhead_QCARE_Flag = true;
				}
				/*Turn Off P to A copy for R6[CQ WLPRD02490909 fix]*/
				/*if(allSourceCodeSet.contains("EPDS1")) {
					E2EFlag = true;
				}*/
			}
			
			if (EPDSV2_UpdatedFlag) 
			{
				//Collect srccodes & legacy id's into a map
				collect_srcodes_and_legacyids(outMemList);
				legacyIdList = new ArrayList<String>(); 
				performDualSubmitCheck(legacyIdList);
			}//end of if E2E & QCARE true Flag
			
			/**
			 * Separating all the source codes with pipe
			 */
			setSourceCodesDelimited();
			if(EPDSV2_UpdatedFlag) {
				initializeV2UpdatedE2EHelper();
				provApaltE2EHelper.v2UpdatedProcessMemrow(outMemList, entRecNum);
			}
			else {
				initializeGeneralHelper();
				provApaltHelper.SimpleProcessMemrow(outMemList, entRecNum);
			}
		return getOutDataList();
	}

	@Override
	public void setMemGetProp(IxnMemGet memGet) {
		memGet.setCvwName("ProviderMDE");
		memGet.setSegCodeFilter(l_segCodeFilter);
		memGet.setRecStatFilter(l_recStatFilter);
		memGet.setMemType("PROVIDER");
		memGet.setEntType("provider");
		memGet.setMemStatFilter("A");
	}
	
	private void initializeGeneralHelper() {
		provApaltHelper = new ProvApaltHelper(hm_AudRow,hm_MemHead,getOutDataList());
		provApaltHelper.setSrcCodesDelimited(srcCodesDelimited);
		provApaltHelper.setEPDSV2_Flag(EPDSV2_Flag);
	}

	private void initializeV2UpdatedE2EHelper() {
		provApaltE2EHelper = new ProvApaltE2EHelper(hm_AudRow,hm_MemHead,getOutDataList());
		provApaltE2EHelper.setEPDSV2_Flag(EPDSV2_Flag);
		provApaltE2EHelper.setAllSourceCodeSet(allSourceCodeSet);
		provApaltE2EHelper.setQcareAlternateIdMap(QcareAlternateIdMap);
		provApaltE2EHelper.setEpdsv2memrecno(epdsv2memrecno);
		provApaltE2EHelper.setE2eLeagcyidMap(e2eLeagcyidMap);
		provApaltE2EHelper.setQcareALTSRCIDMap(QcareALTSRCIDMap);
		provApaltE2EHelper.setQcareAddressMap(QcareAddressMap);
	}
	
	public void collect_srcodes_and_legacyids(MemRowList outMemList) throws Exception{
		memrecnoList = new ArrayList<String>();
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
				if (srcodeKey.equalsIgnoreCase("QCARE") || ("ENCLRTY").equalsIgnoreCase(srcodeKey)) {
					String key_memRecNo = new Long(memRow.getMemHead().getMemRecno()).toString();
					memrecnoList.add(key_memRecNo);
				}
			}
			if(memRow instanceof MemAttrRow) {
				if(memRow.getInt("rowInd")==0 
						&& (ProvEnum.PROVALTSYSID.getValue()).equalsIgnoreCase(((MemAttrRow) memRow).getAttrCode())
						&&  ExtMemgetIxnUtils.getString(memRow, "IDISSUER").length() > 0 && ExtMemgetIxnUtils.getString(memRow, "IDISSUER").equals("1945")){
					outMemListforDual.addRow(memRow);
				}
			}
		}
		//for getting memrecno of EPDSv2 to build altsrcid
		ArrayList<String> epdsv2MemrecnoList = e2eLeagcyidMap.get("EPDSV2"); // change  EPDSV2ORG to EPDSV2
		if (null != epdsv2MemrecnoList) {
			epdsv2memrecno = epdsv2MemrecnoList.get(0);
		}
	}

}
