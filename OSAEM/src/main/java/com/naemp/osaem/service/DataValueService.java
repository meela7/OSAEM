package com.naemp.osaem.service;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.DataValue;
import com.naemp.osaem.model.DataValueJoined;

public interface DataValueService {
	
	/**
	 * Class Name:	DataValueService.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public int newInstance(DataValue value);
	public DataValue readInstance(int valueID);
//	public boolean updateInstance(DataValue value);
//	public boolean deleteInstance(int valueID);	
//	public List<Site> readCollection();
	
	public boolean isInstanceExist(String dateTime, int siteID, int variableID, int featureID, int SourceID, int methodID);
	public List<DataValue> search(Map<String, String> map);
	public List<DataValue> listSearch(Map<String, List<String>> map);
	public List<DataValueJoined> joinedSearch(Map<String, List<String>> map);
}
