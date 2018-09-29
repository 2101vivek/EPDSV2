package com.wellpoint.mde.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import madison.mpi.MemRow;

import com.wellpoint.mde.baseMemgetIxn.AbstractSegment;

public class AbstractServiceImpl extends AbstractSegment {

	HashMap<String, String> term_records = new HashMap<String, String>();
	
	HashMap<String, Date> term_Dates = new HashMap<String, Date>();
	
	protected Properties prop_relTypeCode= new Properties();
	
	protected Properties degree_codes= new Properties();
	
	protected Properties school_name= new Properties();
	
	protected String netIdType="NET";
	protected String netIdSource="EPDS1NET";
	
	public Properties getProp_relTypeCode() {
		return prop_relTypeCode;
	}

	public void setProp_relTypeCode(Properties prop_relTypeCode) {
		this.prop_relTypeCode = prop_relTypeCode;
	}
	
	public Properties getDegree_codes() {
		return degree_codes;
	}

	public void setDegree_codes(Properties degree_codes) {
		this.degree_codes = degree_codes;
	}
	
	public Properties getSchool_name() {
		return school_name;
	}

	public void setSchool_name(Properties school_name) {
		this.school_name = school_name;
	}


	
	/**
	 * ADDED FOR PROVINACTIVE TERM DATE CHECK
	 * @param memRow
	 * @param key
	 * @param segmentData
	 * @return
	 * @throws Exception
	 */
	public String getTermAfterValidation(MemRow memRow,String key, String segmentData, String fieldName) throws Exception
	{
		try
		{
			if(((term_records.size()>0)&&(!term_records.containsKey(key))) || (!(term_records.size()>0)))
			{
				term_records.put(key, segmentData);
				Date termdt = memRow.getDate(fieldName);
				term_Dates.put(key, termdt);
				return segmentData;
			}
			else if(term_records.containsKey(key))
			{
				Date termdt,compare_termdt;
				compare_termdt = term_Dates.get(key);
				termdt= memRow.getDate(fieldName);
				if (null!=termdt){
					if((compare_termdt == null) ||(compare_termdt.compareTo(termdt))>0)
					{
						return term_records.get(key);
					}
					else
					{
						term_records.put(key, segmentData);
						term_Dates.put(key, termdt);
						return segmentData;
					}
				}else{
					term_records.put(key, segmentData);
					return segmentData;
				}
			}
		}
		catch (Exception ex) {
			logInfo("String getter exception - termdt  - " + memRow);
			throw ex;     
		}
		return null;
	}
}
