package com.mentor.datatable;

public class Mrp_of_Brand_details_dt {
	
	//==================variable================================
	
	private int srNO ;
	private String unitName ;
	private String brandName ;
	private String size ;
	private double mrp ;
	private String renew;
	
	private String type;
	
	//========================getter and setter ===========================
	
	
	public String getRenew() {
		return renew;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setRenew(String renew) {
		this.renew = renew;
	}
	public int getSrNO() {
		return srNO;
	}
	public void setSrNO(int srNO) {
		this.srNO = srNO;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public double getMrp() {
		return mrp;
	}
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	

}
