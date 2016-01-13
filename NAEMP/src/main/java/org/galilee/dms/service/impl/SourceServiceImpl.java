package org.galilee.dms.service.impl;

import java.util.List;

import org.galilee.dms.dao.SourceDAO;
import org.galilee.dms.model.Sources;
import org.galilee.dms.service.SourceService;
import org.springframework.stereotype.Service;

@Service
public class SourceServiceImpl implements SourceService {
	
	SourceDAO sourceDao;

	public void setSourceDao(SourceDAO sourceDao) {
		this.sourceDao = sourceDao;
	}

	@Override
	public void add(Sources source) {
		// TODO Auto-generated method stub	
		
		this.sourceDao.insert(source);
	}

	@Override
	public void update(Sources source) {
		
		this.sourceDao.update(source);
	}
	
	@Override
	public void delete(int sourceID) {
		
		Sources source = this.sourceDao.selectByID(sourceID);
		this.sourceDao.delete(source);
	}

	@Override
	public Sources findByID(int sourceID) {
		
		return this.sourceDao.selectByID(sourceID);
	}
	
	@Override
	public List<Sources> findAll() {
		
		return this.sourceDao.selectAll();
	}

	@Override
	public List<Sources> findByContact(String contact) {
		
		return this.sourceDao.selectByContact(contact);
	}
	
	@Override
	public List<Sources> findByInstitute(String institute) {
		
		return this.sourceDao.selectByInstitute(institute);
	}
}
