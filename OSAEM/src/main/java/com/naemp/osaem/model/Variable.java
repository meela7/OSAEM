package com.naemp.osaem.model;

public class Variable {
	
	/**
	 * Class Name: Variable.java 
	 * Description: Hibernate Mapping pojo File
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private int VariableID;
	private String VariableName;
	private Unit Unit;	
	private String ValueType;
	private String Description;
	
	public int getVariableID() {
		return VariableID;
	}
	public void setVariableID(int variableID) {
		VariableID = variableID;
	}
	public String getVariableName() {
		return VariableName;
	}
	public void setVariableName(String variableName) {
		VariableName = variableName;
	}
	public Unit getUnit() {
		return Unit;
	}
	public void setUnit(Unit unit) {
		Unit = unit;
	}
	public String getValueType() {
		return ValueType;
	}
	public void setValueType(String valueType) {
		ValueType = valueType;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	@Override
	public String toString() {
		return "Variable [VariableID=" + VariableID + ", VariableName=" + VariableName + ", Unit=" + Unit
				+ ", ValueType=" + ValueType + ", Description=" + Description + "]";
	}
	
}
