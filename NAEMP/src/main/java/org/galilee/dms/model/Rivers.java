package org.galilee.dms.model;

public class Rivers  {

	public Rivers(){}
	
	private int RiverID;
	private String RiverName;
	private String Basin;
	private String WaterSystem;
	private String MidWatershed;
	private String SubWatershed;
	private String Classification;
	private String Image;
	private String Description;

	public int getRiverID() {
		return RiverID;
	}
	public void setRiverID(int rid) {
		this.RiverID = rid;
	}
	
	public String getRiverName() {
		return RiverName;
	}
	public void setRiverName(String rname) {
		this.RiverName = rname;
	}
	
	public String getBasin() {
		return Basin;
	}
	
	public void setBasin(String basin) {
		this.Basin = basin;
	}
	
	public String getWaterSystem() {
		return WaterSystem;
	}
	public void setWaterSystem(String waterSystem) {
		this.WaterSystem = waterSystem;
	}
	
	public String getMidWatershed() {
		return MidWatershed;
	}
	public void setMidWatershed(String midWatershed) {
		this.MidWatershed = midWatershed;
	}
	
	public String getSubWatershed() {
		return SubWatershed;
	}
	public void setSubWatershed(String subWatershed) {
		this.SubWatershed = subWatershed;
	}
	
	public String getClassification() {
		return Classification;
	}
	public void setClassification(String classification) {
		this.Classification = classification;
	}
	
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		this.Image = image;
	}
	
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		this.Description = description;
	}

}
