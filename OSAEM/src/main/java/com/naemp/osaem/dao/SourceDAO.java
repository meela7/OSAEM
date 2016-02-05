package com.naemp.osaem.dao;

import java.util.List;
import java.util.Map;

import com.naemp.osaem.model.Source;

public interface SourceDAO {
	
	/**
	 * Class Name: SourceDAO.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	public boolean create(Source source);
	public Source read(int sourceID);
	public boolean update(Source source);
	public boolean delete(int sourceID);
	
	public List<Source> list();
	public Source getByUniqueKey(String institution, String investigator);
	public List<Source> search(Map<String, String> map);

}
