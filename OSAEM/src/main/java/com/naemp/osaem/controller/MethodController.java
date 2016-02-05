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

import com.naemp.osaem.model.Method;
import com.naemp.osaem.service.MethodService;

import io.swagger.annotations.Api;

@Api(value = "Methods")
@RestController
public class MethodController {

	/**
	 * Class Name: MethodController.java 
	 * Description: CRUD, Service
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MethodController.class);

	@Autowired
	private MethodService methodService;

	// -------------------- Read and Search Method Collection Resource --------------------
	@RequestMapping(value = "/methods", method = RequestMethod.GET)
	public ResponseEntity<List<Method>> list(@RequestParam(required = false) Map<String, String> params) {
		// read Method collection resource when there is no parameter.
		if (params.isEmpty()) {
			logger.info("Reading Method Collection Resource ...");
			List<Method> methods = methodService.readCollection();
			if (methods.isEmpty()) {
				logger.info("No Methods found.");
				return new ResponseEntity<List<Method>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Method>>(methods, HttpStatus.OK);
		}
		// search Method collection resource with parameters.
		else {
			logger.info("Searching Method Resource ...");

			Map<String, String> map = new HashMap<String, String>();
			for (String key : params.keySet()) {
				logger.info("Parameter :{}", key);
				// uppercase first letter of property name
				String param = key.substring(0, 1).toUpperCase();
				param = param + key.substring(1);
				String value = params.get(key);

				// decode parameters
				try {
					map.put(param, new String(value.getBytes("8859_1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// check if the URL encoded
				// if (value.contains("%")) {
				// logger.info("Parameter value: {} is encoded", value);
				// try {
				// map.put(key, new String(value.getBytes("8859_1"), "UTF-8"));
				// } catch (UnsupportedEncodingException e) {
				// e.printStackTrace();
				// }
				// }
				// else{
				// logger.info("Parameter value: {} is not encoded", value);
				// map.put(key, value);
				// }
			}

			List<Method> methods = this.methodService.search(map);
			return new ResponseEntity<List<Method>>(methods, HttpStatus.OK);
		}
	}

	// -------------------- Create a Method Instance Resource ------------------
	@RequestMapping(value = "/methods/new", method = RequestMethod.POST)
	public ResponseEntity<Integer> create(@RequestBody Method method, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Method Instance Resource of Name: {} ..." + method.getMethodName());
		// check if method contains the Not Null field in the database.
		if (method.getMethodName() == null)
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);

		if (methodService.isInstanceExist(method.getMethodName())) {
			logger.info("A Method with name {} already exist.", method.getMethodName());
			return new ResponseEntity<Integer>(HttpStatus.CONFLICT);
		}

		int createdID = methodService.newInstance(method);
		return new ResponseEntity<Integer>(createdID, HttpStatus.CREATED);
	}

	// -------------------- Read a Method Instance Resource --------------------
	@RequestMapping(value = "/methods/{id}", method = RequestMethod.GET)
	public ResponseEntity<Method> read(@PathVariable("id") int methodID) {
		logger.info("Reading Method Instance Resource of ID: {} ...", methodID);
		Method method = this.methodService.readInstance(methodID);
		if (method == null) {
			logger.info("Method Instance Resource of ID: {}, not found.", methodID);
			return new ResponseEntity<Method>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Method>(method, HttpStatus.OK);
	}

	// -------------------- Update a Method Instance Resource ------------------
	@RequestMapping(value = "/methods/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Boolean> update(@RequestBody Method method, @PathVariable("id") int methodID) {
		logger.info("Updating Method Instance Resource of ID: {} ...", method.getMethodID());

		if (methodID != method.getMethodID()) {
			logger.info("Method Instance Resource of ID: {} , {} doesn't match.", methodID, method.getMethodID());
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		} else {
			Method oldMethod = this.methodService.readInstance(methodID);
			if (oldMethod == null) {
				logger.info("Method Instance Resource of ID: {}, not found.", methodID);
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
			}
		}
		// set the null of Method with oldMethod

		Boolean res = this.methodService.updateInstance(method);
		if (res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT);
	}

	// -------------------- Delete a Method Instance Resource ------------------
	@RequestMapping(value = "/methods/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") int methodID) {
		logger.info("Reading & Deleting Method Instance Resource of ID: {} ...", methodID);
		Method method = this.methodService.readInstance(methodID);
		if (method == null) {
			logger.info("Unable to delete. Method Instance Resource of ID: {}, not found.", methodID);
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
		Boolean res = this.methodService.deleteInstance(methodID);
		if (res)
			return new ResponseEntity<Boolean>(res, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(res, HttpStatus.CONFLICT); // when
																			// Method
																			// has
																			// existing
																			// related
																			// Sites
	}

	// -------------------- Search for Method Resource --------------------
	@RequestMapping(value = "/methods", method = RequestMethod.POST)
	public ResponseEntity<List<Method>> search(@RequestBody Map<String, String> map) {
		logger.info("Searching Method Resource ...");

		for (String key : map.keySet()) {
			logger.info("The search parameter: {} ", key);
		}
		List<Method> methods = this.methodService.search(map);
		if (methods.isEmpty() || methods == null)
			return new ResponseEntity<List<Method>>(methods, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<Method>>(methods, HttpStatus.OK);
	}

}
