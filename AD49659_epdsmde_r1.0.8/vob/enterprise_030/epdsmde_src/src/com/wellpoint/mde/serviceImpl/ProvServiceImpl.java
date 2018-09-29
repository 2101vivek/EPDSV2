package com.wellpoint.mde.serviceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;

import com.wellpoint.mde.constants.Constants;
import com.wellpoint.mde.constants.ProvEnum;
import com.wellpoint.mde.service.ProviderService;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;


public class ProvServiceImpl extends AbstractServiceImpl implements ProviderService{
	
	HashMap<String, String> hm_ALTSRCID_Slg = new HashMap<String, String>();
	
	HashMap<String, String> hm_ALTSRCID_Slg_V2 = new HashMap<String, String>();
	
	HashMap<String, String> hm_PSPT = new HashMap<String, String>();
	
	HashMap<String, String>hm_PSPT_SPC=new HashMap<String, String>();
	
	HashMap<String, String>hm_PSPT_TXNM=new HashMap<String, String>();
	
	HashMap<String, String> hm_PSPT_BRDCRT = new HashMap<String, String>();
	
	HashMap<String, String> hm_PADR = new HashMap<String, String>();
	
	HashMap<String, String> hm_PRVFACSTERE = new HashMap<String, String>();
	
	HashMap<String, String> hm_PALT = new HashMap<String, String>();
	
	HashMap<String,ArrayList<String>> hm_PALT_Prov_Alt_ID_Specialty = new HashMap<String,ArrayList<String>>();
	
	HashMap<String, String> hm_PRF = new HashMap<String, String>();
	
	HashMap<String,String> hm_PRF_PROVETHNIC = new HashMap <String,String>();
	
	HashMap<String, String> hm_APADR = new HashMap<String, String>();
	
	public HashMap<String, String> getHm_PRF() {
		return hm_PRF;
	}
	
	
	@Override
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> orgALTSRCIDMemAttrList, long entRecNum)
			throws Exception {
		outputType = ProvEnum.ALTSRCID.getValue();
			for (MemRow memRow : orgALTSRCIDMemAttrList){
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				String l_memrecno = strBlank;
				String degree_code = strBlank;		
				String grad_school = strBlank;
				getMemHeadValues(memRow);
				l_memrecno  = new Long(memRow.getMemRecno()).toString();
				ProvEnum attrCode = ProvEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				switch(attrCode) {
				case PROVTYPCD:	{
						segmentData = getString(memRow, "codeval")+ strDelim;
						hm_ALTSRCID_Slg.put("PROVTYPCD-"+l_memrecno, segmentData);
						break;
					}
				case PROVINACTIVE:	{
						segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
						+ strDelim + "EPDS V2" + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim ;
						hm_ALTSRCID_Slg.put("MEMHEAD-"+l_memrecno, segmentData);
			
						segmentData = ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
						+ strDelim + getString(memRow, "termrsn") + strDelim + strBlank + strDelim;
						hm_ALTSRCID_Slg.put("PROVINACTIVE-"+l_memrecno, segmentData);
						break;
					}
				
				case PROVNAMEEXT:	{
						segmentData = getString(memRow, "onmfirst") + strDelim + getString(memRow, "onmlast") + strDelim + getString(memRow, "onmmiddle") + strDelim + 
						getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim;
						/*strBlank + strDelim + strBlank + strDelim + l_maudRecNo + strDelim*/;
						hm_ALTSRCID_Slg.put("PROVNAMEEXT_1-"+l_memrecno, segmentData);
			
						segmentData = getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
						+ getString(memRow, "DFCDC_mAudRecno") + strDelim /*+ getSrcCodeforPostProcess(l_strSrcCd)*/;
						hm_ALTSRCID_Slg.put("PROVNAMEEXT_2-"+l_memrecno, segmentData);
						break;
					}
				case PROVACESLGCY:	{
						segmentData = getString(memRow, "npixwalk") + strDelim
						+ getString(memRow, "requestedind1099") + strDelim
						+ getString(memRow, "forprofitind") + strDelim
						+ getString(memRow, "paytoprovtamt") + strDelim
						+ getString(memRow, "paytoprovind") + strDelim
						// CHECK START
						//+ getString(memRow, "paytostateind") + strDelim 
						+ getString(memRow, "provstupst") + strDelim //paytostateind is renamed to provstupst
						//CHECK END
						+ getString(memRow, "payeename") + strDelim
						+ getString(memRow, "provacredidnum") + strDelim
						+ getString(memRow, "provchaifind") + strDelim
						// CFF 2.4 changes start here
						//The field names as per the Inbound CFF chaieffectdate, 
						//usgdateind, usgdatecd,parindt,pareffectdt, parenddt, pnlid In EMEMACESLGCY table.
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "chaieffectdate") + strDelim
						+ getString(memRow, "usgdateind") + strDelim
						+ getString(memRow, "usgdatecd") + strDelim
						+ getString(memRow, "parind") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "pareffectdt") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "parenddt") + strDelim
						+ getString(memRow, "pnlid") + strDelim
						// CFF 2.4 changes end here
						+ getString(memRow, "provclasscd") + strDelim
						+ getString(memRow, "provcorpttl") + strDelim
						+ getString(memRow, "provelectcmrccd") + strDelim
						+ getString(memRow, "provinstttl") + strDelim
						+ getString(memRow, "provliccatcd") + strDelim
						+ getString(memRow, "provmiddlenm") + strDelim
						+ getString(memRow, "profesttl") + strDelim
						+ getString(memRow, "provstatus") + strDelim
						+ getString(memRow, "provsufbusttl") + strDelim
						+ getString(memRow, "provfrstnm") + strDelim
						+ getString(memRow, "provlstnm") + strDelim
						+ getString(memRow, "provmiddleinit") + strDelim
						+ getString(memRow, "provsufnme") + strDelim
						+ getString(memRow, "totantemmbrscurr") + strDelim
						//CFF 2.5b changes starts here
						+ getString(memRow, "provngtind") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "provngteffdt") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "provngtenddt") + strDelim;
						//CFF 2.5b changes ends here;
						hm_ALTSRCID_Slg.put("PROVACESLGCY-"+l_memrecno, segmentData);
						break;
					}
				case PROVCPFLGCY:	{
						segmentData = getString(memRow, "instpeergrpcd") + strDelim + getString(memRow, "instbussind") + strDelim 
						+ getString(memRow, "instinpataccind") + strDelim + getString(memRow, "instctyhospind") 
						+ strDelim + getString(memRow, "instnmemind") + strDelim + getString(memRow, "insthmecareind") 
						+ strDelim + getString(memRow, "insthmdlysisind") + strDelim + getString(memRow, "instotpatind") 
						+ strDelim + getString(memRow, "instsezareacd") + strDelim + getString(memRow, "instprtitind") + strDelim 
						+ getString(memRow, "ihstabrtnind") + strDelim + getString(memRow, "ihstgovngovcd") + strDelim 
						+ getString(memRow, "ihsttypsvccd") + strDelim + getString(memRow, "ihstacrdtncd") + strDelim 
						+ getString(memRow, "ihstaffltncd") + strDelim + getString(memRow, "ihstemhcflg") + strDelim 
						+ getString(memRow, "ihstdrgcd") + strDelim + getString(memRow, "ihstdrgexmptflg") + strDelim;
						hm_ALTSRCID_Slg.put("PROVCPFLGCY-"+l_memrecno, segmentData);
						break;
					}
				case PROVCPMFLGCY:	{
						segmentData = getString(memRow, "ctrlcd") + strDelim
						+ getString(memRow, "provtypcd") + strDelim
						+ getString(memRow, "subscrpayind") + strDelim
						+ getString(memRow, "grprecind") + strDelim
						+ getString(memRow, "loccd") + strDelim
						+ getString(memRow, "medicareind") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcprpteffdt") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcprptthrudt") + strDelim
						+ getString(memRow, "speccatcd1") + strDelim
						+ getString(memRow, "speccatcd2") + strDelim
						+ getString(memRow, "speccatcd3") + strDelim
						+ getString(memRow, "speccatcd4") + strDelim
						+ getString(memRow, "speccatcd5") + strDelim
						+ getString(memRow, "speccatcd6") + strDelim
						+ getString(memRow, "speccatcd7") + strDelim
						+ getString(memRow, "speccatcd8") + strDelim
						+ getString(memRow, "speccatcd9") + strDelim
						+ getString(memRow, "speccatcd10") + strDelim
						+ getString(memRow, "speccatcd11") + strDelim
						+ getString(memRow, "speccatcd12") + strDelim
						+ getString(memRow, "speccatcd13") + strDelim
						+ getString(memRow, "speccatcd14") + strDelim
						+ getString(memRow, "speccatcd15") + strDelim
						+ getString(memRow, "speccatcd16") + strDelim
						+ getString(memRow, "speccatcd17") + strDelim
						+ getString(memRow, "speccatcd18") + strDelim
						+ getString(memRow, "speccatcd19") + strDelim
						+ getString(memRow, "speccatcd20") + strDelim
						+ getString(memRow, "npielgb") + strDelim;
						hm_ALTSRCID_Slg.put("PROVCPMFLGCY-"+l_memrecno, segmentData);
						break;
					}
				case PROVEPSBLGCY:	{
						segmentData = getString(memRow, "tradsftynetcd") + strDelim
						+ getString(memRow, "ipprnkval") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "ipseffdt") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "ipstermdt") + strDelim
						+ getString(memRow, "ipstermrsn") + strDelim
						// CFF 2.4 changes begins here
						+ getString(memRow, "provorgdirabb") + strDelim
						// CFF 2.4 changes ends here
						+ getString(memRow, "pdcd") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "pdeffdt") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "pdtermdt") + strDelim
						+ getString(memRow, "pdtermrsn") + strDelim
						+ getString(memRow, "pdtypcd") + strDelim
						// CFF 2.4 changes begins here
						+ getString(memRow, "pdcd2") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "pdcdeffdt2") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "pdcdtrmdt2") + strDelim
						+ getString(memRow, "pdcdtrmrsncd2") + strDelim
						+ getString(memRow, "pdtypcd2") + strDelim
						// CFF 2.4 changes ends here
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "provtxnmyeffdt") + strDelim
						//CFF 2.5b changes starts here
						+ getString(memRow, "dlgtdcredentcd") + strDelim
						+ getString(memRow, "wlcletreqind") + strDelim
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "wlcletsentdt") + strDelim;
						//CFF 2.5b changes ends here
						hm_ALTSRCID_Slg.put("PROVEPSBLGCY-"+l_memrecno, segmentData);
						break;
					}
				case PROVQCARELGY:	{
						segmentData = getString(memRow, "prv2025ind") + strDelim
						+ getString(memRow, "prv2582ind") + strDelim
						+ getString(memRow, "prv2750ind") + strDelim
						+ getString(memRow, "prv2755ind2") + strDelim
						+ getString(memRow, "prv2755ind3") + strDelim
						+ getString(memRow, "prv2755ind4") + strDelim
						+ getString(memRow, "prv2766ind") + strDelim
						+ getString(memRow, "prv2791ind1") + strDelim
						+ getString(memRow, "prv2791ind2") + strDelim
						+ getString(memRow, "prv2791ind3") + strDelim
						+ getString(memRow, "prv2791ind4") + strDelim
						+ getString(memRow, "prv2791ind5") + strDelim
						// CFF 2.4 changes begins here
						+ getString(memRow, "prv2791ind6") + strDelim
						+ getString(memRow, "prv2791ind7") + strDelim
						+ getString(memRow, "prv2791ind8") + strDelim
						+ getString(memRow, "prv2791ind9") + strDelim
						+ getString(memRow, "prv2791ind10") + strDelim
						// CFF 2.4 changes ends here
						+ getString(memRow, "prv2792ind") + strDelim
						+ getString(memRow, "prv2796ind1") + strDelim
						+ getString(memRow, "prv2796ind2") + strDelim
						+ getString(memRow, "prv2796ind3") + strDelim
						+ getString(memRow, "prv2796ind4") + strDelim
						// CFF 2.4 changes begins here
						+ getString(memRow, "prv2796ind5") + strDelim
						// CFF 2.4 changes ends here
						+ getString(memRow, "prv2798ind") + strDelim
						+ getString(memRow, "prv2799ind") + strDelim
						+ getString(memRow, "prv2614ind") + strDelim;
						hm_ALTSRCID_Slg.put("PROVQCARELGY-"+l_memrecno, segmentData);
						break;
					}
				case PROVTTLSFX:	{
						if(l_strSrcCd.equalsIgnoreCase("EPDS1")) {
							segmentData = getString(memRow, "suffix");
							int length = segmentData.length();
							if (length < 4 ){
								segmentData = segmentData + ExtMemgetIxnUtils.getNBlank(4-length);
							}
							hm_ALTSRCID_Slg.put("PROVTTLSFX-"+l_memrecno, segmentData);
						}
						break;
					}
				case PROVEDUCTN:	{
						if(l_strSrcCd.equalsIgnoreCase("EPDS1")) {
							degree_code = ExtMemgetIxnUtils.getDegreeCode(getString(memRow, "degree"),getDegree_codes());
							grad_school = ExtMemgetIxnUtils.getGradSchool(getString(memRow, "schoolname"),getSchool_name());
							segmentData = degree_code + grad_school + strDelim;
							hm_ALTSRCID_Slg.put("PROVEDUCTN-"+l_memrecno, segmentData);
						}
						break;
					}
				}
			}
		return generateSegmentsforALTSRCID(hm_ALTSRCID_Slg, entRecNum);
	}

	private Set<String> generateSegmentsforALTSRCID(HashMap<String,String> hm_ALTSRCID_Slg,long entRecNum) throws Exception
	{
		Set <String>ALTSRCID_Keys = new HashSet<String>();
		Set<String> segmentDataSet = new HashSet<String>();
		ALTSRCID_Keys = new HashSet<String>(hm_ALTSRCID_Slg.keySet());

		String split_keys[],split_keys_memrecno[];
		String memrecno="",statusdate = "";
		Set <String> ALTSRCID_memrecnos = new HashSet<String>();
		for (Iterator <String>iterator = ALTSRCID_Keys.iterator(); iterator
		.hasNext();) {
			String ALTSRCID_key =iterator.next().toString();
			if(ALTSRCID_key.contains("PROVINACTIVE") || ALTSRCID_key.contains("PROVNAMEEXT_1")
					|| ALTSRCID_key.contains("PROVACESLGCY") || ALTSRCID_key.contains("PROVCPFLGCY")
					|| ALTSRCID_key.contains("PROVCPMFLGCY") || ALTSRCID_key.contains("PROVTYPCD")
					|| ALTSRCID_key.contains("PROVEPSBLGCY") || ALTSRCID_key.contains("PROVNAMEEXT_2")
					|| ALTSRCID_key.contains("PROVQCARELGY") || ALTSRCID_key.contains("MEMHEAD")
					|| ALTSRCID_key.contains("PROVTTLSFX") || ALTSRCID_key.contains("PROVEDUCTN")) {
				//split the keys to get memrecnos
				split_keys=ALTSRCID_key.split("-");
				memrecno=split_keys[1].trim();
				ALTSRCID_memrecnos.add(memrecno);
			}
		}		

		//generate for each memrecnos
		memrecno="";
		for (Iterator<String>iterator2 = ALTSRCID_memrecnos.iterator(); iterator2.hasNext();) {
			//write ALTSRCID segment - BEGIN
			memrecno  = (String) iterator2.next();
			split_keys_memrecno = memrecno.split("\\*");
			String memrecno_key = split_keys_memrecno[0];
			if(null!=hm_MemHead) {
				MemHead temp_memHead ;
				temp_memHead = (MemHead)hm_MemHead.get(memrecno_key);
				l_strSrcCd = temp_memHead.getSrcCode();
				srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				l_memIdNum = temp_memHead.getMemIdnum();
				l_strCaudrecno= new Long(temp_memHead.getCaudRecno()).toString();
				l_maudRecNo = new Long(temp_memHead.getMaudRecno()).toString();
			}
			if(null != hm_ALTSRCID_Slg.get("PROVINACTIVE-"+memrecno) || null != hm_ALTSRCID_Slg.get("PROVNAMEEXT_1-"+memrecno) 
					|| null != hm_ALTSRCID_Slg.get("PROVACESLGCY-"+memrecno) || null != hm_ALTSRCID_Slg.get("PROVCPFLGCY-"+memrecno)
					|| null != hm_ALTSRCID_Slg.get("PROVCPMFLGCY-"+memrecno) || null != hm_ALTSRCID_Slg.get("PROVTYPCD-"+memrecno)
					|| null != hm_ALTSRCID_Slg.get("PROVEPSBLGCY-"+memrecno) || null != hm_ALTSRCID_Slg.get("PROVQCARELGY-"+memrecno)
					|| null != hm_ALTSRCID_Slg.get("PROVNAMEEXT_2-"+memrecno)|| null != hm_ALTSRCID_Slg.get("PROVTTLSFX-"+memrecno)
					|| null != hm_ALTSRCID_Slg.get("PROVEDUCTN-"+memrecno)){
				if (null == hm_ALTSRCID_Slg.get("PROVINACTIVE-"+memrecno)) 
				{
					outputType = ProvEnum.ALTSRCID.getValue();
					segmentData = outputType+ strDelim + strBlank + strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim +
					l_strSrcCd + strDelim + l_memIdNum + strDelim ;
					hm_ALTSRCID_Slg.put("MEMHEAD-"+memrecno, segmentData);
					segmentData = strBlank+ strDelim + strBlank	+ strDelim + strBlank + strDelim + strBlank + strDelim ;
					hm_ALTSRCID_Slg.put("PROVINACTIVE-"+memrecno, segmentData);
				}
			}
			else{
				hm_ALTSRCID_Slg.put("MEMHEAD-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(6));
				hm_ALTSRCID_Slg.put("PROVINACTIVE-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(4));
			}
			if (null == hm_ALTSRCID_Slg.get("PROVNAMEEXT_1-"+memrecno)) {

				segmentData = ExtMemgetIxnUtils.getNDelimiters(5) + "0" + strDelim ;
				hm_ALTSRCID_Slg.put("PROVNAMEEXT_1-"+memrecno, segmentData);
			}
			if (null == hm_ALTSRCID_Slg.get("PROVTYPCD-"+memrecno)) hm_ALTSRCID_Slg.put("PROVTYPCD-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(1));
			if (null == hm_ALTSRCID_Slg.get("PROVACESLGCY-"+memrecno)) hm_ALTSRCID_Slg.put("PROVACESLGCY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(33));//23
			if (null == hm_ALTSRCID_Slg.get("PROVCPFLGCY-"+memrecno)) hm_ALTSRCID_Slg.put("PROVCPFLGCY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(18));
			if (null == hm_ALTSRCID_Slg.get("PROVCPMFLGCY-"+memrecno)) hm_ALTSRCID_Slg.put("PROVCPMFLGCY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(29));
			if (null == hm_ALTSRCID_Slg.get("PROVEPSBLGCY-"+memrecno)) hm_ALTSRCID_Slg.put("PROVEPSBLGCY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(20));//11
			if (null == hm_ALTSRCID_Slg.get("PROVQCARELGY-"+memrecno)) hm_ALTSRCID_Slg.put("PROVQCARELGY-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(26));//20
			if (null == hm_ALTSRCID_Slg.get("PROVNAMEEXT_2-"+memrecno)) {
				segmentData = ExtMemgetIxnUtils.getNDelimiters(2) + "0" + strDelim;
				hm_ALTSRCID_Slg.put("PROVNAMEEXT_2-"+memrecno, segmentData);
			}

			if(l_strSrcCd.equalsIgnoreCase("EPDS1") /*&& !par && !allSourceCodeSet.contains("EPDSV2")*/){
				if (null == hm_ALTSRCID_Slg.get("PROVTTLSFX-"+memrecno)){hm_ALTSRCID_Slg.put("PROVTTLSFX-"+memrecno, "    "+ExtMemgetIxnUtils.getNDelimiters(0));}
				if (null == hm_ALTSRCID_Slg.get("PROVEDUCTN-"+memrecno)){hm_ALTSRCID_Slg.put("PROVEDUCTN-"+memrecno, "      " +ExtMemgetIxnUtils.getNDelimiters(1));}
			}
			else{
				if (null == hm_ALTSRCID_Slg.get("PROVTTLSFX-"+memrecno)){hm_ALTSRCID_Slg.put("PROVTTLSFX-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(0));}
				if (null == hm_ALTSRCID_Slg.get("PROVEDUCTN-"+memrecno)){hm_ALTSRCID_Slg.put("PROVEDUCTN-"+memrecno, ExtMemgetIxnUtils.getNDelimiters(1));}

			}
			/*if (par){statusdate = hm_ALTSRCID_Slg.get("PROVINACTIVE-"+epdsv2memrecno+"*0")+ hm_ALTSRCID_Slg.get("PROVNAMEEXT_1-"+epdsv2memrecno+"*0") ;}
			else*/{
				statusdate = hm_ALTSRCID_Slg.get("PROVINACTIVE-"+memrecno)+ hm_ALTSRCID_Slg.get("PROVNAMEEXT_1-"+memrecno) ;
				}

			segmentData = hm_ALTSRCID_Slg.get("MEMHEAD-"+memrecno) +hm_ALTSRCID_Slg.get("PROVTYPCD-"+memrecno) 
			+ statusdate 
			+ hm_ALTSRCID_Slg.get("PROVACESLGCY-"+memrecno) + hm_ALTSRCID_Slg.get("PROVCPFLGCY-"+memrecno) 
			+ hm_ALTSRCID_Slg.get("PROVCPMFLGCY-"+memrecno) + hm_ALTSRCID_Slg.get("PROVEPSBLGCY-"+memrecno) 
			+ hm_ALTSRCID_Slg.get("PROVQCARELGY-"+memrecno) + hm_ALTSRCID_Slg.get("PROVNAMEEXT_2-"+memrecno)
			+ hm_ALTSRCID_Slg.get("PROVTTLSFX-"+memrecno) + hm_ALTSRCID_Slg.get("PROVEDUCTN-"+memrecno)
			+ ExtMemgetIxnUtils.getNDelimiters(59) + srcCode_postprocess;

			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0) {
				outputType = ProvEnum.ALTSRCID.getValue();
				segmentDataSet.add(segmentData);
			}
			//write ALTSRCID segment - END
		}
		return segmentDataSet;
		//write ALTSRCID segment - END
	}

	@Override
	public Set<String> buildAPSPTSegment(List<MemAttrRow> orgAPSPTMemAttrList,
			long entRecNum) throws Exception {
		outputType = ProvEnum.APSPT.getValue();
		HashMap<String, String>hm_APSPT_PROVBRDCRT = new HashMap<String, String>();
		HashMap<String, String>hm_APSPT_PROVBRDCRT1 = new HashMap<String, String>();
		HashMap<String, String>hm_APSPT_TXNMY = new HashMap<String, String>();
		
		for (MemRow memRow : orgAPSPTMemAttrList){
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
		String l_memrecno = null;
		String row_Indicator = null;
		if(null!=hm_MemHead)
		{
			MemHead temp_memHead ;
			temp_memHead = (MemHead)hm_MemHead.get(new Long(memRow.getMemRecno()).toString());
			l_strSrcCd = temp_memHead.getSrcCode();
			l_memIdNum = temp_memHead.getMemIdnum();
			l_strCaudrecno= new Long(temp_memHead.getCaudRecno()).toString();
			l_maudRecNo = new Long(temp_memHead.getMaudRecno()).toString();
			l_memrecno = new Long(memRow.getMemRecno()).toString();
		}
		if (l_strAttrCode.equalsIgnoreCase("PROVBRDCRT")){
			segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim 
			+ "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "specialtycd") + strDelim 
			+ getString(memRow, "primaryspec") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt") + strDelim 
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt") + strDelim + getString(memRow, "spectermrsn") + strDelim 
			+ getString(memRow, "speclgcycode")+ strDelim  // added new field for cff2.5b
			+ l_strSrcCd+ strDelim + l_memIdNum + strDelim
			//ChangeRequest Term dates not present on outbound files for A files WLPRD00793241
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt")	+ strDelim 
			+ getString(memRow, "spectermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
			+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim 
			+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
			+ getString(memRow, "DFCDC_evtctime") + strDelim 
			+ getString(memRow, "DFCDC_mAudRecno") + strDelim;

			hm_APSPT_PROVBRDCRT.put(getString(memRow, "md5key") + "-" 
					+ getString(memRow, "mds5addrtype")+ "-"
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
					+ getString(memRow, "specialtycd")+"-"+ ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+"-"
					+ getString(memRow, "primaryspec") + "-"
					+ l_memrecno + "-" + row_Indicator, segmentData);

			//Dec Offcycle new feild
			segmentData = getString(memRow, "boardcertcd") + strDelim ;
			hm_APSPT_PROVBRDCRT1.put(getString(memRow, "md5key") + "-" 
					+ getString(memRow, "mds5addrtype")+ "-"
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
					+ getString(memRow, "specialtycd")+"-"+ ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+"-"
					+ getString(memRow, "primaryspec") + "-"
					+ l_memrecno + "-" + row_Indicator, segmentData);
		}
		// APSPT - Extra segments Begin
		else if(l_strAttrCode.equalsIgnoreCase("PROVSPTYTXNM"))	{
			segmentData = getString(memRow, "SPECTAXONOMY1") 
			+ strDelim  + getString(memRow, "SPECTAXONOMY2") + strDelim + getString(memRow, "SPECTAXONOMY3")
			+ ExtMemgetIxnUtils.getNDelimiters(3);

			hm_APSPT_TXNMY.put(getString(memRow, "md5key") + "-" 
					+ getString(memRow, "mds5addrtype")+ "-"
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + "-"
					+ getString(memRow, "specialty")+"-" + l_memrecno + "-" + row_Indicator,segmentData);
		}
		}
		// APSPT - Extra segments End
		return generateSegmentsforAPSPT(entRecNum,hm_APSPT_PROVBRDCRT,hm_APSPT_PROVBRDCRT1,hm_APSPT_TXNMY);
	}
	
	private Set<String> generateSegmentsforAPSPT(long entRecNum,HashMap<String, String>hm_APSPT_PROVBRDCRT,
			HashMap<String, String>hm_APSPT_PROVBRDCRT1,HashMap<String, String>hm_APSPT_TXNMY) throws Exception
	{
		Set <String>APSPT_Keys = new HashSet<String>();
		Set<String> segmentDataSet = new HashSet<String>();
		//if hm_APSPT_PROVBRDCRT is present only then create APSPT segment
		if(null!= hm_APSPT_PROVBRDCRT && hm_APSPT_PROVBRDCRT.size()>0 )
		{
			//get the keys
			APSPT_Keys = hm_APSPT_PROVBRDCRT.keySet();
			String split_keys[];
			for (Iterator<String> iterator = APSPT_Keys.iterator(); iterator.hasNext();) 
			{
				String APSPT_Key = iterator.next();
				split_keys= APSPT_Key.split("-");
				if(null!=hm_MemHead)
				{
					MemHead temp_memHead ;
					temp_memHead = (MemHead)hm_MemHead.get(split_keys[6]);
					l_strSrcCd = temp_memHead.getSrcCode();
					srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				}
				//PROVBRDCRT
				if (null != hm_APSPT_PROVBRDCRT.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5] +"-" + split_keys[6]+"-" + split_keys[7]))
				{
					//PROVSPTYTXNM 
					if (null == hm_APSPT_TXNMY.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6]+"-" + split_keys[7])) 
					{
						segmentData = ExtMemgetIxnUtils.getNDelimiters(5);
						hm_APSPT_TXNMY.put( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6]+"-" + split_keys[7],segmentData);
					}
					//SPLTYTXNMORG-END

					//create a full segment for PSPT
					segmentData = hm_APSPT_PROVBRDCRT.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6]+"-" + split_keys[7]) 
					+ hm_APSPT_TXNMY.get(  split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6]+"-" + split_keys[7])
					+ hm_APSPT_PROVBRDCRT1.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6]+"-" + split_keys[7])
					+ ExtMemgetIxnUtils.getNDelimiters(59)+srcCode_postprocess;

					if(split_keys[7].equalsIgnoreCase("0")){
						/*String comb_key = getCombAddresskey(split_keys[0], split_keys[1]);
						attrCode_Eligible_for_PtoA_Handler(segmentData, "APSPT",comb_key);*/
					}
					else {
						outputType = ProvEnum.APSPT.getValue();
						segmentDataSet.add(segmentData);
					}
				}
				//ORGBRDCRT-END
			}//for -End
		}
		return segmentDataSet;
	}

	@Override
	public Set<String> buildPADRSegment(List<MemAttrRow> orgPADRMemAttrList,
			long entRecNum) throws Exception {
		outputType = ProvEnum.PADR.getValue();
		for (MemRow memRow : orgPADRMemAttrList){
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
		if (l_strAttrCode.equalsIgnoreCase("CORRLOCATION") || l_strAttrCode.equalsIgnoreCase("SERVLOCATION")
				||l_strAttrCode.equalsIgnoreCase("PROV1099ADDR")|| l_strAttrCode.equalsIgnoreCase("PROVBILLADDR")
				||l_strAttrCode.equalsIgnoreCase("PROVCAPADDR") || l_strAttrCode.equalsIgnoreCase("PROVCSAADDR")
				|| l_strAttrCode.equalsIgnoreCase("PROVADDRNA")|| l_strAttrCode.equalsIgnoreCase("PRVCAPCKADDR")
				|| l_strAttrCode.equalsIgnoreCase("PRVPAYINADDR")|| l_strAttrCode.equalsIgnoreCase("PRVHMORELADR"))
		{
			//filter
			if(getString(memRow, "addrtype").length() > 0 
					&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") && ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt").length() > 0
					&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")&& ExtMemgetIxnUtils.getDateAsString(memRow, "termdt").length() > 0
					//Removed the filter check to populate the county address
					/*&& getString(memRow, "stline1").length() > 0 */
					/*&& getString(memRow, "city").length() > 0*/ && getString(memRow, "state").length() > 0
			/*&& getString(memRow, "zipcode").length() > 0*/)
			{
				getMemHeadValues(memRow);
				if(l_strAttrCode.equalsIgnoreCase("CORRLOCATION") 
						|| l_strAttrCode.equalsIgnoreCase("SERVLOCATION")){
					segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
					+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim 
					+ getString(memRow, "addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim 
					+ getString(memRow, "stline1") + strDelim + getString(memRow, "stline2") + strDelim 
					+ getString(memRow, "stline3") + strDelim + getString(memRow, "city") + strDelim 
					+ getString(memRow, "state") + strDelim + getString(memRow, "zipcode") + strDelim 
					+ getString(memRow, "zipextension") + strDelim + getString(memRow, "countycd") + strDelim 
					+ strBlank + strDelim + getString(memRow, "country") + strDelim + getString(memRow, "primaryaddress") 
					+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
					+ strDelim + getString(memRow, "DFCDC_mAudRecno") 
					+ strDelim + getString(memRow, "attrval1") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") 
					+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim 
					+ getString(memRow, "suppresstdzn") + strDelim 
					// CFF 2.4 changes start here
					+ getString(memRow, "addrstatefipscd") + strDelim
					+ getString(memRow, "addrcountyfipscd") + strDelim
					// CFF 2.4 changes end here
					+ getString(memRow, "geocode1") + strDelim 
					+ getString(memRow, "geocode2") + strDelim 
					//CFF 2.5b changes starts here
					/*+ getString(memRow, "attrval3")+ strDelim*/;
					//CFF 2.5b changes ends here
				}
				// CFF 2.4 Changes start here
				else {
					outputType = ProvEnum.PADR.getValue();
					segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
					+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim 
					+ getString(memRow, "addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim 
					+ getString(memRow, "stline1") + strDelim + getString(memRow, "stline2") + strDelim 
					+ getString(memRow, "stline3") + strDelim + getString(memRow, "city") + strDelim 
					+ getString(memRow, "state") + strDelim + getString(memRow, "zipcode") + strDelim 
					+ getString(memRow, "zipextension") + strDelim + getString(memRow, "countycd") + strDelim 
					+ strBlank + strDelim + strBlank + strDelim + strBlank
					+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
					+ strDelim + getString(memRow, "DFCDC_mAudRecno") 
					+ strDelim + getString(memRow, "attrval1") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") 
					+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim 
					+ getString(memRow, "suppresstdzn") + strDelim 
					// CFF 2.4 changes start here
					+ getString(memRow, "addrstatefipscd") + strDelim
					+ getString(memRow, "addrcountyfipscd") + strDelim
					// CFF 2.4 changes end here
					+ getString(memRow, "geocode1") + strDelim 
					+ getString(memRow, "geocode2") + strDelim 
					//CFF 2.5b changes starts here
					/*+ getString(memRow, "attrval3")+ strDelim*/;	
					//CFF 2.5b changes ends here
				}
				hm_PADR.put(getString(memRow, "md5key")+ getString(memRow, "addrtype")+ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+"-"+
						ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") , segmentData);

				//segmentData = generateSegmentsforCFF_APADR1(memRow, entRecNum, false);
				/*String comb_key = getCombAddresskey(getString(memRow, "md5key"),getString(memRow, "addrtype"));
				attrCode_Eligible_for_PtoA_Handler(segmentData,"APADR",comb_key);*/
			}
		}//filter end

		else if (l_strAttrCode.equalsIgnoreCase("PRVFACSTERE")) {

			//CFF 2.5b changes starts here
			segmentData = /*getString(memRow, "attrval")+ strDelim + */ 
				//CFF 2.5b changes ends here
				ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim
				+ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") /*+ strDelim + srcCodesDelimited;*/;
			hm_PRVFACSTERE.put(getString(memRow, "md5key")+ getString(memRow, "MDS5ADDRTYPE")+ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT") , segmentData);
		}
	}
		return generateSegmentsforPADR(hm_PADR, hm_PRVFACSTERE, entRecNum);
	}
	
	private Set<String> generateSegmentsforPADR(HashMap<String,String>hm_PADR,HashMap<String,String>hm_PRVFACSTERE,
			long entRecNum)  throws Exception
			{
		Set <String>PADR_Keys = new HashSet<String>();
		Set<String> segmentDataSet = new HashSet<String>();
		String split_keys[];
		//If PADR hash map not created 
		if(!hm_PADR.isEmpty())
		{
			PADR_Keys = hm_PADR.keySet();
			for (Iterator<String> iterator = PADR_Keys.iterator(); iterator.hasNext();) 
			{
				String segment_ORGFACSTERE;
				String PADR_Key =  iterator.next();
				split_keys = PADR_Key.split("-");
				if(null!= hm_PADR.get(PADR_Key))
				{
					segment_ORGFACSTERE =hm_PRVFACSTERE.get(split_keys[0]);
					if(segment_ORGFACSTERE==null){
						segment_ORGFACSTERE = ExtMemgetIxnUtils.getNDelimiters(1) /*+ srcCodesDelimited;*/;
					}
					/*if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
							.replace(srcCodesDelimited, strBlank).length() > 0) {*/
					segmentData =hm_PADR.get(PADR_Key) +segment_ORGFACSTERE + strDelim + ExtMemgetIxnUtils.getNDelimiters(60) + srcCodesDelimited;
					outputType = ProvEnum.PADR.getValue();
					segmentDataSet.add(segmentData);
					/*}*/
				}
			}
		}
		return segmentDataSet;
	}

	@Override
	public Set<String> buildPALTSegment(List<MemAttrRow> orgPALTMemAttrList,
			long entRecNum) throws Exception {
		outputType = ProvEnum.PALT.getValue();
		for (MemRow memRow : orgPALTMemAttrList){
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
		if (l_strAttrCode.equalsIgnoreCase("PROVALTSYSID") || l_strAttrCode.equalsIgnoreCase("NPI") 
				|| l_strAttrCode.equalsIgnoreCase("UPIN") || l_strAttrCode.equalsIgnoreCase("DEAID") 
				|| l_strAttrCode.equalsIgnoreCase("MEDICARE") || l_strAttrCode.equalsIgnoreCase("MEDICAID") 
				|| l_strAttrCode.equalsIgnoreCase("ENCLARITYID") || l_strAttrCode.equalsIgnoreCase("PROVLICENSE"))
		{
			//CFF 2.5b changes starts here
			if(/*getString(memRow, "idissuer").length() > 0 && */getString(memRow,"idnumber").length() > 0  
					&& ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")!= null && ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate").length() > 0
					&& ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")!=null && ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate").length() > 0){
				//CFF 2.5b changes ends here
				/*Getting the values from MEMHEAD_MAP against each memRecno*/
				if(null!=hm_MemHead)
				{
					MemHead temp_memHead ;
					temp_memHead = (MemHead)hm_MemHead.get(new Long(memRow.getMemRecno()).toString());
					l_strSrcCd = temp_memHead.getSrcCode();
					l_memIdNum = temp_memHead.getMemIdnum();
					l_strCaudrecno= new Long(temp_memHead.getCaudRecno()).toString();
					l_maudRecNo = new Long(temp_memHead.getMaudRecno()).toString();

				}
				//
				if(l_strAttrCode.equalsIgnoreCase("PROVLICENSE")){
					if (getString(memRow,"licensetype").length() > 0){
						segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" 
						+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "idissuer") + strDelim 
						+ getString(memRow, "idnumber") + strDelim + getString(memRow,"licensetype")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim 
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "termrsn") + strDelim 
						+ strBlank + strDelim + getString(memRow, "certnumber") + strDelim + getString(memRow, "status") 
						+ strDelim + getString(memRow, "agencyname") + strDelim 
						+ getString(memRow, "agencytypecd") + strDelim + getString(memRow, "agencyacrtxt") + strDelim 
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "agencyeffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "agencytermdt") + strDelim 
						+ getString(memRow, "agencytrmrsn") + strDelim + getString(memRow, "agencyemail") + strDelim 
						+ getString(memRow, "agencyurl")	+ strDelim + getString(memRow, "agcytelarcd") + strDelim 
						+ getString(memRow, "agencytelnum") + strDelim  + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
						+ strDelim + getString(memRow, "DFCDC_mAudRecno")
						+ strDelim + getString(memRow, "npiedsregind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"npiedseffectdt")+ strDelim;
						String key = getString(memRow, "IDNUMBER");
						if (key.contains("-")) 
							key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
						hm_PALT.put(getString(memRow, "licensetype")+"-"  +key+"-" + getString(memRow, "md5key")+"-" 
								+ getString(memRow, "MDS5ADDRTYPE")+"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")
								+"-"+ getString(memRow,"memrecno")+"-"+ getString(memRow, "termrsn")+ "-" +
								ExtMemgetIxnUtils.getDateAsString(memRow,"idissuedate") +"-"+
								ExtMemgetIxnUtils.getDateAsString(memRow,"idexpdate") , segmentData );
					}
				}else if (getString(memRow,"idtype").length() > 0){
					outputType = ProvEnum.PALT.getValue();
					segmentData = outputType + strDelim +  getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" 
					+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
					+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "idissuer") 
					+ strDelim + getString(memRow, "idnumber") + strDelim + getString(memRow, "idtype") + strDelim 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
					+ strDelim + getString(memRow, "idtermrsn") + strDelim + strBlank + strDelim 
					+ strBlank + strDelim + strBlank + strDelim 
					+ strBlank + strDelim  + getString(memRow, "agencytypecd") + strDelim + getString(memRow, "agencyacrtxt") + strDelim +
					 strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim 
					+ strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
					+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")
					+ strDelim + getString(memRow, "npiedsregind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"npiedseffectdt")+ strDelim;
					String key = getString(memRow, "IDNUMBER");
					if (key.contains("-")) 
						key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
					hm_PALT.put(getString(memRow, "IDTYPE")+"-"  +key+"-" + getString(memRow, "md5key")+"-" 
							+ getString(memRow, "MDS5ADDRTYPE")+"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")
							+"-"+ getString(memRow,"memrecno")+"-"+ getString(memRow, "idtermrsn")+ "-" +
							ExtMemgetIxnUtils.getDateAsString(memRow,"idissuedate") +"-"+
							ExtMemgetIxnUtils.getDateAsString(memRow,"idexpdate") , segmentData );

				}
			}
		}
		else  if(l_strAttrCode.equalsIgnoreCase("PRVALTIDSPEC")){

			String key = getString(memRow, "IDNUMBER");
			if (key.contains("-")) 
				key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
			String spec_key = getString(memRow, "IDTYPE")+"-" + key+"-" + getString(memRow, "md5key")+"-" + 
								getString(memRow, "MDS5ADDRTYPE")+"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT") +"-" + 
								getString(memRow,"memrecno");
			segmentData = getString(memRow, "specialtycd") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+ strDelim; 
			if(hm_PALT_Prov_Alt_ID_Specialty != null && !hm_PALT_Prov_Alt_ID_Specialty.isEmpty() )
			{
				if(hm_PALT_Prov_Alt_ID_Specialty.containsKey(spec_key)) //if contain key
				{
					ArrayList<String>speciality_list = new ArrayList<String>();
					speciality_list = hm_PALT_Prov_Alt_ID_Specialty.get(spec_key);
					speciality_list.add(segmentData);
					hm_PALT_Prov_Alt_ID_Specialty.put(spec_key, speciality_list);
				}
				else //if doesnot contain key
				{
					ArrayList<String>speciality_list = new ArrayList<String>();
					//speciality_list = hm_PALT_Prov_Alt_ID_Specialty.get(spec_key);
					speciality_list.add(segmentData);
					hm_PALT_Prov_Alt_ID_Specialty.put(spec_key, speciality_list);
				}
			}
			else if (hm_PALT_Prov_Alt_ID_Specialty.isEmpty())
			{
				ArrayList<String>speciality_list = new ArrayList<String>();
				speciality_list.add(segmentData);
				hm_PALT_Prov_Alt_ID_Specialty.put(spec_key, speciality_list);
			}
			}
		}
		return generateSegmentsforPALT(entRecNum);
	}
	
	
	private Set<String> generateSegmentsforPALT(long entRecNum)  throws Exception
	{
		HashMap<String,String> hm_PALT_Prov_Alt_ID_Specialty1 = new HashMap<String,String>();
		Set<String> segmentDataSet = new HashSet<String>();
		if (null != hm_PALT && hm_PALT.size() > 0) {			
			if(null != hm_PALT_Prov_Alt_ID_Specialty && hm_PALT_Prov_Alt_ID_Specialty.size() > 0){			
				String ProvAltIDSpecialty1_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty2_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty3_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty4_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty5_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				Set<String> hm_Prov_Alt_ID_Specialty_keys = new HashSet<String>();
				hm_Prov_Alt_ID_Specialty_keys = hm_PALT_Prov_Alt_ID_Specialty.keySet();
				for (Iterator<String> iterator1 = hm_Prov_Alt_ID_Specialty_keys.iterator(); iterator1.hasNext();) {
					String hm_Prov_Alt_ID_Specialty_key= (String) iterator1.next();
					//String[] split_keys1 ; 
					//split_keys1 = hm_Prov_Alt_ID_Specialty_key.split("-");
					ArrayList<String> valueList = hm_PALT_Prov_Alt_ID_Specialty.get(hm_Prov_Alt_ID_Specialty_key);
					int count = valueList.size();
					for (Iterator<String> iterator_value = valueList.iterator(); iterator_value.hasNext();) {
						String spec_info = (String) iterator_value.next();						
						switch (count)
						{
						case 1 : ProvAltIDSpecialty1_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						case 2 : ProvAltIDSpecialty2_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						case 3 : ProvAltIDSpecialty3_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						case 4: ProvAltIDSpecialty4_segmentData1 = spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						case 5 : ProvAltIDSpecialty5_segmentData1 =spec_info!=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						}	
						count --;
					}
					segmentData = ProvAltIDSpecialty1_segmentData1 +ProvAltIDSpecialty2_segmentData1+ProvAltIDSpecialty3_segmentData1+ProvAltIDSpecialty4_segmentData1+ProvAltIDSpecialty5_segmentData1+ExtMemgetIxnUtils.getNDelimiters(60)+srcCodesDelimited;
					hm_PALT_Prov_Alt_ID_Specialty1.put( hm_Prov_Alt_ID_Specialty_key, segmentData);
					segmentData = "";
				}
			}
			Set <String>PALT_Keys = new HashSet<String>();
			String split_keys[];						
			//get the keys
			PALT_Keys = new HashSet<String>(hm_PALT.keySet());
			//PALT_Keys = hm_PALT1.keySet();
			for (Iterator<String> iterator = PALT_Keys.iterator(); iterator.hasNext();) 
			{
				String PALT_Key = iterator.next();
				split_keys = PALT_Key.split("-");	
				String specialityKey = split_keys[0]+"-"+split_keys[1]+"-"+split_keys[2]+"-"+split_keys[3]+"-"+split_keys[4]+"-"+split_keys[5];
				if (null == hm_PALT_Prov_Alt_ID_Specialty.get( specialityKey))
				{
					segmentData = ExtMemgetIxnUtils.getNDelimiters(10)+ ExtMemgetIxnUtils.getNDelimiters(60)+srcCodesDelimited;
					hm_PALT_Prov_Alt_ID_Specialty1.put( specialityKey, segmentData);
				}
				//create a full segment						
				segmentData=hm_PALT.get(PALT_Key) +
				hm_PALT_Prov_Alt_ID_Specialty1.get(specialityKey);
				outputType = ProvEnum.PALT.getValue();
				segmentDataSet.add(segmentData);
			}					
		}
		return segmentDataSet;
	}

	@Override
	public String buildPRFSegment(List<MemAttrRow> OrgPRFMemAttrList,
			long entRecNum, boolean EPDSV2_Flag){
		outputType = ProvEnum.PRF.getValue();
		int provethnic_count = 1;
		String inActive = "Y";
		Date termDate = null;
		Date dtCurrentDate = new Date();
		boolean isV2MemrowPresent = false;
		for (MemRow memRow : OrgPRFMemAttrList){
			inActive = "Y";
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			ProvEnum attrCode = ProvEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			try {
			switch(attrCode) {
			case PROVCATEGORY:
			segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
			+ strDelim + "EPDS V2" + strDelim ;
			getHm_PRF().put("MEMHEAD", segmentData);
			
			segmentData = (getString(memRow, "attrval").length()<0 && EPDSV2_Flag ? "181" : getString(memRow, "attrval") )
			/*getString(memRow, "attrval")*/+ strDelim
			+ strBlank + strDelim + strBlank + strDelim 
			+ "0" + strDelim;
			getHm_PRF().put("PROVCATEGORY", segmentData);
			break;
		
			case PROVINACTIVE: if (!isV2MemrowPresent || l_strSrcCd.equalsIgnoreCase(ProvEnum.EPDSV2.getValue()))	{
			
				termDate = memRow.getDate("termdt");
				if(null != termDate){
					inActive = (dtCurrentDate.compareTo(termDate) < 0 ? "N" : "Y");
				}
	            segmentData = inActive + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") 
				+ strDelim + strBlank + strDelim + strBlank + strDelim + "0"
				+ strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + "0" 				
				+ strDelim + strBlank + strDelim 
				+ strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
	            if(l_strSrcCd.equalsIgnoreCase(ProvEnum.EPDSV2.getValue())){
	            	isV2MemrowPresent = true;
	            	segmentData = getTermAfterValidation(memRow, "PROVINACTIVEV2", segmentData , "termdt");
	            }
	            else if(!isV2MemrowPresent) {
	            	segmentData = getTermAfterValidation(memRow, "PROVINACTIVE", segmentData , "termdt");
	            }
				getHm_PRF().put("PROVINACTIVE", segmentData);
				
			}
			break;
			case PROVNAMEEXT:
		     segmentData = getString(memRow, "onmsuffix") + strDelim + getString(memRow, "onmfirst") + strDelim 
			+ getString(memRow, "onmlast") + strDelim + getString(memRow, "onmmiddle") + strDelim 
			+ getString(memRow, "onmpref") + strDelim 
			+ strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
			getHm_PRF().put("PROVNAMEEXT_1", segmentData);
			segmentData = getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
			+ getString(memRow, "DFCDC_mAudRecno") + strDelim ;
			getHm_PRF().put("PROVNAMEEXT_2", segmentData);
			break;
			
          case DOB:
		    segmentData = ExtMemgetIxnUtils.getStringDateAsString(memRow, "dateval") + strDelim 
			+ strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
			getHm_PRF().put("DOB", segmentData);
			break;
			
          case GENDER:
        	  segmentData = getString(memRow, "attrval") + strDelim 
  			+ strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
        	  getHm_PRF().put("GENDER", segmentData);
        	   break;
          case PRACTYPE:
		   segmentData = getString(memRow, "attrval") + strDelim 
			+ strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
		   getHm_PRF().put("PRACTYPE", segmentData);
		   break;
          case SSN:
		   segmentData = getString(memRow, "idnumber") + strDelim +
			strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
			getHm_PRF().put("SSN", segmentData);
			 break;
          case PROVETHNIC:
        	  segmentData = getString(memRow, "attrval") + strDelim ;
  			hm_PRF_PROVETHNIC.put("PROVETHNIC" + "-" + provethnic_count, segmentData);
  			provethnic_count ++ ;
  			break;
          case PRVNPIELIG:
        	  segmentData = getString(memRow, "codeval") + strDelim + 
  			strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
        	  getHm_PRF().put("PRVNPIELIG", segmentData);
        	  break;
          case PARIND:
        	  segmentData = getString(memRow, "codeval") + strDelim;
        	  getHm_PRF().put("PARIND", segmentData);
        	  break;
          case PRVCACTUSFCL:
        	  segmentData = getString(memRow, "attrval")  + strDelim; /*+ strDelim + srcCodesDelimited */;
        	  getHm_PRF().put("PRVCACTUSFCL", segmentData);
        	  break;
          case PRVGBDCD:
        	  segmentData = getString(memRow, "attrval");
        	  getHm_PRF().put("PRVGBDCD", segmentData); 
        	  break;
        	  
		
		}
				}
			catch (Exception e) {
				e.printStackTrace();
			}
			}
			return getSegmentDataforPRF(entRecNum, EPDSV2_Flag);
	}
	
	public String getSegmentDataforPRF(long entRecNum, boolean EPDSV2_Flag) {
		if(null != getHm_PRF().get("PROVCATEGORY") || null != getHm_PRF().get("PROVINACTIVE") || null != getHm_PRF().get("PROVNAMEEXT_1")
				|| null != getHm_PRF().get("DOB") || null != getHm_PRF().get("GENDER") || null != getHm_PRF().get("PRACTYPE") 
				|| null != getHm_PRF().get("SSN") || null != getHm_PRF().get("PROVETHNIC") || null != getHm_PRF().get("PRVNPIELIG")
				|| null != getHm_PRF().get("PARIND") || null != getHm_PRF().get("PROVNAMEEXT_2") ||
				null != getHm_PRF().get("PRVCACTUSFCL") || null != getHm_PRF().get("PRVGBDCD")){
			if (null == getHm_PRF().get("PROVCATEGORY"))
			{
				outputType = ProvEnum.PRF.getValue();
				segmentData = outputType+ strDelim + strBlank + strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim ;
				getHm_PRF().put("MEMHEAD", segmentData);			
				segmentData = (EPDSV2_Flag ? "181" : "")
				/*strBlank*/ + strDelim + strBlank + strDelim + strBlank + strDelim 
				+ "0" + strDelim ;
				getHm_PRF().put("PROVCATEGORY", segmentData);
			}
		}
		else
		{
			getHm_PRF().put("MEMHEAD", ExtMemgetIxnUtils.getNDelimiters(4));
			getHm_PRF().put("PROVCATEGORY", ExtMemgetIxnUtils.getNDelimiters(4));
		}
		if (null == getHm_PRF().get("PROVINACTIVE"))	{ // for defaulting 0 to CDC indicator.
			segmentData =  ExtMemgetIxnUtils.getNDelimiters(6) + "0" + ExtMemgetIxnUtils.getNDelimiters(5) + "0"  + ExtMemgetIxnUtils.getNDelimiters(4) + "0" + strDelim;
			getHm_PRF().put("PROVINACTIVE", segmentData);
		}
		if (null == getHm_PRF().get("PROVNAMEEXT_1")){
			segmentData =  ExtMemgetIxnUtils.getNDelimiters(7) + "0" + strDelim;
			getHm_PRF().put("PROVNAMEEXT_1", segmentData);
		}
		if (null == getHm_PRF().get("DOB"))  {
			segmentData =  ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim ;
			getHm_PRF().put("DOB", segmentData);
		}
		if (null == getHm_PRF().get("GENDER"))   {
			segmentData =  ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim;
			getHm_PRF().put("GENDER", segmentData);
		}
		if (null == getHm_PRF().get("PRACTYPE")) {
			segmentData =  ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim;
			getHm_PRF().put("PRACTYPE", segmentData);
		}
		if (null == getHm_PRF().get("SSN"))   {
			segmentData =  ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim ;
			getHm_PRF().put("SSN", segmentData);
		}

		/*
		 * new change in cff 2.7 mapping Ethnicity Code1 & Ethnicity Code2
		 * from PROVETHNIC attrval
		 * */ 

		if (null != hm_PRF_PROVETHNIC && hm_PRF_PROVETHNIC.size() > 0){

			String Ethnicity_Code1_segmentData = ExtMemgetIxnUtils.getNDelimiters(1);
			String Ethnicity_Code2_segmentData = ExtMemgetIxnUtils.getNDelimiters(1);

			Set<String> hm_PRF_PROVETHNIC_keys = new HashSet<String>();
			hm_PRF_PROVETHNIC_keys = hm_PRF_PROVETHNIC.keySet();

			for (Iterator<String> iterator = hm_PRF_PROVETHNIC_keys.iterator(); iterator.hasNext();) {
				String hm_PRF_PROVETHNIC_key = (String) iterator.next();
				String[] split_keys ; 
				split_keys = hm_PRF_PROVETHNIC_key.split("-");
				int count = Integer.parseInt(split_keys[1]);
				switch (count)
				{
				case 1 : Ethnicity_Code1_segmentData = (hm_PRF_PROVETHNIC.get(hm_PRF_PROVETHNIC_key)!=null ? hm_PRF_PROVETHNIC.get(hm_PRF_PROVETHNIC_key) : ExtMemgetIxnUtils.getNDelimiters(1));
				break;
				case 2 : Ethnicity_Code2_segmentData = (hm_PRF_PROVETHNIC.get(hm_PRF_PROVETHNIC_key)!=null ? hm_PRF_PROVETHNIC.get(hm_PRF_PROVETHNIC_key) : ExtMemgetIxnUtils.getNDelimiters(1));
				break;
				}
			}
			segmentData = Ethnicity_Code1_segmentData + Ethnicity_Code2_segmentData + ExtMemgetIxnUtils.getNDelimiters(2) + "0" + strDelim;
			getHm_PRF().put("PROVETHNIC", segmentData);
		}

		if (null == getHm_PRF().get("PROVETHNIC")) {
			segmentData =  ExtMemgetIxnUtils.getNDelimiters(4) + "0" + strDelim;
			getHm_PRF().put("PROVETHNIC", segmentData);
		}

		if (null == getHm_PRF().get("PRVNPIELIG")) {
			segmentData =  ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim;					
			getHm_PRF().put("PRVNPIELIG",segmentData);
		}
		if (null == getHm_PRF().get("PARIND"))    getHm_PRF().put("PARIND", ExtMemgetIxnUtils.getNDelimiters(1));//4
		if (null == getHm_PRF().get("PROVNAMEEXT_2"))  {
			segmentData =  ExtMemgetIxnUtils.getNDelimiters(2) + "0" + strDelim;	
			getHm_PRF().put("PROVNAMEEXT_2", segmentData);
		}

		if (null == getHm_PRF().get("PRVCACTUSFCL"))  
		{
			getHm_PRF().put("PRVCACTUSFCL", ExtMemgetIxnUtils.getNDelimiters(1) /*strDelim + srcCodesDelimited */ );//4
		}
		if (null == getHm_PRF().get("PRVGBDCD"))  
		{
			getHm_PRF().put("PRVGBDCD", ExtMemgetIxnUtils.getNDelimiters(0) /*strDelim + srcCodesDelimited */ );//4
		}
		
		segmentData = getHm_PRF().get("MEMHEAD")+ getHm_PRF().get("PROVCATEGORY") + getHm_PRF().get("PROVINACTIVE") + getHm_PRF().get("PROVNAMEEXT_1") + hm_PRF.get("DOB") + hm_PRF.get("GENDER") + hm_PRF.get("PRACTYPE") +
		getHm_PRF().get("SSN") + getHm_PRF().get("PROVETHNIC") + getHm_PRF().get("PRVNPIELIG") + getHm_PRF().get("PARIND") + getHm_PRF().get("PROVNAMEEXT_2")
		+ getHm_PRF().get("PRVCACTUSFCL") + getHm_PRF().get("PRVGBDCD");
			

		if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
				/*.replace(srcCodesDelimited, strBlank)*/.length() > 0) {
			segmentData = segmentData+strDelim + ExtMemgetIxnUtils.getNDelimiters(59) + srcCodesDelimited ; 
			outputType = "PRF";
			
			}
		return segmentData;
	}

	@Override
	public Set<String> buildPSPTSegment(List<MemAttrRow> orgPSPTMemAttrList,
			long entRecNum) throws Exception {
		outputType = ProvEnum.PSPT.getValue();
		for (MemRow memRow : orgPSPTMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
		if (l_strAttrCode.equalsIgnoreCase("PROVBRDCRT"))
		{
			if(getString(memRow, "specialtycd").length()>0){
				segmentData =outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" + strDelim ;
				hm_PSPT.put("MEMHEAD-"+getString(memRow, "md5key")+"-"
						+getString(memRow, "mds5addrtype")+"-"
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"
						+ getString(memRow, "specialtycd")+"-"+ ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+"-"
						+ getString(memRow, "primaryspec") + "-"
						+new Long(memRow.getMemRecno()).toString(), segmentData);

				segmentData = getString(memRow, "md5key") + strDelim
				+ getString(memRow, "mds5addrtype") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")
				+ strDelim + getString(memRow, "specialtycd")
				+ strDelim + getString(memRow, "primaryspec")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spectermdt")
				+ strDelim + getString(memRow, "spectermrsn") 
				// CFF 2.4 changes start here
				+ strDelim + getString(memRow, "boardcertcd")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "boardcertdt")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "boardcertexpirydt")
				+ strDelim + getString(memRow, "agencyname")
				// CFF 2.4 changes end here

				//CFF 2.5b changes start here

				+ strDelim + getString(memRow, "boardcertstatus")
				+ strDelim + getString(memRow, "boardcertlifeind")
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "boardrecertdt")
				+ strDelim + getString(memRow, "speccactuscredind") /*changed cff2.8 field from cactuscredind to speccactuscredind*/
				
				/*+ strDelim + getString(memRow, "specboardstatus") changed cff3.5 field from boardcertstatus to specboardstatus
				+ strDelim + getString(memRow, "speclifecertind") changed cff3.5 field from boardcertlifeind to speclifecertind
				+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "specrecertdt") changed cff3.5 field from boardrecertdt to specrecertdt*/
				//CFF 2.5b changes ends here

				+ strDelim + strBlank + strDelim + strBlank + strDelim + "0" + strDelim;
				/*if (getString(memRow, "mds5addrtype").equalsIgnoreCase("178")){
				psptPracticeType = true ;
			}*/
				hm_PSPT_BRDCRT.put(getString(memRow, "md5key")+"-"
						+getString(memRow, "mds5addrtype")+"-"
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"
						+ getString(memRow, "specialtycd")+"-"+ ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+"-"
						+ getString(memRow, "primaryspec") + "-"
						+new Long(memRow.getMemRecno()).toString(), segmentData);

				segmentData = getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
				+ getString(memRow, "DFCDC_mAudRecno")/* + strDelim +  srcCodesDelimited */;
				hm_PSPT.put("PROVBRDCRT_2-"+getString(memRow, "md5key")+"-"
						+getString(memRow, "mds5addrtype")+"-"
						+ ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"
						+ getString(memRow, "specialtycd")+"-"+ ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+"-"
						+ getString(memRow, "primaryspec") + "-"
						+new Long(memRow.getMemRecno()).toString(), segmentData);
			}
		}
		// PSPT extra attribute check - BEGIN
		else if (l_strAttrCode.equalsIgnoreCase("PROVSPLTYSVC"))	{
			segmentData = getString(memRow, "specialtysvc1") + strDelim + strBlank + strDelim + strBlank 
			+ strDelim + "0" + strDelim + getString(memRow, "specialtysvc2") + strDelim + strBlank + strDelim 
			+ strBlank + strDelim + "0" + strDelim + getString(memRow, "specialtysvc3") + strDelim + strBlank 
			+ strDelim + strBlank + strDelim + "0" + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank 
			+ strDelim + "0" + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim 
			+ "0" + strDelim;
			hm_PSPT_SPC.put(getString(memRow, "md5key")+"-"
					+getString(memRow, "mds5addrtype")+"-"
					+ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"
					+getString(memRow, "specialty")+"-"+ new Long(memRow.getMemRecno()).toString(), segmentData);

		}

		else if (l_strAttrCode.equalsIgnoreCase("PROVSPTYTXNM"))	{
			segmentData = getString(memRow, "spectaxonomy1") + strDelim + strBlank + strDelim + strBlank + strDelim 
			+ "0" + strDelim + getString(memRow, "spectaxonomy2") + strDelim + strBlank + strDelim + strBlank 
			+ strDelim + "0" + strDelim + getString(memRow, "spectaxonomy3") + strDelim + strBlank + strDelim 
			+ strBlank + strDelim + "0" + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim 
			+ "0" + strDelim + strBlank + strDelim;
			//538+ strDelim + strBlank + strDelim;
			hm_PSPT_TXNM.put(getString(memRow, "md5key")+"-"
					+getString(memRow, "mds5addrtype")+"-"
					+ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"
					+getString(memRow, "specialty")+"-"+ new Long(memRow.getMemRecno()).toString(), segmentData);

		}
		}
		return generateSegmentsforPSPT(hm_PSPT, entRecNum);
	}

	private Set<String> generateSegmentsforPSPT(HashMap<String,String> hm_PSPT, long entRecNum) throws Exception
	{
		Set <String>PSPT_Keys = new HashSet<String>();
		Set<String> segmentDataSet = new HashSet<String>();
		PSPT_Keys = new HashSet<String>(hm_PSPT_BRDCRT.keySet());

		String split_keys[];
		for (Iterator<String> iterator = PSPT_Keys.iterator(); iterator.hasNext();) 
		{
			String PSPT_Key = iterator.next();
			split_keys= PSPT_Key.split("-");
			//generate PSPT
			//write PSPT segment - BEGIN
			if(hm_PSPT.get("MEMHEAD-"+ split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6])!= null || 
					hm_PSPT_BRDCRT.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6]) != null ||
					hm_PSPT_SPC.get(split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6])!=null || 
					hm_PSPT_TXNM.get(split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6]) != null ||
					hm_PSPT.get("PROVBRDCRT_2-"+ split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6])!= null){

				//MEMHEAD
				if(null == hm_PSPT.get("MEMHEAD-"+ split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6]))
				{
					outputType = ProvEnum.PSPT.getValue();
					segmentData = outputType+ strDelim + strBlank + strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim ;
					hm_PSPT.put("MEMHEAD-"+ split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6], segmentData);
				}
			}
			else
			{
				hm_PSPT.put("MEMHEAD-"+ split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6], ExtMemgetIxnUtils.getNDelimiters(4));	
				hm_PSPT_BRDCRT.put(split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6], ExtMemgetIxnUtils.getNDelimiters(15));
			}
			//PROVBRDCRT
			if (null == hm_PSPT_BRDCRT.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6]))
			{

				segmentData = ExtMemgetIxnUtils.getNDelimiters(/*14*/18) + "0" + strDelim ;
				hm_PSPT_BRDCRT.put( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6], segmentData);
			}
			//PROVSPLTYSVC	
			if (null == hm_PSPT_SPC.get(split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6]))
			{
				segmentData = ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim
				+ ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim
				+ ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim ;

				hm_PSPT_SPC.put(split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6], segmentData);
			}
			//PROVSPTYTXNM
			if (null == hm_PSPT_TXNM.get(split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6])) 
			{
				segmentData = ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" 
				+ strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" + strDelim + ExtMemgetIxnUtils.getNDelimiters(3) + "0" + ExtMemgetIxnUtils.getNDelimiters(2);

				hm_PSPT_TXNM.put(split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6],segmentData);
			}
			//PROVBRDCRT_2
			if (null == hm_PSPT.get("PROVBRDCRT_2-"+ split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6])) 
			{
				segmentData = ExtMemgetIxnUtils.getNDelimiters(2) + "0";
				hm_PSPT.put("PROVBRDCRT_2-"+ split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+"-" + split_keys[5]+"-" + split_keys[6], segmentData);
			}

			//create a full segment
			segmentData = hm_PSPT.get("MEMHEAD-"+ split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+ "-" + split_keys[5]+"-" + split_keys[6]) 
			+ hm_PSPT_BRDCRT.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+ "-" + split_keys[5]+"-" + split_keys[6]) 
			+ hm_PSPT_SPC.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6]) 
			+ hm_PSPT_TXNM.get( split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[6])
			+ hm_PSPT.get( "PROVBRDCRT_2-" + split_keys[0]+"-" + split_keys[1]+"-" + split_keys[2]+"-" + split_keys[3]+"-" + split_keys[4]+ "-" + split_keys[5]+"-" + split_keys[6]);
			if (segmentData.replace(strDelim, strBlank).replace("0", strBlank)
					/*.replace(srcCodesDelimited, strBlank)*/.length() > 0) 
			{
				segmentData = segmentData + ExtMemgetIxnUtils.getNDelimiters(60) + strDelim + srcCodesDelimited;
				outputType = ProvEnum.PSPT.getValue();
				segmentDataSet.add(segmentData);
			}
			//write PSPT segment - END
		}
		return segmentDataSet;
	}
	
	@Override
	public String getSegmentDataforAPTTL(MemRow memRow, long entRecNum) throws Exception {
		try{	
			getMemHeadValues(memRow);
			outputType = ProvEnum.APTTL.getValue();
			if (ExtMemgetIxnUtils.isNotEmpty(l_strSrcCd) && ExtMemgetIxnUtils.isNotEmpty(l_memIdNum)) {
				segmentData = ExtMemgetIxnUtils.appendStr(outputType, getString(memRow, "DFCDC_evtctime"), Long.toString(entRecNum), 
											"EPDS V2", ExtMemgetIxnUtils.getString(memRow, "suffix"), l_strSrcCd, l_memIdNum, 
											ExtMemgetIxnUtils.getNDelimiters(2), getString(memRow, "DFCDC_evtinitiator"), 
											getString(memRow, "DFCDC_evtctime"), getString(memRow, "DFCDC_mAudRecno"), 
											ExtMemgetIxnUtils.getNDelimiters(59), srcCode_postprocess);
			return segmentData;
			}
		}
		catch(Exception e) {
			logInfo("Mde Error: Segment-APTTL entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	@Override
	public String getSegmentDataforAPADR(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.APADR.getValue();
		segmentData = ExtMemgetIxnUtils.appendStr(outputType, strBlank, Long.toString(entRecNum), 
											"EPDS V2", getString(memRow, "md5key"), getString(memRow, "addrtype"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"), ExtMemgetIxnUtils.getNDelimiters(3),
											l_strSrcCd, l_memIdNum, ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt"),
											ExtMemgetIxnUtils.getDateAsString(memRow, "termdt"), getString(memRow, "termrsn"), strDelim, 
											"0", ExtMemgetIxnUtils.getNDelimiters(30), "0", getString(memRow, "primaryaddress"),
											ExtMemgetIxnUtils.getNDelimiters(58), srcCode_postprocess);
		return segmentData;
	}

	@Override
	public String getSegmentDataforAPCLMACT(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.APCLMACT.getValue();
		segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "claimacttype") + strDelim 
		+ getString(memRow, "claimactreason") + strDelim + getString(memRow, "claimactcrittype") 
		+ strDelim + getString(memRow, "claimactlow") + strDelim + getString(memRow, "claimacthigh") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "claimacteffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "claimacttermdt") + strDelim + getString(memRow, "claimactermrsn") 
		+ strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim +
		//ChangeRequest Term dates not present on outbound files for A files WLPRD00793241
		ExtMemgetIxnUtils.getDateAsString(memRow, "claimacteffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "claimacttermdt") 
		+ strDelim + getString(memRow, "claimactermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
		+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim 
		+ getString(memRow, "acesclaimstpdept") + strDelim + getString(memRow, "acesprovrestid") 
		+ strDelim + getString(memRow, "acesprovreststat") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno") + ExtMemgetIxnUtils.getNDelimiters(60) +strDelim + srcCode_postprocess;
		return segmentData;
	}

	@Override
	public String getSegmentDataforAPGREL(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String sourceId;
		/*Related Provider Organization Id Source should be populated only if we have value in 
		Related Provider Organization Id*/
		if (getString(memRow, "provrelorgsrc").length()>0) {
			 sourceId = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "provrelorgsrc"))+"ORG";
		}
		else {
			sourceId = strBlank;
		}
		outputType = ProvEnum.APGREL.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + /*entRecNum */
		getString(memRow, "relmemidnum") + strDelim + "EPDS V2" + strDelim + entRecNum + strDelim + l_memIdNum
		+ strDelim + l_strSrcCd + strDelim + getString(memRow, "reltype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
		+ strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "remitmds5key") + strDelim 
		+ getString(memRow, "remitaddrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt") 
		+ strDelim + getString(memRow, "provrelorgid") + strDelim + getString(memRow, "provrelorgid") + strDelim + sourceId 
		+ strDelim + getString(memRow, "provrelorgreltype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgeffectdt") + strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")  + strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "relmemsrccode"))+"GRP" + strDelim + getString(memRow, "relmemidnum")+ strDelim 
		+ strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
		+ strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim 
		+ getString(memRow, "provaltid") + strDelim + getString(memRow, "provaltidtype") + strDelim 
		+ getString(memRow, "provaltidnm") + strDelim 
		// CFF 2.7 new feilds starts
		+ getString (memRow , "provgrpaltsrc") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow , "provaltideffectdt")+ strDelim
		// CFF 2.7 new feilds ends
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ExtMemgetIxnUtils.getNDelimiters(60)+strDelim + srcCode_postprocess;
		return segmentData;
	}

	@Override
	public String getSegmentDataforAPREL(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),getProp_relTypeCode());
		/*if (getString(memRow, "RELMEMSRCCODE").length()>0){
			srccode_lookup = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")); 
		}
		else{
			srccode_lookup = srcCode_postprocess;
			if (srccode_lookup.equalsIgnoreCase("WMS") && 
					getString(memRow, "reltype").equalsIgnoreCase("1917")){
				srccode_lookup = "EPDS1";							
			}
		}*/
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		
		outputType = ProvEnum.APREL.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt")
		+ strDelim + getString(memRow, "relmemidnum") + strDelim + getString(memRow, "entrecno") 
		+ strDelim + getString(memRow, "reltype") /*This needs to be mapped to 'reltype'. Not to 'relmemtype'.*/
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") 
		+ strDelim + getString(memRow, "relattrval1") + strDelim + l_strSrcCd + strDelim + l_memIdNum
		+ strDelim +
		// ChangeRequest Term dates not present on outbound files for A files WLPRD00793241
		ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno") + strDelim +
		/*2.7 deleted getString(memRow, "relattrval2") + strDelim + */getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")
		// CFF2.7 new feilds 
		+ strDelim + getString(memRow, "relattrval2") + strDelim + getString(memRow, "relattrval3") 
		+ strDelim + getString(memRow, "relattrval4") + strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt")
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS,CPF,ACES )*/
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(60) +srccode_lookup/* getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")) Changed for ENCLARITY bug-WLPRD00472537*/
		+ strDelim + reltype_code + strDelim + srcCode_postprocess;
		return segmentData;
	}

	@Override
	public String getSegmentDataforAPTXN(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.APTXN.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "taxonomycd")
		+ strDelim + getString(memRow, "taxonomyorgcd")+ strDelim + getString(memRow, "taxonomysetcd")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno") + strDelim + l_strSrcCd + strDelim + l_memIdNum
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim + getString(memRow, "termrsn")
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno") + ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCode_postprocess;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPNET(MemRow memRow, long entRecNum) throws Exception {
		
		getMemHeadValues(memRow);
		reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),getProp_relTypeCode());
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		String netsrccode = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "NET" ;
		String sptyEffectiveDate1="",sptyEffectiveDate2="",sptyEffectiveDate3 ="",sptyEffectiveDate4="",sptyEffectiveDate5 ="";
		//CFF 2.5b changes starts here:- speciality effective date is added instead of network effective date
		if (getString(memRow, "specialty1").length() > 0){sptyEffectiveDate1 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty1effdt");}
		if (getString(memRow, "specialty2").length() > 0){sptyEffectiveDate2 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty2effdt");}
		if (getString(memRow, "specialty3").length() > 0){sptyEffectiveDate3 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty3effdt");}
		if (getString(memRow, "specialty4").length() > 0){sptyEffectiveDate4 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty4effdt");}
		if (getString(memRow, "specialty5").length() > 0){sptyEffectiveDate5 = ExtMemgetIxnUtils.getDateAsString(memRow, "specialty5effdt");}
		//CFF 2.5b changes ends here
		outputType = ProvEnum.PNET.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		+ getString(memRow, "relatedid") + strDelim + getString(memRow, "reltype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "releffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "reltermdt") + strDelim + getString(memRow, "reltermrsn") 
		+ strDelim + strBlank + strDelim + getString(memRow, "specialty1") + strDelim 
		+ /*getDateAsString(memRow, "nweffectdt")*/ sptyEffectiveDate1 + strDelim + strBlank + strDelim 
		+ getString(memRow, "specialty2") + strDelim + /*getDateAsString(memRow, "nweffectdt")*/ sptyEffectiveDate2
		+  strDelim + strBlank + strDelim + getString(memRow, "specialty3") 
		+ strDelim + /*getDateAsString(memRow, "nweffectdt")*/sptyEffectiveDate3 + strDelim + strBlank 
		+  strDelim + getString(memRow, "specialty4") + strDelim + /*getDateAsString(memRow, "nweffectdt")*/ 
		sptyEffectiveDate4 + strDelim + strBlank +  strDelim + getString(memRow, "specialty5") 
		+ strDelim + sptyEffectiveDate5/*getDateAsString(memRow, "nweffectdt")*/ + strDelim + strBlank + strDelim + strBlank 
		+ strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim 
		+ strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank 
		+ strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank 
		+ strDelim + netsrccode + strDelim + getString(memRow, "networkid") + strDelim 
		// CFF 2.7 new feild
		+ getString (memRow, "networkidtype") + strDelim 
		// CFF 2.7 new feild
		+ExtMemgetIxnUtils. getDateAsString(memRow, "nweffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "nwtermdt") 
		+ strDelim + getString(memRow, "nwtermrsn") + strDelim + getString(memRow, "nwacceptpats") 
		+ strDelim + getString(memRow, "nwacceptmdcdpats") + strDelim + getString(memRow, "nwacceptmdcrpats") 
		+ strDelim + getString(memRow, "nwpatgenderpref") + strDelim + getString(memRow, "nwpatagepreffrom") 
		+ strDelim + getString(memRow, "nwpatageprefto") + strDelim + getString(memRow, "nwparind") 
		+ strDelim + getString(memRow, "nwpcpspectype") + strDelim + getString(memRow, "nwtimeoffiledays") 
		+ strDelim + getString(memRow, "nwdirdisplayind") + strDelim + getString(memRow, "nwpcpcurrmemcnt") 
		+ strDelim + getString(memRow, "nwpcpmemcapcnt") + strDelim +
		//CFF 2.5c changes starts here
		getString(memRow, "nwownerid") + strDelim 
		//CFF 2.5b changes ends here
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
		+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")/*+ strBlank + strDelim + strBlank 
		+ strDelim + getString(memRow, "mAudRecno")*/+ strDelim +/*2.8 changes*/getString(memRow, "emgphynparstind") + strDelim +/*2.8 changes*/getString(memRow, "patlgypartstind")+ strDelim +/*2.8 changes*/getString(memRow, "anestpartstind")+ strDelim + getString(memRow, "radlgypartstind") +strDelim + getString(memRow, "acespar") 
		+ strDelim + getString(memRow, "acesaqiind") + strDelim + getString(memRow, "acesaqipercent") + strDelim
		//CFF 2.5b changes starts here
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "aprveffdt")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "aprvenddt")+strDelim
		+ ExtMemgetIxnUtils.getString(memRow, "aprvind")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcpneteffdt")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcpnetenddt")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcpmenteffdt")+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcpmentenddt")+strDelim
		//2.5b changes ends here
		// CFF 2.4 Changes start here
		+ getString(memRow, "direcdispind")
		// CFF 2.4 changes end here
		+ strDelim + l_strSrcCd + strDelim + l_memIdNum
		//CFF 2.5b changes starts here
		+ strDelim + getString(memRow, "netparcd")
		//CFF 2.5b changes ends here
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS,CPF,ACES )*/
		//cff 3.1
		+ strDelim + getString(memRow,"agecattype") +/*Cff 3.8 changes*/strDelim +  getString(memRow, "relatedid") +strDelim+
		 ExtMemgetIxnUtils.getNDelimiters(58) + /*srcCode_postprocess */srccode_lookup+ strDelim + reltype_code + strDelim + srcCode_postprocess;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPRMB(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String rmbsrccode = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "RMB";
		reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),getProp_relTypeCode());
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		String sptyEffectiveDate1="",sptyEffectiveDate2="";
		String sptyEffectiveDate3 ="",sptyEffectiveDate4="",sptyEffectiveDate5 ="";
		if (getString(memRow, "specialty1").length()>0){sptyEffectiveDate1 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality1effectdt");}
		if (getString(memRow, "specialty2").length()>0){sptyEffectiveDate2 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality2effectdt");}
		if (getString(memRow, "specialty3").length()>0){sptyEffectiveDate3 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality3effectdt");}
		if (getString(memRow, "specialty4").length()>0){sptyEffectiveDate4 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality4effectdt");}
		if (getString(memRow, "specialty5").length()>0){sptyEffectiveDate5 = ExtMemgetIxnUtils.getDateAsString(memRow, "speciality5effectdt");}
		//Changes done for 2.4
		outputType = ProvEnum.PRMB.getValue();
		segmentData = outputType+ strDelim +getString(memRow, "DFCDC_evtctime")+ 
		strDelim +entRecNum+ strDelim +"EPDS V2"+ strDelim +getString(memRow, "md5key")
		+ strDelim +getString(memRow, "mds5addrtype")+ strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+ strDelim 
		+getString(memRow, "relatedid")+ strDelim +getString(memRow, "reltype")
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "releffdt")+ strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "reltermdt")+ strDelim +getString(memRow, "reltermrsn")
		+ strDelim +getString(memRow, "specialty1")+ strDelim +sptyEffectiveDate1+ strDelim 
		+getString(memRow, "specialty2")+ strDelim +sptyEffectiveDate2+ strDelim 
		+getString(memRow, "specialty3")+ strDelim +sptyEffectiveDate3+ strDelim 
		+getString(memRow, "specialty4")+ strDelim +sptyEffectiveDate4+ strDelim 
		+getString(memRow, "specialty5")+ strDelim +sptyEffectiveDate5+ strDelim +strBlank
		+ strDelim +strBlank+ strDelim +strBlank+ strDelim +strBlank+ strDelim +strBlank
		+ strDelim +strBlank+ strDelim +strBlank+ strDelim +strBlank+ strDelim +strBlank
		+ strDelim +strBlank+ strDelim +rmbsrccode/*Reimbursement Identifier System of Record 
			field should have source name + RMB*/
		+ strDelim + /*rmbarrangeid+ strDelim 
			+REI+ strDelim +agreeid+ strDelim +AGR+ strDelim +panelid+ strDelim +PLI*/
		getString(memRow, "rmbarrangeid")+ strDelim +getString(memRow, "rmbarrangeidtype")+ strDelim +
		getString(memRow, "agreeid")+ strDelim +getString(memRow, "agreeidtype")+ strDelim +
		getString(memRow, "panelid")+ strDelim +getString(memRow, "panelidtype")+ strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangeeffdt")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "rmbarrangetermdt")
		+ strDelim +getString(memRow, "rmbarrangetermrsn")+ strDelim + getString(memRow,"wgscontcode")
		+ strDelim +ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "NET"
		/*replaced EPDSV2*/
		/*Network Identifier Source Code field should have source name + NET*/
		+ strDelim +getString(memRow, "networkid")+ strDelim 
		+getString(memRow, "networkidtype")+ strDelim +getString(memRow, "ctrctversion")+ strDelim 
		+getString(memRow, "ctrctinclusion")+ strDelim +getString(memRow, "reimbtiercode")+ strDelim 
		// new feild in 2.7 starts
		+ getString(memRow, "epdsv2ctrctdays") + strDelim
		//new feild in 2.7 ends
		+getString(memRow, "nwffscaptype")+ strDelim +getString(memRow, "DFCDC_evtinitiator")+ strDelim 
		+getString(memRow, "DFCDC_evtctime")+ strDelim +getString(memRow, "DFCDC_mAudRecno")+ strDelim 
		//new field in 2.8
		+getString(memRow, "emgphynparstind")+ strDelim + getString(memRow, "patlgypartstind")+ strDelim + getString(memRow, "anestpartstind")+ strDelim + getString(memRow, "radlgypartstind") + strDelim 

		+getString(memRow, "wmsndbgrpind")+ strDelim +getString(memRow, "wmsndbctrlcd")+ strDelim 
		+getString(memRow, "wmsndbctrlvarn")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "wmsndblastcrddt")
		+ strDelim + getString(memRow, "wmsndbstscd")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "wmsndbstsdt")
		+ strDelim + getString(memRow,"wmsndbtaxid")+ strDelim +getString(memRow, "acesspapipind")
		+ strDelim + getString(memRow, "acesspapipfrqind")+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow,"acesspapipeffectdt")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"acesspapipenddt")+ strDelim
		//2.5b Changes -Begin
		+ getString(memRow, "prmptpyind")+ strDelim 
		+ getString(memRow, "prmptpctdiscamt")+ strDelim
		+ getString(memRow, "rmtmethcd")+ strDelim 
		//2.5b Changes -End
		+ l_strSrcCd+ strDelim +l_memIdNum + strDelim
		// new feild in 2.7 starts
		+ getString(memRow, "facetscaptype") + strDelim + getString(memRow, "facetsprefix")	+ strDelim 	
		+ getString(memRow, "facetscapreltype") + strDelim + getString(memRow, "facetscrprid") + strDelim 
		+ getString(memRow, "facetsadjper") + strDelim + getString(memRow, "facetscapcycleid") + strDelim 
		+ getString(memRow, "directoryind") + strDelim + getString(memRow, "dirdisplayopt")
		//new feild in 2.7 ends
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS,CPF,ACES..) + reltypecode*/
		//new field in 2.8
		+ strDelim + getString (memRow, "acptnewptind") +strDelim 
		+/*Cff 3.8 changes*/getString(memRow, "relatedid")+strDelim + ExtMemgetIxnUtils.getNDelimiters(59)+ /*srcCode_postprocess*/srccode_lookup+ strDelim + reltype_code + strDelim + srcCode_postprocess;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPWTH(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PWTH.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim +"EPDS V2"
		+ strDelim + /*WLPRD00990801: from mds5key to md5key*/getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim 
		+ getString(memRow, "termrsn")+ strDelim + getString(memRow, "stline1") + strDelim + getString(memRow, "stline2") 
		+ strDelim 	+ getString(memRow, "stline3") + strDelim + getString(memRow, "city") + strDelim 
		+ getString(memRow, "state") + strDelim + getString(memRow, "zip")+ strDelim 
		+ getString(memRow, "zipextension") + strDelim +getString(memRow, "countycd") + strDelim 
		+ getString(memRow, "country") + strDelim + getString(memRow, "specialty1") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "speclty1effectdt") + strDelim + getString(memRow, "specialty2") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "speclty2effectdt") + strDelim + getString(memRow, "specialty3") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "speclty3effectdt")+ strDelim + getString(memRow, "specialty4")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "speclty4effectdt")+ strDelim + getString(memRow, "specialty5") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "speclty5effectdt") + strDelim + getString(memRow, "legcyrmbid1") + strDelim 
		+ getString(memRow, "legcyrmbid1typcd") + strDelim + getString(memRow, "legcyrmbid2") + strDelim 
		+ getString(memRow, "legcyrmbid2typcd")+ strDelim + getString(memRow, "legcyrmbid3")+ strDelim 
		+ getString(memRow, "legcyrmbid3typcd")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbeffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "legcyrmbtrmdt") + strDelim + getString(memRow, "legcyrmbrsncd")+ strDelim 
		+ getString(memRow, "wgscontcode") + strDelim + getString(memRow, "networkid") + strDelim 
		+ getString(memRow, "networkidtype") + strDelim + getString(memRow, "withhldperc") + strDelim 
		+ getString(memRow, "withhldtypecd") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "withhldeffdt")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "withhldtermdt") + strDelim +getString(memRow, "withhldrsncd") + strDelim
		+ l_strSrcCd + strDelim + l_memIdNum + strDelim+ /*CFF 3.8 chnages*/ getString(memRow, "relmemidnum")+ strDelim+ ExtMemgetIxnUtils.getNDelimiters(29) + srcCode_postprocess;
		return segmentData;
	}

	
	@Override
	public String getSegmentDataforGNET(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		outputType = ProvEnum.GNET.getValue();

		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+  /*entRecNum*/getString(memRow, "relmemidnum") + strDelim + "EPDS V2" + strDelim + entRecNum + strDelim + getString(memRow,"RELMEMSRCCODE") + strDelim 
		+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")  + strDelim + getString(memRow, "termrsn") + strDelim 
		// CFF 2.7 new feild
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		+ getString(memRow, "provrelorgid") + strDelim 	+ getString(memRow, "provrelorgsrc") + strDelim
		+ getString(memRow, "provrelorgreltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgeffectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgtermdt") + strDelim + /*WLPRD00990801: mds5key to md5key*/getString(memRow, "md5key") + strDelim
		+ getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		+ getString(memRow, "networkid") + strDelim 
		//CFF 2.8 changes
		+getString(memRow,"ntwkidtypecd") + strDelim 
		+  ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "NET" //WLPRD01168543
		+ strDelim + getString(memRow, "nwownerid")   
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "grpneteffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "grpnettermdt")  + strDelim + getString(memRow, "grpnettermrsn")
		// passing trimmed sourccode and reltype for 2 lookups 
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30)+ /*getSrcCodeforPostProcess(getString(memRow,"RELMEMSRCCODE"))*/srccode_lookup + strDelim + "GRP"
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"provrelorgsrc")) + strDelim + "ORG"					
		+ strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforGRMB(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		srcCode_postprocess = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		String provrelorgsrc_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "provrelorgsrc"));
		String provrelorgreltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "provrelorgreltype"),getProp_relTypeCode());

		outputType = ProvEnum.GRMB.getValue();

		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "relmemidnum") 
		+ strDelim + "EPDS V2" + strDelim +  /*memRow.getMemRecno()*/  entRecNum
		+ strDelim + getString(memRow, "relmemsrccode") + strDelim + getString(memRow, "reltype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim 
		+ getString(memRow, "termrsn") + strDelim + getString(memRow, "provrelorgid") + strDelim 
		+ getString(memRow, "provrelorgsrc") + strDelim + getString(memRow, "provrelorgreltype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgeffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgtermdt")
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim /*+ strBlank + strDelim + strBlank + strDelim 
		+ strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank 
		+ strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim */
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbreladdreffdt") + strDelim + getString(memRow, "grprmbid") + strDelim 
		+ getString(memRow, "rmbarrangetype") + strDelim + ExtMemgetIxnUtils.getSourceCode(getString(memRow, "srcidentifier"), l_strSrcCd) + "RMB" //WLPRD01168543
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbeffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "grprmbtermdt") 
		+ strDelim + getString(memRow, "grprmbtermrsn") + strDelim + getString(memRow, "caremgmtmpmtier") 
		+ strDelim + getString(memRow, "ctrctid") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "ctrctideffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "ctrctidtermdt") + strDelim + getString(memRow, "ctrctidtermrsn") 
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS,CPF,ACES..)*/
		//1st lookup
		+ ExtMemgetIxnUtils.getNDelimiters(30)+ srcCode_postprocess + strDelim +"GRP" + strDelim
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS,CPF,ACES..)*/
		//2nd lookup:(The lookup is not required for GRMB as the field populated is :provrelorgid )
		+ provrelorgsrc_postprocess + strDelim + provrelorgreltype_code + strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPALTROL(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PALTROL.getValue();

		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim +"EPDS V2"	+ strDelim 
		+ getString(memRow, "rolloversrccode") + strDelim + getString(memRow, "rolloversrcval") + strDelim 
		+ getString(memRow, "rolloversrctype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rolloversrcissuedt") + strDelim 
		// CFF 2.7 new feild 
		+ getString(memRow, "rollovertranseqno") + strDelim
		+ getString(memRow, "rolloverrecipntsrccode") + strDelim + getString(memRow, "rolloverrecipntsrcval")+ strDelim 
		+ getString(memRow, "rolloverrecipnttype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rolloverrecipntissuedt") + strDelim 
		+ getString(memRow, "rollovergrpprocind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rollovergrpprocdt") + strDelim 
		+ getString(memRow, "rolloverisgprocind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rolloverisgprocdt")+ strDelim 
		+ getString(memRow, "rollovercsgprocind") + strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "rollovercsgprocdt") + strDelim 
		+ getString(memRow, "rolloverltrtype") + strDelim + getString(memRow, "rolloveridcardind") + strDelim 
		+ getString(memRow, "rolloversenderid") + strDelim + getString(memRow, "rolloverreceipnt") + strDelim 
		+ getString(memRow, "rolloverreceipntperc") + strDelim + getString(memRow, "rolloverind") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "rollovereffectdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "rollovertermdt")+ strDelim 
		+ getString(memRow, "rollovertermrsn")+ strDelim + getString(memRow, "DFCDC_evtinitiator")+ strDelim 
		+ getString(memRow, "DFCDC_evtctime")+ strDelim + getString(memRow, "DFCDC_mAudRecno")/*+ strDelim + getString(memRow, "networkid") + strDelim + getString(memRow, "networkidtype")*/ + ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPAOF(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PAOF.getValue();

		// CFF 2.4 changes end here
		segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim 
		+ getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim + getString(memRow, "attrval") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno") +ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited ;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPATTS(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		srcCode_postprocess = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		outputType = ProvEnum.PATTS.getValue();

		segmentData = outputType + strDelim
		+ strDFCDC_evtctime + strDelim
		+ entRecNum + strDelim + "EPDS V2" + strDelim
		// CFF 2.4 changes starts here
		//+ getString(memRow, "attestlvltype") + strDelim
		// CFF 2.4 changes ends here
		+ getString(memRow, "atteststate") + strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "attesteffectdt") + strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "attesttermdt") + strDelim
		+ getString(memRow, "attesttermrsn") + strDelim
		+ getString(memRow, "orgnpi") + strDelim
		+ getString(memRow, "orgmedicaid") + strDelim
		+ getString(memRow, "orgtxnmy") + strDelim
		+ getString(memRow, "md5key") + strDelim
		+ getString(memRow, "mds5addrtype") + strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")
		+ strDelim + getString(memRow, "fedtaxid") + strDelim
		+ getString(memRow, "fedtaxidtype") + strDelim
		+ getString(memRow, "providerid") + strDelim
		+ getString(memRow, "provreltypcd") + strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "provreleffectdt") + strDelim
		+ getString(memRow, "provnpi") + strDelim
		+ getString(memRow, "provmedicaid") + strDelim
		+ getString(memRow, "provtxnmy") + strDelim
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim
		+ strDFCDC_evtctime + strDelim
		+ getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(12) + strDelim +  srcCodesDelimited ;
		return segmentData;

	}
	@Override
	public String getSegmentDataforPALIAS(MemRow memRow, long entRecNum)
	throws Exception {
		getMemHeadValues(memRow);

		outputType = ProvEnum.PALIAS.getValue();
		segmentData =outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "onmtype") + strDelim + getString(memRow, "onmfirst")
		+ strDelim + getString(memRow, "onmlast") + strDelim + getString(memRow, "onmmiddle") + strDelim 
		+ getString(memRow, "onmsuffix") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim
		+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim +  srcCodesDelimited ;
		return segmentData;
	}
	@Override
	public String getSegmentDataforPTTL(MemRow memRow, long entRecNum)
	throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PTTL.getValue();
		
		segmentData =outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim 
		+ "EPDS V2" + strDelim + getString(memRow, "suffix") + strDelim + strBlank + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(12) +strDelim +  srcCodesDelimited ;
		
		return segmentData;
	}
	@Override
	public String getSegmentDataforPEDU(MemRow memRow, long entRecNum)
	throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PEDU.getValue();
		String schoolName = ExtMemgetIxnUtils.rmvAllNewLine(getString(memRow, "schoolname"));
		segmentData =outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + schoolName + strDelim + getString(memRow, "stline1") + strDelim 
		+ getString(memRow, "stline2") + strDelim + getString(memRow, "city") + strDelim + getString(memRow, "state") + strDelim + getString(memRow, "zipcode") + strDelim + getString(memRow, "zipextension") 
		+ strDelim + getString(memRow, "countycd") + strDelim + getString(memRow, "country") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "attendedfromdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "attendedtodt") + strDelim + getString(memRow, "gradyear") 
		+ strDelim + getString(memRow, "degree") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "degreeawarddt") 
		+ strDelim 
		//2.5b changes start here
		+ getString(memRow, "trainingtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "trainingeffectdt")+strDelim
		//2.5b changes end here
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(12) + strDelim +  srcCodesDelimited;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPLANG(MemRow memRow, long entRecNum)
	throws Exception {
		getMemHeadValues(memRow);

		outputType = ProvEnum.PLANG.getValue();
		segmentData = outputType + strDelim
		+ getString(memRow, "DFCDC_evtctime") + strDelim
		+ entRecNum + strDelim + "EPDS V2" + strDelim
		+ getString(memRow, "langcd") + strDelim
		// CFF 2.4 changes start here
		//+ getString(memRow, "langwritten") + strDelim
		//+ getString(memRow, "langspoken") + strDelim
		// CFF 2.4 changes end here
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim
		+ getString(memRow, "DFCDC_evtctime") + strDelim
		+ getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(12)+strDelim + srcCodesDelimited ;
		return segmentData;
	}
	
	@Override
	public String getSegmentDataforPBREL(MemRow memRow, long entRecNum)
			throws Exception {
		
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		outputType = ProvEnum.PBREL.getValue();
		String sourceId;
		if (getString(memRow, "relorgmemidnum").length()>0)
			sourceId = "EPDS V2";
		else
			sourceId = strBlank;

		/*if (getString(memRow, "RELMEMSRCCODE").length()>0){
			srccode_lookup = getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE")); 
		}
		else{
			srccode_lookup = getSrcCodeforPostProcess(l_strSrcCd);
		}*/
		
		outputType = ProvEnum.PBREL.getValue();
	
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + /*entRecNum*/
		getString(memRow, "relmemidnum") + strDelim + "EPDS V2" + strDelim + entRecNum /*getString(memRow, "relmemidnum")*/ 
		+ strDelim + /*getString(memRow, "relmemsrccode")Commented as per onsite on July 17*/"EPDS V2"  + strDelim + getString(memRow, "reltype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
		+ strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "relorgmemidnum") 
		+ strDelim + /*getString(memRow, "relorgsrccode")Commented as per onsite on July 17*/sourceId + strDelim + getString(memRow, "relorgreltype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt") 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "reladdrtermdt") + strDelim + getString(memRow, "reladdrtermrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS, CPF, ACES )*/
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30) + /*getSrcCodeforPostProcess(getString(memRow, "relmemsrccode"))*/srccode_lookup
		+ strDelim + "BUS"
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "relorgsrccode"))
		+ strDelim + ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "relorgreltype"),getProp_relTypeCode()) + strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPCLMACT(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PCLMACT.getValue();

		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "claimacttype") + strDelim + getString(memRow, "claimactreason") 
		+ strDelim + getString(memRow, "claimactcrittype") + strDelim + getString(memRow, "claimactlow") + strDelim 
		+ getString(memRow, "claimacthigh") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "claimacteffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "claimacttermdt") + strDelim + getString(memRow, "claimactermrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")+ strDelim + srcCodesDelimited ;
		return segmentData;
		
	}

	@Override
	public String getSegmentDataforPCNTC(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PCNTC.getValue();

		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "onmfirst") + strDelim 
		+ getString(memRow, "onmlast") + strDelim + getString(memRow, "onmmiddle") + strDelim 
		+ getString(memRow, "rolecd") + strDelim + getString(memRow, "titlecd")  
		// CFF 2.4 changes start here
		//+ getString(memRow, "mds5addrtype") 
		// CFF 2.4 changes end here
		+ strDelim + getString(memRow, "phicc") + strDelim 
		+ getString(memRow, "pharea") + strDelim + getString(memRow, "phnumber") + strDelim 
		+ getString(memRow, "phextn") + strDelim + getString(memRow, "teltype") + strDelim 
		// CFF 2.4 changes starts here
		//				+ getDateAsString(memRow, "teleffectdt") + strDelim + getDateAsString(memRow, "teltermdt") + strDelim 
		//				+ getString(memRow, "teltermrsn") + strDelim 
		// CFF 2.4 changes ends here
		+ getString(memRow, "fxarea") + strDelim 
		+ getString(memRow, "fxnumber") + strDelim 
		+ getString(memRow, "faxtype") + strDelim 
		// CFF 2.4 changes starts here
		/*+ getDateAsString(memRow, "faxeffectdt") + strDelim 				
	+ getDateAsString(memRow, "faxtermdt") + strDelim + getString(memRow, "faxtermrsn") + strDelim */
		// CFF 2.4 changes ends here
		+ getString(memRow, "url") + strDelim + getString(memRow, "urltype") + strDelim
		// CFF 2.4 changes starts here
		/* + getDateAsString(memRow, "urleffectdt") + strDelim + getDateAsString(memRow, "urltermdt") + strDelim 
	+ getString(memRow, "urltermrsn") + strDelim*/
		// CFF 2.4 changes ends here
		+ getString(memRow, "emailaddr") + strDelim 
		+ getString(memRow, "emailtype") + strDelim  
		// CFF 2.4 changes starts here
		/*+ getDateAsString(memRow, "emaileffectdt") + strDelim 
	+ getDateAsString(memRow, "emailtermdt") + strDelim + getString(memRow, "emailtermrsn") + strDelim 
	+ getDateAsString(memRow, "cntceffectdt") + strDelim + getDateAsString(memRow, "cntctermdt") + strDelim*/
		// CFF 2.4 changes ends here
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno") +ExtMemgetIxnUtils.getNDelimiters(60) + strDelim + srcCodesDelimited;
		
		return segmentData;
		
	}
		
		@Override
	public String getSegmentDataforPCRED(MemRow memRow, long entRecNum)
			throws Exception {
			getMemHeadValues(memRow);
			outputType = ProvEnum.PCRED.getValue();

			segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
			+ strDelim + "EPDS V2" + strDelim + getString(memRow, "attrval") + strDelim 
			+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
			+ strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator")
			+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim + getString(memRow, "attrval2")
			+ ExtMemgetIxnUtils.getNDelimiters(29)+ strDelim + srcCodesDelimited ;
			return segmentData;
	}

	@Override
	public String getSegmentDataforPDBA(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		String networkRelFeilds;
		if(getString(memRow, "networkid").length()>0){
			networkRelFeilds = getString(memRow, "networkid") + strDelim + getString(memRow, "networkidtyp") + strDelim + 
						ExtMemgetIxnUtils.getDateAsString(memRow, "networkeffdt") + strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd) 
						+ Constants.NET;
		}
		else {
			networkRelFeilds = ExtMemgetIxnUtils.getNDelimiters(3);
		}
		outputType = ProvEnum.PDBA.getValue();
		segmentData =  outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim
		+ entRecNum + strDelim + "EPDS V2" + strDelim + /*WLPRD00990801: from mds5key to md5key*/getString(memRow, "md5key") + strDelim
		+ getString(memRow, "mds5addrtype") + strDelim 	+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")
		+ strDelim + getString(memRow, "relsrcprovid")	+ strDelim + getString(memRow, "reltype")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "releffdt")+ strDelim /*+ getString(memRow, "networkid")
		+ strDelim + getString(memRow, "networkidtyp") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "networkeffdt")
		// new feild CFF 2.7 : Network Identifier Souce Code,Specialty Code Effective Date 
		+ strDelim + l_strSrcCd */ + networkRelFeilds 
		+ strDelim + getString(memRow, "spec1code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec1effdt")
		+ strDelim + getString(memRow, "spec2code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec2effdt")
		+ strDelim + getString(memRow, "spec3code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec3effdt")
		+ strDelim + getString(memRow, "spec4code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec4effdt")
		+ strDelim + getString(memRow, "spec5code")	+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "spec5effdt")
		+ strDelim + getString(memRow, "busasname")	+ strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "busnmeffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "busnmtrmdt")
		+ strDelim + getString(memRow, "busnmtrmrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator")
		+ strDelim + getString(memRow, "DFCDC_evtctime")+ strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ strDelim + getString(memRow, "diridntfier")	+ strDelim + getString(memRow, "dirdispopt") + ExtMemgetIxnUtils.getNDelimiters(12)	
		+ strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPDSTC(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PDSTC.getValue();
		segmentData = outputType + strDelim +  getString(memRow, "DFCDC_evtctime") 
		+ strDelim + entRecNum + strDelim + "EPDS V2" + strDelim + getString(memRow, "attrval") +
		strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")
		+strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator")
		+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")+ strDelim 
		//cff 3.1 changes
		+ getString(memRow,"MD5KEY")+ strDelim + getString(memRow,"MDS5ADDRTYPE")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow,"MDS5ADDREFFECTDT") + ExtMemgetIxnUtils.getNDelimiters(11)+ strDelim + srcCodesDelimited ;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPGREL(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		outputType = ProvEnum.PGREL.getValue();
		String sourceId;
		if (getString(memRow, "provrelorgid").length()>0)
			sourceId = "EPDS V2";
		else
			sourceId = strBlank;


        outputType = ProvEnum.PGREL.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + /*entRecNum */
		getString(memRow, "relmemidnum")+ strDelim + "EPDS V2" + strDelim + /*getString(memRow, "relmemidnum")*/ 
		entRecNum + strDelim + /*getString(memRow, "relmemsrccode")Commented as per onsite on July 17*/
		"EPDS V2" +strDelim
		//cff 2.8 changes
		+ getString(memRow, "grpdynamicind")+ strDelim
		+ getString(memRow, "reltype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
		+ strDelim + getString(memRow, "termrsn") + strDelim + getString(memRow, "remitmds5key") + strDelim 
		+ getString(memRow, "remitaddrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "remitaddreffectdt")+ strDelim
		// CFF 2.7 new feild
		+ getString(memRow, "remitaddrpayeenm")+ strDelim 
		+ getString(memRow, "provrelorgid") + strDelim +/*getString(memRow, "provrelorgsrc")
		Commented as per onsite on July 17*/ sourceId
		+ strDelim + getString(memRow, "provrelorgreltype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgeffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "provrelorgtermdt") 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reladdrtermdt") + strDelim + getString(memRow, "reladdrtermrsn") 
		+ strDelim + getString(memRow, "provaltid") + strDelim + getString(memRow, "provaltidtype") + strDelim 
		+ getString(memRow, "provaltidnm") + strDelim + getString(memRow, "svctabid") + strDelim 
		+ getString(memRow, "rmbarrangetype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "svctabideffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "svctabidtermdt") + strDelim + getString(memRow, "svctabidtermrsn") 
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS, CPF, ACES )*/
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30)+ /*getSrcCodeforPostProcess(getString(memRow, "relmemsrccode"))*/srccode_lookup
		+ strDelim + "GRP"
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "provrelorgsrc"))
		+ strDelim + ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "provrelorgreltype"),getProp_relTypeCode()) + strDelim + srcCodesDelimited;
		
		return segmentData;
	}

	@Override
	public String getSegmentDataforPNOTE(MemRow memRow, long entRecNum)
			throws Exception {
		outputType = ProvEnum.PNOTE.getValue();
		String note = ExtMemgetIxnUtils.rmvAllNewLine(getString(memRow, "note"));
		getMemHeadValues(memRow);
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim 
		+ "EPDS V2" + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + strBlank 
		+ strDelim + strBlank + strDelim + note + strDelim + getString(memRow, "notetype")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "entrydt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")+ strDelim 
		//cff 2.7 new field :Applicable Source System for Note.    
		+ getString(memRow, "SRCIDENTIFIER") +  ExtMemgetIxnUtils.getNDelimiters(12)+strDelim + srcCodesDelimited ;

		return segmentData;
	}

	@Override
	public String getSegmentDataforPOFACS(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.POFACS.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "ofcaccesstype") + strDelim 
		+ getString(memRow, "ofcaccesscd") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "ofcaccesseffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "ofcaccessdt") + strDelim + getString(memRow, "ofcaccesstermrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno") +ExtMemgetIxnUtils.getNDelimiters(12)+ strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPOFSCH(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.POFSCH.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim 
		+ "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "schedtype") 
		+ strDelim + getString(memRow, "schedday") + strDelim + getString(memRow, "schedtimezn") + strDelim 
		+ getString(memRow, "schedstarthr") + strDelim + getString(memRow, "schedendhr") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno") +ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPOFSR(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.POFSR.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "attrval") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim 
		+ getString(memRow, "termrsn") + strDelim + strBlank + strDelim 
		+ strBlank + strDelim + strBlank + strDelim + strBlank + strDelim + getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + ExtMemgetIxnUtils.getNDelimiters(12)+
		strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPOFTCH(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.POFTCH.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim 
		+ getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim + getString(memRow, "officetechtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "officetecheffdt")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "officetechtermdt") + strDelim + getString(memRow, "officetechtermrsn") 
		+ strDelim + getString(memRow, "officesysname") + strDelim + getString(memRow, "vendorname") + strDelim 
		+ExtMemgetIxnUtils.getDateAsString(memRow, "officesyseffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "officesystermdt") + strDelim 
		+ getString(memRow, "officesystermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
		+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(12)
		+ strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPPGM(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PPGM.getValue();
		//CFF 2.5b changes ends here
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim 
		+ getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim + getString(memRow, "attrval") + strDelim 
		//CFF 2.5c changes starts here
		+ getString(memRow, "attrval2") + strDelim + getString(memRow, "codetermrsn") + strDelim
		//CFF 2.5c changes ends here
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") 
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(30)+ strDelim + srcCodesDelimited ;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPPPRF(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PPPRF.getValue();
		segmentData = outputType + strDelim
		+ getString(memRow, "DFCDC_evtctime") + strDelim
		+ entRecNum + strDelim + "EPDS V2" + strDelim
		+ getString(memRow, "md5key") + strDelim
		+ getString(memRow, "mds5addrtype") + strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")
		+ strDelim + getString(memRow, "patlimittype")
		+ strDelim + getString(memRow, "patlimitval")
		//CFF 2.5b changes starts here
		+ strDelim + getString(memRow, "patlimitvaltypefrm")
		//CFF 2.5b changes end here
		+ strDelim + getString(memRow, "pallimitvalto")
		//CFF 2.5b changes starts here
		+ strDelim + getString(memRow, "patlimitvaltypeto")
		//CFF 2.5b changes end here
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "patlimiteffdt")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "patlimittermdt")
		+ strDelim + getString(memRow, "patlimittermrsn")
		+ strDelim + getString(memRow, "DFCDC_evtinitiator")
		+ strDelim + getString(memRow, "DFCDC_evtctime")
		+ strDelim + getString(memRow, "DFCDC_mAudRecno") + ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPREL(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),prop_relTypeCode);
		outputType = ProvEnum.PREL.getValue();
		/*if (getString(memRow, "RELMEMSRCCODE").length()>0){
			srccode_lookup = ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE"));
		}
		else{
			srccode_lookup = srcCode_postprocess;
			if (srccode_lookup.equalsIgnoreCase("WMS") && 
					getString(memRow, "reltype").equalsIgnoreCase("1917")){
				srccode_lookup = "EPDS1";							
			}
		}*/
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		
		outputType = ProvEnum.PREL.getValue();
		segmentData =  outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") 
		+ strDelim + getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "reladdreffectdt") // as per  WLPRD00441176 - PREL
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "reladdrtermdt") + strDelim + getString(memRow, "reladdrtermrsn") 
		/*+ getDateAsString(memRow, "relattrval1") + strDelim + getDateAsString(memRow, "relattrval2") 
		+ strDelim + getString(memRow, "relattrval3")*/ + strDelim + getString(memRow, "relmemidnum")
		+ strDelim + getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") 
		+ strDelim + getString(memRow, "relattrval1") + strDelim + getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")
		/*+ getNDelimiters(5)*/ /* 2.5b changes: 5 blank fields added: No Mappings present in the inbound PREL*/
		// CFF2.7 got mappings for the feilds 
		+ strDelim + getString(memRow, "relattrval2") + strDelim + getString(memRow, "relattrval3") 
		+ strDelim + getString(memRow, "relattrval4") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt")
		/*Added a new field to hold 
		 *the trimmed source code (for example FACETS, CPF, ACES )*/
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(60)+ srccode_lookup/*getSrcCodeforPostProcess(getString(memRow, "RELMEMSRCCODE"))*//*Changed for ENCLARITY bug-WLPRD00472537*/
		+ strDelim + reltype_code + strDelim + srcCodesDelimited ;
		
		return segmentData;
	}

	@Override
	public String getSegmentDataforPRGN(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PRGN.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim +"EPDS V2"	
		+ strDelim + getString(memRow, "idissuer") + strDelim + getString(memRow, "idnumber") + strDelim 
		+ getString(memRow, "idtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "regiontypecd") + strDelim 
		+ getString(memRow, "regionid") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "regioneffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "regiontrmdt")+ strDelim + getString(memRow, "regiontrmrsn") + strDelim 
		+ getString(memRow, "hmositecode") + strDelim +ExtMemgetIxnUtils.getNDelimiters(12) + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPRNK(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PRNK.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ entRecNum + strDelim + "EPDS V2" + strDelim + getString(memRow, "attrval") +
		strDelim + getString(memRow, "elemdesc") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(12) + strDelim + srcCodesDelimited ;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPSANC(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PSANC.getValue();
		segmentData = outputType + strDelim
		+ getString(memRow, "DFCDC_evtctime") + strDelim
		+ entRecNum + strDelim + "EPDS V2" + strDelim
		+ getString(memRow, "sanctiontype") + strDelim
		+ getString(memRow, "sanctionsrc") + strDelim
		+ getString(memRow, "sanctionaction") + strDelim
		+ getString(memRow, "sanctionrsn") + strDelim
		// CFF 2.4 changes start here
		//							+ getDateAsString(memRow, "sanctionreportdt")
		//							+ strDelim
		// CFF 2.4 changtes end here
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "sanctioneffectdt")
		+ strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "sanctionenddt")
		+ strDelim + getString(memRow, "sanctiontermrsn")
		+ strDelim
		+ getString(memRow, "DFCDC_evtinitiator")
		+ strDelim + getString(memRow, "DFCDC_evtctime")
		+ strDelim + getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(30)+ strDelim + srcCodesDelimited ;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPSNAC(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PSNAC.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "sanctype") + strDelim + getString(memRow, "sancsource") 
		+ strDelim + getString(memRow, "sancaction") + strDelim + /*getDateAsString(memRow, "sancreportdt") */
		ExtMemgetIxnUtils.getDateAsString(memRow, "sanctionreportdt") + strDelim + getString(memRow, "claimacttype") + strDelim 
		+ getString(memRow, "claimactcrittype") + strDelim + getString(memRow, "claimactlow") + strDelim 
		+ getString(memRow, "claimacthigh") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "claimacteffdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "sancclaimacteffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "sancclaimacttermdt") 
		+ strDelim + getString(memRow, "sancclaimacttermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") 
		+ strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")
		+ ExtMemgetIxnUtils.getNDelimiters(30)+ strDelim + srcCodesDelimited ;
		return segmentData;

	}

	@Override
	public String getSegmentDataforPSTFLANG(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PSTFLANG.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
		+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim 
		+ getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
		+ strDelim + getString(memRow, "langcd") + strDelim + getString(memRow, "langwritten") + strDelim 
		+ getString(memRow, "langspoken") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim 
		+ getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno")+ExtMemgetIxnUtils.getNDelimiters(12)
		+ strDelim + srcCodesDelimited ;
		return segmentData;
	}

	@Override
	public String getSegmentDataforPTXN(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.PTXN.getValue();
		segmentData =  outputType + strDelim
		+ getString(memRow, "DFCDC_evtctime") + strDelim
		+ entRecNum + strDelim + "EPDS V2" + strDelim
		+ getString(memRow, "md5key") + strDelim
		+ getString(memRow, "mds5addrtype") + strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")
		+ strDelim + getString(memRow, "taxonomycd")
		+ strDelim + getString(memRow, "taxonomyorgcd")
		+ strDelim + getString(memRow, "taxonomysetcd")
		// CFF 2.4 changes start here
		//+ strDelim + getString(memRow, "attestind")
		// CFF 2.4 changes end here
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")
		+ strDelim + getString(memRow, "termrsn")
		+ strDelim
		+ getString(memRow, "DFCDC_evtinitiator")
		+ strDelim + getString(memRow, "DFCDC_evtctime")
		+ strDelim + getString(memRow, "DFCDC_mAudRecno") + ExtMemgetIxnUtils.getNDelimiters(12) +strDelim + srcCodesDelimited;
		return segmentData;
	}

	@Override
	public String getSegmentDataforWCON(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		outputType = ProvEnum.WCON.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "relmemidnum") + strDelim 
		+ "EPDS V2"	+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+ strDelim + /*getString(memRow, "relmemidnum")*/entRecNum + strDelim 
		+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")	+ strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "enrolovrrideind") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "enroleffectdt")+ strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "enroltermdt") + strDelim /* CFF 2.7 deleted + strBlank	+ strDelim*/ + getString(memRow, "hmocntrctcd") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt")+ strDelim 
		+ getString(memRow, "hmocntrcttermrsn")	+ strDelim + getString(memRow, "hmocntrctcapttn") + strDelim 
		+ getString(memRow, "cntrctacceptpats")	+ strDelim +  getString(memRow, "cntrctenrollprefagelo")+ strDelim 
		+ getString(memRow, "cntrctenrollprefagehi")+ strDelim +  getString(memRow, "cntrctenrollprefgndr")	+ strDelim 
		+ getString(memRow, "cntrctenrollprefobsttrcs") + strDelim + getString(memRow, "cntrctmemcapcnt")+ strDelim 
		+ getString(memRow, "cntrctmemcnt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cntrctmemuptdt") + strDelim 
		+ getString(memRow, "cntrctpmgclaims") + strDelim + getString(memRow, "cntrctmixercd")+ strDelim 
		+ getString(memRow, "cntrctdirdisplayind")+ strDelim + getString(memRow, "cntrctspclid")+ strDelim
		+ getString(memRow, "nwhmoownerid")	+ strDelim +  getString(memRow, "hmopgmcd1") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt1")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt1") + strDelim 
		+ getString(memRow, "hmopgmtermrsn1") + strDelim + getString(memRow, "hmopgmcd2") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt2")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt2")+ strDelim 
		+ getString(memRow, "hmopgmtermrsn2") + strDelim + getString(memRow, "hmopgmcd3") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt3")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt3")+ strDelim 
		+ getString(memRow, "hmopgmtermrsn3") + strDelim + getString(memRow, "hmopgmcd4") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt4")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt4")+ strDelim 
		+ getString(memRow, "hmopgmtermrsn4") + strDelim + getString(memRow, "hmopgmcd5") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmeffectdt5")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmopgmtermdt5")+ strDelim 
		+ getString(memRow, "hmopgmtermrsn5") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") 
		// CFF 2.7 new feild : added mapping as per CQ :WLPRD00873914
		+ strDelim + getString(memRow, "relorgmemidnum") + strDelim + getString(memRow, "relorgreltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt")
		// passing trimmed sourccode and reltype for lookup - Source HMO Site Identifier
		+ExtMemgetIxnUtils.getNDelimiters(30)+ strDelim + /*getSrcCodeforPostProcess(getString(memRow,"RELMEMSRCCODE"))*/srccode_lookup + strDelim + "HMO"
		// passing trimmed sourccode and reltype for lookup - HMO Site Relationship Related Source Provider Identifier
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"relorgsrccode")) + strDelim + "ORG"
		+ strDelim + srcCodesDelimited	;
		
		return segmentData;
	}

	@Override
	public String getSegmentDataforWNET(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		outputType = ProvEnum.WNET.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "relmemidnum") + strDelim 
		+ "EPDS V2"	+ strDelim + /*getString(memRow, "relmemidnum")*/entRecNum + strDelim 
		+ getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")	+ strDelim + getString(memRow, "termrsn") + strDelim /* CFF 2.7 deleted + strBlank	+ strDelim*/ + getString(memRow, "hmocntrctcd") + strDelim 
		+ netIdType+ strDelim+netIdSource+strDelim
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcteffdt")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "hmocntrcttermdt")+ strDelim 
		+ getString(memRow, "hmocntrcttermrsn")	+ strDelim + getString(memRow, "cntrctacceptpats")	+ strDelim 
		+ getString(memRow, "cntrctdirdisplayind")+ strDelim + getString(memRow, "cntrctspclid")+ strDelim
		+ getString(memRow, "nwhmoownerid")	+ strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") 
		// CFF 2.7 new feild : added mapping as per CQ :WLPRD00873914
		+ strDelim + getString(memRow, "relorgmemidnum") + strDelim + getString(memRow, "relorgreltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt")
		// passing trimmed sourccode and reltype for lookup - Source HMO Site Identifier
		+ExtMemgetIxnUtils.getNDelimiters(30)+ strDelim + /*getSrcCodeforPostProcess(getString(memRow,"RELMEMSRCCODE"))*/srccode_lookup + strDelim + "HMO"
		// passing trimmed sourccode and reltype for lookup - HMO Site Relationship Related Source Provider Identifier
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"relorgsrccode")) + strDelim + "ORG"
		+ strDelim + srcCodesDelimited	;
		
		return segmentData;
	}

	@Override
	public String getSegmentDataforWREL(MemRow memRow, long entRecNum)
			throws Exception {
		getMemHeadValues(memRow);
		srccode_lookup = ExtMemgetIxnUtils.getSourceCode(getString(memRow, "RELMEMSRCCODE"),l_strSrcCd);
		outputType = ProvEnum.WREL.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+  getString(memRow, "relmemidnum") + strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim //- Need to map HMO Site Address Identifier to md5key instead of asaIdxno
		+ getString(memRow, "mds5addrtype") + strDelim 
		// CFF 2.7 new feild 
		+ getString (memRow, "primpractind") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim 
		// CFF 2.7 new feild 
		+ getString(memRow, "phnumber") + strDelim + getString(memRow, "fxnumber") + strDelim 
		+ entRecNum + strDelim + getString(memRow, "reltype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") + strDelim + getString(memRow, "termrsn") + strDelim 
		+ getString(memRow, "relattrval1") + strDelim + getString(memRow, "relattrval2") + strDelim 
		+ getString(memRow, "relattrval3") 
		// CFF 2.7 new feild 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "pcptaxideffdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "pcptaxidtrmdt") + strDelim
		+ getString(memRow, "pcptaxidtrmrsn") 	
		// CFF 2.4 changes start here
		+ strDelim +getString(memRow, "legacyid")
		// CFF 2.4 changes end here
		+ strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")
		+ strDelim + getString(memRow, "relorgmemidnum") + strDelim 
		+ getString(memRow, "relorgsrccode") + strDelim + getString(memRow, "relorgreltype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "relorgeffectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "relorgtermdt")
		//CFF 2.8 new field
		+ strDelim + getString(memRow, "svcareatypcd") + strDelim + getString(memRow, "cntybdraddrind")
		// passing trimmed sourcecode and reltype for 2 lookups 
		+ strDelim + ExtMemgetIxnUtils.getNDelimiters(30)+ /*getSrcCodeforPostProcess(getString(memRow,"RELMEMSRCCODE"))*/ srccode_lookup + strDelim + "HMO"
		+ strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(getString(memRow,"relorgsrccode")) + strDelim + "ORG"	
		+ strDelim + srcCodesDelimited;
		return segmentData;
	}
	@Override
	public String getSegmentDataforAPCNTC(MemRow memRow, long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		String strDFCDC_evtctime = getString(memRow, "DFCDC_evtctime");
		outputType = ProvEnum.APCNTC.getValue();
		segmentData = outputType + strDelim + strDFCDC_evtctime + strDelim + entRecNum + strDelim + "EPDS V2" 
		+ strDelim + getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
		+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "onmfirst") + strDelim 
		+ getString(memRow, "onmlast") + strDelim + getString(memRow, "onmmiddle") + strDelim + getString(memRow, "rolecd") 
		+ strDelim + getString(memRow, "titlecd") + strDelim + /*getString(memRow, "mds5addrtype") + strDelim+ Removed as per 2.4*/
		getString(memRow, "phicc") + strDelim + getString(memRow, "pharea") + strDelim + getString(memRow, "phnumber") + strDelim + getString(memRow, "phextn") 
		+ strDelim + getString(memRow, "teltype") + strDelim + getString(memRow, "fxarea") + strDelim + getString(memRow, "fxnumber") + strDelim + getString(memRow, "faxtype")
		+ strDelim + getString(memRow, "url") + strDelim + getString(memRow, "urltype")+ strDelim + getString(memRow, "emailaddr") + strDelim + getString(memRow, "emailtype") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim + getString(memRow, "DFCDC_mAudRecno")+ strDelim 
		+ l_strSrcCd + strDelim + l_memIdNum + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cntceffectdt") 
		+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "cntctermdt") + strDelim + getString(memRow, "cntctermrsn") + strDelim 
		+ getString(memRow, "DFCDC_evtinitiator") + strDelim + strDFCDC_evtctime + strDelim 
		+ getString(memRow, "DFCDC_mAudRecno")+ ExtMemgetIxnUtils.getNDelimiters(60)+strDelim + srcCode_postprocess;
		return segmentData;
	}
	

	@Override
	public Set<String> buildALTSRCIDSegmentForV2(
			List<MemAttrRow> orgPRFMemAttrList, String epdsv2memrecno,
			long entrecno) throws Exception {
		outputType=ProvEnum.ALTSRCID.getValue();
		for (MemRow memRow : orgPRFMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			ProvEnum attrCode = ProvEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			case PROVTYPCD:
			{
				hm_ALTSRCID_Slg_V2.put("PROVTYPCD-"+epdsv2memrecno,getString(memRow, "codeval")+ strDelim);
				break;
			}
			case PROVINACTIVE:
			{
				segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") 
				+ strDelim + getString(memRow, "entrecno") + strDelim 
				+ "EPDS V2" + strDelim + ProvEnum.EPDSV2.getValue() + strDelim + epdsv2memrecno + strDelim;
				hm_ALTSRCID_Slg_V2.put("MEMHEAD-" +epdsv2memrecno, segmentData);
				
				segmentData = ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "termdt") 
				+ strDelim + getString(memRow, "termrsn")+ strDelim + strBlank + strDelim;
				segmentData= getTermAfterValidation(memRow,"ALTSRCID",segmentData,"termdt");
				hm_ALTSRCID_Slg_V2.put("PROVINACTIVE-"+epdsv2memrecno, segmentData);
				break;
			}
			case PROVNAMEEXT:
			{
				segmentData = getString(memRow, "onmfirst") + strDelim + getString(memRow, "onmlast") + strDelim + getString(memRow, "onmmiddle") + strDelim + 
				getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + getString(memRow, "DFCDC_mAudRecno") + strDelim;
				/*strBlank + strDelim + strBlank + strDelim + l_maudRecNo + strDelim*/;
				hm_ALTSRCID_Slg_V2.put("PROVNAMEEXT_1-"+epdsv2memrecno, segmentData);
				segmentData = getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
				+ getString(memRow, "DFCDC_mAudRecno") + strDelim /*+ getSrcCodeforPostProcess(l_strSrcCd)*/;
				hm_ALTSRCID_Slg_V2.put("PROVNAMEEXT_2-"+epdsv2memrecno, segmentData);
				break;
			}
			case PROVACESLGCY:
			{
				segmentData = getString(memRow, "npixwalk") + strDelim
				+ getString(memRow, "requestedind1099") + strDelim
				+ getString(memRow, "forprofitind") + strDelim
				+ getString(memRow, "paytoprovtamt") + strDelim
				+ getString(memRow, "paytoprovind") + strDelim
				// CHECK START
				//+ getString(memRow, "paytostateind") + strDelim 
				+ getString(memRow, "provstupst") + strDelim //paytostateind is renamed to provstupst
				//CHECK END
				+ getString(memRow, "payeename") + strDelim
				+ getString(memRow, "provacredidnum") + strDelim
				+ getString(memRow, "provchaifind") + strDelim
				// CFF 2.4 changes start here
				//The field names as per the Inbound CFF chaieffectdate, 
				//usgdateind, usgdatecd,parindt,pareffectdt, parenddt, pnlid In EMEMACESLGCY table.
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "chaieffectdate") + strDelim
				+ getString(memRow, "usgdateind") + strDelim
				+ getString(memRow, "usgdatecd") + strDelim
				+ getString(memRow, "parind") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "pareffectdt") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "parenddt") + strDelim
				+ getString(memRow, "pnlid") + strDelim
				// CFF 2.4 changes end here
				+ getString(memRow, "provclasscd") + strDelim
				+ getString(memRow, "provcorpttl") + strDelim
				+ getString(memRow, "provelectcmrccd") + strDelim
				+ getString(memRow, "provinstttl") + strDelim
				+ getString(memRow, "provliccatcd") + strDelim
				+ getString(memRow, "provmiddlenm") + strDelim
				+ getString(memRow, "profesttl") + strDelim
				+ getString(memRow, "provstatus") + strDelim
				+ getString(memRow, "provsufbusttl") + strDelim
				+ getString(memRow, "provfrstnm") + strDelim
				+ getString(memRow, "provlstnm") + strDelim
				+ getString(memRow, "provmiddleinit") + strDelim
				+ getString(memRow, "provsufnme") + strDelim
				+ getString(memRow, "totantemmbrscurr") + strDelim
				//CFF 2.5b changes starts here
				+ getString(memRow, "provngtind") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "provngteffdt") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "provngtenddt") + strDelim;
				//CFF 2.5b changes ends here;
				hm_ALTSRCID_Slg_V2.put("PROVACESLGCY-"+epdsv2memrecno, segmentData);
				break;
			}
			case PROVCPFLGCY:
			{
				segmentData = getString(memRow, "instpeergrpcd") + strDelim + getString(memRow, "instbussind") + strDelim 
				+ getString(memRow, "instinpataccind") + strDelim + getString(memRow, "instctyhospind") 
				+ strDelim + getString(memRow, "instnmemind") + strDelim + getString(memRow, "insthmecareind") 
				+ strDelim + getString(memRow, "insthmdlysisind") + strDelim + getString(memRow, "instotpatind") 
				+ strDelim + getString(memRow, "instsezareacd") + strDelim + getString(memRow, "instprtitind") + strDelim 
				+ getString(memRow, "ihstabrtnind") + strDelim + getString(memRow, "ihstgovngovcd") + strDelim 
				+ getString(memRow, "ihsttypsvccd") + strDelim + getString(memRow, "ihstacrdtncd") + strDelim 
				+ getString(memRow, "ihstaffltncd") + strDelim + getString(memRow, "ihstemhcflg") + strDelim 
				+ getString(memRow, "ihstdrgcd") + strDelim + getString(memRow, "ihstdrgexmptflg") + strDelim;
				hm_ALTSRCID_Slg_V2.put("PROVCPFLGCY-"+epdsv2memrecno, segmentData);
				break;
			}
			case PROVCPMFLGCY:
			{
				segmentData = getString(memRow, "ctrlcd") + strDelim
				+ getString(memRow, "provtypcd") + strDelim
				+ getString(memRow, "subscrpayind") + strDelim
				+ getString(memRow, "grprecind") + strDelim
				+ getString(memRow, "loccd") + strDelim
				+ getString(memRow, "medicareind") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcprpteffdt") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "pcprptthrudt") + strDelim
				+ getString(memRow, "speccatcd1") + strDelim
				+ getString(memRow, "speccatcd2") + strDelim
				+ getString(memRow, "speccatcd3") + strDelim
				+ getString(memRow, "speccatcd4") + strDelim
				+ getString(memRow, "speccatcd5") + strDelim
				+ getString(memRow, "speccatcd6") + strDelim
				+ getString(memRow, "speccatcd7") + strDelim
				+ getString(memRow, "speccatcd8") + strDelim
				+ getString(memRow, "speccatcd9") + strDelim
				+ getString(memRow, "speccatcd10") + strDelim
				+ getString(memRow, "speccatcd11") + strDelim
				+ getString(memRow, "speccatcd12") + strDelim
				+ getString(memRow, "speccatcd13") + strDelim
				+ getString(memRow, "speccatcd14") + strDelim
				+ getString(memRow, "speccatcd15") + strDelim
				+ getString(memRow, "speccatcd16") + strDelim
				+ getString(memRow, "speccatcd17") + strDelim
				+ getString(memRow, "speccatcd18") + strDelim
				+ getString(memRow, "speccatcd19") + strDelim
				+ getString(memRow, "speccatcd20") + strDelim
				+ getString(memRow, "npielgb") + strDelim;
				hm_ALTSRCID_Slg_V2.put("PROVCPMFLGCY-"+epdsv2memrecno, segmentData);
				break;
			}
				
			case PROVEPSBLGCY:
			{segmentData = getString(memRow, "tradsftynetcd") + strDelim
				+ getString(memRow, "ipprnkval") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "ipseffdt") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "ipstermdt") + strDelim
				+ getString(memRow, "ipstermrsn") + strDelim
				// CFF 2.4 changes begins here
				+ getString(memRow, "provorgdirabb") + strDelim
				// CFF 2.4 changes ends here
				+ getString(memRow, "pdcd") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "pdeffdt") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "pdtermdt") + strDelim
				+ getString(memRow, "pdtermrsn") + strDelim
				+ getString(memRow, "pdtypcd") + strDelim
				// CFF 2.4 changes begins here
				+ getString(memRow, "pdcd2") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "pdcdeffdt2") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "pdcdtrmdt2") + strDelim
				+ getString(memRow, "pdcdtrmrsncd2") + strDelim
				+ getString(memRow, "pdtypcd2") + strDelim
				// CFF 2.4 changes ends here
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "provtxnmyeffdt") + strDelim
				//CFF 2.5b changes starts here
				+ getString(memRow, "dlgtdcredentcd") + strDelim
				+ getString(memRow, "wlcletreqind") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "wlcletsentdt") + strDelim;
				//CFF 2.5b changes ends here
				hm_ALTSRCID_Slg_V2.put("PROVEPSBLGCY-"+epdsv2memrecno, segmentData);
				break;
				}    
			case PROVQCARELGY:
			{segmentData = getString(memRow, "prv2025ind") + strDelim
				+ getString(memRow, "prv2582ind") + strDelim
				+ getString(memRow, "prv2750ind") + strDelim
				+ getString(memRow, "prv2755ind2") + strDelim
				+ getString(memRow, "prv2755ind3") + strDelim
				+ getString(memRow, "prv2755ind4") + strDelim
				+ getString(memRow, "prv2766ind") + strDelim
				+ getString(memRow, "prv2791ind1") + strDelim
				+ getString(memRow, "prv2791ind2") + strDelim
				+ getString(memRow, "prv2791ind3") + strDelim
				+ getString(memRow, "prv2791ind4") + strDelim
				+ getString(memRow, "prv2791ind5") + strDelim
				// CFF 2.4 changes begins here
				+ getString(memRow, "prv2791ind6") + strDelim
				+ getString(memRow, "prv2791ind7") + strDelim
				+ getString(memRow, "prv2791ind8") + strDelim
				+ getString(memRow, "prv2791ind9") + strDelim
				+ getString(memRow, "prv2791ind10") + strDelim
				// CFF 2.4 changes ends here
				+ getString(memRow, "prv2792ind") + strDelim
				+ getString(memRow, "prv2796ind1") + strDelim
				+ getString(memRow, "prv2796ind2") + strDelim
				+ getString(memRow, "prv2796ind3") + strDelim
				+ getString(memRow, "prv2796ind4") + strDelim
				// CFF 2.4 changes begins here
				+ getString(memRow, "prv2796ind5") + strDelim
				// CFF 2.4 changes ends here
				+ getString(memRow, "prv2798ind") + strDelim
				+ getString(memRow, "prv2799ind") + strDelim
				+ getString(memRow, "prv2614ind") + strDelim;
				hm_ALTSRCID_Slg_V2.put("PROVQCARELGY-"+epdsv2memrecno, segmentData);
				break;
				}
			}
		}
		return generateSegmentsforALTSRCID(hm_ALTSRCID_Slg_V2, entrecno);
	}
	private Set<String> generateSegmentsforAPALT(HashMap<String, String> hm_APALT,
			HashMap<String,ArrayList<String>> hm_APALT_Prov_Alt_ID_Specialty,
			boolean APALTFlag)throws Exception
	{
		HashMap<String,String> hm_APALT_Prov_Alt_ID_Specialty1 = new HashMap<String,String>();
		Set<String> segmentDataSet = new HashSet<String>();
		if (null != hm_APALT && hm_APALT.size() > 0) {	
			if(null != hm_APALT_Prov_Alt_ID_Specialty && hm_APALT_Prov_Alt_ID_Specialty.size() > 0){			
				String ProvAltIDSpecialty1_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty2_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty3_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty4_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty5_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				Set<String> hm_Prov_Alt_ID_Specialty_keys = new HashSet<String>();
				hm_Prov_Alt_ID_Specialty_keys = hm_APALT_Prov_Alt_ID_Specialty.keySet();
				for (Iterator<String> iterator1 = hm_Prov_Alt_ID_Specialty_keys.iterator(); iterator1.hasNext();) {
					String hm_Prov_Alt_ID_Specialty_key= (String) iterator1.next();
					String[] split_keys1 ; 
					split_keys1 = hm_Prov_Alt_ID_Specialty_key.split("-");
					ArrayList<String> valueList = hm_APALT_Prov_Alt_ID_Specialty.get(hm_Prov_Alt_ID_Specialty_key);
					int count = valueList.size();
					for (Iterator<String> iterator_value = valueList.iterator(); iterator_value.hasNext();) {
						String spec_info = (String) iterator_value.next();						
						switch (count)
						{
						case 1 : ProvAltIDSpecialty1_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						case 2 : ProvAltIDSpecialty2_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						case 3 : ProvAltIDSpecialty3_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						case 4: ProvAltIDSpecialty4_segmentData1 = spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						case 5 : ProvAltIDSpecialty5_segmentData1 =spec_info!=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
						break;
						}	
						count --;
					}
					if(null!=hm_MemHead)
					{
						MemHead temp_memHead ;
						temp_memHead = (MemHead)hm_MemHead.get(split_keys1[split_keys1.length-1]);
						l_strSrcCd = temp_memHead.getSrcCode();
					}
					segmentData = ProvAltIDSpecialty1_segmentData1 +ProvAltIDSpecialty2_segmentData1
					+ProvAltIDSpecialty3_segmentData1+ProvAltIDSpecialty4_segmentData1+ProvAltIDSpecialty5_segmentData1 
					+ExtMemgetIxnUtils.getNDelimiters(60)+ ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					hm_APALT_Prov_Alt_ID_Specialty1.put(AddSpliterToContent(split_keys1), segmentData);
					segmentData = "";

				}
			}
			Set <String>APALT_Keys = new HashSet<String>();
			String split_keys[];						
			//get the keys
			APALT_Keys = new HashSet<String>(hm_APALT.keySet());
			for (Iterator<String> iterator = APALT_Keys.iterator(); iterator.hasNext();) 
			{
				String APALT_Key = iterator.next();
				split_keys = APALT_Key.split("-");	
				if(null!=hm_MemHead)
				{
					MemHead temp_memHead ;
					temp_memHead = (MemHead)hm_MemHead.get(split_keys[split_keys.length-1]);
					l_strSrcCd = temp_memHead.getSrcCode();
					srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
				}
				if (null == hm_APALT_Prov_Alt_ID_Specialty.get(AddSpliterToContent(split_keys)))
				{
					segmentData = ExtMemgetIxnUtils.getNDelimiters(10)+ ExtMemgetIxnUtils.getNDelimiters(60)+ srcCode_postprocess;
					hm_APALT_Prov_Alt_ID_Specialty1.put(AddSpliterToContent(split_keys), segmentData);
				}						
				//create a full segment	
				if (APALTFlag){
					segmentData=hm_APALT.get(AddSpliterToContent(split_keys)) +
					hm_APALT_Prov_Alt_ID_Specialty1.get(AddSpliterToContent(split_keys)) +
					strDelim + split_keys[split_keys.length-1];
				}
				else {
					segmentData=hm_APALT.get(AddSpliterToContent(split_keys)) +
					hm_APALT_Prov_Alt_ID_Specialty1.get(AddSpliterToContent(split_keys));
				}
				outputType = ProvEnum.APALT.getValue();								
				segmentDataSet.add(segmentData);	
			
		}
	}	
	
		return segmentDataSet;
	}
	
	@Override
	public Set<String> buildAPALTSegment(List<MemAttrRow> orgAPALTMemAttrList,
			long entRecNum, boolean APALTFlag) throws Exception {
		String l_memrecno ="";
		outputType = ProvEnum.APALT.getValue();
		HashMap<String, String> hm_APALT = new HashMap<String, String>();
		HashMap<String,ArrayList<String>> hm_APALT_Prov_Alt_ID_Specialty= new HashMap<String,ArrayList<String>>();
		
		for (MemRow memRow : orgAPALTMemAttrList){
			getMemHeadValues(memRow);
			l_memrecno = new Long(memRow.getMemRecno()).toString();
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			ProvEnum attrCode = ProvEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {
			
			case PROVALTSYSID:
			case NPI:
			case UPIN:
			case DEAID:
			case MEDICARE:
			case MEDICAID:
			case ENCLARITYID:
			case PROVLICENSE:
			{
					if(l_strSrcCd !=null && l_memIdNum !=null && l_strSrcCd .length()> 0  && l_memIdNum.length()>0 &&
							getString(memRow,"idnumber").length() > 0  
							&& ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")!= null && ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate").length() > 0
							&& ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")!=null && ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate").length() > 0)
					{
						srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
						reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),prop_relTypeCode);
						if(l_strAttrCode.equalsIgnoreCase("PROVLICENSE")&& getString(memRow,"licensetype").length() > 0){
								segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
								+ entRecNum + strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim 
								+ getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")
								+ strDelim + getString(memRow, "idissuer") + strDelim + getString(memRow, "idnumber")
								+ strDelim + getString(memRow,"licensetype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "termrsn") 
								+ strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim 

								//ChangeRequest Term dates not present on outbound files for A files WLPRD00793241
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
								+ strDelim + getString(memRow, "termrsn") + strDelim 
								+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
								+ getString(memRow, "DFCDC_evtctime") + strDelim 
								+ getString(memRow, "DFCDC_mAudRecno") + strDelim


								+ getString(memRow, "acesmedintname") + strDelim 
								+ getString(memRow, "acesmedpartind") + strDelim 
								//CFF 2.7 new feild
								+ getString(memRow , "wgscntrtiercd") + strDelim
								+ getString(memRow, "epdsssbpaiid") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpapeffdt") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpaptermdt") + strDelim 
								+ getString(memRow, "epdsssbpaptermrsn") + strDelim 
								+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbqlfdeffdt") + strDelim 
								+ getString(memRow, "epdsssbmdbtcd") + strDelim 
								+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
								+ getString(memRow, "DFCDC_evtctime")
								+ strDelim + getString(memRow, "DFCDC_mAudRecno")
								//CFF 2.8 new feild
								+ strDelim + getString (memRow, "proxtiercd")
								+ strDelim + getString (memRow, "proxmbrcnt")
								+ strDelim + getString (memRow, "proxmbrcapcnt")
								+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "proxmbrcntupddt")
								+ strDelim + getString (memRow, "proxfrmage")
								+ strDelim + getString (memRow, "proxtoage")
								+ strDelim + getString (memRow, "proxgendercd")
								+ strDelim + getString (memRow, "proxsrvcind")
								+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxepreffdt")
								+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxeprtermdt")+ strDelim;
								String key = getString(memRow, "IDNUMBER");
								if (key.contains("-")) 
								{
									key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
								}
								hm_APALT.put(getString(memRow, "idissuer")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+"-" +getString(memRow, "licensetype")+"-"  +key+"-" + getString(memRow, "md5key")+"-" + getString(memRow, "MDS5ADDRTYPE")+"-"
										+ ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"+ l_memrecno /*+ "-" + row_Indicator*/ , segmentData );
							}
						else if (getString(memRow,"idtype").length() > 0 )
						{
							segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum 
							+ strDelim + "EPDS V2" + strDelim + getString(memRow, "md5key") + strDelim 
							+ getString(memRow, "mds5addrtype") + strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") 
							+ strDelim + getString(memRow, "idissuer") + strDelim + getString(memRow, "idnumber") 
							+ strDelim + getString(memRow, "idtype")+ strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") 
							+ strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "idtermrsn") 
							+ strDelim + l_strSrcCd + strDelim + l_memIdNum	+ strDelim

							//ChangeRequest Term dates not present on outbound files for A files WLPRD00793241
							+  ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+ strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
							+ strDelim + getString(memRow, "idtermrsn") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
							+ getString(memRow, "DFCDC_evtctime") + strDelim 
							+ getString(memRow, "DFCDC_mAudRecno") + strDelim

							+ getString(memRow, "acesmedintname") + strDelim 
							+ getString(memRow, "acesmedpartind") + strDelim
							//CFF 2.7 new feild
							+ getString(memRow , "wgscntrtiercd") + strDelim
							+ getString(memRow, "epdsssbpaiid") + strDelim +ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpapeffdt") + strDelim 
							+ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpaptermdt") + strDelim + getString(memRow, "epdsssbpaptermrsn") + strDelim 
							+ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbqlfdeffdt") + strDelim + getString(memRow, "epdsssbmdbtcd") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
							+ strDelim + getString(memRow, "DFCDC_mAudRecno")
							//new 2.8  new feild
							+ strDelim + getString (memRow, "proxtiercd")
							+ strDelim + getString (memRow, "proxmbrcnt")
							+ strDelim + getString (memRow, "proxmbrcapcnt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "proxmbrcntupddt")
							+ strDelim + getString (memRow, "proxfrmage")
							+ strDelim + getString (memRow, "proxtoage")
							+ strDelim + getString (memRow, "proxgendercd")
							+ strDelim + getString (memRow, "proxsrvcind")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxepreffdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxeprtermdt")+ strDelim;
							String key = getString(memRow, "IDNUMBER");
							if (key.contains("-")) 
							{
								key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
							}
							hm_APALT.put(getString(memRow, "idissuer")+"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")+"-"
									+ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+"-" +getString(memRow, "IDTYPE")+"-"  
									+key+"-" + getString(memRow, "md5key")+"-" + getString(memRow, "MDS5ADDRTYPE")+"-" 
									+ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"+ l_memrecno /*+ "-" + row_Indicator */ , segmentData );
						}
						//2.8 changes
					}
				}
			break;
			case PRVALTIDSPEC:
				
			{
		         String key = getString(memRow, "IDNUMBER");
					if (key.contains("-")) 
						key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
					String spec_key = getString(memRow, "idissuer")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")+"-" 
					+ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+"-" +getString(memRow, "IDTYPE")+"-" + key+"-" 
					+ getString(memRow, "md5key")+"-" + getString(memRow, "MDS5ADDRTYPE")+"-" 
					+ ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT") +"-"+ l_memrecno /*+ "-" + row_Indicator*/;
					segmentData = getString(memRow, "specialtycd") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+ strDelim; 
					if(hm_APALT_Prov_Alt_ID_Specialty != null && !hm_APALT_Prov_Alt_ID_Specialty.isEmpty() )
					{
						if(hm_APALT_Prov_Alt_ID_Specialty.containsKey(spec_key)) //if contain key
						{
							ArrayList<String>speciality_list = new ArrayList<String>();
							speciality_list = hm_APALT_Prov_Alt_ID_Specialty.get(spec_key);
							speciality_list.add(segmentData);
							hm_APALT_Prov_Alt_ID_Specialty.put(spec_key, speciality_list);
						}
						else //if doesnot contain key
						{
							ArrayList<String>speciality_list = new ArrayList<String>();
							speciality_list.add(segmentData);
							hm_APALT_Prov_Alt_ID_Specialty.put(spec_key, speciality_list);
						}
					}
					else if (hm_APALT_Prov_Alt_ID_Specialty.isEmpty())
					{
						ArrayList<String>speciality_list = new ArrayList<String>();
						speciality_list.add(segmentData);
						hm_APALT_Prov_Alt_ID_Specialty.put(spec_key, speciality_list);
					}
				}
			break;
			}
			
		}
		return generateSegmentsforAPALT(hm_APALT,hm_APALT_Prov_Alt_ID_Specialty,APALTFlag);
	}
			
			
   @Override
	public Set<String> buildAPADRSegment(List<MemAttrRow> provAPADRMemAttrList,
			List<String> EMEMADDR_Keys, long entRecNum)
			throws Exception {
		outputType = ProvEnum.APADR.getValue();
		for (MemRow memRow : provAPADRMemAttrList){
			getMemHeadValues(memRow);
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			ProvEnum attrCode = ProvEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			
			String comb_key = getString(memRow, "md5key") + "-" + getString(memRow, "mds5addrtype") +"-" + 
			ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+"-" + new Long(memRow.getMemRecno()).toString();

			switch(attrCode) {
			case PROVPADRLGCY : {
				
				segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim + entRecNum + strDelim + "EPDS V2" + strDelim ;
				hm_APADR.put("MEMHEAD-"+comb_key, segmentData);

				segmentData = getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim;
				hm_APADR.put("PROVPADRLGCY_1-"+comb_key, segmentData);

				segmentData = /*l_strSrcCd + strDelim + l_memIdNum + strDelim + split_keys_comb_key [2] + strDelim 
				+ split_ememaddr_key[0] + strDelim + split_ememaddr_key[1] +*/ strDelim +					
				getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim +
				getString(memRow, "DFCDC_mAudRecno") + strDelim 
				+ getString(memRow, "lvlofcare") + strDelim
				+ getString(memRow, "licbedcnt") + strDelim
				+ getString(memRow, "maillblcnt") + strDelim
				+ getString(memRow, "medicareclnind") + strDelim
				+ getString(memRow, "medicarededbrdeligcnt") + strDelim
				+ getString(memRow, "ocpcyrate") + strDelim
				+ getString(memRow, "outstateind") + strDelim
				// CFF 2.4 changes begins here
				//+ getString(memRow, "praclocdesg") + strDelim
				// CFF 2.4 changes ends here
				+ getString(memRow, "praclocnme") + strDelim
				+ getString(memRow, "provspeccopayind") + strDelim
				+ getString(memRow, "provcntcprsnid") + strDelim
				+ getString(memRow, "provlocstat") + strDelim
				+ getString(memRow, "provoffpaymtcd") + strDelim
				+ getString(memRow, "provprefspec") + strDelim
				+ getString(memRow, "staffbedct") + strDelim
				+ getString(memRow, "teachinstind") + strDelim
				+ getString(memRow, "traumactrind") + strDelim
				+ getString(memRow, "phyadbillgrp") + strDelim
				+ getString(memRow, "r1099name1") + strDelim
				+ getString(memRow, "econcd") + strDelim
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "econdt") + strDelim
				+ getString(memRow, "hmorgncd") + strDelim
				+ getString(memRow, "rgncd") + strDelim
				+ getString(memRow, "terrcd") + strDelim
				+ getString(memRow, "prv1182ind") + strDelim
				+ getString(memRow, "prv2032ind") + strDelim
				+ getString(memRow, "prv2033ind") + strDelim
				//CFF 2.5b changes starts here
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "provoptmnl") + strDelim
				//CFF 2.5b changes ends here
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "provoptmanackdt") + strDelim;
				hm_APADR.put("PROVPADRLGCY_2-"+comb_key, segmentData);

				segmentData =  getString(memRow, "DFCDC_evtinitiator") + strDelim
				+ getString(memRow, "DFCDC_evtctime") + strDelim
				+ getString(memRow, "DFCDC_mAudRecno")
				//Dec Offcycle new feild
				+ strDelim /*+ split_ememaddr_key[2] + ExtMemgetIxnUtils.getNDelimiters(4)*/;
				hm_APADR.put("PROVPADRLGCY_3-"+comb_key, segmentData);
			}
			break;

			case PRVSN : {
				
				segmentData = strBlank + strDelim + getString(memRow, "REVISIONCODE") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "REVISIONDT") + strDelim 
				+ ExtMemgetIxnUtils.getDateAsString(memRow, "REVISIONDESC") + strDelim ;
				hm_APADR.put("PRVSN_1-"+comb_key, segmentData);

				segmentData = getString(memRow, "ADJUNCTDBNAME") + strDelim ;					
				hm_APADR.put("PRVSN_2-"+comb_key, segmentData);
				break;
			}

			case PRVFACHSANET : {
				
				segmentData =  getString(memRow, "attrval") + strDelim + getString(memRow, "elemdesc") + strDelim;
				hm_APADR.put("PRVFACHSANET-"+comb_key, segmentData);
				break;
			}
			case PRVDIRDISIND:{
				String comb_key1 = getString(memRow, "md5key") + "-" + getString(memRow, "mds5addrtype") +"-" + 
				ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+"-" + 
				getString(memRow, "attrval2") + "-" + new Long(memRow.getMemRecno()).toString();

				segmentData =  getString(memRow, "attrval") + strDelim ;
				hm_APADR.put("PRVDIRDISIND-"+comb_key1, segmentData);
				break;
			}
			}
		}
		return generateSegmentsforAPADR(EMEMADDR_Keys,entRecNum);
	}


	private Set<String> generateSegmentsforAPADR(List<String> EMEMADDR_Keys, long entRecNum) {
		Set<String> segmentDataSet = new HashSet<String>();
		if(!EMEMADDR_Keys.isEmpty())
		{
			/*Set<String>EMEMADDR_Key_Set = new HashSet<String>();
			EMEMADDR_Key_Set=EMEMADDR_Keys.keySet();*/
			for (Iterator<String> iterator2 = EMEMADDR_Keys.iterator(); iterator2
			.hasNext();) 
			{
				String split_keys_EMEMADDR[],Comb_key,Display_Comb_key;
				String EMEMADDR_Key = iterator2.next();
				split_keys_EMEMADDR = EMEMADDR_Key.split("-");
				Comb_key = split_keys_EMEMADDR[0]+"-"+split_keys_EMEMADDR[1]+"-"+
						   split_keys_EMEMADDR[2]+"-"+split_keys_EMEMADDR[6];
				Display_Comb_key = split_keys_EMEMADDR[0]+"-"+split_keys_EMEMADDR[1]+"-"+
				   					split_keys_EMEMADDR[2]+"-"+split_keys_EMEMADDR[5]+"-"+split_keys_EMEMADDR[6];
				String EMEMADDR_Key_memRecno=split_keys_EMEMADDR[6]/* EMEMADDR_Keys.get(EMEMADDR_Key)*/;
				
					if(null!=hm_MemHead)
					{
						MemHead temp_memHead ;
						temp_memHead = (MemHead)hm_MemHead.get(EMEMADDR_Key_memRecno);
						l_strSrcCd = temp_memHead.getSrcCode();
						srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
						l_memIdNum = temp_memHead.getMemIdnum();
						l_strCaudrecno= new Long(temp_memHead.getCaudRecno()).toString();
						l_maudRecNo = new Long(temp_memHead.getMaudRecno()).toString();
					}
					//write APADR segment for records present in EMEMADDR
					if(null == hm_APADR.get("MEMHEAD-"+Comb_key))
					{
						outputType = ProvEnum.APADR.getValue();
						segmentData= outputType + strDelim + strBlank
						+ strDelim + entRecNum + strDelim + "EPDS V2" + strDelim;
						hm_APADR.put("MEMHEAD-"+Comb_key, segmentData);
					}

					if(null == hm_APADR.get("PROVPADRLGCY_1-"+Comb_key))
					{
						segmentData = split_keys_EMEMADDR[0] + strDelim + split_keys_EMEMADDR[1] + strDelim 
						+ split_keys_EMEMADDR[2]+ strDelim ; 
						hm_APADR.put("PROVPADRLGCY_1-"+Comb_key, segmentData);
					}

					if(null == hm_APADR.get("PROVPADRLGCY_2-"+Comb_key))
					{
						segmentData = /*l_strSrcCd + strDelim + l_memIdNum + strDelim +
						split_keys_EMEMADDR[2] + strDelim + split_ememaddr_key[0] +
						strDelim + split_ememaddr_key[1] + */ExtMemgetIxnUtils.getNDelimiters(3) + "0" + 
						ExtMemgetIxnUtils.getNDelimiters(29);
						hm_APADR.put("PROVPADRLGCY_2-"+Comb_key,segmentData);
					}

					if(null == hm_APADR.get("PROVPADRLGCY_3-"+Comb_key))
					{
						segmentData = ExtMemgetIxnUtils.getNDelimiters(2)+"0"+ strDelim 
						/*+ split_ememaddr_key[2]+ExtMemgetIxnUtils.getNDelimiters(4)*/;
						hm_APADR.put("PROVPADRLGCY_3-"+Comb_key,segmentData);
					}

					if(hm_APADR.get("PRVSN_1-"+Comb_key)==null)
					{
						segmentData = ExtMemgetIxnUtils.getNDelimiters(4);
						hm_APADR.put("PRVSN_1-"+Comb_key,  segmentData);
					}
					
					if(hm_APADR.get("PRVSN_2-"+Comb_key)==null)
					{
						segmentData = ExtMemgetIxnUtils.getNDelimiters(1);
						hm_APADR.put("PRVSN_2-"+Comb_key,  segmentData);
					}
					
					if(hm_APADR.get("PRVFACHSANET-"+Comb_key)==null)
					{
						segmentData = ExtMemgetIxnUtils.getNDelimiters(2);
						hm_APADR.put("PRVFACHSANET-"+Comb_key,  segmentData);
					}

					if(hm_APADR.get("PRVDIRDISIND-"+Display_Comb_key)==null)
					{
						segmentData = ExtMemgetIxnUtils.getNDelimiters(1) ;
						hm_APADR.put("PRVDIRDISIND-"+Display_Comb_key,  segmentData);
					}
					
					segmentData = hm_APADR.get("MEMHEAD-"+Comb_key) 
					+ hm_APADR.get("PROVPADRLGCY_1-"+Comb_key) 
					+ hm_APADR.get("PRVSN_1-"+Comb_key)	
					+ l_strSrcCd + strDelim + l_memIdNum + strDelim 
					+ split_keys_EMEMADDR[2] + strDelim + split_keys_EMEMADDR[3] 
					+ strDelim + split_keys_EMEMADDR[4] 
					+ hm_APADR.get("PROVPADRLGCY_2-"+Comb_key)
					+ hm_APADR.get("PRVSN_2-"+Comb_key)	+ hm_APADR.get("PROVPADRLGCY_3-"+Comb_key)
					+ split_keys_EMEMADDR[5]+ExtMemgetIxnUtils.getNDelimiters(4)
					+ hm_APADR.get("PRVFACHSANET-"+Comb_key) + hm_APADR.get("PRVDIRDISIND-"+Display_Comb_key);

					if (segmentData.replace(strDelim, strBlank).replace("0", strBlank).length() > 0) 
					{
						segmentData = segmentData+ ExtMemgetIxnUtils.getNDelimiters(53)+ srcCode_postprocess;
						outputType = ProvEnum.APADR.getValue();
						segmentDataSet.add(segmentData);
					}
				}
			
		}
		return segmentDataSet;
	}
@Override
	public String getSegmentDataforAPDM(MemRow memRow,long entRecNum) throws Exception {
		getMemHeadValues(memRow);
		outputType = ProvEnum.APDM.getValue();
		segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
		+ getString(memRow, "entrecno") + strDelim + "EPDS V2" + strDelim + getString(memRow, "elemdesc") + strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")
		+ strDelim + getString(memRow, "codetermrsn")  + strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt") 
		+ strDelim +  ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")
		+ strDelim + getString(memRow, "codetermrsn") + strDelim + getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 
		+ strDelim + getString(memRow, "DFCDC_mAudRecno") +ExtMemgetIxnUtils.getNDelimiters(60)+ strDelim + srcCodesDelimited ;
			return segmentData;
	}
	
	@Override
	public String getSegmentDataforNASCOPCNTC(MemRow memRow, long entRecNum)
			throws Exception {
		try{
			getMemHeadValues(memRow);
			outputType = ProvEnum.NASCOPCNTC.getValue();
			if(l_strSrcCd.equalsIgnoreCase("QCARE")||l_strSrcCd.equalsIgnoreCase("QCAREORG")||
					l_strSrcCd.equalsIgnoreCase("EPDSV2")|| l_strSrcCd.equalsIgnoreCase("EPDSV2ORG")){
				if(getString(memRow,"phnumber").length() > 0 ){
					segmentData = outputType + strDelim + getString(memRow, "entrecno")  + strDelim + l_strSrcCd + 
					strDelim +l_memIdNum +strDelim + getString(memRow, "MD5KEY") + strDelim +
					getString(memRow, "mds5addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt")+ strDelim +
					getString(memRow, "pharea")+ getString(memRow,"phnumber") +
					strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					return segmentData;
				}
			}
		}catch (Exception e) {
			logInfo("Mde Error: Segment-NASCO_PCNTC entrecno-"+entRecNum);
		}
		return strBlank;
	}
	
	
	@Override
	public String getSegmentDataforQCAREAPADR(MemRow memRow, long entRecNum)
			throws Exception {
		try{
			getMemHeadValues(memRow);
			outputType = ProvEnum.QCARE_APADR.getValue();
			if(l_strSrcCd.equalsIgnoreCase("QCARE")||l_strSrcCd.equalsIgnoreCase("QCAREORG")||
					l_strSrcCd.equalsIgnoreCase("EPDSV2")|| l_strSrcCd.equalsIgnoreCase("EPDSV2ORG")){
				if(ExtMemgetIxnUtils.isNotEmpty(getString(memRow,"COUNTYCD")) &&
						(getString(memRow,"addrtype").equalsIgnoreCase("178") ||
						getString(memRow,"addrtype").equalsIgnoreCase("179"))){
					segmentData = outputType + strDelim + getString(memRow, "entrecno")  + strDelim + l_strSrcCd + 
					strDelim +l_memIdNum +strDelim + getString(memRow, "MD5KEY") + strDelim +
					getString(memRow, "addrtype") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "effectdt")+ strDelim +
					ExtMemgetIxnUtils.getDateAsString(memRow, "termdt")+ strDelim +getString(memRow,"primaryaddress") +
					strDelim + getString(memRow,"COUNTYCD") + strDelim + getString(memRow,"ADDRCOUNTYFIPSCD")+
					strDelim + ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					return segmentData;
				}
			}
		}catch (Exception e) {
			logInfo("Mde Error: Segment-QCARE_APADR entrecno-"+entRecNum);
		}
		return strBlank;
	}

	@Override
	public Set<String> buildAPREMSegment(List<MemAttrRow> orgAPREMMemAttrList,
			long entRecNum) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getSegmentDataforPCLMFRM(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSegmentDataforPOCON(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSegmentDataforPOFSRR(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSegmentDataforPOT(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSegmentDataforPTAX(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getSegmentDataforPREM(MemRow memRow, long entRecNum)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
