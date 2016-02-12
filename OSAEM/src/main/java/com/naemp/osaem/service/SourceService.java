package com.naemp.osaem.service;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Source;

public interface SourceService {
	
	/**
	 * Class Name:	SourceService.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public int newInstance(Source source);
	public Source readInstance(int sourceID);
	public boolean updateInstance(Source source);
	public boolean deleteInstance(int sourceID);	
	public List<Source> readCollection();
	
	public boolean isInstanceExist(String institution, String invastigator);
	public List<Source> search(Map<String, String> map);
	public List<Source> listSearch(Map<String, List<String>> map);

}
