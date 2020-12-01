package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.impl.SpiritConsumptionImpl;

public class SpiritConsumptionAction {
	
	SpiritConsumptionImpl impl = new SpiritConsumptionImpl();

	private String hidden;
	private Date fromDate;
	private Date toDate;
	private boolean printFlag;
	private String pdfName;
	private String distilleryID;
	private ArrayList distilleryList = new ArrayList();

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
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

	public String getDistilleryID() {
		return distilleryID;
	}

	public void setDistilleryID(String distilleryID) {
		this.distilleryID = distilleryID;
	}

	public ArrayList getDistilleryList() {
		try {
			this.distilleryList = impl.getDistilleryList(this);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return distilleryList;
	}

	public void setDistilleryList(ArrayList distilleryList) {
		this.distilleryList = distilleryList;
	}
	
	public void print() {
		try{
			if(this.distilleryID!=null && this.distilleryID.equalsIgnoreCase("0")){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,
				" Please Select Distillery Name !!"," Please Select Distillery Name !!"));				
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
		this.distilleryID= null;

	}

}
