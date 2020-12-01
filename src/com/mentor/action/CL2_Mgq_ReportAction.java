package com.mentor.action;

import java.util.ArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.CL2_Mgq_ReportImpl;

public class CL2_Mgq_ReportAction {
	CL2_Mgq_ReportImpl impl = new CL2_Mgq_ReportImpl();
	//=================================varaible========================
	
	private boolean printFlag;
	private String pdfName;
	private String radio = "S";
	private String distid;
	private String year;
	private String url="www.cst.up.gov.in";
	public String getUrl() {
		try {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    externalContext.redirect("http://stackoverflow.com");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	ArrayList getAll_List=new ArrayList();
	
	//=================================Getter and setter============================
	
	

	public void setGetAll_List(ArrayList getAll_List) {
		this.getAll_List = getAll_List;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDistid() {
		return distid;
	}

	public void setDistid(String distid) {
		this.distid = distid;
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

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	} 
	public void radioListener(ValueChangeEvent e) {
		String o = (String) e.getNewValue();

		try {
			if(o.length()>0 || o!=null)
			{
			
			if (radio.equalsIgnoreCase("S")) {

				this.printFlag = false;
				/*if(this.shopId==0){
					
				}
				shopId*/
				
			//	System.out.println(" cl2 mgq ====="+o);
				this.shopList = impl.getShop(this, o);
			}
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}

	}

	public void print() {
		if (this.radio.equalsIgnoreCase("C")) {
			 
			impl.printReportDistrict(this);
		} else {
			impl.printReportShopWise(this);
		}

	}

	public void shopname(ValueChangeEvent e) {

	}

	public void reset() {
		this.printFlag = false;
		this.pdfName = null;

	}

	private String shopId = "0";
	private ArrayList shopList = new ArrayList();

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	public ArrayList getShopList() {
		return shopList;
	}

	public void setShopList(ArrayList shopList) {
		this.shopList = shopList;
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

	public void excel() {

		if(this.radio.equalsIgnoreCase("C"))
		{ 
			impl.excelDistrictWise(this);
		}
		else
		{
			impl.excelShopWise(this);
		}
		//impl.write(this);

	}
	
	
		//------------------year List----------------------------------------------------
		public ArrayList getGetAll_List() {
			
			
			this.getAll_List=impl.yearListImpl(this);

		return getAll_List;
	}
	
	

}
