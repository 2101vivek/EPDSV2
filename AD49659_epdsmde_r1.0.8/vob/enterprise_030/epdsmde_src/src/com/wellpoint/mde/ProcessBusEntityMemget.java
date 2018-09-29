package com.wellpoint.mde;

import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemRowList;

import com.wellpoint.mde.BusinessHelper.BusEntityGeneralHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessBusEntityMemget extends AbstractMemgetIxn{
	
	String l_segCodeFilter = "MEMHEAD,MEMATTR,EMEMADDR,EMEMCONTACT,EMEMIDENT,EMEMCONTRACT," +
	"EMEMATRTIMED,EMEMATRCDTMD,EMEMATRCODE,AUDHEAD,ENTXEIA";
	String l_recStatFilter = "A";
	
	private BusEntityGeneralHelper busEntityGeneralHelper;

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
			busEntityGeneralHelper.SimpleProcessMemrow(outMemList, entRecNum);
		}
		return getOutDataList();
	}

	private void initializeGeneralHelper() {
		busEntityGeneralHelper = new BusEntityGeneralHelper(hm_AudRow,hm_MemHead,getOutDataList());
		busEntityGeneralHelper.setSrcCodesDelimited(getSrcCodesDelimited());
		busEntityGeneralHelper.setEPDSV2_Flag(isEPDSV2_Flag());
		
	}
	
	@Override
	public void setMemGetProp(IxnMemGet memGet) {
		memGet.setCvwName("BusinessEntityMDE");
		memGet.setSegCodeFilter(l_segCodeFilter);
		memGet.setRecStatFilter(l_recStatFilter);
		memGet.setMemType("BUSINESSENTITY");
		memGet.setEntType("busentity");
		memGet.setMemStatFilter("A");
	}

}
