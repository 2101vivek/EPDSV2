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
import com.wellpoint.mde.constants.ProvPgmEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.ProviderPgmService;
import com.wellpoint.mde.serviceImpl.AbstractServiceImpl;
import com.wellpoint.mde.serviceImpl.ProviderPgmServiceImpl;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ProviderPgmGeneralHelper extends AbstractHelper<ProvPgmEnum>{
	
	boolean l_memHeadStatus = false;
	
	String l_strSrcCd_common = strBlank;

	List<MemAttrRow> ppgmMPPRGMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> ppgmALTSRCIDMemAttrList = new ArrayList<MemAttrRow>();
	
	ProviderPgmService providerPgmService= new ProviderPgmServiceImpl();
	
	public ProviderPgmGeneralHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
	}
	
	private void initialize() {
		((AbstractSegment) providerPgmService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) providerPgmService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) providerPgmService).setOutDataList(getOutDataList());
		((AbstractSegment) providerPgmService).setSrcCodesDelimited(srcCodesDelimited);
		((AbstractServiceImpl) providerPgmService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
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
				ProvPgmEnum attrCode = ProvPgmEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
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
		
		outputType = ProvPgmEnum.MPPRG.getValue();
		segmentData = providerPgmService.buildMPPRGSegment(ppgmMPPRGMemAttrList, entRecNum, l_strSrcCd_common);
		generateRow();
		
		outputType = ProvPgmEnum.ALTSRCID.getValue();
		segmentDataSet = providerPgmService.buildALTSRCIDSegment(ppgmALTSRCIDMemAttrList, entRecNum);
		generateSegments(segmentDataSet, ProvPgmEnum.ALTSRCID.getValue());
		
	}

	@Override
	protected void generateCompositeSegments(ProvPgmEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		case PROGSTATUS:
		case PROGTYPE:	
		case PROGNAME:	ppgmMPPRGMemAttrList.add((MemAttrRow) memRow);
						if(isEPDSV2_Flag()) {
							ppgmALTSRCIDMemAttrList.add((MemAttrRow) memRow);
						}
						break;
						
		case PROGADMIN:
		case PRGVERNUM:	
		case PRGCATCD:
		case VALBASPRGID:ppgmMPPRGMemAttrList.add((MemAttrRow) memRow);
						break;
						
		}
	}

	@Override
	protected void generateSourceSegments(ProvPgmEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		case PROGSTATUS:	
		case PROGTYPE:
		case PROGNAME:	ppgmALTSRCIDMemAttrList.add((MemAttrRow) memRow);
						break;
		}
	}
}
