package com.wellpoint.mde.BusinessHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import madison.mpi.EntXeia;
import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;
import com.wellpoint.mde.constants.GrpEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.GroupingService;
import com.wellpoint.mde.serviceImpl.AbstractServiceImpl;
import com.wellpoint.mde.serviceImpl.GrpServiceImpl;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class GrpGeneralHelper extends AbstractHelper<GrpEnum>{
	
	List<MemAttrRow> grpALTSRCIDMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> grpGRPMemAttrList = new ArrayList<MemAttrRow>();
	
	GroupingService grpService = new GrpServiceImpl();
	
	public GrpGeneralHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
		setEntityProp(EntityProperties.getGrpProperties());
	}
	
	private void initialize() {
		((AbstractSegment) grpService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) grpService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) grpService).setOutDataList(getOutDataList());
		((AbstractSegment) grpService).setSrcCodesDelimited(srcCodesDelimited);
		((AbstractServiceImpl) grpService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
	}
	
	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof EntXeia) {
				generateEIDSegment(memRow, entRecNum);
			}
			if(memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				GrpEnum attrCode = GrpEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
				try{
					if (((MemAttrRow)memRow).getInt("rowInd") != 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateCompositeSegments(attrCode, memRow, entRecNum);
					}
					else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateSourceSegments(attrCode, memRow, entRecNum);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		buildOtherSegments(entRecNum);
	}

	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		Set<String> segmentDataQcareSet = new HashSet<String>();
		
		outputType = GrpEnum.GRP.getValue();
		segmentData = grpService.buildGRPSegment(grpGRPMemAttrList, entRecNum);
		generateRow();
		
		outputType = GrpEnum.ALTSRCID.getValue();
		segmentDataSet = grpService.buildALTSRCIDSegment(grpALTSRCIDMemAttrList, entRecNum);
		for (String segmntData : segmentDataSet) {
			segmentData = segmntData;
			generateRow();
			String segArray[] = segmntData.split("~");
			srcCode_postprocess = strBlank;
			if(segArray.length>0) {
				srcCode_postprocess = segArray[segArray.length-1];
			}
			if(srcCode_postprocess.equalsIgnoreCase("EPDSV2")
					&& QcareAlternateIdMap.size()> 0 && null!= QcareAlternateIdMap){
				segmentDataQcareSet = QcaregenerateSourceLevelSegments(segmntData,QcareAlternateIdMap,
						"",4,5,206,"Grp");
				generateSegments(segmentDataQcareSet, GrpEnum.ALTSRCID.getValue());
			}
		}
		
	}

	@Override
	protected void generateCompositeSegments(GrpEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		case GRPNAME:
		case GRPTYPE:
		case GRPSTAT:	grpGRPMemAttrList.add((MemAttrRow) memRow);
						if(isEPDSV2_Flag()) {
							grpALTSRCIDMemAttrList.add((MemAttrRow) memRow);
						}
						break;
						
		case GRPID:		
		case GRPOWNNAME:
		case GRPLOCGROUP:
		case GRPLOCGRPTYP:
		case GRPFAMSWEEP:
		case GRPMKTBU:
		case GRPMEMSUP:
		case GRPFUNDTYPE:
		case GRPNETARNGMT:
		case GRPPRODTYPE:
		case GRPSVCLOCZIP:
		case GRPSVCEMPGRP:
		case GRPGBDCD:
						grpGRPMemAttrList.add((MemAttrRow) memRow);
						break;
						
		case GRPCONTRACT:
						if(getString(memRow,"programid").length()>0 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"programideffectdt")) 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"programidtermdt"))
							&& getString(memRow, "cntrctstatecd").length()>0) {
								outputType = GrpEnum.GRPPGM.getValue();
								segmentData = grpService.getSegmentDataforGRPPGM(memRow, entRecNum);
								generateRow();
						}
						break;
						
		case GRPALTID:	outputType = GrpEnum.GALT.getValue();
						segmentData = grpService.getSegmentDataforGALT(memRow, entRecNum); 
						generateRow();
						break;
						
		case GRPRMB:	
						if(getString(memRow, "grprmbid").length()>0 && getString(memRow, "rmbarrangetype").length()>0
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbeffectdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbtermdt"))) {
								outputType = GrpEnum.GRMB.getValue();
								segmentData = grpService.getSegmentDataforGRMB(memRow, entRecNum); 
								generateRow();
						}
						break;
						
		case GRPNTWRK:	
						if(getString(memRow, "networkid").length() > 0 && 
							ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grpneteffectdt")) &&
							ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "grpnettermdt"))){
								outputType = GrpEnum.GNET.getValue();
								segmentData = grpService.getSegmentDataforGNET(memRow, entRecNum); 
								generateRow();
						}
						break;
						
		case GPMDE:		if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "attrval")) &&
							ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))&&
							ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))) {
								outputType = GrpEnum.GPMDE.getValue();
								segmentData = grpService.getSegmentDataforGPMDE(memRow, entRecNum); 
								generateRow();
						}
						break;
		
		//GALTRO
		/*case GRPROLLID:		if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "rolloversrcval")) &&
				ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "rolloversrctype")) &&
				ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rolloversrcissuedt"))&&
				ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "rollovertranseqno")) &&
				ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "rolloverrecipntsrcval")) &&
				ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "rolloverrecipnttype")) &&
				ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rolloverrecipntissuedt")) &&
				ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "rollovereffectdt")) &&
				ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getString(memRow, "networkid"))) {
					outputType = GrpEnum.GALTRO.getValue();
					segmentData = grpService.getSegmentDataforGALTRO(memRow, entRecNum); 
					generateRow();
			}
			break;*/
		}
	}

	@Override
	protected void generateSourceSegments(GrpEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		case GRPTYPE:
		case GRPSTAT:
		case GRPNAME:	grpALTSRCIDMemAttrList.add((MemAttrRow) memRow);
						break;
		}
		
	}

}
