package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.impl.FL1DistileryStockRegisterImpl;

public class FL1DistileryStockRegisterAction {
	
	FL1DistileryStockRegisterImpl impl = new FL1DistileryStockRegisterImpl();

	private String hidden;
	private int loginUserId;
	private String loginUserNm;
	private String loginUserAdrs;
	private String loginUserType;
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private String brandId;
	private String licenseNo;
	private ArrayList brandList = new ArrayList();
	private ArrayList fL1LicenseList = new ArrayList();

	public String getHidden() {
		try{
			impl.getUserDetails(this);
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

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public ArrayList getBrandList() {
		try {
			this.brandList=impl.getBrandList(this);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return brandList;
	}

	public void setBrandList(ArrayList brandList) {
		this.brandList = brandList;
	}

	public ArrayList getfL1LicenseList() {
		try {
			this.fL1LicenseList = impl.getFL1LicenseNmbr(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fL1LicenseList;
	}

	public void setfL1LicenseList(ArrayList fL1LicenseList) {
		this.fL1LicenseList = fL1LicenseList;
	}
	
	public void print() {
		try{
			if(this.brandId!=null && this.brandId.equalsIgnoreCase("0")){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
				" Please Select Brand !!"," Please Select Brand !!"));				
			}else if(this.licenseNo!=null && this.licenseNo.equalsIgnoreCase("0")){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
				" Please Select License Number !!"," Please Select License Number !!"));				
			}else {
				impl.printReport(this);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
		this.licenseNo = null;
		

	}

}
