package org.zkoss.timeline.demo;

public class Country {
	private String name;
	private String picUrl;
	
	public Country (String n, String p) {
		this.name = n;
		this.picUrl = p;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
}
