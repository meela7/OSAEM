package org.galilee.dms.controller;

import java.util.List;

import org.galilee.dms.model.Rivers;
import org.galilee.dms.service.RiverService;
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
public class RiverController {
	private static final Logger logger = LoggerFactory.getLogger(RiverController.class);
	
	@Autowired
	private RiverService riverService;
	
	/*
	 * Services - basic river web resources
	 */
	//--------------------Create a River--------------------
	@RequestMapping(value="/rivers/new", method=RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Rivers river){
		logger.debug("Creating River: {} ..." + river.getRiverName());
		if(riverService.findByName(river.getRiverName()) != null){
			logger.debug("A River with name {} already exist.", river.getRiverName());
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}			
		riverService.add(river);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	//--------------------Retrieve a River--------------------
	@RequestMapping(value="/rivers/{id}", method=RequestMethod.GET)
	public ResponseEntity<Rivers> find(@PathVariable("id") int riverID){
		logger.debug("Fetching River with ID: {} ...", riverID);
		Rivers river = this.riverService.findByID(riverID);
		if(river== null){
			logger.debug("River with ID: {}, not found.", riverID );
			return new ResponseEntity<Rivers>(HttpStatus.NOT_FOUND);
		}
		this.riverService.findByID(riverID);
		return new ResponseEntity<Rivers>(river, HttpStatus.OK);
	}
	
	//--------------------Update a River--------------------
	@RequestMapping(value="/rivers/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Rivers river, @PathVariable("id") int riverID){
		logger.debug("Updating River: {} ...", river.getRiverName());
		
		if(riverID != river.getRiverID()){
			logger.debug("River ID: {} , {} doesn't match.", riverID, river.getRiverID());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else {
			Rivers oldRiver = this.riverService.findByID(riverID);
			if(oldRiver == null){
				logger.debug("River with ID: {}, not found.", riverID);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}
		this.riverService.update(river);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//--------------------Delete a River--------------------
	@RequestMapping(value="/rivers/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Rivers> delete(@PathVariable int riverID){
		logger.debug("Fetching & Deleting River with ID {} ...", riverID);
		Rivers river = this.riverService.findByID(riverID);
		if(river == null){
			logger.debug("Unable to delete. River with id: {}, not found.", riverID);
			return new ResponseEntity<Rivers>(HttpStatus.NO_CONTENT);
		}
		this.riverService.delete(riverID);
		return new ResponseEntity<Rivers>(HttpStatus.OK);		
	}
	
	/*
	 * Services - river collection web resource
	 */	

	//--------------------Retrieve All Rivers--------------------
	@RequestMapping(value="/rivers", method=RequestMethod.GET)
	public ResponseEntity<List<Rivers>> findAll(){
		logger.debug("Fetchin All Rivers ...");
		List<Rivers> rivers = this.riverService.findAll();
		if(rivers.isEmpty()){
			logger.debug("No Rivers found.");
			return new ResponseEntity<List<Rivers>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Rivers>>(rivers, HttpStatus.OK);
	}
	
	//--------------------Retrieve List of Rivers--------------------
	@RequestMapping(value="/rivers", params="list", method=RequestMethod.GET)
	public ResponseEntity<List<Rivers>> findByIDList(@RequestParam("list") List<Integer> riverIDs){
		
		logger.debug("Fetching Rivers by {} IDs", riverIDs.size());
		List<Rivers> rivers = this.riverService.findByIDs(riverIDs);
		if(rivers.size() == 0){
			logger.debug("Fetching Rivers by the list of IDs have no result.");
			return new ResponseEntity<List<Rivers>>(HttpStatus.NO_CONTENT);
		}
        return new ResponseEntity<List<Rivers>>(rivers, HttpStatus.OK);
	}
	
	//--------------------Retrieve Rivers by Basin--------------------
	@RequestMapping(value="/rivers", params="basin", method=RequestMethod.GET)
	public ResponseEntity<List<Rivers>> findByBasin(@RequestParam("basin") String basin){
		
		logger.debug("Searching Rivers by Basin: {}", basin);
        List<Rivers> rivers =  this.riverService.findByBasin(basin);
        
		if(rivers.size() == 0){
			logger.debug("Searching Rivers by Basin: {} have no result.", basin);
			return new ResponseEntity<List<Rivers>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Rivers>>(rivers, HttpStatus.OK);
	}
	
	//--------------------Retrieve Rivers by Mid Watershed (중권역)--------------------
	@RequestMapping(value="/rivers", params="midWatershed", method=RequestMethod.GET)
	public ResponseEntity<List<Rivers>> findByMid(@RequestParam("midWatershed") String mid){
		 
        logger.debug("Searching Rivers by mid watershed: {} ...", mid);
		List<Rivers> rivers = this.riverService.findByMid(mid);
		if(rivers.size() == 0){
			logger.debug("Searching Rivers by mid watershed: {}, have no result.", mid);
			return new ResponseEntity<List<Rivers>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Rivers>>(rivers, HttpStatus.OK); 
	}
	
	//--------------------Retrieve Rivers by Sub Watershed (소권역)--------------------
	@RequestMapping(value="/rivers", params="subWatershed", method=RequestMethod.GET)
	public ResponseEntity<List<Rivers>> findBySub(@RequestParam("subWatershed") String sub){
		
		logger.debug("Searching Rivers by mid watershed: {} ...", sub);
		List<Rivers> rivers = this.riverService.findBySub(sub);
		if(rivers.size() == 0){
			logger.debug("Searching Rivers by mid watershed: {}, have no result.", sub);
			return new ResponseEntity<List<Rivers>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Rivers>>(rivers, HttpStatus.OK); 
	}
	
	@RequestMapping(value="/rivers", params="classification", method=RequestMethod.GET)
	public ResponseEntity<List<Rivers>> findByClass(@RequestParam("classification") String classification){
		
		logger.debug("Searching Rivers by Classification: {} ...", classification);
		List<Rivers> rivers = this.riverService.findByClassification(classification);
		if(rivers.size() == 0){
			logger.debug("Searching Rivers by Classification: {}, have no result.", classification);
			return new ResponseEntity<List<Rivers>>(HttpStatus.NO_CONTENT);
		}
        return new ResponseEntity<List<Rivers>>(rivers, HttpStatus.OK); 
	}
	
	@RequestMapping(value="/rivers", params= {"basin", "classification"}, method=RequestMethod.GET)
	public ResponseEntity<List<Rivers>> findByBasinNClass(@RequestParam("basin") String basin, @RequestParam("classification") String classification){
		
		logger.debug("Searching Rivers by Basin: {} and Classification: {} ...", basin, classification);
		List<Rivers> rivers =  this.riverService.findByBasinNClass(basin, classification);
		if(rivers.size() == 0){
			logger.debug("Searching Rivers by Classification: {}, have no result.", classification);
			return new ResponseEntity<List<Rivers>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Rivers>>(rivers, HttpStatus.OK); 
	}
}