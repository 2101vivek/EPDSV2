package com.wellpoint.mde.BusinessHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import madison.mpi.EntXeia;
import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;
import com.wellpoint.mde.constants.RmbEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.RmbService;
import com.wellpoint.mde.serviceImpl.AbstractServiceImpl;
import com.wellpoint.mde.serviceImpl.RmbServiceImpl;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class RmbGeneralHelper extends AbstractHelper<RmbEnum>{
	
	private String trimmedMemidnum = strBlank;

	private String l_memIdNum_common = strBlank;

	private String l_strSrcCd_common = strBlank;
	
	boolean l_memHeadStatus = false;
	
	private RmbService rmbService = new RmbServiceImpl();
	
	List<MemAttrRow> rmbMRMBMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> rmbMRSVCMemAttrList = new ArrayList<MemAttrRow>();
	
	public String getTrimmedMemidnum() {
		return trimmedMemidnum;
	}

	public void setTrimmedMemidnum(String trimmedMemidnum) {
		this.trimmedMemidnum = trimmedMemidnum;
	}
	
	public RmbGeneralHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
	}
	
	private void initialize() {
		((AbstractSegment) rmbService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) rmbService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) rmbService).setOutDataList(getOutDataList());
		((AbstractSegment) rmbService).setSrcCodesDelimited(srcCodesDelimited);
		((RmbServiceImpl)rmbService).setTrimmedMemidnum(getTrimmedMemidnum());
		((AbstractServiceImpl) rmbService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
	}

	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof EntXeia) {
				generateEIDSegment(memRow, entRecNum);
			}
			if(memRow instanceof MemAttrRow) {
				if (!l_memHeadStatus) {
					l_memIdNum_common = memRow.getMemHead().getMemIdnum();
					l_strSrcCd_common = memRow.getMemHead().getSrcCode();
					l_memHeadStatus = true;
				}
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				RmbEnum attrCode = RmbEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
				try{
					if (((MemAttrRow)memRow).getInt("rowInd") != 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
						generateCompositeSegments(attrCode, memRow, entRecNum);
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
		outputType = RmbEnum.MRMB.getValue();
		segmentData = rmbService.buildMRMBSegment(rmbMRMBMemAttrList, entRecNum, l_memIdNum_common, l_strSrcCd_common);
		generateRow();
		
		outputType = RmbEnum.MRSVC.getValue();
		segmentData = rmbService.buildMRSVCSegment(rmbMRSVCMemAttrList, entRecNum);
		generateRow();
	}

	@Override
	protected void generateCompositeSegments(RmbEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		case RMBARNGTYPE:	rmbMRMBMemAttrList.add((MemAttrRow) memRow);
							rmbMRSVCMemAttrList.add((MemAttrRow) memRow);
							break;
							
		case RMBARNGNAME:
		case RMBCAPTYPE:	
		case RMBSTATUS:		
		case RMBNETPAR:		
		case RMBFILEDAYS:	
		case RMBTIER:	
		case RMBGRPIND:		
		case RMBINCEXCL:	
		case RMBPAR:	
		case RMBNETCOV:	
		case PNLCMPCD:	
		case PNLOONIND:	
		case PDTCOMPCODE:	
		case NMSCTRTIND:	
		case WMSCTRTIND:	
		case SSBCTRCTIND:	
		case CNTPRVTYP:		rmbMRMBMemAttrList.add((MemAttrRow) memRow);
							break;
							
		case RMBCAPSVC:		rmbMRSVCMemAttrList.add((MemAttrRow) memRow);
							break;
							
		
		}
		
	}

	@Override
	protected void generateSourceSegments(RmbEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
