package com.wellpoint.mde.Skinny.BusinessHelper;

import java.util.HashMap;
import java.util.Map;

import madison.mpi.MemHead;
import madison.mpi.MemRow;

import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public abstract class AbstractSkinnyHelper extends AbstractSegment{
	
	Map<String, String> entityProp = new HashMap<String, String>();
	
	public Map<String, String> getEntityProp() {
		return entityProp;
	}

	public void setEntityProp(Map<String, String> entityProp) {
		this.entityProp = entityProp;
	}

	protected void generateEIDSegment(MemRow memRow, long entRecNum) {
		try 
			{
			if(null!=hm_MemHead) {
				MemHead temp_memHead ;
				temp_memHead = (MemHead)hm_MemHead.get(new Long(memRow.getMemRecno()).toString());
				l_memIdNum = temp_memHead.getMemIdnum();
			}
			outputType = "EID";
			segmentData = ExtMemgetIxnUtils.appendStr(outputType,
					ExtMemgetIxnUtils.getDateAsString(memRow, "recCtime"), Long
							.toString(entRecNum), "EPDS V2", "UPDATED",
					ExtMemgetIxnUtils.getLegacyLinkValue(getString(memRow,
							"prevEntRecno")), ExtMemgetIxnUtils.getString(
							memRow, "srcCode"), l_memIdNum, ExtMemgetIxnUtils
							.getDateAsString(memRow, "recCtime"),
					ExtMemgetIxnUtils.getString(memRow, "prevEntRecno"),
					ExtMemgetIxnUtils.getNDelimiters(11), srcCodesDelimited);
			generateRow();						
		} catch (Exception e) {
			logInfo("String getter exception - " );
			e.printStackTrace();
		}
	}
}
