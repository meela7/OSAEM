package org.galilee.dms.model;

import java.io.Serializable;

public class Sources implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int SourceID;
	private String Institution;
	private String Investigator;
	private String Phone;
	private String Email;
	private String Description;

	public Sources() {
	}

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

}
