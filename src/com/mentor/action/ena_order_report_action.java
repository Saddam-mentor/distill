package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.itextpdf.text.log.SysoCounter;
import com.mentor.impl.ena_order_report_impl;

public class ena_order_report_action {

	ena_order_report_impl impl= new ena_order_report_impl();
	
	
	private Date fromdate;
	private Date todate;
	private String radio;
	private ArrayList newList = new ArrayList();
	private boolean printFlag;
	private String pdfName;
	private boolean flag=false;
	private boolean excelFlag;
	private String exlname;
	
	
	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	
	public Date getTodate() {
		return todate;
	}
	
	public void setTodate(Date todate) {
		this.todate = todate;
	}
	
	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}
	public ArrayList getNewList() {
		return newList;
	}

	public void setNewList(ArrayList newList) {
		this.newList = newList;
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


	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isExcelFlag() {
		return excelFlag;
	}

	
	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}
	

	public String getExlname() {
		return exlname;
	}

	
	public void setExlname(String exlname) {
		this.exlname = exlname;
	}

	
	
	
	
	
	
	
	
	
	
	//===============================close methods =====================
	
	
	
	

	public void radiomethod(ValueChangeEvent e){
		String val=(String) e.getNewValue();
		this.radio=val;
		System.out.println("=======radio==========="+radio);
		//this.setFlag(true);
		this.printFlag = false;
	
	}
	
	
         public void print() {
		
		if(this.radio!=null && this.todate!=null && this.fromdate!=null && getRadio().equalsIgnoreCase("WUP")){
		impl.printReportWUP(this);
		
		}else if (this.radio!=null && this.todate!=null && this.fromdate!=null && getRadio().equalsIgnoreCase("OUP")) {
			impl.printReportOUP(this);
		}
         else if (this.radio!=null && this.todate!=null && this.fromdate!=null && getRadio().equalsIgnoreCase("IUP")) {
        	 impl.printReportIUP(this);
		}
		
		
		
	
	}


	public void close()
	{
		
		
	}
	
	//===================================Reset Methods ==================
    public void reset(){
    	this.printFlag = false;
		this.pdfName = null;
		this.fromdate = null;
		this.todate = null;
		this.setRadio(null);
		//this.exlname = null;
    	
    }
// =============================Excel Methods====================
    
public void excel() {
		
		if(this.radio!=null && this.todate!=null && this.fromdate!=null ){
			
			
			    if (radio.equalsIgnoreCase("WUP")) {
			     	try {
						impl.witeExcelWup(this);
					} catch (Exception e) {
						e.printStackTrace();
					}
				
		       	} else if(radio.equalsIgnoreCase("OUP")){
				   impl.witeExcelOup(this);
			        }else if (radio.equalsIgnoreCase("IUP")) {
				       impl.witeExcelIup(this);
			    }
	 
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("complete all fields ", "complete all fields"));
			}
		
		
	}
}


