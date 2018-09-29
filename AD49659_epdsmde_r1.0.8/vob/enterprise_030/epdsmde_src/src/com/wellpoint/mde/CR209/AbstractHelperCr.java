package com.wellpoint.mde.CR209;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import madison.mpi.MemRow;

import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public abstract class AbstractHelperCr<T extends Enum<T>> extends AbstractSegment{
	
	Map<String, String> entityProp = new HashMap<String, String>();
	
	public Map<String, String> getEntityProp() {
		return entityProp;
	}

	public void setEntityProp(Map<String, String> entityProp) {
		this.entityProp = entityProp;
	}

	public Set<String> E2EgenerateSourceLevelSegments(String epdsv2SegmentData,Set<String> allSourceCodeSet,
			HashMap<String,ArrayList<String>> e2eLeagcyidMap,int srccodeIndex,int memidnumIndex,int srcsystmsIndex,
			Boolean epdsv2CopyFlag){
		
		Set<String> segmentDataSet = new HashSet<String>();
		ArrayList<String> epdsv2MemrecnoList = e2eLeagcyidMap.get(getEntityProp().get("EPDSV2"));
		String epdsv2memrecno = epdsv2MemrecnoList.get(0);
		
		if(null ==allSourceCodeSet && epdsv2CopyFlag)
		{
			String[] splitseg =  epdsv2SegmentData.split("~",-1);
			splitseg[srccodeIndex] = getEntityProp().get("EPDSV2");			
			splitseg[memidnumIndex] = epdsv2memrecno;
			segmentData = ExtMemgetIxnUtils.join(splitseg, "~");
			segmentDataSet.add(segmentData);
			return segmentDataSet;
		}
		/*if (allSourceCodeSet.contains(getEntityProp().get("EPDS1")) && !epdsv2CopyFlag){
			ArrayList<String> epds1memidnumList = e2eLeagcyidMap.get(getEntityProp().get("EPDS1"));
			for (Iterator<String> iterator_memidnum = epds1memidnumList.iterator(); iterator_memidnum.hasNext();) {
				String epds1memidnum = (String) iterator_memidnum.next();
				String[] splitseg =  epdsv2SegmentData.split("~",-1);
				splitseg[srccodeIndex] = getEntityProp().get("EPDS1");
				splitseg[memidnumIndex] = epds1memidnum;
				segmentData = ExtMemgetIxnUtils.join(splitseg, "~");
				segmentDataSet.add(segmentData);
			}
			}*/
		
		return segmentDataSet;
	}
	
	public Set<String> QcaregenerateSourceLevelSegments(String epdsv2SegmentData,HashMap<String,ArrayList<String>> QcareAlternateIdMap,
			String combtn_key,int srccodeIndex,int memidnumIndex,int srcsystmsIndex,String entityType){

		
		String Qcare = getEntityProp().get("QCARE");
		int length = 0;
		int keyLength = combtn_key.length();
		if(null != combtn_key && !combtn_key.isEmpty()){
			Set<String> segmentDataSet = new HashSet<String>();
			List<String> altIdList = QcareAlternateIdMap.get(combtn_key);
			if(ExtMemgetIxnUtils.isEmpty(altIdList)) {
				length = combtn_key.lastIndexOf(HYPHEN)+1;
				if(keyLength >= length) {
					combtn_key = combtn_key.substring(0, length);
					altIdList = QcareAlternateIdMap.get(combtn_key);
				}
			}
			if (null != altIdList && !altIdList.isEmpty()) {
				for(String alternateId: altIdList) {
					String[] splitseg =  epdsv2SegmentData.split("~",-1);
					splitseg[srccodeIndex] = Qcare;
					splitseg[memidnumIndex] = alternateId;
					segmentData = ExtMemgetIxnUtils.join(splitseg, "~");
					segmentDataSet.add(segmentData);
				}
			}
			return segmentDataSet;
		}
		else if (combtn_key.isEmpty() && null != combtn_key){
			Set<String> segmentDataSet = new HashSet<String>();
			
			for(Map.Entry<String ,ArrayList<String>> entry: QcareAlternateIdMap.entrySet()) {
				for(String alternateId: entry.getValue()) {
					String[] splitseg =  epdsv2SegmentData.split("~",-1);
					splitseg[srccodeIndex] = Qcare;
					splitseg[memidnumIndex] = alternateId;
					segmentData = ExtMemgetIxnUtils.join(splitseg, "~");
					segmentDataSet.add(segmentData);
				}
			}
			return segmentDataSet;
		}
		return null;
	}
	
	public Set<String> QcaregenerateSourceLevelSegmentsApalt(String epdsv2SegmentData,HashMap<String,ArrayList<String>> QcareAlternateIdMap,
			HashMap<String,String> QcareALTSRCIDMap, String combtn_key,int srccodeIndex,int memidnumIndex,int srcsystmsIndex,String entityType){

		Set<String> segmentDataSet = new HashSet<String>();
		String srcCode = getEntityProp().get("QCARE");
		if(null != combtn_key) {
			if(!combtn_key.isEmpty()){
				
				List<String> altIdList = QcareAlternateIdMap.get(combtn_key);
				if (null != altIdList && !altIdList.isEmpty()) {
					for(String memIdnum: altIdList) {
						segmentDataSet.addAll(addQcareSegmentInfo(epdsv2SegmentData, srccodeIndex, memidnumIndex, srcsystmsIndex, srcCode, memIdnum, true));
					}
				}
			}
			else {
	
				for(Map.Entry<String ,ArrayList<String>> entry: QcareAlternateIdMap.entrySet()) {
					for(String memIdnum: entry.getValue()) {
						segmentDataSet.addAll(addQcareSegmentInfo(epdsv2SegmentData, srccodeIndex, memidnumIndex, srcsystmsIndex, srcCode, memIdnum, true));
					}
				}
			}
		}
		return segmentDataSet;
	}

	/**
	 * addQcareSegmentInfo used for adding md5key, typecode and effectivedate for a Qcare segment with corresponding legacyId. 
	 * Used specifically for APALT segments.
	 * @param epdsv2SegmentData
	 * @param srccodeIndex
	 * @param memidnumIndex
	 * @param srcsystmsIndex
	 * @param srcCode
	 * @param memIdnum
	 * @param apalt
	 * @return Qcare segment
	 */
	private Set<String> addQcareSegmentInfo(String epdsv2SegmentData,
			int srccodeIndex, int memidnumIndex, int srcsystmsIndex, String srcCode, String memIdnum, boolean apalt) {
		Set<String> segmentDataSet = new HashSet<String>();
		String[] splitseg =  epdsv2SegmentData.split("~",-1);
		splitseg[srccodeIndex] = srcCode;
		splitseg[memidnumIndex] = memIdnum;
		
		ArrayList<String> md5DetailList = new ArrayList<String>();
		md5DetailList = QcareAddressMap.get(splitseg[14]);
		for (String string : md5DetailList) {
			String[] md5Detail = string.split("-",-1);
			if(null != md5Detail) {
				splitseg[4] = md5Detail[0];
				splitseg[5] = md5Detail[1];
				splitseg[6] = md5Detail[2];
			}
			segmentData = ExtMemgetIxnUtils.join(splitseg, "~");
			segmentDataSet.add(segmentData);
		}
		
		return segmentDataSet;
	}
	
	/**
	 * For Qcare Granularity
	 * Return comb_key as MD5KEY+MDS5ADDRTYPE+TERMREASON when MD5KEY is present
	 * Return comb_key as blank when MD5KEY is not present
	 * @param md5key
	 * @param address type
	 * @param termination reason
	 * @return
	 */
	public String getCombAddresskey(String md5key, String addrtype, String termReason) {
		
		String addresstype = getEntityProp().get("ADDRTYPE");
		if(null != termReason)
			termReason = termReason.equals(CIE_CODE)? CIE : EMPTY;
		
		if (!md5key.isEmpty() && md5key != null && (addrtype.equalsIgnoreCase(addresstype) || 
				(isDualSubmitter() && addrtype.equalsIgnoreCase("178")))){
			
			return md5key + HYPHEN + addrtype + HYPHEN + termReason;
		}
		else if (md5key.isEmpty() && md5key != null){
			return EMPTY;
		}
		return EMPTY;
	}

	
	protected abstract void generateCompositeSegments(T attrCode, MemRow memRow, long entRecNum) throws Exception;
	
	protected abstract void generateSourceSegments(T attrCode, MemRow memRow, long entRecNum) throws Exception;
	
	protected abstract void buildOtherSegments(long entRecNum)  throws Exception;
}
