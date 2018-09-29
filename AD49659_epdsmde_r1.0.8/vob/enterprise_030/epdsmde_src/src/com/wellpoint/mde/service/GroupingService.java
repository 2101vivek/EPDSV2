package com.wellpoint.mde.service;

import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

public interface GroupingService {
	
	public String getSegmentDataforGALT(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforGPMDE(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforGRMB(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforGNET(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforGRPPGM(MemRow memRow, long entRecNum) throws Exception;
	
	public String 		buildGRPSegment(List<MemAttrRow> grpGRPMemAttrList, long entRecNum) throws Exception;

	public Set<String> 	buildALTSRCIDSegment(List<MemAttrRow> grpGRPMemAttrList, long entRecNum) throws Exception;

	//public String getSegmentDataforGALTRO(MemRow memRow, long entRecNum) throws Exception;

}
