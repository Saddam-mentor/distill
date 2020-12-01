package com.mentor.action;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.RandomStringUtils;

import com.mentor.impl.ENA_Permit_SearchImpl;

public class ENA_Permit_SearchAction {

	ENA_Permit_SearchImpl impl = new ENA_Permit_SearchImpl();

	private String orderNo = null;
	private Date orderDate =null;
	private String pur_dist = "";
	private String seller_dist = "";
	private double pur_ena = 0.0;
	private Date request_Date = null;
	private double approved_ena = 0.0;
	private double lifted_ena = 0.0;
	private String Statuse = null;
	private boolean panal_flag = false;
	private String ena_type = null;
	private String pdf=null;

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getEna_type() {
		return ena_type;
	}

	public void setEna_type(String ena_type) {
		this.ena_type = ena_type;
	}

	public boolean isPanal_flag() {
		return panal_flag;
	}

	public void setPanal_flag(boolean panal_flag) {
		this.panal_flag = panal_flag;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getPur_dist() {
		return pur_dist;
	}

	public void setPur_dist(String pur_dist) {
		this.pur_dist = pur_dist;
	}

	public String getSeller_dist() {
		return seller_dist;
	}

	public void setSeller_dist(String seller_dist) {
		this.seller_dist = seller_dist;
	}

	public double getPur_ena() {
		return pur_ena;
	}

	public void setPur_ena(double pur_ena) {
		this.pur_ena = pur_ena;
	}

	public Date getRequest_Date() {
		return request_Date;
	}

	public void setRequest_Date(Date request_Date) {
		this.request_Date = request_Date;
	}

	public double getApproved_ena() {
		return approved_ena;
	}

	public void setApproved_ena(double approved_ena) {
		this.approved_ena = approved_ena;
	}

	public double getLifted_ena() {
		return lifted_ena;
	}

	public void setLifted_ena(double lifted_ena) {
		this.lifted_ena = lifted_ena;
	}

	public String getStatuse() {
		return Statuse;
	}

	public void setStatuse(String statuse) {
		Statuse = statuse;
	}

	public void search() {
		
		String match = this.getEnterCaptcha1().trim();
		if(this.orderNo==null || this.orderNo.equalsIgnoreCase("")){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Enter Order No !!", "Enter Order No !!"));
		}else if(this.orderDate==null){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Select Order Date !!", "Select Order Date !!"));
		}else
		if(this.getGenerateCaptcha1().trim().equals(match))
		{
		
		
			
			if (impl.search(this) == true) {
				this.panal_flag = true;
			} else {
				this.panal_flag = false;
			}
		
		}
		else{this.enterCaptcha1=null;
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
		" Type the given code correctly in box given below !!",
		" Type the given code correctly in box given below !!"));
	}
	}
	
	
	private String generateCaptcha1;
	private String enterCaptcha1;
	private boolean cptchFlag1 = true;
	public String generateCaptchaString1() {

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&";
		String pwd = RandomStringUtils.random( 6, characters );
		
		return pwd;
	}
	
	public String getGenerateCaptcha1() {
		try {
			if (this.cptchFlag1 == true) {
				this.generateCaptcha1 = this.generateCaptchaString1();
				this.cptchFlag1 = false;
			}

		//	System.out.println(""+ this.generateCaptcha1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return generateCaptcha1;
	}

	public void setGenerateCaptcha1(String generateCaptcha1) {
		this.generateCaptcha1 = generateCaptcha1;
	}

	public String getEnterCaptcha1() {
		return enterCaptcha1;
	}

	public void setEnterCaptcha1(String enterCaptcha1) {
		this.enterCaptcha1 = enterCaptcha1;
	}

	public boolean isCptchFlag1() {
		return cptchFlag1;
	}

	public void setCptchFlag1(boolean cptchFlag1) {
		this.cptchFlag1 = cptchFlag1;
	}

	
	public void resetCptcha1() {
	//	System.out.println("enterd reset cpp----");

		this.cptchFlag1 = true;
		
		
		
	}
	
public void back() {
	 
				this.panal_flag = false;
			 
	}
	

}
