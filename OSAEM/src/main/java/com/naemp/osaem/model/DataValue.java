package com.naemp.osaem.model;

public class DataValue {
	
	/**
	 * Class Name: DataValue.java 
	 * Description: Hibernate Mapping pojo File
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */

	private int ValueID;	
	private double DataValue;
	private String DateTime;
	private int SurveyYear;
	private int SurveyTerm;
	private int SiteID;	
	private String Latitude;
	private String Longitude;	
	private int SourceID;
	private int FeatureID;
	private int VariableID;
	private int MethodID;
	
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
	public int getSiteID() {
		return SiteID;
	}
	public void setSiteID(int siteID) {
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
	public int getSourceID() {
		return SourceID;
	}
	public void setSourceID(int sourceID) {
		SourceID = sourceID;
	}
	public int getFeatureID() {
		return FeatureID;
	}
	public void setFeatureID(int featureID) {
		FeatureID = featureID;
	}
	public int getVariableID() {
		return VariableID;
	}
	public void setVariableID(int variableID) {
		VariableID = variableID;
	}
	public int getMethodID() {
		return MethodID;
	}
	public void setMethodID(int methodID) {
		MethodID = methodID;
	}
	
	@Override
	public String toString() {
		return "DataValue [ValueID=" + ValueID + ", DataValue=" + DataValue + ", DateTime=" + DateTime
				+ ", SurveyYear=" + SurveyYear + ", SurveyTerm=" + SurveyTerm + ", SiteID=" + SiteID
				+ ", Latitude=" + Latitude + ", Longitude=" + Longitude + ", SourceID=" + SourceID + ", FeatureID="
				+ FeatureID + ", VariableID=" + VariableID + ", MethodID=" + MethodID + "]";
	}
	
}
