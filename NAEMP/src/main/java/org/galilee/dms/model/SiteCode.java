package org.galilee.dms.model;

public class SiteCode {
	private int SiteCodeID;
	private String SiteCode;
	private Sites Site;
	private int SurveyYear;
	private int SurveyTerm;
	
	public SiteCode(){
		
	}

	public int getSiteCodeID() {
		return SiteCodeID;
	}

	public void setSiteCodeID(int siteCodeID) {
		SiteCodeID = siteCodeID;
	}

	public String getSiteCode() {
		return SiteCode;
	}

	public void setSiteCode(String siteCode) {
		SiteCode = siteCode;
	}

	public Sites getSite() {
		return Site;
	}

	public void setSite(Sites site) {
		Site = site;
	}

	public int getSurveyYear() {
		return SurveyYear;
	}

	public void setSurveyYear(int surveyYear) {
		SurveyYear = surveyYear;
	}

	public int getSurveyTerm() {
		return SurveyTerm;
	}

	public void setSurveyTerm(int surveyTerm) {
		SurveyTerm = surveyTerm;
	}

	@Override
	public String toString() {
		return "SiteCode [SiteCodeID=" + SiteCodeID + ", SiteCode=" + SiteCode
				+ ", Site=" + Site + ", SurveyYear=" + SurveyYear
				+ ", SurveyTerm=" + SurveyTerm + "]";
	}
}
