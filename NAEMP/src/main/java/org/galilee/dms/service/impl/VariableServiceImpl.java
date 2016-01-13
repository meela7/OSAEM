package org.galilee.dms.service.impl;

import java.util.List;

import org.galilee.dms.dao.VariableDAO;
import org.galilee.dms.model.VariableNameCV;
import org.galilee.dms.model.Variables;
import org.galilee.dms.service.VariableService;

public class VariableServiceImpl implements VariableService {

	VariableDAO variableDao;
	
	public void setVariableDao(	VariableDAO variableDao) {
		this.variableDao = variableDao;
	}
	
	@Override
	public void add(Variables variable) {
				
		this.variableDao.insert(variable);
	}

	@Override
	public void update(Variables variable) {
		
		this.variableDao.update(variable);
	}

	@Override
	public void delete(int variableID) {
		
		Variables variable = this.variableDao.selectByID(variableID);
		this.variableDao.delete(variable);
	}

	@Override
	public Variables findByID(int variableID) {
		
		return this.variableDao.selectByID(variableID);
	}

	@Override
	public List<Variables> findAll() {
		
		return this.variableDao.selectAll();
	}

	@Override
	public List<Variables> findByType(String type) {
		
		return this.variableDao.selectByType(type);
	}

	@Override
	public List<Variables> findByName(VariableNameCV variableName) {
	
		return this.variableDao.selectByName(variableName);
	}

}
