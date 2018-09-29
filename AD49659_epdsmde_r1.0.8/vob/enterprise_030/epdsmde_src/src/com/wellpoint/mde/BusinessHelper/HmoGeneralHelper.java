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
import com.wellpoint.mde.constants.HmoEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.service.HmoService;
import com.wellpoint.mde.serviceImpl.AbstractServiceImpl;
import com.wellpoint.mde.serviceImpl.HmoServiceImpl;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class HmoGeneralHelper extends AbstractHelper<HmoEnum>{
	
	private List<MemAttrRow> hmoWHMMemAttrList = new ArrayList<MemAttrRow>();
	
	private List<MemAttrRow> hmoALTSRCIDMemAttrList = new ArrayList<MemAttrRow>();
	
	private HmoService hmoService = new HmoServiceImpl();

	public HmoGeneralHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
	}
	
	private void initialize() {
		((AbstractSegment) hmoService).setHm_AudRow(hm_AudRow);
		((AbstractSegment) hmoService).setHm_MemHead(hm_MemHead);
		((AbstractSegment) hmoService).setOutDataList(getOutDataList());
		((AbstractSegment) hmoService).setSrcCodesDelimited(srcCodesDelimited);
		((AbstractServiceImpl) hmoService).setProp_relTypeCode(ExtMemgetIxnUtils.createPropertyForReltypeCode());
	}
	
	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof EntXeia) {
				generateEIDSegment(memRow, entRecNum);
			}
			if(memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				HmoEnum attrCode = HmoEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
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
		
		outputType = HmoEnum.WHM.getValue();
		segmentData = hmoService.buildWHMSegment(hmoWHMMemAttrList, entRecNum);
		generateRow();
		
		outputType = HmoEnum.ALTSRCID.getValue();
		segmentDataSet = hmoService.buildALTSRCIDSegment(hmoALTSRCIDMemAttrList, entRecNum);
		generateSegments(segmentDataSet, HmoEnum.ALTSRCID.getValue());
		
	}

	@Override
	protected void generateCompositeSegments(HmoEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		case HMONAME:
		case HMOSTATUS:	hmoWHMMemAttrList.add((MemAttrRow) memRow);
						if(isEPDSV2_Flag()) {
							hmoALTSRCIDMemAttrList.add((MemAttrRow) memRow);
						}
						break;
						
		case HMOID:
		case HMOSITEPRNT:
		case HMOMNSATTYPE:
		case HMOIPAPMGTYP:
		case PREVPCPSFX:
		case PREVSPCSFX:
		case HMOROUTEREG:
		case HMOSAFENET:
		case HMONETADMIN:
		case HMOCTRCTMGR:
		case HMOPROVSUMID:
		case HMOPMPMIND:
		case AREAZIPTYPCD:
		case HMOSITECOPAY:
		case HMOGRPDBA:
		case HMODATAMANG:hmoWHMMemAttrList.add((MemAttrRow) memRow);
		               break;
						
		case HMOSHAREDMGT:
						outputType = HmoEnum.WSHRMGN.getValue();
						segmentData = hmoService.getSegmentDataforWSHRMGN(memRow, entRecNum);
						generateRow();
						break;
						
		case HMOMEMREF:	outputType = HmoEnum.WMBREF.getValue();
						segmentData = hmoService.getSegmentDataforWMBREF(memRow, entRecNum);
						generateRow();
						break;
						
		case HMOSVCAREA: if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "attrval"))){
							outputType = HmoEnum.WSVAREA.getValue();
							segmentData = hmoService.getSegmentDataforWSVAREA(memRow, entRecNum);
							generateRow();
						}
						break;

	case HMOWPDM: if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "attrval"))){
							outputType = HmoEnum.WPDM.getValue();
							segmentData = hmoService.getSegmentDataforWSVAREA(memRow, entRecNum);
							generateRow();
						}
						break;

						
		case HMOTAXID:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "idnumber")) &&
							ExtMemgetIxnUtils.isNotEmpty(getString(memRow,"idtype"))) {
								outputType = HmoEnum.WTAX.getValue();
								segmentData = hmoService.getSegmentDataforWTAX(memRow, entRecNum);
								generateRow();
						}
						break;
						
		case HMOSCHED:	outputType = HmoEnum.WSCH.getValue();
						segmentData = hmoService.getSegmentDataforWSCH(memRow, entRecNum);
						generateRow();
						break;
						
		case HMOSVCTAB: outputType = HmoEnum.WSVC.getValue();
						segmentData = hmoService.getSegmentDataforWSVC(memRow, entRecNum);
						generateRow();
						break;
						
		case HMOENRPROT: outputType = HmoEnum.WENR.getValue();
						segmentData = hmoService.getSegmentDataforWENR(memRow, entRecNum);
						generateRow();
						break;
						
		case HMOMEMROL: if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "hmorolltranseqno"))){
							outputType = HmoEnum.WROL.getValue();
							segmentData = hmoService.getSegmentDataforWROL(memRow, entRecNum);
							generateRow();
						}
						break;
						
		case HMOADDR:	if(getString(memRow, "md5key").length() > 0 
							&& getString(memRow, "addrtype").length() > 0 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"))  
							&& getString(memRow, "zipcode").length() > 0) {
								outputType = HmoEnum.WADR.getValue();
								segmentData = hmoService.getSegmentDataforWADR(memRow, entRecNum);
								generateRow();
						}
						break;
		
		case HMOADRCNT: outputType = HmoEnum.WCNTC.getValue();
						segmentData = hmoService.getSegmentDataforWCNTC(memRow, entRecNum);
						generateRow();
						break;
						
		case HMONOTE:	outputType = HmoEnum.WNOTE.getValue();
						segmentData = hmoService.getSegmentDataforWNOTE(memRow, entRecNum);
						generateRow();
						break;
						
		case HMOWREL:	if(getString(memRow,"relmemidnum").length()>0 && getString(memRow,"reltype").length()>0 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"))
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"termdt"))) {
								outputType = HmoEnum.WREL.getValue();
								segmentData = hmoService.getSegmentDataforWREL(memRow, entRecNum);
								generateRow();
						}
						break;
		
		case HMOCNTREL:	if(getString(memRow, "hmocntrctcd").length()>0 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt")) 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"hmocntrcttermdt"))) {
								outputType = HmoEnum.WCON.getValue();
								segmentData = hmoService.getSegmentDataforWCON(memRow, entRecNum);
								generateRow();
						}
						break;
		
		//WNET
		case HMONET:	if(getString(memRow, "hmocntrctcd").length()>0 
				&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt")) 
				&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"hmocntrcttermdt"))) {
					outputType = HmoEnum.WNET.getValue();
					segmentData = hmoService.getSegmentDataforWNET(memRow, entRecNum);
					generateRow();
			}
			break;
						
		case HMOALTID:
		case NPIHMO:	outputType = HmoEnum.WALT.getValue();
						segmentData = hmoService.getSegmentDataforWALT(memRow, entRecNum);
						generateRow();
						break;
						
		case HMOREGN:	if(getString(memRow, "hzipuptpcd").length()>0  //Making this field optional for WLPRD00874608(Sev2) -Issue solved in Aug
							&& getString(memRow, "regiontypecd").length()>0 
							&& getString(memRow, "regionid").length()>0
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow, "regioneffdt")) 
							&& ExtMemgetIxnUtils.isNotEmpty(ExtMemgetIxnUtils.getDateAsString(memRow,"regiontrmdt"))) {
								outputType = HmoEnum.WRGN.getValue();
								segmentData = hmoService.getSegmentDataforWRGN(memRow, entRecNum);
								generateRow();
						}
						break;
		}
	}
	
	@Override
	protected void generateSourceSegments(HmoEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		switch(attrCode) {
		case HMOSTATUS:	
		case HMONAME:	hmoALTSRCIDMemAttrList.add((MemAttrRow) memRow);
						break;
		}
	}
}
