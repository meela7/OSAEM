package com.naemp.osaem.service.impl;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.dao.UnitDAO;
import com.naemp.osaem.model.Unit;
import com.naemp.osaem.service.UnitService;

public class UnitServiceImpl implements UnitService {

	/**
	 * Class Name:	UnitServiceImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	UnitDAO unitDao;
	
	public void setUnitDao(UnitDAO unitDao){
		this.unitDao = unitDao;
	}
	@Override
	public int newInstance(Unit unit) {
		int newunitID = 0;
		if(unitDao.create(unit))
			newunitID = unitDao.getByUniqueKey(unit.getUnitName()).getUnitID();

		return newunitID;
	}

	@Override
	public Unit readInstance(int unitID) {
		Unit unit = unitDao.read(unitID);
		return unit;
	}

	@Override
	public boolean updateInstance(Unit unit) {
		return unitDao.update(unit);
	}

	@Override
	public boolean deleteInstance(int unitID) {
		return unitDao.delete(unitID);
	}

	@Override
	public List<Unit> readCollection() {
		return unitDao.list();
	}

	@Override
	public boolean isInstanceExist(String name) {
		if(unitDao.getByUniqueKey(name) != null)
			return true;
		return false;
	}

	@Override
	public List<Unit> search(Map<String, String> map) {
		return unitDao.search(map);
	}
	@Override
	public List<Unit> listSearch(Map<String, List<String>> map) {
		return unitDao.listSearch(map);
	}

}
