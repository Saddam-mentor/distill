package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.Shop_WiseDispatchesImpl;

public class Shop_WiseDispatchesAction {
     
	
	
	Shop_WiseDispatchesImpl impl =new Shop_WiseDispatchesImpl();
	
	private String hidden;
	private Date fromdate;
	private Date todate;
	
	private int districId;
	public int getDistricId() {
		return districId;
	}
	public void setDistricId(int districId) {
		this.districId = districId;
	}



	private ArrayList districList = new ArrayList();
	


	public ArrayList getDistricList() {
		try {
			this.shopList = impl.getShop(this);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		
		return districList;
	}
	public void setDistricList(ArrayList districList) {
		this.districList = districList;
	}
	public String getHidden() {

		try{
			this.districList = impl.getDistrict();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hidden;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	public Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}

	
	
	public String issueSpiritsVAT(ValueChangeEvent e1) {
		try {

			impl.getShop(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	public String radio;







	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	
	
	
	
	public void radioListiner(ValueChangeEvent e) {

		String o = (String) e.getNewValue();
		
		if (o != null) {

			this.radio = o;
		}
		
	}
	
	
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public ArrayList getShopList() {
		
		
		return shopList;
	}
	public void setShopList(ArrayList shopList) {
		this.shopList = shopList;
	}




	private int shopId;
	private ArrayList shopList = new ArrayList();
	
	
	
	
	public String shopname(ValueChangeEvent e) {
		
		String o = (String) e.getNewValue();
		
		return "";
	}

	
	
	public void reset()
	{
		this.pdfname = "";
		this.radio = "";
		
		this.printFlag = false;
		this.fromdate = null;
		this.todate = null;
		this.excelFlag=false;
		this.districId=9999;
		this.districList.clear();
		this.shopList.clear();
		this.shopId=0;
		
	}
	
	
	private String pdfname;
	private boolean printFlag;
	
	
	public String getPdfname() {
		return pdfname;
	}
	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}
	public boolean isPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}
	public void print() {
		
		impl.PrintReport(this);
	}
	
	
	//=============================
	
	private boolean excelFlag = false;
	private String exlname;
	
	public String getExlname() {
		return exlname;
	}
	public void setExlname(String exlname) {
		this.exlname = exlname;
	}
	public boolean isExcelFlag() {
		return excelFlag;
	}
	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}
	public void excel() {
		if (this.radio != null) {
			
				impl.writeNameWise(this);
				
			
		}
		else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Select Radio Type ","Select Radio Type"));
		}
	}
	private String district_name;
	public String getDistrict_name() {
		return district_name;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

}
