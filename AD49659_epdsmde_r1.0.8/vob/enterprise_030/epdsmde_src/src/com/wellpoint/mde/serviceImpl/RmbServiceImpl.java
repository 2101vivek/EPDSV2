package com.wellpoint.mde.serviceImpl;

import java.util.HashMap;
import java.util.List;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.RmbEnum;
import com.wellpoint.mde.service.RmbService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class RmbServiceImpl extends AbstractServiceImpl implements RmbService {
	
	private String trimmedMemidnum = strBlank;
	
	public String getTrimmedMemidnum() {
		return trimmedMemidnum;
	}

	public void setTrimmedMemidnum(String trimmedMemidnum) {
		this.trimmedMemidnum = trimmedMemidnum;
	}
	
	@Override
	public String buildMRMBSegment(List<MemAttrRow> rmbMRMBMemAttrList,
			long entRecNum, String l_memIdNum_common, String l_strSrcCd_common) throws Exception {
		outputType = RmbEnum.MRMB.getValue();
		HashMap<String, String> hm_MRMB = new HashMap<String, String>();
		for (MemRow memRow : rmbMRMBMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			RmbEnum attrCode = RmbEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case RMBARNGTYPE:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow , "codeval"))){
									segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + 
									(getTrimmedMemidnum() == "" ? l_memIdNum : getTrimmedMemidnum())
									+ strDelim + "EPDS V2" + strDelim + srcCode_postprocess + "RMB" + strDelim;
									hm_MRMB.put("MEMHEAD",segmentData);
								
									segmentData = getString(memRow , "codeval") + strDelim ;
									hm_MRMB.put("RMBARNGTYPE",segmentData);
								}
								break;
                 
			case RMBARNGNAME:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow , "attrval"))){
									segmentData = getString(memRow , "attrval") + strDelim;
									hm_MRMB.put("RMBARNGNAME",segmentData);
								}
								break;
				
			case RMBCAPTYPE:	segmentData = getString(memRow , "codeval") + strDelim;
								hm_MRMB.put("RMBCAPTYPE",segmentData);
								break;
				
			case RMBSTATUS:		segmentData = ExtMemgetIxnUtils.getDateAsString(memRow , "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"termdt")
								+ strDelim + getString(memRow, "termrsn") + strDelim;
								hm_MRMB.put("RMBSTATUS_1",segmentData);
			
								segmentData =  getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
								+ strDelim + getString(memRow, "DFCDC_mAudRecno") /*+ strDelim + srcCodesDelimited*/;
								hm_MRMB.put("RMBSTATUS_2", segmentData);
								break;
			
			case RMBNETPAR:		segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("RMBNETPAR",segmentData);
								break;
				
			case RMBFILEDAYS:	segmentData = getString (memRow, "num") + strDelim;
								hm_MRMB.put("RMBFILEDAYS",segmentData);
								break;
				
			case RMBTIER:		segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("RMBTIER",segmentData);
								break;
				
			case RMBGRPIND:		segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("RMBGRPIND",segmentData);
								break;
				
			case RMBINCEXCL:	segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("RMBINCEXCL",segmentData);
								break;
			
			case RMBPAR:		segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("RMBPAR",segmentData);
								break;
				
			case RMBNETCOV:		segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("RMBNETCOV",segmentData);
								break;
				
			case PNLCMPCD:		segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("PNLCMPCD",segmentData);
								break;
				
			case PNLOONIND:		segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("PNLOONIND",segmentData);
								break;
				
			case PDTCOMPCODE:	segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("PDTCOMPCODE",segmentData);
								break;
				
			case NMSCTRTIND:	segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("NMSCTRTIND",segmentData);
								break;
				
			case WMSCTRTIND:	segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("WMSCTRTIND",segmentData);
								break;
				
			case SSBCTRCTIND:	segmentData = getString (memRow, "codeval") + strDelim;
								hm_MRMB.put("SSBCTRCTIND",segmentData);
								break;
				
			case CNTPRVTYP:		segmentData = getString (memRow, "attrval") + strDelim; 
								hm_MRMB.put("CNTPRVTYP",segmentData);
								break;
			}
		}
			return generateSegmentsForMRMB(hm_MRMB, entRecNum, l_memIdNum_common, l_strSrcCd_common);
	}
	
	private String generateSegmentsForMRMB(HashMap<String, String> hm_MRMB,
			long entRecNum, String l_memIdNum_common, String l_strSrcCd_common) throws Exception{
		outputType = RmbEnum.MRMB.getValue();
		if(hm_MRMB.get("RMBARNGTYPE") != null && hm_MRMB.get("RMBARNGNAME") !=null){
			if( hm_MRMB.get("RMBARNGTYPE") != null || hm_MRMB.get("RMBARNGNAME") !=null ||hm_MRMB.get("RMBCAPTYPE") !=null 
					|| hm_MRMB.get("RMBSTATUS_1") != null || hm_MRMB.get("RMBNETPAR")!= null
					|| hm_MRMB.get("RMBFILEDAYS") != null || hm_MRMB.get("RMBTIER") !=null 
					|| hm_MRMB.get("RMBGRPIND") != null || hm_MRMB.get("RMBINCEXCL")!= null
					|| hm_MRMB.get("RMBPAR") != null || hm_MRMB.get("RMBNETCOV") !=null 
					|| hm_MRMB.get("PNLCMPCD") != null || hm_MRMB.get("PNLOONIND")!= null
					|| hm_MRMB.get("RMBSTATUS_2")!= null || hm_MRMB.get("PDTCOMPCODE")!= null || hm_MRMB.get("NMSCTRTIND")!= null || hm_MRMB.get("WMSCTRTIND")!= null || hm_MRMB.get("SSBCTRTIND")!= null || null!= hm_MRMB.get("CNTPRVTYP") )
			{
			
				if (null == hm_MRMB.get("RMBARNGTYPE")){
					segmentData = outputType+ strDelim + strBlank + strDelim +
					(getTrimmedMemidnum() == "" ? l_memIdNum_common : getTrimmedMemidnum())
					+ strDelim +"EPDS V2"+ strDelim 
					+ l_strSrcCd_common + strDelim;
					hm_MRMB.put("MEMHEAD", segmentData);
					hm_MRMB.put("RMBARNGTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
				}
			}
			else{
				hm_MRMB.put("MEMHEAD", ExtMemgetIxnUtils.getNDelimiters(5));
				hm_MRMB.put("RMBARNGTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
			}
			if (null == hm_MRMB.get("RMBARNGNAME"))
				hm_MRMB.put("RMBARNGNAME", ExtMemgetIxnUtils.getNDelimiters(1));
			if (null == hm_MRMB.get("RMBCAPTYPE"))
				hm_MRMB.put("RMBCAPTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("RMBSTATUS_1")) 
				hm_MRMB.put("RMBSTATUS_1", ExtMemgetIxnUtils.getNDelimiters(3));
	
			if (null == hm_MRMB.get("RMBNETPAR")) 
				hm_MRMB.put("RMBNETPAR", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("RMBFILEDAYS"))
				hm_MRMB.put("RMBFILEDAYS", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("RMBTIER")) 
				hm_MRMB.put("RMBTIER", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("RMBGRPIND")) 
				hm_MRMB.put("RMBGRPIND", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("RMBINCEXCL"))
				hm_MRMB.put("RMBINCEXCL", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("RMBPAR")) 
				hm_MRMB.put("RMBPAR", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("RMBNETCOV")) 
				hm_MRMB.put("RMBNETCOV", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("PNLCMPCD"))
				hm_MRMB.put("PNLCMPCD", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("PNLOONIND")) 
				hm_MRMB.put("PNLOONIND", ExtMemgetIxnUtils.getNDelimiters(1));
	
			if (null == hm_MRMB.get("RMBSTATUS_2")) 
				hm_MRMB.put("RMBSTATUS_2", ExtMemgetIxnUtils.getNDelimiters(2) + "0" /*+ strDelim + srcCodesDelimited*/);
			
			if (null == hm_MRMB.get("PDTCOMPCODE")) 
				hm_MRMB.put("PDTCOMPCODE", ExtMemgetIxnUtils.getNDelimiters(1));
				
	        if (null == hm_MRMB.get("NMSCTRTIND")) 
				hm_MRMB.put("NMSCTRTIND", ExtMemgetIxnUtils.getNDelimiters(1));
				
	        if (null == hm_MRMB.get("WMSCTRTIND")) 
				hm_MRMB.put("WMSCTRTIND", ExtMemgetIxnUtils.getNDelimiters(1));
				
	         if (null == hm_MRMB.get("SSBCTRCTIND")) 
				hm_MRMB.put("SSBCTRCTIND", ExtMemgetIxnUtils.getNDelimiters(1)); 	
				
			if(null == hm_MRMB.get("CNTPRVTYP"))
	           hm_MRMB.put("CNTPRVTYP", ExtMemgetIxnUtils.getNDelimiters(1)); 	
	           
			segmentData = hm_MRMB.get("MEMHEAD") + hm_MRMB.get("RMBARNGTYPE") + hm_MRMB.get("RMBARNGNAME") + hm_MRMB.get("RMBCAPTYPE") 
			+hm_MRMB.get("RMBSTATUS_1")+  hm_MRMB.get("RMBNETPAR")+ hm_MRMB.get("RMBFILEDAYS") + hm_MRMB.get("RMBTIER")
			+ hm_MRMB.get("RMBGRPIND") +hm_MRMB.get("RMBINCEXCL")+  hm_MRMB.get("RMBPAR")+ hm_MRMB.get("RMBNETCOV")
			+ hm_MRMB.get("PNLCMPCD") + hm_MRMB.get("PNLOONIND") +hm_MRMB.get("RMBSTATUS_2")
			+ strDelim +  hm_MRMB.get("PDTCOMPCODE") +hm_MRMB.get("NMSCTRTIND") +hm_MRMB.get("WMSCTRTIND") + hm_MRMB.get("SSBCTRCTIND")+ hm_MRMB.put("CNTPRVTYP",segmentData);
			
			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0 ) {
				segmentData = segmentData + ExtMemgetIxnUtils.getNDelimiters(29) + srcCodesDelimited ;
				return segmentData;
			}
		}
		return strBlank;
	}
	
	@Override
	public String buildMRSVCSegment(List<MemAttrRow> rmbMRSVCMemAttrList,
			long entRecNum) throws Exception {
		outputType = RmbEnum.MRSVC.getValue();
		HashMap<String, String> hm_MRSVC = new HashMap<String, String>();
		for (MemRow memRow : rmbMRSVCMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			RmbEnum attrCode = RmbEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case RMBARNGTYPE:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "codeval"))){
									segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + 
									(getTrimmedMemidnum() == "" ? l_memIdNum : getTrimmedMemidnum())
									+ strDelim + "EPDS V2" + strDelim + getString(memRow, "codeval") + strDelim;
									hm_MRSVC.put("RMBARNGTYPE",segmentData);
								}
								break;
								
			case RMBCAPSVC:		if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "attrval"))) {
									segmentData = getString(memRow, "attrval") + strDelim + l_strSrcCd  +  strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
									+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") /*+ strDelim +  srcCodesDelimited*/; 
									hm_MRSVC.put("RMBCAPSVC",segmentData);	
								}
								break;
			}
		}
		return generateSegmentsForMRSVC(hm_MRSVC, entRecNum);
		
	}
	
	private String generateSegmentsForMRSVC(HashMap<String, String> hm_MRSVC,
			long entRecNum) throws Exception{
		outputType = RmbEnum.MRSVC.getValue();
			if(null!= hm_MRSVC.get("RMBARNGTYPE") && hm_MRSVC.get("RMBARNGTYPE").length()>0 &&
					null!= hm_MRSVC.get("RMBCAPSVC") && hm_MRSVC.get("RMBCAPSVC").length()>0 ){
				
				segmentData = hm_MRSVC.get("RMBARNGTYPE") + hm_MRSVC.get("RMBCAPSVC") ;
				
				if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
						/*.replace(srcCodesDelimited, strBlank)*/.length() > 0 ) {
					segmentData = segmentData +ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited ;
					return segmentData;
				}
			}
		return strBlank;
	}
}

				
		