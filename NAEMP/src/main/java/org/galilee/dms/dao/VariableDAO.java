package org.galilee.dms.dao;

import java.util.List;

import org.galilee.dms.model.VariableNameCV;
import org.galilee.dms.model.Variables;

public interface VariableDAO {
	
	public Variables insert(Variables variable);	
	public Variables update(Variables variable);
	public void delete(Variables variable);
	public Variables selectByID(int variableID);
	public List<Variables> selectAll();
	public List<Variables> selectByType(String type);
	public List<Variables> selectByName(VariableNameCV variableName);
}
