package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.SaleInCasesImpl;

public class SaleInCasesAction {
	
	SaleInCasesImpl impl = new SaleInCasesImpl ();

	private Date date;
	private boolean printFlag;
	private String pdfName;
	private boolean excelFlag = false;
	private String radio;
	private String exlname;
	

	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	 public void print(){
		impl.getDetails(this);
		Date st = this.getStart_dt();
		Date et = this.getEnd_dt();
		//System.out.println("=====this.getStart_dt()======="+this.getStart_dt());
		//System.out.println("=========this.getEnd_dt()================"+this.getEnd_dt());
		if(this.getDate().before(st) || this.getDate().after(et))
		{	
		   FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Date should not be less than '"+ this.getStart_dt()+ "' And more than '"+ this.getEnd_dt()+ "' ",
								"Date should not be less than '"+ this.getStart_dt()+ "' And more than '"+ this.getEnd_dt()+ "' "));
		//System.out.println("======if(this.fromDate.before(st) && this.toDate.after(et))======"+(this.fromDate.before(st) && this.toDate.after(et)));
	   }
		else
			
		{
		impl.printReport(this);
		
			}
	}
     
	public void excel() {
		//impl.writeExcel(this);
	}

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.date = null;
		this.exlname = null;

	}
	//==================================================
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
		private String Year;
		public String getYear() {
			return Year;
		}

		public void setYear(String year) {
			Year = year;
		}
	ArrayList getAll_List=new ArrayList();
		
		
		public ArrayList getGetAll_List() {
			
		this.getAll_List=impl.yearListImpl(this);
		
			return getAll_List;
		}
	  
		public void setGetAll_List(ArrayList getAll_List) {
			this.getAll_List = getAll_List;
		}	
	
}
