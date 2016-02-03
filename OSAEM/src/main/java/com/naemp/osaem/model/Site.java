package com.naemp.osaem.model;

public class Site {
	
	/**
	 * Class Name:	Site.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.01
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private int SiteID;
	private String SiteName;
	private River River;
	private String Latitude;
	private String Longitude;
	private int StreamOrder;
	private String StreamGrade;
	private String Address;
	private String StandardStructure;
	private String WQMN;
	private String ImageLink;
	private String Description;
	
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
	public River getRiver() {
		return River;
	}
	public void setRiver(River river) {
		River = river;
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
		return "Site [SiteName=" + SiteName + ", River=" + River + ", Latitude=" + Latitude + ", Longitude=" + Longitude
				+ ", StreamOrder=" + StreamOrder + ", StreamGrade=" + StreamGrade + ", Address=" + Address
				+ ", StandardStructure=" + StandardStructure + ", WQMN=" + WQMN + ", ImageLink=" + ImageLink + ", Description="
				+ Description + "]";
	}
	
}
