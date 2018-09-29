package com.wellpoint.mde.service;

import java.util.List;

import madison.mpi.MemAttrRow;

public interface NetworkSkinnyService {
	public String buildMNETSegment(List<MemAttrRow> netMNETMemAttrList,
			long entRecNum, String l_memIdNum_common, String l_strSrcCd_common) throws Exception;
}
