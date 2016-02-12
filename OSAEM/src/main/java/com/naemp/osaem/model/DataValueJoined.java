package com.naemp.osaem.model;

public class DataValueJoined {

	/**
	 * Class Name: DataValue.java 
	 * Description: Hibernate Mapping pojo File
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.11
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */

	private int ValueID;	
	private double DataValue;
	private String DateTime;
	private int SurveyYear;
	private int SurveyTerm;
	private Site SiteID;	
	private String Latitude;
	private String Longitude;	
	private Source SourceID;
	private Feature FeatureID;
	private Variable VariableID;
	private Method MethodID;
	public int getValueID() {
		return ValueID;
	}
	public void setValueID(int valueID) {
		ValueID = valueID;
	}
	public double getDataValue() {
		return DataValue;
	}
	public void setDataValue(double dataValue) {
		DataValue = dataValue;
	}
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
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
	public Site getSiteID() {
		return SiteID;
	}
	public void setSiteID(Site siteID) {
		SiteID = siteID;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public Source getSourceID() {
		return SourceID;
	}
	public void setSourceID(Source sourceID) {
		SourceID = sourceID;
	}
	public Feature getFeatureID() {
		return FeatureID;
	}
	public void setFeatureID(Feature featureID) {
		FeatureID = featureID;
	}
	public Variable getVariableID() {
		return VariableID;
	}
	public void setVariableID(Variable variableID) {
		VariableID = variableID;
	}
	public Method getMethodID() {
		return MethodID;
	}
	public void setMethodID(Method methodID) {
		MethodID = methodID;
	}
	
	@Override
	public String toString() {
		return "DataValueJoined [ValueID=" + ValueID + ", DataValue=" + DataValue + ", DateTime=" + DateTime
				+ ", SurveyYear=" + SurveyYear + ", SurveyTerm=" + SurveyTerm + ", SiteID=" + SiteID + ", Latitude="
				+ Latitude + ", Longitude=" + Longitude + ", SourceID=" + SourceID + ", FeatureID=" + FeatureID
				+ ", VariableID=" + VariableID + ", MethodID=" + MethodID + "]";
	}	
}
