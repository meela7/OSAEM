package org.galilee.dms.service;

import java.util.List;

import org.galilee.dms.model.VariableNameCV;
import org.galilee.dms.model.Variables;

public interface VariableService {	
	
	public void add(Variables variable);
	public void update(Variables variable);	
	public void delete(int variableID);
	public Variables findByID(int variableID);
	public List<Variables> findAll();
	public List<Variables> findByType(String type);
	public List<Variables>  findByName(VariableNameCV variableName);	
}
