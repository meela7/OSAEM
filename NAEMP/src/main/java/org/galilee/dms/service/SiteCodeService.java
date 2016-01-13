package org.galilee.dms.service;

import java.util.List;

import org.galilee.dms.model.SiteCode;

public interface SiteCodeService {	
	
	public boolean add(SiteCode siteCode);
	public boolean update(SiteCode siteCode);	
	public boolean delete(int siteCodeID);
	public SiteCode findByID(int siteCodeID);
	public List<SiteCode> findAll();
	
}
