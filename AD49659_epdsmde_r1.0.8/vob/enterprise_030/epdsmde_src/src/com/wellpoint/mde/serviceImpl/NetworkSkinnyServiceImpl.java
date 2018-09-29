package com.wellpoint.mde.serviceImpl;
import java.util.HashMap;
import java.util.List;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.NetEnum;
import com.wellpoint.mde.service.NetworkSkinnyService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;


public class NetworkSkinnyServiceImpl extends AbstractServiceImpl implements NetworkSkinnyService{
	private String trimmedMemidnum = strBlank;
	public String getTrimmedMemidnum() {
		return trimmedMemidnum;
	}

	public void setTrimmedMemidnum(String trimmedMemidnum) {
		this.trimmedMemidnum = trimmedMemidnum;
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
								segmentData=ExtMemgetIxnUtils.appendStr(outputType,getCDCString(memRow, "DFCDC_evtctime"), Long.toString(entRecNum),
								(getTrimmedMemidnum() == "" ? l_memIdNum : getTrimmedMemidnum()), srcCode_postprocess+"NET",strBlank); 
						
								hm_MNET.put("MEMHEAD", segmentData);
								segmentData = getString(memRow, "ATTRVAL") + strDelim;
							    hm_MNET.put("NETNAME", segmentData);
							}
						    break;
						    
			case NETIDCODE:segmentData = getString(memRow, "codeval") + strDelim;
			               hm_MNET.put("NETIDCODE", segmentData);
		                    break;
						    
			case NETLEGNAME:segmentData = ExtMemgetIxnUtils.appendStr(getString(memRow, "codedesc"), getString(memRow, "codeval"),strBlank) ;
					       	hm_MNET.put("NETLEGNAME", segmentData);
					       	break;
						    
			case NETLVLTYPE:segmentData = getString(memRow, "attrval") + strDelim;
						    hm_MNET.put("NETLVLTYPE", segmentData);
						    break;
						    
			case NETOWNSRC: segmentData = getString(memRow, "attrval") + strDelim + strBlank + strDelim;
						    hm_MNET.put("NETOWNSRC", segmentData);
						    break;
			//iiiii			    
		/*	case NETIDCODE:	segmentData=getString(memRow, "attrval") + strDelim;
						    hm_MNET.put("NETIDCODE", segmentData);
						    break;*/
						    
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
						    segmentData =ExtMemgetIxnUtils.appendStr(ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt"),ExtMemgetIxnUtils.getDateAsString(memRow,"termdt"),
							 getString(memRow, "termrsn"), strBlank) ;
							hm_MNET.put("NETSTATUS", segmentData);
							break;
			     			
			case NETPARIND:
					 		segmentData = getString(memRow,"attrval")/* strDelim + getString(memRow, "DFCDC_evtinitiator")
					 		+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim;*/+ strDelim ;
					 		hm_MNET.put("NETPARIND", segmentData);
					 		break;
		 		
			case NETSTATCODE:
							segmentData =getString(memRow,"attrval")/*+ strDelim + srcCodesDelimited*/;
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
		if( null != hm_MNET.get("NETIDCODE") || null != hm_MNET.get("NETNAME") || null != hm_MNET.get("NETLEGNAME") 
				|| null != hm_MNET.get("NETLVLTYPE") || null != hm_MNET.get("NETOWNSRC")  
				|| null != hm_MNET.get("PCPNETTYPCD") || null != hm_MNET.get("NATCTCTIND") || null != hm_MNET.get("NETAGREETYPE")
				|| null != hm_MNET.get("NETCNTRCTST") || null != hm_MNET.get("NETRENTIND") || null != hm_MNET.get("NETRSTCTTYPE") 
				|| null != hm_MNET.get("NETFOCUSTYPE") || null != hm_MNET.get("NETPRODTYPE") || null != hm_MNET.get("NETSTATUS")
				|| null != hm_MNET.get("NETSTATCODE") || null != hm_MNET.get("NETPARIND") || null != hm_MNET.get("NETSTALIND")){
		 
		 if(null==hm_MNET.get("MEMHEAD"))
		 {
			 segmentData = outputType+ strDelim + strBlank + strDelim + entRecNum + strDelim +
			 //if NETWORKID attribute is not present setting memidnum from memhead for old data without appended by idtype.
				(getTrimmedMemidnum() == "" ? l_memIdNum_common : getTrimmedMemidnum())+ strDelim + 
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
	 if (null == hm_MNET.get("NETIDCODE")) hm_MNET.put("NETIDCODE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETLEGNAME")) hm_MNET.put("NETLEGNAME", ExtMemgetIxnUtils.getNDelimiters(2));
	 if (null == hm_MNET.get("NETLVLTYPE")) hm_MNET.put("NETLVLTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETOWNSRC")) hm_MNET.put("NETOWNSRC", ExtMemgetIxnUtils.getNDelimiters(2));
	 if (null == hm_MNET.get("PCPNETTYPCD")) hm_MNET.put("PCPNETTYPCD", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NATCTCTIND")) hm_MNET.put("NATCTCTIND", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETAGREETYPE")) hm_MNET.put("NETAGREETYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETCNTRCTST")) hm_MNET.put("NETCNTRCTST", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETRENTIND")) hm_MNET.put("NETRENTIND", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETRSTCTTYPE")) hm_MNET.put("NETRSTCTTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETFOCUSTYPE")) hm_MNET.put("NETFOCUSTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETPRODTYPE")) hm_MNET.put("NETPRODTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETSTATUS")) hm_MNET.put("NETSTATUS", ExtMemgetIxnUtils.getNDelimiters (3));
	 if (null == hm_MNET.get("NETPARIND")) hm_MNET.put("NETPARIND", "0" +strDelim );
	 if (null == hm_MNET.get("NETSTALIND")) hm_MNET.put("NETSTALIND",ExtMemgetIxnUtils.getNDelimiters(1));
	 if (null == hm_MNET.get("NETSTATCODE")) hm_MNET.put("NETSTATCODE",strBlank);
	 
	
	 segmentData = hm_MNET.get("MEMHEAD")+  hm_MNET.get("NETIDCODE") + hm_MNET.get("NETNAME") + hm_MNET.get("NETLEGNAME") + hm_MNET.get("NETLVLTYPE") 
	 + hm_MNET.get("NETOWNSRC") + hm_MNET.get("PCPNETTYPCD") + hm_MNET.get("NATCTCTIND") 
	 + hm_MNET.get("NETAGREETYPE") + hm_MNET.get("NETCNTRCTST") +  hm_MNET.get("NETRENTIND") + hm_MNET.get("NETRSTCTTYPE")
	 + hm_MNET.get("NETFOCUSTYPE") +  hm_MNET.get("NETPRODTYPE") +  hm_MNET.get("NETSTATUS") +hm_MNET.get("NETPARIND") 
	 + hm_MNET.get("NETSTATCODE") + hm_MNET.get("NETSTALIND");;
		if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0) {
			return segmentData;
		}
	}
	return strBlank;
	}
	

}
