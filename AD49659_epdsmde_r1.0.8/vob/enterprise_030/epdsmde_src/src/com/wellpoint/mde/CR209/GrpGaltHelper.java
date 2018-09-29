package com.wellpoint.mde.CR209;

import java.util.HashMap;
import java.util.List;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.constants.GrpEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;


public class GrpGaltHelper extends AbstractHelperCr<GrpEnum>{
	
	public GrpGaltHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
		setEntityProp(EntityProperties.getGrpProperties());
	}

	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void generateCompositeSegments(GrpEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		case GRPALTID:	outputType = GrpEnum.GALT.getValue();
						getMemHeadValues(memRow);
						segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"),
									Long.toString(entRecNum) , "EPDS V2", getString(memRow, "idissuer"),
									getString(memRow, "idnumber"), getString(memRow, "idtype"),
									ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate"),
									ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate"),
									getString(memRow, "idtermrsn"), ExtMemgetIxnUtils.getNDelimiters(29));
						generateRow();
		}
		
	}

	@Override
	protected void generateSourceSegments(GrpEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum, boolean qcare)  throws Exception{
		for (MemRow memRow : outMemList.listToArray()){
			if (memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				GrpEnum attrCode = GrpEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
				if (((MemAttrRow)memRow).getInt("rowInd") != 5 && 
						((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
					generateCompositeSegments(attrCode, memRow, entRecNum);
				}
			}
		}
	}
}
