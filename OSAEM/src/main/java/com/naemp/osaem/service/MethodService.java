package com.naemp.osaem.service;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Method;

public interface MethodService {
	
	/**
	 * Class Name:	MethodService.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public int newInstance(Method method);
	public Method readInstance(int methodID);
	public boolean updateInstance(Method method);
	public boolean deleteInstance(int methodID);	
	public List<Method> readCollection();
	
	public boolean isInstanceExist(String name);
	public List<Method> search(Map<String, String> map);

}
