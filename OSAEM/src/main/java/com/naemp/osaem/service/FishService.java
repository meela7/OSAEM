package com.naemp.osaem.service;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Fish;

public interface FishService {
	
	/**
	 * Class Name:	FishService.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public int newInstance(Fish fish);
	public Fish readInstance(int fishID);
	public boolean updateInstance(Fish fish);
	public boolean deleteInstance(int fishID);	
	public List<Fish> readCollection();
	
	public boolean isInstanceExist(String name);
	public List<Fish> search(Map<String, String> map);
	public List<Fish> listSearch(Map<String, List<String>> map);

}
