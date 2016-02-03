package com.naemp.osaem.service;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.River;

public interface RiverService {

	/**
	 * Class Name:	RiverService.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.01.30
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public int newInstance(River river);
	public River readInstance(int riverID);
	public boolean updateInstance(River river);
	public boolean deleteInstance(int riverID);	
	public List<River> readCollection();
	
	public boolean isInstanceExist(String name, String mid, String sub);
	public List<River> search(Map<String, String> map);
}
