package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.WholesaleStockImpl;

public class WholesaleStockAction {

	WholesaleStockImpl impl = new WholesaleStockImpl();

	private String radio = "CD";
	private Date dtDate;
	private boolean printFlag;
	private String pdfname;
	private String type;
	private String district;
	private ArrayList districtList = new ArrayList();
	private boolean excelFlag = false;
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

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public ArrayList getDistrictList() {
		this.districtList = impl.getDistList();
		return districtList;
	}

	public void setDistrictList(ArrayList districtList) {
		this.districtList = districtList;
	}

	public void radioListener(ValueChangeEvent e) {

		this.printFlag = false;
		this.excelFlag = false;

	}

	public void print() {

		if (this.radio.equalsIgnoreCase("CD")) {
			impl.printReportConsolidated(this);
		} else {
			if (this.type.equalsIgnoreCase("CL2")) {
				impl.printReportCl2(this);
			} else if (this.type.equalsIgnoreCase("FL2")) {
				impl.printReportFl2(this);
			} else if (this.type.equalsIgnoreCase("FL2B")) {
				impl.printReportFl2B(this);
			} else if (this.type.equalsIgnoreCase("Select")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Select Type ", "Select Type"));
			}

		}

	}

	public void excel() {
		if (this.radio.equalsIgnoreCase("CD")) {
			impl.writeConsolidated(this);
		} else {
			if (this.type.equalsIgnoreCase("CL2")) {
				impl.writeCL2(this);
			} else if (this.type.equalsIgnoreCase("FL2")) {
				impl.writeFL2(this);
			} else if (this.type.equalsIgnoreCase("FL2B")) {
				impl.writeFL2B(this);
			} else if (this.type.equalsIgnoreCase("Select")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Select Type ", "Select Type"));
			}

		}
	}

	public void reset() {
		this.printFlag = false;
		this.pdfname = null;
		this.dtDate = null;
		this.radio = "CD";
		this.excelFlag = false;
		this.exlname = null;
		this.type = null;
		this.district = null;
		

	}
}
