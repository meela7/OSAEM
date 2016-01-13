package org.galilee.dms.service;

import java.util.List;

import org.galilee.dms.model.Fishes;

public interface FishService {	
	
	public void add(Fishes fish);
	public void update(Fishes fish);	
	public void delete(Fishes fish);
	public Fishes findByID(int fishID);
	public List<Fishes> findAll();
	public List<Fishes> findByIDSet(List<Integer> fishIDList);
	
	public List<Fishes> findEndangeredSpecies();
	public List<Fishes> findNativeSpecies();
	public List<Fishes> findSensitiveSpsecies();
	public List<Fishes> findTolerantSpsecies();
	public List<Fishes> findOmnivoreSpsecies();
	public List<Fishes> findInsectivoreSpsecies();
	
	public List<Fishes> findByClass(String name);
	public List<Fishes> findByOrder(String name);	
	public List<Fishes> findByFamily(String name);
	public List<Fishes> findByTaxon(String name);
	public List<Fishes> findBySpecies(String species);	
}
