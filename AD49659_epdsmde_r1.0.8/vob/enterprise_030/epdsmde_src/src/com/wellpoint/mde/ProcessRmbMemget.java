package com.wellpoint.mde;

import java.util.List;

import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.BusinessHelper.RmbGeneralHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractMemgetIxn;
import com.wellpoint.mde.generateRow.outData;

public class ProcessRmbMemget extends AbstractMemgetIxn{
	
	String l_segCodeFilter = "MEMHEAD,AUDHEAD,ENTXEIA,EMEMATRCODE,MEMATTR,EMEMTIMED,EMEMNUM,EMEMATRCDTMD,EMEMATRTIMED";
	String l_recStatFilter = "A";

	String trimmedMemidnum = strBlank;
	
	private RmbGeneralHelper rmbGeneralHelper;

	@Override
	public List<outData> processMemRows(MemRowList outMemList,
			AudRowList outAudList, long entRecNum) throws Exception {
		getHm_AudRowlist(outAudList);
		
		for (int i = 0; i < outMemList.size(); i++)
		{
			MemRow memRow = (MemRow) outMemList.rowAt(i);
			if (memRow instanceof MemHead) 
			{
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
				if (strAttrCode.equalsIgnoreCase("RMBID")) {
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
			rmbGeneralHelper.SimpleProcessMemrow(outMemList, entRecNum);
		}
		return getOutDataList();
	}

	private void initializeGeneralHelper() {
		rmbGeneralHelper = new RmbGeneralHelper(hm_AudRow, hm_MemHead, getOutDataList());
		rmbGeneralHelper.setSrcCodesDelimited(getSrcCodesDelimited());
		rmbGeneralHelper.setEPDSV2_Flag(isEPDSV2_Flag());
		rmbGeneralHelper.setTrimmedMemidnum(trimmedMemidnum);
	}
	
	@Override
	public void setMemGetProp(IxnMemGet memGet) {
		memGet.setCvwName("ReimbursementMDE");
		memGet.setSegCodeFilter(l_segCodeFilter);
		memGet.setRecStatFilter(l_recStatFilter);
		memGet.setMemType("REIMBURSEMENT");
		memGet.setEntType("reimb");
		memGet.setMemStatFilter("A");
	}

}
