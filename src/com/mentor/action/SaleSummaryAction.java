package com.mentor.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.SaleSummaryImpl;
import com.mentor.impl.StockAtWholeSellersImpl;
import com.mentor.impl.StockAtWholeSellersImpl;
import com.mentor.utility.ResourceUtil;

public class SaleSummaryAction {

	SaleSummaryImpl impl = new SaleSummaryImpl();

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
	private String radio = "CL";

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

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

	public ArrayList getDistList() {/*
									 * 
									 * if (vch.equals("D")) { this.distList =
									 * impl.getChallanDataDist(); } else if
									 * (vch.equals("B")) { this.distList =
									 * impl.getChallanDataBrew(); } else if
									 * (vch.equals("BWFL")) { this.distList =
									 * impl.getChallanDataBWFL(); } else if
									 * (vch.equals("FL2D")) { this.distList =
									 * impl.getChallanDataFL2D(); }
									 */
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

	public void radioListener(ValueChangeEvent e) {

		try {
			String val = (String) e.getNewValue();
			this.setRadio(val);
			this.printFlag = false;
			this.excelFlag = false;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void print() {
		try {
			if (isValidate()) {
				impl.printReport(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			// ------------------
			/*
			 * if (this.radio.equalsIgnoreCase("CL2")) { SimpleDateFormat sdf =
			 * new SimpleDateFormat("yyyy-MM-dd"); Date date1 = null; try {
			 * date1 = sdf.parse("2018-10-22"); } catch (ParseException e1) { //
			 * TODO Auto-generated catch block e1.printStackTrace(); } Date
			 * date2 = getFromDate(); if (date1.compareTo(date2) > 0) {
			 * validateFlag = false;
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage( "From-Date can't be before 22 October 2018")); }
			 * 
			 * if (date1.compareTo(date2) <= 0) {
			 * FacesContext.getCurrentInstance().addMessage(null,new
			 * FacesMessage(" "));
			 * 
			 * } Date date3 = getToDate(); if (date1.compareTo(date3) > 0) {
			 * validateFlag = false;
			 * FacesContext.getCurrentInstance().addMessage( null, new
			 * FacesMessage( "To-Date can't be before 22 October 2018")); } }
			 */
			// ----------
		} catch (Exception e) {
			e.getStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));

		}
		return validateFlag;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public void excel() {
		try {
			if (isValidate()) {
				impl.writeExcel(this);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void reset() {
		this.printFlag = false;
		this.pdfname = null;
		this.dtDate = null;
		this.excelFlag = false;
		this.exlname = null;
		this.type = null;
		this.fromDate = null;
		this.toDate = null;

	}
}
