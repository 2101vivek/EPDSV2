package com.wellpoint.mde.service;

import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

public interface ReportingGrpService {

	public String getSegmentDataforRPTRL(MemRow memRow, long entRecNum) throws Exception;
	
	public String buildRPTGPSegment(List<MemAttrRow> regrpRPTGPMemAttrList, long entRecNum, String l_strSrcCd_common) throws Exception;
	
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> regrpALTSRCIDMemAttrList, long entRecNum) throws Exception;
}
