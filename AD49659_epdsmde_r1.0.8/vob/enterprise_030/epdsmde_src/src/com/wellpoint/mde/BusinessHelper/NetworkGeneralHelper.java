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
import com.wellpoint.mde.constants.NetEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.NetworkService;
import com.wellpoint.mde.serviceImpl.AbstractServiceImpl;
import com.wellpoint.mde.serviceImpl.NetworkServiceImpl;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class NetworkGeneralHelper extends AbstractHelper<NetEnum>{
	
	NetworkService networkService = new NetworkServiceImpl();
	
	List<MemAttrRow> netMNETMemAttrList = new ArrayList<MemAttrRow>();

	private String trimmedMemidnum = strBlank;

	private String l_memIdNum_common = strBlank;

	private String l_strSrcCd_common = strBlank;
	
	boolean l_memHeadStatus = false;
	
	public String getTrimmedMemidnum() {
		return trimmedMemidnum;
	}

	public void setTrimmedMemidnum(String trimmedMemidnum) {
		this.trimmedMemidnum = trimmedMemidnum;
	}

	public NetworkGeneralHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
	}
	
	private void initialize() {
		((AbstractSegment) networkService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) networkService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) networkService).setOutDataList(getOutDataList());
		((AbstractSegment) networkService).setSrcCodesDelimited(srcCodesDelimited);
		((NetworkServiceImpl)networkService).setTrimmedMemidnum(getTrimmedMemidnum());
		((AbstractServiceImpl) networkService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
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
				NetEnum attrCode = NetEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
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
		outputType = NetEnum.MNET.getValue();
		segmentData = networkService.buildMNETSegment(netMNETMemAttrList, entRecNum, l_memIdNum_common, l_strSrcCd_common);
		generateRow();
	}

	@Override
	protected void generateCompositeSegments(NetEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		case NETPRNTREL:if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "relmemidnum")) &&
							ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "reltype"))) {
								outputType = NetEnum.MNTRL.getValue();
								segmentData = networkService.getSegmentDataforMNTRL(memRow, entRecNum);
								generateRow();
						}
						break;
							
		case NETRMBREL:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "relmemidnum")) &&
							ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "relmemtype"))) {
								outputType = NetEnum.MNTRM.getValue();
								segmentData = networkService.getSegmentDataforMNTRM(memRow, entRecNum);
								generateRow();
						}
						break;
						
		case NETNAME:
		case NETLEGNAME:
		case NETLVLTYPE:
		case NETOWNSRC:
		case NETIDCODE:
		case PCPNETTYPCD:
		case NATCTCTIND:
		case NETAGREETYPE:
		case NETCNTRCTST:
		case NETRENTIND:
		case NETRSTCTTYPE:
		case NETFOCUSTYPE:
		case NETPRODTYPE:
		case NETSTATUS:
		case NETPARIND:
		case NETSTATCODE:
		case NETSTALIND:
						netMNETMemAttrList.add((MemAttrRow) memRow);
						break;
						
		
		}
	}

	@Override
	protected void generateSourceSegments(NetEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		
	}

}
