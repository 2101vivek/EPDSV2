package com.wellpoint.mde.constants;

public enum Constants {
	
	NET("NET");
	
	private String value;
	
	private Constants(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
