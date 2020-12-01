package com.mentor.action;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.ChallanDepositDetailSuccessRptImpl;

public class ChallanDepositDetailSuccessRptAction {

	ChallanDepositDetailSuccessRptImpl impl = new ChallanDepositDetailSuccessRptImpl();

	private String hidden;
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private String radioType = "ALL";
	private String exlname;
    private boolean excelFlag; 

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

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
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

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}

	public void radioListener(ValueChangeEvent e) {

		try {
			String val = (String) e.getNewValue();
			this.setRadioType(val);
			this.printFlag = false;
			this.excelFlag = false;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void print() {

		try {
			if (this.fromDate != null && this.toDate != null) {
				impl.printReport(this);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
				" Please Select Dates !!"," Please Select Dates !!"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	
	public void excel(){
		
		try {
			if (this.fromDate != null && this.toDate != null) {
				impl.printExcel(this);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
				" Please Select Dates !!"," Please Select Dates !!"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
			

	public void reset() {
		this.printFlag = false;
		this.excelFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
		this.radioType = "ALL";

	}

}




