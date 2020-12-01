package com.mentor.action;

import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.OccasionalBarStatusImpl;

public class OccasionalBarStatusAction {
	
	
	OccasionalBarStatusImpl impl = new OccasionalBarStatusImpl();
	
	
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private boolean excelFlag = false;	
	private String exlname;

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

	public void radioListener(ValueChangeEvent e) {

		this.printFlag = false;
		this.excelFlag = false;
	}

	public void print() {
		impl.printReport(this);
	}

	public void excel() {
		//impl.writeExcel(this);
	}

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
		this.exlname = null;	
		this.excelFlag = false;

	}

}
