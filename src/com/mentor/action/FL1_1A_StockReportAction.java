package com.mentor.action;

import java.util.ArrayList;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.FL1_1A_StockReportImpl;

public class FL1_1A_StockReportAction {

	FL1_1A_StockReportImpl impl = new FL1_1A_StockReportImpl();
	private String radio;
	private String licenseNo;
	private boolean printFlag;
	private String pdfname;
	private String address;
	private String name;
	private String hidden;
	private int loginId;
	private ArrayList fl1aLicenseList = new ArrayList();

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getHidden() {
		try {
			impl.getDetails(this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList getFl1aLicenseList() {
		try {
			this.fl1aLicenseList = impl.getFL1ALicenseNmbr(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fl1aLicenseList;
	}

	public void setFl1aLicenseList(ArrayList fl1aLicenseList) {
		this.fl1aLicenseList = fl1aLicenseList;
	}

	public void radioListener(ValueChangeEvent e) {
		this.printFlag = false;
		
	}

	public void print() {
		
		try{
			 impl.printReportDist(this);
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public void reset() {
		this.printFlag = false;
		this.pdfname = null;
		this.name = null;
		this.address = null;
		this.loginId = 0;
		this.licenseNo = null;
		this.radio = null;
		this.fl1aLicenseList.clear();	
	}
}
