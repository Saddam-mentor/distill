package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import com.mentor.impl.DispatchReportBWFL_FL2D_OLDSTOCK_Impl;

public class DispatchReportBWFL_FL2D_OLDSTOCK_Action {


	DispatchReportBWFL_FL2D_OLDSTOCK_Impl impl = new DispatchReportBWFL_FL2D_OLDSTOCK_Impl();

	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfname;
	private boolean excelFlag = false;
	private String radio="CD";
	private String exlname;
	private int bwflId;
	private int fl2DId;
	private String drpdown;
	private boolean drpFlg;
	private ArrayList bwflList = new ArrayList();
	private ArrayList fl2DList = new ArrayList();

	public boolean isDrpFlg() {
		return drpFlg;
	}

	public void setDrpFlg(boolean drpFlg) {
		this.drpFlg = drpFlg;
	}

	public String getDrpdown() {
		return drpdown;
	}

	public void setDrpdown(String drpdown) {
		this.drpdown = drpdown;
	}

	public String getExlname() {
		return exlname;
	}

	public void setExlname(String exlname) {
		this.exlname = exlname;
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

	public String getPdfname() {
		return pdfname;
	}

	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
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

	public int getBwflId() {
		return bwflId;
	}

	public void setBwflId(int bwflId) {
		this.bwflId = bwflId;
	}

	public int getFl2DId() {
		return fl2DId;
	}

	public void setFl2DId(int fl2dId) {
		fl2DId = fl2dId;
	}

	public ArrayList getBwflList() {
		return bwflList;
	}

	public void setBwflList(ArrayList bwflList) {
		this.bwflList = bwflList;
	}

	public ArrayList getFl2DList() {
		try {
			this.fl2DList = impl.getFL2DList(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fl2DList;
	}

	public void setFl2DList(ArrayList fl2dList) {
		fl2DList = fl2dList;
	}

	public void chngval(ValueChangeEvent e) {

		String val = (String) e.getNewValue();
		try {
			this.drpFlg = true;
			this.bwflList = impl.getBWFLList(this, val);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void radioListener(ValueChangeEvent e) {
		this.drpFlg = false;
		this.printFlag = false;
		this.excelFlag = false;
	}

	public void print() {
		if (this.radio != null) {
			impl.printReport(this);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Select Radio Type ","Select Radio Type"));
		}
	}

	public void excel() {
		if (this.radio != null) {
			if (this.radio.equals("CD")) {
				impl.write(this);
			} else {
				impl.writeNameWise(this);
			}
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Select Radio Type ","Select Radio Type"));
		}
	}

	public void reset() {
		this.printFlag = false;
		this.pdfname = null;
		this.fromDate = null;
		this.toDate = null;
		this.exlname = null;
		this.bwflId = 0;
		this.fl2DId = 0;
		this.radio = null;
		this.excelFlag = false;
	}

}
