package com.mentor.action;


import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.arjuna.ats.arjuna.tools.report;
import com.mentor.impl.Report_On_DispatchesImpl;


public class Report_On_Dispatches_Action {

	Report_On_DispatchesImpl impl = new Report_On_DispatchesImpl();

	private Date formdate;
	private Date todate;
	public Date getFormdate() {
		return formdate;
	}

	public void setFormdate(Date formdate) {
		this.formdate = formdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	private String printPDF;
	private String Generate_Excle;
	private String radio = "FL2";
	private String exlname;
	private boolean excelFlag;
	private String firm_name;
	private int int_id;
	private String hidden;
	public ArrayList getRequestList() {
		return requestList;
	}

	public void setRequestList(ArrayList requestList) {
		this.requestList = requestList;
	}


	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}

	private boolean printFlag;
	private String pdfName;
	private String prt;

	private boolean viewFlag;
	private ArrayList requestList;

	public boolean isViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(boolean viewFlag) {
		this.viewFlag = viewFlag;
	}



	public String getPrt() {
		return prt;
	}

	public void setPrt(String prt) {
		this.prt = prt;
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

	public String getHidden() {
		try {
			impl.getdata(this);
		} catch (Exception e) {
			e.printStackTrace();
		}


		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getFirm_name() {
		return firm_name;
	}

	public void setFirm_name(String firm_name) {
		this.firm_name = firm_name;
	}

	public int getInt_id() {
		return int_id;
	}

	public void setInt_id(int int_id) {
		this.int_id = int_id;
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

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}




	public String getPrintPDF() {
		return printPDF;
	}

	public void setPrintPDF(String printPDF) {
		this.printPDF = printPDF;
	}

	public String getGenerate_Excle() {
		return Generate_Excle;
	}

	public void setGenerate_Excle(String generate_Excle) {
		Generate_Excle = generate_Excle;
	}

	public Report_On_DispatchesImpl getImpl() {
		return impl;
	}

	public void setImpl(Report_On_DispatchesImpl impl) {
		this.impl = impl;
	}

	public void print() {

		try {
			if (this.formdate != null && this.todate != null) {
				impl.printReport(this);
			} else {
				/*FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								" Please Select Dates !!",
								" Please Select Dates !!"));*/


				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" Please Select Dates !!", " Please Select Dates !!"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void excel() {

		try {
			if (this.formdate != null && this.todate != null) {
				impl.excelMrpBrandDetails(this);
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								" Please Select Dates !!",
								" Please Select Dates !!"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}


	}
	public void reset(){
		this.formdate = null;
		this.todate = null;
		this.pdfName = null;
		this.exlname = null;
		this.radio = null;
		//this.excelFlag = false;
		//this.printFlag = false ;
		
	}
}



