package com.wellpoint.mde.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.RprtGrpEnum;
import com.wellpoint.mde.service.ReportingGrpService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ReportingGrpServiceImpl extends AbstractServiceImpl implements ReportingGrpService{

	@Override
	public String getSegmentDataforRPTRL(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = RprtGrpEnum.RPTRL.getValue();
		segmentData= outputType + strDelim +getString(memRow, "DFCDC_evtctime")
		+strDelim +entRecNum+strDelim +"EPDS V2"+strDelim + getString(memRow, "relmemidnum")
		+ strDelim +getString(memRow, "relmemsrccode")+strDelim +getString(memRow, "reltype")
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")
		+ strDelim +getString(memRow, "termrsn") +ExtMemgetIxnUtils.getNDelimiters(12) + strDelim + srcCodesDelimited ;
		return segmentData;
	}
	
	@Override
	public String buildRPTGPSegment(List<MemAttrRow> regrpRPTGPMemAttrList,
			long entRecNum, String l_strSrcCd_common) throws Exception {
		outputType = RprtGrpEnum.RPTGP.getValue();
		HashMap<String, String> hm_RPTGP = new HashMap<String, String>();
		for (MemRow memRow : regrpRPTGPMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			RprtGrpEnum attrCode = RprtGrpEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case RPTGRPNAME:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "attrval"))) {
									segmentData = outputType + strDelim +getString(memRow, "DFCDC_evtctime")+ strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim +getString(memRow, "attrval")+ strDelim ;
									hm_RPTGP.put("RPTGRPNAME",segmentData);
								}	
								break;
								
			case RPTGRPTYPE:	if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow, "attrval"))) {
									segmentData =getString(memRow, "attrval")+ strDelim;
									hm_RPTGP.put("RPTGRPTYPE",segmentData);
								}
								break;
								
			case RPTGRPID:		segmentData=getString(memRow, "attrval")+ strDelim;
								hm_RPTGP.put("RPTGRPID",segmentData);
								break;
								
			case RPTPRVPROGID:	segmentData=getString(memRow, "attrval")+ strDelim + l_strSrcCd + strDelim;
								hm_RPTGP.put("RPTPRVPROGID",segmentData);
								break;
								
			case RPTGRPSTAT:	segmentData= /*strBlank + strDelim +*/getString(memRow, "attrval")
								+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim +getString(memRow, "TERMRSN")/*+ strDelim + srcCodesDelimited*/ ;
								hm_RPTGP.put("RPTGRPSTAT",segmentData);
								break;
			}
		}
		return generateSegmentsforRPTGP(hm_RPTGP, entRecNum, l_strSrcCd_common);
	}

	private String generateSegmentsforRPTGP(HashMap<String, String> hm_RPTGP,
			long entRecNum, String l_strSrcCd_common) {
		if(null != hm_RPTGP.get("RPTGRPNAME")&&(null!=hm_RPTGP.get("RPTGRPTYPE"))) {
		if (null == hm_RPTGP.get("RPTGRPNAME")&&(null!=hm_RPTGP.get("RPTGRPTYPE")||null!=hm_RPTGP.get("RPTGRPID")
				||null!=hm_RPTGP.get("RPTPRVPROGID")||null!=hm_RPTGP.get("RPTGRPSTAT")))
			{
				outputType = "RPTGP";
				segmentData = outputType+strDelim +strBlank + strDelim +entRecNum+ strDelim 
				+"EPDS V2"+ strDelim +strBlank+ strDelim ;
				hm_RPTGP.put("RPTGRPNAME", segmentData);
			}
			if(null ==hm_RPTGP.get("RPTGRPNAME"))hm_RPTGP.put("RPTGRPNAME", ExtMemgetIxnUtils.getNDelimiters(5));
		//	if(null ==hm_RPTGP.get("GRPNAME_1"))hm_RPTGP.put("GRPNAME_1", getNDelimiters(2)+"0");
			if(null ==hm_RPTGP.get("RPTGRPTYPE"))hm_RPTGP.put("RPTGRPTYPE", ExtMemgetIxnUtils.getNDelimiters(1));
			if(null ==hm_RPTGP.get("RPTGRPID"))hm_RPTGP.put("RPTGRPID", ExtMemgetIxnUtils.getNDelimiters(1));
			if(null ==hm_RPTGP.get("RPTPRVPROGID"))hm_RPTGP.put("RPTPRVPROGID", ExtMemgetIxnUtils.getNDelimiters(1) + l_strSrcCd_common + strDelim);
			if(null ==hm_RPTGP.get("RPTGRPSTAT"))hm_RPTGP.put("RPTGRPSTAT", ExtMemgetIxnUtils.getNDelimiters(3));
			
			segmentData = hm_RPTGP.get("RPTGRPNAME")+hm_RPTGP.get("RPTGRPTYPE") + hm_RPTGP.get("RPTGRPID") 
			+ hm_RPTGP.get("RPTPRVPROGID") + hm_RPTGP.get("RPTGRPSTAT");
			//+ hm_RPTGP.get("GRPNAME_1");

			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
					/*.replace(srcCodesDelimited, strBlank)*/.length() > 0) 
			{
				segmentData += ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited;
				return segmentData;
			}
		}
		return strBlank;
	}
	
	@Override
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> regrpALTSRCIDMemAttrList,
			long entRecNum) throws Exception {
		outputType = RprtGrpEnum.RPTGP.getValue();
		HashMap<String, String> hm_ALTSRCID_Slg = new HashMap<String, String>();
		for (MemRow memRow : regrpALTSRCIDMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			RprtGrpEnum attrCode = RprtGrpEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case RPTGRPNAME:	segmentData = getString(memRow, "attrval") + strDelim + strBlank + strDelim 
								+ strBlank + strDelim + strBlank + strDelim 
								+ getString(memRow, "DFCDC_evtinitiator")  + strDelim 
								+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim;
								hm_ALTSRCID_Slg.put("RPTGRPNAME_1-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
								
								segmentData = getString(memRow, "DFCDC_evtinitiator")  + strDelim 
								+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")/*+ strDelim + getSrcCodeforPostProcess(l_strSrcCd)*/;
								hm_ALTSRCID_Slg.put("RPTGRPNAME_2-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
								break;
								
			case RPTGRPTYPE:	hm_ALTSRCID_Slg.put("RPTGRPTYPE-"+new Long(memRow.getMemRecno()).toString(),getString(memRow, "attrval")+ strDelim);
								break;
								
			case RPTGRPSTAT:	segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime") 
								+ strDelim + entRecNum + strDelim 
								+ "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim;
								hm_ALTSRCID_Slg.put("MemHead_1-" + new Long(memRow.getMemRecno()).toString(), segmentData);
								/*segmentData = getString(memRow, "DFCDC_mAudRecno")+ strDelim;
								hm_ALTSRCID_Slg.put("MemHead_2-" + new Long(memRow.getMemRecno()).toString(), segmentData)*/;
								
								segmentData = ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
								+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
								+ strDelim + getString(memRow, "termrsn")+ strDelim;
								hm_ALTSRCID_Slg.put("RPTGRPSTAT-"+ new Long(memRow.getMemRecno()).toString(), segmentData);
								break;
			}
		}
		return generateSegmentsforALTSRCID(hm_ALTSRCID_Slg, entRecNum);
	}
	
	private Set<String> generateSegmentsforALTSRCID(HashMap<String,String> hm_ALTSRCID_Slg,long entRecNum)throws Exception
	{
		Set<String> segmentDataSet = new HashSet<String>();
		Set <String>ALTSRCID_Keys = new HashSet<String>();
		//get the keys
		ALTSRCID_Keys = new HashSet<String>(hm_ALTSRCID_Slg.keySet());
		
		String split_keys[];
		String memrecno="";
		Set <String> ALTSRCID_memrecnos = new HashSet<String>();
		for (Iterator <String>iterator = ALTSRCID_Keys.iterator(); iterator
				.hasNext();) 
		{
			String ALTSRCID_key =iterator.next().toString();
			if(ALTSRCID_key.contains("RPTGRPSTAT")
					||ALTSRCID_key.contains("RPTGRPTYPE")
					||ALTSRCID_key.contains("RPTGRPNAME_1")
					||ALTSRCID_key.contains("RPTGRPNAME_2")
					||ALTSRCID_key.contains("MemHead_1"))
			{
				//split the keys to get memrecnos
				split_keys=ALTSRCID_key.split("-");
				memrecno=split_keys[1].trim();
				if(!ALTSRCID_memrecnos.contains(memrecno))
				ALTSRCID_memrecnos.add(memrecno);
			}
			
		}
		
		//generate for each memrecnos
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
			if ((null == hm_ALTSRCID_Slg.get("RPTGRPSTAT-"+memrecno))&&(null!=hm_ALTSRCID_Slg.get("RPTGRPTYPE-"+memrecno)||
					null!=hm_ALTSRCID_Slg.get("RPTGRPNAME_1-"+memrecno)||null!=hm_ALTSRCID_Slg.get("RPTGRPNAME_2-"+memrecno)))
			{
				outputType="ALTSRCID";
				segmentData =  outputType + strDelim + strBlank+ strDelim + entRecNum + strDelim 
				+ "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim;
				hm_ALTSRCID_Slg.put("MemHead_1-"+memrecno, segmentData);
				/*segmentData = strBlank + strDelim;*/
				/*hm_ALTSRCID_Slg.put("MemHead_2-"+memrecno, segmentData);*/
				hm_ALTSRCID_Slg.put("RPTGRPSTAT-"+ memrecno, ExtMemgetIxnUtils.getNDelimiters(3));
			}
			
			if (null==hm_ALTSRCID_Slg.get("MemHead_1-"+memrecno))hm_ALTSRCID_Slg.put("MemHead_1-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(6));
			if (null == hm_ALTSRCID_Slg.get("RPTGRPTYPE-"+memrecno)) hm_ALTSRCID_Slg.put("RPTGRPTYPE-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(1));
			if (null==hm_ALTSRCID_Slg.get("RPTGRPSTAT-"+memrecno))hm_ALTSRCID_Slg.put("RPTGRPSTAT-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(3));
			if (null == hm_ALTSRCID_Slg.get("RPTGRPNAME_1-"+memrecno)) hm_ALTSRCID_Slg.put("RPTGRPNAME_1-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(6)+ "0" + strDelim);
			/*if (null == hm_ALTSRCID_Slg.get("MemHead_2-"+memrecno)) hm_ALTSRCID_Slg.put("MemHead_2-"+memrecno, getNDelimiters(1));*/
			if (null == hm_ALTSRCID_Slg.get("RPTGRPNAME_2-"+memrecno)) hm_ALTSRCID_Slg.put("RPTGRPNAME_2-"+memrecno,ExtMemgetIxnUtils.getNDelimiters(2)+ "0"/*+ strDelim  + getSrcCodeforPostProcess(l_strSrcCd)*/) ;
			
			segmentData = hm_ALTSRCID_Slg.get("MemHead_1-"+memrecno) +hm_ALTSRCID_Slg.get("RPTGRPTYPE-"+memrecno)+ hm_ALTSRCID_Slg.get("RPTGRPSTAT-"+memrecno) + hm_ALTSRCID_Slg.get("RPTGRPNAME_1-"+memrecno) 
						+ExtMemgetIxnUtils.getNDelimiters(/*141*/126)+ hm_ALTSRCID_Slg.get("RPTGRPNAME_2-"+memrecno);

			//logInfo("ALTSRCID_Slg: " + segmentData);
			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
					/*.replace(getSrcCodeforPostProcess(l_strSrcCd), strBlank)*/.length() > 0) 
			{
				segmentData += strDelim + ExtMemgetIxnUtils.getNDelimiters(60) + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				segmentDataSet.add(segmentData);
			}
		}
		return segmentDataSet;
	}
}
