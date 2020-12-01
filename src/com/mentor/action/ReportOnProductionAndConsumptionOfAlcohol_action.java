package com.mentor.action;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.impl.ReportOnProductionAndConsumptionOfAlcohol_impl;

public class ReportOnProductionAndConsumptionOfAlcohol_action {
	

	
ReportOnProductionAndConsumptionOfAlcohol_impl impl = new ReportOnProductionAndConsumptionOfAlcohol_impl();
	
private ArrayList month = new ArrayList();
private String exlname; 
private String montth ;
private boolean excelFlag = false;
private String month_name ;
private boolean printFlag;
private String pdfName;
private String year_name ;
private String year_value;
private ArrayList year = new ArrayList();



public void setYear(ArrayList year) {
	this.year = year;
}

public String getYear_value() {
	return year_value;
}

public void setYear_value(String year_value) {
	this.year_value = year_value;
}

public String getYear_name() {
	return year_name;
}

public void setYear_name(String year_name) {
	this.year_name = year_name;
}

public boolean isPrintFlag() {
	return printFlag;
}

public void setPrintFlag(boolean printFlag) {
	this.printFlag = printFlag;
}

public String getPdfName() {
	return pdfName;
}

public void setPdfName(String pdfName) {
	this.pdfName = pdfName;
}

public String getMonth_name() {
	return month_name;
}

public void setMonth_name(String month_name) {
	this.month_name = month_name;
}

public String getExlname() {
	return exlname;
}

public void setExlname(String exlname) {
	this.exlname = exlname;
}

public boolean isExcelFlag() {
	return excelFlag;
}

public void setExcelFlag(boolean excelFlag) {
	this.excelFlag = excelFlag;
}

public String getMontth() {
	return montth;
}

public void setMontth(String montth) {
	this.montth = montth;
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
public void excel()
{
	if(this.year_value!=null &&this.year_value.length()>0)	{
  if(this.montth!=null &&this.montth.length()>0)	
	{
	  impl.month_name(this);
	 impl.generateexcel(this);	
	}
  else
	{
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Select Month !!! ", "Select Month !!!"));
	}}
	else{
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Select Year !!! ", "Select Year !!!"));
	}
	
}

public void print_pdf() {
	
	impl.print_pdf(this);
	}



//===================== year method==================================================


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


public void reset ()
{

  this.year.clear();
 
  this.month.clear();
  
  this.montth = null ;
  
  this.year_value = null;
  this.pdfName = null ;
  this.exlname = null ;
  this.printFlag = false ;
  this.excelFlag = false ;
  
}

}
