package com.wellpoint.mde;

import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemRowList;

import com.wellpoint.mde.BusinessHelper.ProviderPgmGeneralHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessProviderPgmMemget extends AbstractMemgetIxn{
	
	String l_segCodeFilter = "MEMHEAD,AUDHEAD,ENTXEIA,EMEMTIMED,EMEMATRCODE,MEMATTR,EMEMATRTIMED";
	String l_recStatFilter = "A";
	
	private ProviderPgmGeneralHelper providerPgmGeneralHelper;

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
			providerPgmGeneralHelper.SimpleProcessMemrow(outMemList, entRecNum);
		}
		return getOutDataList();
	}

	private void initializeGeneralHelper() {
		providerPgmGeneralHelper = new ProviderPgmGeneralHelper(hm_AudRow, hm_MemHead, getOutDataList());
		providerPgmGeneralHelper.setSrcCodesDelimited(getSrcCodesDelimited());
		providerPgmGeneralHelper.setEPDSV2_Flag(isEPDSV2_Flag());
	}
	
	@Override
	public void setMemGetProp(IxnMemGet memGet) {
		memGet.setCvwName("ProvProgramMDE");
		memGet.setSegCodeFilter(l_segCodeFilter);
		memGet.setRecStatFilter(l_recStatFilter);
		memGet.setMemType("PROVPROGRAM");
		memGet.setEntType("provprogram");
		memGet.setMemStatFilter("A");
	}

}
