package org.galilee.dms.dao;

import java.util.List;

import org.galilee.dms.model.Methods;

public interface MethodDAO {
	
	public boolean insert(Methods method);
	public boolean update(Methods method);
	public boolean delete(Methods method);
	public Methods selectByID(int methodID);
	public List<Methods> selectAll();
	public List<Methods> selectByName(String methodName);
	
}
