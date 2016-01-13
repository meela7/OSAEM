package org.galilee.dms.model;


public class Units {

	private int UnitID ;	
	private String UnitName;
	private String UnitNameLong;
	
	public Units() {}
	
	public Units(int unitID){
		super();
		this.UnitID = unitID;
	}
	
	public int getUnitID() {
		return UnitID;
	}
	public void setUnitID(int unitID) {
		UnitID = unitID;
	}
	public String getUnitName() {
		return UnitName;
	}
	public void setUnitName(String unitName) {
		UnitName = unitName;
	}

	public String getUnitNameLong() {
		return UnitNameLong;
	}

	public void setUnitNameLong(String unitNameLong) {
		UnitNameLong = unitNameLong;
	}

	@Override
	public String toString() {
		return "Units [UnitID=" + UnitID + ", UnitName=" + UnitName
				+ ", UnitNameLong=" + UnitNameLong + "]";
	}
	
}

