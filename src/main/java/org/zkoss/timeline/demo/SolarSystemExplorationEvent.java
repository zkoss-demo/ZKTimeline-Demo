package org.zkoss.timeline.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.timeline.impl.SimpleTimelineEvent;

public class SolarSystemExplorationEvent extends SimpleTimelineEvent{
	private Date startDate;
	private Date endDate;
	private List<Country> countries;
	private String topic;
	private String description;
	private String linkUrl;
	
	public SolarSystemExplorationEvent() {
		
		
	}
	
	public SolarSystemExplorationEvent(Date s, List<Country> c, String t, String d, String l) {
		this.startDate = s;
		this.endDate = s;
		this.topic = t;
		this.countries = new ArrayList<Country>(c);
		this.description = d;
		this.linkUrl = l;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = new ArrayList<Country>(countries);
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
}

