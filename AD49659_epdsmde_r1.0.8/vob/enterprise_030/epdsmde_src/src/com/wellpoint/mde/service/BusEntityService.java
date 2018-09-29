package com.wellpoint.mde.service;

import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

public interface BusEntityService {
	
	public String getSegmentDataforBALT(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforBADR(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforBECON(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforBUSGRP(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforAPADR(MemRow memRow, long entRecNum) throws Exception;
	
	public String buildBUSSegment(List<MemAttrRow> busBUSMemAttrList, long entRecNum) throws Exception;
	
	public String buildBCNTCSegment(List<MemAttrRow> busBCNTCMemAttrList,long entRecNum) throws Exception;
	
	public String buildBTAXSegment(List<MemAttrRow> busBTAXMemAttrList,long entRecNum) throws Exception;
	
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> busALTSRCIDMemAttrList,long entRecNum) throws Exception;
	
}
