package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.StockAtWholeSellersImpl;
import com.mentor.impl.StockAtWholeSellersImpl;
import com.mentor.utility.ResourceUtil;

public class StockAtWholeSellersAction {

	StockAtWholeSellersImpl impl = new StockAtWholeSellersImpl();

	private Date dtDate;
	private boolean printFlag;
	private String pdfname;
	private String type;
	private ArrayList districtList = new ArrayList();
	private boolean excelFlag = false;
	private String exlname;
	private Date fromDate;
	private Date toDate;
	private String distid;
	private String hidden;
	private ArrayList distList = new ArrayList();
	private boolean districtFlag;
	private String licence_type;
	private int int_id;
	private String licence_no;
	private String applicant_name;
	private String district;
	private int districtId;
	private String radio = "FL2";
	

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
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

	public boolean isDistrictFlag() {
		return districtFlag;
	}

	public void setDistrictFlag(boolean districtFlag) {
		this.districtFlag = districtFlag;
	}

	public String getHidden() {
		try {
			impl.getDetails(this);
			if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10)
					.equalsIgnoreCase("Excise-DEO")) {
				this.districtFlag = false;
			} else {
				this.districtFlag = true;
			}
		} catch (Exception ex) {
			ex.getMessage();
		}
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
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

	public String getDistid() {
		return distid;
	}

	public void setDistid(String distid) {
		this.distid = distid;
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

	public ArrayList getDistrictList() {
		this.districtList = impl.getDistList();
		return districtList;
	}

	public void setDistrictList(ArrayList districtList) {
		this.districtList = districtList;
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

		} catch (Exception e) {
			e.getStackTrace();
		}
		return validateFlag;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public void excel() {
		if (isValidate()) {/*
							 * if (this.vch.equalsIgnoreCase("D")) {
							 * impl.writeDistillery(this); } else if
							 * (this.vch.equalsIgnoreCase("B")) {
							 * impl.writeBrewary(this); } else if
							 * (this.vch.equalsIgnoreCase("BWFL")) {
							 * impl.writeBWFL(this); } else if
							 * (this.vch.equalsIgnoreCase("FL2D")) {
							 * impl.writeFL2D(this); } else if
							 * (this.vch.equalsIgnoreCase("")) {
							 * FacesContext.getCurrentInstance
							 * ().addMessage(null, new
							 * FacesMessage("Select Type ", "Select Type")); }
							 */
		}
	}

	public void reset() {
		this.printFlag = false;
		this.pdfname = null;
		this.dtDate = null;
		this.excelFlag = false;
		this.exlname = null;
		this.type = null;

	}
}
