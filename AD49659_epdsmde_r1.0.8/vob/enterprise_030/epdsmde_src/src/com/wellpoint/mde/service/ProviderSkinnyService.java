package com.wellpoint.mde.service;

import madison.mpi.MemRow;

public interface ProviderSkinnyService {
	
	public String getSegmentDataforWREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWCON(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPPPRF(MemRow memRow, long entRecNum)	throws Exception;
	
	public String getSegmentDataforPNET(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforPRMB(MemRow memRow, long entRecNum)	throws Exception;
	
	public String getSegmentDataforPSPT(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSrcCode();
}
