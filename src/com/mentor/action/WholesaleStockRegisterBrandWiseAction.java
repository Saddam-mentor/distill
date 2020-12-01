package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.WholesaleStockRegisterBrandWiseImpl;

public class WholesaleStockRegisterBrandWiseAction {
	
	WholesaleStockRegisterBrandWiseImpl impl = new WholesaleStockRegisterBrandWiseImpl();

	private String hidden;
	private String radioType;
	private int loginUserId;
	private String loginUserNm;
	private String loginUserType;
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private String brandId;
	private ArrayList brandList = new ArrayList();

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

	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
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
	
	public void radioListener(ValueChangeEvent e) {

		String val = (String) e.getNewValue();		
		this.setRadioType(val);
		this.printFlag = false;
		
	}
	
	public void print() {
		try{
			if(this.brandId!=null && this.brandId.equalsIgnoreCase("0")){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
				" Please Select Brand !!"," Please Select Brand !!"));				
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

	/*public void reset() {

		this.formdate = null;
		this.todate = null;
		this.radioType = null;
		this.excelFlag = false;
		this.printFlag = false;
	}
  */
	
	public void excel() {

		try {
			System.out.println("===========excsagcvhacbv");
			if (this.fromDate != null && this.toDate != null) {
                 if(this.radioType != null){
                	 
                	 impl.printExcel(this);
		
                 }
                 else {

     				FacesContext.getCurrentInstance().addMessage(
     						null,
     						new FacesMessage(FacesMessage.SEVERITY_ERROR,
     								" Please Select Radio Value !!",
     								" Please Select Radio Value !!"));
     			}
				System.out.println("====excel");
				// impl.printExcel(this);
			} else {

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								" Please Select Dates !!",
								" Please Select Dates !!"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
