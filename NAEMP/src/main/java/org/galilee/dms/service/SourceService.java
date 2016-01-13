package org.galilee.dms.service;

import java.util.List;

import org.galilee.dms.model.Sources;

public interface SourceService {	
	
	public void add(Sources source);
	public void update(Sources source);	
	public void delete(int sourceID);
	public Sources findByID(int sourceID);
	public List<Sources> findAll();
	public List<Sources> findByContact(String contact);
	public List<Sources> findByInstitute(String institute);
	
}
