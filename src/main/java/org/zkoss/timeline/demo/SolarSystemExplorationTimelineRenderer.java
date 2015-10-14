package org.zkoss.timeline.demo;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.timeline.api.TimelineRenderer;

public class SolarSystemExplorationTimelineRenderer implements TimelineRenderer<SolarSystemExplorationEvent>{
	public long getStartDate(SolarSystemExplorationEvent e) {
		Long result = null;
		if (e.getStartDate() != null) {
			result = e.getStartDate().getTime();
		}
		return result;
	}

	public long getEndDate(SolarSystemExplorationEvent e) {
		Long result = null;
		if (e.getStartDate() != null) {
			result = e.getStartDate().getTime();
		}
		return result;
	}

	public String getContent(SolarSystemExplorationEvent e) {
		StringBuilder content = new StringBuilder();
		List<String> countryNames = new ArrayList<String>();
		List<String> countryPicUrls = new ArrayList<String>();
		for (Country c : e.getCountries()) {
			countryNames.add(c.getName());
			countryPicUrls.add(c.getPicUrl());
			content.append("<img src='" + c.getPicUrl() + "' title='" + c.getName() + "' />");
		}
		String topic = e.getTopic();
		if (topic.length() > 0) {
			content.append("<div>" + topic + "</div>");
		}
		return content.toString();
	}
}
