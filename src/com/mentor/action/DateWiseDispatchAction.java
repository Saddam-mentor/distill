package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.DateWiseDispatchImpl;

public class DateWiseDispatchAction {

	DateWiseDispatchImpl impl = new DateWiseDispatchImpl();

	private String pdfname;
	private boolean printFlag = false;
	private Date fromdate;
	private Date todate;
	
	private boolean excelFlag = false;
	private String exlname;
	
	
	
	
	private String pdfDraft;
	
	

	public String isPdfDraft() {
		return pdfDraft;
	}

	public void setPdfDraft(String pdfDraft) {
		this.pdfDraft = pdfDraft;
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

	

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public String getPdfname() {
		return pdfname;
	}
	

	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}

	public boolean isPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}

	

	

	public void print() {
		
		if(impl.printReportImpl(this)==true)
		{
			this.printFlag=true;
		}else
		{
			this.printFlag=false;
		}
	}

	public void excel() {
		impl.write(this);
	}

	public void reset() {

		this.printFlag = false;
		this.pdfname = "";
		this.fromdate = null;
		this.todate = null;
		
		this.excelFlag = false;
		this.exlname = null;
		this.fromdate = null;
		this.todate = null;
		
		
	}

	public void radioListiner(ValueChangeEvent e) {
		this.excelFlag = false;
		this.printFlag = false;
		this.pdfname = "";
		
		
		/*if (this.getRadioVal().equalsIgnoreCase("DW")) {
			this.drpdwnFlg = true;
		} else if (this.getRadioVal().equalsIgnoreCase("A")) {
			this.drpdwnFlg = false;
		}*/
	}

	

}
