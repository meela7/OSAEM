package com.naemp.osaem.dao;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.River;

public interface RiverDAO {
	
	/**
	 * Class Name:	RiverDAO.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.01.29
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public boolean create(River river);
	public River read(int riverID);
	public boolean update(River river);
	public boolean delete(int riverID);
	
	public List<River> list();
	
	/*
	 * used to create new instance.
	 * check with unique key sets in the database.
	 */
	public River getByUniqueKey(String name, String mid, String sub); 
	public List<River> search(Map<String, String> map);
//	public String[] getColumnNames();
}
