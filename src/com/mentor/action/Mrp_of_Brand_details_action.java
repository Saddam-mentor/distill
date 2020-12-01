package com.mentor.action;

import java.util.ArrayList;
import javax.faces.event.ValueChangeEvent;

import org.omg.Security.Clearance;

import com.mentor.impl.Mrp_of_Brand_details_impl;

//=====================Prasoon 08-04-2020==================================

public class Mrp_of_Brand_details_action {
	Mrp_of_Brand_details_impl impl = new Mrp_of_Brand_details_impl();
	
//==============================variable=============================
	private String radio = "CL";
	ArrayList showData = new ArrayList();
	
	private boolean excelFlag = false;
	private String exlname;
	
//==========================methods==========================================
	
	public String getRadio() {      
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public ArrayList getShowData() {
		this.showData =  impl.display_datatable(this);	
		return showData;
	}
	public void setShowData(ArrayList showData) {
		this.showData = showData;
	}
	public boolean isExcelFlag() {
		return excelFlag;
	}
	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}
	public String getExlname() {
		return exlname;
	}
	public void setExlname(String exlname) {
		this.exlname = exlname;
	}
	

//============================methods===========================================
	
	
	public void changeRadio(ValueChangeEvent vce){
		
		 String val = (String) vce.getNewValue();	 
		 this.setRadio(val);
		 this.showData =  impl.display_datatable(this);
	}
	
	
	//========================Excel Print ==========================================
	
	public void excel() {
		  
		System.out.println("---- excel--- print---- ");
			impl.excelMrpBrandDetails(this);

	}
	
	//============================Reset==============================================
	
	public void reset(){
		System.out.println(" ---- reset button----");
		this.exlname=null ;	
	}
	
}
