package org.galilee.dms.controller;

import java.util.List;

import org.galilee.dms.model.Units;
import org.galilee.dms.service.UnitService;
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
public class UnitController {
	private static final Logger logger = LoggerFactory.getLogger(UnitController.class);
	
	@Autowired
	private UnitService unitService;
	
	/*
	 * Services - basic unit web resources
	 */
	//--------------------Create a Unit--------------------
	@RequestMapping(value="/units/new", method=RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Units unit){
		logger.debug("Creating Unit: {} ..." + unit.getUnitName());
		if(unitService.findByName(unit.getUnitName()) != null){
			logger.debug("A Unit with name {} already exist.", unit.getUnitName() );
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		unitService.add(unit);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	//--------------------Read a Unit--------------------
	@RequestMapping(value="/units/{id}", method=RequestMethod.GET)
	public ResponseEntity<Units> find(@PathVariable("id") int unitID){
		logger.debug("Fetching Unit with ID: {} ...", unitID);
		Units unit = unitService.findByID(unitID);
		if(unit == null){
			logger.debug("Unit with ID: {}, not found.", unitID );
			return new ResponseEntity<Units>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Units>(unit, HttpStatus.OK);
	}
	
	//--------------------Update a Unit--------------------
	@RequestMapping(value="/units/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Units unit, @PathVariable("id") int unitID){
		logger.debug("Updating Unit: {} ...", unit.getUnitName());		
		if(unitID != unit.getUnitID()){
			logger.debug("Unit ID: {} , {} doesn't match.", unitID, unit.getUnitID());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else {
			Units oldUnit = this.unitService.findByID(unitID);
			if(oldUnit == null){
				logger.debug("Unit with ID: {}, not found.", unitID);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}
		this.unitService.update(unit);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//--------------------Delete a Unit--------------------
	@RequestMapping(value="/units/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable int unitID){
		logger.debug("Fetching & Deleting Unit with ID {} ...", unitID);
		Units unit = this.unitService.findByID(unitID);
		if(unit == null){
			logger.debug("Unable to delete. Unit with id: {}, not found.", unitID);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		this.unitService.delete(unitID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/*
	 * Services - unit collection resource
	 */
	//--------------------Retrieve All Units--------------------
	@RequestMapping(value="/units", method=RequestMethod.GET)
	public ResponseEntity<List<Units>> findAll(){
		logger.debug("Fetchin All Units ...");
		List<Units> units = unitService.findAll();
		if(units.isEmpty()){
			logger.debug("No Units found.");
			return new ResponseEntity<List<Units>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Units>>(units, HttpStatus.OK);
	}	
}