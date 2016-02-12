package com.naemp.osaem.dao;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.DataValue;

public interface DataValueDAO {
	
	/**
	 * Class Name:	DataValueDAO.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	
	public boolean create(DataValue dataValue);
	public DataValue read(int dataValueID);
//	public boolean update(DataValue dataValue);
//	public boolean delete(int dataValueID);
	
//	public List<DataValues> list();
	public DataValue getByUniqueKey(String dateTime, int siteID, int variableID, int featureID, int SourceID, int methodID);
	public List<DataValue> search(Map<String, String> map);
	public List<DataValue> listSearch(Map<String, List<String>> map);
}
