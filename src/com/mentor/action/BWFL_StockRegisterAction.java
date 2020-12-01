package com.mentor.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.BWFL_StockRegisterImpl;
import com.mentor.impl.BWFL_StockRegisterImpl;

public class BWFL_StockRegisterAction {

	BWFL_StockRegisterImpl impl = new BWFL_StockRegisterImpl();

	private Date dtDate;
	private boolean printFlag;
	private String pdfname;
	private String type;
 	private boolean excelFlag = false;
	private String exlname;
	private Date fromDate;
	private Date toDate;
 	 	private ArrayList distList = new ArrayList();
 private String licence_type;
	private int int_id;
	private String licence_no;
	private String applicant_name;
	 private int districtId;
	 
	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getLicence_type() {
		return licence_type;
	}

	public void setLicence_type(String licence_type) {
		this.licence_type = licence_type;
	}

	public int getInt_id() {
		return int_id;
	}

	public void setInt_id(int int_id) {
		this.int_id = int_id;
	}

	public String getLicence_no() {
		return licence_no;
	}

	public void setLicence_no(String licence_no) {
		this.licence_no = licence_no;
	}

	public String getApplicant_name() {
		return applicant_name;
	}

	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}

	 

	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}



	public ArrayList getDistList() {
		return distList;
	}

	public void setDistList(ArrayList distList) {
		this.distList = distList;
	}

	public void distBrewId(ValueChangeEvent vce) {
		this.printFlag = false;
		this.excelFlag = false;

	} 
	public Date getFromDate() {
		


		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {

			Date appdate = formatter.parse("01-04-2020");
			this.fromDate = appdate;
		} catch (ParseException e) 
		{
		
			e.printStackTrace();

		}
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

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(Date dtDate) {
		this.dtDate = dtDate;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

 

	public void radioListener2(ValueChangeEvent e) {
		this.printFlag = false;
		this.excelFlag = false;

	}

	public void radioListener(ValueChangeEvent e) {

		this.printFlag = false;
		this.excelFlag = false;

	}

	public void print() {
		if (isValidate()) {

			impl.printReport(this);

		}
	}

	public boolean validate;

	public boolean isValidate() {
		boolean validateFlag = true;
		try {
			if (this.fromDate == null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("Select Form Date ",
								"Select Form Date"));
				validateFlag = false;
			}
			if (this.toDate == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Select To Date ", "Select To Date"));
				validateFlag = false;
			}
			
			//------------------------


			DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
			String st = "01/04/2020";
			 	Date april_dt = new SimpleDateFormat("dd/MM/yyyy").parse(st);
			 	Date from_date = new SimpleDateFormat("dd/MM/yyyy").parse(df.format(this.fromDate)); 
			//System.out.println("from_date="+from_date);
			//System.out.println("april_dt="+april_dt);
			if (from_date.before(april_dt)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("From Date Can't be before 01-04-2020", "From Date Can't be before 01-04-2020"));
				validateFlag = false;
			 } 
		} catch (Exception e) {
			e.getStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));

		}
		return validateFlag;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public void excel() {
		if (isValidate()) { 
		}
	}

	public void reset() {
		this.printFlag = false;
		this.pdfname = null;
		this.dtDate = null;
		this.excelFlag = false;
		this.exlname = null;
		this.type = null;
		this.fromDate=null;
		this.toDate=null;

	}
}
