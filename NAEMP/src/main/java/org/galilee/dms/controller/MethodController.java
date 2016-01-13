package org.galilee.dms.controller;

import java.util.List;

import org.galilee.dms.model.Methods;
import org.galilee.dms.service.MethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class MethodController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(MethodController.class);

	@Autowired
	private MethodService methodService;

	/*
	 * Services - basin method web services
	 */
	//--------------------Create a Method--------------------
	@RequestMapping(value = "/methods/new", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Methods method) {
		logger.debug("Creating Method: {} ..." + method.getMethodName());
		if(methodService.findByName(method.getMethodName()) != null){
			logger.debug("A Method with name {} already exist.", method.getMethodName() );
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		this.methodService.add(method);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}	

	//--------------------Read a Method--------------------
	@RequestMapping(value = "/methods/{id}", method = RequestMethod.GET)
	public ResponseEntity<Methods> find(@PathVariable("id") int methodID) {

		logger.debug("Fetching Method with ID: {} ...", methodID);
		Methods method = this.methodService.findByID(methodID);
		if(method == null){
			logger.debug("Method with ID: {}, not found.", methodID );
			return new ResponseEntity<Methods>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Methods>(method, HttpStatus.OK);
	}

	//--------------------Update a Method--------------------
	@RequestMapping(value = "/methods/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Methods method, @PathVariable("id") int methodID) {

		logger.debug("Updating Method: {} ...", method.getMethodName());		
		if(methodID != method.getMethodID()){
			logger.debug("Method ID: {} , {} doesn't match.", methodID, method.getMethodID());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else {
			Methods oldMethod = this.methodService.findByID(methodID);
			if(oldMethod == null){
				logger.debug("Method with ID: {}, not found.", methodID);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}
		this.methodService.update(method);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	//--------------------Delete a Method--------------------
	@RequestMapping(value = "/methods/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable int methodID) {
		logger.debug("Fetching & Deleting Method with ID {} ...", methodID);
		Methods method = this.methodService.findByID(methodID);
		if(method == null){
			logger.debug("Unable to delete. Method with id: {}, not found.", methodID);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		this.methodService.delete(methodID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*
	 * Services - method collection web service
	 */
	//--------------------Retrieve All Methods--------------------
	@RequestMapping(value = "/methods", method = RequestMethod.GET)
	public ResponseEntity<List<Methods>> findAll() {

		logger.debug("Fetchin All Methods ...");
		List<Methods> methods = this.methodService.findAll();
		if(methods.isEmpty()){
			logger.debug("No Methods found.");
			return new ResponseEntity<List<Methods>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Methods>>(methods, HttpStatus.OK);
	}

}