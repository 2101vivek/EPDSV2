package com.wellpoint.mde.service;

import java.util.List;

import madison.mpi.MemAttrRow;

public interface RmbService {

	public String buildMRMBSegment(List<MemAttrRow> rmbMRMBMemAttrList, long entRecNum, String l_memIdNum_common, String l_strSrcCd_common) throws Exception;
	
	public String buildMRSVCSegment(List<MemAttrRow> rmbMRSVCMemAttrList, long entRecNum) throws Exception;
	
}
