package org.galilee.dms.model;

public class Variables {

	private int VariableID;
	private VariableNameCV VariableName;
	private Units Unit;	
	private ValueTypeCV ValueType;
	private String Description;

	public Variables() {}
	
	public int getVariableID() {
		return VariableID;
	}

	public void setVariableID(int variableID) {
		VariableID = variableID;
	}

	public Units getUnit() {
		return Unit;
	}

	public void setUnit(Units unit) {
		Unit = unit;
	}

	public VariableNameCV getVariableName() {
		return VariableName;
	}

	public void setVariableName(VariableNameCV variableName) {
		VariableName = variableName;
	}

	public ValueTypeCV getValueType() {
		return ValueType;
	}

	public void setValueType(ValueTypeCV valueType) {
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
		return "Variables [VariableID=" + VariableID + ", VariableName="
				+ VariableName + ", Unit=" + Unit + ", ValueType=" + ValueType
				+ ", Description=" + Description + "]";
	}
}
