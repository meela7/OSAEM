package com.naemp.osaem.model;

public class Source {
	
	/**
	 * Class Name:	Source.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */

	private int SourceID;
	private String Institution;
	private String Investigator;
	private String Phone;
	private String Email;
	private String Description;
	
	public int getSourceID() {
		return SourceID;
	}
	public void setSourceID(int sourceID) {
		SourceID = sourceID;
	}
	public String getInstitution() {
		return Institution;
	}
	public void setInstitution(String institution) {
		Institution = institution;
	}
	public String getInvestigator() {
		return Investigator;
	}
	public void setInvestigator(String investigator) {
		Investigator = investigator;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	@Override
	public String toString() {
		return "Source [SourceID=" + SourceID + ", Institution=" + Institution + ", Investigator=" + Investigator
				+ ", Phone=" + Phone + ", Email=" + Email + ", Description=" + Description + "]";
	}
	
}
