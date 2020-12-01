package com.mentor.action;

import it.businesslogic.ireport.gui.event.ValueChangedEvent;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.HeadWise_challan_detail_impl;

public class HeadWise_challan_detail_action {
	
	HeadWise_challan_detail_impl impl = new HeadWise_challan_detail_impl();
 
/*=====================prasoon mishra==========27-05-2020=============================*/
    /*========================variable================================================*/
	private Date from_date;
	private Date to_date;
	private String challanHeadId ="";
	private ArrayList challanHeadList = new ArrayList();
	private String g6HeadId="";
	private ArrayList g6HeadList = new ArrayList(); 
	private boolean printFlag;
	private String pdfName;
	private boolean excelFlag = false;
	private String exlname;
	
	
	/*=============================getter and setter======================================*/

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
	public Date getFrom_date() {
		return from_date;
	}
	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}
	public Date getTo_date() {
		return to_date;
	}
	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}
	public String getChallanHeadId() {
		return challanHeadId;
	}
	public void setChallanHeadId(String challanHeadId) {
		this.challanHeadId = challanHeadId;
	}
 
	public String getG6HeadId() {
		return g6HeadId;
	}
	public void setG6HeadId(String g6HeadId) {
		this.g6HeadId = g6HeadId;
	}
  
	
    public ArrayList getChallanHeadList() {
    	try{
    	this.challanHeadList =impl.getChallanHeadList(this);
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return challanHeadList;
	}
	public void setChallanHeadList(ArrayList challanHeadList) {
		this.challanHeadList = challanHeadList;
	}
	
	public ArrayList getG6HeadList() {
		return g6HeadList;
	}
	public void setG6HeadList(ArrayList g6HeadList) {
		this.g6HeadList = g6HeadList;
	}
/*================================challan change listner===============================*/
  public String challan_change(ValueChangeEvent vce){
		try
		{
			//System.out.println("---challan-change---");
		String val = String.valueOf(vce.getNewValue());
		this.challanHeadId = val;
			this.g6HeadList = impl.g_six_list(this);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		return "" ;
		
	}
	
	
	/*	===================================g6 challan listner  method================================*/
    public void g6_challan_listner(ValueChangeEvent vce){
    	
    	String val = String.valueOf(vce.getNewValue());
    	   this.setG6HeadId(val);
    	   	
    }
	
	/*	==================================print report===============================*/
  public void print()throws Exception {
		
		
		if(this.from_date != null && this.to_date != null){
			
			try {	
				//System.out.println("===print report action==");
				impl.printReport(this);
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}else{

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Date should not be blank", "Date should not be blank"));

		}
		
	}
	
	/*	=================================== excel ================================*/
       public void excel() {
    	   
    	   if(this.from_date != null && this.to_date != null){
				//System.out.println("---- excel--- print---- ");
				impl.excelheadwise_challan(this);
       }else{

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Date should not be blank", "Date should not be blank"));

		}
       }
	/*	================================= reset ===============================*/
	public void reset(){
		this.pdfName = null;
		this.exlname = null;
		this.challanHeadId = null;
		this.g6HeadId = null;
		this.from_date = null;
		this.to_date = null;
		this.challanHeadList.clear();
		this.g6HeadList.clear();
		printFlag = false;
		excelFlag = false;
		
	}
	

	
}
