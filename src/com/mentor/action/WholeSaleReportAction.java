package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.WholeSaleReportImpl;
import com.mentor.utility.ResourceUtil;

 
public class WholeSaleReportAction {




	WholeSaleReportImpl impl = new WholeSaleReportImpl();
	
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private boolean excelFlag = false;
	private String radio = "FL2";
	private String exlname;
	private String wholesaleid;
	

	private ArrayList wholesaleList = new ArrayList();

	

	public void setWholesaleList(ArrayList wholesaleList) {
		this.wholesaleList = wholesaleList;
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

	public String getExlname() {
		return exlname;
	}

	public void setExlname(String exlname) {
		this.exlname = exlname;
	}

	public void radioListener(ValueChangeEvent e) {
		
		//impl.getWholesaleList();

		this.printFlag = false;
		this.excelFlag = false;
	}

	public void print() {
		
		if(this.radio!=null && this.toDate!=null && this.fromDate!=null ){
		impl.printReport(this);
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("complete all fields ", "complete all fields"));
		}
	
	}

	public void excel() {
		
		if(this.radio!=null && this.toDate!=null && this.fromDate!=null ){
			impl.writeExcel(this);
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("complete all fields ", "complete all fields"));
			}
		
		
	}

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
		this.exlname = null;
		this.radio = "FL2" ;
		this.excelFlag = false;

	}

//----------------------------------------------------------------------
	
	private ArrayList districtList = new ArrayList();
	private String hidden;
	private boolean districtFlag;
	private int districtId;
	private String district;
	private String distid;
	private boolean flag;

	
	
	
	public ArrayList getWholesaleList() 
	{
		if(this.distid!=null || this.district!=null)
		{
		this.wholesaleList=impl.getWholesaleList(this);
		}
		return wholesaleList;
	}
	public String getHidden() {
		try {
			impl.getDetails(this);
			if (ResourceUtil.getUserNameAllReq().trim().substring(0, 10).equalsIgnoreCase("Excise-DEO")) 
			{
				this.districtFlag = false;
			} 
			else 
			{
				this.districtFlag = true;
			}
		} catch (Exception ex) {
			ex.getMessage();
		}
		return hidden;
	}
	public ArrayList getDistrictList() {
		this.districtList = impl.getDistList();
		return districtList;
	}

	public void setDistrictList(ArrayList districtList) {
		this.districtList = districtList;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public boolean isDistrictFlag() {
		return districtFlag;
	}

	public void setDistrictFlag(boolean districtFlag) {
		this.districtFlag = districtFlag;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getDistid() {
		return distid;
	}

	public void setDistid(String distid) {
		this.distid = distid;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public String getWholesaleid() {
		return wholesaleid;
	}

	public void setWholesaleid(String wholesaleid) {
		this.wholesaleid = wholesaleid;
	}
	
	
	
	

}
