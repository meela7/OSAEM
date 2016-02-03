package com.naemp.osaem.model;

public class River {

	/**
	 * Class Name:	River.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.01.29
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private int RiverID;
	private String RiverName;
	private String Basin;		//대권역
	private String WaterSystem;		//수계
	private String MidWatershed;	//중권역
	private String SubWatershed;	//소권역
	private String Classification;	//하천 구분
	private String ImageLink;			//하천 이미지 주소
	private String Description;

	public int getRiverID() {
		return RiverID;
	}

	public void setRiverID(int riverID) {
		RiverID = riverID;
	}

	public String getRiverName() {
		return RiverName;
	}

	public void setRiverName(String riverName) {
		RiverName = riverName;
	}

	public String getBasin() {
		return Basin;
	}

	public void setBasin(String basin) {
		Basin = basin;
	}

	public String getWaterSystem() {
		return WaterSystem;
	}

	public void setWaterSystem(String waterSystem) {
		WaterSystem = waterSystem;
	}

	public String getMidWatershed() {
		return MidWatershed;
	}

	public void setMidWatershed(String midWatershed) {
		MidWatershed = midWatershed;
	}

	public String getSubWatershed() {
		return SubWatershed;
	}

	public void setSubWatershed(String subWatershed) {
		SubWatershed = subWatershed;
	}

	public String getClassification() {
		return Classification;
	}

	public void setClassification(String classification) {
		Classification = classification;
	}

	public String getImageLink() {
		return ImageLink;
	}

	public void setImageLink(String imageLink) {
		ImageLink = imageLink;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
	@Override
	public String toString() {
		return "River [RiverID=" + RiverID + ", RiverName=" + RiverName + ", Basin=" + Basin + ", WaterSystem="
				+ WaterSystem + ", MidWatershed=" + MidWatershed + ", SubWatershed=" + SubWatershed
				+ ", Classification=" + Classification + ", ImageLink=" + ImageLink + ", Description=" + Description + "]";
	}
	
}
