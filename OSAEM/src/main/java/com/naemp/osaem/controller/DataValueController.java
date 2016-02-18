package com.naemp.osaem.controller;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.naemp.osaem.model.DataValue;
import com.naemp.osaem.model.DataValueJoined;
import com.naemp.osaem.service.DataValueService;

import io.swagger.annotations.Api;

@Api(value = "Values")
@RestController
public class DataValueController {

	/**
	 * Class Name: DataValueController.java 
	 * Description: CRUD, Service
	 * 
	 * @author Meilan Jiang
	 * @since 2016.01.30
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataValueController.class);

	@Autowired
	private DataValueService dataValueService;

	// -------------------- Read and Search DataValue Collection Resource --------------------
	@RequestMapping(value = "/values", method = RequestMethod.GET)
	public ResponseEntity<List> list(@RequestParam(required = false) MultiValueMap<String, String> params) {
		
		logger.info("Searching DataValue Resource ...");
		boolean joinFlag = false;
		
		PropertyDescriptor[] props = BeanUtils.getPropertyDescriptors(DataValue.class);
		List<String> variables = new ArrayList<String>();
		for (PropertyDescriptor desc : props) {
			variables.add(desc.getName());
		}
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();	
		for (String key : params.keySet()) {
			if(key.equals("join") && params.get(key).get(0).equals("on"))
				joinFlag = true;
			if(variables.contains(key)){
				// uppercase first letter of property name
				String param = key.substring(0,1).toUpperCase();
				param = param + key.substring(1);				
			
				List<String> values = new ArrayList<String>();
				// set forceEncodingFilter in the web.xml, therefore need decode every value.
				for(String value: params.get(key)){
					try {
						values.add(new String(value.getBytes("8859_1"), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				map.put(param, values);		
			}
			else
				logger.info("Unexpected Parameter :{} has been removed.", key);
		}
		if(map.keySet().size() == 0){
			return new ResponseEntity<List>(HttpStatus.BAD_REQUEST);
		}else{
			
			if(joinFlag){
				logger.info("JOIN ON");
				List<DataValueJoined> dataValues = this.dataValueService.joinedSearch(map);
				if(dataValues.isEmpty() || dataValues == null)
					return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
				else
					return new ResponseEntity<List>(dataValues, HttpStatus.OK);
			}
			else{
				logger.info("JOIN OFF");
				
				List<DataValue> dataValues = this.dataValueService.listSearch(map);
				if(dataValues.isEmpty() || dataValues == null)
					return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
				else
					return new ResponseEntity<List>(dataValues, HttpStatus.OK);
			}
		}
	}

	// -------------------- Create a DataValue Instance Resource ------------------
	@RequestMapping(value = "/values/new", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(@RequestBody DataValue value, UriComponentsBuilder ucBuilder) {
		logger.info("Creating DataValue Instance Resource from Site: {} ..." + value.getSiteID());
		// check if value contains the Not Null field in the database.
		if (value.getDateTime() == null || value.getDataValue() == 0.0 || value.getSurveyTerm() == 0 || value.getSurveyYear() == 0 || value.getSiteID() == 0 || value.getVariableID() == 0 || value.getFeatureID() == 0 || value.getSourceID() == 0 || value.getMethodID() == 0 ) 
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		
		// check the unique key value first. check if it's null
		if (dataValueService.isInstanceExist(value.getDateTime(), value.getSiteID(), value.getVariableID(), value.getFeatureID(), value.getSourceID(), value.getMethodID())) {
			logger.info("A DataValue from Site: {} already exist.", value.getSiteID());
			return new ResponseEntity<Integer>(HttpStatus.CONFLICT);
		}
		
		int createdID = dataValueService.newInstance(value);
		return new ResponseEntity<Integer>(createdID, HttpStatus.CREATED);
	}

	// -------------------- Read a DataValue Instance Resource --------------------
	@RequestMapping(value = "/values/{id}", method = RequestMethod.GET)
	public ResponseEntity<DataValue> read(@PathVariable("id") int valueID) {
		logger.info("Reading DataValue Instance Resource of ID: {} ...", valueID);
		DataValue value = this.dataValueService.readInstance(valueID);
		if (value == null) {
			logger.info("DataValue Instance Resource of ID: {}, not found.", valueID);
			return new ResponseEntity<DataValue>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DataValue>(value, HttpStatus.OK);
	}

	// -------------------- Search for DataValue Resource --------------------
	@RequestMapping(value = "/values", method = RequestMethod.POST)
	public ResponseEntity<List> search(@RequestBody Map<String, List<String>> reqMap) {
		logger.info("Searching DataValue Resource ...");
		boolean joinFlag = false;
		
		PropertyDescriptor[] params = BeanUtils.getPropertyDescriptors(DataValue.class);
		List<String> variables = new ArrayList<String>();
		for (PropertyDescriptor desc : params) {
			variables.add(desc.getName());
		}
			
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (String key : reqMap.keySet()) {
			logger.info("Parameter: {}, Value:{}", key, reqMap.get(key));
			if(key.equals("join") && reqMap.get(key).get(0).equals("on"))
				joinFlag = true;
			if(variables.contains(key)){
				String param = key.substring(0,1).toUpperCase();
				param = param + key.substring(1);
				map.put(param, reqMap.get(key));
			}
			else
				logger.info("Unexpected Parameter :{} has been removed.", key);
		}
		
		if(map.keySet().size() == 0){
			return new ResponseEntity<List>(HttpStatus.BAD_REQUEST);
		}else{
			
			if(joinFlag){
				logger.info("JOIN ON");
				List<DataValueJoined> dataValues = this.dataValueService.joinedSearch(map);
				if(dataValues.isEmpty() || dataValues == null)
					return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
				else
					return new ResponseEntity<List>(dataValues, HttpStatus.OK);
			}
			else{
				logger.info("JOIN OFF");
				
				List<DataValue> dataValues = this.dataValueService.listSearch(map);
				if(dataValues.isEmpty() || dataValues == null)
					return new ResponseEntity<List>(HttpStatus.NOT_FOUND);
				else
					return new ResponseEntity<List>(dataValues, HttpStatus.OK);
			}
		}
	}
}
