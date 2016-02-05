package com.naemp.osaem.dao;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Variable;

public interface VariableDAO {
	
	/**
	 * Class Name:	VariableDAO.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public boolean create(Variable variable);
	public Variable read(int variableID);
	public boolean update(Variable variable);
	public boolean delete(int variableID);
	
	public List<Variable> list();
	public Variable getByUniqueKey(String name, int unitID);
	public List<Variable> search(Map<String, String> map);

}
