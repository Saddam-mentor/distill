package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.DetailsOfIUImpl;
import com.mentor.impl.StateWiseAlcoholEportReportImpl;

public class StateWiseAlcoholEportReportAction {
	
	StateWiseAlcoholEportReportImpl impl = new StateWiseAlcoholEportReportImpl();
	private Date fromDate;
	private Date toDate;
	private String radio="S";
	private String unitType="";
	private String pdf_name;
	private boolean printFlag;
	private String month_id;
	private String year_id;
	private String monthName;
	private ArrayList monthList = new ArrayList();
	
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getMonth_id() {  
		return month_id;
	}
	public String getYear_id() {
		return year_id;
	}
	public void setYear_id(String year_id) {
		this.year_id = year_id;
	}
	public void setMonth_id(String month_id) {
		this.month_id = month_id;
	}
	public ArrayList getMonthList() {
		this.monthList=impl.getMonthList(this);
		return monthList;
	}
	public void setMonthList(ArrayList monthList) {
		this.monthList = monthList;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
	public String getPdf_name() {
		return pdf_name;
	}
	public void setPdf_name(String pdf_name) {
		this.pdf_name = pdf_name;
	}
	
	public boolean isPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}
	public void radioListnr(ValueChangeEvent e) {

		try { 
			String id = (String)e.getNewValue();
			this.unitType=id;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public void printReport() {
		if(this.year_id!=null && this.year_id.length()>0){
		    if (this.month_id!=null && this.month_id.length()>0){
		    	 impl.getMonthName(this);
			   impl.printReport(this);
		    }else {
		    	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
		     			"Please Select Month  !!! ", "Please Select Month!!!"));
		    }
		}else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
		 			"Please Select Year !!! ", "Please Select Year!!!"));
		}
	}
	public void reset(){
		this.fromDate=null;
		this.toDate=null;
		this.radio="S";
		this.unitType="";
		this.printFlag=false;
		this.excelFlag=false;
	}
	private String exlname;
	private boolean excelFlag = false;
	
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
	public void excel() {

		if(this.year_id!=null && this.year_id.length()>0){
		    if (this.month_id!=null && this.month_id.length()>0){
		    	impl.getMonthName(this);
		    	impl.writeExcel(this);
		    }else {
		    	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
		     			"Please Select Month !!! ", "Please Select Month!!!"));
		    }
		}else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
		 			"Please Select Year !!! ", "Please Select Year!!!"));
		}
	
		
	
		
	}
}
