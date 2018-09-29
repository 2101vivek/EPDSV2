package com.wellpoint.mde.Skinny.BusinessHelper;

import java.util.HashMap;
import java.util.List;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;
import com.wellpoint.mde.constants.HmoEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.HmoSkinnyService;
import com.wellpoint.mde.serviceImpl.HmoSkinnyServiceImpl;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class HmoSkinnyHelper extends AbstractSkinnyHelper{
	
	private HmoSkinnyService hmoService = new HmoSkinnyServiceImpl();

	public HmoSkinnyHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
	}
	
	private void initialize() {
		((AbstractSegment) hmoService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) hmoService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) hmoService).setOutDataList(getOutDataList());
	}
	
	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode().toUpperCase();
				HmoEnum attrCode = HmoEnum.getInitiateEnum(l_strAttrCode);
				
				if (((MemAttrRow)memRow).getInt("rowInd") != 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateCompositeSegments(attrCode, memRow, entRecNum);
				}
			}
		}
	}
	
	protected void generateCompositeSegments(HmoEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
			case HMOWREL:	if(ExtMemgetIxnUtils.getString(memRow,"relmemidnum").length()>0 
								&& ExtMemgetIxnUtils.getString(memRow,"reltype").length()>0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"termdt"))) {
									outputType = HmoEnum.WREL.getValue();
									segmentData = hmoService.getSegmentDataforWREL(memRow, entRecNum);
									generateRow();
							}
							break;
	
			case HMOCNTREL:	if(ExtMemgetIxnUtils.getString(memRow, "relmemidnum") .length() > 0  
								&& ExtMemgetIxnUtils.getString(memRow, "reltype") .length() > 0 
								&& ExtMemgetIxnUtils.getString(memRow, "hmocntrctcd") .length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt"))) {
									outputType = HmoEnum.WCON.getValue();
									segmentData = hmoService.getSegmentDataforWCON(memRow, entRecNum);
									generateRow();
									
									/*outputType = HmoEnum.WNET.getValue();
									segmentData = hmoService.getSegmentDataforWNET(memRow, entRecNum);
									generateRow();*/
							}
							break;

		}
	}
}
