package com.mentor.action;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.DetailsOfIUImpl;

public class DetailsOfIUAction {
	
	private Date fromDate;
	private Date toDate;
	private String radio="S";
	private String unitType="";
	private String pdf_name;
	private boolean printFlag;
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
		
			new DetailsOfIUImpl().printReport(this);
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
		
		new DetailsOfIUImpl().writeExcel(this);
		
	}
}
