package com.wellpoint.mde.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * EntityProperties Contain methods which return the properties of different 
 * entities in MDE. BusinessHelper class of different entities can use these
 * methods to attain the respective properties.
 *
 */
public final class EntityProperties {
	
	public static Map<String, String> getOrgProperties() {
		Map<String, String> entityProp= new HashMap<String, String>();
		entityProp.put("TYPE", "Org");
		entityProp.put("QCARE", "QCAREORG");
		entityProp.put("WMS", "WMSORG");
		entityProp.put("EPDS1", "EPDS1ORG");
		entityProp.put("EPDSV2", "EPDSV2ORG");
		entityProp.put("WMS", "WMSORG");
		entityProp.put("EPDS2", "EPDS2ORG");
		entityProp.put("ADDRTYPE", "179");
		return entityProp;
	}
	
	public static Map<String, String> getProvProperties() {
		Map<String, String> entityProp= new HashMap<String, String>();
		entityProp.put("TYPE", "Prov");
		entityProp.put("QCARE", "QCARE");
		entityProp.put("WMS", "WMS");
		entityProp.put("EPDS1", "EPDS1");
		entityProp.put("EPDSV2", "EPDSV2");
		entityProp.put("WMS", "WMS");
		entityProp.put("EPDS2", "EPDS2");
		entityProp.put("ADDRTYPE", "178");
		return entityProp;
	}
	
	public static Map<String, String> getGrpProperties() {
		Map<String, String> entityProp= new HashMap<String, String>();
		entityProp.put("TYPE", "Grp");
		entityProp.put("QCARE", "QCAREGRP");
		entityProp.put("WMS", "WMS");
		entityProp.put("EPDS1", "EPDS1");
		entityProp.put("EPDSV2", "EPDSV2");
		return entityProp;
	}
}
