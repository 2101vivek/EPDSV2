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

import com.wellpoint.mde.constants.OrgEnum;
import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.utils.EntityProperties;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public class OrgApaltHelper extends AbstractHelperCr<OrgEnum>{
	
	List<MemAttrRow> OrgAPALTMemAttrList = new ArrayList<MemAttrRow>();
	
	Set<String> segmentDataQcareSet = new HashSet<String>();
	
	Set<String> segmentDataE2ESet = new HashSet<String>();
	
	protected Properties prop_relTypeCode= new Properties();
	
	boolean v2Update = false ;
	
	public boolean isV2Updated() {
		return v2Update;
	}

	public void setv2Updated(boolean qcare) {
		v2Update = qcare;
	}

	public OrgApaltHelper(HashMap<String, String[]> hm_AudRow,
			HashMap<String, MemHead> hm_MemHead, List<outData> outDataList) {
		super();
		setHm_AudRow(hm_AudRow);
		setHm_MemHead(hm_MemHead);
		setOutDataList(outDataList);
		setEntityProp(EntityProperties.getOrgProperties());
	}

	protected void initialize() {
		prop_relTypeCode = ExtMemgetIxnUtils.createPropertyForReltypeCode();
	}
	
	public void SimpleProcessMemrow(MemRowList outMemList, long entRecNum) throws Exception {
		initialize();
		setv2Updated(false);
		for (MemRow memRow : outMemList.listToArray()){
			if (memRow instanceof MemAttrRow) {
				String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
				OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
				
				if (((MemAttrRow)memRow).getInt("rowInd") == 5 && 
						((MemAttrRow)memRow).getString("recStat").equalsIgnoreCase("A")) {
					generateSourceSegments(attrCode, memRow, entRecNum);
				}
			}
		}
		buildOtherSegments(entRecNum);
	}
	
	@Override
	protected void generateSourceSegments(OrgEnum attrCode, MemRow memRow,
			long entRecNum) throws Exception {
		switch(attrCode) {
		
		// APALT-Begin
		case ALTSYSIDSORG:
		case NPIORG:
		case UPINORG:
		case MEDICAREORG:
		case MEDICAIDORG:
		case ENCLRTYIDORG:
		case LICENSEORG:
		case ORGALTIDSPEC: 	OrgAPALTMemAttrList.add((MemAttrRow) memRow);
							break;
		}
	}
	
	@Override
	protected void generateCompositeSegments(OrgEnum attrCode,
			MemRow memRow, long entRecNum) throws Exception {
		
	}
	
	@Override
	protected void buildOtherSegments(long entRecNum) throws Exception {
		Set<String> segmentDataSet = new HashSet<String>();
		segmentDataSet = buildAPALTSegment(OrgAPALTMemAttrList, entRecNum);
		generateSegments(segmentDataSet, OrgEnum.APALT.getValue());
	}
	
	public Set<String> buildAPALTSegment(List<MemAttrRow> orgAPALTMemAttrList, long entRecNum) throws Exception {
		String l_memrecno ="";
		outputType = OrgEnum.APALT.getValue();
		HashMap<String, String> hm_APALT = new HashMap<String, String>();
		HashMap<String,ArrayList<String>> hm_APALT_Org_Alt_ID_Specialty = new HashMap<String,ArrayList<String>>();
		
		for (MemRow memRow : orgAPALTMemAttrList){
			getMemHeadValues(memRow);
			l_memrecno = new Long(memRow.getMemRecno()).toString();
			String l_strAttrCode = ((MemAttrRow)memRow).getAttrCode();
			OrgEnum attrCode = OrgEnum.getInitiateEnumIgnoreCase(l_strAttrCode);
			switch(attrCode) {

			case ALTSYSIDSORG:
			case NPIORG:
			case UPINORG: 
			case MEDICAREORG: 
			case MEDICAIDORG: 
			case ENCLRTYIDORG: 
			case LICENSEORG: {
				if(null!= l_strSrcCd && l_strSrcCd.length()>0
						&& null!=l_memIdNum && l_memIdNum.length()>0
						&& getString(memRow, "idnumber").length()>0
						&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") 
						&& ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate").length()>0
						&& null!=ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
						&& ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate").length()>0) {
					srcCode_postprocess= ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
					reltype_code = ExtMemgetIxnUtils.getRelTypeCode(getString(memRow, "reltype"),prop_relTypeCode);
					if(l_strAttrCode.equalsIgnoreCase("LICENSEORG") && getString(memRow, "licensetype").length()>0) {
						segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
							+ entRecNum + strDelim + "EPDS V2" + strDelim 
							+ getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "idissuer") + strDelim 
							+ getString(memRow, "idnumber")+ strDelim + getString(memRow, "licensetype")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") 
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "termrsn") 
							+ strDelim + l_strSrcCd + strDelim + l_memIdNum + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
							+ strDelim + getString(memRow, "termrsn") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
							+ getString(memRow, "DFCDC_evtctime") + strDelim 
							+ getString(memRow, "DFCDC_mAudRecno") + strDelim
							+ getString(memRow, "acesmedintname") + strDelim + getString(memRow, "acesmedpartind") + strDelim 
							+ getString(memRow , "wgscntrtiercd") + strDelim 
							+ getString(memRow, "epdsssbpaiid") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpapeffdt") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpaptermdt") + strDelim + getString(memRow, "epdsssbpaptermrsn") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbqlfdeffdt") + strDelim + getString(memRow, "epdsssbmdbtcd") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime") 	
							+ strDelim + getString(memRow, "DFCDC_mAudRecno")
							+ strDelim + getString (memRow, "proxtiercd")
							+ strDelim + getString (memRow, "proxmbrcnt")
							+ strDelim + getString (memRow, "proxmbrcapcnt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "proxmbrcntupddt")
							+ strDelim + getString (memRow, "proxfrmage")
							+ strDelim + getString (memRow, "proxtoage")
							+ strDelim + getString (memRow, "proxgendercd")
							+ strDelim + getString (memRow, "proxsrvcind")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxepreffdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxeprtermdt") + strDelim;
						String key = getString(memRow, "IDNUMBER");
						if (key.contains("-")) {
							key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
						}
						hm_APALT.put(getString(memRow, "idissuer")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+"-" +getString(memRow, "licensetype")+"-"  +key+"-" + getString(memRow, "md5key")+"-" + getString(memRow, "MDS5ADDRTYPE")+"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"+ l_memrecno , segmentData );	
					}
					else if(getString(memRow, "idtype").length()>0) {
						segmentData = outputType + strDelim + getString(memRow, "DFCDC_evtctime") + strDelim 
							+ entRecNum + strDelim + "EPDS V2" + strDelim 
							+ getString(memRow, "md5key") + strDelim + getString(memRow, "mds5addrtype") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "mds5addreffectdt") + strDelim + getString(memRow, "idissuer") + strDelim 
							+ getString(memRow, "idnumber")+ strDelim + getString(memRow, "idtype") +strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate") 
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") + strDelim + getString(memRow, "idtermrsn") + strDelim 
							+ l_strSrcCd+ strDelim + l_memIdNum + strDelim
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate") 
							+ strDelim + getString(memRow, "idtermrsn") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim 
							+ getString(memRow, "DFCDC_evtctime") + strDelim 
							+ getString(memRow, "DFCDC_mAudRecno") + strDelim
							+ getString(memRow, "acesmedintname")+ strDelim + getString(memRow, "acesmedpartind") + strDelim 
							+ getString(memRow , "wgscntrtiercd") + strDelim
							+ getString(memRow, "epdsssbpaiid") + strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpapeffdt") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbpaptermdt") + strDelim + getString(memRow, "epdsssbpaptermrsn") + strDelim 
							+ ExtMemgetIxnUtils.getDateAsString(memRow, "epdsssbqlfdeffdt") + strDelim + getString(memRow, "epdsssbmdbtcd") + strDelim 
							+ getString(memRow, "DFCDC_evtinitiator") + strDelim + getString(memRow, "DFCDC_evtctime")
							+ strDelim + getString(memRow, "DFCDC_mAudRecno")
							+ strDelim + getString (memRow, "proxtiercd")
							+ strDelim + getString (memRow, "proxmbrcnt")
							+ strDelim + getString (memRow, "proxmbrcapcnt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString(memRow, "proxmbrcntupddt")
							+ strDelim + getString (memRow, "proxfrmage")
							+ strDelim + getString (memRow, "proxtoage")
							+ strDelim + getString (memRow, "proxgendercd")
							+ strDelim + getString (memRow, "proxsrvcind")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxepreffdt")
							+ strDelim + ExtMemgetIxnUtils.getDateAsString (memRow, "proxeprtermdt") + strDelim;
						String key = getString(memRow, "IDNUMBER");
						if (key.contains("-")) {
							key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
						}
						hm_APALT.put(getString(memRow, "idissuer")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")+"-" +ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+"-" +getString(memRow, "IDTYPE")+"-"  +key+"-" + getString(memRow, "md5key")+"-" + getString(memRow, "MDS5ADDRTYPE")+"-" + ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT")+"-"+ l_memrecno , segmentData );
					}
				}
			}
			break;
			case ORGALTIDSPEC: {
				String key = getString(memRow, "IDNUMBER");
				if (key.contains("-")) 
					key = getString(memRow, "IDNUMBER").replaceAll("-", "*");
				String spec_key = getString(memRow, "idissuer")+"-" +
									ExtMemgetIxnUtils.getDateAsString(memRow, "idexpdate")+"-" +
									ExtMemgetIxnUtils.getDateAsString(memRow, "idissuedate")+"-" +
									getString(memRow, "IDTYPE")+"-" + key+"-" + 
									getString(memRow, "md5key")+"-" + 
									getString(memRow, "MDS5ADDRTYPE")+"-" + 
									ExtMemgetIxnUtils.getDateAsString(memRow, "MDS5ADDREFFECTDT") +"-" + l_memrecno;
				segmentData = getString(memRow, "specialtycd") + strDelim + 
								ExtMemgetIxnUtils.getDateAsString(memRow, "speceffectdt")+ strDelim; 
				ArrayList<String>speciality_list = new ArrayList<String>();
				if(ExtMemgetIxnUtils.isNotEmpty(hm_APALT_Org_Alt_ID_Specialty)) {
					if(hm_APALT_Org_Alt_ID_Specialty.containsKey(spec_key)) { //if contain key 
						speciality_list = hm_APALT_Org_Alt_ID_Specialty.get(spec_key);
					}
				}	
				speciality_list.add(segmentData);
				hm_APALT_Org_Alt_ID_Specialty.put(spec_key, speciality_list);
				
			}
			break;
			}
		}
		return generateSegmentsforAPALT(hm_APALT,hm_APALT_Org_Alt_ID_Specialty);
	}
	
	private Set<String> generateSegmentsforAPALT(HashMap<String, String> hm_APALT,
			HashMap<String,ArrayList<String>> hm_APALT_Org_Alt_ID_Specialty)  throws Exception {
		
		HashMap<String,String> hm_APALT_Org_Alt_ID_Specialty1 = new HashMap<String,String>();
		Set<String> segmentDataSet = new HashSet<String>();
		if (null != hm_APALT && hm_APALT.size() > 0) {
			if(null != hm_APALT_Org_Alt_ID_Specialty && hm_APALT_Org_Alt_ID_Specialty.size() > 0) {			
				String ProvAltIDSpecialty1_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty2_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty3_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty4_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				String ProvAltIDSpecialty5_segmentData1 = ExtMemgetIxnUtils.getNDelimiters(2);
				Set<String> hm_Org_Alt_ID_Specialty_keys = new HashSet<String>();
				hm_Org_Alt_ID_Specialty_keys = hm_APALT_Org_Alt_ID_Specialty.keySet();
				for (Iterator<String> iterator1 = hm_Org_Alt_ID_Specialty_keys.iterator(); iterator1.hasNext();) {
					String hm_Org_Alt_ID_Specialty_key = (String) iterator1.next();
					String[] split_keys1 ; 
					split_keys1 = hm_Org_Alt_ID_Specialty_key.split("-");
					ArrayList<String> valueList = hm_APALT_Org_Alt_ID_Specialty.get(hm_Org_Alt_ID_Specialty_key);
					int count = valueList.size();
					for (Iterator<String> iterator_value = valueList.iterator(); iterator_value.hasNext();) {
						String spec_info = (String) iterator_value.next();						
						switch (count) {
						case 1 : 	ProvAltIDSpecialty1_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						case 2 : 	ProvAltIDSpecialty2_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						case 3 :	ProvAltIDSpecialty3_segmentData1 =spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						case 4: 	ProvAltIDSpecialty4_segmentData1 = spec_info !=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						case 5 : 	ProvAltIDSpecialty5_segmentData1 =spec_info!=null ? spec_info : ExtMemgetIxnUtils.getNDelimiters(2);
									break;
						}	
						count --;
					}
					if(null!=hm_MemHead) {
						MemHead temp_memHead ;
						temp_memHead = (MemHead)hm_MemHead.get(split_keys1[split_keys1.length-1]);
						l_strSrcCd = temp_memHead.getSrcCode();
					}
					segmentData = ProvAltIDSpecialty1_segmentData1 +ProvAltIDSpecialty2_segmentData1+ProvAltIDSpecialty3_segmentData1+ProvAltIDSpecialty4_segmentData1+ProvAltIDSpecialty5_segmentData1 +ExtMemgetIxnUtils.getNDelimiters(59);
					hm_APALT_Org_Alt_ID_Specialty1.put(AddSpliterToContent(split_keys1), segmentData);
					segmentData = "";
				}
			}
			Set <String>APALT_Keys = new HashSet<String>();
			String split_keys[];						
			//get the keys
			APALT_Keys = new HashSet<String>(hm_APALT.keySet());			
			for (Iterator<String> iterator = APALT_Keys.iterator(); iterator.hasNext();) {
				String APALT_Key = iterator.next();
				split_keys = APALT_Key.split("-");	
				if (null == hm_APALT_Org_Alt_ID_Specialty.get(AddSpliterToContent(split_keys))) {
					segmentData = ExtMemgetIxnUtils.getNDelimiters(69);
					hm_APALT_Org_Alt_ID_Specialty1.put(AddSpliterToContent(split_keys), segmentData);
				}						
				//create a full segment						
				segmentData=hm_APALT.get(AddSpliterToContent(split_keys)) +
				hm_APALT_Org_Alt_ID_Specialty1.get(AddSpliterToContent(split_keys));
				outputType = OrgEnum.APALT.getValue();								
				segmentDataSet.add(segmentData);
			}
			
		}
		return segmentDataSet;
	}
}
