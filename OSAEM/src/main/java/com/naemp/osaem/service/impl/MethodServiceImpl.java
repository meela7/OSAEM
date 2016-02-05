package com.naemp.osaem.service.impl;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.dao.MethodDAO;
import com.naemp.osaem.model.Method;
import com.naemp.osaem.service.MethodService;

public class MethodServiceImpl implements MethodService {

	/**
	 * Class Name:	MethodServiceImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	MethodDAO methodDao;
	
	public void setMethodDao(MethodDAO methodDao){
		this.methodDao = methodDao;
	}
	@Override
	public int newInstance(Method method) {
		int newmethodID = 0;
		if(methodDao.create(method))
			newmethodID = methodDao.getByUniqueKey(method.getMethodName()).getMethodID();

		return newmethodID;
	}

	@Override
	public Method readInstance(int methodID) {
		Method method = methodDao.read(methodID);
		return method;
	}

	@Override
	public boolean updateInstance(Method method) {
		return methodDao.update(method);
	}

	@Override
	public boolean deleteInstance(int methodID) {
		return methodDao.delete(methodID);
	}

	@Override
	public List<Method> readCollection() {
		return methodDao.list();
	}

	@Override
	public boolean isInstanceExist(String name) {
		if(methodDao.getByUniqueKey(name) != null)
			return true;
		return false;
	}

	@Override
	public List<Method> search(Map<String, String> map) {
		return methodDao.search(map);
	}

}
