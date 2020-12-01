package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.DispatchReportDIST_BRE_OLDSTOCK_Impl;



public class DispatchReportDIST_BRE_OLDSTOCK_Action {


	DispatchReportDIST_BRE_OLDSTOCK_Impl impl = new DispatchReportDIST_BRE_OLDSTOCK_Impl();

	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfname;
	private boolean excelFlag = false;
	private String radio="CD";

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	private String exlname;

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

	public String getPdfname() {
		return pdfname;
	}

	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}

	public void print() {
		impl.printReport(this);
	}

	public void excel() {
		
		
		if (this.radio.equals("CD"))
		{
			impl.write(this);
		}
		else{
			impl.write_ditillery_wise(this);
		}
	}

	public void radioListener(ValueChangeEvent e) {
		
		this.printFlag = false;
		this.excelFlag = false;
	}
	
	public void reset() {
		this.printFlag = false;
		this.pdfname = null;
		this.fromDate = null;
		this.toDate = null;
	}

	// ------------------new code------------------------

	private int distilleryId;
	private int breweryId;
	private ArrayList distilleryList = new ArrayList();
	private ArrayList breweryList = new ArrayList();

	public int getDistilleryId() {
		return distilleryId;
	}

	public void setDistilleryId(int distilleryId) {
		this.distilleryId = distilleryId;
	}

	public int getBreweryId() {
		return breweryId;
	}

	public void setBreweryId(int breweryId) {
		this.breweryId = breweryId;
	}

	public ArrayList getDistilleryList() {
		try {
			this.distilleryList = impl.getDistilleryList(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return distilleryList;
	}

	public void setDistilleryList(ArrayList distilleryList) {
		this.distilleryList = distilleryList;
	}

	public ArrayList getBreweryList() {
		try {
			this.breweryList = impl.getBreweryList(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breweryList;
	}

	public void setBreweryList(ArrayList breweryList) {
		this.breweryList = breweryList;
	}


}
