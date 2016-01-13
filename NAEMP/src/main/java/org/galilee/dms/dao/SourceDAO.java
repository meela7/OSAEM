package org.galilee.dms.dao;

import java.util.List;

import org.galilee.dms.model.Sources;

public interface SourceDAO {
	
	public Sources insert(Sources source);
	public List<Sources> selectAll();
	public Sources update(Sources source);
	public void delete(Sources source);
	public Sources selectByID(int sourceID);
	public List<Sources> selectByContact(String contact);
	public List<Sources> selectByInstitute(String institute);
}
