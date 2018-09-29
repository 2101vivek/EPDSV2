package com.wellpoint.mde.service;

import java.util.List;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

public interface NetworkService {

	public String getSegmentDataforMNTRL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforMNTRM(MemRow memRow, long entRecNum)	throws Exception;
	
	public String buildMNETSegment(List<MemAttrRow> netMNETMemAttrList,
			long entRecNum, String l_memIdNum_common, String l_strSrcCd_common) throws Exception;
	
}
