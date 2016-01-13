package org.galilee.dms.model;

import java.io.Serializable;

public class FishCharacterCV implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Term;
	private String Definition;
	
	public FishCharacterCV(){
		
	}

	public String getTerm() {
		return Term;
	}

	public void setTerm(String term) {
		Term = term;
	}

	public String getDefinition() {
		return Definition;
	}

	public void setDefinition(String definition) {
		Definition = definition;
	}
	
	
}
