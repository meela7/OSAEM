package org.galilee.dms.model;

import java.util.List;

public class CoreQuery {
	@Override
	public String toString() {
		return "CoreQuery for [ " + siteIDs.size() + " sites , " + variableIDs.size()
				+ " variables, " + featureIDs.size() + " entities , from " + start + " to "
				+ end + "]";
	}

	private List<Integer> siteIDs;
	private List<Integer> variableIDs;
	private List<Integer> featureIDs;
	private String start;
	private String end;
	
	public CoreQuery(){}

	public List<Integer> getSiteIDs() {
		return siteIDs;
	}

	public void setSiteIDs(List<Integer> siteIDs) {
		this.siteIDs = siteIDs;
	}

	public List<Integer> getVariableIDs() {
		return variableIDs;
	}

	public void setVariableIDs(List<Integer> variableIDs) {
		this.variableIDs = variableIDs;
	}

	public List<Integer> getFeatureIDs() {
		return featureIDs;
	}

	public void setFeatureIDs(List<Integer> featureIDs) {
		this.featureIDs = featureIDs;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}	
}
