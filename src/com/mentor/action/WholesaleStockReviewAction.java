package com.mentor.action;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.WholesaleStockReviewImpl;

public class WholesaleStockReviewAction {

	WholesaleStockReviewImpl impl = new WholesaleStockReviewImpl();

	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private boolean excelFlag = false;
	private String radio = "FL2";
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

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
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

		if (this.radio.equalsIgnoreCase("CL2")) {
			impl.printReportCl2(this);
		} else if (this.radio.equalsIgnoreCase("FL2")) {
			impl.printReportFl2(this);
		} else if (this.radio.equalsIgnoreCase("FL2B")) {
			impl.printReportFl2B(this);
		}

	}

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
		this.exlname = null;
		this.excelFlag=false;

	}

	public void excel() {


		if (this.radio.equalsIgnoreCase("CL2")) {
			impl.writeCL2(this);
		} else if (this.radio.equalsIgnoreCase("FL2")) {
			impl.writeFL2(this);
		} else if (this.radio.equalsIgnoreCase("FL2B")) {
			impl.writeFL2B(this);
		}else if (this.radio.equalsIgnoreCase("Select")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Select Type ", "Select Type"));
		}

	
		
		 
	}
}
