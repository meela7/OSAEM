package com.naemp.osaem.model;

public class Feature {

	/**
	 * Class Name:	Feature.java
	 * Description: Hibernate Mapping pojo File
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private int FeatureID;
	private String FeatureType;
	private String FeatureName;
	
	public int getFeatureID() {
		return FeatureID;
	}
	public void setFeatureID(int featureID) {
		FeatureID = featureID;
	}
	public String getFeatureType() {
		return FeatureType;
	}
	public void setFeatureType(String featureType) {
		FeatureType = featureType;
	}
	public String getFeatureName() {
		return FeatureName;
	}
	public void setFeatureName(String featureName) {
		FeatureName = featureName;
	}
	
}
