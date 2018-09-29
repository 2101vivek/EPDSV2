package com.wellpoint.mde.service;

import madison.mpi.MemRow;

public interface HmoSkinnyService {

	public String getSegmentDataforWREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWCON(MemRow memRow, long entRecNum) throws Exception;
	
	//public String getSegmentDataforWNET(MemRow memRow, long entRecNum) throws Exception;
	
}
