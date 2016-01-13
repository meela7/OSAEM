package org.galilee.dms.model;

public class Fishes {
	
	private int FishID;
	private String FishClass;
	private String Order;
	private String Family;
	private String ScientificName;
	private String Species;
	private FishCharacterCV ToleranceGuild;
	private FishCharacterCV TrophicGuild;
	private FishCharacterCV HabitatGuild;
	private String InvasiveSpecies;
	private String EndemicSpecies;
	private String EndangeredSpecies;
	private String NaturalMonument;
	private String ImageLink;
	private String Description;
	private Features Feature;
	
	public Fishes(){
		
	}

	public int getFishID() {
		return FishID;
	}
	public void setFishID(int fishID) {
		FishID = fishID;
	}
	
	public String getFishClass() {
		return FishClass;
	}
	
	public void setFishClass(String fishClass) {
		FishClass = fishClass;
	}
	
	public String getOrder() {
		return Order;
	}
	public void setOrder(String order) {
		Order = order;
	}
	public String getFamily() {
		return Family;
	}
	public void setFamily(String family) {
		Family = family;
	}
	public String getScientificName() {
		return ScientificName;
	}
	public void setScientificName(String scientificName) {
		ScientificName = scientificName;
	}
	public String getSpecies() {
		return Species;
	}
	public void setSpecies(String species) {
		Species = species;
	}
	public FishCharacterCV getToleranceGuild() {
		return ToleranceGuild;
	}
	public void setToleranceGuild(FishCharacterCV toleranceGuild) {
		ToleranceGuild = toleranceGuild;
	}
	public FishCharacterCV getTrophicGuild() {
		return TrophicGuild;
	}
	public void setTrophicGuild(FishCharacterCV trophicGuild) {
		TrophicGuild = trophicGuild;
	}
	public FishCharacterCV getHabitatGuild() {
		return HabitatGuild;
	}
	public void setHabitatGuild(FishCharacterCV habitatGuild) {
		HabitatGuild = habitatGuild;
	}
	public String getInvasiveSpecies() {
		return InvasiveSpecies;
	}
	public void setInvasiveSpecies(String invasiveSpecies) {
		InvasiveSpecies = invasiveSpecies;
	}
	
	public String getEndemicSpecies() {
		return EndemicSpecies;
	}

	public void setEndemicSpecies(String endemicSpecies) {
		EndemicSpecies = endemicSpecies;
	}

	public String getEndangeredSpecies() {
		return EndangeredSpecies;
	}
	public void setEndangeredSpecies(String endangeredSpecies) {
		EndangeredSpecies = endangeredSpecies;
	}
	public String getNaturalMonument() {
		return NaturalMonument;
	}
	public void setNaturalMonument(String natureMonument) {
		NaturalMonument = natureMonument;
	}
	public String getImageLink() {
		return ImageLink;
	}
	public void setImageLink(String imageLink) {
		ImageLink = imageLink;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}

	public Features getFeature() {
		return Feature;
	}

	public void setFeature(Features feature) {
		Feature = feature;
	}
	
}
