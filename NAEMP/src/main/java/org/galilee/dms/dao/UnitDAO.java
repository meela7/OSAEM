package org.galilee.dms.dao;

import java.util.List;

import org.galilee.dms.model.Units;

public interface UnitDAO {
	
	public Units insert(Units unit);	
	public Units update(Units unit);
	public void delete(Units unit);
	public Units selectByID(int unitID);
	public List<Units> selectAll();
	public Object selectByName(String unitName);
}
