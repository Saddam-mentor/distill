package com.mentor.action;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.mentor.impl.ProdOfSpiritReportImpl;

import net.sf.jasperreports.engine.JRException;

public class ProdOfSpiritReportAction {

	ProdOfSpiritReportImpl impl = new ProdOfSpiritReportImpl();

	private String pdfname;
	private boolean printFlag = false;
	private Date fromdate;
	private Date todate;
	
	private boolean excelFlag = false;
	private String exlname;
	
	
	public int dist_id;
	
	
	
	
	private String pdfDraft;
	private String radio="D";
	
	

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String isPdfDraft() {
		return pdfDraft;
	}

	public void setPdfDraft(String pdfDraft) {
		this.pdfDraft = pdfDraft;
	}

	

	

	public String getExlname() {
		return exlname;
	}

	public void setExlname(String exlname) {
		this.exlname = exlname;
	}

	public boolean isExcelFlag() {
		return excelFlag;
	}

	public void setExcelFlag(boolean excelFlag) {
		this.excelFlag = excelFlag;
	}

	

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

	

	

	public int getDist_id() {
		return dist_id;
	}

	public void setDist_id(int dist_id) {
		this.dist_id = dist_id;
	}

	public void print() {
		try {
		
		if(this.radio=="D")                ///rahul 03-12-2019
		{
		if(this.dist_id == 0 ){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Please select an unit!!", "Please select an unit!!"));
			return;}
		}
		if(impl.printReportImpl(this)==true)
		{
			this.printFlag=true;
		}else
		{
			this.printFlag=false;
		}
		}catch (Exception e) {
			 
			} 
		 
	}

	public void excel() {
		//impl.write(this);
	}
	
	
	public boolean dist_login;
	
	

	public boolean isDist_login() {
		return dist_login;
	}

	public void setDist_login(boolean dist_login) {
		this.dist_login = dist_login;
	}
	
	public String hidden;
	
	
	
	public String getHidden() {
		///impl.validateDistillery(this);

		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	
	public void reset() {

		this.printFlag = false;
		this.pdfname = "";
		this.fromdate = null;
		this.todate = null;
		
		this.excelFlag = false;
		this.exlname = null;
		this.fromdate = null;
		this.todate = null;
		this.radio = null;
		this.dist_list.clear();
	
	}

	public void radioListiner(ValueChangeEvent e) {
		this.excelFlag = false;
		this.printFlag = false;
		this.pdfname = "";
		
		
		/*if (this.getRadioVal().equalsIgnoreCase("DW")) {
			this.drpdwnFlg = true;
		} else if (this.getRadioVal().equalsIgnoreCase("A")) {
			this.drpdwnFlg = false;
		}*/
	}

public ArrayList dist_list = new ArrayList();



public ArrayList getDist_list() {
	this.dist_list = impl.getDistillery();
	return dist_list;
}

public void setDist_list(ArrayList dist_list) {
	this.dist_list = dist_list;
}

public void datalist(ValueChangeEvent e) {

}

}
