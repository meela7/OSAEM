package com.naemp.osaem.model;

public class Method {
	
	/**
	 * Class Name: Method.java 
	 * Description: Hibernate Mapping pojo File
	 * 
	 * @author Meilan Jiang
	 * @since 2016.02.04
	 * @version 1.0
	 * 
	 * Copyright(c) 2016 by CILAB All right reserved.
	 */
	private int MethodID ;	
	private String MethodName;
	private String MethodLink;
	
	public int getMethodID() {
		return MethodID;
	}
	public void setMethodID(int methodID) {
		MethodID = methodID;
	}
	public String getMethodName() {
		return MethodName;
	}
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	public String getMethodLink() {
		return MethodLink;
	}
	public void setMethodLink(String methodLink) {
		MethodLink = methodLink;
	}
	
	@Override
	public String toString() {
		return "Method [MethodID=" + MethodID + ", MethodName=" + MethodName + ", MethodLink=" + MethodLink + "]";
	}

}
