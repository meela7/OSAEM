package org.galilee.dms.service.impl;

import java.util.List;

import org.galilee.dms.dao.MethodDAO;
import org.galilee.dms.model.Methods;
import org.galilee.dms.service.MethodService;

public class MethodServiceImpl implements MethodService {
	
	MethodDAO methodDao;
	
	public void setMethodDao(MethodDAO methodDao){
		this.methodDao = methodDao;
	}

	@Override
	public boolean add(Methods method) {
		
		return this.methodDao.insert(method);
	}

	@Override
	public boolean update(Methods method) {
	
		return this.methodDao.update(method);
	}

	@Override
	public boolean delete(int methodID) {
		Methods method = this.methodDao.selectByID(methodID);
		return this.methodDao.delete(method);
	}

	@Override
	public Methods findByID(int methodID) {
		
		return this.methodDao.selectByID(methodID);
	}

	@Override
	public List<Methods> findAll() {
		
		return this.methodDao.selectAll();
	}

	@Override
	public List<Methods> findByName(String methodName) {
		
		return this.methodDao.selectByName(methodName);
	}

}
