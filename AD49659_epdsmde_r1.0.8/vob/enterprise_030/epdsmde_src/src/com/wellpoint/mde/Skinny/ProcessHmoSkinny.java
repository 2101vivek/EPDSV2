package com.wellpoint.mde.Skinny;

import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemRowList;

import com.wellpoint.mde.Skinny.BusinessHelper.HmoSkinnyHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessHmoSkinny extends AbstractMemgetIxn{
	
	String l_segCodeFilter = "MEMHEAD,AUDHEAD,MEMATTR,EMEMREL,EMEMHMOREL,EMEMADDR";
	String l_recStatFilter = "A";
	
	
	private HmoSkinnyHelper hmoSkinnyHelper;

	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {
		
		getHm_AudRowlist(outAudList);
		getSrcCodes(outMemList);
		
		initializeGeneralHelper();
		hmoSkinnyHelper.SimpleProcessMemrow(outMemList, entRecNum);
		return getOutDataList();
	}

	private void initializeGeneralHelper() {
		hmoSkinnyHelper = new HmoSkinnyHelper(hm_AudRow,hm_MemHead,getOutDataList());
	}
	
	@Override
	public void setMemGetProp(IxnMemGet memGet) {
		memGet.setCvwName("HmoSiteMDE");
		memGet.setSegCodeFilter(l_segCodeFilter);
		memGet.setRecStatFilter(l_recStatFilter);
		memGet.setMemType("HMOSITE");
		memGet.setEntType("hmosite");
		memGet.setMemStatFilter("A");
	}

}
