package com.mentor.action;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.Search_For_BarCode_BottleCode_Impl;

public class Search_For_BarCode_BottleCode_Action {

	Search_For_BarCode_BottleCode_Impl impl=new Search_For_BarCode_BottleCode_Impl();
	private String bottleCode="";
	private String radio;
	public ArrayList searchDisplaylist = new ArrayList();
	public ArrayList getSearchDisplaylist() 
	{ 
		
		return searchDisplaylist;
	}
	public void setSearchDisplaylist(ArrayList searchDisplaylist) {
		this.searchDisplaylist = searchDisplaylist;
	}
	public String getBottleCode() {
		return bottleCode;
	}
	public void setBottleCode(String bottleCode) {
		this.bottleCode = bottleCode;
	}
	
public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	//--------------------------------------------------------
	public void checkETIN() 
	{
	}
//--------------------------------------------------------

	public void checkBottleCode() 
	{
		try
		{
		  if(this.getRadio()!=null)
		  {	
			if(this.getBottleCode()==null || this.getBottleCode().length()==0 )
			{
				
				 FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Kindly Enter BarCode Or QRCode","Kindly Enter BarCode Or QRCode"));
			 
				
				
				
			}else
			  {
				 
					if(this.getRadio().equalsIgnoreCase("BRC"))
					{
						if(this.getBottleCode().trim().length()==35)
						{
						
							this.searchDisplaylist = impl.bottlesDetailCaseCode(this);
						}else
						{

							 FacesContext.getCurrentInstance().addMessage(null,
										new FacesMessage("BarCode Length Should Be 35",
												"BarCode Length Should Be 35"));
						 
						}
					
					}
					else if(this.getRadio().equalsIgnoreCase("BCOD"))
					{
						if(this.getBottleCode().trim().length()==32)
						{
						
						this.searchDisplaylist = impl.getQrDetail(this);
						
						}else
						{

							 FacesContext.getCurrentInstance().addMessage(null,
										new FacesMessage("BottleCode Length Should Be 32",
												"BottleCode Length Should Be 32"));
						 
						}
						
					}
			  
			  }
		  }else
		  {
			  FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Kindly Select BarCode Or QRCode","Kindly Select BarCode Or QRCode"));
		  }
		}catch(Exception e)
		{
		
		}
	}
//--------------------------------------------------------

	public void reset() 
	{

		this.searchDisplaylist.clear();
		this.bottleCode = "";
		this.radio= "";
	}
	public void stateCountryChange(ValueChangeEvent e) {

		try 
		{
			
  reset();
			

		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}
}
