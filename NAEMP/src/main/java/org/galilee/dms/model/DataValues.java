package org.galilee.dms.model;

public class DataValues {

	private int ValueID;	
	private double DataValue;
	private String DateTime;
	private int Duration;
	private int SurveyYear;
	private int SurveyTerm;
	private Sites Site;	
	private String Latitude;
	private String Longitude;	
	private Sources Source;
	private Features Feature;
	private Variables Variable;
	private Methods Method;

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

	public Sites getSite() {
		return Site;
	}

	public void setSite(Sites site) {
		Site = site;
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

	public Sources getSource() {
		return Source;
	}

	public void setSource(Sources source) {
		Source = source;
	}

	public Variables getVariable() {
		return Variable;
	}

	public void setVariable(Variables variable) {
		Variable = variable;
	}

	public Features getFeature() {
		return Feature;
	}

	public void setFeature(Features feature) {
		Feature = feature;
	}

	public int getDuration() {
		return Duration;
	}

	public void setDuration(int duration) {
		Duration = duration;
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

	public Methods getMethod() {
		return Method;
	}

	public void setMethod(Methods method) {
		Method = method;
	}

}
