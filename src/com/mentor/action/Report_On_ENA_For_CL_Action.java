package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import com.mentor.impl.Report_On_ENA_For_CL_Impl;

public class Report_On_ENA_For_CL_Action {
	Report_On_ENA_For_CL_Impl impl=new Report_On_ENA_For_CL_Impl();

	private String pdfname;
	private boolean printFlag=false;
	private Date fromdate;
	private Date todate;
	
	private ArrayList bwfl_FL2d_List=new ArrayList();
	

	
	public Report_On_ENA_For_CL_Impl getImpl() {
		return impl;
	}



	public void setImpl(Report_On_ENA_For_CL_Impl impl) {
		this.impl = impl;
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



	public ArrayList getBwfl_FL2d_List() {
		return bwfl_FL2d_List;
	}



	public void setBwfl_FL2d_List(ArrayList bwfl_FL2d_List) {
		this.bwfl_FL2d_List = bwfl_FL2d_List;
	}



	public void print()
	{
		
			impl.print(this);
	
		
	}
	
	
	
	

}
