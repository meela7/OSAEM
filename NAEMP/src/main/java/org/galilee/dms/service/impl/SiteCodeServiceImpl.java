package org.galilee.dms.service.impl;

import java.util.List;

import org.galilee.dms.dao.SiteCodeDAO;
import org.galilee.dms.model.SiteCode;
import org.galilee.dms.service.SiteCodeService;

public class SiteCodeServiceImpl implements SiteCodeService {

	SiteCodeDAO siteCodeDao;

	public void setSiteCodeDao(SiteCodeDAO siteCodeDao) {
		this.siteCodeDao = siteCodeDao;
	}

	@Override
	public boolean add(SiteCode siteCode) {
		
		return this.siteCodeDao.insert(siteCode);
	}

	@Override
	public boolean update(SiteCode siteCode) {
		
		return this.siteCodeDao.update(siteCode);
	}

	@Override
	public boolean delete(int siteCodeID) {
		
		SiteCode siteCode = this.siteCodeDao.selectByID(siteCodeID);
		return this.siteCodeDao.delete(siteCode);
	}

	@Override
	public SiteCode findByID(int siteCodeID) {
		
		return this.siteCodeDao.selectByID(siteCodeID);
	}

	@Override
	public List<SiteCode> findAll() {
		
		return this.siteCodeDao.selectAll();
	}

}
