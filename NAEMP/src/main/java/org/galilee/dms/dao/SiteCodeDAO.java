package org.galilee.dms.dao;

import java.util.List;

import org.galilee.dms.model.SiteCode;

public interface SiteCodeDAO {
	
	public boolean insert(SiteCode siteCode);
	public boolean update(SiteCode siteCode);
	public boolean delete(SiteCode siteCode);
	public SiteCode selectByID(int siteCodeID);
	public List<SiteCode> selectAll();
	
}
