package com.naemp.osaem.model;

public class Unit {
	
	/**
	 * Class Name:	Unit.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private int UnitID;
	private String UnitName;
	private String UnitNameLong;
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
		return "Unit [UnitID=" + UnitID + ", UnitName=" + UnitName + ", UnitNameLong=" + UnitNameLong + "]";
	}
}
