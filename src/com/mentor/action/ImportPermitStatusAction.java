package com.mentor.action;

import java.text.ParseException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.ImportPermitStatusImpl;

public class ImportPermitStatusAction {

	ImportPermitStatusImpl impl = new ImportPermitStatusImpl();

	private String hidden;
	private String radioType = "CD";
	private String unitType;
	private boolean printFlag;
	private String pdfName;

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
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

	public void typeListener(ValueChangeEvent e) {

		try {
			String val = (String) e.getNewValue();
			this.setUnitType(val);
			this.printFlag = false;
			this.excelFlag = false;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

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
			if (!this.unitType.equalsIgnoreCase("Select")) {
				if(this.getRadioType().equalsIgnoreCase("CD")){
					impl.printReportCD(this);
				}
				else if(this.getRadioType().equalsIgnoreCase("DT")){
					impl.printReportDTL(this);
				}
				 
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				" Select Unit Type !!"," Select Unit Type !!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excel() throws ParseException {
		try {
			if (!this.unitType.equalsIgnoreCase("Select")) {
				if(this.getRadioType().equalsIgnoreCase("CD")){
					impl.excelCD(this);
				}
				else if(this.getRadioType().equalsIgnoreCase("DT")){
					impl.excelDTL(this);
				}
				 
			} else {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
				" Select Unit Type !!"," Select Unit Type !!"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void reset() {

		this.printFlag = false;
		this.excelFlag = false;
		this.pdfName = null;
		this.unitType = null;
		this.radioType ="CD";

	}

}
