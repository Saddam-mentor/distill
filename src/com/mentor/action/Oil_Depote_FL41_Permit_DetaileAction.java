package com.mentor.action;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.RandomStringUtils;

import com.mentor.impl.Oil_Depote_FL41_Permit_DetaileImpl;


public class Oil_Depote_FL41_Permit_DetaileAction {

	Oil_Depote_FL41_Permit_DetaileImpl impl = new Oil_Depote_FL41_Permit_DetaileImpl();

	private String orderNo = null;
	private Date orderDate =null;
	private boolean panal_flag = false;
	private String ena_type = null;
	private String oil_dep_name=null;
	private String district=null;
	private double approve_qty=0.0;
	private double lifted_qty=0.0;
	private String Ditillery_name=null;
	

	public String getDitillery_name() {
		return Ditillery_name;
	}

	public void setDitillery_name(String ditillery_name) {
		Ditillery_name = ditillery_name;
	}

	public String getOil_dep_name() {
		return oil_dep_name;
	}

	public void setOil_dep_name(String oil_dep_name) {
		this.oil_dep_name = oil_dep_name;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public double getApprove_qty() {
		return approve_qty;
	}

	public void setApprove_qty(double approve_qty) {
		this.approve_qty = approve_qty;
	}

	public double getLifted_qty() {
		return lifted_qty;
	}

	public void setLifted_qty(double lifted_qty) {
		this.lifted_qty = lifted_qty;
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
	public void close() {
		this.panal_flag = false;
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
	
	

}
