package com.naemp.osaem.service.impl;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.dao.SourceDAO;
import com.naemp.osaem.model.Source;
import com.naemp.osaem.service.SourceService;

public class SourceServiceImpl implements SourceService {

	/**
	 * Class Name:	SourceServiceImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	SourceDAO sourceDao;
	
	public void setSourceDao(SourceDAO sourceDao){
		this.sourceDao = sourceDao;
	}
	@Override
	public int newInstance(Source source) {
		int newsourceID = 0;
		if(sourceDao.create(source))
			newsourceID = sourceDao.getByUniqueKey(source.getInstitution(), source.getInvestigator()).getSourceID();

		return newsourceID;
	}

	@Override
	public Source readInstance(int sourceID) {
		Source source = sourceDao.read(sourceID);
		return source;
	}

	@Override
	public boolean updateInstance(Source source) {
		return sourceDao.update(source);
	}

	@Override
	public boolean deleteInstance(int sourceID) {
		return sourceDao.delete(sourceID);
	}

	@Override
	public List<Source> readCollection() {
		return sourceDao.list();
	}

	@Override
	public boolean isInstanceExist(String institution, String investigator) {
		if(sourceDao.getByUniqueKey(institution, investigator) != null)
			return true;
		return false;
	}

	@Override
	public List<Source> search(Map<String, String> map) {
		return sourceDao.search(map);
	}
	@Override
	public List<Source> listSearch(Map<String, List<String>> map) {
		
		return sourceDao.listSearch(map);
	}

}
