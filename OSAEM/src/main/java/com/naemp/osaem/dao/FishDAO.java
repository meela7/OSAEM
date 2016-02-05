package com.naemp.osaem.dao;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Fish;

public interface FishDAO {
	
	/**
	 * Class Name:	FishDAO.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public boolean create(Fish Fish);
	public Fish read(int fishID);
	public boolean update(Fish Fish);
	public boolean delete(int fishID);
	
	public List<Fish> list();
	public Fish getByUniqueKey(String name);
	public List<Fish> search(Map<String, String> map);

}
