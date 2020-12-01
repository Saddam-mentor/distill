package com.mentor.action;

import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.wholesale_Manual_Dispatch_OldStock_18_19_rpt_Impl;

public class Wholesale_Manual_Dispatch_OldStock_18_19_rpt_Action {

	wholesale_Manual_Dispatch_OldStock_18_19_rpt_Impl impl = new wholesale_Manual_Dispatch_OldStock_18_19_rpt_Impl();  
	  private int user_id;
	  private Date from_dt;
	  private Date to_dt;
	  private String pdfName;
	  private boolean flag;
	  private String liqourType="FL2";
	  private String distType="A";
	  private int district_id;
	  private int shop_id;
	  private ArrayList disttrictList = new ArrayList();
	  private ArrayList shopList = new ArrayList();
	  private String Name;
	  private boolean flag1;
	  private boolean excelFlag;
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
	public boolean isFlag1() {
		return flag1;
	}
	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public ArrayList getDisttrictList() {
		//this.disttrictList=impl.districtList(this);
		return disttrictList;
	}
	public void setDisttrictList(ArrayList disttrictList) {
		this.disttrictList = disttrictList;
	}
	public ArrayList getShopList() {
		if(this.getDistrict_id()==0)
		      this.shopList=impl.shop(this);
		return shopList;
	}
	public void setShopList(ArrayList shopList) {
		this.shopList = shopList;
	}
	public String getLiqourType() {
		return liqourType;
	}
	public void setLiqourType(String liqourType) {
		this.liqourType = liqourType;
	}
	public String getDistType() {
		return distType;
	}
	public void setDistType(String distType) {
		this.distType = distType;
	}
	public int getDistrict_id() {
		return district_id;
	}
	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}
	public int getShop_id() {
		return shop_id;
	}
	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public Date getFrom_dt() {
		return from_dt;
	}
	public void setFrom_dt(Date from_dt) {
		this.from_dt = from_dt;
	}
	public Date getTo_dt() {
		return to_dt;
	}
	public void setTo_dt(Date to_dt) {
		this.to_dt = to_dt;
	}
	
	public void print(){
		if(this.from_dt!=null && this.to_dt!=null){
		   
			if(this.getDistType().equalsIgnoreCase("A") || this.getDistrict_id()==0){
				impl.printReportAll(this);
			}
				
			else{	
				
				     impl.printReportDistrict(this);
			}
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Please Select Date !!","Please Select Date !!"));
		}
		
		this.setExcelFlag(false);
	}
	
		public void getDistName(ValueChangeEvent e){
			 int val = (Integer) e.getNewValue();
			 impl.getDistrictName(this, val);
			 this.shopList=impl.shopList(this, val);
			 this.setExcelFlag(false);
			 this.setFlag(false);
		}
		
		public void listener(ValueChangeEvent e){
			String val= (String) e.getNewValue();
			this.setDistType(val);
			this.disttrictList=impl.districtList(this);
			if(this.getDistType().equalsIgnoreCase("D"))
			        this.setFlag1(true);
			else
			        this.setFlag1(false);
		}
	
		public void excel(){
			if(this.from_dt!=null && this.to_dt!=null){
				
				    impl.printExcelDistrictWise(this);
		}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Please Select Date !!","Please Select Date !!"));
			}
			this.setFlag(false);
		}
}
