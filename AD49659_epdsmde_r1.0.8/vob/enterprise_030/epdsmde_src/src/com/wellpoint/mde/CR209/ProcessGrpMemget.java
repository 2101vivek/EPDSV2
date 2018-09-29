package com.wellpoint.mde.CR209;

import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemRowList;

import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessGrpMemget extends AbstractMemgetIxn{
	
	private GrpGaltHelper grpGaltHelper;
	
	String l_segCodeFilter = "MEMHEAD,MEMATTR,AUDHEAD,EMEMIDENT";
	String l_recStatFilter = "A";

	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {
		getHm_AudRowlist(outAudList);
		getSrcCodes(outMemList);
		if(EPDSV2_UpdatedFlag) {
			initializeOrgV2UpdatedHelper();
			/*grpGaltHelper.v2UpdatedProcessMemrow(outMemList, entRecNum, QCARE_EPDSV2_Flag);*/
		}
		else {
			initializeGeneralHelper();
			grpGaltHelper.SimpleProcessMemrow(outMemList, entRecNum, EPDSV2_UpdatedFlag);
		}
		
		return getOutDataList();
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
	
	private void initializeGeneralHelper() {
		grpGaltHelper = new GrpGaltHelper(hm_AudRow,hm_MemHead,getOutDataList());
		grpGaltHelper.setEPDSV2_Flag(EPDSV2_Flag);
	}

	private void initializeOrgV2UpdatedHelper() {
		
	}

}
