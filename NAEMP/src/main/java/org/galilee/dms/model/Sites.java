package org.galilee.dms.model;

public class Sites {
	
	private int SiteID;
	private String SiteName;
	private Rivers River;
	private String Latitude;
	private String Longitude;
	private int StreamOrder;
	private String StreamGrade;
	private String Address;
	private String StandardStructure;
	private String WQMN;
	private String Image;
	private String Description;

	public Sites() {

	}

	public int getSiteID() {
		return SiteID;
	}

	public void setSiteID(int siteID) {
		this.SiteID = siteID;
	}

	public String getSiteName() {
		return SiteName;
	}

	public void setSiteName(String siteName) {
		this.SiteName = siteName;
	}

	public Rivers getRiver() {
		return this.River;
	}

	public void setRiver(Rivers river) {
		this.River = river;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitute) {
		this.Latitude = latitute;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		this.Longitude = longitude;
	}

	public int getStreamOrder() {
		return StreamOrder;
	}

	public void setStreamOrder(int streamOrder) {
		this.StreamOrder = streamOrder;
	}

	public String getStreamGrade() {
		return StreamGrade;
	}

	public void setStreamGrade(String streamGrade) {
		this.StreamGrade = streamGrade;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String add) {
		this.Address = add;
	}

	public String getStandardStructure() {
		return StandardStructure;
	}

	public void setStandardStructure(String structure) {
		this.StandardStructure = structure;
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
		this.Image = image;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

}
