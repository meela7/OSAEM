package com.naemp.osaem.service.impl;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.dao.RiverDAO;
import com.naemp.osaem.model.River;
import com.naemp.osaem.service.RiverService;

public class RiverServiceImpl implements RiverService {
	
	/**
	 * Class Name:	RiverServiceImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.01.30
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	RiverDAO riverDao;
	
	public void setRiverDao(RiverDAO riverDao){
		this.riverDao = riverDao;
	}

	@Override
	public int newInstance(River river) {
		River newRiver = null;
		if(riverDao.create(river))
			newRiver = riverDao.getByUniqueKey(river.getRiverName(), river.getMidWatershed(), river.getSubWatershed());
		return newRiver.getRiverID();		
	}

	@Override
	public River readInstance(int riverID) {
		River river = riverDao.read(riverID);
		return river;
	}

	@Override
	public boolean updateInstance(River river) {
		return riverDao.update(river);
	}

	@Override
	public boolean deleteInstance(int riverID) {		
		return riverDao.delete(riverID);
	}

	@Override
	public List<River> readCollection() {
		return riverDao.list();
		
	}

	@Override
	public boolean isInstanceExist(String name, String mid, String sub) {
		if(riverDao.getByUniqueKey(name, mid, sub) != null)
			return true;
		return false;
	}

	@Override
	public List<River> search(Map<String, String> map) {	
		return riverDao.search(map);
	}

}
