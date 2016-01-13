package org.galilee.dms.service.impl;

import java.util.List;

import org.galilee.dms.dao.UnitDAO;
import org.galilee.dms.model.Units;
import org.galilee.dms.service.UnitService;

public class UnitServiceImpl implements UnitService {
	
	UnitDAO unitDao;
	public void setUnitDao(UnitDAO unitDao){
		this.unitDao = unitDao;
	}
	@Override
	public void add(Units unit) {
		
		this.unitDao.insert(unit);
	}

	@Override
	public void update(Units unit) {
		
		this.unitDao.update(unit);
	}

	@Override
	public void delete(int unitID) {
//		Units unit = this.unitDao.selectByID(unitID);
		this.unitDao.delete(findByID(unitID));
	}

	@Override
	public Units findByID(int unitID) {
		return this.unitDao.selectByID(unitID);
	}

	@Override
	public List<Units> findAll() {
		return this.unitDao.selectAll();
	}
	@Override
	public Object findByName(String unitName) {
		
		return this.unitDao.selectByName(unitName);
	}

}
