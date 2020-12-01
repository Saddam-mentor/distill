package com.mentor.action;

import java.util.Date;

import com.mentor.impl.TotalLabelApprovedCategoryWiseImpl; 

public class TotalLabelApprovedCategoryWiseAction {
	
	

	
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private String excelName;
	
	
	
	public String getExcelName() {
		return excelName;
	}
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
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
	
	
	public void printReport()
	{
		
	try{
		new TotalLabelApprovedCategoryWiseImpl().printReport(this);
	}catch(Exception e)	
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
			e.printStackTrace();
		}
	}

	
	

}
