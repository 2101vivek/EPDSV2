package com.wellpoint.mde.CR209;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.constants.ProvEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class ProvApaltE2EHelper extends AbstractHelperCr<ProvEnum>{
	
	List<MemAttrRow> ProvAPALTMemAttrList = new ArrayList<MemAttrRow>();
	
	List<MemAttrRow> E2EProvPALTMemAttrList = new ArrayList<MemAttrRow>();
	
	Set<String> segmentDataSet = new HashSet<String>();

	Set<String> segmentDataQcareSet = new HashSet<String>();

	Set<String> segmentDataE2ESet = new HashSet<String>();
	
	protected Properties prop_relTypeCode= new Properties();

	public ProvApaltE2EHelper(HashMap<String, String[]> hm_AudRow,HashMap<String, MemHead> hm_MemHead,List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
		setEntityProp(EntityProperties.getProvProperties());
	}
	
	private void initialize() {
		prop_relTypeCode = ExtMemgetIxnUtils.createPropertyForReltypeCode();
	}
	
	public void v2UpdatedProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		String srccode = "";
		initialize();
		for (MemRow memRow : outMemList.listToArray()){
			if(memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				ProvEnum attrCode = ProvEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				srccode = memRow.getSrcCode();

				try{
					if(((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")){
						
						//Composite view with new ECO with tier1 merge rules
						if (((MemAttrRow)memRow).getInt("rowInd") == 3) {
							generateRetiredSourceSegments(attrCode, memRow, entRecNum);
						}
						else if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
								!getEntityProp().get("QCARE").equalsIgnoreCase(srccode) &&
								!getEntityProp().get("EPDSV2").equalsIgnoreCase(srccode)
								/*Turn Off P to A copy for R6[CQ WLPRD02490909 fix]*/
								//&& !getEntityProp().get("EPDS1").equalsIgnoreCase(srccode)
								
								) {
							
							generateSourceSegments(attrCode, memRow, entRecNum);
						}
					}
				} catch (Exception e) {
				}
			}
		}
		buildOtherSegments(entRecNum);
	}
	
	private void generateRetiredSourceSegments(ProvEnum attrCode,MemRow memRow, 
			long entRecNum)throws Exception {

		switch(attrCode) {
		//E2E APALT:
		case PROVALTSYSID:
		case NPI:
		case UPIN:
		case DEAID:
		case MEDICARE:
		case MEDICAID:
		case PROVLICENSE:
		case PRVALTIDSPEC:	
		case ENCLARITYID: E2EProvPALTMemAttrList.add((MemAttrRow) memRow);
							break;
		}
	}
	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		
		//APALT
		segmentDataSet = buildAPALTSegment(ProvAPALTMemAttrList, entRecNum, APALTFlag);
		generateSegments(segmentDataSet, ProvEnum.APALT.getValue());
		
		//P to A Copy: Generating EPDSV2 source record for APALT
		APALTFlag = true;
		outputType = ProvEnum.APALT.getValue();
		segmentDataSet = buildAPALTSegment(E2EProvPALTMemAttrList, entRecNum, APALTFlag);
		for (String segmntData : segmentDataSet) {
			segmentDataE2ESet = E2EgenerateSourceLevelSegments(segmntData,null,e2eLeagcyidMap,13,14,113,true);
			for (String segData : segmentDataE2ESet) {
				
				boolean Epds1AltIdFlag = false;
				boolean NpiFlag = false;
				boolean qcareAltIdFlag = false;
				String[] comb_key_array = segData.split("~",-1);
				String segment_Data = ExtMemgetIxnUtils.join(comb_key_array, "~", 0, 113);
				segmentData = segment_Data;
				generateRow();
				
				/*For PCP IDs & License numbers - When V2 creates the extract in MDE, 
				 * V2 will send license/PCP IDs for corresponding source systems 
				 * extracted to A segments not all licenses/PCP IDs for the Individual, 
				 * if there are more than 1.*/
				MemHead temp_memHead = (MemHead)hm_MemHead.get(comb_key_array[113]);
				if (null != temp_memHead){
					l_strSrcCd = temp_memHead.getSrcCode();
					l_memIdNum = temp_memHead.getMemIdnum();
				}

				if ((comb_key_array[7].equalsIgnoreCase("1945") || comb_key_array[7].equalsIgnoreCase("903"))
						&& (comb_key_array[9].equalsIgnoreCase("8016") || comb_key_array[9].equalsIgnoreCase("8023")
								|| comb_key_array[9].equalsIgnoreCase("8006") || comb_key_array[9].equalsIgnoreCase("879"))) {
					qcareAltIdFlag = true;
				}
				if(comb_key_array[7].equalsIgnoreCase("8005") && 
						(comb_key_array[9].equalsIgnoreCase("8006") || comb_key_array[9].equalsIgnoreCase("8023")
								|| comb_key_array[9].equalsIgnoreCase("8016") || comb_key_array[9].equalsIgnoreCase("879"))){
					Epds1AltIdFlag = true;
				}
				if(comb_key_array[9].equals("8014") || comb_key_array[9].equals("8015") || comb_key_array[9].equals("33")) {
					NpiFlag = true;
				}
				//EPDSV2 UI added PCP IDs & License numbers should be delivered to all R6 legacy id's available 
				if(l_strSrcCd.equalsIgnoreCase("EPDSV2")){
					if(!Epds1AltIdFlag){
						String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[12]);
						if(NpiFlag){
							segmentDataQcareSet = QcaregenerateSourceLevelSegmentsApalt(segment_Data,QcareAlternateIdMap, QcareALTSRCIDMap,
									comb_key,13,14,113,"Prov");
						}else {
						segmentDataQcareSet = QcaregenerateSourceLevelSegments(segment_Data,QcareAlternateIdMap,
								comb_key,13,14,113,"Prov");
						}
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
					}
					if(!qcareAltIdFlag){
						segmentDataQcareSet = E2EgenerateSourceLevelSegments(segment_Data,allSourceCodeSet,
								e2eLeagcyidMap,13,14,113,false);
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
					}
				}
				//PCP IDs & License (legacy) id of other source system's should not delivered to R6 
				else if(comb_key_array[9].equalsIgnoreCase("8023") || comb_key_array[9].equalsIgnoreCase("8006")
						||comb_key_array[9].equalsIgnoreCase("8016") || comb_key_array[9].equalsIgnoreCase("879") ){
					/*Turn Off P to A copy for R6[CQ WLPRD02490909 fix]*/
					/*if(comb_key_array[7].equalsIgnoreCase("8005")){
						String[] splitseg =  segment_Data.split("~",-1);
						splitseg[13] = getEntityProp().get("EPDS1");
						splitseg[14] = l_memIdNum;
						segmentData = ExtMemgetIxnUtils.join(splitseg, "~", 0, 113);
						generateRow();
					}
					else */if (comb_key_array[7].equalsIgnoreCase("1945") || comb_key_array[7].equalsIgnoreCase("903")){
						String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[12]);
						if(NpiFlag){
							segmentDataQcareSet = QcaregenerateSourceLevelSegmentsApalt(segment_Data,QcareAlternateIdMap, QcareALTSRCIDMap,
									comb_key,13,14,113,"Prov");
						}else {
						segmentDataQcareSet = QcaregenerateSourceLevelSegments(segment_Data,QcareAlternateIdMap,
								comb_key,13,14,113,"Prov");
						}
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
					}
					/*Turn Off P to A copy for R6[CQ WLPRD02490909 fix]*/
					else if (!comb_key_array[7].equalsIgnoreCase("8005") && comb_key_array[9].equalsIgnoreCase("879")){
						//QCARE granularity
						String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[12]);
						segmentDataQcareSet = QcaregenerateSourceLevelSegments(segment_Data,QcareAlternateIdMap,
								comb_key,13,14,113,"Prov");
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
						
						//P to A Copy: Generating EPDS1 source record
						segmentDataQcareSet = E2EgenerateSourceLevelSegments(segment_Data,allSourceCodeSet,
								e2eLeagcyidMap,13,14,113,false);
						generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
					}
				}
				else if(!comb_key_array[9].equalsIgnoreCase("8006") && !comb_key_array[9].equalsIgnoreCase("8023")
						&& !comb_key_array[9].equalsIgnoreCase("8016") && !comb_key_array[9].equalsIgnoreCase("879")){
					String comb_key = getCombAddresskey(comb_key_array[4],comb_key_array[5],comb_key_array[12]);
					if(NpiFlag){
						segmentDataQcareSet = QcaregenerateSourceLevelSegmentsApalt(segment_Data,QcareAlternateIdMap, QcareALTSRCIDMap,
								comb_key,13,14,113,"Prov");
					}else {
					segmentDataQcareSet = QcaregenerateSourceLevelSegments(segment_Data,QcareAlternateIdMap,
							comb_key,13,14,113,"Prov");
					}
					generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());

					segmentDataQcareSet = E2EgenerateSourceLevelSegments(segment_Data,allSourceCodeSet,
							e2eLeagcyidMap,13,14,113,false);
					generateSegments(segmentDataQcareSet, ProvEnum.APALT.getValue());
				}
			}
		}
				
	}

	@Override
	protected void generateCompositeSegments(ProvEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
				
	}

	@Override
	protected void generateSourceSegments(ProvEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		//APALT
		case PROVALTSYSID:
		case NPI:
		case UPIN:
		case DEAID:
		case MEDICARE:
		case MEDICAID:
		case ENCLARITYID:
		case PROVLICENSE:
		case PRVALTIDSPEC:	ProvAPALTMemAttrList.add((MemAttrRow) memRow);
							break;
		
		}
	}

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
					+ExtMemgetIxnUtils.getNDelimiters(59);
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
					segmentData = ExtMemgetIxnUtils.getNDelimiters(69);
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
}
