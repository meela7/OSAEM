package com.naemp.osaem.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.naemp.osaem.model.River;
import com.naemp.osaem.service.RiverService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:/META-INF/hibernate-context.xml")
public class ServiceTest {

	/**
	 * Class Name: ServiceTest.java 
	 * Description:
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.03
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	@Autowired
	private RiverService riverService;
	
	@Test
	public void testRiverService() {
		List<River> rivers = riverService.readCollection();	
		if(rivers !=null){
			for(River river : rivers)
				System.out.println(river.getRiverName());
		}
	}
}
