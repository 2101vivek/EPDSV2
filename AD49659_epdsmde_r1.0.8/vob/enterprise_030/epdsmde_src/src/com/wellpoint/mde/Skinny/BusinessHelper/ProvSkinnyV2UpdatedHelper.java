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
import com.wellpoint.mde.constants.ProvEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.ProviderSkinnyService;
import com.wellpoint.mde.serviceImpl.ProvSkinnyServiceImpl;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ProvSkinnyV2UpdatedHelper extends AbstractHelper<ProvEnum>{

	ProviderSkinnyService provProviderService = new ProvSkinnyServiceImpl();

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

	public ProvSkinnyV2UpdatedHelper(HashMap<String, String[]> hm_AudRow,HashMap<String, MemHead> hm_MemHead,List<outData> outDataList) {
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

	public void v2UpdatedProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if (memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode().toUpperCase();
				ProvEnum attrCode = ProvEnum.getInitiateEnum(l_strAttrCode);

				if (((MemAttrRow)memRow).getInt("rowInd") < 3 && 
						((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
					generateCompositeSegments(attrCode, memRow, entRecNum);
				}
				else if (((MemAttrRow)memRow).getInt("rowInd") == 3 && 
						((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
					generateRetiredSourceSegments(attrCode, memRow, entRecNum);
				}
			}
		}
	}

	private void generateRetiredSourceSegments(ProvEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {

		switch(attrCode) {	
		//PSPT
		case PROVBRDCRT: 	if(!isPnet()
				&& getString(memRow, "specialtycd").length() > 0
				&& getString(memRow, "primaryspec").length() > 0
				&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")) 
				&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt"))) {	
				
				outputType = ProvEnum.PSPT.getValue();
				segmentData = provProviderService.getSegmentDataforPSPT(memRow,entRecNum);
				
				segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,4,3,-1,true);
				for (String segData : segmentDataE2ESet) {
					segmentData = segData ;
					generateRow();
					
					//P to A Copy: Generating EPDS1 source record
					segmentDataQcareSet = E2EgenerateSourceLevelSegments(segmentData,allSourceCodeSet,
							e2eLeagcyidMap,4,3,-1,false);
					generateSegments(segmentDataQcareSet, ProvEnum.PSPT.getValue());
				}
			}
			break;
		}
	}

	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {

	}

	@Override
	protected void generateCompositeSegments(ProvEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		try{
			switch(attrCode) {
			//WREL:
			case PROVWREL:	if(!isPnet()
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
			case OFFPATLIM:	if(!isPnet()){
								outputType = ProvEnum.PPPRF.getValue();
								segmentData = provProviderService.getSegmentDataforPPPRF(memRow, entRecNum);
								generateRow();
							}
							break;

			//PNET
			case PROVNTWRK: if (ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "networkid"))
					&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "nweffectdt"))
					&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "nwtermdt"))) {
				outputType = ProvEnum.PNET.getValue();
				segmentData = provProviderService.getSegmentDataforPNET(memRow,entRecNum);
				segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,4,3,-1,true);

				//P to A Copy: Generating EPDSV2 source record
				for (String segmntData : segmentDataSet) {
					segmentData = segmntData;
					generateRow();

					//P to A Copy: Generating EPDS1 source record
					if(provProviderService.getSrcCode().equalsIgnoreCase("EPDS1")){
						segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
								e2eLeagcyidMap,4,3,-1, false);
						generateSegments(segmentDataE2ESet, ProvEnum.PNET.getValue());
					}
				}
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
				if(ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2")){
					generateRow();
				}
				//P to A Copy: Generating EPDS1 source record
				//Fixed as part of CQ:WLPRD01725314
				if(/*ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDSV2") ||*/
						ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")).equalsIgnoreCase("EPDS1")){
					segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
							e2eLeagcyidMap,4,3,-1, false);
					generateSegments(segmentDataE2ESet, ProvEnum.PREL.getValue());	
				}
			}
			break;

			//PRMB
			case PROVRMB: if(!isPnet()
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangeeffdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangetermdt"))){
					
					outputType = ProvEnum.PRMB.getValue();
					segmentData = provProviderService.getSegmentDataforPRMB(memRow,entRecNum);
					segmentDataSet = E2EgenerateSourceLevelSegments(segmentData,null,e2eLeagcyidMap,4,3,-1,true);
	
					for (String segmntData : segmentDataSet) {
						segmentData = segmntData;
						generateRow();
	
						//P to A Copy: Generating EPDS1 source record
						if(provProviderService.getSrcCode().equalsIgnoreCase("EPDS1")){
							segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmentData, allSourceCodeSet,
									e2eLeagcyidMap,4,3,-1,false);
							generateSegments(segmentDataE2ESet, ProvEnum.PRMB.getValue());
						}
					}
				}
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected void generateSourceSegments(ProvEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
	}

}
