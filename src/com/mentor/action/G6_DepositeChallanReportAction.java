package com.mentor.action;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.G6_DepositeChallanReportImpl;

public class G6_DepositeChallanReportAction {
	
	private String districtID;
	private ArrayList districtList = new ArrayList();
	private String month;
	private boolean printFlag;
	private String pdfName;
	private String radio;
	private boolean districFlag;
	private String year="19";
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public boolean isDistricFlag() {
		return districFlag;
	}
	public void setDistricFlag(boolean districFlag) {
		this.districFlag = districFlag;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
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
	
	
	public String getDistrictID() {
		return districtID;
	}
	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}
	public ArrayList getDistrictList() {
		this.districtList=new G6_DepositeChallanReportImpl().getDistrictList();
		return districtList;
	}
	
	public void setDistrictList(ArrayList districtList) {
	
		this.districtList = districtList;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
	public void radioListener(ValueChangeEvent e) {

		this.printFlag = false;
	
	}
	
	
	
	
	public void print() {
		G6_DepositeChallanReportImpl impl=new G6_DepositeChallanReportImpl();
		
		if(this.radio !=null){

		if (this.radio.equalsIgnoreCase("DWR")) {
			impl.printDistrictWise(this);
		} else if (this.radio.equalsIgnoreCase("HWR")) {
			impl.printHeadWise(this);
		} else if (this.radio.equalsIgnoreCase("MWR")) {
			impl.printMonthWise(this);
		}

		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Select Radio Type !!", "Select Radio Type!!"));	
			
		}
		
	}

	
	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.radio=null;
		

	}

	
	

}
