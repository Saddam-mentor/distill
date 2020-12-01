package com.mentor.action;

import java.util.Date;
import java.util.ArrayList;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import com.mentor.impl.ReportOn_tankConversion_DistilleryImpl;

public class ReportOn_tankConversion_DistilleryAction {
	
	private Date formdate;
	private Date todate;
	private int srno;
	private String tankname;
	private double capacity;
	private int orderno;
	private Date orderdate;
	private String from;
	private String to;
	private int Distillery_id;
	private String Distillery_nam;
	
	
	

	
	public Date getFormdate() {
		return formdate;
	}


	public void setFormdate(Date formdate) {
		this.formdate = formdate;
	}



	public Date getTodate() {
		return todate;
	}




	public void setTodate(Date todate) {
		this.todate = todate;
	}


	public int getSrno() {
		return srno;
	}



	public void setSrno(int srno) {
		this.srno = srno;
	}


	public String getTankname() {
		return tankname;
	}



	public void setTankname(String tankname) {
		this.tankname = tankname;
	}


	public double getCapacity() {
		return capacity;
	}


	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}


	public int getOrderno() {
		return orderno;
	}




	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}




	public Date getOrderdate() {
		return orderdate;
	}




	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}




	public String getFrom() {
		return from;
	}





	public void setFrom(String from) {
		this.from = from;
	}




	public String getTo() {
		return to;
	}



	public void setTo(String to) {
		this.to = to;
	}




	public int getDistillery_id() {
		return Distillery_id;
	}



	public void setDistillery_id(int distillery_id) {
		Distillery_id = distillery_id;
	}


	public String getDistillery_nam() {
		return Distillery_nam;
	}



	public void setDistillery_nam(String distillery_nam) {
		Distillery_nam = distillery_nam;
	}
    
	
	ReportOn_tankConversion_DistilleryImpl impl = new ReportOn_tankConversion_DistilleryImpl();

	public void print() {
		impl.printReport(this);
		
	}
	
	public String printName;
	private boolean printFlag;





	public String getPrintName() {
		return printName;
	}


	public void setPrintName(String printName) {
		this.printName = printName;
	}


	public boolean isPrintFlag() {
		return printFlag;
	}


	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}
	

}
