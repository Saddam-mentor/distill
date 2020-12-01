package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.WholesaleCancelAllGatepassImpl;

public class WholesaleCancelAllGatepassAction {

	WholesaleCancelAllGatepassImpl impl = new WholesaleCancelAllGatepassImpl();

	private String hidden;
	private Date crDate;
	private ArrayList displayAllGatepass = new ArrayList();

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public Date getCrDate() {
		return crDate;
	}

	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	public ArrayList getDisplayAllGatepass() {
		return displayAllGatepass;
	}

	public void setDisplayAllGatepass(ArrayList displayAllGatepass) {
		this.displayAllGatepass = displayAllGatepass;
	}

	public String dateListener(ValueChangeEvent vce) {

		Date crDt = (Date) vce.getNewValue();
		this.setCrDate(crDt);
		
		if (this.crDate != null) {
			this.displayAllGatepass = impl.displayAllGatepassImpl(this);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
			"Select Date First !!", "Select Date First !!"));
		}

		return "";

	}

	public String cancelGatepass() {
		try{
			impl.cancelGatepass(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "";

	}

	public void reset() {
		this.displayAllGatepass = impl.displayAllGatepassImpl(this);
	}

}
