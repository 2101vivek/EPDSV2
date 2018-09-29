package com.wellpoint.mde.baseMemgetIxn;


import java.util.List;

import madison.mpi.AudRow;
import madison.mpi.AudRowList;
import madison.mpi.IxnMemGet;
import madison.mpi.MemHead;
import madison.mpi.MemRow;
import madison.mpi.MemRowList;

import com.wellpoint.mde.generateRow.outData;
import com.wellpoint.mde.utils.ExtMemgetIxnUtils;

public abstract class AbstractMemgetIxn extends AbstractSegment{

		
	protected int l_errorCd;
	protected String l_errorTxt;
	
	protected boolean Memhead_QCARE_Flag = false;
	protected boolean EPDSV2_UpdatedFlag = false;
	protected boolean E2EFlag = false;
	
	protected static final String strDelimPipe  = "|";
	
	boolean pnet = false;
	
	public boolean isPnet() {
		return pnet;
	}

	public void setPnet(boolean pnet) {
		this.pnet = pnet;
	}
	
	public int getL_errorCd() {
		return l_errorCd;
	}
	
	public String getL_errorTxt() {
		return l_errorTxt;
	}
	
	/**Adds AudRecNo and AudArray having two fields of 
	 * evtInitiator and evtCtime to hm_AudRow List.
	 * @param outAudList
	 */
	public void getHm_AudRowlist(AudRowList outAudList) {
		String temp;// to get the yyyyMMddHHmmss format for evtCtime
		String[] strAudArray = new String[2];
		String strAudRecNo;
		for (AudRow audRow: outAudList.listToArray())
		{
			strAudArray= new String[2];
			try	{
				strAudArray[0] = (null == audRow.getAsString("evtInitiator")) ? "" : audRow.getAsString("evtInitiator");
				temp = (null == audRow.getDateAsDT("evtCtime")) ? "" : audRow.getDateAsDT("evtCtime");
				strAudArray[1] = temp.replace("-", "").replace(" ", "").replace(":", "");
				strAudRecNo = new Long(audRow.getAudRecno()).toString();
				hm_AudRow.put(strAudRecNo, strAudArray);
			}
			catch (Exception ex)	{
				logInfo("Error getting AUDIT Info");
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Code For Introducing a new hash map to store 
	 * l_memIdNum,l_strSrcCd ,l_strCaudrecno & l_maudRecNo 
	 * from MEMHEAD with memRecno as the key
	 * Adding all memHead sourceCode to allSourceCodeSet
	 */
	public void getSrcCodes(MemRowList outMemList) {
		String key_memRecNo;
		MemHead memHead;
		for (MemRow memRow :outMemList.listToArray())
		{
			if (memRow instanceof MemHead) 
			{
				key_memRecNo = new Long(memRow.getMemHead().getMemRecno()).toString();
				memHead = new MemHead();
				memHead = memRow.getMemHead();
				if(null!=hm_MemHead && null==hm_MemHead.get(key_memRecNo))
				{
					hm_MemHead.put(key_memRecNo, memHead);
					//Collecting the srcCodes for pre-fixing each segment.
					allSourceCodeSet.add(memRow.getSrcCode());
				}
			}
		}
	}
	
	/**
	 * Separating all the source codes with pipe
	 */
	public void setSourceCodesDelimited(){
		String sourceCodeNm = null;
		for (String  srcCode:allSourceCodeSet) {
			sourceCodeNm = ExtMemgetIxnUtils.getSrcCodeforPostProcess(srcCode);
			srcCodesDelimited = srcCodesDelimited + sourceCodeNm + strDelimPipe;
			/** END*/
		}
		/**
		* for EPDSV2 inserted records, to generate the ATSRCID segment -> generateAltsrcID_Flag = true
		*/
		if(allSourceCodeSet.size()==1 && null !=sourceCodeNm){
			if(!isEPDSV2_Flag() && sourceCodeNm.equalsIgnoreCase("EPDSV2")){
				setEPDSV2_Flag(true);
			}
		}
		srcCodesDelimited = srcCodesDelimited.substring(0, srcCodesDelimited.length() - 1);
		
	}
	
	public abstract List<outData> processMemRows(MemRowList outMemList, AudRowList outAudList, long entRecNum) throws Exception;
	
	public abstract void setMemGetProp(IxnMemGet memGet);
}
