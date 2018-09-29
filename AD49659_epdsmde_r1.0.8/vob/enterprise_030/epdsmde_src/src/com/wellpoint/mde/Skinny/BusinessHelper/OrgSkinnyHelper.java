package com.wellpoint.mde.Skinny.BusinessHelper;

import java.util.HashMap;
import java.util.List;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;
import com.wellpoint.mde.constants.OrgEnum;
import com.wellpoint.mde.constants.ProvEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.OrgSkinnyService;
import com.wellpoint.mde.serviceImpl.OrgSkinnyServiceImpl;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class OrgSkinnyHelper extends AbstractSkinnyHelper{

	
	private OrgSkinnyService orgService = new OrgSkinnyServiceImpl();

	boolean pnet = false;
	
	public boolean isPnet() {
		return pnet;
	}

	public void setPnet(boolean pnet) {
		this.pnet = pnet;
	}
	
	public OrgSkinnyHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
		setEntityProp(EntityProperties.getOrgProperties());
	}
	
	private void initialize() {
		((AbstractSegment) orgService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) orgService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) orgService).setOutDataList(getOutDataList());
		setEntityProp(EntityProperties.getOrgProperties());
	}
	
	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		String srccode = "";
		for (MemRow memRow : outMemList.listToArray()){
			if (memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode().toUpperCase();
				OrgEnum attrCode = OrgEnum.getInitiateEnum(l_strAttrCode);
				srccode = memRow.getSrcCode();
				try{
					//Composite view
					if (((MemAttrRow)memRow).getInt("rowInd") != 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A") &&
							getEntityProp().get("EPDS1").equalsIgnoreCase(srccode)) {
						generateCompositeSegments(attrCode, memRow, entRecNum);
					}
					//A segments for sources other than EPDS1,QCARE,EPDSV2 should generate from RowInd:5 memrows  
					else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A") &&
							getEntityProp().get("EPDS1").equalsIgnoreCase(srccode)) {
						generateSourceSegments(attrCode, memRow, entRecNum);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void generateSourceSegments(OrgEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		
		//PNET
		case ORGNTWRK: if (ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "networkid")) &&
								ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "nweffectdt")) &&
								ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "nwtermdt"))) {
							outputType = ProvEnum.PNET.getValue();
							segmentData = orgService.getSegmentDataforPNET(memRow,entRecNum);
							generateRow();
						}
						break;
		//PSPT
		case ORGBRDCRT: if(!isPnet()) {	
							outputType = ProvEnum.PSPT.getValue();
							segmentData = orgService.getSegmentDataforPSPT(memRow,entRecNum);
							generateRow();
						}
						break;
		//PREL			
		case ORGREL: if(!isPnet()
				&& ExtMemgetIxnUtils.getString(memRow, "relmemidnum") .length() > 0  
	      		&& ExtMemgetIxnUtils.getString(memRow, "reltype") .length() > 0 
	      		&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
	      		&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))) {
					outputType = ProvEnum.PREL.getValue();
					segmentData = orgService.getSegmentDataforPREL(memRow, entRecNum);
					generateRow();
				}
				break;
				
		//PRMB
		case ORGRMB: 	if(!isPnet()) {
							outputType = ProvEnum.PRMB.getValue();
							segmentData = orgService.getSegmentDataforPRMB(memRow,entRecNum);
							generateRow();
						}
						break;
		}
	}

	protected void generateCompositeSegments(OrgEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		//PREL-Begin
		case ORGREL:  if(!isPnet()
						&& getString(memRow, "relmemidnum").length() > 0
						&& getString(memRow, "reltype").length() > 0
						&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")) 
						&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))) {
							outputType = OrgEnum.PREL.getValue();
							segmentData = orgService.getSegmentDataforPREL(memRow, entRecNum);
							generateRow();
						}
						break;
						
				//PPPRF
			 case OFFPTLIMORG: 	if(!isPnet()) {
					 				outputType = OrgEnum.PPPRF.getValue();
									segmentData = orgService.getSegmentDataforPPPRF(memRow, entRecNum);
									generateRow();
			 					}
								break;
		}
	}

}
