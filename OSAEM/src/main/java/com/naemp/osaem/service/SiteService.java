package com.naemp.osaem.service;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Site;

public interface SiteService {
	
	/**
	 * Class Name:	SiteService.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.01
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public int newInstance(Site site);
	public Site readInstance(int siteID);
	public boolean updateInstance(Site site);
	public boolean deleteInstance(int siteID);	
	public List<Site> readCollection();
	
	public boolean isInstanceExist(String name, String lat, String lng);
	public List<Site> search(Map<String, String> map);
	public List<Site> listSearch(Map<String, List<String>> map);
	
	public List<Site> findByIDs(List<Integer> siteIDs);

}
