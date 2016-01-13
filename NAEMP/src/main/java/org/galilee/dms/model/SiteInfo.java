package org.galilee.dms.model;

public class SiteInfo {
	private int SiteID;
	private String SiteName;
	// Does it need to add RiverID
	private String RiverName;
	private String Basin;
	private String WaterSystem;
	private String MidWatershed;
	private String SubWatershed;
	private String Classification;
	private String RiverImage;
	private String Latitude;
	private String Longitude;
	private int StreamOrder;
	private String StreamGrade;
	private String Address;
	private String StandardStructure;
	private String WQMN;
	private String Image;
	private String Description;

	public SiteInfo(){
		
	}

	public int getSiteID() {
		return SiteID;
	}

	public void setSiteID(int siteID) {
		SiteID = siteID;
	}

	public String getSiteName() {
		return SiteName;
	}

	public void setSiteName(String siteName) {
		SiteName = siteName;
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

	public String getRiverImage() {
		return RiverImage;
	}

	public void setRiverImage(String riverImage) {
		RiverImage = riverImage;
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

	public int getStreamOrder() {
		return StreamOrder;
	}

	public void setStreamOrder(int streamOrder) {
		StreamOrder = streamOrder;
	}

	public String getStreamGrade() {
		return StreamGrade;
	}

	public void setStreamGrade(String streamGrade) {
		StreamGrade = streamGrade;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getStandardStructure() {
		return StandardStructure;
	}

	public void setStandardStructure(String standardStructure) {
		StandardStructure = standardStructure;
	}

	public String getWQMN() {
		return WQMN;
	}

	public void setWQMN(String wQMN) {
		WQMN = wQMN;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
	public void setSiteInfo(Sites site){
		this.setSiteID(site.getSiteID());
		this.setSiteName(site.getSiteName());
		
		this.setRiverName(site.getRiver().getRiverName());
		this.setBasin(site.getRiver().getBasin());
		this.setWaterSystem(site.getRiver().getWaterSystem());
		this.setMidWatershed(site.getRiver().getMidWatershed());
		this.setSubWatershed(site.getRiver().getSubWatershed());
		this.setClassification(site.getRiver().getClassification());
		this.setRiverImage(site.getRiver().getImage());
		
		this.setLatitude(site.getLatitude());
		this.setLongitude(site.getLongitude());
		this.setAddress(site.getAddress());
		this.setStreamOrder(site.getStreamOrder());
		this.setStreamGrade(site.getStreamGrade());
		this.setStandardStructure(site.getStandardStructure());
		this.setImage(site.getImage());
		this.setDescription(site.getDescription());
	}
}
