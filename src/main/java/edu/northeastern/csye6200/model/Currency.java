package edu.northeastern.csye6200.model;

import java.io.Serializable;

public class Currency implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String countryCode;
	private String name;
	private String flagIconPath;
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlagIconPath() {
		return flagIconPath;
	}
	public void setFlagIconPath(String flagIconPath) {
		this.flagIconPath = flagIconPath;
	}
	
	@Override
	public String toString() {
		return "Currency [countryCode=" + countryCode + ", name=" + name + ", flagIconPath=" + flagIconPath + "]";
	}
}
