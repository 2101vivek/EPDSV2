package com.wellpoint.mde.Skinny;

import java.util.ArrayList;
import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;
import madison.mpi.RowIterator;

import com.wellpoint.mde.Skinny.BusinessHelper.ProvSkinnyHelper;
import com.wellpoint.mde.Skinny.BusinessHelper.ProvSkinnyV2UpdatedHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessProviderSkinny extends AbstractMemgetIxn{
	
	private ProvSkinnyHelper provSkinnyHelper;
	
	private ProvSkinnyV2UpdatedHelper provSkinnyv2UpdatedHelper;
	
	String l_segCodeFilter = "MEMHEAD,AUDHEAD,MEMATTR,MEMDATE,EMEMHMOREL,EMEMREL,EMEMRMB," +
			"EMEMSPLTYBRD,EMEMOFCPTLIM,EMEMATRCDTMD,EMEMNET,EMEMADDR";
	
	String l_recStatFilter = "A";
	
	ArrayList<String> qcareMemrecnoList = new ArrayList<String>();
	MemRowList ememidentMemrow = new MemRowList();
	
	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {

		getHm_AudRowlist(outAudList);
		getSrcCodes(outMemList);
		
		if(allSourceCodeSet.contains("EPDSV2")) {
			EPDSV2_UpdatedFlag = true;
		}
		
		if(EPDSV2_UpdatedFlag) {
			collect_srcodes_and_legacyids(outMemList);
			initializeSkinnyV2UpdatedHelper();
			provSkinnyv2UpdatedHelper.v2UpdatedProcessMemrow(outMemList, entRecNum);
		}
		else {
			initializeSkinnyHelper();
			provSkinnyHelper.SimpleProcessMemrow(outMemList, entRecNum);
		}
		
		return getOutDataList();
	}

	public void collect_srcodes_and_legacyids(MemRowList outMemList) throws Exception{
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
			}
		}
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
	
	private void initializeSkinnyHelper() {
		provSkinnyHelper = new ProvSkinnyHelper(hm_AudRow,hm_MemHead,getOutDataList());
		provSkinnyHelper.setPnet(isPnet());
	}
	
	private void initializeSkinnyV2UpdatedHelper() {
		provSkinnyv2UpdatedHelper = new ProvSkinnyV2UpdatedHelper(hm_AudRow,hm_MemHead,getOutDataList());
		provSkinnyv2UpdatedHelper.setE2eLeagcyidMap(e2eLeagcyidMap);
		provSkinnyv2UpdatedHelper.setAllSourceCodeSet(allSourceCodeSet);
		provSkinnyv2UpdatedHelper.setPnet(isPnet());
	}
}
