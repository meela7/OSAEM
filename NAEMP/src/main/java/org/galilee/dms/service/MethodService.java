package org.galilee.dms.service;

import java.util.List;

import org.galilee.dms.model.Methods;

public interface MethodService {	
	
	public boolean add(Methods method);
	public boolean update(Methods method);	
	public boolean delete(int methodID);
	public Methods findByID(int methodID);
	public List<Methods> findAll();
	public List<Methods> findByName(String methodName);
	
}
