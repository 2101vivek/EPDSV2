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
import com.wellpoint.mde.constants.RprtGrpEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.ReportingGrpService;
import com.wellpoint.mde.serviceImpl.AbstractServiceImpl;
import com.wellpoint.mde.serviceImpl.ReportingGrpServiceImpl;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ReportingGrpGeneralHelper extends AbstractHelper<RprtGrpEnum>{
	
	private boolean l_memHeadStatus = false;
	
	private String l_strSrcCd_common = strBlank;
	
	private ReportingGrpService reportingGrpService = new ReportingGrpServiceImpl();
	
	private List<MemAttrRow> regrpALTSRCIDMemAttrList = new ArrayList<MemAttrRow>();
	
	private List<MemAttrRow> regrpRPTGPMemAttrList = new ArrayList<MemAttrRow>();

	public ReportingGrpGeneralHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
	}
	
	private void initialize() {
		((AbstractSegment) reportingGrpService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) reportingGrpService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) reportingGrpService).setOutDataList(getOutDataList());
		((AbstractSegment) reportingGrpService).setSrcCodesDelimited(srcCodesDelimited);
		((AbstractServiceImpl) reportingGrpService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
	}

	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof EntXeia) {
				generateEIDSegment(memRow, entRecNum);
			}
			if(memRow instanceof MemAttrRow) {
				if (!l_memHeadStatus) {
					l_strSrcCd_common = memRow.getMemHead().getSrcCode();
					l_memHeadStatus = true;
				}
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				RprtGrpEnum attrCode = RprtGrpEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
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
		
		outputType = RprtGrpEnum.RPTGP.getValue();
		segmentData = reportingGrpService.buildRPTGPSegment(regrpRPTGPMemAttrList, entRecNum, l_strSrcCd_common);
		generateRow();
		
		segmentDataSet = reportingGrpService.buildALTSRCIDSegment(regrpALTSRCIDMemAttrList, entRecNum);
		generateSegments(segmentDataSet, RprtGrpEnum.ALTSRCID.getValue());
		
	}

	@Override
	protected void generateCompositeSegments(RprtGrpEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		case RPTGRPNAME:
		case RPTGRPTYPE:
		case RPTGRPSTAT:	regrpRPTGPMemAttrList.add((MemAttrRow) memRow);
							if(isEPDSV2_Flag()) {
								regrpALTSRCIDMemAttrList.add((MemAttrRow) memRow);
							}
							break;
							
		case RPTGRPID:
		case RPTPRVPROGID:	regrpRPTGPMemAttrList.add((MemAttrRow) memRow);
							break;
							
		case RPTGRPREL:		if(getString(memRow, "relmemidnum").length()>0 
							&& getString(memRow, "relmemsrccode").length()>0
							&& getString(memRow, "reltype").length()>0
						    && ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt"))
						    && ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"termdt"))) {
								outputType = RprtGrpEnum.RPTRL.getValue();
								segmentData = reportingGrpService.getSegmentDataforRPTRL(memRow, entRecNum);
								generateRow();
							}
							break;
		}
	}

	@Override
	protected void generateSourceSegments(RprtGrpEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		case RPTGRPTYPE:
		case RPTGRPSTAT:	
		case RPTGRPNAME:	regrpALTSRCIDMemAttrList.add((MemAttrRow) memRow);
							break;
		}
	}
}
