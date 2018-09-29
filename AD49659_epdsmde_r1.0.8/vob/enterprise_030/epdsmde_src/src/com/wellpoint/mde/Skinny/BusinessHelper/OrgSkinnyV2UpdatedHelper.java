package com.wellpoint.mde.Skinny.BusinessHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.BusinessHelper.AbstractHelper;
import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;
import com.wellpoint.mde.constants.OrgEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.OrgSkinnyService;
import com.wellpoint.mde.serviceImpl.OrgSkinnyServiceImpl;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class OrgSkinnyV2UpdatedHelper extends AbstractHelper<OrgEnum>{

	private OrgSkinnyService orgService = new OrgSkinnyServiceImpl();
	
	Set<String> segmentDataSet = new HashSet<String>();

	Set<String> segmentDataQcareSet = new HashSet<String>();

	Set<String> segmentDataE2ESet = new HashSet<String>();
	
	boolean pnet = false;
	
	public boolean isPnet() {
		return pnet;
	}

	public void setPnet(boolean pnet) {
		this.pnet = pnet;
	}

	public OrgSkinnyV2UpdatedHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
	}
	
	private void initialize() {
		((AbstractSegment) orgService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) orgService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) orgService).setOutDataList(getOutDataList());
		setEntityProp(EntityProperties.getOrgProperties());
	}
	
	public void v2UpdatedProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			try{
				if(memRow instanceof MemAttrRow) {
					String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode().toUpperCase();
					OrgEnum attrCode = OrgEnum.getInitiateEnum(l_strAttrCode);
					if (((MemAttrRow)memRow).getInt("rowInd") != 5 && 
								((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
							generateCompositeSegments(attrCode, memRow, entRecNum);
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
				logInfo("MDE Error: entrecno = " + entRecNum);
			}
		}
	}
	
	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		
	}
	
	protected void generateSourceSegments(OrgEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		}
	}

	@Override
	protected void generateCompositeSegments(OrgEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		
		//PSPT
		case ORGBRDCRT: if(!isPnet()
							&& getString(memRow, "specialtycd").length() > 0
							&& getString(memRow, "primaryspec").length() > 0
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")) 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt"))) {	
								
								outputType = OrgEnum.PSPT.getValue();
								segmentData = orgService.getSegmentDataforPSPT(memRow,entRecNum);
								
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,4,3,-1,true);
								for (String segData : segmentDataE2ESet) {
									segmentData = segData ;
									generateRow();
									
									//P to A Copy: Generating EPDS1 source record
									segmentDataQcareSet = E2EgenerateSourceLevelSegments(segmentData,allSourceCodeSet,
											e2eLeagcyidMap,4,3,-1,false);
									generateSegments(segmentDataQcareSet, OrgEnum.PSPT.getValue());
							}
						}
						break;
						
		//PREL-Begin
		case ORGREL:  if(!isPnet()
						&& getString(memRow, "relmemidnum").length() > 0
						&& getString(memRow, "reltype").length() > 0
						&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")) 
						&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))) {
							outputType = OrgEnum.PREL.getValue();
							segmentData = orgService.getSegmentDataforPREL(memRow, entRecNum);
							
							if(ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2")) {
								generateRow();
							}
							//P to A Copy: Generating EPDS1 source record
							if(/*ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2") || */
									ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDS1")){
								segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
										e2eLeagcyidMap,4,3,-1, false);
								generateSegments(segmentDataE2ESet, OrgEnum.PREL.getValue());
							}
						}
						break;
			//PNET
		 case ORGNTWRK: if (ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "networkid")) &&
				 			ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "nweffectdt")) &&
							ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "nwtermdt"))) {
							 	outputType = OrgEnum.PNET.getValue();
								segmentData = orgService.getSegmentDataforPNET(memRow,entRecNum);
								segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,4,3,-1,true);

								//P to A Copy: Generating EPDSV2 source record
								for (String segmntData : segmentDataSet) {
									segmentData = segmntData;
									generateRow();
				
									//P to A Copy: Generating EPDS1 source record
									if(orgService.getSrcCode().equalsIgnoreCase("EPDS1")){
										segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
												e2eLeagcyidMap,4,3,-1, false);
										generateSegments(segmentDataE2ESet, OrgEnum.PNET.getValue());
									}
								}
						}
						break;
						
			//PRMB
		 case ORGRMB: 	if(!isPnet()
				 		&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangeeffdt"))
						&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangetermdt"))){
			 
							outputType = OrgEnum.PRMB.getValue();
							segmentData = orgService.getSegmentDataforPRMB(memRow,entRecNum);
							segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,4,3,-1,true);

							for (String segmntData : segmentDataSet) {
								segmentData = segmntData;
								generateRow();

								//P to A Copy: Generating EPDS1 source record
								if(orgService.getSrcCode().equalsIgnoreCase("EPDS1RMB")){
									segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
											e2eLeagcyidMap,4,3,-1,false);
									generateSegments(segmentDataE2ESet, OrgEnum.PRMB.getValue());
								}
							}
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
