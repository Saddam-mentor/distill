package com.mentor.action;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.mentor.impl.Monthwisereportonproductionandconsumptionofalcohol_impl;

public class Monthwisereportonproductionandconsumptionofalcohol_action {
	
	Monthwisereportonproductionandconsumptionofalcohol_impl impl = new Monthwisereportonproductionandconsumptionofalcohol_impl();
	
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
//=========================
private boolean printFlag = false;
private String pdfname;
private boolean excelFlag = false;
private String exlname;

public String getPdfname() {
	return pdfname;
}

public void setPdfname(String pdfname) {
	this.pdfname = pdfname;
}

public String getExlname() {
	return exlname;
}

public boolean isPrintFlag() {
	return printFlag;
}

public void setPrintFlag(boolean printFlag) {
	this.printFlag = printFlag;
}

public boolean isExcelFlag() {
	return excelFlag;
}

public void setExcelFlag(boolean excelFlag) {
	this.excelFlag = excelFlag;
}

public void setExlname(String exlname) {
	this.exlname = exlname;
}

public void print() {
	
	    if(this.yearr!=null && this.yearr.length()>0)
	    {
	    if(this.montth!=null &&this.montth.length()>0)
	    {
	    	impl.getMonthName(this);
	    	if(impl.printReportImpl(this)==true)
			{
				this.printFlag=true;
			}else
			{
				this.printFlag=false;
			}	
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
		
	}

	public void excel() {
		    
		    if(this.yearr!=null && this.yearr.length()>0)
		    {
		    if(this.montth!=null &&this.montth.length()>0)
		    {
		      impl.getMonthName(this);	
		      impl.write(this);
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
		    
	}

	private String monthName ;
	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public void reset() 
	{
        this.printFlag = false;
		this.pdfname = "";
		this.excelFlag = false;
		this.exlname = null;
		this.year.clear();
		this.month.clear();
		this.yearr = null ;
		this.montth = null ;
	}
}
