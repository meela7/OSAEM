package org.galilee.dms.dao;

import java.util.List;

import org.galilee.dms.model.Fishes;

public interface FishDAO {
	
	public Fishes insert(Fishes fish);
	public Fishes update(Fishes fish);
	public void delete(Fishes fish);
	public Fishes selectByID(int fishID);
	public List<Fishes> selectAll();
	public List<Fishes> selectByIDs(List<Integer> fishIDList);
		
	public List<Fishes> selectByClass(String taxon);
	public List<Fishes> selectByOrder(String taxon);
	public List<Fishes> selectByFamily(String taxon);
	
	public List<Fishes> selectByToleranceGuild(String query);
	public List<Fishes> selectByTrophicGuild(String query);
	public List<Fishes> selectByHabitatGuild(String query);
	public List<Fishes> selectEndangered();
	public List<Fishes> selectNative();
	public List<Fishes> selectBySpecies(String species);
}
