package org.galilee.dms.model;

import java.io.Serializable;

public class Features implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int FeatureID;
	private String FeatureType;
	private String FeatureName;
	
	public Features(){}

	public int getFeatureID() {
		return FeatureID;
	}

	public void setFeatureID(int featureID) {
		FeatureID = featureID;
	}

	public String getFeatureName() {
		return FeatureName;
	}

	public void setFeatureName(String featureName) {
		FeatureName = featureName;
	}

	public String getFeatureType() {
		return FeatureType;
	}

	public void setFeatureType(String featureType) {
		FeatureType = featureType;
	}
}
