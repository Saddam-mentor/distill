package com.mentor.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.StockReportCBWImpl;

public class StockReportCBWAction {


	StockReportCBWImpl impl = new StockReportCBWImpl();
	
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private boolean excelFlag = false;
	private String radio = "B";
	private String exlname;
	private String packId;
	private ArrayList brandList = new ArrayList();
	private String licence_type;
	private int int_id;
	private String licence_no;
	private String applicant_name;
	private String district;
	private int districtId;
	private String hidden;
	private String brandpack;
	private Date lastDate;
	private int balance;
	private String gatepass_no;
	private int recv_bottels;
	private int dispatch_bottle;
	private String brand_name;
	private String package_name;
	private Date date1;
	private int newb;
	private int int_import_id;
	private int int_bond_id;
	

	public int getInt_import_id() {
		return int_import_id;
	}

	public void setInt_import_id(int int_import_id) {
		this.int_import_id = int_import_id;
	}

	public int getInt_bond_id() {
		return int_bond_id;
	}

	public void setInt_bond_id(int int_bond_id) {
		this.int_bond_id = int_bond_id;
	}

	public int getNewb() {
		return newb;
	}

	public void setNewb(int newb) {
		this.newb = newb;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public String getGatepass_no() {
		return gatepass_no;
	}

	public void setGatepass_no(String gatepass_no) {
		this.gatepass_no = gatepass_no;
	}

	public int getRecv_bottels() {
		return recv_bottels;
	}

	public void setRecv_bottels(int recv_bottels) {
		this.recv_bottels = recv_bottels;
	}

	public int getDispatch_bottle() {
		return dispatch_bottle;
	}

	public void setDispatch_bottle(int dispatch_bottle) {
		this.dispatch_bottle = dispatch_bottle;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Date getLastDate() throws Exception {
		if(this.radio.equalsIgnoreCase("B")){
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(expiredDate);
		this.lastDate=date1;
		}
		
		return lastDate;
	}

	

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public String getBrandpack() {
		return brandpack;
	}

	public void setBrandpack(String brandpack) {
		this.brandpack = brandpack;
	}

	public String getHidden() {
		
		try {
			impl.getDetails(this);
			
		} catch (Exception ex) {
			ex.getMessage();
		}
		
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getLicence_type() {
		return licence_type;
	}

	public void setLicence_type(String licence_type) {
		this.licence_type = licence_type;
	}

	public int getInt_id() {
		return int_id;
	}

	public void setInt_id(int int_id) {
		this.int_id = int_id;
	}

	public String getLicence_no() {
		
		return licence_no;
	}

	public void setLicence_no(String licence_no) {
		this.licence_no = licence_no;
	}

	public String getApplicant_name() {
		return applicant_name;
	}

	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	


	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public ArrayList getBrandList() {
		if(this.radio.equalsIgnoreCase("B")){
		this.brandList = impl.getBrandList(this);
		}
		return brandList;
	}

	public void setBrandList(ArrayList brandList) {
		this.brandList = brandList;
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

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String expiredDate = null;

	
	public void print()throws Exception {
		
		
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if(this.radio.equalsIgnoreCase("B")){
		if(this.packId!=null && this.packId.length()>0){
		if(this.fromDate != null && this.toDate != null){
			impl.getopening(this);
			String currentDate = dateFormat.format(this.fromDate);
			Calendar cal = Calendar.getInstance();

			try {
				cal.setTime(dateFormat.parse(currentDate));
				cal.add(Calendar.DATE, -1);
				expiredDate = dateFormat.format(cal.getTimeInMillis());

				//System.out.println(expiredDate);
				
				impl.printReport(this);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}else{

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Date should not be null", "Date should not be null"));

		}
		
		}else{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Please Select Brand Name!!", "Please Select Brand Name!!"));
		}
		}else{
			impl.printReportDetailed(this);
		}
		
	}

	public void excel() {
		//impl.writeExcel(this);
	}

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;
		this.fromDate = null;
		this.toDate = null;
		this.exlname = null;
		this.radio = "FL2" ;
		this.excelFlag = false;
		this.brandList.clear();
		this.packId=null;
		this.radio="B";

	}

	public void listener(ValueChangeEvent e){
		this.printFlag=false;
	}
		
}
