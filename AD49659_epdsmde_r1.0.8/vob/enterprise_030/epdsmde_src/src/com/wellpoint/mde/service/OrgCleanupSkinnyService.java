package com.wellpoint.mde.service;

import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;

public interface OrgCleanupSkinnyService {

	public Set<String> buildPREMSegment(List<MemAttrRow> orgPREMMemAttrList, long entRecNum) throws Exception;
	
	public Set<String> buildPALTSegment(List<MemAttrRow> orgPALTMemAttrList, long entRecNum) throws Exception;
	
}
