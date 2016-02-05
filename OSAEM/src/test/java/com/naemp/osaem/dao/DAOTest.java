package com.naemp.osaem.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.naemp.osaem.model.Variable;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:/META-INF/hibernate-context.xml")
public class DAOTest {

	/**
	 * Class Name:	DAOTest.java
	 * Description: 	
	 * 
	 * @author Meilan Jiang
	 * @since 2016.01.29
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
//	@Autowired
//	private RiverDAO riverDao;
	
//	@Autowired
//	private SiteDAO siteDao;
	
	@Autowired
	private VariableDAO vDao;
	
	@Test
	public void testMethodDAO(){
		Variable v  = vDao.read(1);
		if(v == null)
			System.out.println("Null!");
		else
			System.out.println(v.getVariableName());
	}
	
//
//	@Test
//	public void testRiverDAO() {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("Basin", "ÇÑ°­");
//		List<River> rivers = riverDao.search(map);
//		if(rivers !=null){
//			for(River river : rivers)
//				System.out.println(river.getRiverName());
//		}
//		else
//			System.out.println("No Result");
//	}
	
//	@Test
//	public void testSiteDAO(){
//		Site site  = siteDao.getByUniqueKey("test", "test", "test");
//		if(site == null)
//			System.out.println("Null!");
//		else
//			System.out.println(site.getRiver().getRiverName());
//	}
}
