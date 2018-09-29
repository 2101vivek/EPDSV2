package com.wellpoint.mde;

import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemRowList;

import com.wellpoint.mde.BusinessHelper.ReportingGrpGeneralHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessReportingGrpMemget extends AbstractMemgetIxn{
	
	String l_segCodeFilter = "MEMHEAD,MEMATTR,AUDHEAD,ENTXEIA,MEMATTR,EMEMATRCDTMD,EMEMREL";
	String l_recStatFilter = "A";
	
	private ReportingGrpGeneralHelper reportingGrpGeneralHelper;

	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {
		getHm_AudRowlist(outAudList);
		getSrcCodes(outMemList);
		setSourceCodesDelimited();
		if(EPDSV2_UpdatedFlag) {
			//to perform v2UpdatedRecords
		}
		else {
			initializeGeneralHelper();
			reportingGrpGeneralHelper.SimpleProcessMemrow(outMemList, entRecNum);
		}
		return getOutDataList();
	}

	private void initializeGeneralHelper() {
		reportingGrpGeneralHelper = new ReportingGrpGeneralHelper(hm_AudRow, hm_MemHead, getOutDataList());
		reportingGrpGeneralHelper.setSrcCodesDelimited(getSrcCodesDelimited());
		reportingGrpGeneralHelper.setEPDSV2_Flag(isEPDSV2_Flag());
	}
	
	@Override
	public void setMemGetProp(IxnMemGet memGet) {
		memGet.setCvwName("ReportingGroupingMDE");
		memGet.setSegCodeFilter(l_segCodeFilter);
		memGet.setRecStatFilter(l_recStatFilter);
		memGet.setMemType("REPORTINGGROUPING");
		memGet.setEntType("rptgrp");
		memGet.setMemStatFilter("A");
	}

}
