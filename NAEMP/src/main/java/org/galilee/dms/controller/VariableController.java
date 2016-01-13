package org.galilee.dms.controller;

import java.util.List;

import org.galilee.dms.model.Variables;
import org.galilee.dms.service.VariableService;
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

@RestController
@RequestMapping(value = "/api/v1")
public class VariableController {
	private static final Logger logger = LoggerFactory.getLogger(VariableController.class);
	
	@Autowired
	private VariableService variableService;
	
	/*
	 * Services - basic variable web resources
	 */
	//--------------------Create a Variable--------------------
	@RequestMapping(value="/variables/new", method=RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Variables variable){
		logger.debug("Creating Variable: {} ..." + variable.getVariableName());
		if(variableService.findByName(variable.getVariableName()) != null){
			logger.debug("A Variable with name {} already exist.", variable.getVariableName() );
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}	
		variableService.add(variable);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	//--------------------Retrieve a Variable--------------------
	@RequestMapping(value="/variables/{id}", method=RequestMethod.GET)
	public ResponseEntity<Variables> find(@PathVariable("id") int variableID){
		logger.debug("Fetching Variable with ID: {} ...", variableID);
		Variables variable = variableService.findByID(variableID);
		if(variable== null){
			logger.debug("Varaiable with ID: {}, not found.", variableID );
			return new ResponseEntity<Variables>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Variables>(variable, HttpStatus.OK);
	}
	 
	//--------------------Update a Variable--------------------
	@RequestMapping(value="/variables/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Variables variable,  @PathVariable("id") int variableID){
		logger.debug("Updating Variable: {} ...", variable.getVariableName());		
		if(variableID != variable.getVariableID()){
			logger.debug("Variable ID: {} , {} doesn't match.", variableID, variable.getVariableID());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else {
			Variables oldVariable = this.variableService.findByID(variableID);
			if(oldVariable == null){
				logger.debug("Variable with ID: {}, not found.", variableID);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}
		variableService.update(variable);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//--------------------Delete a Variable--------------------	
	@RequestMapping(value="/variables/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable int variableID){
		logger.debug("Fetching & Deleting Variable with ID {} ...", variableID);
		Variables variable = this.variableService.findByID(variableID);
		if(variable == null){
			logger.debug("Unable to delete. Variable with id: {}, not found.", variableID);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		variableService.delete(variableID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/*
	 * Services - variable collection web resource
	 */
	//--------------------Retrieve All Variables--------------------
	@RequestMapping(value="/variables", method=RequestMethod.GET)
	public ResponseEntity<List<Variables>> findAll(){
		logger.debug("Fetchin All Variables ...");
		List<Variables> variables = variableService.findAll();
		if(variables.isEmpty()){
			logger.debug("No Variables found.");
			return new ResponseEntity<List<Variables>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Variables>>(variables, HttpStatus.OK);
	}
	
	//--------------------Retrieve Variables by Type--------------------
	@RequestMapping(value="/variables", params="type", method=RequestMethod.GET)
	public ResponseEntity<List<Variables>> findByType(@RequestParam("type") String type){
		logger.debug("Searching Variables by type: {} ... ", type);
		List<Variables> variables = variableService.findByType(type);
		if(variables.isEmpty()){
			logger.debug("No Variables found.");
			return new ResponseEntity<List<Variables>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Variables>>(variables, HttpStatus.OK);
	}
}