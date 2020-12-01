package com.mentor.action;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.datatable.statewiseexportofalcohol_dt;
import com.mentor.impl.statewiseexportofalcohol_impl;

public class statewiseexportofalcohol_action {
	
	statewiseexportofalcohol_impl impl = new statewiseexportofalcohol_impl();
	
	private String hidden;	
	private int loginUserId;
	private String loginUserNm;
	private String loginUserAdrs;
	
	public String getHidden() {
		
		try{
			 impl.getUserDetails(this);
			
		/*if(this.yearr!=null && this.yearr.length()>0 && this.montth!=null &&this.montth.length()>0)
		{
			impl.showdata(this);
		}*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	public int getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserNm() {
		return loginUserNm;
	}

	public void setLoginUserNm(String loginUserNm) {
		this.loginUserNm = loginUserNm;
	}

	public String getLoginUserAdrs() {
		return loginUserAdrs;
	}

	public void setLoginUserAdrs(String loginUserAdrs) {
		this.loginUserAdrs = loginUserAdrs;
	}
	
	private ArrayList year = new ArrayList();

	private ArrayList month = new ArrayList();

	private String yearr ;

	private String montth ;
	
	public String getYearr() {
		return yearr;
	}

	public void setYearr(String yearr) {
		this.yearr = yearr;
	}

	public String getMontth() {
		return montth;
	}

	public void setMontth(String montth) {
		this.montth = montth;
	}
	public ArrayList getYear() 
	{
		try 
		{
			 this.year=impl.yearListImpl(this);
		}
		catch (Exception e) 
		{			
			e.printStackTrace();
		}
		return year;
	}

	public void setYear(ArrayList year) {
		this.year = year;
	}

	public ArrayList getMonth() {
		try {
			this.month=impl.getMonthList(this);
		}
		catch (Exception e)
		{			
			e.printStackTrace();
		}
		return month;
	}

	public void setMonth(ArrayList month) {
		this.month = month;
}
	ArrayList displaylist = new ArrayList();


	public ArrayList getDisplaylist() {
		 
		return displaylist;
	}
	    public void setDisplaylist(ArrayList displaylist) {
		this.displaylist = displaylist;
	}
	
  public void getdata (ValueChangeEvent e)
  {
	  String val = (String)e.getNewValue();
	  this.montth = val ;
	  if(this.yearr!=null && this.yearr.length()>0 && this.montth!=null &&this.montth.length()>0)
		{	
			displaylist = impl.showdata(this);
		}  
	  
  }
  public void getdat(ValueChangeEvent e)
  {
	  String val = (String)e.getNewValue();
	  this.yearr = val ;
	  if(this.yearr!=null && this.yearr.length()>0 && this.montth!=null &&this.montth.length()>0)
		{	
			displaylist = impl.showdata(this);
		}  
	  
  }
//==============Save
	
	public String save ()
	{ 
		if(this.yearr!=null && this.yearr.length()>0)  
		{
		if(this.montth!=null &&this.montth.length()>0)	
		{
		 impl.save(this);
		 
		 this.displaylist=impl.showdata(this);
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Select Month !!! ", "Select Month !!!"));
		}
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Select Year !!! ", "Select Year !!!"));
			
			
		}
		
		return "";
	}

//==========================Reset
	public void reset ()
	{
	  this.year.clear();
	  
	  this.yearr = null ;
	  
	  this.month.clear();
	  
	  this.montth = null ;
	  
	  this.displaylist.clear();
 }	
}	
