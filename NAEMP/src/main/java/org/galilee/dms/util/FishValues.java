package org.galilee.dms.util;

import java.util.List;

public class FishValues {
	private String[] predicates;
	private List<double[]> rowCollection;
	
	public FishValues(){}

	public String[] getPredicates() {
		return predicates;
	}

	public void setPredicates(String[] predicates) {
		this.predicates = predicates;
	}

	public List<double[]> getRowCollection() {
		return rowCollection;
	}

	public void setRowCollection(List<double[]> rowCollection) {
		this.rowCollection = rowCollection;
	}
}
