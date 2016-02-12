package com.naemp.osaem.service;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Unit;

public interface UnitService {
	
	/**
	 * Class Name:	UnitService.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public int newInstance(Unit unit);
	public Unit readInstance(int unitID);
	public boolean updateInstance(Unit unit);
	public boolean deleteInstance(int unitID);	
	public List<Unit> readCollection();
	
	public boolean isInstanceExist(String name);
	public List<Unit> search(Map<String, String> map);
	public List<Unit> listSearch(Map<String, List<String>> map);

}
