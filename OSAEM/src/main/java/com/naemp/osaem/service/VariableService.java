package com.naemp.osaem.service;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Variable;

public interface VariableService {
	
	/**
	 * Class Name:	VariableService.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public int newInstance(Variable variable);
	public Variable readInstance(int variableID);
	public boolean updateInstance(Variable variable);
	public boolean deleteInstance(int variableID);	
	public List<Variable> readCollection();
	
	public boolean isInstanceExist(String name, int unitID);
	public List<Variable> search(Map<String, String> map);

}
