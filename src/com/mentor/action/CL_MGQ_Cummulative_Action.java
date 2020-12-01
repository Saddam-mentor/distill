package com.mentor.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;


import com.mentor.impl.CL_MGQ_Cummulative_Impl;

public class CL_MGQ_Cummulative_Action 
{
	
	

	CL_MGQ_Cummulative_Impl impl = new CL_MGQ_Cummulative_Impl();
	private boolean printFlag;
	private String pdfName;
	
	
	private String bwfl_id;
	private String radio = "S";

	private String distid="99";

	public String getDistid() {
		return distid;
	}

	public String getBwfl_id() {
		return bwfl_id;
	}

	public void setBwfl_id(String bwfl_id) {
		this.bwfl_id = bwfl_id;
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
				
				
				this.setDistid(o);
				
			}
		} catch (Exception ee) 
		{
			ee.printStackTrace();
		}

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
				
				System.out.println("=jfhsdhfsdfsdfsgfgfgsdfvcvsdhgcscsc"+this.getDistid());
				
				this.printFlag = false;
				
				this.shopList = impl.getShop(this,o);
				
				
			}
		} catch (Exception ee) 
		{
			ee.printStackTrace();
		}
	}
	
	
	
	
	
	
	

	

	public void shopname(ValueChangeEvent e) {

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

	
	private int selectedMonth=0;
	private ArrayList monthList=new ArrayList();

	public int getSelectedMonth() {
		
		
		return selectedMonth;
	}

	public void setSelectedMonth(int selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public ArrayList getMonthList() {
		this.monthList = impl.selectedMonth();
		return monthList;
	}

	public void setMonthList(ArrayList monthList) {
		this.monthList = monthList;
	}
	/*public void monthListener(ValueChangeEvent ve)
	{
		//String month=(String)ve.getNewValue();
		
		this.setSelectedMonth((Integer)ve.getNewValue());
		
		 
		
		if(this.getSelectedMonth()==1)
		{
			this.dateSelected="31/01/2020";
			this.setMonthName("January");
		}
		
		if(this.getSelectedMonth()==2)
		{
			this.dateSelected="28/02/2020";
			this.setMonthName("February");
		}
		
		if(this.getSelectedMonth()==3)
		{
			this.dateSelected="31/03/2020";
			this.setMonthName("March");
		}
		
		if(this.getSelectedMonth()==4)
		{
			this.dateSelected="30/04/2020";
			this.setMonthName("April");
		}
		
		else if(this.getSelectedMonth()==5)
		{
			this.setDateSelected("31/05/2020");
			this.setMonthName("May");
			 	
		}
		
		else if(this.getSelectedMonth()==6)
		{
			this.dateSelected="30/06/2020";
			this.setMonthName("June");
		}
		
		else if(this.getSelectedMonth()==7)
		{
			this.dateSelected="31/07/2020";
			this.setMonthName("July");
		}
		
		else if(this.getSelectedMonth()==8)
		{
			this.dateSelected="31/08/2020";
			this.setMonthName("August");
		}
		else if(this.getSelectedMonth()==9)
		{
			this.dateSelected="30/09/2020";
			this.setMonthName("September");
		}
		else if(this.getSelectedMonth()==10)
		{
			this.dateSelected="31/10/2020";
			this.setMonthName("October");
		}
		else if(this.getSelectedMonth()==11)
		{
			this.dateSelected="30/11/2020";
			this.setMonthName("November");
		}
		else if(this.getSelectedMonth()==12)
		{
			this.dateSelected="31/12/2020";
			this.setMonthName("December");
		}
		
	}
	*/
	public void monthListener(ValueChangeEvent ve)
	{
		//String month=(String)ve.getNewValue();
		
		this.setSelectedMonth((Integer)ve.getNewValue());
		
		 if(this.bwfl_id.equalsIgnoreCase("19_20"))
		 {
		if(this.getSelectedMonth()==1)
		{
			this.dateSelected="2020/01/31";
			this.setMonthName("January");
		}
		
		if(this.getSelectedMonth()==2)
		{
			this.dateSelected="2020/02/29";
			this.setMonthName("February");
		}
		
		if(this.getSelectedMonth()==3)
		{
			this.dateSelected="2020/03/31";
			this.setMonthName("March");
		}
		
		if(this.getSelectedMonth()==4)
		{
			this.dateSelected="2019/04/30";
			this.setMonthName("April");
		}
		
		else if(this.getSelectedMonth()==5)
		{
			this.setDateSelected("2019/05/31");
			this.setMonthName("May");
			 	
		}
		
		else if(this.getSelectedMonth()==6)
		{
			this.dateSelected="2019/06/30";
			this.setMonthName("June");
		}
		
		else if(this.getSelectedMonth()==7)
		{
			this.dateSelected="2019/07/31";
			this.setMonthName("July");
		}
		
		else if(this.getSelectedMonth()==8)
		{
			this.dateSelected="2019/08/31";
			this.setMonthName("August");
		}
		else if(this.getSelectedMonth()==9)
		{
			this.dateSelected="2019/09/30";
			this.setMonthName("September");
		}
		else if(this.getSelectedMonth()==10)
		{
			this.dateSelected="2019/10/31";
			this.setMonthName("October");
		}
		else if(this.getSelectedMonth()==11)
		{
			this.dateSelected="2019/11/30";
			this.setMonthName("November");
		}
		else if(this.getSelectedMonth()==12)
		{
			this.dateSelected="2019/12/31";
			this.setMonthName("December");
		}
		 }else {

				if(this.getSelectedMonth()==1)
				{
					this.dateSelected="2021/01/31";
					this.setMonthName("January");
				}
				
				if(this.getSelectedMonth()==2)
				{
					this.dateSelected="2021/02/29";
					this.setMonthName("February");
				}
				
				if(this.getSelectedMonth()==3)
				{
					this.dateSelected="2021/03/31";
					this.setMonthName("March");
				}
				
				if(this.getSelectedMonth()==4)
				{
					this.dateSelected="2020/04/30";
					this.setMonthName("April");
				}
				
				else if(this.getSelectedMonth()==5)
				{
					this.setDateSelected("2020/05/31");
					this.setMonthName("May");
					 	
				}
				
				else if(this.getSelectedMonth()==6)
				{
					this.dateSelected="2020/06/30";
					this.setMonthName("June");
				}
				
				else if(this.getSelectedMonth()==7)
				{
					this.dateSelected="2020/07/31";
					this.setMonthName("July");
				}
				
				else if(this.getSelectedMonth()==8)
				{
					this.dateSelected="2020/08/31";
					this.setMonthName("August");
				}
				else if(this.getSelectedMonth()==9)
				{
					this.dateSelected="2020/09/30";
					this.setMonthName("September");
				}
				else if(this.getSelectedMonth()==10)
				{
					this.dateSelected="2020/10/31";
					this.setMonthName("October");
				}
				else if(this.getSelectedMonth()==11)
				{
					this.dateSelected="2020/11/30";
					this.setMonthName("November");
				}
				else if(this.getSelectedMonth()==12)
				{
					this.dateSelected="2020/12/31";
					this.setMonthName("December");
				}
				 
		 }
	}
	
	private String dateSelected=null;

	public String getDateSelected() {
		return dateSelected;
	}
	public void setDateSelected(String dateSelected) {
		this.dateSelected = dateSelected;
	}
	
	 private String monthName;

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) 
	{
		this.monthName = monthName;
	}
	public void reset() 
	{
		this.printFlag = false;
		this.excelFlag=false;
		this.pdfName = null;
		this.shopList.clear();
		this.sectorList.clear();
		this.monthName="";
		this.dateSelected ="";
		this.selectedMonth=0;
	}
	
	
	
	
	
	public void print() 
	{
		
		if(this.selectedMonth>0)
		{
			
			if(this.radioDwCons.equalsIgnoreCase("D"))
			{
				impl.printReportShopWise(this);
			}else
			{
				impl.printReportDistrictWise(this);
			}
			
			
			
		}else
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Kindly Select Month!!", "Kindly Select Month!!"));
		}

	}
	public void excel() throws ParseException {

		
		
		
		if(this.selectedMonth>0)
		{
			
			if(this.radioDwCons.equalsIgnoreCase("D"))
			{
				impl.excelShopWise(this);
			}
			else
			{
				impl.excelDitrictWise(this);	
			}
			
			
		}else
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Kindly Select Month!!", "Kindly Select Month!!"));
		}

	}
	private String radioDwCons;
	
	public String getRadioDwCons() {
		return radioDwCons;
	}

	public void setRadioDwCons(String radioDwCons) {
		this.radioDwCons = radioDwCons;
	}

	public void chngval() 
	{
		
	}
	
	
}
