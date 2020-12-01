package com.mentor.action;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.impl.productionandconsumptionofalcohol_impl;

public class productionandconsumptionofalcohol_action {
	
productionandconsumptionofalcohol_impl impl = new productionandconsumptionofalcohol_impl();
	
private String hidden;	
private int loginUserId;
private String loginUserNm;
private String loginUserAdrs;
private String loginUserType;

public String getHidden() {
	
	try{
		 impl.getUserDetails(this);
		
	if(this.yearr!=null && this.yearr.length()>0 && this.montth!=null &&this.montth.length()>0 && this.spriit_type!=null )
	{
		impl.getdata(this);
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return hidden;
}

public void setHidden(String hidden) {
	this.hidden = hidden;
}
public int getLoginUserId() {
	return loginUserId;
}

public void setLoginUserId(int loginUserId) {
	this.loginUserId = loginUserId;
}

public String getLoginUserNm() {
	return loginUserNm;
}

public void setLoginUserNm(String loginUserNm) {
	this.loginUserNm = loginUserNm;
}

public String getLoginUserAdrs() {
	return loginUserAdrs;
}

public void setLoginUserAdrs(String loginUserAdrs) {
	this.loginUserAdrs = loginUserAdrs;
}

public String getLoginUserType() {
	return loginUserType;
}

public void setLoginUserType(String loginUserType) {
	this.loginUserType = loginUserType;
}

//==============================dropdown

private ArrayList year = new ArrayList();

private ArrayList month = new ArrayList();

private String yearr ;

private String montth ;

private String spriit_type ;

public String getYearr() {
	return yearr;
}

public void setYearr(String yearr) {
	this.yearr = yearr;
}

public String getMontth() {
	return montth;
}

public void setMontth(String montth) {
	this.montth = montth;
}

public String getSpriit_type() {
	return spriit_type;
}

public void setSpriit_type(String spriit_type) {
	this.spriit_type = spriit_type;
}

public ArrayList getYear() 
{
	try 
	{
		 this.year=impl.yearListImpl(this);
	}
	catch (Exception e) 
	{			
		e.printStackTrace();
	}
	return year;
}

public void setYear(ArrayList year) {
	this.year = year;
}

public ArrayList getMonth() {
	try {
		this.month=impl.getMonthList(this);
	}
	catch (Exception e)
	{			
		e.printStackTrace();
	}
	return month;
}

public void setMonth(ArrayList month) {
	this.month = month;
}
//===================Variables

private double production_bl = 0.0 ;  
private double production_al = 0.0 ;

private double import_outofstate_bl = 0.0 ;
private double import_outofstate_al = 0.0 ;

private double import_outofindia_bl = 0.0 ;
private double import_outofindia_al = 0.0 ;

private double consumption_bl = 0.0 ;
private double consumption_al = 0.0 ;

private double saleinup_drink_bl = 0.0 ;
private double saleinup_drink_al = 0.0 ;

private double saleinup_other_bl = 0.0 ;
private double saleinup_other_al = 0.0 ;

public double getProduction_bl() {
	return production_bl;
}

public void setProduction_bl(double production_bl) {
	this.production_bl = production_bl;
}

public double getProduction_al() {
	return production_al;
}

public void setProduction_al(double production_al) {
	this.production_al = production_al;
}

public double getImport_outofstate_bl() {
	return import_outofstate_bl;
}

public void setImport_outofstate_bl(double import_outofstate_bl) {
	this.import_outofstate_bl = import_outofstate_bl;
}

public double getImport_outofstate_al() {
	return import_outofstate_al;
}

public void setImport_outofstate_al(double import_outofstate_al) {
	this.import_outofstate_al = import_outofstate_al;
}

public double getImport_outofindia_bl() {
	return import_outofindia_bl;
}

public void setImport_outofindia_bl(double import_outofindia_bl) {
	this.import_outofindia_bl = import_outofindia_bl;
}

public double getImport_outofindia_al() {
	return import_outofindia_al;
}

public void setImport_outofindia_al(double import_outofindia_al) {
	this.import_outofindia_al = import_outofindia_al;
}

public double getConsumption_bl() {
	return consumption_bl;
}

public void setConsumption_bl(double consumption_bl) {
	this.consumption_bl = consumption_bl;
}

public double getConsumption_al() {
	return consumption_al;
}

public void setConsumption_al(double consumption_al) {
	this.consumption_al = consumption_al;
}

public double getSaleinup_drink_bl() {
	return saleinup_drink_bl;
}

public void setSaleinup_drink_bl(double saleinup_drink_bl) {
	this.saleinup_drink_bl = saleinup_drink_bl;
}

public double getSaleinup_drink_al() {
	return saleinup_drink_al;
}

public void setSaleinup_drink_al(double saleinup_drink_al) {
	this.saleinup_drink_al = saleinup_drink_al;
}

public double getSaleinup_other_bl() {
	return saleinup_other_bl;
}

public void setSaleinup_other_bl(double saleinup_other_bl) {
	this.saleinup_other_bl = saleinup_other_bl;
}

public double getSaleinup_other_al() {
	return saleinup_other_al;
}

public void setSaleinup_other_al(double saleinup_other_al) {
	this.saleinup_other_al = saleinup_other_al;
}

public double getSaleoutstate_drink_bl() {
	return saleoutstate_drink_bl;
}

public void setSaleoutstate_drink_bl(double saleoutstate_drink_bl) {
	this.saleoutstate_drink_bl = saleoutstate_drink_bl;
}

public double getSaleoutstate_drink_al() {
	return saleoutstate_drink_al;
}

public void setSaleoutstate_drink_al(double saleoutstate_drink_al) {
	this.saleoutstate_drink_al = saleoutstate_drink_al;
}

public double getSaleoutstate_other_bl() {
	return saleoutstate_other_bl;
}

public void setSaleoutstate_other_bl(double saleoutstate_other_bl) {
	this.saleoutstate_other_bl = saleoutstate_other_bl;
}

public double getSaleoutstate_other_al() {
	return saleoutstate_other_al;
}

public void setSaleoutstate_other_al(double saleoutstate_other_al) {
	this.saleoutstate_other_al = saleoutstate_other_al;
}

public double getSaleoutcountry_drink_bl() {
	return saleoutcountry_drink_bl;
}

public void setSaleoutcountry_drink_bl(double saleoutcountry_drink_bl) {
	this.saleoutcountry_drink_bl = saleoutcountry_drink_bl;
}

public double getSaleoutcountry_drink_al() {
	return saleoutcountry_drink_al;
}

public void setSaleoutcountry_drink_al(double saleoutcountry_drink_al) {
	this.saleoutcountry_drink_al = saleoutcountry_drink_al;
}

public double getSaleoutcountry_other_bl() {
	return saleoutcountry_other_bl;
}

public void setSaleoutcountry_other_bl(double saleoutcountry_other_bl) {
	this.saleoutcountry_other_bl = saleoutcountry_other_bl;
}

public double getSaleoutcountry_other_al() {
	return saleoutcountry_other_al;
}

public void setSaleoutcountry_other_al(double saleoutcountry_other_al) {
	this.saleoutcountry_other_al = saleoutcountry_other_al;
}

public double getWastage_bl() {
	return wastage_bl;
}

public void setWastage_bl(double wastage_bl) {
	this.wastage_bl = wastage_bl;
}

public double getWastage_al() {
	return wastage_al;
}

public void setWastage_al(double wastage_al) {
	this.wastage_al = wastage_al;
}
private double saleoutstate_drink_bl = 0.0 ;
private double saleoutstate_drink_al = 0.0  ;

private double saleoutstate_other_bl = 0.0  ;
private double saleoutstate_other_al = 0.0;

private double saleoutcountry_drink_bl = 0.0 ;
private double saleoutcountry_drink_al = 0.0 ;

private double saleoutcountry_other_bl = 0.0;
private double saleoutcountry_other_al = 0.0 ;

private double wastage_bl = 0.0 ;
private double wastage_al = 0.0 ;

//================save=============

public void save ()
{ 
	if(this.yearr!=null && this.yearr.length()>0)  
	{
	if(this.montth!=null &&this.montth.length()>0)	
	{
	if(this.spriit_type!=null)	
	{	
	 impl.save(this);
	}
	else
	{
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Select Spirit Type !!! ", "Select Spirit Type !!!"));
	}
	}
	else
	{
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Select Month !!! ", "Select Month !!!"));
	}
	}
	else
	{
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Select Year !!! ", "Select Year !!!"));
	}
}
	


//===================reset============

public void reset ()
{
  this.production_bl = 0.0 ;
  this.production_al = 0.0 ;
  
  this.import_outofindia_bl = 0.0 ;
  this.import_outofindia_al = 0.0 ;
  
  this.import_outofstate_bl = 0.0 ;
  this.import_outofstate_al = 0.0 ;
  
  this.consumption_bl = 0.0 ;
  this.consumption_al = 0.0 ;
  
  this.saleinup_drink_bl = 0.0 ;
  this.saleinup_drink_al = 0.0 ;
  
  this.saleinup_other_bl = 0.0 ;
  this.saleinup_other_al = 0.0 ;
  
  this.saleoutstate_drink_bl = 0.0 ;
  this.saleoutstate_drink_al = 0.0 ;
  
  this.saleoutstate_other_bl = 0.0 ;
  this.saleoutstate_other_al = 0.0 ;
  
  this.saleoutcountry_drink_bl = 0.0 ;
  this.saleoutcountry_drink_al = 0.0 ;
  
  this.saleoutcountry_other_bl = 0.0 ;
  this.saleoutcountry_other_al = 0.0 ;
  
  this.wastage_bl = 0.0 ;
  this.wastage_al = 0.0 ;
  
  this.year.clear();
  
  this.yearr = null ;
  
  this.month.clear();
  
  this.montth = null ;
  
  this.spriit_type = null ;

}

public void clear()
{
	this.production_bl = 0.0 ;
	  this.production_al = 0.0 ;
	  
	  this.import_outofindia_bl = 0.0 ;
	  this.import_outofindia_al = 0.0 ;
	  
	  this.import_outofstate_bl = 0.0 ;
	  this.import_outofstate_al = 0.0 ;
	  
	  this.consumption_bl = 0.0 ;
	  this.consumption_al = 0.0 ;
	  
	  this.saleinup_drink_bl = 0.0 ;
	  this.saleinup_drink_al = 0.0 ;
	  
	  this.saleinup_other_bl = 0.0 ;
	  this.saleinup_other_al = 0.0 ;
	  
	  this.saleoutstate_drink_bl = 0.0 ;
	  this.saleoutstate_drink_al = 0.0 ;
	  
	  this.saleoutstate_other_bl = 0.0 ;
	  this.saleoutstate_other_al = 0.0 ;
	  
	  this.saleoutcountry_drink_bl = 0.0 ;
	  this.saleoutcountry_drink_al = 0.0 ;
	  
	  this.saleoutcountry_other_bl = 0.0 ;
	  this.saleoutcountry_other_al = 0.0 ;
	  
	  this.wastage_bl = 0.0 ;
	  this.wastage_al = 0.0 ;	


}

}
