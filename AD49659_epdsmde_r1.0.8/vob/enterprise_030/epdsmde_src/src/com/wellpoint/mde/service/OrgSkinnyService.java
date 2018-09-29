package com.wellpoint.mde.service;

import madison.mpi.MemRow;

public interface OrgSkinnyService {

	public String getSegmentDataforPPPRF(MemRow memRow, long entRecNum)	throws Exception;
	
	public String getSegmentDataforPNET(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforPRMB(MemRow memRow, long entRecNum)	throws Exception;
	
	public String getSegmentDataforPREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPSPT(MemRow memRow, long entRecNum) throws Exception;
     
	public String getSrcCode();
}
