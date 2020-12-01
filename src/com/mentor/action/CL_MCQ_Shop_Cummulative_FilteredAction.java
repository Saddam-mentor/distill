package com.mentor.action;

import java.util.ArrayList;
import java.util.Calendar;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.CL_MGQ_Cummulative_Filtered_Impl;


public class CL_MCQ_Shop_Cummulative_FilteredAction {

	CL_MGQ_Cummulative_Filtered_Impl impl = new CL_MGQ_Cummulative_Filtered_Impl();
	private boolean printFlag;
	private String pdfName;
	private String sorted;
	
	private String fl_cl;
    
	
	public String getFl_cl() {
		return fl_cl;
	}

	public void setFl_cl(String fl_cl) {
		this.fl_cl = fl_cl;
	}

		
	private String radio = "S";

	private String distid="99";
	
	private String monthId = String.valueOf((Calendar.getInstance().get(Calendar.MONTH) + 1));
	
	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
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
	
	

	public String getSorted() {
		return sorted;
	}

	public void setSorted(String sorted) {
		this.sorted = sorted;
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
	public void radioListener(ValueChangeEvent e) 
	{
		
	//	this.distid="99";
		
		
		String o = (String) e.getNewValue();
		try 
		{
			
			if(o.length()>0 || o!=null)
			{
				
				this.printFlag = false;
			//	this.shopList = impl.getShop(this, o);
				
				this.sectorList = impl.getSectorList(this, o);
				this.shopList = impl.getShopL(this,o);
				
			}
		} catch (Exception ee) 
		{
			ee.printStackTrace();
		}

	}
	
	
	public void sortedListener(ValueChangeEvent e){
		this.sorted = (String) e.getNewValue();
		this.printFlag=false;
		this.excelFlag = false;
	}

	public void sortedListenerF(ValueChangeEvent e){
		this.sorted = (String) e.getNewValue();
		this.printFlag=false;
		this.excelFlag = false;
	}
	
	
	
	public void radioListenerSector(ValueChangeEvent e) 
	{
		String o = (String) e.getNewValue();
		try {
			if(o==null)
			{
				System.out.println("=jfhsdhfsdfsdfsgfgfgsdfvcvsdhgcscsc");
			}
			
			else
			{
				
				this.printFlag = false;
				
				this.shopList = impl.getShop(this,o);
				
				
			}
		} catch (Exception ee) 
		{
			ee.printStackTrace();
		}
	}
	
	
	public void radioListenerMonth(ValueChangeEvent e){
		this.monthId = (String) e.getNewValue();
	}
	

	public void print() {
	/*	    
		if(this.monthId.equalsIgnoreCase("0"))
			return;
	*/
		try{
		if(this.fl_cl.equalsIgnoreCase("FL")) 
		{
		impl.printReportFL_lifting(this);
		}
		else
		{

			impl.printReportShopWise(this);
			
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void shopname(ValueChangeEvent e) {

	}

	public void reset() 
	{
		this.printFlag = false;
		this.pdfName = null;
		this.shopList.clear();
		this.sectorList.clear();
		this.fl_cl = null;
		this.sorted = null;
		
		this.monthId=String.valueOf((Calendar.getInstance().get(Calendar.MONTH) + 1));
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

		if(this.monthId.equalsIgnoreCase("0"))
			return;
			impl.excelShopWise(this);
		

	}
	private ArrayList sectorList = new ArrayList();
	
	public void setSectorList(ArrayList sectorList) {
		this.sectorList = sectorList;
	}

	public ArrayList getSectorList() {

		
		return sectorList;
	}
	
	private String sectorId="0";

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	private ArrayList districtList = new ArrayList();
	public ArrayList getDistrictList() 
	{
		this.districtList = impl.getDistList();
		return districtList;
	}

	public void setDistrictList(ArrayList districtList) {
		this.districtList = districtList;
	}
	
	
	
	
	//---------------------------------------------------------arvind by 6-4-2020--------------------
	private String bwfl_id;
	private ArrayList bwfl_list=new ArrayList();
	public ArrayList getBwfl_list() {
		//System.out.println("----------------bwfl-----list ---------");
				
				try {
			
				System.out.println("====================radio======================"+this.getRadio());
					this.bwfl_list=impl.getBrandName();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			
				return bwfl_list;
			}
			public void setBwfl_list(ArrayList bwfl_list) {
				this.bwfl_list = bwfl_list;
			}
			public String getBwfl_id() {
				
				return bwfl_id;
			}
			public void setBwfl_id(String bwfl_id) {
				this.bwfl_id = bwfl_id;
			}
}
