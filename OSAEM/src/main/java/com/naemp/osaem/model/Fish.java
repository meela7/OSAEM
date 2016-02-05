package com.naemp.osaem.model;

public class Fish {

	/**
	 * Class Name: Fish.java 
	 * Description: Hibernate Mapping pojo File
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	
	private int FishID;
	private String FishClass;
	private String Order;
	private String Family;
	private String ScientificName;
	private String Species;
	private String ToleranceGuild;
	private String TrophicGuild;
	private String HabitatGuild;
	private String InvasiveSpecies;
	private String EndemicSpecies;
	private String EndangeredSpecies;
	private String NaturalMonument;
	private String ImageLink;
	private String Description;
	private Feature Feature;
	
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
	public String getToleranceGuild() {
		return ToleranceGuild;
	}
	public void setToleranceGuild(String toleranceGuild) {
		ToleranceGuild = toleranceGuild;
	}
	public String getTrophicGuild() {
		return TrophicGuild;
	}
	public void setTrophicGuild(String trophicGuild) {
		TrophicGuild = trophicGuild;
	}
	public String getHabitatGuild() {
		return HabitatGuild;
	}
	public void setHabitatGuild(String habitatGuild) {
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
	public void setNaturalMonument(String naturalMonument) {
		NaturalMonument = naturalMonument;
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
	public Feature getFeature() {
		return Feature;
	}
	public void setFeature(Feature feature) {
		Feature = feature;
	}
	@Override
	public String toString() {
		return "Fish [FishID=" + FishID + ", FishClass=" + FishClass + ", Order=" + Order + ", Family=" + Family
				+ ", ScientificName=" + ScientificName + ", Species=" + Species + ", ToleranceGuild=" + ToleranceGuild
				+ ", TrophicGuild=" + TrophicGuild + ", HabitatGuild=" + HabitatGuild + ", InvasiveSpecies="
				+ InvasiveSpecies + ", EndemicSpecies=" + EndemicSpecies + ", EndangeredSpecies=" + EndangeredSpecies
				+ ", NaturalMonument=" + NaturalMonument + ", ImageLink=" + ImageLink + ", Description=" + Description
				+ ", Feature=" + Feature + "]";
	}
}
