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
import com.wellpoint.mde.constants.BusEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.BusEntityService;
import com.wellpoint.mde.serviceImpl.AbstractServiceImpl;
import com.wellpoint.mde.serviceImpl.BusEntityServiceImpl;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class BusEntityGeneralHelper extends AbstractHelper<BusEnum>{
	
	List<MemAttrRow> busBTAXMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> busBCNTCMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> busBUSMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> busALTSRCIDMemAttrList = new ArrayList<MemAttrRow>();
	
	BusEntityService busEntityService = new BusEntityServiceImpl();
	
	public BusEntityGeneralHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
	}
	
	private void initialize() {
		((AbstractSegment) busEntityService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) busEntityService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) busEntityService).setOutDataList(getOutDataList());
		((AbstractSegment) busEntityService).setSrcCodesDelimited(srcCodesDelimited);
		((AbstractServiceImpl) busEntityService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
	}
	
	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof EntXeia) {
				generateEIDSegment(memRow, entRecNum);
			}
			if(memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				BusEnum attrCode = BusEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
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
					logInfo(entRecNum + e.toString());
				}
			}
		}
		buildOtherSegments(entRecNum);
	}

	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		
		outputType = BusEnum.BTAX.getValue();
		segmentData = busEntityService.buildBTAXSegment(busBTAXMemAttrList, entRecNum);
		generateRow();
		
		outputType = BusEnum.BCNTC.getValue();
		segmentData = busEntityService.buildBCNTCSegment(busBCNTCMemAttrList, entRecNum);
		generateRow();
		
		outputType = BusEnum.BUS.getValue();
		segmentData = busEntityService.buildBUSSegment(busBUSMemAttrList, entRecNum);
		generateRow();
		
		segmentDataSet = busEntityService.buildALTSRCIDSegment(busALTSRCIDMemAttrList, entRecNum);
		generateSegments(segmentDataSet, BusEnum.ALTSRCID.getValue());
		
	}

	@Override
	protected void generateCompositeSegments(BusEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode){
		case BUSENTTAXID:	busBTAXMemAttrList.add((MemAttrRow) memRow);
							break;
		
		case BUSENTALTID:	outputType = BusEnum.BALT.getValue();
							segmentData = busEntityService.getSegmentDataforBALT(memRow, entRecNum);
							generateRow();
							break;
							
		case BUSENTADDR:	if(getString(memRow, "addrtype").length()>0 
							&& ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt").length()>0 
							&& ExtMemgetIxnUtils.getDateAsString(memRow, "termdt").length()>0 
							&& getString(memRow, "stline1").length()>0 
						    && getString(memRow, "city").length()>0 
							&& getString(memRow, "state").length()>0  && getString(memRow, "zipcode").length()>0 ) {
								outputType = BusEnum.BADR.getValue();
								segmentData = busEntityService.getSegmentDataforBADR(memRow, entRecNum);
								generateRow();
							}
							busBCNTCMemAttrList.add((MemAttrRow) memRow);
							break;
							
		case BUSENTCNTRCT:	if(getString(memRow,"taxid").length()>0) {
								outputType = BusEnum.BECON.getValue();
								segmentData = busEntityService.getSegmentDataforBECON(memRow, entRecNum);
								generateRow();
							}
							break;
							
		case BUSENTNAME:
		case BUSENTTYPE:
		case BUSENTSTATUS:	busBUSMemAttrList.add((MemAttrRow) memRow);
							if(isEPDSV2_Flag()) {
								busALTSRCIDMemAttrList.add((MemAttrRow) memRow);
							}
							break;
							
		case RSKBEARORGNM:
		case BUSENTSVCTAB:	busBUSMemAttrList.add((MemAttrRow) memRow);
							break;
							
		case BUFEDTAX1099:	busBUSMemAttrList.add((MemAttrRow) memRow);
							busBTAXMemAttrList.add((MemAttrRow) memRow);
							break;
							
		case BUSENTADRCNT:	busBCNTCMemAttrList.add((MemAttrRow) memRow);
							break;
							
		case BUSENTGRPREL:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "elemdesc")) &&
								ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")) &&
								ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))) {
									outputType = BusEnum.BUSGRP.getValue();
									segmentData = busEntityService.getSegmentDataforBUSGRP(memRow, entRecNum);
									generateRow();
							}
							break;
		}
	}

	@Override
	protected void generateSourceSegments(BusEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode){
		case BUSENTADDR:	outputType = BusEnum.APADR.getValue();
							segmentData = busEntityService.getSegmentDataforAPADR(memRow, entRecNum);
							generateRow();
							break;
							
		case BUSENTSTATUS:
		case BUSENTTYPE:
		case BUSENTNAME:	busALTSRCIDMemAttrList.add((MemAttrRow) memRow);
							break;
							
		
							
		}		
	}

}
