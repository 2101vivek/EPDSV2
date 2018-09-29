package com.wellpoint.mde.baseMemgetIxn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import madison.mpi.MemHead;
import madison.mpi.MemRow;

import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public abstract class AbstractSegment {
	
	protected static final String strDelim = "~";
	protected static final String strBlank = "";
	protected static final String CIE_CODE = "297";
	protected static final String CIE = "CIE";
	protected static final String EMPTY = "";
	protected static final String HYPHEN = "-";
	
	
	protected String segmentData;
	protected String outputType;
	protected String l_strCaudrecno;
	protected String srcCode_postprocess="";
	protected String l_strSrcCd = "";
	protected String l_memIdNum="";
	protected String l_maudRecNo="";
	
	protected String reltype_code="";
	protected String srccode_lookup = "";
	
	protected String srcCodesDelimited = "";
	protected String epdsv2memrecno = "";
	
	protected boolean EPDSV2_Flag = false;
	protected boolean APALTFlag = false;
	protected boolean DualSubmitter = false;
	
	protected HashMap<String, MemHead>hm_MemHead = new HashMap<String,MemHead>();
	protected Set<String> allSourceCodeSet = new HashSet<String>();
	protected HashMap<String,ArrayList<String>> QcareAlternateIdMap = new HashMap<String, ArrayList<String>>();
	protected HashMap<String,String> QcareALTSRCIDMap = new HashMap<String, String>();
	protected HashMap<String,ArrayList<String>> QcareAddressMap = new HashMap<String, ArrayList<String>>();
	protected HashMap<String,ArrayList<String>>e2eLeagcyidMap = new HashMap<String, ArrayList<String>>();
	private List<outData> outDataList = new ArrayList<outData>();
	protected HashMap<String, String[]> hm_AudRow = new HashMap<String, String[]>();
	

	public boolean isDualSubmitter() {
		return DualSubmitter;
	}

	public void setDualSubmitter(boolean dualSubmitter) {
		DualSubmitter = dualSubmitter;
	}
	
	public Set<String> getAllSourceCodeSet() {
		return allSourceCodeSet;
	}

	public void setAllSourceCodeSet(Set<String> allSourceCodeSet) {
		this.allSourceCodeSet = allSourceCodeSet;
	}
	
	public boolean isAPALTFlag() {
		return APALTFlag;
	}

	public void setAPALTFlag(boolean flag) {
		APALTFlag = flag;
	}
	
	public boolean isEPDSV2_Flag() {
		return EPDSV2_Flag;
	}

	public void setEPDSV2_Flag(boolean flag) {
		EPDSV2_Flag = flag;
	}
	
	public HashMap<String, MemHead> getHm_MemHead() {
		return hm_MemHead;
	}

	public void setHm_MemHead(HashMap<String, MemHead> hm_MemHead) {
		this.hm_MemHead = hm_MemHead;
	}
	
	public HashMap<String, String[]> getHm_AudRow() {
		return hm_AudRow;
	}

	public void setHm_AudRow(HashMap<String, String[]> hm_AudRow) {
		this.hm_AudRow = hm_AudRow;
	}

	public List<outData> getOutDataList() {
		return outDataList;
	}

	public void setOutDataList(List<outData> outDataList) {
		this.outDataList = outDataList;
	}
	
	public String getL_strCaudrecno() {
		return l_strCaudrecno;
	}

	public void setL_strCaudrecno(String caudrecno) {
		l_strCaudrecno = caudrecno;
	}

	public String getSrcCode_postprocess() {
		return srcCode_postprocess;
	}

	public void setSrcCode_postprocess(String srcCode_postprocess) {
		this.srcCode_postprocess = srcCode_postprocess;
	}

	public String getL_strSrcCd() {
		return l_strSrcCd;
	}

	public void setL_strSrcCd(String srcCd) {
		l_strSrcCd = srcCd;
	}

	public String getL_memIdNum() {
		return l_memIdNum;
	}

	public void setL_memIdNum(String idNum) {
		l_memIdNum = idNum;
	}

	public String getL_maudRecNo() {
		return l_maudRecNo;
	}

	public void setL_maudRecNo(String recNo) {
		l_maudRecNo = recNo;
	}

	public String getSrcCodesDelimited() {
		return srcCodesDelimited;
	}

	public void setSrcCodesDelimited(String srcCodesDelimited) {
		this.srcCodesDelimited = srcCodesDelimited;
	}

	public void setQcareAlternateIdMap(HashMap<String,ArrayList<String>> QcareAlternateIdMap) {
		this.QcareAlternateIdMap = QcareAlternateIdMap;
	}
	
	public void setQcareALTSRCIDMap(HashMap<String,String> QcareALTSRCIDMap) {
		this.QcareALTSRCIDMap = QcareALTSRCIDMap;
	}
	
	public void setQcareAddressMap(HashMap<String, ArrayList<String>> qcareAddressMap) {
		this.QcareAddressMap = qcareAddressMap;
	}
	
	public HashMap<String, ArrayList<String>> getE2eLeagcyidMap() {
		return e2eLeagcyidMap;
	}

	public void setE2eLeagcyidMap(HashMap<String, ArrayList<String>> leagcyidMap) {
		this.e2eLeagcyidMap = leagcyidMap;
	}
	
	public String getEpdsv2memrecno() {
		return epdsv2memrecno;
	}

	public void setEpdsv2memrecno(String epdsv2memrecno) {
		this.epdsv2memrecno = epdsv2memrecno;
	}

	protected void generateRow() {
		if(ExtMemgetIxnUtils.isNotEmpty(segmentData)) {
			outData segData = new outData();
			segData.setType("GR");
			segData.setSegData(segmentData);
			segData.setOutType(outputType);
			outDataList.add(segData);
		}
	}
	
	protected void logInfo(String s) {
		outData segData = new outData();
		segData.setType("LI");
		segData.setSegData(s);
		outDataList.add(segData);
	}
	
	protected void generateSegments(Set<String> segmentDataSet, String outType)throws Exception {
		if(null != segmentDataSet) {
			for (String data : segmentDataSet) {
				segmentData=data;
				outputType=outType;
				generateRow();
			}
			segmentDataSet.clear();
		}
	}
	
	protected void getMemHeadValues(MemRow memRow) {
		if(null!=hm_MemHead) {
			MemHead temp_memHead ;
			temp_memHead = (MemHead)hm_MemHead.get(new Long(memRow.getMemRecno()).toString());
			l_strSrcCd = temp_memHead.getSrcCode();
			l_memIdNum = temp_memHead.getMemIdnum();
			l_strCaudrecno= new Long(temp_memHead.getCaudRecno()).toString();
			l_maudRecNo = new Long(temp_memHead.getMaudRecno()).toString();
		}
		if(null!=l_strSrcCd && l_strSrcCd.length()>0) {
			srcCode_postprocess = ExtMemgetIxnUtils.getSrcCodeforPostProcess(l_strSrcCd);
		}
	}
	
	protected String getString(MemRow row, String fieldName) throws Exception
	{
		if (fieldName.equals("DFCDC_evtinitiator")
				|| fieldName.equals("DFCDC_evtctime")
				|| fieldName.equals("DFCDC_mAudRecno")) {
			return (getCDCString(row, fieldName));
		}

		return ExtMemgetIxnUtils.getString(row, fieldName);
	}
	
	protected String getCDCString(MemRow row, String fieldName) throws Exception	{
		String strMAudRecNo;
		String strCAudRecNo;
		String[] strAud;
		String strReturn = "";
		try	{
			if (null == row.getAsString("mAudRecno"))    {
				strReturn = "";
			}
			else    {
				strMAudRecNo = new Long(row.getLong("mAudRecno")).toString();
				//for CDC Indicator we have to compare with the cAudRecno field
				strCAudRecNo = new Long(row.getLong("cAudRecno")).toString();
				if (fieldName.equals("DFCDC_evtinitiator"))	{
					strAud = hm_AudRow.get(strMAudRecNo);
					strReturn = strAud[0];
				}
				else if (fieldName.equals("DFCDC_evtctime"))	{
					strAud = hm_AudRow.get(strMAudRecNo);
					strReturn = strAud[1];
				}
				else if (fieldName.equals("DFCDC_mAudRecno"))	{
					if (l_strCaudrecno.equals(strCAudRecNo))	{
						strReturn = "0";
					}
					else	{
						strReturn = "1";
					}
				}
			}
		}
		catch (Exception ex)	{
			logInfo("CDC String getter exception - mAudRecno - " + row);
			throw ex;  
		}

		return strReturn;
	}
	
	protected String AddSpliterToContent(String split_keys[]) {
		List<String> wordList = Arrays.asList(split_keys);  
		StringBuilder stringBuilder = new StringBuilder();
		for (int i=0;i<wordList.size();i++) {
			if(i!=wordList.size()-1) {
				stringBuilder.append(wordList.get(i)).append("-");
			}
			else {
				stringBuilder.append(wordList.get(i));
			}
		}
		return stringBuilder.toString(); 
	}
}
