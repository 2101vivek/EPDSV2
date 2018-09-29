package com.wellpoint.mde.Skinny;

import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.Skinny.BusinessHelper.NetworkSkinnyHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessNetworkSkinny extends AbstractMemgetIxn{
	
	String l_segCodeFilter = "MEMHEAD,EMEMREL,MEMATTR,EMEMATRCODE,EMEMTIMED,AUDHEAD,ENTXEIA";
	String trimmedMemidnum = strBlank;
	String l_recStatFilter = "A";
	
	
	private NetworkSkinnyHelper networkSkinnyHelper;

	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {
		getHm_AudRowlist(outAudList);
		for (int i = 0; i < outMemList.size(); i++) {
			MemRow memRow = (MemRow) outMemList.rowAt(i);
			if (memRow instanceof MemHead) {
				String key_memRecNo = new Long(memRow.getMemHead().getMemRecno()).toString();
				MemHead memHead = new MemHead();
				memHead = memRow.getMemHead();
				if(null!=hm_MemHead && null==hm_MemHead.get(key_memRecNo)) {
					hm_MemHead.put(key_memRecNo, memHead);
					allSourceCodeSet.add(memRow.getSrcCode());
				}
			}
			if (memRow instanceof MemAttrRow) {
				String strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				if (strAttrCode.equalsIgnoreCase("NETWORKID")) {
					try {
						trimmedMemidnum = getString(memRow, "attrVal");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		setSourceCodesDelimited();
		if(EPDSV2_UpdatedFlag) {
			//to perform v2UpdatedRecords
		}
		else {
			initializeGeneralHelper();
			networkSkinnyHelper.SimpleProcessMemrow(outMemList, entRecNum);
		}
		return getOutDataList();
	}

	private void initializeGeneralHelper() {
		networkSkinnyHelper = new NetworkSkinnyHelper(hm_AudRow, hm_MemHead, getOutDataList());
		networkSkinnyHelper.setSrcCodesDelimited(getSrcCodesDelimited());
		networkSkinnyHelper.setEPDSV2_Flag(isEPDSV2_Flag());
		networkSkinnyHelper.setTrimmedMemidnum(trimmedMemidnum);
	}

	@Override
	public void setMemGetProp(IxnMemGet memGet) {
		memGet.setCvwName("NetworkMDE");
		memGet.setSegCodeFilter(l_segCodeFilter);
		memGet.setRecStatFilter(l_recStatFilter);
		memGet.setMemType("NETWORK");
		memGet.setEntType("network");
		memGet.setMemStatFilter("A");
	}

}

