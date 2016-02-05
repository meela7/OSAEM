package com.naemp.osaem.dao;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Method;

public interface MethodDAO {
	
	/**
	 * Class Name:	MethodDAO.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public boolean create(Method method);
	public Method read(int methodID);
	public boolean update(Method method);
	public boolean delete(int methodID);
	
	public List<Method> list();
	public Method getByUniqueKey(String name);
	public List<Method> search(Map<String, String> map);

}
