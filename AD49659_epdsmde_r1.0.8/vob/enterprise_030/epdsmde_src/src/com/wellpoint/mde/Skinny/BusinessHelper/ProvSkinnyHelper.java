package com.wellpoint.mde.Skinny.BusinessHelper;

import java.util.HashMap;
import java.util.List;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;
import com.wellpoint.mde.constants.ProvEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.ProviderSkinnyService;
import com.wellpoint.mde.serviceImpl.ProvSkinnyServiceImpl;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ProvSkinnyHelper extends AbstractSkinnyHelper{
	
	ProviderSkinnyService provProviderService = new ProvSkinnyServiceImpl();
	
	boolean pnet = false;
	
	public boolean isPnet() {
		return pnet;
	}

	public void setPnet(boolean pnet) {
		this.pnet = pnet;
	}

	public ProvSkinnyHelper(HashMap<String, String[]> hm_AudRow,HashMap<String, MemHead> hm_MemHead,List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
		setEntityProp(EntityProperties.getProvProperties());
	}
	
	private void initialize() {
		((AbstractSegment) provProviderService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) provProviderService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) provProviderService).setOutDataList(getOutDataList());
	}

	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		String srccode = "";
		for (MemRow memRow : outMemList.listToArray()){
			if (memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode().toUpperCase();
				ProvEnum attrCode = ProvEnum.getInitiateEnum(l_strAttrCode);
				srccode = memRow.getSrcCode();
				if (((MemAttrRow)memRow).getInt("rowInd") !=5 && 
						((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A") &&
						getEntityProp().get("EPDS1").equalsIgnoreCase(srccode)) {
					generateCompositeSegments(attrCode, memRow, entRecNum);
				}
				else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
						((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A") &&
						getEntityProp().get("EPDS1").equalsIgnoreCase(srccode)) {
					generateSourceSegments(attrCode, memRow, entRecNum);
				}
			}
		}
	}
	
	protected void generateCompositeSegments(ProvEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		//WREL:
		case PROVWREL:	if(	!isPnet() 
							&& ExtMemgetIxnUtils.getString(memRow, "relmemidnum") .length() > 0  
							&& ExtMemgetIxnUtils.getString(memRow, "reltype") .length() > 0 
							&& ExtMemgetIxnUtils.getString(memRow, "relmemsrccode") .length() > 0 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))){
								outputType = ProvEnum.WREL.getValue();
								segmentData = provProviderService.getSegmentDataforWREL(memRow, entRecNum);
								generateRow();
						}
						break;
		//WCON:
		case PRVHMOCNTREL:	if(!isPnet() 
								&& ExtMemgetIxnUtils.getString(memRow, "relmemidnum") .length() > 0  
								&& ExtMemgetIxnUtils.getString(memRow, "reltype") .length() > 0 
								&& ExtMemgetIxnUtils.getString(memRow, "relmemsrccode") .length() > 0 
								&& ExtMemgetIxnUtils.getString(memRow, "hmocntrctcd") .length() > 0 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt"))
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt"))){
									outputType = ProvEnum.WCON.getValue();
									segmentData = provProviderService.getSegmentDataforWCON(memRow, entRecNum);
									generateRow();
							}
							break;
		//PPPRF:
		case OFFPATLIM:		if(!isPnet()) {
							outputType = ProvEnum.PPPRF.getValue();
							segmentData = provProviderService.getSegmentDataforPPPRF(memRow, entRecNum);
							generateRow();
		}
							break;
							
		}
	}
	
	protected void generateSourceSegments(ProvEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		
		//PNET
		case PROVNTWRK: if (ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "networkid")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "nweffectdt")) 
								&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "nwtermdt"))) {
							outputType = ProvEnum.PNET.getValue();
							segmentData = provProviderService.getSegmentDataforPNET(memRow,entRecNum);
							generateRow();
						}
						break;
		//PSPT
		case PROVBRDCRT: 	if(!isPnet()) {
								outputType = ProvEnum.PSPT.getValue();
								segmentData = provProviderService.getSegmentDataforPSPT(memRow,entRecNum);
								generateRow();
							}
							break;
		//PREL			
		case PROVREL: if(!isPnet()
				&& ExtMemgetIxnUtils.getString(memRow, "relmemidnum") .length() > 0  
	      		&& ExtMemgetIxnUtils.getString(memRow, "reltype") .length() > 0 
	      		&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
	      		&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))) {
					outputType = ProvEnum.PREL.getValue();
					segmentData = provProviderService.getSegmentDataforPREL(memRow, entRecNum);
					generateRow();
				}
				break;
				
		//PRMB
		case PROVRMB: 	if(!isPnet()) {
							outputType = ProvEnum.PRMB.getValue();
							segmentData = provProviderService.getSegmentDataforPRMB(memRow,entRecNum);
							generateRow();
						}
						break;
		}
	}
}
