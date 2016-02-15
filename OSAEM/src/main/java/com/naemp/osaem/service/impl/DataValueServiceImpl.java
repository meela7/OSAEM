package com.naemp.osaem.service.impl;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.dao.DataValueDAO;
import com.naemp.osaem.model.DataValue;
import com.naemp.osaem.model.DataValueJoined;
import com.naemp.osaem.service.DataValueService;

public class DataValueServiceImpl implements DataValueService {

	/**
	 * Class Name:	DataValueServiceImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	DataValueDAO dataValueDao;
	
	public void setDataValueDao(DataValueDAO dataValueDao){
		this.dataValueDao = dataValueDao;
	}
	@Override
	public int newInstance(DataValue value) {
		int newSiteID = 0;
		if(dataValueDao.create(value))
			newSiteID = dataValueDao.getByUniqueKey(value.getDateTime(), value.getSiteID(), value.getVariableID(), value.getFeatureID(), value.getSourceID(), value.getMethodID()).getValueID();

		return newSiteID;
	}

	@Override
	public DataValue readInstance(int valueID) {
		DataValue value = dataValueDao.read(valueID);
		return value;
	}

	@Override
	public boolean isInstanceExist(String dateTime, int siteID, int variableID, int featureID, int SourceID,
			int methodID) {
		if(dataValueDao.getByUniqueKey(dateTime, siteID, variableID, featureID, SourceID, methodID)!= null)
			return true;
		return false;
	}

	@Override
	public List<DataValue> search(Map<String, String> map) {
		return dataValueDao.search(map);
	}
	
	@Override
	public List<DataValue> listSearch(Map<String, List<String>> map) {
		return dataValueDao.listSearch(map);
	}
	@Override
	public List<DataValueJoined> joinedSearch(Map<String, List<String>> map) {
		
		return dataValueDao.joinedSearch(map);
	}

}
