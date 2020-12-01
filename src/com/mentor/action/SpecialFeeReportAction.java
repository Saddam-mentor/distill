package com.mentor.action;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.impl.SpecialFeeReportImpl;

public class SpecialFeeReportAction {
	
	
	
private String selectRadio="B";
private Date fromDate;
private Date toDate;
private boolean printFlag;
private String pdfName;



public String getPdfName() {
	return pdfName;
}

public void setPdfName(String pdfName) {
	this.pdfName = pdfName;
}

public boolean isPrintFlag() {
	return printFlag;
}

public void setPrintFlag(boolean printFlag) {
	this.printFlag = printFlag;
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

public String getSelectRadio() {
	return selectRadio;
}

public void setSelectRadio(String selectRadio) {
	this.selectRadio = selectRadio;
}


public void printReport()
{
try{
	if((this.fromDate!=null && this.toDate!=null)&&(this.toDate.after(fromDate)))
	{
	new SpecialFeeReportImpl().printReportPDF(this);
	}else{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Select Date", "Please Select Date"));
	}
}	catch(Exception e)
{
	
e.printStackTrace();
}

}
public void printReport2020()
{
try{
	if((this.fromDate!=null && this.toDate!=null)&&(this.toDate.after(fromDate)))
	{
	new SpecialFeeReportImpl().printReportPDF2020(this);
	}else{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Select Date", "Please Select Date"));
	}
}	catch(Exception e)
{
	
e.printStackTrace();
}

}
public void reset()
{
	
try{
	
	this.fromDate=null;
	
	this.toDate=null;
	this.printFlag=false;
	
}catch(Exception e)
{
	e.printStackTrace();}

}



}
