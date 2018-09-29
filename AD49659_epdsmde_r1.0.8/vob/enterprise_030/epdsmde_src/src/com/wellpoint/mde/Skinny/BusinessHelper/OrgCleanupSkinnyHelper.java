package com.wellpoint.mde.Skinny.BusinessHelper;

import java.util.ArrayList;
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
import com.wellpoint.mde.service.OrgCleanupSkinnyService;
import com.wellpoint.mde.serviceImpl.OrgSkinnyServiceImpl;
import com.wellpoint.mde.utils.EntityProperties;

public class OrgCleanupSkinnyHelper extends AbstractHelper<OrgEnum>{

	private OrgCleanupSkinnyService orgService = new OrgSkinnyServiceImpl();
	
	List<MemAttrRow> OrgPALTMemAttrList = new ArrayList<MemAttrRow>();

	List<MemAttrRow> OrgPREMMemAttrList = new ArrayList<MemAttrRow>();
	
	public OrgCleanupSkinnyHelper(HashMap<String, String[]> hm_AudRow,
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
	
	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		String srccode = "";
		for (MemRow memRow : outMemList.listToArray()){
			if (memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode().toUpperCase();
				OrgEnum attrCode = OrgEnum.getInitiateEnum(l_strAttrCode);
				srccode = memRow.getSrcCode();
				try{
					if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
							((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")
							&& (getEntityProp().get("QCARE").equalsIgnoreCase(srccode)
									|| getEntityProp().get("EPDSV2").equalsIgnoreCase(srccode))) {
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
		
		segmentDataSet = orgService.buildPALTSegment(OrgPALTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.PALT.getValue());
		
		segmentDataSet = orgService.buildPREMSegment(OrgPREMMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.PREM.getValue());
	}

	@Override
	protected void generateCompositeSegments(OrgEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		
	}

	@Override
	protected void generateSourceSegments(OrgEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
			switch(attrCode){
			case REMITSEG:
			case REMITSEGDTL:
							OrgPREMMemAttrList.add((MemAttrRow) memRow);
							break;
							
			case LICENSEORG:
			case ENCLRTYIDORG:
			case MEDICAIDORG:
			case MEDICAREORG:
			case NPIORG:
			case UPINORG:
			case ALTSYSIDSORG:
							OrgPALTMemAttrList.add((MemAttrRow) memRow);
							break;
				
		}
	}

}
