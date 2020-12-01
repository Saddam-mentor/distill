package com.mentor.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.impl.Currnt_Cumulativ_TillNow_ReportImpl;

public class Currnt_Cumulativ_TillNow_ReportAction {

	Currnt_Cumulativ_TillNow_ReportImpl impl = new Currnt_Cumulativ_TillNow_ReportImpl();

	private boolean printFlag;
	private String pdfName;
	private String monthID;
	private ArrayList monthList = new ArrayList();
	private String year ;
	private ArrayList getAll_List = new ArrayList();
	private boolean validInput ;
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
//===========================================Methods=========================================	

	public ArrayList getGetAll_List() {
	 this.getAll_List=impl.yearListImpl(this);
		
		return getAll_List;
	}

	public void setGetAll_List(ArrayList getAll_List) {
		this.getAll_List = getAll_List;
	}
	public void setValidInput(boolean validInput) {
		this.validInput = validInput;
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

	public String getMonthID() {
		return monthID;
	}

	public void setMonthID(String monthID) {
		this.monthID = monthID;
	}

	public ArrayList getMonthList() {
		try {
			this.monthList=impl.getMonthList(this);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return monthList;
	}

	public void setMonthList(ArrayList monthList) {		
		this.monthList = monthList;
	}

	public void print() {
		
		try {
			impl.printReport(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;

	}
	
	//=====================================
		private Date start_dt;
		private Date end_dt ;
		
		
		public Date getStart_dt() {
			return start_dt;
		}

		public void setStart_dt(Date start_dt) {
			this.start_dt = start_dt;
		}

		public Date getEnd_dt() {
			return end_dt;
		}

		public void setEnd_dt(Date end_dt) {
			this.end_dt = end_dt;
		}
		


		
		
}
