package com.mentor.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.Fl2D_Stock_Register_Impl;

public class Fl2D_Stock_Register_Action {
	
	Fl2D_Stock_Register_Impl impl=new Fl2D_Stock_Register_Impl();

	private String hidden;
	public int fl2d_id;
	public String name;
	public String address = "NA";
	
	
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;

	private String radio;
	private String district_Name;
	

	




	public String getDistrict_Name() {
		return district_Name;
	}




	public void setDistrict_Name(String district_Name) {
		this.district_Name = district_Name;
	}




	public int getFl2d_id() {
		return fl2d_id;
	}




	public void setFl2d_id(int fl2d_id) {
		this.fl2d_id = fl2d_id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public void setHidden(String hidden) {
		this.hidden = hidden;
	}




	public String getHidden() {
		try {
			impl.getDetails(this);
		
		} catch (Exception e) {
		}
		return hidden;
	}
	
	public void dateCheck(ValueChangeEvent e) throws ParseException
	{
		Date s=(Date)e.getNewValue();
		
		/*String  fixDate="01-04-2020";
		
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(fixDate);*/ 
		
		
		  String sDate1="01/04/2020";  
		    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);  
		
		
		System.out.println("===selected date=="+s);
		
		System.out.println("=fix date==="+date1);
		
		if(s.before(date1))
		{
			FacesContext.getCurrentInstance().addMessage
			(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Date Should Not Less Than 1st April!!", "Date Should Not Less Than 1st April!!"));
		}
		
		
		
		
		
		
	}
	
	
	public Date getFromDate()
	{
		
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

	

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}


	public void radioListener(ValueChangeEvent e) {

		this.printFlag = false;
		
	}

	public void print() 
	{
		
		Date d=this.toDate;
		
		
		
		
		
		if(this.toDate.after(fromDate))
		{
			 
			impl.printReport(this);	
		}
		if(this.toDate.equals(fromDate))
		{
			 
			impl.printReport(this);	
		}
		
		
		if(this.toDate.before(fromDate))
		{
			 	
			FacesContext.getCurrentInstance().addMessage
			(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"From Date Should Be Less Than To Date!!",
							"From Date Should Be Less Than To Date!!"));
		}
		
	}

	

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
	

	}

}
