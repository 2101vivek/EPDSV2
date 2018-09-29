package com.wellpoint.mde.serviceImpl;

import java.util.HashMap;
import java.util.List;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.NetEnum;
import com.wellpoint.mde.service.NetworkService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class NetworkServiceImpl extends AbstractServiceImpl implements NetworkService{
	
	private String trimmedMemidnum = strBlank;
	
	public String getTrimmedMemidnum() {
		return trimmedMemidnum;
	}

	public void setTrimmedMemidnum(String trimmedMemidnum) {
		this.trimmedMemidnum = trimmedMemidnum;
	}

	@Override
	public String getSegmentDataforMNTRL(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		getMemHeadValues(memRow);
		outputType = NetEnum.MNTRL.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + 
		(getTrimmedMemidnum() == "" ? l_memIdNum : getTrimmedMemidnum())
		+ strDelim + "EPDS V2" + strDelim 
		+ getString(memRow, "relattrval1") + strDelim 
		+ l_strSrcCd + strDelim + getString(memRow, "relmemidnum") 
		+ strDelim + getString(memRow, "relattrval2")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
		+ strDelim + getString(memRow, "termrsn")
		+ strDelim + getString(memRow, "reltype") + ExtMemgetIxnUtils.getNDelimiters(30)
		+ strDelim + srcCodesDelimited; ;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforMNTRM(MemRow memRow, long entRecNum)
	throws Exception {
		segmentData = strBlank;
		getMemHeadValues(memRow);
		outputType = NetEnum.MNTRM.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + 
		(getTrimmedMemidnum() == "" ? l_memIdNum : getTrimmedMemidnum())
		+ strDelim + "EPDS V2"  
		+ strDelim + /*l_strSrcCd*//*CQ WLPRD01012072*/srcCode_postprocess +"RMB" + strDelim + getString(memRow, "relmemidnum") + strDelim + getString(memRow, "relmemtype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relidenteffectdt") + strDelim 
		+ /*l_strSrcCd*//*CQ WLPRD01012072*/srcCode_postprocess +"RMB" + strDelim 
		+ getString(memRow, "relattrval1") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn")+ExtMemgetIxnUtils.getNDelimiters(30) +strDelim + srcCodesDelimited; ;
         return segmentData;
	
	}
	
	@Override
	public String buildMNETSegment(List<MemAttrRow> netMNETMemAttrList,
			long entRecNum, String l_memIdNum_common, String l_strSrcCd_common) throws Exception {
		outputType = NetEnum.MNET.getValue();
		HashMap<String, String> hm_MNET = new HashMap<String, String>();
		for (MemRow memRow : netMNETMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			NetEnum attrCode = NetEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case NETNAME:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "ATTRVAL"))) {
								segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + 
								//if NETWORKID attribute is not present setting memidnum from memhead for old data without appended by idtype.
								(getTrimmedMemidnum() == "" ? l_memIdNum : getTrimmedMemidnum())
								+  strDelim + "EPDS V2" + strDelim +  srcCode_postprocess + "NET"  + strDelim;
								hm_MNET.put("MEMHEAD", segmentData);
							
								segmentData = getString(memRow, "ATTRVAL") + strDelim;
							    hm_MNET.put("NETNAME", segmentData);
							}
						    break;
						    
			case NETLEGNAME:segmentData = getString(memRow, "codedesc") + strDelim + getString(memRow, "codeval") + strDelim ;
						    hm_MNET.put("NETLEGNAME", segmentData);
						    break;
						    
			case NETLVLTYPE:segmentData = getString(memRow, "attrval") + strDelim;
						    hm_MNET.put("NETLVLTYPE", segmentData);
						    break;
						    
			case NETOWNSRC: segmentData = getString(memRow, "attrval") + strDelim;
						    hm_MNET.put("NETOWNSRC", segmentData);
						    break;
						    
			case NETIDCODE:	segmentData=getString(memRow, "codeval") + strDelim;
						    hm_MNET.put("NETIDCODE", segmentData);
						    break;
						    
			case  PCPNETTYPCD:
						    segmentData = getString(memRow, "attrval") + strDelim;
						    hm_MNET.put("PCPNETTYPCD", segmentData);
						    break;
						    
			case  NATCTCTIND:
						    segmentData = getString(memRow, "attrval") + strDelim;
						    hm_MNET.put("NATCTCTIND", segmentData);
						    break;
						    
			case  NETAGREETYPE:
			     			segmentData = getString(memRow, "codeval") + strDelim;
						    hm_MNET.put("NETAGREETYPE", segmentData);
						    break;
						    
			case NETCNTRCTST:
						    segmentData = getString(memRow, "codeval") + strDelim;
						    hm_MNET.put("NETCNTRCTST", segmentData);
						    break;
						    
			case NETRENTIND:
			     			segmentData = getString(memRow, "attrval") + strDelim;
			     			hm_MNET.put("NETRENTIND", segmentData);
			     			break;
			     			
			case  NETRSTCTTYPE:
			     			segmentData = getString(memRow, "codeval") + strDelim;
			     			hm_MNET.put("NETRSTCTTYPE", segmentData);
			     			break;
			     			
			case  NETFOCUSTYPE:
				     		segmentData = getString(memRow, "codeval") + strDelim;
				     		hm_MNET.put("NETFOCUSTYPE", segmentData);
				     		break;
				     		
			case NETPRODTYPE:
							segmentData = getString(memRow, "codeval") + strDelim;
						    hm_MNET.put("NETPRODTYPE", segmentData);
						    break;
						    
			case NETSTATUS:
						    segmentData =ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"termdt")
							+ strDelim + getString(memRow, "termrsn") + strDelim ;
							hm_MNET.put("NETSTATUS", segmentData);
							break;
							
			case NETPARIND:
					 		segmentData = getString(memRow,"attrval") + strDelim + getString(memRow, "DFCDC_evtinitiator")
					 		+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim;
					 		hm_MNET.put("NETPARIND", segmentData);
					 		break;
					 		
			case NETSTATCODE:
							segmentData =getString(memRow,"attrval") +strDelim/*+ strDelim + srcCodesDelimited*/;
							hm_MNET.put("NETSTATCODE", segmentData);
							break;
			case NETSTALIND:
				            segmentData =getString(memRow,"CodeVal")/*+ strDelim + srcCodesDelimited*/;
				            hm_MNET.put("NETSTALIND", segmentData);
				            break;
				          
							
			}
		}
		return generateSegmentsForMNET(hm_MNET, entRecNum, l_memIdNum_common, l_strSrcCd_common);
	}
	
	
	private String generateSegmentsForMNET(HashMap<String, String> hm_MNET,
			long entRecNum, String l_memIdNum_common, String l_strSrcCd_common) {
		outputType = NetEnum.MNET.getValue();
		if(null != hm_MNET.get("NETNAME")) {
		if(null != hm_MNET.get("NETNAME") || null != hm_MNET.get("NETLEGNAME") 
				|| null != hm_MNET.get("NETLVLTYPE") || null != hm_MNET.get("NETOWNSRC") || null != hm_MNET.get("NETIDCODE") 
				|| null != hm_MNET.get("PCPNETTYPCD") || null != hm_MNET.get("NATCTCTIND") || null != hm_MNET.get("NETAGREETYPE")
				|| null != hm_MNET.get("NETCNTRCTST") || null != hm_MNET.get("NETRENTIND") || null != hm_MNET.get("NETRSTCTTYPE") 
				|| null != hm_MNET.get("NETFOCUSTYPE") || null != hm_MNET.get("NETPRODTYPE") || null != hm_MNET.get("NETSTATUS")
				|| null != hm_MNET.get("NETSTATCODE") || null != hm_MNET.get("NETPARIND") || null != hm_MNET.get("NETSTALIND") ){
		 
		 if(null==hm_MNET.get("MEMHEAD"))
		 {
			 segmentData = outputType+ strDelim + strBlank + strDelim +
			 //if NETWORKID attribute is not present setting memidnum from memhead for old data without appended by idtype.
				(getTrimmedMemidnum() == "" ? l_memIdNum_common : getTrimmedMemidnum())+ strDelim +"EPDS V2"+ strDelim + 
				ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd_common) + "NET" + strDelim;
				hm_MNET.put("MEMHEAD", segmentData);
		 }
		 if (null == hm_MNET.get("NETNAME")){
				outputType = "MNET";
				hm_MNET.put("NETNAME", ExtMemgetIxnUtils.getNDelimiters(1));
				}
	 	}
		 else{
			 hm_MNET.put("MEMHEAD", ExtMemgetIxnUtils.getNDelimiters(5));	
			 hm_MNET.put("NETNAME", ExtMemgetIxnUtils.getNDelimiters(1));	
			}
	 if (null == hm_MNET.get("NETLEGNAME")) hm_MNET.put("NETLEGNAME", ExtMemgetIxnUtils.getNDelimiters(2));
	 if (null == hm_MNET.get("NETLVLTYPE")) hm_MNET.put("NETLVLTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETOWNSRC")) hm_MNET.put("NETOWNSRC", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETIDCODE")) hm_MNET.put("NETIDCODE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("PCPNETTYPCD")) hm_MNET.put("PCPNETTYPCD", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NATCTCTIND")) hm_MNET.put("NATCTCTIND", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETAGREETYPE")) hm_MNET.put("NETAGREETYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETCNTRCTST")) hm_MNET.put("NETCNTRCTST", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETRENTIND")) hm_MNET.put("NETRENTIND", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETRSTCTTYPE")) hm_MNET.put("NETRSTCTTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETFOCUSTYPE")) hm_MNET.put("NETFOCUSTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETPRODTYPE")) hm_MNET.put("NETPRODTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETSTATUS")) hm_MNET.put("NETSTATUS", ExtMemgetIxnUtils.getNDelimiters (3) /*(5) + "0" +strDelim */);
	 if (null == hm_MNET.get("NETPARIND")) hm_MNET.put("NETPARIND", ExtMemgetIxnUtils.getNDelimiters(3)+ "0" +strDelim );
	 if (null == hm_MNET.get("NETSTATCODE")) hm_MNET.put("NETSTATCODE",ExtMemgetIxnUtils.getNDelimiters(1)/*+ strDelim + srcCodesDelimited*/);
	 if (null == hm_MNET.get("NETSTALIND")) hm_MNET.put("NETSTALIND",ExtMemgetIxnUtils.getNDelimiters(0));
	
	 segmentData = hm_MNET.get("MEMHEAD")+  hm_MNET.get("NETNAME") + hm_MNET.get("NETLEGNAME") + hm_MNET.get("NETLVLTYPE") 
	 + hm_MNET.get("NETOWNSRC") + hm_MNET.get("NETIDCODE") + hm_MNET.get("PCPNETTYPCD") + hm_MNET.get("NATCTCTIND") 
	 + hm_MNET.get("NETAGREETYPE") + hm_MNET.get("NETCNTRCTST") +  hm_MNET.get("NETRENTIND") + hm_MNET.get("NETRSTCTTYPE")
	 + hm_MNET.get("NETFOCUSTYPE") +  hm_MNET.get("NETPRODTYPE") +  hm_MNET.get("NETSTATUS") +hm_MNET.get("NETPARIND") 
	 + hm_MNET.get("NETSTATCODE") + hm_MNET.get("NETSTALIND");
		if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0) {
			segmentData = segmentData +ExtMemgetIxnUtils.getNDelimiters(29)+strDelim + srcCodesDelimited ;
			return segmentData;
		}
	}
	return strBlank;
	}
	
}
