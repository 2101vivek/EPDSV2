package com.wellpoint.mde.service;

import java.util.List;
import java.util.Set;

import madison.mpi.MemAttrRow;
import madison.mpi.MemRow;

public interface ProviderService {
	
	public String getSegmentDataforPATTS(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPPPRF(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPOT(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPCRED(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPRNK(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPDSTC(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPCLMFRM(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPNOTE(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPSANC(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPSNAC(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPSTFLANG(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPPGM(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPAOF(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPOFSR(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPDBA(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPCNTC(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPOFSCH(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPOFTCH(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPOFACS(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPOFSRR(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPALTROL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPRGN(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPTAX(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPBREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPALIAS(MemRow memRow, long entRecNum)throws Exception;
	
	public String getSegmentDataforPTTL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPEDU(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPOCON(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPLANG(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPGREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforGRMB(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforGNET(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWCON(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforWNET(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPCLMACT(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforPTXN(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforPRMB(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforPNET(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforPWTH(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforAPREL(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforAPTXN(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforAPCLMACT(MemRow memRow, long entRecNum) throws Exception;

	public String getSegmentDataforAPGREL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforAPTTL(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforAPADR(MemRow memRow,long entRecNum) throws Exception;
	
	public String getSegmentDataforAPCNTC(MemRow memRow,long entRecNum) throws Exception;
   
    public String getSegmentDataforAPDM(MemRow memRow,long entRecNum) throws Exception;

	public String getSegmentDataforPRF(long entRecNum, boolean EPDSV2_Flag);
	
	public String getSegmentDataforPREM(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforNASCOPCNTC(MemRow memRow, long entRecNum) throws Exception;
	
	public String getSegmentDataforQCAREAPADR(MemRow memRow, long entRecNum) throws Exception;
	
	public String buildPRFSegment(List<MemAttrRow> OrgPRFMemAttrList, long entRecNum , boolean EPDSV2_Flag) throws Exception;

	public Set<String> 	buildPSPTSegment(List<MemAttrRow> orgPSPTMemAttrList, long entRecNum) throws Exception;
	
	public Set<String> 	buildPADRSegment(List<MemAttrRow> orgPADRMemAttrList, long entRecNum) throws Exception;

	public Set<String> 	buildPALTSegment(List<MemAttrRow> orgPALTMemAttrList, long entRecNum)throws Exception;

	public Set<String> 	buildAPREMSegment(List<MemAttrRow> orgAPREMMemAttrList, long entRecNum) throws Exception;

	public Set<String> 	buildAPSPTSegment(List<MemAttrRow> orgAPSPTMemAttrList, long entRecNum) throws Exception;

	public Set<String> 	buildALTSRCIDSegment(List<MemAttrRow> orgALTSRCIDMemAttrList, long entRecNum) throws Exception;

	public Set<String> 	buildAPADRSegment(List<MemAttrRow> orgAPADRMemAttrList, List<String>EMEMADDR_Keys, long entRecNum) throws Exception;

	public Set<String> 	buildAPALTSegment(List<MemAttrRow> orgAPALTMemAttrList, long entRecNum, boolean APALTFlag) throws Exception;

	public Set<String> 	buildALTSRCIDSegmentForV2(List<MemAttrRow> orgPRFMemAttrList, String epdsv2memrecno, long entRecNum) throws Exception ;
	
}
