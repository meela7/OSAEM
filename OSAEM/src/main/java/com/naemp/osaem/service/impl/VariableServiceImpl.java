package com.naemp.osaem.service.impl;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.dao.VariableDAO;
import com.naemp.osaem.model.Variable;
import com.naemp.osaem.service.VariableService;

public class VariableServiceImpl implements VariableService {

	/**
	 * Class Name:	VariableServiceImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.01
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	VariableDAO variableDao;
	
	public void setVariableDao(VariableDAO variableDao){
		this.variableDao = variableDao;
	}
	@Override
	public int newInstance(Variable variable) {
		int newVariableID = 0;
		if(variableDao.create(variable))
			newVariableID = variableDao.getByUniqueKey(variable.getVariableName(), variable.getUnit().getUnitID()).getVariableID();

		return newVariableID;
	}

	@Override
	public Variable readInstance(int variableID) {
		Variable variable = variableDao.read(variableID);
		return variable;
	}

	@Override
	public boolean updateInstance(Variable variable) {
		return variableDao.update(variable);
	}

	@Override
	public boolean deleteInstance(int variableID) {
		return variableDao.delete(variableID);
	}

	@Override
	public List<Variable> readCollection() {
		return variableDao.list();
	}

	@Override
	public boolean isInstanceExist(String name, int unitID) {
		if(variableDao.getByUniqueKey(name, unitID) != null)
			return true;
		return false;
	}

	@Override
	public List<Variable> search(Map<String, String> map) {
		return variableDao.search(map);
	}

}
