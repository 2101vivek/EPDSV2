package com.wellpoint.mde;

import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemRowList;

import com.wellpoint.mde.BusinessHelper.HmoGeneralHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessHmoMemget extends AbstractMemgetIxn{
	
	String l_segCodeFilter = "MEMHEAD,AUDHEAD,ENTXEIA,MEMATTR,EMEMATRCODE,EMEMTIMED,EMEMATRCDTMD,EMEMIDENT," +
	"EMEMHMOSVCTB,EMEMSCHED,EMEMHMOENR,EMEMHMOROLL,EMEMADDR,EMEMCONTACT,EMEMPROVNOTE,EMEMREL,EMEMHMOCTRCT,EMEMHMOREL,EMEMREGION";
	String l_recStatFilter = "A";
	
	
	private HmoGeneralHelper hmoGeneralHelper;

	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {
		getSrcCodes(outMemList);
		setSourceCodesDelimited();
		getHm_AudRowlist(outAudList);
		if(EPDSV2_UpdatedFlag) {
			//to perform v2UpdatedRecords
		}
		else {
			initializeGeneralHelper();
			hmoGeneralHelper.SimpleProcessMemrow(outMemList, entRecNum);
		}
		return getOutDataList();
	}

	private void initializeGeneralHelper() {
		hmoGeneralHelper = new HmoGeneralHelper(hm_AudRow,hm_MemHead,getOutDataList());
		hmoGeneralHelper.setSrcCodesDelimited(getSrcCodesDelimited());
		hmoGeneralHelper.setEPDSV2_Flag(isEPDSV2_Flag());
		
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
