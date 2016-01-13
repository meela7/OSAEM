package org.galilee.dms.service;

import java.util.List;

import org.galilee.dms.model.Units;

public interface UnitService {	
	
	public void add(Units unit);
	public void update(Units unit);	
	public void delete(int unitID);
	public Units findByID(int unitID);
	public List<Units> findAll();
	public Object findByName(String unitName);
	
}
