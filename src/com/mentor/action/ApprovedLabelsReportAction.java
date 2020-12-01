package com.mentor.action;

import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.ApprovedLabelsReportImpl;

public class ApprovedLabelsReportAction {
	
	ApprovedLabelsReportImpl impl = new ApprovedLabelsReportImpl();

	private boolean printFlag;
	private String pdfName;
	private String radio="CL";

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
	
	public void print() {
		
		if(this.radio.equalsIgnoreCase("Beer"))
		{
			impl.printReportBeer(this);
		}else{
			impl.printReport(this);
		}
		
	}

	
	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.radio="CL";


	}

}
