package com.wellpoint.mde.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.ProvPgmEnum;
import com.wellpoint.mde.service.ProviderPgmService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ProviderPgmServiceImpl extends AbstractServiceImpl implements ProviderPgmService{

	@Override
	public String buildMPPRGSegment(List<MemAttrRow> ppgmMPPRGMemAttrList,
			long entRecNum, String l_strSrcCd_common) throws Exception {
		outputType = ProvPgmEnum.MPPRG.getValue();
		HashMap<String, String> hm_MPPRG = new HashMap<String, String>();
		for (MemRow memRow : ppgmMPPRGMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			ProvPgmEnum attrCode = ProvPgmEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case PROGTYPE:	segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
							+ strDelim + "EPDS V2" + strDelim 
							// CFF 2.7 new field
							+ l_strSrcCd + strDelim;
							hm_MPPRG.put("MEMHEAD",segmentData);
				
							segmentData = getString(memRow , "codeval") + strDelim ;
							hm_MPPRG.put("PROGTYPE",segmentData);
							break;
							
			case PROGADMIN:	segmentData = getString(memRow , "codeval") + strDelim ;
							hm_MPPRG.put("PROGADMIN",segmentData);
							break;
							
			case PROGNAME:	segmentData = getString(memRow , "attrval") + strDelim ;
							hm_MPPRG.put("PROGNAME",segmentData);
							break;
							
			case PROGSTATUS:segmentData = ExtMemgetIxnUtils.getDateAsString(memRow , "effectdt") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow,"termdt")+
							strDelim + getString (memRow, "termrsn")+ strDelim;/* + srcCodesDelimited*/ 
							hm_MPPRG.put("PROGSTATUS",segmentData);
							break;
							
			case PRGVERNUM:	segmentData =getString(memRow , "attrval") + strDelim;
							hm_MPPRG.put("PRGVERNUM",segmentData);
							break;
							
			case PRGCATCD:	segmentData =getString(memRow , "attrval") + strDelim;
							hm_MPPRG.put("PRGCATCD",segmentData);
							break;
							  /*Added new field from  CFF3.7*/
			case VALBASPRGID: segmentData =getString(memRow , "attrval") + strDelim;
										               hm_MPPRG.put("VALBASPRGID",segmentData);
										               break;
							
			}
		}
		return generateSegmentsForMPPRG(hm_MPPRG, entRecNum, l_strSrcCd_common);
	}
	
	private String generateSegmentsForMPPRG(HashMap<String, String> hm_MPPRG, long entRecNum, String l_strSrcCd_common) throws Exception {
		outputType = ProvPgmEnum.MPPRG.getValue();
		if( hm_MPPRG.get("PROGTYPE") != null || hm_MPPRG.get("PROGADMIN") !=null 
				|| hm_MPPRG.get("PROGNAME") != null || hm_MPPRG.get("PROGSTATUS")!= null ||  hm_MPPRG.get("PRGVERNUM")!= null ||  hm_MPPRG.get("PRGCATCD")!= null ||  hm_MPPRG.get("VALBASPRGID")!= null  ){
		
			if (null == hm_MPPRG.get("PROGTYPE")){
				outputType = "MPPRG";
				segmentData = outputType+ strDelim + strBlank + strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim
				// CFF 2.7 new field
				+ l_strSrcCd_common + strDelim;
				hm_MPPRG.put("MEMHEAD", segmentData);
				hm_MPPRG.put("PROGTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
			}
		}
		else{
			hm_MPPRG.put("MEMHEAD", ExtMemgetIxnUtils.getNDelimiters(5));
			hm_MPPRG.put("PROGTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
		}
		if (null == hm_MPPRG.get("PROGADMIN"))
			hm_MPPRG.put("PROGADMIN", ExtMemgetIxnUtils.getNDelimiters(1));

		if (null == hm_MPPRG.get("PROGNAME")) 
			hm_MPPRG.put("PROGNAME", ExtMemgetIxnUtils.getNDelimiters(1));

		if (null == hm_MPPRG.get("PROGSTATUS")) 
			hm_MPPRG.put("PROGSTATUS", ExtMemgetIxnUtils.getNDelimiters(3) /*+ strDelim + srcCodesDelimited*/);

          if(null==hm_MPPRG.get("PRGVERNUM"))
		     hm_MPPRG.put("PRGVERNUM", ExtMemgetIxnUtils.getNDelimiters(1));
		 
		 if(null==hm_MPPRG.get("PRGCATCD"))
			hm_MPPRG.put("PRGCATCD", ExtMemgetIxnUtils.getNDelimiters(1));
		  /*Added new field from  CFF3.7*/
		 if(null==hm_MPPRG.get("VALBASPRGID"))
		   hm_MPPRG.put("VALBASPRGID", ExtMemgetIxnUtils.getNDelimiters(1));
		 
		segmentData = hm_MPPRG.get("MEMHEAD") + hm_MPPRG.get("PROGTYPE") + hm_MPPRG.get("PROGADMIN") 
		+hm_MPPRG.get("PROGNAME")+  hm_MPPRG.get("PROGSTATUS") + hm_MPPRG.get("PRGVERNUM")+ hm_MPPRG.get("PRGCATCD") + hm_MPPRG.get("VALBASPRGID");

		if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
				/*.replace(srcCodesDelimited, strBlank)*/.length() > 0 ) {
			segmentData = segmentData + ExtMemgetIxnUtils.getNDelimiters(8) +strDelim + srcCodesDelimited ;
			return segmentData;
		}
		return strBlank;
	}
	
	@Override
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> ppgmALTSRCIDMemAttrList,
			long entRecNum) throws Exception {
		outputType = ProvPgmEnum.ALTSRCID.getValue();
		HashMap<String, String> hm_ALTSRCID_Slg = new HashMap<String, String>();
		for (MemRow memRow : ppgmALTSRCIDMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			ProvPgmEnum attrCode = ProvPgmEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case PROGTYPE:	segmentData = getString(memRow, "codeval")+ strDelim;
							hm_ALTSRCID_Slg.put("PROGTYPE-"+new Long(memRow.getMemRecno()).toString(), segmentData);
							break;
							
			case PROGNAME:	segmentData =  getString(memRow,"attrval") + ExtMemgetIxnUtils.getNDelimiters(4) + getString(memRow, "DFCDC_evtinitiator")
							+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")
							+ ExtMemgetIxnUtils.getNDelimiters(/*142*/127) + getString(memRow, "DFCDC_evtinitiator")
							+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")/*+ strDelim + getSrcCodeforPostProcess(l_strSrcCd)*/;
							hm_ALTSRCID_Slg.put("PROGNAME-"+new Long(memRow.getMemRecno()).toString(), segmentData);
							break;
							
			case PROGSTATUS:segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
							+ strDelim + "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim;
							hm_ALTSRCID_Slg.put("MEMHEAD-"+new Long(memRow.getMemRecno()).toString(), segmentData);
							
							segmentData =  ExtMemgetIxnUtils.getDateAsString(memRow,"effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
							+ strDelim + getString(memRow, "termrsn")+strDelim;
							hm_ALTSRCID_Slg.put("PROGSTATUS-"+new Long(memRow.getMemRecno()).toString(), segmentData);
						
			}
		}
		return generateSegmentsforALTSRCID(hm_ALTSRCID_Slg, entRecNum);
	}
	
	private Set<String> generateSegmentsforALTSRCID(HashMap<String,String> hm_ALTSRCID_Slg,long entRecNum) throws Exception {
		Set <String> segmentDataSet = new HashSet<String>();
		Set <String>ALTSRCID_Keys = new HashSet<String>();
		ALTSRCID_Keys = new HashSet<String>(hm_ALTSRCID_Slg.keySet());

		String split_keys[];
		String memrecno="";
		Set <String> ALTSRCID_memrecnos = new HashSet<String>();
		for (Iterator <String>iterator = ALTSRCID_Keys.iterator(); iterator
		.hasNext();) {
			String ALTSRCID_key =iterator.next().toString();
			if(ALTSRCID_key.contains("PROGSTATUS")
					||ALTSRCID_key.contains("PROGNAME")
					||ALTSRCID_key.contains("PROGTYPE")
					||ALTSRCID_key.contains("MEMHEAD"))
			{
				//split the keys to get memrecnos
				split_keys=ALTSRCID_key.split("-");
				memrecno=split_keys[1].trim();
				ALTSRCID_memrecnos.add(memrecno);
			}

		}		

		memrecno="";
		for (Iterator<String>iterator2 = ALTSRCID_memrecnos.iterator(); iterator2
		.hasNext();) 
		{
			//write ALTSRCID segment - BEGIN
			memrecno  = (String) iterator2.next();
			if(null!=hm_MemHead)
			{
				MemHead temp_memHead ;
				temp_memHead = (MemHead)hm_MemHead.get(memrecno);
				l_strSrcCd = temp_memHead.getSrcCode();
				l_memIdNum = temp_memHead.getMemIdnum();
				l_strCaudrecno= new Long(temp_memHead.getCaudRecno()).toString();
				l_maudRecNo = new Long(temp_memHead.getMaudRecno()).toString();

			}
			if(null != hm_ALTSRCID_Slg.get("PROGSTATUS-"+memrecno)
					|| null != hm_ALTSRCID_Slg.get("PROGNAME-"+memrecno) 
					|| null != hm_ALTSRCID_Slg.get("PROGTYPE-"+memrecno)){
				if (null == hm_ALTSRCID_Slg.get("PROGSTATUS-"+memrecno)) 
				{
					outputType = "ALTSRCID";
					segmentData =outputType+ strDelim + strBlank + strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim +
					l_strSrcCd + strDelim + l_memIdNum + strDelim ;
					hm_ALTSRCID_Slg.put("MEMHEAD-"+memrecno, segmentData);
					segmentData = strBlank	+ strDelim + strBlank + strDelim + strBlank + strDelim ;
					hm_ALTSRCID_Slg.put("PROGSTATUS-"+memrecno, segmentData);
				}
			}
			else{
				hm_ALTSRCID_Slg.put("MEMHEAD-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(6));
				hm_ALTSRCID_Slg.put("PROGSTATUS-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(3));
			}
			if (null == hm_ALTSRCID_Slg.get("PROGTYPE-"+memrecno)) 
				hm_ALTSRCID_Slg.put("PROGTYPE-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(1));
			
			if (null == hm_ALTSRCID_Slg.get("PROGNAME-"+memrecno)) {

				segmentData = ExtMemgetIxnUtils.getNDelimiters(6) + "0" + ExtMemgetIxnUtils.getNDelimiters(/*144*/129) + "0" /*+ strDelim  + getSrcCodeforPostProcess(l_strSrcCd)*/  ;
				hm_ALTSRCID_Slg.put("PROGNAME-"+memrecno, segmentData);
			}
			
			segmentData = hm_ALTSRCID_Slg.get("MEMHEAD-"+memrecno)+ hm_ALTSRCID_Slg.get("PROGTYPE-"+memrecno) +hm_ALTSRCID_Slg.get("PROGSTATUS-"+memrecno)
				+ hm_ALTSRCID_Slg.get("PROGNAME-"+memrecno);

			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
					/*.replace(getSrcCodeforPostProcess(l_strSrcCd), strBlank)*/.length() > 0) {
				segmentData = segmentData + ExtMemgetIxnUtils.getNDelimiters(60)+ strDelim  + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				segmentDataSet.add(segmentData);
			}
			//write ALTSRCID segment - END
		}
		//write ALTSRCID segment - END
		return segmentDataSet;
	}
}
