package com.mentor.action;

import javax.faces.context.FacesContext;

import org.jboss.portal.portlet.impl.jsr168.api.ActionRequestImpl;

import com.mentor.impl.UnitImpl;

public class UnitAction {

	private boolean printFlag;
	private String printName;
	private String year;
	private boolean excelFlag = false;

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

	private String exlname;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
				this.printName = printName;
	}

	public boolean isPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}

	public void print() {
		System.out.println("Actioncheck");
		new UnitImpl().printReport(this);

		// sssreturn "";
	}

	public void excel() {
		new UnitImpl().writeExcel(this);
	}
}
