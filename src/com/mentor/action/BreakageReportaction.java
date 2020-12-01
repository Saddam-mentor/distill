package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import com.mentor.impl.BreakageReportimpl;
import com.mentor.utility.ResourceUtil;

public class BreakageReportaction {

	
	BreakageReportimpl impl = new BreakageReportimpl();

	
	private Date fromDate;
	private Date toDate;
	private String pdfname;
	private boolean printFlag;
	private String radio;
	private String radio2;
	private String unit_id;
	private String hidden;
	private boolean hideFlag;
	
	
	
	
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
	public boolean isHideFlag() {
		return hideFlag;
	}
	public void setHideFlag(boolean hideFlag) {
		this.hideFlag = hideFlag;
	}
	private ArrayList unit_List = new ArrayList();
	public ArrayList getUnit_List() {
	this.unit_List=impl.getList(this);
		return unit_List;
	}
	public void setUnit_List(ArrayList unit_List) {
		this.unit_List = unit_List;
	}

	public String getHidden() {
		try {
			impl.getDetails(this);
			if (ResourceUtil.getUserNameAllReq().trim().substring(0, 9).equalsIgnoreCase("Excise-DL"))
			{
	        this.radio="D";
	        this.unit_List=impl.getList(this);
	        this.hideFlag="T" != null;
	        
             }	
			
			// impl.getSeasonDetails(this);

		} catch (Exception e) {
		}
		return hidden;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	public String getPdfname() {
		return pdfname;
	}
	public void setPdfname(String pdfname) {
		this.pdfname = pdfname;
	}
	public boolean isPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}
	
	 
	public String getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}
	public String getRadio2() {
		return radio2;
	}
	public void setRadio2(String radio2) {
		this.radio2 = radio2;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public void radioListener(ValueChangeEvent e) {

		try {
			String val = (String) e.getNewValue();
			this.setRadio(val);
			
			this.printFlag=false;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	} 
	
	{   
	}
	public void print()
	{   
		try{
			/*if(radio==null || radio=="0" || radio.equalsIgnoreCase("0")){
				if(radio==null || radio=="0" || radio.equalsIgnoreCase("0")){
			impl.printReport(this);
				}
				else{
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Please Select the Radio!!","any one radio !!"));
				}
			}
			else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Please Select the Radio!!","Please Select the  Summary & Detail !!"));
			}*/
			{   
				if(radio==null || radio=="0" || radio.equalsIgnoreCase("0")){
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Please Select the radio!!","Please Select the radio !!"));
				}
				else{	
				impl.printReport(this);
				}
			}
			

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
	
	
	public void change_List(ValueChangeEvent e) {

		try {
			String val = (String) e.getNewValue();
			this.setUnit_id(val);
			
			this.printFlag=false;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	} 
	
	public void radioListener1(ValueChangeEvent e) {

		try {
			String val = (String) e.getNewValue();																					

			this.setRadio2(val);
			
			this.printFlag=false;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	} 
	public void reset() {
		this.printFlag = false;
		this.pdfname = null;
		this.radio=null;
		this.radio2=null;
		this.unit_id=null;

	}
	}

