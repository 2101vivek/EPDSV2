package com.wellpoint.mde;

import java.util.ArrayList;
import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.BusinessHelper.GrpGeneralHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessGroupingMemget extends AbstractMemgetIxn{

	String l_segCodeFilter = "MEMHEAD,MEMATTR,AUDHEAD,ENTXEIA,EMEMATRCODE," +
	"EMEMCTRCTREF,EMEMATRCDTMD,EMEMSVCEMP,EMEMIDENT,EMEMGRPRMB,EMEMATRTIMED,EMEMGRPNET,EMEMATRTIMED";
	
	String l_recStatFilter = "A";

	private GrpGeneralHelper grpGeneralHelper;

	private void addToAlternateIdMap(MemRow memRow, String uniqueKey) throws Exception{
		ArrayList<String> keyValueList = new ArrayList<String>();
		String combKey = strBlank;
		
		if(QcareAlternateIdMap != null) {
			if(!QcareAlternateIdMap.isEmpty()){
				keyValueList = QcareAlternateIdMap.get(combKey);
			}
			keyValueList.add(uniqueKey);
			QcareAlternateIdMap.put(combKey,keyValueList);	
		}
	}
	
	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {
		getHm_AudRowlist(outAudList);
		getSrcCodes(outMemList);
		setSourceCodesDelimited();
		for (MemRow memRow : outMemList.listToArray()) {
			try{
				if (memRow instanceof MemAttrRow && allSourceCodeSet.contains("EPDSV2GRP")) {
					String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
					if(((MemAttrRow)memRow).getInt("rowInd") == 0 && 
							l_strAttrCode.equalsIgnoreCase("GRPALTID")){
						if (memRow.getAsString("IDNUMBER").length() > 0){
							String legacyId = getString(memRow, "IDNUMBER");
							addToAlternateIdMap(memRow, legacyId);
						}
					}
				}
			} catch (Exception e) {
				logInfo("String getter exception - " );
				e.printStackTrace();
			}
		}
		if(EPDSV2_UpdatedFlag/* && QcareAlternateIdMap.size()>0*/) {
			
		}
		else {
			initializeGeneralHelper();
			grpGeneralHelper.SimpleProcessMemrow(outMemList, entRecNum);
		}
		return getOutDataList();
	}

	private void initializeGeneralHelper() {
		grpGeneralHelper = new GrpGeneralHelper(hm_AudRow,hm_MemHead,getOutDataList());
		grpGeneralHelper.setSrcCodesDelimited(srcCodesDelimited);
		grpGeneralHelper.setEPDSV2_Flag(EPDSV2_Flag);
		grpGeneralHelper.setQcareAlternateIdMap(QcareAlternateIdMap);
	}
	
	@Override
	public void setMemGetProp(IxnMemGet memGet) {
		memGet.setCvwName("GroupingMDE");
		memGet.setSegCodeFilter(l_segCodeFilter);
		memGet.setRecStatFilter(l_recStatFilter);
		memGet.setMemType("GROUPING");
		memGet.setEntType("GROUPING");
		memGet.setMemStatFilter("A");
	}

}
