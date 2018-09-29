package com.wellpoint.mde.service;

import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;

public interface ProviderPgmService {

	public String buildMPPRGSegment(List<MemAttrRow> ppgmMPPRGMemAttrList, long entRecNum, String l_strSrcCd_common) throws Exception;
	
	public Set<String> buildALTSRCIDSegment(List<MemAttrRow> ppgmALTSRCIDMemAttrList, long entRecNum) throws Exception;
}
