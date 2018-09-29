package com.wellpoint.mde.service;

import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

public interface HmoService {

	public String getSegmentDataforWSHRMGN(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWMBREF(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWSVAREA(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWTAX(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWSCH(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWSVC(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWENR(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWROL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWADR(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWCNTC(MemRow memRow, long entRecNum)	throws Exception;
	
	public String getSegmentDataforWNOTE(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWCON(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWNET(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWALT(MemRow memRow, long entRecNum) throws Exception;
	/* ADDED FOR WPDM */
	public String getSegmentDataforWPDM(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWRGN(MemRow memRow, long entRecNum) throws Exception;
	
	public String buildWHMSegment(List<MemAttrRow> hmoWHMMemAttrList, long entRecNum) throws Exception;
	
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> hmoALTSRCIDMemAttrList, long entRecNum) throws Exception;
	
}
