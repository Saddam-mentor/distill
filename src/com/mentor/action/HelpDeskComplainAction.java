package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.HelpDeskComplainImpl;


public class HelpDeskComplainAction {

	
	HelpDeskComplainImpl impl = new HelpDeskComplainImpl();

	private String hidden;
	private int loginUserId;
	private String loginUserNm;
	private String loginUserAdrs;
	private String loginUserType;
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private String prblmType;
	private String radioType="P";
	
	public String getHidden() {
		try{
			//impl.getUserDetails(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public int getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserNm() {
		return loginUserNm;
	}

	public void setLoginUserNm(String loginUserNm) {
		this.loginUserNm = loginUserNm;
	}

	public String getLoginUserAdrs() {
		return loginUserAdrs;
	}

	public void setLoginUserAdrs(String loginUserAdrs) {
		this.loginUserAdrs = loginUserAdrs;
	}

	public String getLoginUserType() {
		return loginUserType;
	}

	public void setLoginUserType(String loginUserType) {
		this.loginUserType = loginUserType;
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

	
	public String getPrblmType() {
		return prblmType;
	}

	public void setPrblmType(String prblmType) {
		this.prblmType = prblmType;
	}

	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}
	
	public void radioListener(ValueChangeEvent e) {

		String val = (String) e.getNewValue();
		this.setRadioType(val);
		this.printFlag = false;
		
	}
	
	public void drpdwnListener(ValueChangeEvent e) {

		String val = (String) e.getNewValue();
		this.setPrblmType(val);
		this.printFlag = false;
		
	}

	public void print() {
		try{
			
			impl.printReport(this);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
		this.prblmType = "9999";
		this.radioType="P";

	}


}
