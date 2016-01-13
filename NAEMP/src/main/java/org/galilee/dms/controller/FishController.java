package org.galilee.dms.controller;

import java.util.ArrayList;
import java.util.List;

import org.galilee.dms.model.Fishes;
import org.galilee.dms.service.FishService;
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
public class FishController {
	private static final Logger logger = LoggerFactory
			.getLogger(FishController.class);

	@Autowired
	private FishService fishService;
	
	/*
	 * Services - basic fish web resource
	 */		
	//--------------------Create a Fish--------------------
	@RequestMapping(value = "/fishes/new", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Fishes fish) {

		logger.debug("Creating Fish: {} ..." + fish.getSpecies());
		if(fishService.findBySpecies(fish.getSpecies()) != null){
			logger.debug("A Fish with name {} already exist.", fish.getSpecies() );
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}			
		this.fishService.add(fish);
		return new ResponseEntity<Void>(HttpStatus.CREATED);	
	}

	//--------------------Retrieve a Fish--------------------
	@RequestMapping(value = "/fishes/{id}", method = RequestMethod.GET)
	public ResponseEntity<Fishes> find(@PathVariable("id") int fishID) {
		logger.debug("Fetching Fish with ID: {} ...", fishID);
		Fishes fish =  this.fishService.findByID(fishID);
		if(fish== null){
			logger.debug("Fish with ID: {}, not found.", fishID );
			return new ResponseEntity<Fishes>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Fishes>(fish, HttpStatus.OK);
		
	}
	
	//--------------------Update a Fish--------------------
	@RequestMapping(value = "/fishes/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Fishes fish, @PathVariable("id") int fishID) {

		logger.debug("Updating Fish: {} ...", fish.getSpecies());		
		if(fishID != fish.getFishID()){
			logger.debug("Fish ID: {} , {} doesn't match.", fishID, fish.getFishID());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else {
			Fishes oldFish = this.fishService.findByID(fishID);
			if(oldFish == null){
				logger.debug("Fish with ID: {}, not found.", fishID);
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		}
		this.fishService.update(fish);
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}

	//--------------------Delete a Fish--------------------
	@RequestMapping(value = "/fishes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable int fishID) {

		
		logger.debug("Fetching & Deleting Fish with ID {} ...", fishID);
		Fishes fish = this.fishService.findByID(fishID);
		if(fish == null){
			logger.debug("Unable to delete. Fish with id: {}, not found.", fishID);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		this.fishService.delete(fish);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/*
	 * Services - collection fish web resource
	 */
	//--------------------Retrieve All Fishes--------------------
	@RequestMapping(value = "/fishes", method = RequestMethod.GET)
	public ResponseEntity<List<Fishes>> findAll() {

		logger.debug("Fetchin All Fishes ...");
		List<Fishes> fishes = this.fishService.findAll();
		if(fishes.isEmpty()){
			logger.debug("No Fishes found.");
			return new ResponseEntity<List<Fishes>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Fishes>>(fishes, HttpStatus.OK);
	}
	//--------------------Retrieve List of Features--------------------
	@RequestMapping(value = "/fishes", params = {"list"}, method = RequestMethod.GET)
	public ResponseEntity<List<Fishes>> findFishes(@RequestParam("list") List<Integer> fishIDs) {

		logger.debug("Fetching Fishes by {} IDs", fishIDs.size());
		List<Fishes> fishes = this.fishService.findByIDSet(fishIDs);
		if(fishes.size() == 0){
			logger.debug("Fetching Fishes by the list of IDs have no result.");
			return new ResponseEntity<List<Fishes>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Fishes>>(fishes, HttpStatus.OK);
	}		
	
	//--------------------Retrieve Features by taxon--------------------
	@RequestMapping(value = "/fishes", params = {"taxon"}, method = RequestMethod.GET)
	public ResponseEntity<List<Fishes>> searchFishesByTaxon(@RequestParam("taxon") String taxon) {
	
		logger.debug("Searching Fishes by Taxon: {}", taxon);
		List<Fishes> fishes = this.fishService.findByTaxon(taxon);	
		if(fishes.size() == 0){
			logger.debug("Searching Fishes by Taxon: {} have no result.", taxon);
			return new ResponseEntity<List<Fishes>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Fishes>>(fishes, HttpStatus.OK);
	}
	
	//--------------------Retrieve Features by class--------------------
	@RequestMapping(value = "/fishes", params = {"class"}, method = RequestMethod.GET)
	public ResponseEntity<List<Fishes>> searchFishesByClass(@RequestParam("class") String name) {
	
		logger.debug("Searching Fishes by Class: {}", name);
		List<Fishes> fishes = this.fishService.findByClass(name);	
		if(fishes.size() == 0){
			logger.debug("Searching Fishes by Class: {} have no result.", name);
			return new ResponseEntity<List<Fishes>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Fishes>>(fishes, HttpStatus.OK);
	}
	
	//--------------------Retrieve Features by order--------------------
	@RequestMapping(value = "/fishes", params = {"order"}, method = RequestMethod.GET)
	public ResponseEntity<List<Fishes>> searchFishesByOrder(@RequestParam("order") String name) {
	
		logger.debug("Searching Fishes by Order: {}", name);
		List<Fishes> fishes = this.fishService.findByOrder(name);		
		if(fishes.size() == 0){
			logger.debug("Searching Fishes by Order: {} have no result.", name);
			return new ResponseEntity<List<Fishes>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Fishes>>(fishes, HttpStatus.OK);
	}
	
	//--------------------Retrieve Features by family--------------------
	@RequestMapping(value = "/fishes", params = {"family"}, method = RequestMethod.GET)
	public ResponseEntity<List<Fishes>> searchFishesByFamily(@RequestParam("family") String name) {
	
		logger.debug("Searching Fishes by Family: {}", name);
		List<Fishes> fishes = this.fishService.findByFamily(name);		
		if(fishes.size() == 0){
			logger.debug("Searching Fishes by Family: {} have no result.", name);
			return new ResponseEntity<List<Fishes>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Fishes>>(fishes, HttpStatus.OK);
	}
	
	//--------------------Retrieve Features by characteristics--------------------
	@RequestMapping(value = "/fishes", params = {"char"}, method = RequestMethod.GET)
	public ResponseEntity<List<Fishes>> searchFishesbyChar(@RequestParam("char") String character) {
		List<Fishes> fishes = new ArrayList<Fishes>();
		logger.debug("Searching Fishes by Characteristics: {}", character);
		if(character.equals("endangered"))	
			fishes = this.fishService.findEndangeredSpecies();
		else if(character.equals("tolerant"))
			fishes = this.fishService.findTolerantSpsecies();
		else if(character.equals("omnivore"))
			fishes = this.fishService.findOmnivoreSpsecies();
		else if(character.equals("insectivore"))
			fishes = this.fishService.findInsectivoreSpsecies();
		else if(character.equals("native"))
			fishes = this.fishService.findNativeSpecies();
		if(fishes.size() == 0){
			logger.debug("Searching Fishes by Characteristics: {} have no result.", character);
			return new ResponseEntity<List<Fishes>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Fishes>>(fishes, HttpStatus.OK);
	}
}