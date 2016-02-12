package com.naemp.osaem.dao;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Site;

public interface SiteDAO {
	
	/**
	 * Class Name:	SiteDAO.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.01
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public boolean create(Site site);
	public Site read(int siteID);
	public boolean update(Site site);
	public boolean delete(int siteID);
	
	public List<Site> list();
	public Site getByUniqueKey(String name, String lat, String lng);
	public List<Site> search(Map<String, String> map);
	public List<Site> listSearch(Map<String, List<String>> map);

}
