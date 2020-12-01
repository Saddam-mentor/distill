package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.FL2D_Import_ReportImpl;
import com.mentor.utility.ResourceUtil;

public class FL2D_Import_ReportAction {

	FL2D_Import_ReportImpl impl = new FL2D_Import_ReportImpl();
	
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private boolean excelFlag = false,deo_flag;
	private String radio;
	private String exlname;

	public boolean isDeo_flag() {
		return deo_flag;
	}

	public void setDeo_flag(boolean deo_flag) {
		this.deo_flag = deo_flag;
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

		this.printFlag = false;
		this.excelFlag = false;
	}

	public void print() {
		if(this.getFromDate()==null || this.getToDate()==null)
		{	
		   FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Please Select Date !!",
								"Please Select Date !!"));
		
	   }
		else{
		impl.printReport(this);
	}
		}

	public void excel() {
		if(this.getFromDate()==null || this.getToDate()==null)
		{	
		   FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Please Select Date !!",
								"Please Select Date !!"));
		
	   }
		else
		{
		impl.writeExcel(this);
	}
	}

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
		this.exlname = null;
		this.excelFlag=false;

	}
	
	//=======================================
	
	private Date start_dt;
	private Date end_dt ;
	
	
	public Date getStart_dt() {
		return start_dt;
	}

	public void setStart_dt(Date start_dt) {
		this.start_dt = start_dt;
	}

	public Date getEnd_dt() {
		return end_dt;
	}

	public void setEnd_dt(Date end_dt) {
		this.end_dt = end_dt;
	}
		private String Year;
		private int district_id;
		public int getDistrict_id() {
			return district_id;
		}

		public void setDistrict_id(int district_id) {
			this.district_id = district_id;
		}

		public String getYear() {
			return Year;
		}

		public void setYear(String year) {
			Year = year;
		}
	ArrayList getAll_List=new ArrayList();
	ArrayList districtList=new ArrayList();
		
		
		public ArrayList getDistrictList() {
			try {
				if(ResourceUtil.getUserNameAllReq().length()>9 && ResourceUtil.getUserNameAllReq().substring(0, 10).equalsIgnoreCase("Excise-DEO")){
					this.districtList=impl.deoDistrictListImpl(this);
				}else{
				this.districtList =impl.districtListImpl(this);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return districtList;
	}

	public void setDistrictList(ArrayList districtList) {
		
		this.districtList = districtList;
	}

		public ArrayList getGetAll_List() {
			
		
			return getAll_List;
		}
	  
		public void setGetAll_List(ArrayList getAll_List) {
			this.getAll_List = getAll_List;
		}	
	
}
