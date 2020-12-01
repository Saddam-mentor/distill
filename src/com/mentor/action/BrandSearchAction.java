package com.mentor.action;

import java.util.ArrayList;

import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.BrandSearchImpl;



public class BrandSearchAction {
	private String vch;
	private String radio;
	private String enterData;
	BrandSearchImpl impl=new BrandSearchImpl();
	
	ArrayList getVal=new ArrayList();
	
	public ArrayList getGetVal() {
		return getVal;
	}


	public void setGetVal(ArrayList getVal) {
		this.getVal = getVal;
	}


	public String getRadio() {
		return radio;
	}


	public void setRadio(String radio) {
		this.radio = radio;
	}


	public String getEnterData() {
		return enterData;
	}


	public void setEnterData(String enterData) {
		this.enterData = enterData;
	}


	public String getVch() {
		return vch;
	}


	public void setVch(String vch) {
		this.vch = vch;
	}


	public void radioListener(ValueChangeEvent e)
	{
		String r=(String)e.getNewValue();
		this.setRadio(r);
		this.getVal=null;
	}
	public void getData( )
	{
		this.getVal=impl.getData(this);
	}

}
