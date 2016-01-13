package org.galilee.dms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.galilee.dms.dao.FishDAO;
import org.galilee.dms.model.Fishes;
import org.galilee.dms.service.FishService;

public class FishServiceImpl implements FishService {

	FishDAO fishDao;
	
	public void setFishDao(FishDAO fishDao){
		this.fishDao = fishDao;
	}
	
	@Override
	public void add(Fishes fish) {

		this.fishDao.insert(fish);
	}

	@Override
	public void update(Fishes fish) {

		
		this.fishDao.update(fish);
	}

	@Override
	public void delete(Fishes fish) {

		this.fishDao.delete(fish);
	}

	@Override
	public Fishes findByID(int fishID) {
		
		return this.fishDao.selectByID(fishID);
	}

	@Override
	public List<Fishes> findAll() {
		
		return this.fishDao.selectAll();
	}

	@Override
	public List<Fishes> findByIDSet(List<Integer> fishIDList) {
		return this.fishDao.selectByIDs(fishIDList);
	}

	@Override
	public List<Fishes> findEndangeredSpecies() {

		return this.fishDao.selectEndangered();
	}	
	
	@Override
	public List<Fishes> findByTaxon(String name) {		
		List<Fishes> fishes = new ArrayList<Fishes>();
		fishes = this.findByClass(name);
		if(fishes.size() != 0 )
			return fishes;
		else{
			fishes = this.findByOrder(name);
			if(fishes.size() != 0 )
				return fishes;
			else{
				return fishes = this.findByFamily(name);				
			}
		}
	}

	@Override
	public List<Fishes> findByClass(String name) {
		
		return this.fishDao.selectByClass(name);
	}

	@Override
	public List<Fishes> findByOrder(String name) {
		
		return this.fishDao.selectByOrder(name);
	}

	@Override
	public List<Fishes> findByFamily(String name) {
		
		return this.fishDao.selectByFamily(name);
	}

	@Override
	public List<Fishes> findTolerantSpsecies() {
		
		return this.fishDao.selectByToleranceGuild("TS");
	}

	@Override
	public List<Fishes> findSensitiveSpsecies() {
		
		return this.fishDao.selectByToleranceGuild("SS");
	}

	@Override
	public List<Fishes> findOmnivoreSpsecies() {
		
		return this.fishDao.selectByTrophicGuild("O");
	}

	@Override
	public List<Fishes> findInsectivoreSpsecies() {
		
		return this.fishDao.selectByTrophicGuild("I");
	}

	@Override
	public List<Fishes> findNativeSpecies() {

		return this.fishDao.selectNative();
	}

	@Override
	public List<Fishes> findBySpecies(String species) {
		
		return this.fishDao.selectBySpecies(species);
	}

}
