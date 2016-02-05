package com.naemp.osaem.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.naemp.osaem.model.Variable;
import com.naemp.osaem.service.VariableService;

import io.swagger.annotations.Api;

@Api(value = "Variables")
@RestController
public class VariableController {

	/**
	 * Class Name: VariableController.java 
	 * Description: CRUD, Search
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.01
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */

	private static final Logger logger = LoggerFactory.getLogger(VariableController.class);

	@Autowired
	private VariableService variableService;

	// -------------------- Read and Search Variable Collection Resource --------------------
	@RequestMapping(value = "/variables", method = RequestMethod.GET)
	public ResponseEntity<List<Variable>> list(@RequestParam(required = false) Map<String, String> params) {
		// read River collection resource when there is no parameter.
		if (params.isEmpty()) {
			logger.info("Reading Variable Collection Resource ...");
			List<Variable> variables = variableService.readCollection();
			if (variables.isEmpty()) {
				logger.info("No Variables found.");
				return new ResponseEntity<List<Variable>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Variable>>(variables, HttpStatus.OK);
		}
		// search Variable collection resource with parameters.
		else {
			logger.info("Searching Variable Resource ...");

			Map<String, String> map = new HashMap<String, String>();
			for (String key : params.keySet()) {
				// uppercase first letter of property name
				String param = key.substring(0,1).toUpperCase();
				param = param + key.substring(1);
				String value = params.get(key);
				
				// decode parameters
				try {
					map.put(param, new String(value.getBytes("8859_1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// check if the URL encoded
//				if (value.contains("%")) {
//					logger.info("Parameter value: {} is encoded", value);
//					try {
//						map.put(key, new String(value.getBytes("8859_1"), "UTF-8"));
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//					}
//				}
//				else{
//					logger.info("Parameter value: {} is not encoded", value);
//					map.put(key, value);
//				}
			}
			
			List<Variable> variables = this.variableService.search(map);
			return new ResponseEntity<List<Variable>>(variables, HttpStatus.OK);
		}
	}

	// -------------------- Create a Variable Instance Resource --------------------
	@RequestMapping(value = "/variables/new", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(@RequestBody Variable variable, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Variable Instance Resource of Name: {} ..." + variable.getVariableName());
		
		if (variable.getVariableName() == null || variable.getUnit().getUnitID() == 0 ) 
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		
		if (variableService.isInstanceExist(variable.getVariableName(), variable.getUnit().getUnitID())) {
			logger.info("A Variable with name {} already exist.", variable.getVariableName());
			return new ResponseEntity<Integer>(HttpStatus.CONFLICT);
		}
		int createdID = variableService.newInstance(variable);
		return new ResponseEntity<Integer>(createdID, HttpStatus.CREATED);
	}

	// -------------------- Read a Variable Instance Resource --------------------
	@RequestMapping(value = "/variables/{id}", method = RequestMethod.GET)
	public ResponseEntity<Variable> find(@PathVariable("id") int variableID) {
		logger.info("Reading Variable Instance Resource of ID: {} ...", variableID);
		Variable variable = this.variableService.readInstance(variableID);
		if (variable == null) {
			logger.info("Variable Instance Resource of ID: {}, not found.", variableID);
			return new ResponseEntity<Variable>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Variable>(variable, HttpStatus.OK);
	}

	// -------------------- Update a Variable Instance Resource --------------------
	@RequestMapping(value = "/variables/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> update(@RequestBody Variable variable, @PathVariable("id") int variableID) {
		logger.info("Updating Variable Instance Resource of ID: {} ...", variable.getVariableID());

		if (variableID != variable.getVariableID()) {
			logger.info("Variable Instance Resource of ID: {} , {} doesn't match.", variableID, variable.getVariableID());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} else {
			Variable oldVariable = this.variableService.readInstance(variableID);
			if (oldVariable == null) {
				logger.info("Variable Instance Resource of ID: {}, not found.", variableID);
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		}
		// set the null of Variable with oldVariable
		
		Boolean res = this.variableService.updateInstance(variable);
		if(res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT);
	}

	// -------------------- Delete a Variable Instance Resource --------------------
	@RequestMapping(value = "/variables/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") int variableID) {
		logger.info("Reading & Deleting Variable Instance Resource of ID: {} ...", variableID);
		Variable variable = this.variableService.readInstance(variableID);
		if (variable == null) {
			logger.info("Unable to delete. Variable Instance Resource of ID: {}, not found.", variableID);
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
		Boolean res = this.variableService.deleteInstance(variableID);
		if(res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT);
	}

	// -------------------- Search for Variable Resource --------------------
	@RequestMapping(value = "/variables", method = RequestMethod.POST)
	public ResponseEntity<List<Variable>> search(@RequestBody HashMap<String, String> map) {
		logger.info("Searching Variable Resource ...");

		for (String key : map.keySet()) {
			logger.info("The search parameter: {} ", key);
		}
		List<Variable> variables = this.variableService.search(map);
		if(variables == null || variables.isEmpty())
			return new ResponseEntity<List<Variable>>(variables, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<Variable>>(variables, HttpStatus.OK);
	}
}
