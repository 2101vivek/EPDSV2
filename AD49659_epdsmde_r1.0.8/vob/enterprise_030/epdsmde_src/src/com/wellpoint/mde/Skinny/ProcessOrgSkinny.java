package com.wellpoint.mde.Skinny;

import java.util.ArrayList;
import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;
import madison.mpi.RowIterator;

import com.wellpoint.mde.Skinny.BusinessHelper.OrgSkinnyHelper;
import com.wellpoint.mde.Skinny.BusinessHelper.OrgSkinnyV2UpdatedHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessOrgSkinny extends AbstractMemgetIxn{
	
	private OrgSkinnyHelper orgSkinnyHelper;
	
	private OrgSkinnyV2UpdatedHelper orgSkinnyV2UpdatedHelper;
	
	private static final String l_segCodeFilter = "MEMHEAD,AUDHEAD,MEMATTR,EMEMRMB,EMEMNET,EMEMHMOREL" +
			",EMEMREL,EMEMSPLTYBRD,EMEMOFCPTLIM,EMEMADDR,EMEMATRCDTMD"; 
	private static final String l_recStatFilter = "A";

	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {
		getHm_AudRowlist(outAudList);
		getSrcCodes(outMemList);
		
		if (allSourceCodeSet.contains("EPDSV2ORG")) {
			EPDSV2_UpdatedFlag = true;
		}
		
		if(EPDSV2_UpdatedFlag) {
			collect_srcodes_and_legacyids(outMemList);
			initializeSkinnyV2UpdatedHelper();
			orgSkinnyV2UpdatedHelper.v2UpdatedProcessMemrow(outMemList, entRecNum);
		}
		else {
			initializeSkinnyHelper();
			orgSkinnyHelper.SimpleProcessMemrow(outMemList, entRecNum);
		}
		
		return getOutDataList();
	}
	
	private void collect_srcodes_and_legacyids(MemRowList outMemList) throws Exception{
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

	private void initializeSkinnyHelper() {
		orgSkinnyHelper = new OrgSkinnyHelper(hm_AudRow,hm_MemHead,getOutDataList());
		orgSkinnyHelper.setPnet(isPnet());
	}
	
	private void initializeSkinnyV2UpdatedHelper() {
		orgSkinnyV2UpdatedHelper = new OrgSkinnyV2UpdatedHelper(hm_AudRow, hm_MemHead, getOutDataList());
		orgSkinnyV2UpdatedHelper.setE2eLeagcyidMap(e2eLeagcyidMap);
		orgSkinnyV2UpdatedHelper.setAllSourceCodeSet(allSourceCodeSet);
		orgSkinnyV2UpdatedHelper.setPnet(isPnet());
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
