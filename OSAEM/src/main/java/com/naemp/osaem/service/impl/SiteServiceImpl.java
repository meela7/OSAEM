package com.naemp.osaem.service.impl;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.dao.SiteDAO;
import com.naemp.osaem.model.Site;
import com.naemp.osaem.service.SiteService;

public class SiteServiceImpl implements SiteService {

	/**
	 * Class Name:	SiteServiceImpl.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.01
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	SiteDAO siteDao;
	
	public void setSiteDao(SiteDAO siteDao){
		this.siteDao = siteDao;
	}
	@Override
	public int newInstance(Site site) {
		Site newSite = null;
		if(siteDao.create(newSite))
			newSite = siteDao.getByUniqueKey(site.getSiteName(), site.getLatitude(), site.getLongitude());
		return newSite.getRiver().getRiverID();
	}

	@Override
	public Site readInstance(int siteID) {
		Site site = siteDao.read(siteID);
		return site;
	}

	@Override
	public boolean updateInstance(Site site) {
		return siteDao.update(site);
	}

	@Override
	public boolean deleteInstance(int siteID) {
		return siteDao.delete(siteID);
	}

	@Override
	public List<Site> readCollection() {
		return siteDao.list();
	}

	@Override
	public boolean isInstanceExist(String name, String lat, String lng) {
		if(siteDao.getByUniqueKey(name, lat, lng) != null)
			return true;
		return false;
	}

	@Override
	public List<Site> search(Map<String, String> map) {
		return siteDao.search(map);
	}

}
